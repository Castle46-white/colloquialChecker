package jp.ac.chitose.colloquial_checker.repository;

import jp.ac.chitose.colloquial_checker.MySession;
import jp.ac.chitose.colloquial_checker.data.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ColloquialRepository implements IColloquialRepository {

    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedJdbc;

    public ColloquialRepository(JdbcTemplate jdbc,NamedParameterJdbcTemplate namedjdbc) {
        this.jdbc = jdbc;
        this.namedJdbc = namedjdbc;
    }


    //改行に対応する。
    //句点・改行コードで処理を分ける
    @Override
    public List<Colloquy> selectColloquial(int lineIndex, List<Morpheme> sentenceList) {

        if (sentenceList.size() <= 0) {
            return new ArrayList<>();
        }

        String sql =
                "select t.line_index, t.morpheme_index, colloquial.colloquial_id, colloquial.colloquial_name, " +
                        "part.part_level1_name, part.part_level2_name, colloquial.colloquial_category, conjugation_form.conjugation_form_name " +
                        "from colloquial " +
                        "left outer join conjugation_form " +
                        "on colloquial.conjugation_form_id = conjugation_form.conjugation_form_id " +
                        "left outer join (" +
                        "select level1.part_level1_id, level2.part_level2_id, " +
                        "level1.part_level1_name, level2.part_level2_name " +
                        "from part_level2 as level2 " +
                        "left outer join part_level1 as level1 " +
                        "on level2.part_level1_id = level1.part_level1_id " +
                        ") as part " +
                        "on colloquial.colloquial_part_level1 = part.part_level1_id and colloquial.colloquial_part_level2 = part.part_level2_id " +
                        "inner join (values ";
        for (int i = 0; i <= sentenceList.size() - 1; i++) {
            if (i > 0) {
                sql = sql + ",";
            }
            sql = sql +
                    "(:line_index" + i + ", :morpheme_index" + i + ", :morpheme_base_reading" + i +
                    ", :morpheme_part_level1" + i + ", :morpheme_part_level2" + i + ")";
        }
        sql = sql +
                ") as t (line_index, morpheme_index, morpheme_base_reading, morpheme_part_level1, morpheme_part_level2) " +
                "on colloquial.colloquial_base_reading = t.morpheme_base_reading and part.part_level1_name = t.morpheme_part_level1 and part.part_level2_name = t.morpheme_part_level2";

        MapSqlParameterSource param = new MapSqlParameterSource();
        for (int i = 0; i < sentenceList.size(); i++) {
            param.addValue("line_index" + i, lineIndex);
            param.addValue("morpheme_index" + i, i);
            param.addValue("morpheme_base_reading" + i, sentenceList.get(i).getBaseReading());
            param.addValue("morpheme_part_level1" + i, sentenceList.get(i).getPartLevel1());
            param.addValue("morpheme_part_level2" + i, sentenceList.get(i).getPartLevel2());
        }

        return namedJdbc.query(
                sql,
                param,
                new BeanPropertyRowMapper<>(Colloquy.class) {

                    @Override
                    public Colloquy mapRow(ResultSet rs, int rowNumber) throws SQLException {

                        return new Colloquy(
                                rs.getInt("line_index"),
                                rs.getInt("morpheme_index"),
                                rs.getInt("colloquial_id"),
                                rs.getString("colloquial_name"),
                                rs.getString("part_level1_name"),
                                rs.getString("part_level2_name"),
                                rs.getString("conjugation_form_name"),
                                rs.getString("colloquial_category"));
                    }
                });

    }

    @Override
    public List<ExampleSentence> selectExampleSentence(int colloquialId) {
        String sql = "select sentence, fix_sentence from example_sentence " +
                "left outer join colloquial using (colloquial_id) " +
                "where colloquial_id = :colloquial_id";

//        return namedJdbc.query(sql,
//                new MapSqlParameterSource().addValue("colloquial_id", colloquialId),
//                new BeanPropertyRowMapper<>(ExampleSentence.class) {
//                    @Override
//                    public ExampleSentence mapRow(ResultSet rs, int num) throws SQLException {
//                        return new ExampleSentence(
//                                rs.getString("es_sentence"),
//                                rs.getString("es_fix_sentence")
//                        );
//                    }
//                });
        var map = new MapSqlParameterSource().addValue("colloquial_id", colloquialId);
        return namedJdbc.query(sql, map, new BeanPropertyRowMapper<>(ExampleSentence.class));
    }


    @Override
    public int insertHistory(long accountId,int colloquialId){
        //String sql = "insert into history values(?,?,?,GETDATE())";
        String sql = "insert into history(account_id, colloquial_id, colloquial_time) VALUES (?, ?, now())";
        return jdbc.update(sql,accountId,colloquialId);
    }


    @Override
    public List<History> find(){

        String sql = "select " +
                "       colloquial_name, " +
                "       colloquial_count, " +
                "       historyA.colloquial_time as colloquial_date " +
                "from " +
                "       history as historyA " +
                "       inner join (select " +
                "                            colloquial_id, " +
                "                            max(history_id) as new_history_id, " +
                "                          count(history_id) as colloquial_count " +
                "                     from " +
                "                            history " +
                "                      where account_id = " + MySession.get().getAccountId() +
                "                     group by " +
                "                            colloquial_id) as historyB " +
                "       on historyA.colloquial_id = historyB.colloquial_id " +
                "       and historyA.history_id = historyB.new_history_id " +
                "inner join colloquial on historyA.colloquial_id = colloquial.colloquial_id " +
                "where account_id =  " + MySession.get().getAccountId() +
                "order by colloquial_count desc";

        List<History> historyList = jdbc.query(sql,
                new BeanPropertyRowMapper<>(History.class),
                new Object[]{});

        return historyList;
    }
}

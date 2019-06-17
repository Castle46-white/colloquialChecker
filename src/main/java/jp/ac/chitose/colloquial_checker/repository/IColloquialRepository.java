package jp.ac.chitose.colloquial_checker.repository;

import jp.ac.chitose.colloquial_checker.data.*;

import java.util.List;
import java.util.Map;

public interface IColloquialRepository {

    /**
     * @param lineIndex    行番号
     * @param sentenceList 形態素解析されたレポートの1行
     * @return 話しことばと思われる単語が入ったリスト Lise<Colloquy>
     */
    List<Colloquy> selectColloquial(int lineIndex, List<Morpheme> sentenceList);

    /**
     * @param colloquialId 話し言葉のID
     * @return 話し言葉のIDに関連した話し言葉の例文をList<ExampleSentence>型で返す
     */
    List<ExampleSentence> selectExampleSentence(int colloquialId);

    /**
     *
     * @param historyId 履歴のID
     * @param accountId　アカウントID
     * @param colloquialId　話しことばID
     * @return 追加した行数
     */

    int insertHistory(long accountId,int colloquialId);



    /**
     * Historyテーブルから必要なデータを検索
     *
     * @return レコード内容を　{@link History}　の　{@link List}　で返す
     */

    List<History> find();
}

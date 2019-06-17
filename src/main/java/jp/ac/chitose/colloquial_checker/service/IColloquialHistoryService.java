package jp.ac.chitose.colloquial_checker.service;

import jp.ac.chitose.colloquial_checker.data.History;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;


public interface IColloquialHistoryService {

    /**
     * 話し言葉の追加
     * @param historyId 履歴ID
     * @param accountId　アカウントID
     * @param colloquialId　話しことばID
     * @param colloquialTime　更新日時
     * @return　追加成功したらtrue,失敗したらfalse
     * @throws BadSqlGrammarException
     */

    boolean colloquialHistoryAdd(long accountId,int colloquialId) throws BadSqlGrammarException;




    /**
     * 話しことば、回数の一覧をColloquialHistory型のリストで検索する
     *
     * @return　ColloquialHistory型のListインスタンス
     */

    List<History> findHistory();
}

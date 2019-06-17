package jp.ac.chitose.colloquial_checker.service;

import jp.ac.chitose.colloquial_checker.data.Morpheme;

import java.util.List;

public interface IAnalyzeReportService {

    /**
     * 形態素解析された単語,品詞,活用形を使って話しことばを抽出する
     *
     * @param report ユーザーが送信したレポート
     * @return 話しことばか否かを調べた単語リスト List<Morpheme> が入った行リスト List<List<Morpheme>> を返す
     */
    List<List<Morpheme>> analyzeReport(String report);


}

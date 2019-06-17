package jp.ac.chitose.colloquial_checker.service;

import java.util.List;

public interface IMeCabService {

    /**
     * MeCabで1回目の形態素解析を行い、ユーザーが書いた文章からMorphemeリストを作成する
     *
     * @param report ユーザーが書いた文章
     * @return 語彙素表記、語彙素読み、品詞大分類、品詞中分類が入ったMorphemeリスト
     */
    List<String> morphologicalAnalysis1st(String report);

    /**
     * MeCabで2回目の形態素解析を行い、単語から単語の読みを作成する
     *
     * @param word 単語
     * @return 単語の読み
     */
    String morphologicalAnalysis2nd(String word);

}

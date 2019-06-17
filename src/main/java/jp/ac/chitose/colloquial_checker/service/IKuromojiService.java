package jp.ac.chitose.colloquial_checker.service;

import com.atilika.kuromoji.ipadic.Token;

import java.util.List;

public interface IKuromojiService {


    /**
     * ユーザーが書いた文章をkuromojiで形態素解析し、Morphemeリストを作成する
     *
     * @param report
     * @return 語彙素表記、語彙素読み、品詞大分類、品詞中分類が入ったMorphemeリスト
     */
    List<Token> morphologicalAnalysis(String report);
}

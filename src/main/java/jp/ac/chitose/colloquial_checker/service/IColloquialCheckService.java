package jp.ac.chitose.colloquial_checker.service;

import jp.ac.chitose.colloquial_checker.data.Colloquy;
import jp.ac.chitose.colloquial_checker.data.Morpheme;

import java.util.List;

public interface IColloquialCheckService {

    /**
     * Colloquyの話し言葉カテゴリごとに話し言葉か否かを判別させる
     *
     * @param sentenceList 形態素解析されたレポート
     * @return List<Colloquy> に話し言葉の情報が入ったレポート List<List<Morpheme>>
     */
    List<List<Morpheme>> colloquialCheck(List<List<Morpheme>> sentenceList);

    /**
     * １．「対象キーワード」＝話しことば か否かを判定する
     * 話し言葉であった場合はMorphemeのフィールドのList<Colloquy>に情報を追加する
     *
     * @param colloquy 話し言葉の情報
     * @return 話し言葉であればtrue, 違うならfalse
     */
    boolean independenceCheck(Colloquy colloquy);

    /**
     * ２．接頭語（っぽい）＋「対象キーワード」＝話しことば
     * 話し言葉であった場合はMorphemeのフィールドのList<Colloquy>に情報を追加する
     *
     * @param colloquy 話し言葉の情報
     * @return 話し言葉であればtrue, 違うならfalse
     */
    boolean prefixCheck(Colloquy colloquy);


    /**
     * ３．「対象キーワード」＋接尾語（っぽい）＝話しことば
     * 話し言葉であった場合はMorphemeのフィールドのList<Colloquy>に情報を追加する
     *
     * @param colloquy 話し言葉の情報
     * @return 話し言葉であればtrue, 違うならfalse
     */
    boolean suffixCheck(Colloquy colloquy);

    /**
     * ４．「対象キーワード」＋共起単語（同一文章内）＝話しことば
     * 話し言葉であった場合はMorphemeのフィールドのList<Colloquy>に情報を追加する
     *
     * @param colloquy 話し言葉の情報
     * @return 話し言葉であればtrue, 違うならfalse
     */
    boolean collocationCheck(Colloquy colloquy);

    /**
     * ５. その他＝文法的なミス（若者的な言葉づかい）
     * 話し言葉であった場合はMorphemeのフィールドのList<Colloquy>に情報を追加する
     *
     * @param colloquy 話し言葉の情報
     * @return 話し言葉であればtrue, 違うならfalse
     */
    boolean otherCheck(Colloquy colloquy);

}

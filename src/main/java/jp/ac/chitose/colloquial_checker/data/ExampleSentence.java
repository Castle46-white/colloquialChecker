package jp.ac.chitose.colloquial_checker.data;

import java.io.Serializable;

public class ExampleSentence implements Serializable {
    private String sentence;             //例文
    private String fixSentence;    //例文の修正案

    //初期化
    public ExampleSentence() {
        this.sentence = "";
        this.fixSentence = "";
    }

    //コンストラクタ
    public ExampleSentence(String sentence, String fixSentence) {
        this.sentence = sentence;
        this.fixSentence = fixSentence;
    }


    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getFixSentence() {
        return fixSentence;
    }

    public void setFixSentence(String fixSentence) {
        this.fixSentence = fixSentence;
    }
}

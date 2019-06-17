package jp.ac.chitose.colloquial_checker.data;

import jp.ac.chitose.colloquial_checker.ColloquialCategory;

import java.io.Serializable;
import java.util.List;

public class Colloquy implements Serializable {

    private int lineIndex;                              //行番号
    private int sentenceIndex;                          //文番号
    private int id;                                     //データベースid
    private String form;                                //基本形
    private String partLevel1;                          //品詞大分類
    private String partLevel2;                          //品詞中分類
    private String conjugationForm;                     //活用形
    private String category;                            //話し言葉のカテゴリ(ColloquialCategoryから選択)
    private List<ExampleSentence> exampleSentenceList;  //例文と修正案の提示

    //初期化
    public Colloquy() {
        this.lineIndex = -1;
        this.sentenceIndex = -1;
        this.id = -1;
        this.form = "";
        this.partLevel1 = "";
        this.partLevel2 = "";
        this.conjugationForm = "";
        this.category = ColloquialCategory.UNKNOWN;
        this.exampleSentenceList = null;
    }

    //コンストラクタ
    public Colloquy(int lineIndex, int sentenceIndex, int id, String form, String partLevel1, String partLevel2, String conjugationForm, String category, List<ExampleSentence> exampleSentenceList) {
        this.lineIndex = lineIndex;
        this.sentenceIndex = sentenceIndex;
        this.id = id;
        this.form = form;
        this.partLevel1 = partLevel1;
        this.partLevel2 = partLevel2;
        this.conjugationForm = conjugationForm;
        this.category = category;
        this.exampleSentenceList = exampleSentenceList;
    }

    //exampleSentenceListを除いたコンストラクタ
    public Colloquy(int lineIndex, int sentenceIndex, int id, String form, String partLevel1, String partLevel2, String conjugationForm, String category) {
        this.lineIndex = lineIndex;
        this.sentenceIndex = sentenceIndex;
        this.id = id;
        this.form = form;
        this.partLevel1 = partLevel1;
        this.partLevel2 = partLevel2;
        this.conjugationForm = conjugationForm;
        this.category = category;
        this.exampleSentenceList = null;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public int getSentenceIndex() {
        return sentenceIndex;
    }

    public void setSentenceIndex(int sentenceIndex) {
        this.sentenceIndex = sentenceIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getPartLevel1() {
        return partLevel1;
    }

    public void setPartLevel1(String partLevel1) {
        this.partLevel1 = partLevel1;
    }

    public String getPartLevel2() {
        return partLevel2;
    }

    public void setPartLevel2(String partLevel2) {
        this.partLevel2 = partLevel2;
    }

    public String getConjugationForm() {
        return conjugationForm;
    }

    public void setConjugationForm(String conjugationForm) {
        this.conjugationForm = conjugationForm;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ExampleSentence> getExampleSentenceList() {
        return exampleSentenceList;
    }

    public void setExampleSentenceList(List<ExampleSentence> exampleSentenceList) {
        this.exampleSentenceList = exampleSentenceList;
    }

    public boolean isConjugationForm() {
        return this.conjugationForm == null;
    }

}

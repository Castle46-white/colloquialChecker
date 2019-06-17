package jp.ac.chitose.colloquial_checker.data;


import java.io.Serializable;
import java.util.Objects;

public class Morpheme implements Serializable {
    private String surfaceForm;         //表層系 ex) 思っ
    private String baseForm;            //基本形 ex) 思う
    private String baseReading;         //読み ex) オモウ
    private String partLevel1;          //品詞細分類1 ex) 動詞
    private String partLevel2;          //品詞細分類2 ex) 自立
    private String conjugationForm;     //活用形　ex) 連用タ接続
    private Colloquy colloquy;          //話しことばの情報(話しことばでないのなら null)

    //初期化
    public Morpheme() {
        this.surfaceForm = "";
        this.baseForm = "";
        this.baseReading = "";
        this.partLevel1 = "";
        this.partLevel2 = "";
        this.conjugationForm = "";
        this.colloquy = null;
    }

    //コンストラクタ
    public Morpheme(String surfaceForm, String baseForm, String baseReading, String partLevel1, String partLevel2, String conjugationForm, Colloquy colloquy) {
        this.surfaceForm = surfaceForm;
        this.baseForm = baseForm;
        this.baseReading = baseReading;
        this.partLevel1 = partLevel1;
        this.partLevel2 = partLevel2;
        this.conjugationForm = conjugationForm;
        this.colloquy = colloquy;
    }

    //colloquyを除いたコンストラクタ
    public Morpheme(String surfaceForm, String baseForm, String baseReading, String partLevel1, String partLevel2, String conjugationForm) {
        this.surfaceForm = surfaceForm;
        this.baseForm = baseForm;
        this.baseReading = baseReading;
        this.partLevel1 = partLevel1;
        this.partLevel2 = partLevel2;
        this.conjugationForm = conjugationForm;
        this.colloquy = null;
    }

    public String getSurfaceForm() {
        return surfaceForm;
    }

    public void setSurfaceForm(String surfaceForm) {
        this.surfaceForm = surfaceForm;
    }

    public String getBaseForm() {
        return baseForm;
    }

    public void setBaseForm(String baseForm) {
        this.baseForm = baseForm;
    }

    public String getBaseReading() {
        return baseReading;
    }

    public void setBaseReading(String baseReading) {
        this.baseReading = baseReading;
    }

    public String getPartLevel1() {
        return partLevel1;
    }

    public void setPartLevel1(String part) {
        this.partLevel1 = part;
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

    public Colloquy getColloquy() {
        return colloquy;
    }

    public void setColloquy(Colloquy colloquy) {
        this.colloquy = colloquy;
    }

    public boolean hasColloquy() {
        return !Objects.isNull(colloquy);
    }
}

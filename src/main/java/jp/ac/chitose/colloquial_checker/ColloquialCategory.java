package jp.ac.chitose.colloquial_checker;

public class ColloquialCategory {

    /**
     * １．「対象キーワード」＝話しことば
     * 例：たくさん、大丈夫、だんだん、どんどん
     */
    public static final String INDEPENDENCE = "INDEPENDENCE";

    /**
     * ２． 接頭語（っぽい）＋「対象キーワード」＝話しことば
     * 例：て＋しまう、知ら＋ないで、（話し）＋てる、（聞い）＋とく、（行く）＋し
     */
    public static final String PREFIX = "PREFIX";

    /**
     * ３．「対象キーワード」＋接尾語（っぽい）＝話しことば
     * 例：くせ＋に、わり＋と、割＋と、（飛）ん＋で、（高）く＋て
     */
    public static final String SUFFIX = "SUFFIX";

    /**
     * ４．「対象キーワード」＋共起単語（同一文章内）＝話しことば
     * 例：一番＋（人口が）多い、
     */
    public static final String COLLOCATION = "COLLOCATION";

    /**
     * ５. その他＝文法的なミス（若者的な言葉づかい）
     * 例：ぜんぜん＋○○ない、ぜんぜん＋違う、信頼 + されなくする
     */
    public static final String OTHER = "OTHER";

    /**
     * 1.～5.のどれにも属さないもの
     */
    public static final String UNKNOWN = "UNKNOWN";
}

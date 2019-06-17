package jp.ac.chitose.colloquial_checker;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class KuromojiTest {
    public static void main(String args[]) {

        // 解析対象文字列をセット
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next().replace("★", "");


        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(str);
        //結果を出力してみる
        for (Token token : tokens) {
            System.out.println("==================================================");
            System.out.println(token);
            System.out.println("表層系\t\t : " + token.getSurface());
            System.out.println("品詞大分類\t : " + token.getPartOfSpeechLevel1());
            System.out.println("品詞中分類\t : " + token.getPartOfSpeechLevel2());
            System.out.println("品詞小分類\t : " + token.getPartOfSpeechLevel3());
            System.out.println("品詞細分類\t : " + token.getPartOfSpeechLevel4());
            System.out.println("活用型\t\t : " + token.getConjugationType());
            System.out.println("活用形\t\t : " + token.getConjugationForm());
            System.out.println("基本形\t\t : " + token.getBaseForm());
            System.out.println("読み\t\t\t : " + token.getReading());
            System.out.println("発音\t\t\t : " + token.getPronunciation());
            System.out.println("全情報txt\t : " + token.getAllFeatures());
            System.out.println("単語の場所\t : " + token.getPosition());
            System.out.println("全情報Array\t : " + Arrays.asList(token.getAllFeaturesArray()));
            System.out.println("辞書にある言葉? : " + token.isKnown());
            System.out.println("未知語?\t\t : " + token.isKnown());
            System.out.println("ユーザ定義?\t : " + token.isUser());
        }
    }
}

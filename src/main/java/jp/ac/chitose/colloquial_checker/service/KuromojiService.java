package jp.ac.chitose.colloquial_checker.service;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KuromojiService implements IKuromojiService {

    @Override
    public List<Token> morphologicalAnalysis(String report) {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(report);
//        List<Morpheme> morphemeList = new ArrayList<>();
//         //結果を出力してみる
//        for (Token token : tokens) {
//            System.out.println("==================================================");
//            System.out.println("表層系\t\t : " + token.getSurface());
//            System.out.println("品詞大分類\t : " + token.getPartOfSpeechLevel1());
//            System.out.println("品詞中分類\t : " + token.getPartOfSpeechLevel2());
//            System.out.println("品詞小分類\t : " + token.getPartOfSpeechLevel3());
//            System.out.println("品詞細分類\t : " + token.getPartOfSpeechLevel4());
//            System.out.println("活用形\t\t : " + token.getConjugationForm());
//            System.out.println("活用型\t\t : " + token.getConjugationType());
//            System.out.println("語彙素読み\t : " + token.getLemmaReadingForm());
//            System.out.println("語彙素表記\t : " + token.getLemma());
//            System.out.println("発音\t\t\t : " + token.getPronunciation());
//            System.out.println("発音形基本形\t : " + token.getPronunciationBaseForm());
//            System.out.println("書字形\t\t : " + token.getWrittenForm());
//            System.out.println("書字形出現形\t : " + token.getWrittenBaseForm());
//            System.out.println("語種\t\t\t : " + token.getLanguageType());
//            System.out.println("語頭変化型\t : " + token.getInitialSoundAlterationType());
//            System.out.println("語頭変化形\t : " + token.getInitialSoundAlterationForm());
//            System.out.println("語末変化型\t : " + token.getFinalSoundAlterationType());
//            System.out.println("語末変化形\t : " + token.getFinalSoundAlterationForm() + "\n");
//            System.out.println("全情報txt\t : " + token.getAllFeatures());
//            System.out.println("単語の場所\t : " + token.getPosition());
//            System.out.println("reading : " + token.getLemmaReadingForm());
//            System.out.println("全情報Array\t : " + Arrays.asList(token.getAllFeaturesArray()));
//            System.out.println("辞書にある言葉? : " + token.isKnown());
//            System.out.println("未知語?\t\t : " + token.isKnown());
//            System.out.println("ユーザ定義?\t : " + token.isUser());

//            morphemeList.add(new Morpheme(token.getLemma(), token.getLemmaReadingForm(), token.getPartOfSpeechLevel1(), token.getPartOfSpeechLevel2()));

//        }

        return tokens;
    }
}

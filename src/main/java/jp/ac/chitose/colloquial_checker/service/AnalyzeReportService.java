package jp.ac.chitose.colloquial_checker.service;

import com.atilika.kuromoji.ipadic.Token;
import jp.ac.chitose.colloquial_checker.data.Morpheme;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AnalyzeReportService implements IAnalyzeReportService {

    private static final String LINE_SEPARATOR_PATTERN = "\r\n|[\n\r\u2028\u2029\u0085]"; //改行コード

    @SpringBean
    private IMeCabService meCabService;
    @SpringBean
    private IKuromojiService kuromojiService;
    @SpringBean
    private IColloquialCheckService colloquialCheckService;

    public AnalyzeReportService(IMeCabService meCabService, IKuromojiService kuromojiService,
                                IColloquialCheckService colloquialCheckService) {
        this.meCabService = meCabService;
        this.kuromojiService = kuromojiService;
        this.colloquialCheckService = colloquialCheckService;
    }

    @Override
    public List<List<Morpheme>> analyzeReport(String report) {

        //レポートの単語を解析
        List<List<Morpheme>> sentenceList = morphologicalAnalysis(report);

        //話し言葉の解析
        sentenceList = colloquialCheckService.colloquialCheck(sentenceList);

        //何も入っていない行があった場合、nullを追加
        for (List<Morpheme> morphemeList : sentenceList) {
            if (morphemeList.size() <= 0) {
                morphemeList.add(new Morpheme());
            }
        }

        return sentenceList;
    }

    //レポートを形態素解析し、morpheme型Listが入ったListを返す
    //List<morpheme>は一文における単語群
    private List<List<Morpheme>> morphologicalAnalysis(String report) {

        //文章を文ごとのListにする
        var paragraphList = split(report);

        //MeCabによる形態素解析を行う
//        List<List<Morpheme>> sentenceList = morphologicalAnalysisInMecab(paragraphList);

        //kuromojiによる形態素解析を行う
        List<List<Morpheme>> sentenceList = morphologicalAnalysisInKuromoji(paragraphList);

        return sentenceList;
    }

    private List<List<Morpheme>> morphologicalAnalysisInMecab(List<String> paragraphList) {
        List<List<Morpheme>> sentenceList = new ArrayList<>();  //文章における文リスト

        //文ごとに形態素解析してmorphemeListにaddする
        //2回形態素解析しているのは基本形の読みをとるため
        for (String paragraph : paragraphList) {

            List<Morpheme> morphemeList = new ArrayList<>();        //一文における単語リスト

            List<String> list = meCabService.morphologicalAnalysis1st(paragraph);  //meCab ipadic による解析1回目

            for (String morphemes : list) {

                String[] str = morphemes.split(",");

                try {
                    //str[0]:表層系 str2[0]:基本形 str2[8]:基本形読み(カタカナ) str[1]:品詞細分類1 str[2]:品詞細分類2 str[6]:活用形
                    morphemeList.add(new Morpheme(str[0], str[7], meCabService.morphologicalAnalysis2nd(str[7]), str[1], str[2], str[6]));
                }
                //読みが辞書にないとき
                catch (ArrayIndexOutOfBoundsException e) {
                    //str[0]:表層系 str[0]:基本形 str[1]:品詞細分類1 str[2]:品詞細分類2 str[8]:活用形
                    morphemeList.add(new Morpheme(str[0], str[7], "", str[1], str[2], ""));
                }
            }
            //一文を追加
            sentenceList.add(morphemeList);
        }

        return sentenceList;
    }

    private List<List<Morpheme>> morphologicalAnalysisInKuromoji(List<String> paragraphList) {
        List<List<Morpheme>> sentenceList = new ArrayList<>();  //文章における文リスト

        //文ごとに形態素解析してmorphemeListにaddする
        //2回形態素解析しているのは基本形の読みをとるため
        for (String paragraph : paragraphList) {

            List<Morpheme> morphemeList = new ArrayList<>();        //一文における単語リスト

            var kuromojiTokens = kuromojiService.morphologicalAnalysis(paragraph);

            for (Token token : kuromojiTokens) {

                String baseFormReading = kuromojiService.morphologicalAnalysis(token.getBaseForm()).get(0).getReading();

                morphemeList.add(new Morpheme(
                                token.getSurface(),
                                token.getBaseForm(),
                                baseFormReading,
                                token.getPartOfSpeechLevel1(),
                                token.getPartOfSpeechLevel2(),
                                token.getConjugationForm()
                        )
                );
            }
            sentenceList.add(morphemeList);
        }
        return sentenceList;
    }

    //改行ごとにListにaddする
    private List<String> split(String report) {
        String[] split = report.split(LINE_SEPARATOR_PATTERN);
        return Arrays.asList(split);
    }
}

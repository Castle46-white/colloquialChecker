package jp.ac.chitose.colloquial_checker.service;

import jp.ac.chitose.colloquial_checker.ColloquialCategory;
import jp.ac.chitose.colloquial_checker.MySession;
import jp.ac.chitose.colloquial_checker.data.Colloquy;
import jp.ac.chitose.colloquial_checker.data.Morpheme;
import jp.ac.chitose.colloquial_checker.repository.IColloquialRepository;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColloquialCheckService implements IColloquialCheckService {

    @SpringBean
    private IColloquialRepository colloquialRepository;
    @SpringBean
    private IColloquialHistoryService colloquialHistoryService;


    public ColloquialCheckService(IColloquialRepository colloquialRepository,IColloquialHistoryService colloquialHistoryService) {
        this.colloquialRepository = colloquialRepository;
        this.colloquialHistoryService = colloquialHistoryService;
    }

    private List<List<Morpheme>> sentenceList;

    @Override
    public List<List<Morpheme>> colloquialCheck(List<List<Morpheme>> sentenceList) {


        //話し言葉と思われる単語をDBから検索

        int lineIndex = 0;
        List<List<Colloquy>> sentenceColloquyList = new ArrayList<>();
        for (List<Morpheme> morphemeList : sentenceList) {
            sentenceColloquyList.add(colloquialRepository.selectColloquial(lineIndex, morphemeList));
            lineIndex++;
        }

        this.sentenceList = sentenceList;

        //話し言葉である単語に情報を追加する
        for (List<Colloquy> colloquyList : sentenceColloquyList) {
            for (Colloquy colloquy : colloquyList) {
                //活用形に関係なく話し言葉である or DBに格納されている活用形と一致しているのならば
                if (colloquy.isConjugationForm()
                        || this.sentenceList
                        .get(colloquy.getLineIndex()).get(colloquy.getSentenceIndex()).getConjugationForm()
                        .equals(colloquy.getConjugationForm())) {
                    //その話し言葉のカテゴリの判定で話し言葉であったら
                    if (colloquialCategoryCheck(colloquy)) {
                        //話し言葉に例文と修正文例を追加
                        colloquy.setExampleSentenceList(colloquialRepository.selectExampleSentence(colloquy.getId()));
                        //文の中にある単語の中で話し言葉の単語に話し言葉情報を追加
                        addColloquy(colloquy);

                        //System.out.println(colloquialHistoryService.colloquialHistorySelect(MySession.get().getAccountId(),colloquy.getId()));
                        colloquialHistoryService.colloquialHistoryAdd(MySession.get().getAccountId(),colloquy.getId());

                    }
                }
            }
        }

        return this.sentenceList;
    }

    private boolean colloquialCategoryCheck(Colloquy colloquy) {
        //「対象キーワード」＝話しことば
        if (colloquy.getCategory().equals(ColloquialCategory.INDEPENDENCE)) {
            return independenceCheck(colloquy);
        }
        //接頭語（っぽい）＋「対象キーワード」＝話しことば
        else if (colloquy.getCategory().equals(ColloquialCategory.PREFIX)) {
            return prefixCheck(colloquy);
        }
        //「対象キーワード」＋接尾語（っぽい）＝話しことば
        else if (colloquy.getCategory().equals(ColloquialCategory.SUFFIX)) {
            return suffixCheck(colloquy);
        }
        //「対象キーワード」＋共起単語（同一文章内）＝話しことば
        else if (colloquy.getCategory().equals(ColloquialCategory.COLLOCATION)) {
            return collocationCheck(colloquy);
        }
        //その他＝文法的なミス（若者的な言葉づかい）
        else if (colloquy.getCategory().equals(ColloquialCategory.OTHER)) {
            return otherCheck(colloquy);
        }
        return false;
    }

    //「対象キーワード」＝話しことば
    @Override
    public boolean independenceCheck(Colloquy colloquy) {
        //現時点では全てtrueであると思われる
        return true;
    }

    //接頭語（っぽい）＋「対象キーワード」＝話しことば
    @Override
    public boolean prefixCheck(Colloquy colloquy) {
        if (false/*一つ前の単語の品詞大分類が対象と一致している*/) {
            return true;
        } else if (false/*一つ前の単語が対象の語句である*/) {
            return true;
        }
        return false;
    }

    //「対象キーワード」＋接尾語（っぽい）＝話しことば
    @Override
    public boolean suffixCheck(Colloquy colloquy) {
        if (false/*一つ後の単語の品詞大分類が対象と一致している*/) {
            return true;
        } else if (false/*一つ後の単語が対象の語句である*/) {
            return true;
        }
        return false;
    }

    //「対象キーワード」＋共起単語（同一文章内）＝話しことば
    @Override
    public boolean collocationCheck(Colloquy colloquy) {
        return true;
    }

    //その他＝文法的なミス（若者的な言葉づかい）
    @Override
    public boolean otherCheck(Colloquy colloquy) {
        return true;
    }


    /**
     * 単語に話し言葉の情報を追加
     *
     * @param colloquy 話し言葉の情報
     */
    private void addColloquy(Colloquy colloquy) {
        this.sentenceList.get(colloquy.getLineIndex()).get(colloquy.getSentenceIndex()).setColloquy(colloquy);
    }

}

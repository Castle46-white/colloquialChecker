package jp.ac.chitose.colloquial_checker.service;

//import net.moraleboost.mecab.Lattice;
//import net.moraleboost.mecab.Node;
//import net.moraleboost.mecab.impl.StandardTagger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeCabService implements IMeCabService {


    public List<String> morphologicalAnalysis1st(String text) {

        return morphologicalAnalysis(text);

    }

    @Override
    public String morphologicalAnalysis2nd(String word) {

        var list = morphologicalAnalysis(word);
        String baseForm = "";
        for (String str : list) {
            baseForm = baseForm + str.split(",")[8];
        }
        return baseForm;
    }


    private List<String> morphologicalAnalysis(String text) {
//        StandardTagger tagger = new StandardTagger("");
//
//        // Lattice（形態素解析に必要な実行時情報が格納されるオブジェクト）を構築
//        Lattice lattice = tagger.createLattice();
//        lattice.setSentence(text);
//
//        // tagger.parse()を呼び出して、文字列を形態素解析する。
//        tagger.parse(lattice);
//
//        // 一つずつ形態素をたどりながら、表層形と素性を出力
//        Node node = lattice.bosNode();
//        List<String> list = new ArrayList<>();
//
//        while (node != null) {
//            String surface = node.surface();
//            String feature = node.feature();
//            String[] str = node.feature().split(",");
//            if (!(str[0].equals("BOS/EOS"))) {
//                list.add(surface + "," + feature);
//            }
//            node = node.next();
//        }
//        return list;
        return null;
    }
}
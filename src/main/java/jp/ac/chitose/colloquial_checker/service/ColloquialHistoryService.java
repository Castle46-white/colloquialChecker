package jp.ac.chitose.colloquial_checker.service;

import jp.ac.chitose.colloquial_checker.data.History;
import jp.ac.chitose.colloquial_checker.repository.IColloquialRepository;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ColloquialHistoryService implements IColloquialHistoryService {

    @SpringBean
    private IColloquialRepository colloquialRepository;

    public ColloquialHistoryService(IColloquialRepository colloquialRepository) {
        this.colloquialRepository = colloquialRepository;
    }


    @Override
    public boolean colloquialHistoryAdd(long accountId,int colloquialId) throws BadSqlGrammarException {
        //    System.out.println("historyId:" + historyId);
        System.out.println("accountId:" + accountId);
        System.out.println("colloquialId:" + colloquialId);

        int n = colloquialRepository.insertHistory(accountId,colloquialId);

        return  n>0;
    }


    @Override
    public List<History> findHistory() {
        List<History> historyList = colloquialRepository.find();
        System.out.println("データ件数：" + historyList.size());
        return historyList;
    }
}

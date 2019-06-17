package jp.ac.chitose.colloquial_checker.page;

import jp.ac.chitose.colloquial_checker.MySession;
import jp.ac.chitose.colloquial_checker.data.History;
import jp.ac.chitose.colloquial_checker.repository.IColloquialRepository;
import jp.ac.chitose.colloquial_checker.service.IColloquialHistoryService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;


import java.util.List;

public class ColloquialHistoryPage extends WebPage {
    private static final long serialVersionUID = -3315326755316913206L;

    @SpringBean
    private IColloquialRepository colloquialRepository;
    @SpringBean
    private IColloquialHistoryService colloquialHistoryService;

    public ColloquialHistoryPage(){

        IModel<List<History>> historyListModel = Model.ofList(colloquialHistoryService.findHistory());

        ListView<History> historyListView = new ListView<History>("historyList",historyListModel) {
            @Override
            protected void populateItem(ListItem<History> listItem) {
                IModel<History> historyModel = listItem.getModel();
                History colloquialHistory = historyModel.getObject();

                IModel<String> colloquialNameModel = Model.of(colloquialHistory.getColloquialName());
                Label colloquialNameLabel = new Label("colloquialName",colloquialNameModel);
                listItem.add(colloquialNameLabel);

                IModel<Integer> countModel = Model.of(colloquialHistory.getColloquialCount());
                Label countLabel = new Label("count",countModel);
                listItem.add(countLabel);

                IModel<String> dateModel = Model.of(colloquialHistory.getColloquialDate());
                Label dateLabel = new Label("date",dateModel);
                listItem.add(dateLabel);


            }
        };
        add(historyListView);


        Link<Void> toStudentTopPage = new Link<>("toStudentTopPage") {
            private static final long serialVersionUID = 573619681460444434L;

            @Override
            public void onClick() {
                setResponsePage(new StudentTopPage());
            }
        };


        Link<Void> toColloquialCheckOldPage = new Link<>("toColloquialCheckPageOld") {
            private static final long serialVersionUID = -6176575191180563034L;

            @Override
            public void onClick() {
                setResponsePage(new ColloquialCheckPageOld());
            }
        };


        Link<Void> toColloquialCheckPage = new Link<>("toColloquialCheckPage") {
            private static final long serialVersionUID = -727784739796812475L;

            @Override
            public void onClick() {
                setResponsePage(new ColloquialCheckPage());
            }
        };


        Link<Void> toColloquialHistoryPage = new Link<>("toColloquialHistoryPage") {
            private static final long serialVersionUID = -6158488720015875828L;

            @Override
            public void onClick() {
                setResponsePage(new ColloquialHistoryPage());
            }
        };


        Link<Void> signOut = new Link<>("signOut") {
            private static final long serialVersionUID = -6239391976133601685L;

            @Override
            public void onClick() {
                MySession.get().signOut();
                setResponsePage(new SignPage());
            }
        };

        add(toStudentTopPage);
        add(toColloquialCheckOldPage);
        add(toColloquialCheckPage);
        add(toColloquialHistoryPage);
        add(signOut);


    }


}

package jp.ac.chitose.colloquial_checker.page;

import jp.ac.chitose.colloquial_checker.MyRole;
import jp.ac.chitose.colloquial_checker.data.Colloquy;
import jp.ac.chitose.colloquial_checker.data.ExampleSentence;
import jp.ac.chitose.colloquial_checker.data.Morpheme;
import jp.ac.chitose.colloquial_checker.service.IAnalyzeReportService;
import jp.ac.chitose.colloquial_checker.service.IMeCabService;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

@AuthorizeInstantiation({MyRole.STUDENT, MyRole.TEACHER})
public class ColloquialCheckPageOld extends WebPage {
    private static final long serialVersionUID = 7298246485584063657L;

    private IModel<String> txtModel;
    //    private IModel<List<Morpheme>> morphemeListModel;
    private IModel<List<Colloquy>> colloquyListModel;
    private IModel<List<ExampleSentence>> ExampleSentenceModel;
    //    private ListView<Morpheme> morphemeListView;
    private ListView<Colloquy> colloquyListView;
    private Label colloquialNameLabel;
    private Label sentenceModel;
    private Label fixSentenceModel;


    @SpringBean
    private IAnalyzeReportService colloquialCheckService;
    @SpringBean
    private IMeCabService mecabService;

    public ColloquialCheckPageOld() {

        this("", null);

    }


    public ColloquialCheckPageOld(String txt, List<List<Morpheme>> morphemeList) {


        txtModel = Model.of(txt);
//        morphemeListModel = Model.ofList(null);
        ExampleSentenceModel = Model.ofList(null);

        List<Colloquy> colloquyList = new ArrayList<>();
        if (morphemeList != null) {
            for (List<Morpheme> list : morphemeList) {
                for (Morpheme morpheme : list) {
                    if (morpheme.getColloquy() != null) {
                        colloquyList.add(morpheme.getColloquy());
                    }
                }
            }
        }
        colloquyListModel = Model.ofList(colloquyList);


//        Label result = new Label("result", resultModel);

        //全文チェック
        Button allCheckButton = new Button("allCheck") {


            private static final long serialVersionUID = -4137769831654907405L;

            @Override
            public void onSubmit() {
                String report = txtModel.getObject();

                List<List<Morpheme>> sentenceList = colloquialCheckService.analyzeReport(report);
                List<Colloquy> colloquyList = new ArrayList<>();

                for (List<Morpheme> list : sentenceList) {
                    for (Morpheme morpheme : list) {
                        if (morpheme.getColloquy() != null) {
                            colloquyList.add(morpheme.getColloquy());
                            System.out.println(morpheme.getColloquy().getId() + "\t" +
                                    morpheme.getColloquy().getForm() + "\t" +
                                    morpheme.getColloquy().getPartLevel1() + "\t" +
                                    morpheme.getColloquy().getCategory()
                            );

                            for (ExampleSentence exampleSentence : morpheme.getColloquy().getExampleSentenceList()) {
                                System.out.println("\t例文　：" + exampleSentence.getSentence() +
                                        "\n\t修正文：" + exampleSentence.getFixSentence());
                            }
                        }

                    }
                }

                setResponsePage(new ColloquialCheckPageOld(report, sentenceList));

            }
        };

        Button oneLineCheckButton = new Button("oneLineCheck") {
            private static final long serialVersionUID = 6170008299179923514L;

            @Override
            public void onSubmit() {
                String txt = txtModel.getObject() + "  //一文チェック";

                setResponsePage(new ColloquialCheckPageOld(txt, null));
            }
        };

        var form = new StatelessForm<Void>("form") {
            private static final long serialVersionUID = 2489824036645989067L;

        };

        if (colloquyListModel != null) {
            colloquyListView = new ListView<>("colloquialResultList", colloquyListModel) {


                @Override
                public void populateItem(ListItem<Colloquy> item) {


                    colloquialNameLabel = new Label("colloquialName", item.getModelObject().getForm());

                    ExampleSentenceModel = Model.ofList(item.getModelObject().getExampleSentenceList());

                    ListView<ExampleSentence> exampleSentenceListView = new ListView<>("colloquy", ExampleSentenceModel) {

                        @Override
                        public void populateItem(ListItem<ExampleSentence> item) {
                            sentenceModel = new Label("sentence", item.getModelObject().getSentence());
                            fixSentenceModel = new Label("fixSentence", item.getModelObject().getFixSentence());

                            item.add(sentenceModel);
                            item.add(fixSentenceModel);
                        }


                    };

                    item.add(colloquialNameLabel);
                    item.add(exampleSentenceListView);

                }
            };

            add(colloquyListView);
        } else {
            colloquyListView = new ListView<>("colloquialResultList", colloquyListModel) {

                @Override
                public void populateItem(ListItem<Colloquy> item) {

                    colloquialNameLabel = new Label("colloquialName", "");

                    ExampleSentenceModel = Model.ofList(new ArrayList<>());

                    ListView<ExampleSentence> exampleSentenceListView = new ListView<>("colloquy", ExampleSentenceModel) {

                        @Override
                        public void populateItem(ListItem<ExampleSentence> item) {
                            sentenceModel = new Label("sentence", "");
                            fixSentenceModel = new Label("fixSentence", "");

                            item.add(sentenceModel);
                            item.add(fixSentenceModel);

                        }


                    };

                    item.add(colloquialNameLabel);
                    item.add(exampleSentenceListView);

                }

            };
            add(colloquyListView);
        }

        add(form);
        form.add(new TextArea<>("txt", txtModel));
        form.add(allCheckButton);
        form.add(oneLineCheckButton);

    }
}

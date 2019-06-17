package jp.ac.chitose.colloquial_checker.page;

import jp.ac.chitose.colloquial_checker.MyRole;
import jp.ac.chitose.colloquial_checker.data.ExampleSentence;
import jp.ac.chitose.colloquial_checker.data.Morpheme;
import jp.ac.chitose.colloquial_checker.repository.IColloquialRepository;
import jp.ac.chitose.colloquial_checker.service.IAnalyzeReportService;
import jp.ac.chitose.colloquial_checker.service.IMeCabService;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

@AuthorizeInstantiation({MyRole.TEACHER, MyRole.STUDENT})
public class ColloquialCheckPage extends WebPage {

    private static final long serialVersionUID = -1947861033973281842L;


    private IModel<String> reportModel; //レポート入力
    private IModel<List<List<Morpheme>>> sentenceListModel;

    @SpringBean
    private IAnalyzeReportService colloquialCheckService;
    @SpringBean
    private IMeCabService mecabService;
    @SpringBean
    private IColloquialRepository colloquialRepository;

    public ColloquialCheckPage() {
        this(Model.of(""), Model.ofList(null));
    }

    public ColloquialCheckPage(IModel<String> reportModel, IModel<List<List<Morpheme>>> sentenceListModel) {

        this.reportModel = reportModel;
        this.sentenceListModel = sentenceListModel;

        var form = new Form<Void>("form");

        Button colloquialCheckButton = new Button("colloquialCheck") {

            private static final long serialVersionUID = -7631047762823929715L;

            @Override
            public void onSubmit() {
               try {
                    String formedReport = reportModel.getObject();
                    List<List<Morpheme>> formedSentenceList = colloquialCheckService.analyzeReport(formedReport);
                    var model = Model.ofList(formedSentenceList);
                    setResponsePage(new ColloquialCheckPage(reportModel, model));
                } catch (NullPointerException e) {
                    System.out.println(e);
                    setResponsePage(new ColloquialCheckPage());
                }
            }
        };

        var sentenceListView = new ListView<>("sentenceList", this.sentenceListModel) {

            private static final long serialVersionUID = 1132108091680169522L;

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisibilityAllowed(getModelObject() != null);
            }

            @Override
            protected void populateItem(ListItem<List<Morpheme>> listItem) {

                var morphemeListModel = Model.ofList(listItem.getModelObject());

                var morphemeListView = new ListView<>("morphemeList", morphemeListModel) {

                    private static final long serialVersionUID = -4673265660669360259L;

                    @Override
                    protected void populateItem(ListItem<Morpheme> item) {

                        var surfaceFormLabel = new Label("surfaceForm", item.getModelObject().getSurfaceForm());

                        //item の colloquy に値が入っているのなら(話しことばであるのなら)
                        if (item.getModelObject().hasColloquy()) {
                            surfaceFormLabel.add(new AttributeModifier("style", "background-color :#FFFF00;"));
                            surfaceFormLabel.add(new AttributeModifier("class", "morpheme" + item.getModelObject().hashCode()));
                        }
                        item.add(surfaceFormLabel);
                    }
                };
                listItem.add(morphemeListView);
            }
        };

//            Label myScript = new Label("myScript", callScript(sentenceListModel.getObject()));
        var myScriptModel = new IModel<String>() {

            private static final long serialVersionUID = 400918297142397659L;

            @Override
            public String getObject() {
                if (sentenceListModel.getObject() != null) {
                    return callScript(sentenceListModel.getObject());
                }
                return "";
            }
        };

        Label myScript = new Label("myScript", myScriptModel) {

            private static final long serialVersionUID = -4622456861334416673L;

            @Override
            protected void onInitialize() {
                super.onInitialize();
                setEscapeModelStrings(false);
            }
        };
        add(myScript);
        add(form);
        form.add(new TextArea<>("reportArea", this.reportModel));
        form.add(colloquialCheckButton);
        add(sentenceListView);
    }


    protected String callScript(List<List<Morpheme>> sentenceList) {
        String script =
                "$(function() {\n";

        for (List<Morpheme> morphemeList : sentenceList) {
            for (Morpheme morpheme : morphemeList) {
                if (morpheme.hasColloquy()) {
                    script = script +
                            "   $('.morpheme" + morpheme.hashCode() + "').balloon({\n" +
                            "       position : \"bottom right\",\n" +
                            "       html : true,\n" +
                            "       contents :\n" +
                            "           '<h1>" + morpheme.getColloquy().getForm() + "</h1>' +\n" +
                            "           '<div class=\"container\">' +\n" +
                            "               '<table class=\"pure-table pure-table-bordered\">' +\n" +
                            "                   '<thead>' +\n" +
                            "                       '<tr>' +\n" +
                            "                           '<th>例文</th>' +\n" +
                            "                           '<th>修正例</th>' +\n" +
                            "                       '</tr>' +\n" +
                            "                   '</thead>' +\n" +
                            "                   '<tbody>' +\n";
                    for (ExampleSentence exampleSentence : morpheme.getColloquy().getExampleSentenceList()) {
                        script = script +
                                "                       '<tr>' +\n" +
                                "                           '<td>" + exampleSentence.getSentence() + "</td>' +\n" +
                                "                           '<td>" + exampleSentence.getFixSentence() + "</td>' +\n" +
                                "                       '</tr>' +\n";
                    }
                    script = script +
                            "                   '</tbody>' +\n" +
                            "               '</table>' +\n" +
                            "           '</div>'\n" +
                            "   });\n";
                }
            }
        }

        script = script +
                "});";
        return script;

    }
}

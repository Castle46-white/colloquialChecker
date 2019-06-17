package jp.ac.chitose.colloquial_checker.page;

import com.giffing.wicket.spring.boot.context.scan.WicketHomePage;
import jp.ac.chitose.colloquial_checker.MyRole;
import jp.ac.chitose.colloquial_checker.MySession;
import jp.ac.chitose.colloquial_checker.data.Account;
import jp.ac.chitose.colloquial_checker.service.IAuthenticationService;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.annotation.mount.MountPath;

@WicketHomePage
@MountPath("SignPage")
public class SignPage extends WebPage {
    private static final long serialVersionUID = -2164111319028038371L;


    private IModel<String> accountNameModel;
    private IModel<String> passwordModel;

    @SpringBean
    private IAuthenticationService authenticationService;


    public SignPage() {

        accountNameModel = Model.of("");
        passwordModel = Model.of("");


        var form = new StatelessForm<Void>("form") {
            private static final long serialVersionUID = -4226663937473796400L;

            @Override
            protected void onSubmit() {
                super.onSubmit();
                String accountName = accountNameModel.getObject();
                String passphrase = passwordModel.getObject();
                Account account = authenticationService.authenticate(accountName, passphrase);
                MySession.get().signIn(account);
                if (MySession.get().isSignedIn()) {
                    if (MySession.get().getRoles().hasRole(MyRole.TEACHER)) {
                        setResponsePage(new TeacherTopPage());
                    } else if (MySession.get().getRoles().hasRole(MyRole.STUDENT)) {
                        setResponsePage(new StudentTopPage());
                    } else {
                        error("");
                    }
                } else {
                    error("IDもしくはパスワードが間違っています");
                }


            }
        };

        add(form);

        form.add(new FeedbackPanel("feedback"));

        form.add(new TextField<>("accountId", accountNameModel));
        form.add(new PasswordTextField("password", passwordModel));
    }
}

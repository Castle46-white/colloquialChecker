package jp.ac.chitose.colloquial_checker;

import com.giffing.wicket.spring.boot.starter.app.WicketBootSecuredWebApplication;
import jp.ac.chitose.colloquial_checker.page.SignPage;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ColloquialCheckerApplication extends WicketBootSecuredWebApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ColloquialCheckerApplication.class)
                .run(args);
    }

//  @Override
//  public Class<? extends WebPage> getHomePage() {
//    return SignPage.class;
//
//  }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return SignPage.class;
    }

//  @Override
//  public Session newSession(Request request, Response response) {
//    return MySession.get();
//  }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return MySession.class;
    }


}

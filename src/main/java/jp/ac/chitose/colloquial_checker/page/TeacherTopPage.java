package jp.ac.chitose.colloquial_checker.page;

import jp.ac.chitose.colloquial_checker.MyRole;
import jp.ac.chitose.colloquial_checker.MySession;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

@AuthorizeInstantiation({MyRole.TEACHER})
public class TeacherTopPage extends WebPage {
    private static final long serialVersionUID = 8197478038367832699L;

    public TeacherTopPage() {

        Link<Void> toTeacherTopPage = new Link<>("toTeacherTopPage") {
            private static final long serialVersionUID = 573619681460444434L;

            @Override
            public void onClick() {
                setResponsePage(new TeacherTopPage());
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


        add(toTeacherTopPage);
        add(toColloquialCheckOldPage);
        add(toColloquialCheckPage);
        add(toColloquialHistoryPage);
        add(signOut);
    }
}
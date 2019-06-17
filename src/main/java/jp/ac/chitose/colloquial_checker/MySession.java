package jp.ac.chitose.colloquial_checker;

import jp.ac.chitose.colloquial_checker.data.Account;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

public class MySession extends AbstractAuthenticatedWebSession {

    private Roles roles;
    private String accountName;
    private long accountId;

    public MySession(Request request) {
        super(request);
        roles = new Roles();
    }

    public void signIn(Account account) {
        roles.clear();
        this.accountName = account.getAccountName();
        this.accountId = account.getAccountId();
        roles.add(account.getRole());
    }

    public void signOut() {
        invalidate();
    }

    @Override
    public Roles getRoles() {

        return roles;
    }

    @Override
    public boolean isSignedIn() {
        return roles.hasRole(MyRole.TEACHER) || roles.hasRole(MyRole.STUDENT);
    }

    public static MySession get() {
        return (MySession) Session.get();
    }

    public long getAccountId(){
        return accountId;
    }


}

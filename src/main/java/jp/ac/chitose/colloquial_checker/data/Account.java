package jp.ac.chitose.colloquial_checker.data;

import jp.ac.chitose.colloquial_checker.MyRole;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {

    private long accountId;
    private String accountName;
    private String passphrase;
    private String role;

    public Account() {
        this.accountId = -1;
        this.accountName = "";
        this.passphrase = "";
        this.role = MyRole.UNKNOWN;
    }

    public Account(long accountId, String accountName, String passphrase, String role) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.passphrase = passphrase;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                Objects.equals(accountName, account.accountName) &&
                Objects.equals(passphrase, account.passphrase) &&
                Objects.equals(role, account.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountName, passphrase, role);
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

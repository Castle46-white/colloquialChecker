package jp.ac.chitose.colloquial_checker.repository;

import jp.ac.chitose.colloquial_checker.data.Account;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepository implements IAccountRepository {
    private final JdbcTemplate jdbc;

    public AccountRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Account> sign(String accountName, String passphrase) {
        String sql = "select account_id,account_name,passphrase,role "
                + "from account left outer join authentication using(account_id) "
                + "where account_name=? and passphrase=?";

        return jdbc.query(sql, new BeanPropertyRowMapper<>(Account.class), new Object[]{accountName, passphrase})
                .stream()
                .findFirst();
    }
}

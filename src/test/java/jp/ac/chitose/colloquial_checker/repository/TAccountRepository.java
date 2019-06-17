package jp.ac.chitose.colloquial_checker.repository;

import jp.ac.chitose.colloquial_checker.MyRole;
import jp.ac.chitose.colloquial_checker.data.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TAccountRepository {

    @Autowired
    IAccountRepository sut;

    @Test
    public void 正しいアカウント名とパスワードで認証情報を返す() {
        Optional<Account> actual = sut.sign("hoge", "fuga");
        Account account = actual.orElseGet(null);
        assertThat(account.getRole()).isEqualTo(MyRole.TEACHER);
    }

    @Test
    public void 誤ったアカウント名とパスワードで空の認証情報を返す() {
        Optional<Account> actual = sut.sign("aaa", "bbb");
        boolean present = actual.isPresent();
        assertThat(present).isFalse();
    }
}

package jp.ac.chitose.colloquial_checker.service;


import jp.ac.chitose.colloquial_checker.MyRole;
import jp.ac.chitose.colloquial_checker.data.Account;
import jp.ac.chitose.colloquial_checker.repository.IAccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TAuthenticationService {

    @Autowired
    IAuthenticationService sut;

    @MockBean
    IAccountRepository accountRepository;

    @Before
    public void 初期化() {
        Mockito.when(accountRepository.sign(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.of(new Account()));
        Mockito.when(accountRepository.sign("hoge", "fuga"))
                .thenReturn(Optional.of(new Account(1, "hoge", "fuga", MyRole.TEACHER)));
        Mockito.when(accountRepository.sign("poge", "puga"))
                .thenReturn(Optional.of(new Account(1, "poge", "puga", MyRole.STUDENT)));

    }


    @Test
    public void 教師の認証が成功する() {
        Account actual = sut.authenticate("hoge", "fuga");
        assertThat(actual.getRole()).isEqualTo(MyRole.TEACHER);
    }

    @Test
    public void 生徒の認証が成功する() {
        Account actual = sut.authenticate("poge", "puga");
        assertThat(actual.getRole()).isEqualTo(MyRole.STUDENT);
    }

    @Test
    public void アカウント名が間違っていれば認証が失敗する() {
        Account actual = sut.authenticate("", "fuga");
        assertThat(actual.getRole()).isEqualTo(MyRole.UNKNOWN);
    }

    @Test
    public void パスワードが間違っていれば認証が失敗する() {
        Account actual = sut.authenticate("hoge", "");
        assertThat(actual.getRole()).isEqualTo(MyRole.UNKNOWN);
    }
//    
//    @Test
//    public void 正しいアカウント名と
}

package jp.ac.chitose.colloquial_checker.service;

import jp.ac.chitose.colloquial_checker.data.Account;
import jp.ac.chitose.colloquial_checker.repository.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    private IAccountRepository accountRepository;

    public AuthenticationService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account authenticate(String accountName, String passphrase) {

        Optional<Account> resp = accountRepository.sign(accountName, passphrase);
        return resp.orElseGet(
                () -> new Account());
    }

}

package jp.ac.chitose.colloquial_checker.repository;


import jp.ac.chitose.colloquial_checker.data.Account;

import java.util.Optional;

public interface IAccountRepository {

    /**
     * アカウント名とパスワードを使ってユーザ認証をする
     *
     * @param accountName アカウント名
     * @param passphrase  パスワード
     * @return 認証結果をOptionalのAccountで返す
     */
    public Optional<Account> sign(String accountName, String passphrase);


}

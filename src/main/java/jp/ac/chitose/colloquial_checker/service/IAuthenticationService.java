package jp.ac.chitose.colloquial_checker.service;

import jp.ac.chitose.colloquial_checker.data.Account;

public interface IAuthenticationService {
    /**
     * アカウント名とパスワードを使ってユーザ認証をする
     *
     * @param accountName アカウント名
     * @param password    パスワード
     * @return 認証成功すれば Account を返す それ以外はRoleが MyRole.UNKNOWN のAccountを返す
     */
    Account authenticate(String accountName, String password);


}

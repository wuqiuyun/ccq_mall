package clerk.sz28yun.com.bean;

import java.io.Serializable;

/**
 * 账号
 * Created by SUE on 2016/7/8 0008.
 */
public class AccountBean implements Serializable{

    private String account;
    private String password;

    public AccountBean(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package mangoabliu.finalproject;

/**
 * Created by herenjie on 2016/11/14.
 */

public class UserAccount {

    private String userName;
    private String password;

    public UserAccount(String newUserName, String newPassword){
        userName = newUserName;
        password = newPassword;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }
}

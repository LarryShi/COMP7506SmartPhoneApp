package mangoabliu.finalproject;

/**
 * Created by herenjie on 2016/11/14.
 */

public class UserAccount {

    private String userName;
    private double walkDistance;


    public UserAccount(String newUserName, double walkDistance){
        userName = newUserName;
        walkDistance = walkDistance;
    }

    public String getUserName(){
        return userName;
    }

    public double getWalkDistance(){
        return walkDistance;
    }



}

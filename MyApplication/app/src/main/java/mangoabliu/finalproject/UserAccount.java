package mangoabliu.finalproject;

/**
 * Created by herenjie on 2016/11/14.
 */

public class UserAccount {

    private String userName;
    private int walkDistance;
    private int userId;


    public UserAccount(int id, String newUserName, int distance){
        userId = id;
        userName = newUserName;
        walkDistance = distance;
    }

    public String getUserName(){
        return userName;
    }

    public int getWalkDistance(){
        return walkDistance;
    }

    public void setWalkDistance(int distance){
        walkDistance = distance;
    }

    public int getUserId(){
        return userId;
    }


}

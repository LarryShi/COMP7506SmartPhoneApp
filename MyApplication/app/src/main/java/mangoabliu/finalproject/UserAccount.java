package mangoabliu.finalproject;

/**
 * Created by herenjie on 2016/11/14.
 */

public class UserAccount {

    private String userName;
    private int walkDistance;
    private int userId;
    private int currentLocId;
    private int targetLocId;
    private int[] currentLocCoordinate;


    public UserAccount(int id, String newUserName, int distance){
        userId = id;
        userName = newUserName;
        walkDistance = distance;
        currentLocId = 1;
        currentLocCoordinate = new int[]{0, 0};
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

    public int getCurrentLocId(){
        return currentLocId;
    }

    public void setCurrentLocId(int loc){
        currentLocId = loc;
    }

    public int getTargetLocId(){
        return currentLocId;
    }

    public void setTargetLocId(int loc){
        targetLocId = loc;
    }

    public void setCurrentLocCoordinate(int[] coordinate){
        currentLocCoordinate = coordinate;
    }

    public int[] getCurrentLocCoordinate(){
        return currentLocCoordinate;
    }


}

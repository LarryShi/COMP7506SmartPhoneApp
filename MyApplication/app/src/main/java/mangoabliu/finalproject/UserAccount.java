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
    private double[] currentLocCoordinate;


    public UserAccount(int id, String newUserName, int distance){
        userId = id;
        userName = newUserName;
        walkDistance = distance;
        currentLocId = 1;
        targetLocId = 2;
        currentLocCoordinate = new double[]{350, 440};
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
        return targetLocId;
    }

    public void setTargetLocId(int loc){
        targetLocId = loc;
    }

    public void setCurrentLocCoordinate(double[] coordinate){
        currentLocCoordinate = coordinate;
    }

    public double[] getCurrentLocCoordinate(){
        return currentLocCoordinate;
    }


}

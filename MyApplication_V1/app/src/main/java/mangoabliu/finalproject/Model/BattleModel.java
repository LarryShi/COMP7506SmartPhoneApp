package mangoabliu.finalproject.Model;


import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mangoabliu.finalproject.BattleActivity;

/**
 * Created by SHI Zhongqi on 2016-11-29.
 */

public class BattleModel {
    private static BattleModel instance;
    private final String String_base_url="http://i.cs.hku.hk/~zqshi/ci/index.php/";
    private BattleActivity battleActivity;
    private ArrayList<Card> UserCards;

    private HashMap<Card,Integer> mapIsUserCardPicked =new HashMap<>();
    private HashMap<Integer,Card> mapCardIDtoCard =new HashMap<>();
    private HashMap<Integer,Card> mapBtnNumberChooseCard = new HashMap<>();

    private UserAccount myUser;

    private HashMap<Integer,Card> mapOtherCardIDtoCard =new HashMap<>();
    private HashMap<Integer,Card> mapOtherBtnNumberChooseToCard = new HashMap<>();
    private Handler handler = new Handler( );
    private Runnable runnable;
    private Runnable timeRunnable;
    private int stateCase=0;
    private int myplayerID=0;
    private int roomId;
    private int myPlayCardIndex=0;
    private int otherPlayCardIndex=0;
    private int otherUserID=0;
    private String otherUserName;
    /**
     *  0  just start
     *  1  循环str_isRoomReadyM_function
     *  3  循环，str_isFightReadyM_function
     *  4  循环，str_myTurnM_function
     *  5  循环，str_getTime_function ->单独拿出来写
     */
    protected final static String str_getTime_function="getTime";
    protected final static String str_myTurnM_function = "myTurnM";
    protected final static String str_playCardM_function = "playCardM";
    protected final static String str_isFightReadyM_function = "isFightReadyM";
    protected final static String str_setCardsM_function = "setCardsM";
    protected final static String str_isRoomReadyM_function = "isRoomReadyM";
    protected final static String str_applyForFightM_function = "applyForFightM";


    private BattleModel() {
        // public Card(int _CardID,String _CardName,int _CardHP,int _CardAttack,int _CardArmor,int _CardRarity);
        runnable = new Runnable( ) {
            public void run ( ) {
                switch(stateCase){
                    case 1:
                        getIsRoomReady();
                        break;
                    case 3:
                        getIsFightReady();
                        break;
                    case 4:
                        getIsMyTurn();
                        break;
                }
                handler.postDelayed(this,3000);
                //postDelayed(this,2000)方法安排一个Runnable对象到主线程队列中
            }
        };


        timeRunnable = new Runnable( ) {
            public void run ( ) {
                serverPHPPostConnection(getTimeUrl(),"",str_getTime_function);
                handler.postDelayed(this,3000);
                //postDelayed(this,2000)方法安排一个Runnable对象到主线程队列中
            }
        };
    }

    public static BattleModel getInstance(){
        if(null == instance){
            instance = new BattleModel();
        }
        instance.roomId=0;
        instance.myplayerID=0;
        instance.stateCase=0;
        instance.myPlayCardIndex=0;
        instance.otherPlayCardIndex=0;
        instance.otherUserID=0;
        instance.otherUserName="";
        return instance;
    }

    public void setBattleActivity(BattleActivity battleActivity){
        this.battleActivity=battleActivity;
    }

    public void setUserCards(ArrayList<Card> userCards){
        this.UserCards=userCards;
        for(int i=0;i<UserCards.size();i++){
            mapIsUserCardPicked.put(UserCards.get(i),1);//1 就是可以选的，0是不可以选的
            mapCardIDtoCard.put(UserCards.get(i).getCardID(),UserCards.get(i));
        }
    }

    public ArrayList<Card> getUserCards(){
        return this.UserCards;
    }
    //1 就是可以选的，0是不可以选的
    public int checkCardPick(int CardID){
        return mapIsUserCardPicked.get(mapCardIDtoCard.get(CardID));
    }

    public Card getCard(int CardID){
        return mapCardIDtoCard.get(CardID);
    }

    public Card getOtherCard(int CardID){
        return mapOtherCardIDtoCard.get(CardID);
    }

    public void attackOtherTarget(int otherSideIndex){
        otherPlayCardIndex=otherSideIndex;
    }

    public void chooseMyCard(int myIndex){
        myPlayCardIndex=myIndex;
    }

    public void pickCard(int CardID,int index){
        mapIsUserCardPicked.put(mapCardIDtoCard.get(CardID),0);
        if(mapBtnNumberChooseCard.containsKey(index))
            mapIsUserCardPicked.put(mapBtnNumberChooseCard.get(index),1);
        mapBtnNumberChooseCard.put(index, mapCardIDtoCard.get(CardID));
        battleActivity.updateMyCard(CardID,index);
    }

    public int chosedCardNo(){
        return mapBtnNumberChooseCard.size();
    }

    public void setUserAccount(UserAccount user){
        myUser = user;
    }

    public UserAccount getUserAccount(){
        return myUser;
    }



    //applyForFightM
    public void applyForFight(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserID",myUser.getUserId());
            stateCase=1;
            serverPHPPostConnection(getApplyForFightUrl(),jsonObject.toString(),str_applyForFightM_function);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void applyForFightSuccessful(String result){
        try {
            JSONObject jsonObj = new JSONObject(result);
            if((Integer)jsonObj.get("code")==0) {
                this.roomId=(Integer)jsonObj.get("RoomID");
            }
            else
                battleActivity.displayMessage((String)jsonObj.get("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //isRoomReadyM
    public void getIsRoomReady(){
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("RoomID",roomId);

            serverPHPPostConnection(getIsRoomReadyUrl(),jsonObject.toString(),str_isRoomReadyM_function);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //getRoomSuccessful
    public void getIsRoomReadySuccessful(String result){
        try {
            JSONObject jsonObj = new JSONObject(result);

            if((Integer)jsonObj.get("code")==0) {
                JSONArray jsonArr=(JSONArray)jsonObj.get("UserName");
                JSONObject jsonUserObject1=jsonArr.getJSONObject(0);
                JSONObject jsonUserObject2=jsonArr.getJSONObject(0);
                int Player1ID = (int)jsonUserObject1.get("UserID");
                if(Player1ID==myUser.getUserId())
                    myplayerID=1;
                else
                    myplayerID=2;

                switch(myplayerID){
                    case 1:
                        otherUserID=(int)jsonUserObject2.get("UserID");
                        otherUserName=(String) jsonUserObject2.get("UserName");
                        break;
                    case 2:
                        otherUserID=(int)jsonUserObject1.get("UserID");
                        otherUserName=(String) jsonUserObject1.get("UserName");
                        break;
                }

            }
            else
                battleActivity.displayMessage((String)jsonObj.get("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //setCardsM
    public void playerCardPickConfirm(){
        /*
        $paramemter['RoomID'] = $json['RoomID'];
        $paramemter['UserID'] = $json['UserID'];
        $paramemter['CardID1'] = $json['CardID1'];
        $paramemter['CardID2'] = $json['CardID2'];
        $paramemter['CardID3'] = $json['CardID3'];
         */
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("RoomID",roomId);
            jsonObject.put("UserID",myUser.getUserId());
            jsonObject.put("CardID1", mapBtnNumberChooseCard.get(1).getCardID());
            jsonObject.put("CardID2", mapBtnNumberChooseCard.get(2).getCardID());
            jsonObject.put("CardID3", mapBtnNumberChooseCard.get(3).getCardID());

            stateCase=3;
            serverPHPPostConnection(getApplyForFightUrl(),jsonObject.toString(),str_applyForFightM_function);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setCardsSuccessful(String result){
        try {
            JSONObject jsonObj = new JSONObject(result);
            if((Integer)jsonObj.get("code")==0) {
                this.roomId=(Integer)jsonObj.get("RoomID");
                stateCase=3;
                /**
                 *  0  just start
                 *  1  循环str_isRoomReadyM_function
                 *  3  循环，str_isFightReadyM_function
                 *  4  循环，str_myTurnM_function
                 *  5  循环，str_getTime_function ->单独拿出来写
                 */
            }
            else {
                battleActivity.displayMessage((String) jsonObj.get("message"));
                battleActivity.setState(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //isFightReadyM
    public void getIsFightReady(){
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("RoomID",roomId);

            serverPHPPostConnection(getIsFightReadyUrl(),jsonObject.toString(),str_isFightReadyM_function);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void isFightReadySuccessful(String result){
        try {
            JSONObject jsonObj = new JSONObject(result);
            if((Integer)jsonObj.get("code")==0) {
                JSONArray jsonArr=(JSONArray)jsonObj.get("CardInfo");
                int temp_id=0;
                if(myplayerID==1)
                    temp_id+=3;
                else
                    stateCase=4;
                JSONObject jsonUserObject1=jsonArr.getJSONObject(0+temp_id);
                JSONObject jsonUserObject2=jsonArr.getJSONObject(1+temp_id);
                JSONObject jsonUserObject3=jsonArr.getJSONObject(2+temp_id);

                int CardID = jsonUserObject1.getInt("CardID");
                String CardName = jsonUserObject1.getString("CardName");
                int CardHP = jsonUserObject1.getInt("CardHP");
                int CardAttack = jsonUserObject1.getInt("CardAttack");
                int CardArmor = jsonUserObject1.getInt("CardArmor");
                int CardRarity = jsonUserObject1.getInt("CardRarity");
                mapOtherBtnNumberChooseToCard.put(1,new Card(CardID,CardName,CardHP,CardAttack,CardArmor,CardRarity));
                CardID = jsonUserObject2.getInt("CardID");
                CardName = jsonUserObject2.getString("CardName");
                CardHP = jsonUserObject2.getInt("CardHP");
                CardAttack = jsonUserObject2.getInt("CardAttack");
                CardArmor = jsonUserObject2.getInt("CardArmor");
                CardRarity = jsonUserObject2.getInt("CardRarity");
                mapOtherBtnNumberChooseToCard.put(2,new Card(CardID,CardName,CardHP,CardAttack,CardArmor,CardRarity));
                CardID = jsonUserObject3.getInt("CardID");
                CardName = jsonUserObject3.getString("CardName");
                CardHP = jsonUserObject3.getInt("CardHP");
                CardAttack = jsonUserObject3.getInt("CardAttack");
                CardArmor = jsonUserObject3.getInt("CardArmor");
                CardRarity = jsonUserObject3.getInt("CardRarity");
                mapOtherBtnNumberChooseToCard.put(2,new Card(CardID,CardName,CardHP,CardAttack,CardArmor,CardRarity));
                mapOtherCardIDtoCard.put(mapOtherBtnNumberChooseToCard.get(1).getCardID(),mapOtherBtnNumberChooseToCard.get(1));
                mapOtherCardIDtoCard.put(mapOtherBtnNumberChooseToCard.get(2).getCardID(),mapOtherBtnNumberChooseToCard.get(2));
                mapOtherCardIDtoCard.put(mapOtherBtnNumberChooseToCard.get(3).getCardID(),mapOtherBtnNumberChooseToCard.get(3));

                /**
                 *  0  just start
                 *  1  循环str_isRoomReadyM_function
                 *  3  循环，str_isFightReadyM_function
                 *  4  循环，str_myTurnM_function
                 *  5  循环，str_getTime_function ->单独拿出来写
                 */
            }
            else {
                battleActivity.displayMessage((String) jsonObj.get("message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //playCardM
    public void playACard(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("RoomID",roomId);
            jsonObject.put("UserID",myUser.getUserId());
            jsonObject.put("Player"+myplayerID+"CardID", mapBtnNumberChooseCard.get(1));
            jsonObject.put("Player2CardID", mapBtnNumberChooseCard.get(2));
            jsonObject.put("Player"+myplayerID+" CardNum", mapBtnNumberChooseCard.get(3));
            jsonObject.put("Player2CardNum", mapBtnNumberChooseCard.get(3));
            jsonObject.put("Player", myplayerID);

            stateCase=3;
            serverPHPPostConnection(getApplyForFightUrl(),jsonObject.toString(),str_applyForFightM_function);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //isMyTurn
    public void getIsMyTurn(){
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("RoomID",roomId);

            serverPHPPostConnection(getMyTurnUrl(),jsonObject.toString(),str_myTurnM_function);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //HTTP Request Related Info
    private void serverPHPPostConnection(String str_URL,String str_JSON,String str_Function){
        try{
            GameHTTPRequest mTask = new GameHTTPRequest();
            mTask.execute(str_URL,str_JSON,str_Function);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getTimeUrl(){
        return String_base_url+"/Game/getTime";
    }

    private String getMyTurnUrl(){
        return String_base_url+"/Game/myTurnM";
    }

    private String getPlayCardUrl(){
        return String_base_url+"/Game/playCardM";
    }

    private String getIsFightReadyUrl(){
        return String_base_url+"/Game/isFightReadyM";
    }

    private String getSetCardsUrl(){
        return String_base_url+"/Game/setCardsM";
    }

    private String getIsRoomReadyUrl(){
        return String_base_url+"/Game/isRoomReadyM";
    }

    private String getApplyForFightUrl(){
        return String_base_url+"/Game/applyForFightM";
    }
}

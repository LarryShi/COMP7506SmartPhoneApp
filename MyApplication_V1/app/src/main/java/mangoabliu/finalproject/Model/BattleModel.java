package mangoabliu.finalproject.Model;


import android.os.Handler;

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
    private ArrayList<Card> UserCards= new ArrayList<>();
    private HashMap<Card,Integer> CardPickMap=new HashMap<>();
    private HashMap<Integer,Card> CardIDMap=new HashMap<>();
    private HashMap<Integer,Card> playChooseCard = new HashMap<>();
    private UserAccount myUser;
    private UserAccount otherUser;
    private Handler handler = new Handler( );
    private Runnable runnable;
    private int roomId;


    protected final static String str_getTime_function="getTime";
    protected final static String str_myTurnM_function = "myTurnM";
    protected final static String str_playCardM_function = "playCardM";
    protected final static String str_isFightReadyM_function = "isFightReadyM";
    protected final static String str_setCardsM_function = "setCardsM";
    protected final static String str_isRoomReadyM_function = "isRoomReadyM";
    protected final static String str_applyForFightM_function = "applyForFightM";


    private BattleModel() {
        // public Card(int _CardID,String _CardName,int _CardHP,int _CardAttack,int _CardArmor,int _CardRarity);
    }

    public static BattleModel getInstance(){
        if(null == instance){
            instance = new BattleModel();
        }
        return instance;
    }

    public void setBattleActivity(BattleActivity battleActivity){
        this.battleActivity=battleActivity;
    }

    public void setUserCards(ArrayList<Card> userCards){
        this.UserCards=userCards;
        for(int i=0;i<UserCards.size();i++){
            CardPickMap.put(UserCards.get(i),1);//1 就是可以选的，0是不可以选的
            CardIDMap.put(UserCards.get(i).getCardID(),UserCards.get(i));
        }
    }

    public ArrayList<Card> getUserCards(){
        return this.UserCards;
    }
    //1 就是可以选的，0是不可以选的
    public int checkCardPick(int CardID){
        return CardPickMap.get(CardIDMap.get(CardID));
    }


    public void attackOtherTarget(int otherSideIndex){

    }

    public void chooseMyCard(int myIndex){

    }

    public void pickCard(int CardID,int index){
        CardPickMap.put(CardIDMap.get(CardID),0);
        if(playChooseCard.containsKey(index))
            CardPickMap.put(playChooseCard.get(index),1);
        playChooseCard.put(index,CardIDMap.get(CardID));
        battleActivity.updateMyCard(CardID,index);
    }

    public int chosedCardNo(){
        return playChooseCard.size();
    }

    public void setUserAccount(UserAccount user){
        myUser = user;
    }

    public UserAccount getUserAccount(){
        return myUser;
    }

    public void setRoomId(int id){
        this.roomId=id;
    }

    public void playerCardPickConfirm(){


    }

    public void applyForFight(){
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("UserID",myUser.getUserId());

            serverPHPPostConnection(getApplyForFightUrl(),jsonObject.toString(),str_applyForFightM_function);
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

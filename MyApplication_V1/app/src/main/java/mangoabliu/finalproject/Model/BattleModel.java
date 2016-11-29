package mangoabliu.finalproject.Model;


import java.util.ArrayList;
import java.util.HashMap;

import mangoabliu.finalproject.BattleActivity;

/**
 * Created by SHI Zhongqi on 2016-11-29.
 */

public class BattleModel {
    private static BattleModel instance;
    private final String String_base_url="http://i.cs.hku.hk/~zqshi/ci/index.php/";
    BattleActivity battleActivity;
    ArrayList<Card> UserCards= new ArrayList<>();
    HashMap<Card,Integer> CardPickMap=new HashMap<>();
    HashMap<Integer,Card> CardIDMap=new HashMap<>();
    HashMap<Integer,Card> playChooseCard = new HashMap<>();
    private UserAccount myUser;


    private BattleModel() {
        // public Card(int _CardID,String _CardName,int _CardHP,int _CardAttack,int _CardArmor,int _CardRarity){
        UserCards.add(new Card(1,"Spider Man",300,100,100,3));
        UserCards.add(new Card(2,"Captain America",300,100,100,3));
        UserCards.add(new Card(3,"Dr Strange",300,100,100,3));
        UserCards.add(new Card(4,"Black Widow",200,75,75,2));
        UserCards.add(new Card(5,"Thor",200,75,75,2));
        UserCards.add(new Card(6,"Wolverine",200,75,75,2));
        for(int i=0;i<UserCards.size();i++){
            CardPickMap.put(UserCards.get(i),1);//1 就是可以选的，0是不可以选的
            CardIDMap.put(UserCards.get(i).getCardID(),UserCards.get(i));
        }
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

    public void setUserAccount(UserAccount user){
        myUser = user;
    }

    public UserAccount getUserAccount(){
        return myUser;
    }

    public void playerCardPickConfirm(){

    }



}

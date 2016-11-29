package mangoabliu.finalproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import mangoabliu.finalproject.Layout.CardLayout;
import mangoabliu.finalproject.Layout.FontTextView;
import mangoabliu.finalproject.Model.BattleModel;
import mangoabliu.finalproject.Model.GameModel;

/**
 * Created by Lyris on 29/11/16.
 * Modify by SHI Zhongqi on 29/11/16
 */

public class BattleActivity extends AppCompatActivity {

    private FontTextView tv_searching;


    BattleModel battleModel;
    GameModel gameModel;
    Button btn_test,btn_card_choose_confirm,btn_exit;
    RelativeLayout rl_battle_waiting_up,rl_battle_waiting_down,rl_battle_waiting_other_side;
    ImageView imageView_battle_waiting,imageView_battle_border;
    CardLayout myCard1,myCard2,myCard3,otherCard1,otherCard2,otherCard3;
    int int_state=0;//0在等待匹配，1在选卡，2在等待对方选卡，3在对战；
    BattlePickCardDialog cardPicker1;
    BattlePickCardDialog cardPicker2;
    BattlePickCardDialog cardPicker3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameModel= GameModel.getInstance();
        gameModel.addActivity(this);
        battleModel= BattleModel.getInstance();
        battleModel.setBattleActivity(this);
        battleModel.setUserAccount(gameModel.getUserAccount());
        Log.i("Battle Activity", gameModel.getUserAccount().getUserName());
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initBattleView();
        //blink
        Animation animSearch= new AlphaAnimation(0.0f, 1.0f);
        animSearch.setDuration(1500); //You can manage the blinking time with this parameter
        animSearch.setStartOffset(20);
        animSearch.setRepeatMode(Animation.REVERSE);
        animSearch.setRepeatCount(Animation.INFINITE);
        tv_searching.startAnimation(animSearch);
        cardPicker1 = new BattlePickCardDialog(BattleActivity.this,R.style.DialogTranslucent,1);
        cardPicker2 = new BattlePickCardDialog(BattleActivity.this,R.style.DialogTranslucent,2);
        cardPicker3 = new BattlePickCardDialog(BattleActivity.this,R.style.DialogTranslucent,3);
    }


    private void initBattleView(){

        setContentView(R.layout.activity_battle);
        btn_test = (Button) findViewById(R.id.btn_battle_test);
        btn_exit = (Button) findViewById(R.id.btn_battle_exit);
        btn_card_choose_confirm=(Button)findViewById(R.id.btn_battle_choose_card_finished);
        rl_battle_waiting_down = (RelativeLayout) findViewById(R.id.relativeLayout_battle_waiting_bg_down);
        rl_battle_waiting_up = (RelativeLayout) findViewById(R.id.relativeLayout_battle_waiting_bg_up);
        rl_battle_waiting_other_side=(RelativeLayout)findViewById(R.id.relativeLayout_battle_waiting_otherside);
        imageView_battle_waiting = (ImageView) findViewById(R.id.imageView_battle_loading);
        imageView_battle_border=(ImageView)findViewById(R.id.imageView_battle_border);
        tv_searching = (FontTextView)findViewById(R.id.battle_searching_text);

        myCard1=(CardLayout)findViewById(R.id.card_battle_mycard_1);
        myCard2=(CardLayout)findViewById(R.id.card_battle_mycard_2);
        myCard3=(CardLayout)findViewById(R.id.card_battle_mycard_3);

        otherCard1=(CardLayout)findViewById(R.id.card_battle_otherside_card_1);
        otherCard2=(CardLayout)findViewById(R.id.card_battle_otherside_card_2);
        otherCard3=(CardLayout)findViewById(R.id.card_battle_otherside_card_3);

        myCard1.setOnClickListener(new clickListener_myCard(1));
        myCard2.setOnClickListener(new clickListener_myCard(2));
        myCard3.setOnClickListener(new clickListener_myCard(3));

        otherCard1.setOnClickListener(new clickListener_otherCard(1));
        otherCard2.setOnClickListener(new clickListener_otherCard(2));
        otherCard3.setOnClickListener(new clickListener_otherCard(3));

        float center_height=imageView_battle_waiting.getDrawable().getIntrinsicHeight();
        float center_width=imageView_battle_waiting.getDrawable().getIntrinsicHeight();
        RotateAnimation animationWaitingIcon = new RotateAnimation(0, 360.0f,center_width/2,center_height/2);
        animationWaitingIcon.setDuration(2000);
        animationWaitingIcon.setStartOffset(120);
        animationWaitingIcon.setRepeatMode(Animation.RESTART);
        animationWaitingIcon.setRepeatCount(Animation.INFINITE);
        LinearInterpolator lin = new LinearInterpolator();
        animationWaitingIcon.setInterpolator(lin);

        imageView_battle_waiting.startAnimation(animationWaitingIcon);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waitingRoomFinished();
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_card_choose_confirm.setOnClickListener(new clickListener_Confirm());

        Animation animSearch= new AlphaAnimation(0.0f, 1.0f);
        animSearch.setDuration(1500); //You can manage the blinking time with this parameter
        animSearch.setStartOffset(20);
        animSearch.setRepeatMode(Animation.REVERSE);
        animSearch.setRepeatCount(Animation.INFINITE);
        tv_searching.startAnimation(animSearch);
    }

    public void playerWin(){
    //用户赢了
    }

    public void OtherWin(){
    //对面赢了
    }


    public void playerAttackOther(int hurt){
    //用户攻击对面后对面减少的HP
    }


    public void otherSideAttackPlayer(int hurt){
        //对面攻击用户减少的HP
    }

    public void setTurn(String userName){
        //设置中间的Turn信息
    }

    public void setTime(int times){
        //设置旁边的时间
    }

    public void setOtherSideUserName(String userName){
        //设置对面用户名字
    }

    public void waitingRoomFinished(){
        Animation animationWiatingDown = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 1);
        animationWiatingDown.setDuration(1000);
        animationWiatingDown.setFillAfter(true);
        Animation animationWaitingUp = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, -1);
        animationWaitingUp.setDuration(1000);
        animationWaitingUp.setFillAfter(true);

        rl_battle_waiting_down.startAnimation(animationWiatingDown);
        rl_battle_waiting_up.startAnimation(animationWaitingUp);
        imageView_battle_waiting.clearAnimation();
        tv_searching.clearAnimation();
        imageView_battle_waiting.setVisibility(View.INVISIBLE);
//        imageView_battle_border.setVisibility(View.INVISIBLE);
        rl_battle_waiting_down.setVisibility(View.INVISIBLE);
        rl_battle_waiting_up.setVisibility(View.INVISIBLE);
        tv_searching.setVisibility(View.INVISIBLE);
        int_state=1;
    }

    //请不要改这个...
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    //请不要改这个...
    public void gameFinished(){
        finish();
    }
    //请不要改这个...
    public void updateMyCard(int CardID,int index){

        if(index==1) {
            myCard1.setCardBack(CardID);
            myCard1.setCardHP(battleModel.getUserCards().get(CardID).getCardHP());
            myCard1.setCardArmor(battleModel.getUserCards().get(CardID).getCardArmor());
            myCard1.setCardAttack(battleModel.getUserCards().get(CardID).getCardAttack());
        }

        if(index==2){
            myCard2.setCardBack(CardID);
            myCard2.setCardHP(battleModel.getUserCards().get(CardID).getCardHP());
            myCard2.setCardArmor(battleModel.getUserCards().get(CardID).getCardArmor());
            myCard2.setCardAttack(battleModel.getUserCards().get(CardID).getCardAttack());
        }

        if(index==3){
            myCard3.setCardBack(CardID);
            myCard3.setCardHP(battleModel.getUserCards().get(CardID).getCardHP());
            myCard3.setCardArmor(battleModel.getUserCards().get(CardID).getCardArmor());
            myCard3.setCardAttack(battleModel.getUserCards().get(CardID).getCardAttack());
        }
    }

    //请不要修改范围外的代码
    public void updateOtherSideCard(int CardID,int index){

        if(index==1) {
            otherCard1.setCardBack(CardID);
            otherCard1.setCardHP(battleModel.getUserCards().get(CardID).getCardHP());
            otherCard1.setCardArmor(battleModel.getUserCards().get(CardID).getCardArmor());
            otherCard1.setCardAttack(battleModel.getUserCards().get(CardID).getCardAttack());
        }

        if(index==2){
            otherCard2.setCardBack(CardID);
            otherCard2.setCardHP(battleModel.getUserCards().get(CardID).getCardHP());
            otherCard2.setCardArmor(battleModel.getUserCards().get(CardID).getCardArmor());
            otherCard2.setCardAttack(battleModel.getUserCards().get(CardID).getCardAttack());
        }

        if(index==3){
            otherCard3.setCardBack(CardID);
            otherCard3.setCardHP(battleModel.getUserCards().get(CardID).getCardHP());
            otherCard3.setCardArmor(battleModel.getUserCards().get(CardID).getCardArmor());
            otherCard3.setCardAttack(battleModel.getUserCards().get(CardID).getCardAttack());
            /*可修改范围*/
            Animation animationWaitingUp = new TranslateAnimation(
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                    TranslateAnimation.RELATIVE_TO_SELF, -1);
            animationWaitingUp.setDuration(1000);
            animationWaitingUp.setFillAfter(true);
            rl_battle_waiting_other_side.startAnimation(animationWaitingUp);
            /*可修改范围*/
            int_state=3;
        }
    }

    private class clickListener_Confirm implements View.OnClickListener {
        public void onClick(View v) {
            if(int_state==1&&battleModel.chosedCardNo()==3) {
                battleModel.playerCardPickConfirm();
                int_state = 2;
            }
            //updateOtherSideCard(1,3);
        }
    }

    //请不要改这个...
    private class clickListener_otherCard implements View.OnClickListener {
        int index;//从左往右第几张牌

        public clickListener_otherCard(int id){
            this.index=id;
        }

        public void onClick(View v) {
            if(int_state==3)
                battleModel.attackOtherTarget(index);

        }
    }

    //请不要改这个...
    private class clickListener_myCard implements View.OnClickListener {
        int index;//从左往右第几张牌

        public clickListener_myCard(int id){
            this.index=id;
        }

        public void onClick(View v) {
            if(int_state==1)
                switch(index){
                    case 1:cardPicker1.show();break;
                    case 2:cardPicker2.show();break;
                    case 3:cardPicker3.show();break;
                }
            if(int_state==3)
                battleModel.chooseMyCard(index);

        }
    }

    //显示Toast
    public void displayMessage(String msg){
            gameModel.showToast(BattleActivity.this, msg);

    }

}

package mangoabliu.finalproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import mangoabliu.finalproject.Layout.FontTextView;

/**
 * Created by Lyris on 29/11/16.
 */

public class BattleActivity extends AppCompatActivity {

    private FontTextView tv_searching;
    private ImageView img_searchingUp,img_searchingDown;
    private RelativeLayout rl_battleStart;

    //Test
    private Button btnTest;
    private Button btnExit;

    Animation animSearch= new AlphaAnimation(0.0f, 1.0f);
    Animation animShow= new AlphaAnimation(0.0f, 1.0f);
//    TranslateAnimation animMove;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_battle);
        initBattleView();
        //blink
        animSearch.setDuration(1500); //You can manage the blinking time with this parameter
        animSearch.setStartOffset(20);
        animSearch.setRepeatMode(Animation.REVERSE);
        animSearch.setRepeatCount(Animation.INFINITE);
        tv_searching.startAnimation(animSearch);

        animShow.setDuration(1500);
        animShow.setStartOffset(1000);
    }


    private void initBattleView(){

        tv_searching = (FontTextView) findViewById(R.id.battle_searching_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Marvel-Bold.ttf");
        tv_searching.setTypeface(typeFace);

        img_searchingUp = (ImageView) findViewById(R.id.img_battle_searchingup);
        img_searchingDown = (ImageView) findViewById(R.id.img_battle_searchingdown);
        rl_battleStart = (RelativeLayout) findViewById(R.id.battle_start);

        btnTest = (Button) findViewById(R.id.btn_battle_test);
        btnExit = (Button) findViewById(R.id.btn_battle_exit);

        btnTest.setOnClickListener(new btnTestListener());
        btnExit.setOnClickListener(new btnExitListener());

    }

    //向上隐藏动画
    private void hiddenScrollAnimUp(ImageView img) {
        img.setVisibility(View.GONE);
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(1000);
        mHiddenAction.setStartOffset(20);
        img.startAnimation(mHiddenAction);
    }

    //向下隐藏动画
    private void hiddenScrollAnimDown(ImageView img) {
        img.setVisibility(View.GONE);
        TranslateAnimation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(1000);
        mHiddenAction.setStartOffset(20);
        img.startAnimation(mHiddenAction);
    }


    private class btnTestListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            tv_searching.clearAnimation();
            tv_searching.setVisibility(View.GONE);
            hiddenScrollAnimUp(img_searchingUp);
            hiddenScrollAnimDown(img_searchingDown);

//            //MOVING ANIM
//            animMove.setDuration(3000);
//            animMove = new TranslateAnimation(0,0,500,0);
//            img_searchingUp.setAnimation(animMove);
//            img_searchingUp.setVisibility(View.GONE);

            rl_battleStart.startAnimation(animShow);
            rl_battleStart.setVisibility(View.VISIBLE);

        }
    }

    private class btnExitListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(BattleActivity.this, MainGameActivity.class);
            startActivity(myIntent);
        }
    }


}

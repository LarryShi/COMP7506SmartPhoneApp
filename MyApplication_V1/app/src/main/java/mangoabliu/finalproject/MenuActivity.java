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
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LyrisXu on 2016-11-26.
 */


public class MenuActivity extends AppCompatActivity implements AnimationListener {

    Button btn_Switch;

    TextView tv_Menu;
    //    Point size;
//    @TargetApi(16)
//    Animation animationFadeIn, animationFadeOut;
    Animation anim = new AlphaAnimation(0.0f, 1.0f);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);
        btn_Switch = (Button) findViewById(R.id.btn_SwitchLogin);
        btn_Switch.setOnClickListener(new bt_SwitchListener());
        tv_Menu = (TextView) findViewById(R.id.tv_menu);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Marvel-Bold.ttf");
        tv_Menu.setTypeface(typeFace);

        // translation animate -deleted
////        tv_Menu.setOnClickListener(new tv_MenuListener());
//        //get screen size
//        size=new Point();
//        Display display=getWindowManager().getDefaultDisplay();
//        display.getSize(size);
//        tv_Menu.animate().translationX(size.y/2).withStartAction(new Runnable(){
//            @Override
//            public void run() {
//                tv_Menu.animate().translationY(size.y/4).alpha(0.5f);
//            }
//        });

        //slide animate -deleted
//        tv_Menu.startAnimation(AnimationUtils.loadAnimation(MenuActivity.this,android.R.anim.slide_in_left));

        //fade_In
//        animationFadeIn= AnimationUtils.loadAnimation(MenuActivity.this,R.anim.fade_in);
//        animationFadeIn.setRepeatCount(10);
//        animationFadeOut=AnimationUtils.loadAnimation(MenuActivity.this,R.anim.fade_out);
//        tv_Menu.setVisibility(View.VISIBLE);
//        tv_Menu.setAnimation(animationFadeIn);

        //blink
        anim.setDuration(1500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        tv_Menu.startAnimation(anim);



    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    private class bt_SwitchListener implements View.OnClickListener {

        public void onClick(View view) {
            Intent myIntent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivityForResult(myIntent, 0);
        }
    }

}

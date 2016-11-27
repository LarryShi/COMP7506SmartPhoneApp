package mangoabliu.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Button;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.widget.TextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Random;

import mangoabliu.finalproject.Model.GameModel;
import mangoabliu.finalproject.RotateObject;
import static mangoabliu.finalproject.DisplayImageOptionsUtil.getDisplayImageOptions;


/**
 * Created by Byanka on 26/11/2016.
 */


public class CardDropActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout CardRootM;
    private ImageView CardBackM;
    private ImageView CardFrontM;
    private RelativeLayout CardRootR;
    private ImageView CardBackR;
    private ImageView CardFrontR;
    private RelativeLayout CardRootL;
    private ImageView CardBackL;
    private ImageView CardFrontL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.carddropactivity);



        initView();
        initData();
    }

    private void initView() {

        CardRootM = (RelativeLayout) findViewById(R.id.CardRootM);
        CardBackM = (ImageView) findViewById(R.id.CardBackM);
        CardFrontM = (ImageView) findViewById(R.id.CardFrontM);
        CardRootR = (RelativeLayout) findViewById(R.id.CardRootR);
        CardBackR = (ImageView) findViewById(R.id.CardBackR);
        CardFrontR = (ImageView) findViewById(R.id.CardFrontR);
        CardRootL = (RelativeLayout) findViewById(R.id.CardRootL);
        CardBackL = (ImageView) findViewById(R.id.CardBackL);
        CardFrontL = (ImageView) findViewById(R.id.CardFrontL);
        Button ReturnMain = (Button) findViewById(R.id.ReturnMain);

        CardBackM.setOnClickListener(this);
        CardBackR.setOnClickListener(this);
        CardBackL.setOnClickListener(this);
        ReturnMain.setOnClickListener(new ReturnMain_Listener());

    }

    private class ReturnMain_Listener implements View.OnClickListener {

        public void onClick(View v) {
            Intent intent = new Intent();
            //setClass函数的第一个参数是一个Context对象
            //Context是一个类，Activity是Context类的子类，也就是说，所有的Activity对象，都可以向上转型为Context对象
            //setClass函数的第二个参数是一个Class对象，在当前场景下，应该传入需要被启动的Activity类的class对象
            intent.setClass(CardDropActivity.this, MainGameActivity.class);
            startActivity(intent);

        }
    }


    /**
     * 设置数据
     */
    public void initData() {

        ImageLoader imageLoader;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        String imageUri = "drawable://" + R.drawable.grey_bg;
        ImageLoader.getInstance().displayImage(imageUri, CardBackM, getDisplayImageOptions());
        ImageLoader.getInstance().displayImage(imageUri, CardBackR, getDisplayImageOptions());
        ImageLoader.getInstance().displayImage(imageUri, CardBackL, getDisplayImageOptions());

        Random random=new Random();
        int DropOneCard =random.nextInt(17);
        Resources res = getResources();
        String[] CardsName = res.getStringArray(R.array.cards_name);
        Context context = CardBackM.getContext();
        int DropCardId = context.getResources().getIdentifier(CardsName[DropOneCard], "drawable", context.getPackageName());
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        String imageUriFront = "drawable://" + DropCardId ;
        ImageLoader.getInstance().displayImage(imageUriFront, CardFrontL, getDisplayImageOptions());
        ImageLoader.getInstance().displayImage(imageUriFront, CardFrontM, getDisplayImageOptions());
        ImageLoader.getInstance().displayImage(imageUriFront, CardFrontR, getDisplayImageOptions());
        CardFrontL.setVisibility(View.INVISIBLE);
        CardFrontR.setVisibility(View.INVISIBLE);
        CardFrontM.setVisibility(View.INVISIBLE);
        CardBackM.setVisibility(View.VISIBLE);
        CardBackR.setVisibility(View.VISIBLE);
        CardBackL.setVisibility(View.VISIBLE);
        setCameraDistance(CardRootM);
        setCameraDistance(CardRootL);
        setCameraDistance(CardRootR);

    }


    /**
     * 改变视角距离, 贴近屏幕
     */
    private void setCameraDistance(RelativeLayout CardTurnRoot) {
        int distance = 10000;
        float scale = getResources().getDisplayMetrics().density * distance;
        CardTurnRoot.setCameraDistance(scale);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.CardBackL: {
                ViewHelper.setRotationY(CardFrontL, 180f);//先翻转180，转回来时就不是反转的了
                RotateObject rotatable = new RotateObject.Builder(CardRootL)
                        .sides(R.id.CardBackL, R.id.CardFrontL)
                        .direction(RotateObject.ROTATE_Y)
                        .rotationCount(1)
                        .build();
                rotatable.setTouchEnable(false);
                rotatable.rotate(RotateObject.ROTATE_Y, -180, 1500);
                CardBackM.setEnabled(false);
                CardBackR.setEnabled(false);

            }break;
            case R.id.CardBackM: {

                ViewHelper.setRotationY(CardFrontM, 180f);//先翻转180，转回来时就不是反转的了
                RotateObject rotatable = new RotateObject.Builder(CardRootM)
                        .sides(R.id.CardBackM, R.id.CardFrontM)
                        .direction(RotateObject.ROTATE_Y)
                        .rotationCount(1)
                        .build();
                rotatable.setTouchEnable(false);
                rotatable.rotate(RotateObject.ROTATE_Y, -180, 1500);
                CardBackL.setEnabled(false);
                CardBackR.setEnabled(false);


            }break;
            case R.id.CardBackR: {

                ViewHelper.setRotationY(CardFrontR, 180f);//先翻转180，转回来时就不是反转的了
                RotateObject rotatable = new RotateObject.Builder(CardRootR)
                        .sides(R.id.CardBackR, R.id.CardFrontR)
                        .direction(RotateObject.ROTATE_Y)
                        .rotationCount(1)
                        .build();
                rotatable.setTouchEnable(false);
                rotatable.rotate(RotateObject.ROTATE_Y, -180, 1500);
                CardBackM.setEnabled(false);
                CardBackL.setEnabled(false);


            }
                break;
        }

    }

}

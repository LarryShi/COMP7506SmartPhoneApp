package mangoabliu.finalproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Random;

import mangoabliu.finalproject.Animation.ZoomObject;
import mangoabliu.finalproject.Layout.FontTextView;
import mangoabliu.finalproject.Model.GameModel;
import mangoabliu.finalproject.Model.Planet;
import mangoabliu.finalproject.Model.StepService;

import static android.content.ContentValues.TAG;
import static mangoabliu.finalproject.Animation.DisplayImageOptionsUtil.getDisplayImageOptions;

/**
 * Created by 10836 on 2016-11-16.
 */

// Add PossibleCard, LocName by Lyrisxu  11-28

public class LocationDialogView extends Dialog {

    GameModel gameModel;
    int clickedLoc;
    ImageView image_pCard;

    Button start, drop;

    FontTextView tv_locName;
    String clickedLocName;
    ImageView expandedImageView;
    ZoomObject zoomHelper;

    //Add Style /Lyris 11-26
    protected LocationDialogView(Context context, int loc, int style ) {
        super(context, style);
        clickedLoc = loc;
    }

    public Button getStart(){
        return start;
    }

    public Button getDrop(){
        return drop;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不显示对话框标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置对话框显示哪个布局文件

        //ADD FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_location);
        //getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //对话框也可以通过资源id找到布局文件中的组件，从而设置点击侦听
//        Button cancel = (Button) findViewById(R.id.locationDialogCancel);
        start = (Button) findViewById(R.id.locationGo);
        drop = (Button) findViewById(R.id.dropCard);

        //PossibleCardDisplay  /Lyris
        image_pCard =(ImageView) findViewById(R.id.locationImage);
        final int pCardID = possibleCardGenerator();
        expandedImageView =  (ImageView) findViewById(
                R.id.expanded_image);
        initCardData(pCardID);
        zoomHelper = new ZoomObject(this);
        image_pCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                zoomHelper.zoomImageFromThumb(image_pCard,expandedImageView,findViewById(R.id.expanded_image));
            }
        });

        //Display LocationName  /Lyris
        tv_locName = (FontTextView) findViewById(R.id.locationDialogTitle);

        gameModel = GameModel.getInstance();

//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

        start.setOnClickListener(new startTripListener());

        drop.setOnClickListener(new drop_DropCardListener());


        start.setVisibility(View.INVISIBLE);
        start.setEnabled(false);

        gameModel = GameModel.getInstance();
        int currentLocID = gameModel.getUserAccount().getCurrentLocId();
        Log.i(TAG, currentLocID+","+clickedLoc);
        if ((currentLocID == 1 && clickedLoc == 2) ||
                (currentLocID == 2 && (clickedLoc == 1 || clickedLoc == 3 || clickedLoc ==4)) ||
                (currentLocID == 3 && (clickedLoc == 2 || clickedLoc == 5))||
                (currentLocID == 4 && (clickedLoc == 2 || clickedLoc == 5))||
                (currentLocID == 5 && (clickedLoc == 3 || clickedLoc == 4 || clickedLoc == 6))||
                (currentLocID == 6 && (clickedLoc == 5))) {

            start.setVisibility(View.VISIBLE);
            start.setEnabled(true);
        }

        initLocName();

    }




    //PossibleCardGenerator - Random
    private int possibleCardGenerator(){
        Random rand = new Random();
        int pOneCard = rand.nextInt(18);
        Resources res = this.getContext().getResources();
        String[] CardsName = res.getStringArray(R.array.cards_name);
        Context context = image_pCard.getContext();
        int pCardID = context.getResources().getIdentifier(CardsName[pOneCard], "drawable", context.getPackageName());
        return pCardID;
    }

    //Init Card
    private void initCardData(int pCardID){

        ImageLoader imageLoader;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getContext()));
        String imageUriFront = "drawable://" + pCardID;
        ImageLoader.getInstance().displayImage(imageUriFront, image_pCard, getDisplayImageOptions());
        ImageLoader.getInstance().displayImage(imageUriFront, expandedImageView, getDisplayImageOptions());
    }

    //Init Location Name
    private  void initLocName(){
        ArrayList<Planet> planets = gameModel.getPlanets();
        Planet clickedPlanet = planets.get(clickedLoc-1);
        clickedLocName = clickedPlanet.getPlanetName();
        tv_locName.setText(clickedLocName);
//        tv_locName.setTypeface(typeFace);
    }



    private class drop_DropCardListener implements View.OnClickListener {

        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(v.getContext(), CardDropActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);

        }
    }
    private class startTripListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            gameModel.getUserAccount().setTargetLocId(clickedLoc);
            gameModel.updateTargetLocation(gameModel.getUserAccount().getUserId(),
                    clickedLoc);
            gameModel.setTotalSteps(gameModel.getDistances()[gameModel.getWalkingLine()-1]);
            gameModel.getMainGameActivity().startService(new Intent(gameModel.getMainGameActivity(),StepService.class));
            dismiss();
        }

    }


}


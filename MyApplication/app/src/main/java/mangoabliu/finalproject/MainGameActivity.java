package mangoabliu.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Dictionary;

import mangoabliu.finalproject.Model.GameModel;
import mangoabliu.finalproject.Model.StepService;

import static android.content.ContentValues.TAG;
import static mangoabliu.finalproject.R.attr.height;

/**
 * Created by 10836 on 2016-11-16.
 */


/*
* planet1开始坐标(350,440)           startPoint(460,510),endPoint(615,618)
* planet2开始坐标(680,660)           startPoint(720,575),endPoint(795,284)
* planet3开始坐标(800,210)          startPoint(780,625),endPoint(910,495)
* planet4开始坐标(970,430)           startPoint(880,224),endPoint(1350,335)
* planet5开始坐标(1430,330)           startPoint(1080,445),endPoint(1353,375)
* planet6开始坐标(1270,750)           startPoint(1335,695),endPoint(1425,425)
*/

public class MainGameActivity extends AppCompatActivity {
    GameModel gameModel;
    UserAccount myUser;
    Button userProfile, loc1,loc2,loc3,loc4,loc5,loc6,fight,stepTest;
    TextView distance;
    ImageView thumbnail;
    int walkedDistance=0;

    int stepCount;
    int sendStepCounter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //register to Model
        gameModel=GameModel.getInstance();
        gameModel.addActivity(this);
        gameModel.setMainGameActivity(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gamepage);



        userProfile = (Button) findViewById(R.id.userProfile);
        loc1 = (Button) findViewById(R.id.location1);
        loc2 = (Button) findViewById(R.id.location2);
        loc3 = (Button) findViewById(R.id.location3);
        loc4 = (Button) findViewById(R.id.location4);
        loc5 = (Button) findViewById(R.id.location5);
        loc6 = (Button) findViewById(R.id.location6);
        fight = (Button) findViewById(R.id.fight);

        distance = (TextView) findViewById(R.id.distance);

        stepTest = (Button) findViewById(R.id.stepTest);
        stepTest.setOnClickListener(new stepTestListener());
        stepCount = 0;
        sendStepCounter = 0;

        userProfile.setOnClickListener(new userProfileListener());
        loc1.setOnClickListener(new location1Listener());
        loc2.setOnClickListener(new location2Listener());
        loc3.setOnClickListener(new location3Listener());
        loc4.setOnClickListener(new location4Listener());
        loc5.setOnClickListener(new location5Listener());
        loc6.setOnClickListener(new location6Listener());

        initiateGame();

        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.myLayout);
        thumbnail = new ImageView(MainGameActivity.this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);
        thumbnail.setX(gameModel.getPlanets().get(gameModel.getUserAccount().getCurrentLocId()-1).getPlanetX());
        thumbnail.setY(gameModel.getPlanets().get(gameModel.getUserAccount().getCurrentLocId()-1).getPlanetY());
        thumbnail.setImageResource(R.drawable.ufo);
        myLayout.addView(thumbnail,layoutParams);
    }


    public void updateDistance(int step){

        //currentDistance = myService.getTotalStepsTaken();
        //Log.d(TAG, String.valueOf(step));
        double startLocX = gameModel.getPlanets().get(gameModel.getUserAccount().getCurrentLocId()-1).getPlanetX();
        double startLocY = gameModel.getPlanets().get(gameModel.getUserAccount().getCurrentLocId()-1).getPlanetY();
        double destLocX = gameModel.getPlanets().get(gameModel.getUserAccount().getTargetLocId()-1).getPlanetX();
        double destLocY = gameModel.getPlanets().get(gameModel.getUserAccount().getTargetLocId()-1).getPlanetY();

        double currentLocX = gameModel.getUserAccount().getCurrentLocCoordinate()[0];
        double currentLocY = gameModel.getUserAccount().getCurrentLocCoordinate()[1];
        double nextLocX = 0;
        double nextLocY = 0;

        int totalSteps = 200;

        int walkedDis = gameModel.getUserAccount().getWalkDistance();

        gameModel.getUserAccount().setWalkDistance(step + walkedDis);
        distance.setText(gameModel.getUserAccount().getWalkDistance() + "Miles");
        if (walkedDis + step >= 200){
            //gameModel.updateTargetLocation(gameModel.getUserAccount().getUserId(),
            //        gameModel.getUserAccount().getTargetLocId());
            stopService(new Intent(MainGameActivity.this,StepService.class));
            return;
        }


        nextLocY = Math.sqrt(((Math.pow(destLocX-startLocX,2)+Math.pow(destLocY-startLocY,2))/Math.pow(totalSteps,2))/
                    (Math.pow((destLocX-startLocX)/(destLocY-startLocY),2)+1))+currentLocY;
        nextLocX = (destLocX-startLocX)/(destLocY-startLocY)*(nextLocY-currentLocY)+currentLocX;

        gameModel.getUserAccount().setCurrentLocCoordinate(new double[]{ nextLocX, nextLocY});

        //Animation animation = new TranslateAnimation((float)currentLocX,(float)nextLocX,
         //       (float) currentLocY,(float)nextLocY);

        System.out.println("currentLocX " + currentLocX);
        System.out.println("nextLocX " + nextLocX);
        System.out.println("currentLocY " + currentLocY);
        System.out.println("nextLocY " + nextLocY);

        float differenceX = (float)nextLocX- (float)startLocX;
        float differenceY = (float)nextLocY- (float)startLocY;
        Animation animation = new TranslateAnimation(differenceX,differenceX,differenceY,differenceY);
        animation.setDuration(300);
        animation.setFillAfter(true);
        thumbnail.startAnimation(animation);
    }


    private class stepTestListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            stepCount++;
            sendStepCounter++;
            //Log.d(TAG, String.valueOf(totalStepsTaken));
            if (stepCount == 1) {
                gameModel.updateMainGameStep(stepCount);
                stepCount = 0;
            }
            if (sendStepCounter == 10){
                gameModel.sendUserStep(gameModel.getUserAccount().getUserId(),
                        gameModel.getUserAccount().getWalkDistance());
                sendStepCounter = 0;
            }
        }
    }

    public void sendStepSuccessful(String result){
        Log.d(TAG,result);
    }

    public void updateTargetLocationSuccessful(String result){
        Log.d(TAG, result);
    }

    public void errorMessage(String err){
        gameModel.showToast(MainGameActivity.this, err);
    }


    private class userProfileListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            UserProfileAlertView dialog = new UserProfileAlertView(MainGameActivity.this);
            dialog.show();

        }
    }

    private class location1Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,1);
            loc.show();
        }
    }
    private class location2Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,2);
            loc.show();
        }
    }
    private class location3Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,3);
            loc.show();
        }
    }
    private class location4Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,4);
            loc.show();
        }
    }
    private class location5Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,5);
            loc.show();
        }
    }
    private class location6Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,6);
            loc.show();
        }
    }

    private class fightListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }




    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog_exit();
            return true;
        }
        return false;
    }

    private void dialog_exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainGameActivity.this);
        builder.setMessage(R.string.confirm_to_exit);
        builder.setTitle(R.string.NOTICE);
        builder.setPositiveButton(R.string.Confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                gameModel.finshAllActivities();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    public void initiateGame(){
        String data = getIntent().getExtras().getString("UserAccount");
        try {
                JSONObject passedData = new JSONObject(data);
                int id = Integer.parseInt((String) passedData.getJSONObject("UserInfo").get("UserID"));
                String userName = (String) passedData.getJSONObject("UserInfo").get("UserName");
                walkedDistance = Integer.parseInt((String) passedData.getJSONObject("UserInfo").get("WalkDistance"));
                myUser = new UserAccount(id,userName,walkedDistance);
                gameModel.setUserAccount(myUser);
                distance.setText(gameModel.getUserAccount().getWalkDistance() + "Miles");
                Log.i(TAG, "UserName = " + userName +" walkDistance = " + walkedDistance);
                initiatePlanetLocation(passedData);
               /* for (int i = 0; i < gameModel.getPlanets().size(); i++)
                {
                    Log.i(TAG,"LocationID: " + gameModel.getPlanets().get(i).getPlanetID());
                    Log.i(TAG,"LocationName: " + gameModel.getPlanets().get(i).getPlanetName());
                    Log.i(TAG,"LocationX: " + gameModel.getPlanets().get(i).getPlanetX());
                    Log.i(TAG,"LocationY: " + gameModel.getPlanets().get(i).getPlanetY());
                }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void initiatePlanetLocation(JSONObject data){
        try {
            JSONArray planets = data.getJSONArray("LocationInfo");
            for(int i = 0; i < planets.length(); i++){
                JSONObject currentPlanet = planets.getJSONObject(i);
                int id = currentPlanet.getInt("LocationID");
                String name = currentPlanet.getString("LocationName");
                int x = currentPlanet.getInt("LocationPositionX");
                int y = currentPlanet.getInt("LocationPositionY");
                Planet myPlanet = new Planet(id,name,x,y);
                gameModel.getPlanets().add(myPlanet);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

}

package mangoabliu.finalproject;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mangoabliu.finalproject.Layout.FontTextView;
import mangoabliu.finalproject.Model.Card;
import mangoabliu.finalproject.Model.GameModel;
import mangoabliu.finalproject.Model.Planet;
import mangoabliu.finalproject.Model.UserAccount;

import static android.content.ContentValues.TAG;

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
    Button userProfile, loc1,loc2,loc3,loc4,loc5,loc6,fight;
    FontTextView distance;
    ImageView thumbnail;
    String userProfileName;
    int walkedDistance=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //register to Model
        gameModel=GameModel.getInstance();
        gameModel.addActivity(this);
        gameModel.setMainGameActivity(this);

        // FULLSCREEN  /LYRIS 11.26
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);



        userProfile = (Button) findViewById(R.id.userProfile);
        loc1 = (Button) findViewById(R.id.location1);
        loc2 = (Button) findViewById(R.id.location2);
        loc4 = (Button) findViewById(R.id.location4);
        loc5 = (Button) findViewById(R.id.location5);
        loc3 = (Button) findViewById(R.id.location3);
        loc6 = (Button) findViewById(R.id.location6);
        fight = (Button) findViewById(R.id.fight);

        distance = (FontTextView) findViewById(R.id.distance);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Marvel-Bold.ttf");
        distance.setTypeface(typeFace);

        userProfile.setOnClickListener(new userProfileListener());
        loc1.setOnClickListener(new location1Listener());
        loc2.setOnClickListener(new location2Listener());
        loc3.setOnClickListener(new location3Listener());
        loc4.setOnClickListener(new location4Listener());
        loc5.setOnClickListener(new location5Listener());
        loc6.setOnClickListener(new location6Listener());

        String fromActivity = getIntent().getExtras().getString("FromActivity");
        if (fromActivity.equals("MainActivity"))
            initiateGame();

        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.myLayout);
        thumbnail = new ImageView(MainGameActivity.this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(100, 100);

        thumbnail.setX((float)gameModel.getUserAccount().getCurrentLocCoordinate()[0]);
        thumbnail.setY((float)gameModel.getUserAccount().getCurrentLocCoordinate()[1]);
        thumbnail.setImageResource(R.drawable.ufo);
        myLayout.addView(thumbnail,layoutParams);
    }


    public void updateDistance(float animEndX, float animEndY){

        displayDistance();
        updateUFO(animEndX,animEndY);
    }

    public void updateUFO(float x, float y){
        thumbnail.setX(x);
        thumbnail.setY(y);
    }
    public void displayDistance(){
        distance.setText(gameModel.getUserAccount().getWalkDistance() + "Miles");
    }

    public void sendStepSuccessful(String result){
        Log.d(TAG,result);
    }

    public void updateTargetLocationSuccessful(String result){
        Log.d(TAG, result);
    }

    public void updateCurrentLocationSuccessful(String result){
        Log.d(TAG, result);
    }

    public void updateCurrentPositionSuccessful(String result){
        Log.d(TAG, result);
    }

    public void errorMessage(String err){
        gameModel.showToast(MainGameActivity.this, err);
    }

    public void getUserCards_successful(String jsonObject) {
        try {
            JSONObject passedData = new JSONObject(jsonObject);
            JSONArray UserCards = passedData.getJSONArray("UserInfo");
            for (int i = 0; i < UserCards.length(); i++) {
                JSONObject currentCard = UserCards.getJSONObject(i);
                int CardID = currentCard.getInt("CardID");
                String CardName = currentCard.getString("CardName");
                int CardHP = currentCard.getInt("CardHP");
                int CardAttack = currentCard.getInt("CardAttack");
                int CardArmor = currentCard.getInt("CardArmor");
                int CardRarity = currentCard.getInt("CardRarity");
                Card myCard = new Card(CardID,CardName,CardHP,CardAttack,CardArmor,CardRarity);
                gameModel.getUserCards().add(myCard);
            }
            }catch(JSONException e){
                e.printStackTrace();

        }
    }


    private class userProfileListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            gameModel.getUserCard(gameModel.getUserAccount().getUserId());
            UserProfileAlertView dialog = new UserProfileAlertView(MainGameActivity.this,R.style.DialogTranslucent,userProfileName);

            dialog.show();
        }
    }

    // Increase Style /Lyris 11-26

    private class location1Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,1,R.style.DialogTranslucent);
            loc.show();
        }
    }
    private class location2Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,2,R.style.DialogTranslucent);
            loc.show();
        }
    }
    private class location3Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,3,R.style.DialogTranslucent);
            loc.show();
        }
    }
    private class location4Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,4,R.style.DialogTranslucent);
            loc.show();
        }
    }
    private class location5Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,5,R.style.DialogTranslucent);
            loc.show();
        }
    }
    private class location6Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this,6,R.style.DialogTranslucent);
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

            userProfileName = (String) passedData.getJSONObject("UserInfo").get("UserName");
            walkedDistance = Integer.parseInt((String) passedData.getJSONObject("UserInfo").get("WalkDistance"));
            int currentLocID = Integer.parseInt((String) passedData.getJSONObject("UserInfo").get("CurrentLocationID"));
            int targetLocID = Integer.parseInt((String) passedData.getJSONObject("UserInfo").get("TargetLocationID"));
            double currentPositionX = Double.parseDouble((String) passedData.getJSONObject("UserInfo").get("CurrentPositionX"));
            double currentPositionY = Double.parseDouble((String) passedData.getJSONObject("UserInfo").get("CurrentPositionY"));
            double[] currentPosition = new double[]{currentPositionX,currentPositionY};
            myUser = new UserAccount(id,userProfileName,walkedDistance,currentLocID,targetLocID,currentPosition);
            gameModel.setUserAccount(myUser);
            distance.setText(gameModel.getUserAccount().getWalkDistance() + "Miles");
            Log.i(TAG, "UserName = " + userProfileName +" walkDistance = " + walkedDistance);
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

    public void updateUserCardRelationSuccessful(String result) {

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

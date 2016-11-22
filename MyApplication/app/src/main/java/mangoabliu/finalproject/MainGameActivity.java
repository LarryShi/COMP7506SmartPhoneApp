package mangoabliu.finalproject;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import mangoabliu.finalproject.Model.GameModel;
import mangoabliu.finalproject.Model.StepService;

import static android.content.ContentValues.TAG;
import static mangoabliu.finalproject.R.attr.height;

/**
 * Created by 10836 on 2016-11-16.
 */

public class MainGameActivity extends AppCompatActivity {
    GameModel gameModel;
    UserAccount myUser;
    Button userProfile, loc1,loc2,loc3,loc4,fight;
    TextView distance;
    double currentDistance;
    double walkedDistance;
    double totalDistance;
    StepService myService;
    Handler distanceHandler;
    Runnable distanceRunnable;


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
        fight = (Button) findViewById(R.id.fight);

        distance = (TextView) findViewById(R.id.distance);

        userProfile.setOnClickListener(new userProfileListener());
        loc1.setOnClickListener(new location1Listener());

        initiateGame();
        updateDistance();

    }


    public void updateDistance(){
        distanceHandler = new Handler();
        distanceRunnable = new Runnable() {
            @Override
            public void run() {
                currentDistance = myService.getTotalStepsTaken();
                totalDistance = currentDistance + walkedDistance;
                distance.setText(totalDistance + "Miles");
                distanceHandler.postDelayed(this,5000);
            }
        };
        distanceHandler.postDelayed(distanceRunnable,5000);//打开定时器，执行操作

        if (totalDistance >= 200){
            distanceHandler.removeCallbacks(distanceRunnable);// 关闭定时器处理
        }
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
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this);
            loc.show();
        }
    }
    private class location2Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this);
            loc.show();
        }
    }
    private class location3Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this);
            loc.show();
        }
    }
    private class location4Listener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            LocationDialogView loc = new LocationDialogView(MainGameActivity.this);
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
        myService = new StepService();
        currentDistance =  myService.getTotalStepsTaken();

        try {
                JSONObject passedData = new JSONObject(data);
                String userName = (String) passedData.getJSONObject("UserInfo").get("UserName");
                walkedDistance = Double.parseDouble((String) passedData.getJSONObject("UserInfo").get("WalkDistance"));
                myUser = new UserAccount(userName,walkedDistance);
                distance.setText(Double.toString(walkedDistance) + "Miles");
                Log.i(TAG, "UserName = " + userName +" walkDistance = " + walkedDistance);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

package mangoabliu.finalproject.Model;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by herenjie on 2016/11/22.
 */

public class StepService extends Service {
    String TAG = "StepService";
    GameModel gameModel;
    private  boolean isRunning = true;
    private int stepCount;
    private int sendStepCounter;




    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        gameModel=GameModel.getInstance();
        int retVal = super.onStartCommand(intent,flags,startId);
        stepCount = 0;
        sendStepCounter = 0;
        Log.d(TAG,String.valueOf(stepCount));
        Log.d(TAG, "onStartCommand" + intent);
        registerForSensorEvents();
        return retVal;

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        this.stopService(new Intent(this,StepService.class));
        isRunning = false;
        Log.d(TAG,"onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void registerForSensorEvents() {
        SensorManager sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //step counter
        Sensor countSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if (isRunning){

                        stepCount++;
                        sendStepCounter++;
                        //Log.d(TAG, String.valueOf(totalStepsTaken));
                        if (stepCount == 1) {

                            gameModel.updateMainGameStep(stepCount);
                            stepCount = 0;

                        }
                        if (sendStepCounter == 10){
                            gameModel.updateUserStep(gameModel.getUserAccount().getUserId(),
                                    gameModel.getUserAccount().getWalkDistance());
                            sendStepCounter = 0;
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else{
            Log.d(TAG,"Count sensor not available!");
        }
    }

}

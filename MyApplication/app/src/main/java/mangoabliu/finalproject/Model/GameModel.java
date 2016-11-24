package mangoabliu.finalproject.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


import mangoabliu.finalproject.LoginActivity;
import mangoabliu.finalproject.MainGameActivity;
import mangoabliu.finalproject.RegistrationActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by SHI Zhongqi on 2016-11-16.
 */

public class GameModel {
    private LinkedList<AppCompatActivity> activityLinkedList = new LinkedList<>();
    private static GameModel instance;
    private final String String_base_url="http://i.cs.hku.hk/~zqshi/ci/index.php/";
    private LoginActivity loginActivity;
    private RegistrationActivity registrationActivity;
    private MainGameActivity mainGameActivity;
    protected final static String str_login_function="login";
    protected final static String str_registration_function="registration";

    private GameModel() {

    }

    public static GameModel getInstance(){
        if(null == instance){
            instance = new GameModel();
        }
        return instance;
    }
    //Registration Related
    public void setRegistrationActivity(RegistrationActivity registrationActivity){
        this.registrationActivity=registrationActivity;
    }

    public void registration(String str_Username,String str_Password){
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("UserName",str_Username);
            jsonObject.put("Password",str_Password);
            jsonObject.put("Nickname","shabi");
            jsonObject.put("Alliance","adf");

            serverPHPPostConnection(getRegistrationURL(),jsonObject.toString(),str_registration_function);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void registration_finished(String str_result){
        try {
            JSONObject jsonObj = new JSONObject(str_result);
            if((Integer)jsonObj.get("code")==0) {
                registrationActivity.successful((String)jsonObj.get("message"));
            }
            else
                registrationActivity.errorMessage((String)jsonObj.get("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateMainGameStep(int step){
        this.mainGameActivity.updateDistance(step);
    }

    //Main Game Related
    public void setMainGameActivity(MainGameActivity mainGameActivity){
        this.mainGameActivity=mainGameActivity;
    }

    //login related Activity
    public void setLoginActivity(LoginActivity loginActivity){
        this.loginActivity=loginActivity;
    }

    public void login(String str_UserName, String str_Password) {
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("UserName", str_UserName);
            jsonObject.put("Password", str_Password);

            serverPHPPostConnection(getLoginURL(),jsonObject.toString(),str_login_function);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void login_finished(String str_result){
        try {
            JSONObject jsonObj = new JSONObject(str_result);
            if((Integer)jsonObj.get("code")==0) {
                loginActivity.successful(jsonObj.toString());
            }
            else
                loginActivity.errorMessage((String)jsonObj.get("message"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //HTTP Request Related Info
    private void serverPHPPostConnection(String str_URL,String str_JSON,String str_Function){
        try{
            HTTPRequest mTask = new HTTPRequest();
            mTask.execute(str_URL,str_JSON,str_Function);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLoginURL(){
        return String_base_url+"Server/loginM";
    }

    public String getRegistrationURL(){
        return String_base_url+"Server/registerM";
    }
    //HTTP Request Related End

    public void addActivity(AppCompatActivity activity){
        activityLinkedList.add(activity);

    }


    public void finshAllActivities() {
        for (AppCompatActivity activity : activityLinkedList) {
            activity.finish();
        }

    }


    public void showToast(Context context, String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public boolean isConnectingToInternet(Context _context){
        //Toast.makeText(Home.this, "10%", Toast.LENGTH_SHORT).show();
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Toast.makeText(Home.this, "20%", Toast.LENGTH_SHORT).show();
        if (connectivity != null)
        {
            //Toast.makeText(Home.this, "30%", Toast.LENGTH_SHORT).show();
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null){
                //Toast.makeText(Home.this, "70%", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
            }
        }
        return false;
    }


}

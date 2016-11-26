package mangoabliu.finalproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mangoabliu.finalproject.Model.GameModel;


public class LoginActivity extends AppCompatActivity {

    //REVISED BY XP   11/26+++
    //TYPEFACE FONT
    TextView tv_UserName;
    TextView tv_Password;

    EditText et_UserName;
    EditText et_Password;
    Button btn_Registration;
    Button btn_Login;
    GameModel gameModel;
    //for service Reference :http://www.cnblogs.com/yejiurui/p/3429451.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //register to Model
        gameModel=GameModel.getInstance();
        gameModel.addActivity(this);
        gameModel.setLoginActivity(this);

        // FULLSCREEN  /LYRIS 11.26
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //Elements on the Screen
        btn_Registration = (Button) findViewById(R.id.register);
        btn_Login = (Button) findViewById(R.id.login);

        et_UserName = (EditText) findViewById(R.id.usernameText);
        et_Password = (EditText) findViewById(R.id.passwordText);

        //REVISED BY XP   11/26+++
        tv_UserName =(TextView) findViewById(R.id.username);
        tv_Password =(TextView)findViewById(R.id.password);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Marvel-Bold.ttf");
        tv_UserName.setTypeface(typeFace);
        tv_Password.setTypeface(typeFace);
        //IME
        et_UserName.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        et_Password.setInputType(EditorInfo.TYPE_CLASS_TEXT);


        //Elements on the Screen Ends
        //Set Btn Listener
        btn_Login.setOnClickListener(new bt_LoginListener());
        btn_Registration.setOnClickListener(new bt_RegisterListener());

        //check Internet
        try{
            gameModel.showToast(LoginActivity.this, "Checking");
            boolean boolean_connected = gameModel.isConnectingToInternet(getApplicationContext());
            if(!boolean_connected){
                gameModel.showToast(LoginActivity.this, "No Network Connection!");
            }
            else
                gameModel.showToast(LoginActivity.this, "Internet Works!");
        }catch(Exception e){

        }

    }

    private class bt_LoginListener implements View.OnClickListener {

        public void onClick(View v) {
            String str_UserName=et_UserName.getText().toString();
            String str_Password=et_Password.getText().toString();
            Log.i("LoginActivity","bt_LoginListener onClick!");
            if(str_UserName.equals(""))
                gameModel.showToast(LoginActivity.this, "Please Input the Username");
            else if(str_Password.equals(""))
                gameModel.showToast(LoginActivity.this, "Please Input the Password");
            else {
                gameModel.login(str_UserName, str_Password);
            }

        }
    }

    private class bt_RegisterListener implements View.OnClickListener {

        public void onClick(View view) {
            Intent myIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivityForResult(myIntent, 0);
        }
    }

    public void successful(String jsonObject){
        Intent myIntent = new Intent(LoginActivity.this, MainGameActivity.class);
        myIntent.putExtra("UserAccount",jsonObject);
        startActivityForResult(myIntent, 0);
    }

    public void errorMessage(String err){
        gameModel.showToast(LoginActivity.this, err);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

package mangoabliu.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import mangoabliu.finalproject.Model.GameModel;

/**
 * Created by herenjie on 2016/11/13.
 */

public class RegistrationActivity extends AppCompatActivity {
    GameModel gameModel;
    Button btn_cancel,btn_register;
    EditText et_UserName,et_Password,et_Password_repeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //register to Model
        gameModel= GameModel.getInstance();
        gameModel.addActivity(this);
        gameModel.setRegistrationActivity(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.registrationpage);

        btn_cancel = (Button)findViewById(R.id.cancelButton);
        btn_register = (Button) findViewById(R.id.registerButton);

        btn_cancel.setOnClickListener(new bt_CancelListener());
        btn_register.setOnClickListener(new bt_RegisterListener());

        et_UserName = (EditText) findViewById(R.id.usernameRegisterText);
        et_Password = (EditText) findViewById(R.id.passwordRegisterText);
        et_Password_repeat = (EditText) findViewById(R.id.passwordConfirmText);
    }

    public void successful(String msg){
        gameModel.showToast(RegistrationActivity.this, msg);
        super.onBackPressed();
    }

    public void errorMessage(String err){
        gameModel.showToast(RegistrationActivity.this, err);
    }

    private class bt_RegisterListener implements View.OnClickListener {

        public void onClick(View v) {
            String str_UserName=et_UserName.getText().toString();
            String str_Password=et_Password.getText().toString();
            String str_Pwd_confirm = et_Password_repeat.getText().toString();
            Log.i("RegistrationActivity","bt_RegisterListener onClick!");
            if(str_UserName.equals(""))
                gameModel.showToast(RegistrationActivity.this, "Please Input the Username");
            else if(str_Password.equals(""))
                gameModel.showToast(RegistrationActivity.this, "Please Input the Password");
            else if(!str_Pwd_confirm.equals(str_Password)){
                gameModel.showToast(RegistrationActivity.this, "Password is not same");

            }else{
                gameModel.registration(str_UserName, str_Password);
            }

        }
    }

    private class bt_CancelListener implements View.OnClickListener {
        public void onClick(View view) {
            RegistrationActivity.super.onBackPressed();
        }
    }
}

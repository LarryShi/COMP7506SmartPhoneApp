package mangoabliu.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by herenjie on 2016/11/13.
 */

public class RegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.registrationpage);

        Button cancel = (Button)findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(myIntent,0);
            }
        });


        final EditText username = (EditText) findViewById(R.id.usernameRegisterText);
        final EditText password = (EditText) findViewById(R.id.passwordRegisterText);
        Button register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "http://i.cs.hku.hk/~zqshi/ci/index.php/Server/register";
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("UserName",username.getText());
                    jsonObject.put("Password",password.getText());
                    jsonObject.put("Nickname","shabi");
                    jsonObject.put("Alliance","adf");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               // myServer.submitPostData(url,jsonObject);
            }
        });
    }

}

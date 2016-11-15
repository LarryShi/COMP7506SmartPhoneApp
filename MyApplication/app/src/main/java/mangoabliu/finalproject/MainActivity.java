package mangoabliu.finalproject;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        Button registration = (Button)findViewById(R.id.register);
        registration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), Registration.class);
                startActivityForResult(myIntent,0);
            }
        });

        final EditText userName = (EditText) findViewById(R.id.usernameText);
        final EditText password = (EditText) findViewById(R.id.passwordText);


        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                ServerConnector myServer = new ServerConnector();
                UserAccount myUser = new UserAccount(userName.getText().toString(),
                        password.getText().toString());
                String url = "http://i.cs.hku.hk/~zqshi/ci/index.php/Server/loginM";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("UserName", userName.getText().toString());
                    jsonObject.put("Password", password.getText().toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(jsonObject.toString());
                myServer.execute(url,jsonObject.toString());

                Intent myIntent = new Intent(view.getContext(), MainGame.class);
                startActivityForResult(myIntent,0);
            }
        });
    }
}

package mangoabliu.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    Button btn_Switch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // FULLSCREEN  /LYRIS 11.26
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);
        btn_Switch = (Button) findViewById(R.id.btn_SwitchLogin);
        btn_Switch.setOnClickListener(new bt_SwitchListener());
    }


    private class bt_SwitchListener implements View.OnClickListener {

        public void onClick(View view) {
            Intent myIntent = new Intent(MenuActivity.this, LoginActivity.class);
            startActivityForResult(myIntent, 0);
        }
    }
}

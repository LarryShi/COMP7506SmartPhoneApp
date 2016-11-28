package mangoabliu.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import mangoabliu.finalproject.Model.GameModel;

import static android.content.ContentValues.TAG;

/**
 * Created by herenjie on 2016/11/16.
 */

public class UserProfileAlertView extends Dialog {

    //Add Style_transparent
    String name;
    GameModel gameModel;

    protected UserProfileAlertView(Context context,int style,String username){
        super(context,style);
        name=username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不显示对话框标题栏
        gameModel.getInstance();
        gameModel.setUserProfileAlertView(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //ADD FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        //设置对话框显示哪个布局文件
        setContentView(R.layout.dialog_userprofile);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //对话框也可以通过资源id找到布局文件中的组件，从而设置点击侦听
        Button bt = (Button) findViewById(R.id.userProfileDialogCancel);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        TextView tv_userName = (TextView) findViewById(R.id.usernameProfile);
        tv_userName.setText(name);
    }


    public void getUserCardSuccessful(String result){
        Log.d(TAG, result);
    }

}

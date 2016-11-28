package mangoabliu.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.BaseAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

import mangoabliu.finalproject.Layout.FontTextView;
import mangoabliu.finalproject.Model.Card;
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
        gameModel=GameModel.getInstance();
        gameModel.setUserProfileAlertView(this);

        ArrayList<Card> myCards = gameModel.getUserCards();
        for (int i = 0; i < myCards.size(); i++){



        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //ADD FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        //设置对话框显示哪个布局文件
        setContentView(R.layout.dialog_userprofile);
        final GridView gridview=(GridView)findViewById(R.id.gridview);

        gridview.setAdapter(new ImageAdapter(this));

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        //对话框也可以通过资源id找到布局文件中的组件，从而设置点击侦听
        Button bt = (Button) findViewById(R.id.userProfileDialogCancel);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        FontTextView tv_userName = (FontTextView) findViewById(R.id.usernameProfile);
        tv_userName.setText(name);
    }

    public class ImageAdapter extends BaseAdapter {

        Context context;
        public Integer[] myImageIds = {R.drawable.auto1, R.drawable.auto2, R.drawable.auto3, R.drawable.auto4,
                R.drawable.auto5, R.drawable.bike, R.drawable.bike1};

        public ImageAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return myImageIds.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv;
            if (convertView == null) {
                iv.setImageResource(myImageIds[position]);
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv.setLayoutParams(new GridView.LayoutParams(85, 85));
            } else {
                iv = (ImageView) convertView;
            }
            iv.setImageResource(myImageIds[position]);
            return iv;


        }

    }






}

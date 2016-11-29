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
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //ADD FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //设置对话框显示哪个布局文件
        setContentView(R.layout.dialog_userprofile);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final GridView gridview=(GridView)findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getContext()));






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

        private Context mcontext;

        ArrayList<Integer> myImageIds = gameModel.getMyImageIds();


        public ImageAdapter(Context c) {
            mcontext = c;
        }

        @Override
        public int getCount() {
            return myImageIds.size();
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
                iv =new ImageView(mcontext);
                iv.setImageResource(myImageIds.get(position));
                iv.setScaleType(ImageView.ScaleType.FIT_XY);
                iv.setLayoutParams(new GridView.LayoutParams(400,500));
            } else {
                iv = (ImageView) convertView;
            }
            iv.setImageResource(myImageIds.get(position));
            return iv;


        }

    }






}

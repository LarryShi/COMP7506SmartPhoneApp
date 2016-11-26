package mangoabliu.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import mangoabliu.finalproject.Model.GameModel;
import mangoabliu.finalproject.Model.StepService;

/**
 * Created by herenjie on 2016/11/17.
 */



public class LocationDialogView extends Dialog {

    GameModel gameModel;
    int clickedLoc;

    protected LocationDialogView(Context context, int loc) {
        super(context);
        clickedLoc = loc;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不显示对话框标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置对话框显示哪个布局文件
        setContentView(R.layout.dialog_location);
        //getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //对话框也可以通过资源id找到布局文件中的组件，从而设置点击侦听
        Button cancel = (Button) findViewById(R.id.locationDialogCancel);
        Button start = (Button) findViewById(R.id.locationGo);
        Button drop = (Button) findViewById(R.id.dropCard);

        gameModel = GameModel.getInstance();

        if (gameModel.getUserAccount().getCurrentLocId() == clickedLoc)
        {
            start.setVisibility(View.GONE);
            start.setEnabled(false);
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        start.setOnClickListener(new startTripListener());


        drop.setOnClickListener(new drop_DropCardListener());
    }

    private class drop_DropCardListener implements View.OnClickListener {

        public void onClick(View v) {

        }
    }

    private class startTripListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            v.getContext().startService(new Intent(v.getContext(),StepService.class));
            //v.getContext().stopService(new Intent(v.getContext(),StepService.class));
            dismiss();
        }

    }

}


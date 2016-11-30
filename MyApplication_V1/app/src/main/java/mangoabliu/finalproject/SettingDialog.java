package mangoabliu.finalproject;

import android.app.Dialog;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.SeekBar;

import mangoabliu.finalproject.Model.GameModel;

/**
 * Created by Lyris on 30/11/16.
 */

public class SettingDialog extends Dialog {
    GameModel gameModel;

    // setting
    private ImageButton btnMusicCtrl = null;
    private SeekBar seekbar;
    private int maxVolume;
    private int currentVolume;
    private int muteFlag,cv;
    public AudioManager audioManager;
    Context con;


    protected SettingDialog (Context context, int style) {
        super(context,style);
        con = context;
        gameModel= GameModel.getInstance();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置不显示对话框标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置对话框显示哪个布局文件

        //ADD FULLSCREEN
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dialog_setting);

        SettingInit();

    }

    public void SettingInit(){

        btnMusicCtrl = (ImageButton) findViewById(R.id.btnMute);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        audioManager = (AudioManager) con.getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        cv=currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekbar.setMax(maxVolume);
        seekbar.setProgress(currentVolume);

        //unmute、mute切换
        if(cv!=0) {btnMusicCtrl.setBackgroundResource(R.drawable.unmute);}
        else {btnMusicCtrl.setBackgroundResource(R.drawable.mute);}

        btnMusicCtrl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                isMute();
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
                currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                if(i>0) {
                    btnMusicCtrl.setBackgroundResource(R.drawable.unmute);
                    seekbar.setProgress(i);}
                else  btnMusicCtrl.setBackgroundResource(R.drawable.mute);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }




    public void isMute(){
        //按静音键,muteFlag=1说明静音状态
        if (currentVolume!=0 || muteFlag==0){
            muteFlag=1;
            audioManager = (AudioManager) con.getSystemService(Context.AUDIO_SERVICE);
            cv = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_MUTE,0);
            btnMusicCtrl.setBackgroundResource(R.drawable.mute);
            seekbar.setProgress(0);
        }else//按开启键
        {
            muteFlag=0;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_UNMUTE,0);
            btnMusicCtrl.setBackgroundResource(R.drawable.unmute);
            seekbar.setProgress(cv);
        }
    }

}

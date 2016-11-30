package mangoabliu.finalproject;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lyris on 30/11/16.
 */

public class SoundPlayer {

    private static MediaPlayer music;
    private static SoundPool soundPool;
    private static boolean musicSt = true; //Music start
    private static boolean soundSt = true; //Sound Effect start
    private static Context context;
    private static final int[] musicId = {R.raw.index_bg};
    private static Map<Integer,Integer> soundMap; //音效与加载音效ID的对应MAP

    /**
     * init
     * @param c
     */
    public  SoundPlayer(Context c)
    {
        context = c;
        initMusic();
        initSound();
    }

    //init SoundEffect
    private static void initSound()
    {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,100);
        soundMap = new HashMap<Integer,Integer>();
        soundMap.put(R.raw.go, soundPool.load(context, R.raw.go, 1));
        soundMap.put(R.raw.choose_confirm, soundPool.load(context, R.raw.choose_confirm, 1));
    }

    //init Music
    private static void initMusic()
    {
        int r = new Random().nextInt(musicId.length);
        music = MediaPlayer.create(context,musicId[r]);
        music.setLooping(true);
    }

    /**
     * play sound
     * @param resId resource sound id
     */
    public static void playSound(int resId)
    {
        if(!soundSt)
            return;

        Integer soundId = soundMap.get(resId);
        if(soundId != null)
            soundPool.play(soundId, 1, 1, 1, 0, 1);
    }

    /**
     * change music
     */
    public static void changeAndPlayMusic()
    {
        if(music != null)
            music.release();
        initMusic();
        setMusicSt(true);
    }

    /**
     * Is music on/off ?
     * @return
     */
    public static boolean isMusicSt() {
        return musicSt;
    }

    /**
     * set music on/off
     * @param musicSt
     */
    public static void setMusicSt(boolean musicSt) {
        SoundPlayer.musicSt = musicSt;
        if(musicSt)
            music.start();
        else
            music.stop();
    }

    /**
     * Is sound on/off ?
     * @return
     */
    public static boolean isSoundSt() {
        return soundSt;
    }

    /**
     * Set sound on/off
     * @param soundSt
     */
    public static void setSoundSt(boolean soundSt) {
        SoundPlayer.soundSt = soundSt;
    }

    /**
     * release res
     */
    public static void releasePlayer()
    {
        music.release();
        soundPool.release();
        soundMap.clear();
    }

}

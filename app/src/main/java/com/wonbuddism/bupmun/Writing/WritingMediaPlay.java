package com.wonbuddism.bupmun.Writing;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by csc-pc on 2016. 7. 27..
 */
public class WritingMediaPlay {
    private String TAG = "WritingMediaPlay";
    private Context context;

    private MediaPlayer mMediaPlayer;


    public WritingMediaPlay(Context context) {
        this.context = context;
    }


    private String getUrl(String index){
        String url = "http://www.won.or.kr/contents/bupmun/mp3/EB/"+index+".mp3";
        return url;
    }

    public boolean isPalyingAudio(){
        return mMediaPlayer.isPlaying();
    }

    public void SetupMedia(String index){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mMediaPlayer.setDataSource(getUrl(index));
            //mMediaPlayer.prepare(); // might take long! (for buffering, etc)
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReleaseMedia(){
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    public void RePlayAudio(){
        mMediaPlayer.stop();
        try {
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.seekTo(0);
        mMediaPlayer.start();
    }

    public int PlayPauseAudio(){
        if(!mMediaPlayer.isPlaying()){
            mMediaPlayer.start();
            return 0;

        }else{
            mMediaPlayer.pause();
            return 1;
        }
    }


}

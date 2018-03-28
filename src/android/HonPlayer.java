package com.honeywell.sip;

import android.util.Log;

import org.pjsip.pjsua2.AudioMedia;

import java.util.ArrayList;

/**
 * Created by weiminji on 1/10/18.
 */

public class HonPlayer {
    // Used to load the 'native-lib' library on application startup.

    ArrayList<String> mPlayStatusType;
    private long mNativeObject;
    private OnPlayStatusListener mPlayStatusListener;

    public HonPlayer(OnPlayStatusListener playStatusListener) {
        initNative();

        mPlayStatusListener = playStatusListener;

        mPlayStatusType = new ArrayList<>();
        mPlayStatusType.add("Exit");
        mPlayStatusType.add("Start");
        mPlayStatusType.add("Playing");
        mPlayStatusType.add("Finish");
    }

    public void startTransmit(AudioMedia sink) {
        nativeStartTransmit(HonAudioMedia.getNativePtr(sink));
    }

    public void stopTransmit(AudioMedia sink) {
        nativeStopTransmit(HonAudioMedia.getNativePtr(sink));
    }


    public void onStatusChanged(String songPath, int type, int param) {
        Log.d("HonPlayer",
                "onStatusChanged: song=" + songPath + " type=" + type + " param:" + param);

        if (mPlayStatusListener == null) {
            return;
        }

        if (type < 0 || type >= mPlayStatusType.size()) {
            return;
        }

        mPlayStatusListener.onPlayStatusChanged(mPlayStatusType.get(type), songPath, param);
    }


    public void playSong(String path) {
        Log.d("HonPlayer", "Play song " + path);

        nativeSetPlaySong(path);
    }

    /*
    public void setRepeatType(int type) {
        nativeSetRepeatType(type);
    }

    public boolean addMusics(ArrayList<String> songs){
        return nativeAddMusics(songs);
    }

    public boolean deleteMusic(int index) {
        return nativeDeleteMusic(index);
    }

    public boolean reorderMusic(int from, int to) {
        return nativeReorderMusic(from, to);
    }
    */
    public void delete() {
        nativeDelete();
    }
    
    private native int initNative();
    //public native boolean createPlayer(ArrayList<String> playlist, PlayOption option);
    private native void nativeStartTransmit(long nativePtr);
    public native void nativeStopTransmit(long nativePtr);
    public native void nativeSetPlaySong(String path);
    //public native void nativeSetRepeatType(int index);
    //public native boolean nativeAddMusics(ArrayList<String> songs);
    //public native boolean nativeDeleteMusic(int index);
    //public native boolean nativeReorderMusic(int from, int to);
    public native void nativeDelete();

}

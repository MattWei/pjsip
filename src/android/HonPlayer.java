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

    public void stopTransmit(AudioMedia sink) {
        nativeStopTransmit(HonAudioMedia.getNativePtr(sink));
    }

    private long mNativeObject;
    private OnPlayStatusListener mPlayStatusListener;

    public HonPlayer(OnPlayStatusListener playStatusListener) {
        initNative();

        mPlayStatusListener = playStatusListener;

        mPlayStatusType = new ArrayList<>();
        mPlayStatusType.add("Start");
        mPlayStatusType.add("Playing");
        mPlayStatusType.add("Finish");

    }

    public void startTransmit(AudioMedia sink) {
        nativeStartTransmit(HonAudioMedia.getNativePtr(sink));
    }

    private native int initNative();
    public native boolean createPlayer(ArrayList<String> playlist, PlayOption option);
    private native void nativeStartTransmit(long nativePtr);
    public native void nativeStopTransmit(long nativePtr);
    public native void nativeSetPlaySong(int index);
    public native void nativeSetRepeatType(int index);
    public native void nativeAddMusices(ArrayList<String> songs);
    public native void nativeDeleteMusic(int index);
    public native void nativeReorderMusic(int from, int to);
    
    public void onStatusChanged(int index, int type, int param) {
        Log.d("HonPlayer",
                "onStatusChanged: index=" + index + " type=" + type + " param:" + param);

        if (mPlayStatusListener == null) {
            return;
        }

        if (type < 1 || type > mPlayStatusType.size()) {
            return;
        }

        mPlayStatusListener.onPlayStatusChanged(mPlayStatusType.get(type - 1), index, param);
        
    }

    public void setPlaySong(int index) {
        Log.d("HonPlayer", "Set play song " + index);

        nativeSetPlaySong(index);
    }

    public void setRepeatType(int type) {
        Log.d("HonPlayer", "setRepeatType to " + type);
        nativeSetRepeatType(type);
    }

    public void addMusices(ArrayList<String> songs){
        nativeAddMusices(songs);
    }

    public void deleteMusic(int index) {
        nativeDeleteMusic(index);
    }

    public void reorderMusic(int from, int to) {
        nativeReorderMusic(from, to);
    }
}

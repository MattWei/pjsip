package gr.navarino.cordova.plugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.media.RingtoneManager;
import android.media.Ringtone;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.provider.Settings;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.R;

import gr.navarino.cordova.plugin.scAudioManager;


import gr.navarino.cordova.plugin.PjsipActivity;
import gr.navarino.cordova.plugin.Utils;

import com.honeywell.sip.PlayOption;

import java.util.ArrayList;

/**
 * Created by infuser on 10/04/17.
 * A singleton class to handles the calls
 * It guaranties that there is no problem between
 * Garbage Collector and PJSIP library
 */
public class PjsipActions extends CordovaActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


    private Context mContext;
    public static PjsipActivity pjsipActivity = null;
    public Utils utils = null;


    final scAudioManager scAudio = scAudioManager.getInstance();
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO= 5000;

    private static CordovaInterface cordova = null;
    private static CordovaWebView webView =  null;

    private static PjsipActions ourInstance = new PjsipActions();

    public static PjsipActions getInstance() {

        return ourInstance;
    }

    private PjsipActions() {}

    public Boolean isConnected(){
        return pjsipActivity.isConnected();
    }

    public void initialise(CordovaInterface crd, CordovaWebView wbview){
        if (pjsipActivity == null){


//            super.onRequestPermissionsResult(MY_PERMISSIONS_REQUEST_RECORD_AUDIO,new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

            cordova = crd;
            webView = wbview;

            pjsipActivity = new PjsipActivity();
            pjsipActivity.initialise(cordova.getActivity().getFilesDir().getAbsolutePath());

            mContext = cordova.getActivity();

            scAudio.initialise(cordova, webView);


        }

    }

    public synchronized Boolean connect(final String user, final String pass, final String domain,
                                        final String proxy, final CallbackContext callbackContext){


        return pjsipActivity.connect(user,pass,domain,proxy, callbackContext);

    }
    public synchronized Boolean disconnect(final CallbackContext callbackContext){

        return pjsipActivity.disconnect(callbackContext);

    }


    public synchronized void acceptCall(final CallbackContext callbackContext){

        scAudio.stopRingtone();
        pjsipActivity.acceptCall(callbackContext);

    }

    public synchronized void declineCall(final CallbackContext callbackContext){

        scAudio.stopRingtone();
        pjsipActivity.declineCall(callbackContext);

    }

    public synchronized void endCall(final CallbackContext callbackContext){

        scAudio.stopTone();
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                pjsipActivity.endCall(callbackContext);

            }
        });

    }


    public synchronized void askPermissions(){

    }


    public synchronized void makeCall(final String number, final String param, final CallbackContext callbackContext){

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pjsipActivity.makeCall(number, param, callbackContext);
            }
        });

    }

    public void setSpeakerMode(Boolean isActive, final CallbackContext callbackContext){

        try{
            scAudio.setSpeakerMode(isActive);
            if (callbackContext!=null)
                callbackContext.success();
        } catch(Exception e){
            callbackContext.error(e.toString());
        }

    }

    public void muteMicrophone(Boolean isActive,final CallbackContext callbackContext){

        try{
            scAudio.muteMicrophone(isActive);
            callbackContext.success();
        } catch(Exception e){
            callbackContext.error(e.toString());
        }



    }

    public synchronized void holdCall(final Boolean isActive, final CallbackContext callbackContext){

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pjsipActivity.holdCall(isActive,callbackContext);

            }
        });


    }


    public synchronized void sendDTMF(final String num, final CallbackContext callbackContext){

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                scAudio.playDTMF(num);
                pjsipActivity.sendDTMF(num,callbackContext);

            }
        });


    }

    public synchronized void sendInstantMessage(final String buddy, final String message, final CallbackContext callbackContext){

        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pjsipActivity.sendInstantMessage(buddy, message, callbackContext);
            }
        });
    }

    public void addBuddy(final String buddy, final CallbackContext callbackContext){
        try{
            pjsipActivity.addBuddy(buddy);
            if (callbackContext!=null)
                callbackContext.success();
        } catch(Exception e){
            callbackContext.error(e.toString());
        }
    }

    public void deleteBuddy(final String buddy, final CallbackContext callbackContext){
        try{
            pjsipActivity.deleteBuddy(buddy);
            if (callbackContext!=null)
                callbackContext.success();
        } catch(Exception e){
            callbackContext.error(e.toString());
        }
    }

    public synchronized void makeFilesCall(final String number, final String songPath, 
                                        /*final PlayOption option, */final String callOption, final CallbackContext callbackContext){
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                pjsipActivity.makeFilesCall(number, songPath, /*option, */callOption, callbackContext);
            }
        });
    }

    public void changePlayingSong(final String songPath, final CallbackContext callbackContext) {
        try{
            pjsipActivity.changePlayingSong(songPath, callbackContext);
            if (callbackContext!=null)
                callbackContext.success();
        } catch(Exception e){
            callbackContext.error(e.toString());
        }
    }

    /*
    public void changeFilesCallRepeatType(final int type, final CallbackContext callbackContext) {
        try{
            pjsipActivity.changeFilesCallRepeatType(type, callbackContext);
        } catch(Exception e){
            callbackContext.error(e.toString());
        }
    }

    public void addMusicsToPlaylistCall(final ArrayList<String> songs, final CallbackContext callbackContext) {
        try {
            pjsipActivity.addMusicsToPlaylistCall(songs, callbackContext);
        } catch(Exception e){
            callbackContext.error(e.toString());
        }
    }

    public void deleteMusicFromPlaylistCall(final int index, final CallbackContext callbackContext) {
        try {
            pjsipActivity.deleteMusicFromPlaylistCall(index, callbackContext);
        } catch(Exception e){
            callbackContext.error(e.toString());
        }
    }
    
    public void reorderMusic(final int from, final int to, final CallbackContext callbackContext) {
        try {
            pjsipActivity.reorderMusic(from, to, callbackContext);
        } catch(Exception e){
            callbackContext.error(e.toString());
        }
    }
    */
}

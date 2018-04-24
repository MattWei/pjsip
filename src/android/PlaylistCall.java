package gr.navarino.cordova.plugin;

import org.pjsip.pjsua2.AudioMedia;
import org.pjsip.pjsua2.Call;
import org.pjsip.pjsua2.OnDtmfDigitParam;

import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallMediaInfo;
import org.pjsip.pjsua2.CallMediaInfoVector;
import org.pjsip.pjsua2.Media;
import org.pjsip.pjsua2.OnCallMediaStateParam;
import org.pjsip.pjsua2.OnCallStateParam;
import org.pjsip.pjsua2.VideoPreview;
import org.pjsip.pjsua2.VideoWindow;
import org.pjsip.pjsua2.pjmedia_type;
import org.pjsip.pjsua2.pjsip_inv_state;
import org.pjsip.pjsua2.pjsua2;
import org.pjsip.pjsua2.pjsua_call_media_status;

import gr.navarino.cordova.plugin.MyApp;
import gr.navarino.cordova.plugin.MyAccount;
import gr.navarino.cordova.plugin.MyCall;

import android.util.Log;

import com.honeywell.sip.HonPlayer;
import com.honeywell.sip.OnPlayStatusListener;
//import com.honeywell.sip.PlayOption;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import android.Manifest;
import android.os.Environment;
import android.os.Bundle;

/**
 * Created by infuser on 30/03/17.
 */

public class PlaylistCall extends MyCall implements OnPlayStatusListener {
    HonPlayer honPlayer = null;

    PlaylistCall(MyAccount acc, int call_id, final String songPath/*, PlayOption playOption*/) {
        super(acc, call_id);

        this.honPlayer = new HonPlayer(this);
        honPlayer.playSong(songPath);
    }

    public void setPlaySong(final String songPath) {
        if (honPlayer != null) {
            honPlayer.playSong(songPath);
        }
    }

    @Override
    public void onCallMediaState(OnCallMediaStateParam prm) {
        CallInfo ci;
        try {
            ci = getInfo();
        } catch (Exception e) {
            return;
        }

        CallMediaInfoVector cmiv = ci.getMedia();
        for (int i = 0; i < cmiv.size(); i++) {
            CallMediaInfo cmi = cmiv.get(i);
            if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_AUDIO
                    && (cmi.getStatus() == pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE
                            || cmi.getStatus() == pjsua_call_media_status.PJSUA_CALL_MEDIA_REMOTE_HOLD)) {
                // unfortunately, on Java too, the returned Media cannot be
                // downcasted to AudioMedia
                Media m = getMedia(i);
                AudioMedia am = AudioMedia.typecastFromMedia(m);

                // connect ports
                try {
                    //MyApp.ep.audDevManager().getCaptureDevMedia().startTransmit(am);
                    //am.startTransmit(MyApp.ep.audDevManager().getPlaybackDevMedia());

                    honPlayer.startTransmit(am);
                } catch (Exception e) {
                    continue;
                }
            }
            /*
            else if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_VIDEO
                    && cmi.getStatus() == pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE
                    && cmi.getVideoIncomingWindowId() != pjsua2.INVALID_ID) {
                vidWin = new VideoWindow(cmi.getVideoIncomingWindowId());
                vidPrev = new VideoPreview(cmi.getVideoCapDev());
            }
            */
        }

        MyApp.observer.notifyCallMediaState(this);
    }

    @Override
    public void onPlayStatusChanged(String statusType, String song, int param) {
        MyApp.observer.notifyPlayStatus(statusType, song, param);
    }


    @Override
    public void onCallState(OnCallStateParam prm)
    {
        MyApp.observer.notifyCallState(this);
        try {
            CallInfo ci = getInfo();
            if (ci.getState() ==
                    pjsip_inv_state.PJSIP_INV_STATE_DISCONNECTED)
            {
                if (honPlayer != null) {
                    Log.d("PlaylistCall", "disconnected");
                    honPlayer.delete();
                }
                this.delete();
            }
        } catch (Exception e) {
            return;
        }
    }
    
}

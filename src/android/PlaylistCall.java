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
import com.honeywell.sip.PlayOption;

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
    public VideoWindow vidWin;
    public VideoPreview vidPrev;

    ArrayList<String> playlist;
    HonPlayer honPlayer = null;
    private PlayOption option = null;
    PlaylistCall(MyAccount acc, int call_id, final ArrayList<String> playlist, PlayOption playOption)
    {
        super(acc, call_id);
        this.playlist = playlist;
        option = playOption;
        vidWin = null;
    }

    public void setPlaySong(int index) {
        if (honPlayer != null) {
            honPlayer.setPlaySong(index);
        }
    }

    public void setRepeatType(int type) {
        if (honPlayer != null) {
            honPlayer.setRepeatType(type);
        }
    }
    
    @Override
    public void onCallMediaState(OnCallMediaStateParam prm)
    {
        CallInfo ci;
        try {
            ci = getInfo();
        } catch (Exception e) {
            return;
        }

        CallMediaInfoVector cmiv = ci.getMedia();
        Log.d("PlaylistCall", "Create HonPlayer with loopOption:" + option.loopOption + 
            " listInterval:" + option.listInterval + " songInterval:" + option.songInterval);

        this.honPlayer = new HonPlayer(this);
        honPlayer.createPlayer(playlist, option);

        for (int i = 0; i < cmiv.size(); i++) {
            CallMediaInfo cmi = cmiv.get(i);
            if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_AUDIO &&
                    (cmi.getStatus() ==
                            pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE ||
                            cmi.getStatus() ==
                                    pjsua_call_media_status.PJSUA_CALL_MEDIA_REMOTE_HOLD))
            {
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
            } else if (cmi.getType() == pjmedia_type.PJMEDIA_TYPE_VIDEO &&
                    cmi.getStatus() == pjsua_call_media_status.PJSUA_CALL_MEDIA_ACTIVE &&
                    cmi.getVideoIncomingWindowId() != pjsua2.INVALID_ID)
            {
                vidWin = new VideoWindow(cmi.getVideoIncomingWindowId());
                vidPrev = new VideoPreview(cmi.getVideoCapDev());
            }
        }

        MyApp.observer.notifyCallMediaState(this);
    }

    @Override
    public void onPlayStatusChanged(String statusType, int index, int param) {
        MyApp.observer.notifyPlayStatus(statusType, index, param);
    }
}

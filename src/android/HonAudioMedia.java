package com.honeywell.sip;

import org.pjsip.pjsua2.AudioMedia;
import org.pjsip.pjsua2.pjsua2JNI;

/**
 * Created by weiminji on 1/10/18.
 */

public class HonAudioMedia extends AudioMedia  {
    protected HonAudioMedia(long cPtr, boolean cMemoryOwn) {
        super(pjsua2JNI.AudioMedia_SWIGUpcast(cPtr), cMemoryOwn);
    }

    public static long getNativePtr(AudioMedia obj) {
        return getCPtr(obj);
    }
}

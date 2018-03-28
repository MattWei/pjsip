package com.honeywell.sip;

import java.util.ArrayList;

/**
 * Created by weiminji on 1/18/18.
 */

public interface OnPlayStatusListener {
    void onPlayStatusChanged(String statusType, String song, int param);
}

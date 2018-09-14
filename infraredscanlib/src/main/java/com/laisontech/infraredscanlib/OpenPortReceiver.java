package com.laisontech.infraredscanlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * ..................................................................
 * .         The Buddha said: I guarantee you have no bug!          .
 * .                                                                .
 * .                            _ooOoo_                             .
 * .                           o8888888o                            .
 * .                           88" . "88                            .
 * .                           (| -_- |)                            .
 * .                            O\ = /O                             .
 * .                        ____/`---'\____                         .
 * .                      .   ' \\| |// `.                          .
 * .                       / \\||| : |||// \                        .
 * .                     / _||||| -:- |||||- \                      .
 * .                       | | \\\ - /// | |                        .
 * .                     | \_| ''\---/'' | |                        .
 * .                      \ .-\__ `-` ___/-. /                      .
 * .                   ___`. .' /--.--\ `. . __                     .
 * .                ."" '< `.___\_<|>_/___.' >'"".                  .
 * .               | | : `- \`.;`\ _ /`;.`/ - ` : | |               .
 * .                 \ \ `-. \_ __\ /__ _/ .-` / /                  .
 * .         ======`-.____`-.___\_____/___.-`____.-'======          .
 * .                            `=---='                             .
 * ..................................................................
 * Created by SDP on 2018/9/13.
 */
public class OpenPortReceiver extends BroadcastReceiver {
    private OnOpenPortListener mListener;

    public OpenPortReceiver() {
    }

    public OpenPortReceiver(OnOpenPortListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) return;
        String action = intent.getAction();
        if (action == null) return;
        switch (action) {
            case ScanConstants.FLAG_OPEN_SUCCESS:
                if (mListener!=null){
                    mListener.onOpenPort(true);
                    ScanManager.getManager().setPortStatus(true);
                }
                break;
            case ScanConstants.FLAG_OPEN_FAIL:
                if (mListener!=null){
                    mListener.onOpenPort(false);
                    ScanManager.getManager().setPortStatus(false);
                }
                break;
        }
    }
}

package com.laisontech.infraredscanlib;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

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
 * 注册管理类，可以在Application中调用，使用单例的方法
 */
public class ScanManager {
    @SuppressLint("StaticFieldLeak")
    private static ScanManager mManager;
    private Context mContext;
    private ScanConfig mConfig;

    public static ScanManager getManager() {
        if (mManager == null) {
            synchronized (ScanManager.class) {
                if (mManager == null) {
                    mManager = new ScanManager();
                }
            }
        }
        return mManager;
    }

    private ScanManager() {
    }

    //初始化
    public ScanManager init(Context context) {
        mConfig = new ScanConfig(context);
        mContext = context;
        return this;
    }

    //配置参数
    public ScanManager setDefaultConfig() {
        if (mConfig != null) {
            mConfig.setF1(false);
            mConfig.setF2(false);
            mConfig.setF3(false);
            mConfig.setF4(false);
            mConfig.setF5(true);
            mConfig.setF6(false);
            mConfig.setF7(false);
            mConfig.setVoice(true);
            mConfig.setSurfix("");
            mConfig.setPrefix("");
        }
        return this;
    }

    public void setPortStatus(boolean openSuccess) {
        if (mConfig == null) return;
        mConfig.setOpen(openSuccess);
    }

    //发送广播
    public void sendEmptyAction(String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        mContext.sendBroadcast(intent);
    }

    public boolean registerScanReceiver(BroadcastReceiver receiver) {
        if (mContext == null || receiver == null) return false;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ScanConstants.ACTION_RECEIVE_DATA);
        mContext.registerReceiver(receiver, filter);
        return true;
    }

    public boolean unregisterScanReceiver(BroadcastReceiver receiver) {
        if (mContext == null || receiver == null) return false;
        mContext.unregisterReceiver(receiver);
        return true;
    }

    public boolean registerOpenReceiver(BroadcastReceiver receiver) {
        if (mContext == null || receiver == null) return false;
        IntentFilter filter = new IntentFilter();
        filter.addAction(ScanConstants.FLAG_OPEN_FAIL);
        filter.addAction(ScanConstants.FLAG_OPEN_SUCCESS);
        mContext.registerReceiver(receiver, filter);
        return true;
    }

    public boolean unregisterOpenReceiver(BroadcastReceiver receiver) {
        if (mContext == null || receiver == null) return false;
        mContext.unregisterReceiver(receiver);
        return true;
    }

    //开启扫描服务
    @SuppressLint("WrongConstant")
    public void openScanService() {
        Intent toService = new Intent(mContext, Scan1DService.class);
        toService.addFlags(ScanConstants.SCAN_FLAG);
        mContext.startService(toService);
    }

    public void closeScanService() {
        Intent toKill = new Intent();
        toKill.setAction(ScanConstants.ACTION_KILL_SERVER);
        toKill.putExtra(ScanConstants.KEY_KILL, true);
        mContext.sendBroadcast(toKill);
    }
}

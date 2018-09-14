package com.laisontech.infraredscanlib;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.pda.scan.ScanThread;
import cn.pda.serialport.Tools;

public class Scan1DService extends Service {

    private ScanThread scan = null;

    private ScanConfig scanConfig;
    private String prefixStr;
    private String suffixStr;

    private ScanHandler mHandler = new ScanHandler(Scan1DService.this);

    private static class ScanHandler extends Handler {
        private WeakReference<Scan1DService> mWeakReference;

        public ScanHandler(Scan1DService scan1dService) {
            mWeakReference = new WeakReference<Scan1DService>(scan1dService);
        }

        public void handleMessage(android.os.Message msg) {
            Scan1DService scan1dService = mWeakReference.get();
            scan1dService.prefixStr = scan1dService.scanConfig.getPrefix();
            scan1dService.suffixStr = scan1dService.scanConfig.getSurfix();
            if (msg.what == ScanConstants.SCAN_CODE) {
                String data = msg.getData().getString(ScanConstants.KEY_DATA);
                Log.e(scan1dService.TAG, "prefixStr=" + scan1dService.prefixStr + "++");
                byte[] surByte = scan1dService.suffixStr.getBytes();
                Log.e(scan1dService.TAG, "suffixStr=" + Tools.Bytes2HexString(surByte, surByte.length) + "++ , surfixStrLen = "
                        + scan1dService.suffixStr.length());
                Log.e(scan1dService.TAG, "data = " + data);
                // input prefix
                if ("0A0D".equals(scan1dService.prefixStr)) {
                    scan1dService.sendToInput("", true);
                } else {
                    scan1dService.sendToInput(scan1dService.prefixStr, false);
                }
                // input barcode
                scan1dService.sendToInput(data, false);
                // input prefix
                if ("".equals(scan1dService.suffixStr) || scan1dService.suffixStr.length() == 0) {
                    // sendToInput("", fa) ;
                } else if ("0A0D".startsWith(scan1dService.suffixStr)) {
                    scan1dService.sendToInput("", true);
                } else {
                    scan1dService.sendToInput(scan1dService.suffixStr, false);
                }
                if (scan1dService.scanConfig.isVoice()) {
                    Util.play();
                }
                scan1dService.isRuning = false;
            } else if (msg.what == ScanConstants.SWITCH_CODE) {
                Log.i(scan1dService.TAG, "handleMessage, SWITCH_INPUT >>>>>> ");
                scan1dService.isRuning = false;
            }
        }

        ;
    }

    ;

    private String TAG = "Scan1DService";

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        scanConfig = new ScanConfig(this);
        Util.initSoundPool(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ScanConstants.ACTION_KILL_SERVER);
        registerReceiver(killReceiver, filter);

        IntentFilter screenFilter = new IntentFilter();
        screenFilter.addAction(Intent.ACTION_SCREEN_OFF);
        screenFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(powerModeReceiver, screenFilter);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(killReceiver);
        unregisterReceiver(powerModeReceiver);
        super.onDestroy();
    }

    private boolean isRuning = false;

    private boolean isKeyDown = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "+++ onstart command++++");
        if (intent == null) {
            return -1;
        }
        boolean keyDown = intent.getBooleanExtra("keyDown", false);
        int initFlag = intent.getFlags();
        if (initFlag == ScanConstants.SCAN_FLAG) {
            if (scan == null) {
                try {
                    scan = new ScanThread(this, mHandler);
                    scan.start();
                } catch (Exception e) {
                    e.printStackTrace();
                    ScanManager.getManager().sendEmptyAction(ScanConstants.FLAG_OPEN_FAIL);
                }
            } else {
                isKeyDown = true;
                isRuning = true;
                scan.scan();
            }
        }
        Log.i(TAG, "onStartCommand >>>>>> isKeyDown = " + isKeyDown);
        Log.i(TAG, "onStartCommand >>>>>> keyDown = " + keyDown);
        Log.i(TAG, "onStartCommand >>>>>> isRunning = " + isRuning);
        if (!isKeyDown && keyDown && !isRuning) {
            if (scan == null) {
                try {
                    scan = new ScanThread(this, mHandler);
                    scan.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                isKeyDown = true;
                isRuning = true;
                scan.scan();
            }
        } else if (!keyDown) {
            isKeyDown = false;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void sendToInput(String data, boolean enterFlag) {
        Intent toBack = new Intent();
        toBack.setAction(ScanConstants.ACTION_RECEIVE_DATA);
        toBack.putExtra(ScanConstants.KEY_DATA, data);
        toBack.putExtra("enter", enterFlag);
        sendBroadcast(toBack);
    }

    private BroadcastReceiver killReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra(ScanConstants.KEY_KILL, false)) {
                if (scan != null) {
                    scan.close();
                    scan = null;
                }
                //服务关闭时，将状态置为关闭
                scanConfig.setOpen(false);
                Scan1DService.this.stopSelf();
            }
        }
    };

    // listner
    private BroadcastReceiver powerModeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // SCREEN ON ACTION
            if (action.equals(Intent.ACTION_SCREEN_ON)) {
                Log.e("powerModeReceiver", "screent on +++ ");
                // new Thread(initTask).start() ;
                try {
                    scan = new ScanThread(Scan1DService.this, mHandler);
                    scan.start();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // SCREEN OFF ACTION
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                Log.e("powerModeReceiver", "screent off +++");
                // if(scanDev != null){
                // scanDev.close() ;
                // }
                if (scan != null) {
                    scan.close();
                    scan = null;
                }
            }

        }
    };

}

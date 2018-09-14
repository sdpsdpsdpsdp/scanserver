package cn.pda.scan;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.laisontech.infraredscanlib.ScanManager;
import com.laisontech.infraredscanlib.ScanConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import cn.pda.serialport.SerialPort;
import cn.pda.serialport.Tools;

public class ScanThread extends Thread {

    private SerialPort mSerialPort;
    private InputStream is;
    private OutputStream os;
    /* serialport parameter */
    private int port = 0;
    private int baudrate = 9600;
    private int flags = 0;
    private Context context;
    private Handler handler;
    private Timer mTimer = null;

    /**
     * if throw exception, serialport initialize fail.
     *
     * @throws SecurityException
     * @throws IOException
     */
    public ScanThread(Context context, Handler handler) throws SecurityException, IOException {
        try {
            this.context = context;
            this.handler = handler;
            mSerialPort = new SerialPort(port, baudrate, flags);

            is = mSerialPort.getInputStream();
            os = mSerialPort.getOutputStream();
            // mSerialPort.scaner_trigoff();
            mSerialPort.scaner_poweron();
            Thread.sleep(200);
            mSerialPort.scaner_trigoff();
            /** clear useless data **/
            byte[] temp = new byte[128];
            is.read(temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
            ScanManager.getManager().sendEmptyAction(ScanConstants.FLAG_OPEN_FAIL);
        }
    }

    public void stopScan() {
        if (mSerialPort.scaner_trig_stat()) {
            mSerialPort.scaner_trigoff();
        }
    }

    @Override
    public void run() {
        try {
            int size = 0;
            byte[] buffer = new byte[4096];
            int available = 0;
            ScanManager.getManager().sendEmptyAction(ScanConstants.FLAG_OPEN_SUCCESS);
            while (!isInterrupted()) {
                available = is.available();
                if (available > 0) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    size = is.read(buffer);
                    if (size > 0) {
                        sendMessege(buffer, size, ScanConstants.SCAN_CODE);
                        if (mTimer != null) {
                            mTimer.cancel();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }

    private void sendMessege(byte[] data, int dataLen, int mode) {
        String dataStr = "";
        if (data != null && dataLen > 0) {
            try {
                dataStr = new String(data, 0, dataLen, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        byte[] temp = dataStr.getBytes();
        if (temp.length == 0) {
            Message msg = new Message();
            msg.what = mode;
            handler.sendMessage(msg);
            return;
        }
        Log.e("", Tools.Bytes2HexString(temp, temp.length));
        if (temp[temp.length - 1] == 0x0a || temp[temp.length - 1] == 0x0d) {
            if (temp[temp.length - 2] == 0x0d) {
                dataStr = new String(temp, 0, temp.length - 2);
            } else {
                dataStr = new String(temp, 0, temp.length - 1);
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(ScanConstants.KEY_DATA, dataStr);
        Message msg = new Message();
        msg.what = mode;
        msg.setData(bundle);
        handler.sendMessage(msg);
        mSerialPort.scaner_trigoff();

    }


    public void scan() {
        mSerialPort.scaner_poweron();
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                mSerialPort.scaner_trigoff();
                sendMessege(null, 0, ScanConstants.SWITCH_CODE);
            }
        }, 3000);
        if (mSerialPort.scaner_trig_stat()) {
            mSerialPort.scaner_trigoff();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return;
        }
        mSerialPort.scaner_trigon();
    }

    public void close() {
        if (mSerialPort != null) {
            mSerialPort.scaner_poweroff();
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSerialPort.close(port);
        }
    }

}

package com.pda.scan1dserver;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.laisontech.infraredscanlib.OnOpenPortListener;
import com.laisontech.infraredscanlib.OnScanResultListener;
import com.laisontech.infraredscanlib.OpenPortReceiver;
import com.laisontech.infraredscanlib.ReceiveScanCodeReceiver;
import com.laisontech.infraredscanlib.ScanManager;
import com.laisontech.infraredscanlib.ScanConfig;

public class MainActivity extends Activity {

    private LinearLayout mMainContainer;
    private ScanConfig scanConfig;
    private Switch openSwitch;

    private Dialog dialogLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanConfig = new ScanConfig(this);
        initView();
    }

    private void initView() {
        mMainContainer = (LinearLayout) findViewById(R.id.main_container);

        openSwitch = (Switch) findViewById(R.id.switch_scan);

        // open dev
        openSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // open scan
                if (isChecked) {
                    createLoaddingDialog();
                    ScanManager.getManager().openScanService();
                } else {
                    ScanManager.getManager().closeScanService();
                }
                scanConfig.setOpen(isChecked);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        ScanManager.getManager().registerScanReceiver(mReceiver);
        ScanManager.getManager().registerOpenReceiver(mOpenReceiver);
    }

    private BroadcastReceiver mReceiver = new ReceiveScanCodeReceiver(new OnScanResultListener() {
        @Override
        public void scanResult(String code) {
            ((TextView) findViewById(R.id.tv_result)).setText(code);
        }
    });
    private BroadcastReceiver mOpenReceiver = new OpenPortReceiver(new OnOpenPortListener() {
        @Override
        public void onOpenPort(boolean openSuccess) {
            if (dialogLoading!=null){
                dialogLoading.dismiss();
            }
            showToast(openSuccess ? "开启成功" : "开启失败");
        }
    });


    // show toast
    private Toast mToast;

    private void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(MainActivity.this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    // create loading dialog
    private void createLoaddingDialog() {
        Builder builder = new Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_loading, mMainContainer, false);
        builder.setView(view);
        dialogLoading = builder.create();
        dialogLoading.setCancelable(false);
        dialogLoading.show();
    }


}

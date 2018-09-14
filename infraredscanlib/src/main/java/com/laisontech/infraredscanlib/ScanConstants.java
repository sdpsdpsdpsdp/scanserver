package com.laisontech.infraredscanlib;

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
public class ScanConstants {
    public static final String FLAG_OPEN_FAIL = "flag_open_fail";
    public static final String FLAG_OPEN_SUCCESS = "flag_open_success";

    public static final String KEY_DATA = "ScanResultData";
    public static final String KEY_KILL = "kill";

    public static final String ACTION_KILL_SERVER = "android.rfid.KILL_SERVER";
    public static final String ACTION_RECEIVE_DATA = "android.rfid.INPUT";

    public static final int SCAN_FLAG = 1693;
    public static final int SCAN_CODE = 1001;
    public static final int SWITCH_CODE = 1002;
}

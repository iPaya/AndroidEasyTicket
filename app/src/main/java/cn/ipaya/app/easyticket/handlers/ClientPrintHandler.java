package cn.ipaya.app.easyticket.handlers;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by ZhangDi on 2017/12/22.
 */

public class ClientPrintHandler extends Handler {
    private static final String TAG = "ClientPrintHandler";
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Log.w(TAG, "reveive msg from server");
    }
}

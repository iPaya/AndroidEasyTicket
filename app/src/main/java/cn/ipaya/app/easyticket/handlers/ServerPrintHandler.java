package cn.ipaya.app.easyticket.handlers;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

public class ServerPrintHandler extends Handler {
    private static final String TAG = "ServerPrintHandler";

    public static final int CMD_STATUS = 0x00;
    public static final int CMD_CONNECT = 0x01;

    @Override
    public void handleMessage(Message msg) {
        Log.d(TAG, "Receive message from client: What: " + msg.what);
        switch (msg.what) {
            case CMD_STATUS:
                Message replyMsg = new Message();
                Bundle data = new Bundle();
                data.putBoolean("isConnected", false);
                replyMsg.setData(data);
                try {
                    msg.replyTo.send(replyMsg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
}

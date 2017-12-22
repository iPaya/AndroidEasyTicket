package cn.ipaya.app.easyticket.messages;

import android.bluetooth.BluetoothDevice;
import android.os.Message;

import cn.ipaya.app.easyticket.handlers.ServerPrintHandler;

public class PrintMessageBuilder {
    /**
     * 构建连接蓝牙打印机消息
     *
     * @param device
     * @return
     */
    public static Message buildConnectMessage(BluetoothDevice device) {
        Message msg = new Message();
        msg.what = ServerPrintHandler.CMD_CONNECT;
        msg.obj = device;
        return msg;
    }

    /**
     * 构建蓝牙打印机连接状态消息
     *
     * @param device
     * @return
     */
    public static Message buildConnectionStatusMessage(BluetoothDevice device) {
        Message msg = new Message();
        msg.what = ServerPrintHandler.CMD_STATUS;
        msg.obj = device;
        return msg;
    }
}

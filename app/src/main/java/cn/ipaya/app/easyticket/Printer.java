package cn.ipaya.app.easyticket;

import android.bluetooth.BluetoothDevice;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ZhangDi on 2017/12/22.
 */

public class Printer {
    private OutputStream mOutputStream;

    /**
     * 复位打印机
     */
    public static final byte[] RESET = {0x1b, 0x40};

    /**
     * 左对齐
     */
    public static final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};

    /**
     * 中间对齐
     */
    public static final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};

    /**
     * 右对齐
     */
    public static final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};

    /**
     * 选择加粗模式
     */
    public static final byte[] BOLD = {0x1b, 0x45, 0x01};

    /**
     * 取消加粗模式
     */
    public static final byte[] BOLD_CANCEL = {0x1b, 0x45, 0x00};

    /**
     * 宽高加倍
     */
    public static final byte[] DOUBLE_HEIGHT_WIDTH = {0x1d, 0x21, 0x11};

    /**
     * 宽加倍
     */
    public static final byte[] DOUBLE_WIDTH = {0x1d, 0x21, 0x10};

    /**
     * 高加倍
     */
    public static final byte[] DOUBLE_HEIGHT = {0x1d, 0x21, 0x01};

    /**
     * 字体不放大
     */
    public static final byte[] NORMAL = {0x1d, 0x21, 0x00};

    /**
     * 设置默认行间距
     */
    public static final byte[] LINE_SPACING_DEFAULT = {0x1b, 0x32};

    public Printer(OutputStream outputStream) {
        mOutputStream = outputStream;
    }

    public void printText(String text) {
        try {
            byte[] data = text.getBytes("gbk");
            mOutputStream.write(data, 0, data.length);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行打印机指令
     *
     * @param command
     */
    public void runCommand(byte[] command) {
        try {
            mOutputStream.write(command);
            mOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复位打印机
     */
    public void reset() {
        runCommand(RESET);
    }

    /**
     * 左对齐
     */
    public void alignLeft() {
        runCommand(ALIGN_LEFT);
    }

    /**
     * 居中
     */
    public void alignCenter() {
        runCommand(ALIGN_CENTER);
    }

    /**
     * 右对齐
     */
    public void alignRight() {
        runCommand(ALIGN_RIGHT);
    }

    /**
     * 加粗
     */
    public void bold() {
        runCommand(BOLD);
    }

    /**
     * 取消加粗
     */
    public void cancelBold() {
        runCommand(BOLD_CANCEL);
    }

    /**
     * 宽高加倍
     */
    public void doubleHeightWidth() {
        runCommand(DOUBLE_HEIGHT_WIDTH);
    }

    /**
     * 宽加倍
     */
    public void doubleWidth() {
        runCommand(DOUBLE_WIDTH);
    }

    /**
     * 高加倍
     */
    public void doubleHeight() {
        runCommand(DOUBLE_HEIGHT);
    }

    /**
     * 字体不放大
     */
    public void fontNormal() {
        runCommand(NORMAL);
    }

    /**
     * 默认行间距
     */
    public void defaultLineSpacing() {
        runCommand(LINE_SPACING_DEFAULT);
    }
}

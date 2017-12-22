package cn.ipaya.app.easyticket.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.ipaya.app.easyticket.R;
import cn.ipaya.app.easyticket.adapters.BondedBluetoothDeviceAdapter;
import cn.ipaya.app.easyticket.base.BaseActivity;

public class PrinterActivity extends BaseActivity {

    boolean isSupportBluetooth = false;
    BluetoothAdapter mBluetoothAdapter;
    View mContentSupportBluetooth;
    View mContentUnsupportBluetooth;
    List<BluetoothDevice> mBondedDevices;
    BondedBluetoothDeviceAdapter mBondedDeviceAdapter;
    ListView mBondedDeviceListView;
    @BindView(R.id.default_printer_name)
    TextView mDefaultPrinterName;

    @BindView(R.id.default_printer_address)
    TextView mDefaultPrinterAddress;

    @BindView(R.id.view_no_default_printer)
    View mViewNoDefaultPrinter;

    @BindView(R.id.view_has_default_printer)
    View mViewHasDefaultPrinter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        enableActionToolbar();
        showBackIcon();

        mContentSupportBluetooth = findViewById(R.id.content_printer_support_bluetooth);
        mContentUnsupportBluetooth = findViewById(R.id.content_printer_unsupport_bluetooth);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mBondedDevices = new ArrayList<BluetoothDevice>();
        mBondedDeviceAdapter = new BondedBluetoothDeviceAdapter(this, mBondedDevices);

        mBondedDeviceListView = (ListView) findViewById(R.id.printer__bonded_devices);
        mBondedDeviceListView.setAdapter(mBondedDeviceAdapter);

        refreshDefaultPrinter();
        detectBluetoothStatus();

        if (isSupportBluetooth) {
            loadBondedDevices();
        }
    }

    private void detectBluetoothStatus() {
        if (mBluetoothAdapter == null) {
            isSupportBluetooth = false;
            mContentUnsupportBluetooth.setVisibility(View.VISIBLE);
            mContentSupportBluetooth.setVisibility(View.INVISIBLE);
        } else {
            isSupportBluetooth = true;
            mContentUnsupportBluetooth.setVisibility(View.INVISIBLE);
            mContentSupportBluetooth.setVisibility(View.VISIBLE);
        }
    }

    private void loadBondedDevices() {
        //遍历已绑定设备
        if (mBluetoothAdapter.getBondedDevices().size() > 0) {
            for (BluetoothDevice bluetoothDevice : mBluetoothAdapter.getBondedDevices()) {
                if (bluetoothDevice.getBluetoothClass().getDeviceClass() == 1664) {
                    mBondedDevices.add(bluetoothDevice);
                }
            }
        }
        mBondedDeviceAdapter.notifyDataSetChanged();
    }

    private void enableBluetooth() {
        mBluetoothAdapter.enable();
    }

    private void disableBluetooth() {
        mBluetoothAdapter.disable();
    }

    @OnItemClick(R.id.printer__bonded_devices)
    void onClickBondedDevices(int position) {
        final BluetoothDevice bluetoothDevice = mBondedDeviceAdapter.getItem(position);
        new AlertDialog.Builder(this)
                .setTitle(R.string.printer__set_default_printer)
                .setMessage("名称: " + bluetoothDevice.getName() + "\n地址: " + bluetoothDevice.getAddress())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = mApplication.getSharedPreferences().edit();
                        editor.putString("default_printer__name", bluetoothDevice.getName());
                        editor.putString("default_printer__address", bluetoothDevice.getAddress());
                        editor.commit();
                        refreshDefaultPrinter();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

    }

    private void refreshDefaultPrinter() {
        SharedPreferences sharedPreferences = mApplication.getSharedPreferences();
        String defaultPrinterName = sharedPreferences.getString("default_printer__name", null);
        String defaultPrinterAddress = sharedPreferences.getString("default_printer__address", null);

        if (defaultPrinterName != null) {
            // 已设置默认打印机
            mViewHasDefaultPrinter.setVisibility(View.VISIBLE);
            mViewNoDefaultPrinter.setVisibility(View.GONE);

            mDefaultPrinterName.setText(defaultPrinterName);
            mDefaultPrinterAddress.setText(defaultPrinterAddress);
        } else {
            // 未设置默认打印机
            mViewHasDefaultPrinter.setVisibility(View.GONE);
            mViewNoDefaultPrinter.setVisibility(View.VISIBLE);

            mDefaultPrinterName.setText(null);
            mDefaultPrinterAddress.setText(null);
        }
    }
}

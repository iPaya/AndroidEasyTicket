package cn.ipaya.app.easyticket.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.ipaya.app.easyticket.R;
import cn.ipaya.app.easyticket.adapters.BondedBluetoothDeviceAdapter;
import cn.ipaya.app.easyticket.base.BaseActivity;

public class PrinterActivity extends BaseActivity {

    BluetoothAdapter mBluetoothAdapter;
    CheckBox mBluetoothStatus;
    View mContentSupportBluetooth;
    View mContentUnsupportBluetooth;
    List<BluetoothDevice> mBondedDevices;
    BondedBluetoothDeviceAdapter mBondedDeviceAdapter;
    ListView mBondedDeviceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);
        enableActionToolbar();
        showBackIcon();

        mContentSupportBluetooth = findViewById(R.id.content_printer_support_bluetooth);
        mContentUnsupportBluetooth = findViewById(R.id.content_printer_unsupport_bluetooth);

        mBluetoothStatus = (CheckBox) findViewById(R.id.printer__bluetooth_status);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mBondedDevices = new ArrayList<BluetoothDevice>();
        mBondedDeviceAdapter = new BondedBluetoothDeviceAdapter(this, mBondedDevices);

        mBondedDeviceListView = (ListView) findViewById(R.id.printer__bonded_devices);
        mBondedDeviceListView.setAdapter(mBondedDeviceAdapter);

        detectBluetoothStatus();

        loadBondedDevices();

        mBluetoothStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableBluetooth();
                } else {
                    disableBluetooth();
                }
            }
        });


    }

    private void detectBluetoothStatus() {
        if (mBluetoothAdapter == null) {
            mContentUnsupportBluetooth.setVisibility(View.VISIBLE);
            mContentSupportBluetooth.setVisibility(View.INVISIBLE);
        } else {
            mContentUnsupportBluetooth.setVisibility(View.INVISIBLE);
            mContentSupportBluetooth.setVisibility(View.VISIBLE);
            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothStatus.setChecked(true);
            } else {
                mBluetoothStatus.setChecked(false);
            }
        }
    }

    private void loadBondedDevices() {
        //遍历已绑定设备
        if (mBluetoothAdapter.getBondedDevices().size() > 0) {
            for (BluetoothDevice bluetoothDevice : mBluetoothAdapter.getBondedDevices()) {
//                if (bluetoothDevice.getBluetoothClass().getDeviceClass() == 1664) {
                    // NYear 蓝牙打印机
                    mBondedDevices.add(bluetoothDevice);
//                }
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

}

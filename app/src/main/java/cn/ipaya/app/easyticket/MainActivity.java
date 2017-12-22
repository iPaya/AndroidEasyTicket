package cn.ipaya.app.easyticket;

import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.ipaya.app.easyticket.activities.PrinterActivity;
import cn.ipaya.app.easyticket.activities.SettingsActivity;
import cn.ipaya.app.easyticket.activities.prints.FastPrintActivity;
import cn.ipaya.app.easyticket.base.BaseActivity;
import cn.ipaya.app.easyticket.messages.PrintMessageBuilder;
import cn.ipaya.app.easyticket.services.PrintService;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private Intent mPrintServiceIntent;
    private Messenger mPrintMessenger;
    private boolean mPrintServiceBound;
    private ServiceConnection mPrintServiceConnection = new PrintServiceConnection();

    @BindView(R.id.default_printer_name)
    TextView mDefaultPrinterNameTextView;

    @BindView(R.id.default_printer_address)
    TextView mDefaultPrinterAddressTextView;

    @BindView(R.id.view_no_default_printer)
    View mViewNoDefaultPrinter;

    @BindView(R.id.view_has_default_printer)
    View mViewHasDefaultPrinter;

    @BindView(R.id.default_printer_disconnected)
    Button mDefaultPrinterDisconnected;

    @BindView(R.id.default_printer_connected)
    TextView mDefaultPrinterConnected;

    private boolean isDefaultPrinterConnected = false;

    private String mDefaultPrinterName;
    private String mDefaultPrinterAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enableActionToolbar();

        mPrintServiceIntent = new Intent(this, PrintService.class);
        startService(mPrintServiceIntent);
        bindService(mPrintServiceIntent, mPrintServiceConnection, Context.BIND_AUTO_CREATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_fast_print:
                startActivity(new Intent(this, FastPrintActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_printer:
                startActivity(new Intent(this, PrinterActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(mPrintServiceIntent);
    }

    /**
     * 初始化默认打印机
     */
    private void initDefaultPrinter() {
        SharedPreferences sharedPreferences = mApplication.getSharedPreferences();
        mDefaultPrinterName = sharedPreferences.getString("default_printer__name", null);
        mDefaultPrinterAddress = sharedPreferences.getString("default_printer__address", null);

        if (mDefaultPrinterName != null) {
            // 已设置默认打印机
            mViewHasDefaultPrinter.setVisibility(View.VISIBLE);
            mViewNoDefaultPrinter.setVisibility(View.GONE);

            mDefaultPrinterNameTextView.setText(mDefaultPrinterName);
            mDefaultPrinterAddressTextView.setText(mDefaultPrinterAddress);

            Message msg = PrintMessageBuilder.buildConnectionStatusMessage(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mDefaultPrinterAddress));

            msg.replyTo = new Messenger(new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    Bundle data = msg.getData();
                    isDefaultPrinterConnected = data.getBoolean("isConnected");
                    if (isDefaultPrinterConnected) {
                        onDefaultPrinterConnected();
                    } else {
                        onDefaultPrinterDisconnected();
                    }
                    return true;
                }
            }));

            try {
                mPrintMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {
            // 未设置默认打印机
            mViewHasDefaultPrinter.setVisibility(View.GONE);
            mViewNoDefaultPrinter.setVisibility(View.VISIBLE);

            mDefaultPrinterNameTextView.setText(null);
            mDefaultPrinterAddressTextView.setText(null);
        }

    }

    @OnClick(R.id.default_printer_disconnected)
    void onClickDefaultPrinterDisconnected() {
        connectDefaultPrinter();
    }

    private void connectDefaultPrinter() {
        // 开始连接，更新 UI

        Message msg = PrintMessageBuilder.buildConnectMessage(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mDefaultPrinterAddress));

        msg.replyTo = new Messenger(new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle data = msg.getData();
                isDefaultPrinterConnected = data.getBoolean("isConnected");
                if (isDefaultPrinterConnected) {
                    onDefaultPrinterConnected();
                } else {
                    onDefaultPrinterDisconnected();
                }
                return true;
            }
        }));

        try {
            mPrintMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private class PrintServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mPrintMessenger = new Messenger(binder);
            mPrintServiceBound = true;
            onDefaultPrintServiceBonded();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPrintServiceBound = false;
        }
    }

    private void onDefaultPrinterConnected() {
        mDefaultPrinterDisconnected.setVisibility(View.GONE);
        mDefaultPrinterConnected.setVisibility(View.VISIBLE);
    }

    private void onDefaultPrinterDisconnected() {
        mDefaultPrinterDisconnected.setVisibility(View.VISIBLE);
        mDefaultPrinterConnected.setVisibility(View.GONE);
    }

    private void onDefaultPrintServiceBonded() {
        initDefaultPrinter();
    }
}

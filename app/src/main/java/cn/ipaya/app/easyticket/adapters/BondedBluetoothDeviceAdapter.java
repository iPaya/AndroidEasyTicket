package cn.ipaya.app.easyticket.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ipaya.app.easyticket.R;

/**
 * Created by Di on 2017/12/21 0021.
 */

public class BondedBluetoothDeviceAdapter extends BaseAdapter {

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.subtitle)
        TextView subtitle;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private Context mContext;
    private List<BluetoothDevice> mBondedDevices;
    private LayoutInflater mInflater;

    public BondedBluetoothDeviceAdapter(Context context, List<BluetoothDevice> devices) {
        mBondedDevices = devices;
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mBondedDevices.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return mBondedDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_bonded_bluetooth_device, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BluetoothDevice bluetoothDevice = getItem(position);
        holder.title.setText(bluetoothDevice.getName());
        holder.subtitle.setText(bluetoothDevice.getAddress());

        return convertView;
    }

}


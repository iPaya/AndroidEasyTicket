package cn.ipaya.app.easyticket.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import cn.ipaya.app.easyticket.R;
import cn.ipaya.app.easyticket.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @BindView(R.id.settings_list)
    ListView mSettings;
    List<HashMap<String, String>> mSettingsItems;
    SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        enableActionToolbar();
        showBackIcon();
        initSettingsItems();

        mAdapter = new SimpleAdapter(this, mSettingsItems, R.layout.simple_list_item_1, new String[]{"text"}, new int[]{android.R.id.text1});
        mSettings.setAdapter(mAdapter);
    }

    @OnItemClick(R.id.settings_list)
    void onClickSettingsItem(int position){
        HashMap<String, String> item = (HashMap<String, String>) mAdapter.getItem(position);
        switch (item.get("id")) {
            case "about":
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
                break;
            case "checkUpdate":
                startActivity(new Intent(SettingsActivity.this, CheckUpdateActivity.class));
                break;
        }
    }

    private void initSettingsItems() {
        mSettingsItems = new ArrayList<>();
        // 关于
        HashMap<String, String> aboutItem = new HashMap<String, String>();
        aboutItem.put("text", "关于" + getString(R.string.app_name));
        aboutItem.put("id", "about");
        mSettingsItems.add(aboutItem);
        // 检查新版本
        HashMap<String, String> checkUpdateItem = new HashMap<String, String>();
        checkUpdateItem.put("text", "检查新版本");
        checkUpdateItem.put("id", "checkUpdate");
        mSettingsItems.add(checkUpdateItem);
    }
}

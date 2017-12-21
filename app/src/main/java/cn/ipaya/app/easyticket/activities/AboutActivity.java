package cn.ipaya.app.easyticket.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.ipaya.app.easyticket.R;
import cn.ipaya.app.easyticket.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        enableActionToolbar();
        showBackIcon();
    }
}

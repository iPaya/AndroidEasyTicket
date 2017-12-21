package cn.ipaya.app.easyticket.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.ipaya.app.easyticket.R;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected boolean enabledToolbar = false;

    /**
     * 启用 Toolbar
     */
    protected void enableActionToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        enabledToolbar = true;
    }

    protected void showBackIcon() {
        if (!enabledToolbar) {
            enableActionToolbar();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

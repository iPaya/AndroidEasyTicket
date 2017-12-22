package cn.ipaya.app.easyticket.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import cn.ipaya.app.easyticket.Application;
import cn.ipaya.app.easyticket.R;

public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected boolean enabledToolbar = false;
    protected Application mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (Application) getApplication();
    }

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

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }
}

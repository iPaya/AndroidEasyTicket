package cn.ipaya.app.easyticket.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.ipaya.app.easyticket.R;
import cn.ipaya.app.easyticket.base.BaseActivity;

public class CheckUpdateActivity extends BaseActivity {

    TextView mCheckResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_update);

        enableActionToolbar();
        showBackIcon();

        mCheckResult = (TextView) findViewById(R.id.check_result);

        mCheckResult.setText("Not support now.");
    }
}

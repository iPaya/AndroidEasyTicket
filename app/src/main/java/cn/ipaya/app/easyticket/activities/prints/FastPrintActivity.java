package cn.ipaya.app.easyticket.activities.prints;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import cn.ipaya.app.easyticket.R;
import cn.ipaya.app.easyticket.base.BaseActivity;

public class FastPrintActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_print);
        enableActionToolbar();
        showBackIcon();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_fast_print, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId() == R.id.action_print);
        return super.onOptionsItemSelected(item);
    }
}

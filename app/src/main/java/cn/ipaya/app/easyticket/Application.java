package cn.ipaya.app.easyticket;

import android.content.SharedPreferences;

/**
 * Created by ZhangDi on 2017/12/22.
 */

public class Application extends android.app.Application {

    private SharedPreferences mSharedPreferences;

    public SharedPreferences getSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

}

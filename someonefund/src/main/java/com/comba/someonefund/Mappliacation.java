package com.comba.someonefund;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hss01248.dialog.MyActyManager;
import com.hss01248.dialog.StyledDialog;

/**
 * Created by chenhailin on 2017/7/14.
 */

public class Mappliacation  extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        StyledDialog.init(this);//ios 风格dialog
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                MyActyManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}

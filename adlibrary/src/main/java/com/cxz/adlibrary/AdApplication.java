package com.cxz.adlibrary;

import android.app.Application;
import android.util.DisplayMetrics;

import com.cxz.adlibrary.utils.DisplayUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by admin on 2018/6/13.
 */

public class AdApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);

        initDisplayOpinion();

    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenHeightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHeightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }
}

package tsm.citylink.com.addialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cxz.adlibrary.AdConstant;
import com.cxz.adlibrary.AdManager;
import com.cxz.adlibrary.bean.AdInfo;
import com.cxz.adlibrary.transformer.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<AdInfo> adInfos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        findViewById(R.id.btn_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdManager adManager = new AdManager(MainActivity.this,adInfos);
                adManager.setOverScreen(false)
                        .setPageTransformer(new DepthPageTransformer());
                adManager.setOnImageClickListener(new AdManager.OnImageClickListener() {
                    @Override
                    public void onImageClick(View view, AdInfo adInfo) {
                        Toast.makeText(MainActivity.this,adInfo.getActivityImg(),Toast.LENGTH_SHORT).show();
                    }
                });
                adManager.showAdDialog(AdConstant.ANIM_DOWN_TO_UP);
            }
        });
    }
    /**
     * 初始化数据
     */
    private void initData() {
        adInfos = new ArrayList<>();
        AdInfo adInfo = new AdInfo();
        adInfo.setActivityImg("https://raw.githubusercontent.com/yipianfengye/android-adDialog/master/images/testImage1.png");
        adInfos.add(adInfo);

        adInfo = new AdInfo();
        adInfo.setActivityImg("https://raw.githubusercontent.com/yipianfengye/android-adDialog/master/images/testImage2.png");
        adInfos.add(adInfo);

        adInfo = new AdInfo();
        adInfo.setActivityImg("https://github.com/bjchenxz/AdDialog/raw/master/images/ad01.png");
        adInfos.add(adInfo);
        adInfo = new AdInfo();
        adInfo.setActivityImg("https://github.com/bjchenxz/AdDialog/raw/master/images/ad02.png");
        adInfos.add(adInfo);
    }
}

package com.cxz.adlibrary.adapter;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.cxz.adlibrary.AdManager;
import com.cxz.adlibrary.R;
import com.cxz.adlibrary.bean.AdInfo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;

import java.util.List;

public class AdAdapter extends PagerAdapter {

    private List<AdInfo> adInfos;
    private Activity context;
    private AdManager.OnImageClickListener onImageClickListener;

    public AdAdapter(Activity context, List<AdInfo> adInfos) {
        this.context = context;
        this.adInfos = adInfos;
    }

    public void setOnImageClickListener(AdManager.OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    @Override
    public int getCount() {
        return adInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        AdInfo advInfo = adInfos.get(position);

        View rootView = context.getLayoutInflater().inflate(R.layout.item_viewpager, null);
        final ViewGroup errorView = rootView.findViewById(R.id.error_view);
        final ViewGroup loadingView = rootView.findViewById(R.id.loading_view);
        final SimpleDraweeView simpleDraweeView = rootView.findViewById(R.id.simpleDraweeView);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(rootView, params);
        simpleDraweeView.setTag(advInfo);

        // 图片的点击事件
        simpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdInfo info = (AdInfo) v.getTag();
                if (info != null && onImageClickListener != null) {
                    onImageClickListener.onImageClick(v, info);
                }
            }
        });

        ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                errorView.setVisibility(View.GONE);
                loadingView.setVisibility(View.GONE);
                simpleDraweeView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
                Log.i("##########", "onIntermediateImageSet()");
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                errorView.setVisibility(View.VISIBLE);
                loadingView.setVisibility(View.GONE);
                simpleDraweeView.setVisibility(View.GONE);
            }
        };

        Uri uri = Uri.parse(advInfo.getActivityImg());
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(uri)
                .build();
        simpleDraweeView.setController(controller);

        return rootView;
    }
}
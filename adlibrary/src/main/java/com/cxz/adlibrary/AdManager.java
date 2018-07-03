package com.cxz.adlibrary;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cxz.adlibrary.adapter.AdAdapter;
import com.cxz.adlibrary.bean.AdInfo;
import com.cxz.adlibrary.utils.AdDialogUtil;
import com.cxz.adlibrary.utils.DisplayUtil;
import com.flyco.pageindicator.indicator.FlycoPageIndicaor;

import java.util.List;

/**
 * 广告的管理类
 */
public class AdManager {

    private Activity mContext;
    private DisplayMetrics displayMetrics;

    private View mContentView;
    private ViewPager mViewPager;
    private RelativeLayout adRootContent;
    private AdAdapter mAdAdapter;
    /**
     * 底部指示器
     */
    private FlycoPageIndicaor mIndicator;
    /**
     * AdDialogUtil
     */
    private AdDialogUtil adDialogUtil;
    /**
     * 广告列表数据
     */
    private List<AdInfo> adInfos;
    /**
     * 广告弹窗距离两侧的距离-单位(dp)
     */
    private int padding = 44;
    /**
     * 广告弹窗的宽高比
     */
    private float widthPerHeight = 0.75f;

    /**
     * 弹窗背景是否透明
     */
    private boolean isAnimBackViewTransparent = false;
    /**
     * 弹窗是否可关闭
     */
    private boolean isDialogCloseable = true;
    /**
     * 弹窗关闭点击事件
     */
    private View.OnClickListener onCloseClickListener = null;
    /**
     * 设置弹窗背景颜色
     */
    private int backViewColor = Color.parseColor("#bf000000");
    /**
     * 弹性动画弹性参数
     */
    private double bounciness = AdConstant.BOUNCINESS;
    /**
     * 弹性动画速度参数
     */
    private double speed = AdConstant.SPEED;
    /**
     * viewPager滑动动画效果
     */
    private ViewPager.PageTransformer pageTransformer = null;
    /**
     * 是否覆盖全屏幕
     */
    private boolean isOverScreen = true;
    /**
     * 广告的点击事件
     */
    private OnImageClickListener onImageClickListener = null;

    public AdManager(Activity context, List<AdInfo> adInfos) {
        this.mContext = context;
        this.adInfos = adInfos;
        this.displayMetrics = new DisplayMetrics();
        this.mAdAdapter = new AdAdapter(context, adInfos);
        // 初始化布局
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.ad_dialog_content_layout, null);
        adRootContent = mContentView.findViewById(R.id.ad_root_content);
        mViewPager = mContentView.findViewById(R.id.viewPager);
        mIndicator = mContentView.findViewById(R.id.indicator);

        this.mViewPager.setAdapter(mAdAdapter);
        this.mIndicator.setViewPager(mViewPager);

    }

    /**
     * 开始执行显示广告弹窗的操作
     *
     * @param animType 动画类型
     */
    public void showAdDialog(final int animType) {

        mAdAdapter.setOnImageClickListener(onImageClickListener);
        if (pageTransformer != null) {
            mViewPager.setPageTransformer(true, pageTransformer);
        }
        isShowIndicator();

        adDialogUtil = AdDialogUtil.getInstance(mContext)
                .setAnimBackViewTransparent(isAnimBackViewTransparent)
                .setDialogCloseable(isDialogCloseable)
                .setDialogBackViewColor(backViewColor)
                .setOnCloseClickListener(onCloseClickListener)
                .setOverScreen(isOverScreen)
                .initView(mContentView);
        setRootContainerHeight();

        // 延迟1s展示，为了避免ImageLoader还为加载完缓存图片时就展示了弹窗的情况
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adDialogUtil.show(animType, bounciness, speed);
            }
        }, 1000);
    }

    /**
     * 开始执行销毁弹窗的操作
     */
    public void dismissAdDialog() {
        adDialogUtil.dismiss(AdConstant.ANIM_STOP_DEFAULT);
    }

    /**
     * 设置ContainerHeight
     */
    private void setRootContainerHeight() {
        mContext.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int totalPadding = DisplayUtil.dip2px(mContext, padding * 2);
        int width = widthPixels - totalPadding;
        final int height = (int) (width / widthPerHeight);
        ViewGroup.LayoutParams params = adRootContent.getLayoutParams();
        params.height = height;
    }

    /**
     * 根据页面数量，判断是否显示Indicator
     */
    private void isShowIndicator() {
        if (adInfos.size() > 1) {
            mIndicator.setVisibility(View.VISIBLE);
        } else {
            mIndicator.setVisibility(View.INVISIBLE);
        }
    }

    // ######################## 点击事件处理操作类 ########################

    /**
     * ViewPager每一项的单击事件的回调接口
     */
    public interface OnImageClickListener {
        void onImageClick(View view, AdInfo adInfo);
    }

    // ######################## get set方法 #########################

    /**
     * 设置弹窗距离屏幕左右两侧的距离
     *
     * @param padding padding
     * @return this
     */
    public AdManager setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    /**
     * 设置弹窗宽高比
     *
     * @param widthPerHeight 宽高比
     * @return this
     */
    public AdManager setWidthPerHeight(float widthPerHeight) {
        this.widthPerHeight = widthPerHeight;
        return this;
    }

    /**
     * 设置ViewPager Item点击事件
     *
     * @param onImageClickListener OnImageClickListener
     * @return this
     */
    public AdManager setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
        return this;
    }

    /**
     * 设置背景是否透明
     *
     * @param animBackViewTransparent 是否透明
     * @return this
     */
    public AdManager setAnimBackViewTransparent(boolean animBackViewTransparent) {
        isAnimBackViewTransparent = animBackViewTransparent;
        return this;
    }

    /**
     * 设置弹窗关闭按钮是否可见
     *
     * @param dialogCloseable dialogCloseable
     * @return this
     */
    public AdManager setDialogCloseable(boolean dialogCloseable) {
        isDialogCloseable = dialogCloseable;
        return this;
    }

    /**
     * 设置弹窗关闭按钮点击事件
     *
     * @param onCloseClickListener onCloseClickListener
     * @return this
     */
    public AdManager setOnCloseClickListener(View.OnClickListener onCloseClickListener) {
        this.onCloseClickListener = onCloseClickListener;
        return this;
    }

    /**
     * 设置弹窗背景颜色
     *
     * @param backViewColor backViewColor
     * @return this
     */
    public AdManager setBackViewColor(int backViewColor) {
        this.backViewColor = backViewColor;
        return this;
    }

    /**
     * 设置弹窗弹性动画弹性参数
     *
     * @param bounciness bounciness
     * @return this
     */
    public AdManager setBounciness(double bounciness) {
        this.bounciness = bounciness;
        return this;
    }

    /**
     * 设置弹窗弹性动画速度参数
     *
     * @param speed speed
     * @return this
     */
    public AdManager setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    /**
     * 设置ViewPager滑动动画效果
     *
     * @param pageTransformer pageTransformer
     * @return this
     */
    public AdManager setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this.pageTransformer = pageTransformer;
        return this;
    }

    /**
     * 设置弹窗背景是否覆盖全屏幕
     *
     * @param overScreen overScreen
     * @return this
     */
    public AdManager setOverScreen(boolean overScreen) {
        isOverScreen = overScreen;
        return this;
    }

}

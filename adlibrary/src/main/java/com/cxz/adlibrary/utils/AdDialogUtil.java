package com.cxz.adlibrary.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cxz.adlibrary.AdConstant;
import com.cxz.adlibrary.R;
import com.cxz.adlibrary.anim.AnimSpring;

/**
 * Created by admin on 2018/6/13.
 */

public class AdDialogUtil {

    static final String ANIM_DIALOG_TAG = "AnimDialogTag";
    private Activity context;
    private ViewGroup androidContentView;
    private View rootView;
    private RelativeLayout animBackView;
    private FrameLayout flContentContainer;
    private RelativeLayout animContainer;
    private ImageView ivClose;

    /**
     * 广告页是否显示
     */
    private boolean isShowing = false;
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
     * 弹窗背景是否覆盖全屏幕
     */
    private boolean isOverScreen = true;

    private AdDialogUtil(Activity context) {
        this.context = context;
    }

    public static AdDialogUtil getInstance(Activity context) {
        return new AdDialogUtil(context);
    }

    /**
     * 初始化弹窗中的界面，添加传入的customView界面，并监听关闭按钮点击事件
     *
     * @param customView view
     * @return this
     */
    public AdDialogUtil initView(final View customView) {
        if (isOverScreen) {
            androidContentView = (ViewGroup) context.getWindow().getDecorView();
        } else {
            androidContentView = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        }
        rootView = LayoutInflater.from(context).inflate(R.layout.ad_dialog_layout, null);
        rootView.setTag(ANIM_DIALOG_TAG);

        animBackView = rootView.findViewById(R.id.anim_back_view);
        animContainer = rootView.findViewById(R.id.anim_container);
        animContainer.setVisibility(View.INVISIBLE);
        flContentContainer = rootView.findViewById(R.id.fl_content_container);
        ViewGroup.LayoutParams contentParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        flContentContainer.addView(customView, contentParams);
        ivClose = rootView.findViewById(R.id.iv_close);

        return this;
    }

    /**
     * 开始执行弹窗的展示动画
     *
     * @param animType 动画的类型
     */
    public void show(int animType, double bounciness, double speed) {
        // 判断是否设置背景透明
        if (isAnimBackViewTransparent) {
            backViewColor = Color.TRANSPARENT;
        }
        // 判断背景颜色
        animBackView.setBackgroundColor(backViewColor);

        // 判断弹窗是否可关闭
        if (isDialogCloseable) {
            ivClose.setVisibility(View.VISIBLE);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onCloseClickListener != null) {
                        onCloseClickListener.onClick(view);
                    }
                    dismiss(AdConstant.ANIM_STOP_TRANSPARENT);
                }
            });
        } else {
            ivClose.setVisibility(View.GONE);
        }
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        androidContentView.addView(rootView, params);
        AnimSpring.getInstance().startAnim(animType, animContainer, bounciness, speed);
        isShowing = true;
    }

    /**
     * 开始执行关闭动画的操作
     */
    public void dismiss(int animType) {
        isShowing = false;
        AnimSpring.getInstance().stopAnim(animType, this);
    }


    /**
     * 设置背景组件颜色
     *
     * @param color color
     * @return this
     */
    public AdDialogUtil setDialogBackViewColor(int color) {
        backViewColor = color;
        return this;
    }

    /**
     * 设置弹窗关闭按钮是否可见
     *
     * @param dialogCloseable dialogCloseable
     * @return this
     */
    public AdDialogUtil setDialogCloseable(boolean dialogCloseable) {
        isDialogCloseable = dialogCloseable;
        return this;
    }

    /**
     * 设置关闭按钮的点击事件
     *
     * @param onCloseClickListener onCloseClickListener
     * @return this
     */
    public AdDialogUtil setOnCloseClickListener(View.OnClickListener onCloseClickListener) {
        this.onCloseClickListener = onCloseClickListener;
        return this;
    }

    /**
     * 设置背景是否透明
     *
     * @param animBackViewTransparent animBackViewTransparent
     * @return this
     */
    public AdDialogUtil setAnimBackViewTransparent(boolean animBackViewTransparent) {
        isAnimBackViewTransparent = animBackViewTransparent;
        return this;
    }

    /**
     * 设置弹窗背景是否覆盖全屏幕
     *
     * @param overScreen overScreen
     * @return this
     */
    public AdDialogUtil setOverScreen(boolean overScreen) {
        isOverScreen = overScreen;
        return this;
    }

    // ################### get方法 ####################

    public RelativeLayout getAnimContainer() {
        return animContainer;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public ViewGroup getAndroidContentView() {
        return androidContentView;
    }

    public View getRootView() {
        return rootView;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

}

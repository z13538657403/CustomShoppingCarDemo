package com.test.zhangtao.activitytest.animutils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nineoldandroids.animation.Animator;
import com.test.zhangtao.activitytest.R;

/**
 * Created by zhangtao on 16/12/31.
 */

public class GoodsAnimUtil
{
    /** 动画层 */
    private static ViewGroup anim_mask_layout;
    private static Activity mActivity;
    private static View mImgCar;
    private static OnEndAnimListener mEndAnimListener;

    /** 定义动画结束后的接口 */
    public interface  OnEndAnimListener
    {
        void onEndAnim();
    }

    public static void setOnEndAnimListener(OnEndAnimListener listener)
    {
        mEndAnimListener = listener;
    }

    public static void setAnim(Activity activity , View imgPhoto , View imgCar)
    {
        mActivity = activity;
        mImgCar = imgCar;
        //一个整形数组，用来存储按钮的在屏幕的X，y坐标
        int[] start_location = new int[2];
        //这是获取购买按钮的在屏幕的x，y坐标（这里也是动画开始的坐标）
        imgPhoto.getLocationInWindow(start_location);
        //buyImg是动画的图片，我的是一个小球（R.drawable.sign）
        ImageView buyImg = new ImageView(mActivity);
        //设置buyImg的图片
        buyImg.setImageResource(R.mipmap.aii);
        //开始执行动画
        startAnim(buyImg , start_location);
    }

    private static void startAnim(final ImageView buyImg, int[] start_location)
    {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(buyImg);
        addViewToAnimLayout(buyImg , start_location);
        int[] end_location = new int[2];
        mImgCar.getLocationInWindow(end_location);

        int endY = end_location[1] - start_location[1];
        int endX = end_location[0] - start_location[0] + (mImgCar.getMeasuredWidth() / 2);

        TranslateAnimation translateAnimationX = new TranslateAnimation(0 , endX , 0 , 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);
        translateAnimationX.setFillAfter(true);
        TranslateAnimation translateAnimationY = new TranslateAnimation(0 , 0 , 0 , endY);
        translateAnimationY.setInterpolator(new LinearInterpolator());
        translateAnimationY.setRepeatCount(0);
        translateAnimationY.setFillAfter(true);
        final AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationX);
        set.addAnimation(translateAnimationY);
        set.setDuration(800);
        buyImg.startAnimation(set);
        //动画监听事件
        set.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
                buyImg.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                buyImg.setVisibility(View.GONE);
                anim_mask_layout.removeAllViews();
                YoYo.with(Techniques.Bounce).withListener(new Animator.AnimatorListener()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        mEndAnimListener.onEndAnim();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation)
                    {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                }).interpolate(new BounceInterpolator()).duration(400).playOn(mImgCar);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
        });
    }

    private static ViewGroup createAnimLayout()
    {
        ViewGroup rootView = (ViewGroup) mActivity.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mActivity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private static void addViewToAnimLayout(ImageView buyImg, int[] start_location)
    {
        int x = start_location[0];
        int y = start_location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT ,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        buyImg.setLayoutParams(lp);
    }
}

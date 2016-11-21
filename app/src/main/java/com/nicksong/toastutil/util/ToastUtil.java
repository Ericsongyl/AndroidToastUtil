package com.nicksong.toastutil.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nicksong.toastutil.R;

/**
 * 作者：nicksong
 * 创建时间：2016/11/21
 * 功能描述:自定义toast样式、显示时长
 */

public class ToastUtil {

    private Toast mToast;
    private TextView mTextView;
    private TimeCount timeCount;
    private String message;
    private Handler mHandler = new Handler();
    private boolean canceled = true;

    public ToastUtil(Context context, int layoutId, String msg) {
        message = msg;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        View view = inflater.inflate(layoutId, null);
        //自定义toast文本
        mTextView = (TextView)view.findViewById(R.id.toast_msg);
        mTextView.setText(msg);
        Log.i("ToastUtil", "Toast start...");
        if (mToast == null) {
            mToast = new Toast(context);
            Log.i("ToastUtil", "Toast create...");
        }
        //设置toast居中显示
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.setView(view);
    }

    /**
     * 自定义居中显示toast
     */
    public void show() {
        mToast.show();
        Log.i("ToastUtil", "Toast show...");
    }

    /**
     * 自定义时长、居中显示toast
     * @param duration
     */
    public void show(int duration) {
        timeCount = new TimeCount(duration, 1000);
        Log.i("ToastUtil", "Toast show...");
        if (canceled) {
            timeCount.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏toast
     */
    private void hide() {
        if (mToast != null) {
            mToast.cancel();
        }
        canceled = true;
        Log.i("ToastUtil", "Toast that customed duration hide...");
    }

    private void showUntilCancel() {
        if (canceled) { //如果已经取消显示，就直接return
            return;
        }
        mToast.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i("ToastUtil", "Toast showUntilCancel...");
                showUntilCancel();
            }
        }, Toast.LENGTH_LONG);
    }

    /**
     *  自定义计时器
     */
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //millisInFuture总计时长，countDownInterval时间间隔(一般为1000ms)
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText(message + ": " + millisUntilFinished / 1000 + "s后消失");
        }

        @Override
        public void onFinish() {
            hide();
        }
    }
}

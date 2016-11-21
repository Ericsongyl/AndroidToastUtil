# AndroidToastUtil
Android：实现Toast自定义样式（包括：自定义位置、带图片等）、自定义显示时长（包括可用系统时长、可自定义）

鉴于系统toast，一般都是黑色背景且位于界面底部，我们看到有些app弹出的toast，有的在界面中间、有的在界面顶部，还有的是带图片的，那是怎么实现的呢？

-------------------

## 了解系统toast类有哪些方法

 - setView( )：设置toast视图，也就是通过layout布局来控制toast显示不同的视图。 
 - setGravity( )：设置toast显示位置。
 - setDuration( )：设置toast显示的时长，注意：此方法设置自定义时长不起作用，需用其他方法实现。
 

## 自定义Toast类ToastUtil：包括自定义样式、自定义显示时长

```
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

```

##具体使用方法

```
package com.nicksong.toastutil;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nicksong.toastutil.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Context context = MainActivity.this;
    private Button btDefaultToast;
    private Button btCenterToast;
    private Button btTopToast;
    private Button btCenterImgToast1;
    private Button btCenterImgToast2;
    private Button btCustomDurationToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        btDefaultToast = (Button)findViewById(R.id.bt_default_toast);
        btCenterToast = (Button)findViewById(R.id.bt_center_toast);
        btTopToast = (Button)findViewById(R.id.bt_top_toast);
        btCenterImgToast1 = (Button)findViewById(R.id.bt_center_img_toast1);
        btCenterImgToast2 = (Button)findViewById(R.id.bt_center_img_toast2);
        btCustomDurationToast = (Button)findViewById(R.id.bt_custom_duration_toast);
    }

    private void initListener() {
        btDefaultToast.setOnClickListener(this);
        btCenterToast.setOnClickListener(this);
        btTopToast.setOnClickListener(this);
        btCenterImgToast1.setOnClickListener(this);
        btCenterImgToast2.setOnClickListener(this);
        btCustomDurationToast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_default_toast:
                Toast.makeText(context, "默认toast", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_center_toast:
                Toast toast = Toast.makeText(context, "自定义居中toast", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                break;
            case R.id.bt_top_toast:
                Toast toast1 = Toast.makeText(context, "自定义顶部toast", Toast.LENGTH_SHORT);
                toast1.setGravity(Gravity.TOP, 0, 0);
                toast1.show();
                break;
            case R.id.bt_center_img_toast1:
                ToastUtil toastUtil = new ToastUtil(context, R.layout.toast_center, "完全自定义居中toast 1");
                toastUtil.show();
                break;
            case R.id.bt_center_img_toast2:
                ToastUtil toastUtil2 = new ToastUtil(context, R.layout.toast_center_horizontal, "完全自定义居中toast 2");
                toastUtil2.show();
                break;
            case R.id.bt_custom_duration_toast:
                ToastUtil toastUtil3 = new ToastUtil(context, R.layout.toast_center_horizontal, "带图片自定义时长toast");
                toastUtil3.show(5000);
                break;
            default:
                break;
        }
    }
}

```

##自定义样式和时长的Toast效果图
![GIF.gif](https://github.com/Ericsongyl/AndroidToastUtil/blob/master/GIF.gif)

##项目源码
[https://github.com/Ericsongyl/AndroidToastUtil](https://github.com/Ericsongyl/AndroidToastUtil)

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

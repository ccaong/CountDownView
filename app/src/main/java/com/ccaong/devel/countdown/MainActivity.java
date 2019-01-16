package com.ccaong.devel.countdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ccaong.devel.countdownview.CountDownConstant;
import com.ccaong.devel.countdownview.CountDownView;


/**
 * @author devel
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {


    CountDownView countDownView;
    CountDownView countDownView1;
    CountDownView countDownView2;
    CountDownView countDownView3;
    CountDownView countDownView4;
    CountDownView countDownView5;
    CountDownView countDownView6;
    CountDownView countDownView7;
    CountDownView countDownView8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    public void initView() {

        countDownView = findViewById(R.id.tv_time);
        countDownView1 = findViewById(R.id.count_down_view_1);
        countDownView2 = findViewById(R.id.count_down_view_2);
        countDownView3 = findViewById(R.id.count_down_view_3);
        countDownView4 = findViewById(R.id.count_down_view_4);
        countDownView5 = findViewById(R.id.count_down_view_5);
        countDownView6 = findViewById(R.id.count_down_view_6);
        countDownView7 = findViewById(R.id.count_down_view_7);
        countDownView8 = findViewById(R.id.count_down_view_8);

    }

    public void initData() {

        //倒计时默认  倒计时90秒
        countDownView.setCountDownTime(90L, CountDownConstant.CountDownTime.SECOND);
        countDownView.start(MainActivity.this);


        //倒计时1  倒计时100小时
        countDownView1.setStyle(true, true, true, true);
        countDownView1.setCountDownTime(10L, CountDownConstant.CountDownTime.HOUR);
        countDownView1.start(this);

        //倒计时2   倒计时10分钟
        countDownView2.setStyle(false, true, true, true);
        countDownView2.setCountDownTime(10L, CountDownConstant.CountDownTime.MINUTE);
        countDownView2.start(this);

        //倒计时3   倒计时10分钟
        countDownView3.setStyle(false, true, true, false);
        countDownView3.setCountDownTime(10L, CountDownConstant.CountDownTime.MINUTE);
        countDownView3.start(this);

        //倒计时4   倒计时2天  中文分隔符
        countDownView4.setStyle(true, true, true, true);
        countDownView4.setCountDownTime(2L, CountDownConstant.CountDownTime.DATE);
        countDownView4.setSeparatorStyle(CountDownConstant.CountDownStyle.ZN_CH);
        countDownView4.start(this);


        //倒计时5，时间间隔为2秒钟
        countDownView5.setStyle(true, true, true, true);
        countDownView5.setCountDownTime(10L, CountDownConstant.CountDownTime.MINUTE);
        countDownView5.setTimeInterval(2);
        countDownView5.start(this);


        //倒计时6，时间不足10秒时回调
        countDownView6.setCountDownTime(15L, CountDownConstant.CountDownTime.SECOND);
        countDownView6.start(this);
        countDownView6.setShowRemindTime(10L, CountDownConstant.CountDownTime.SECOND);
        countDownView6.setmTimesUpListener(new CountDownView.TimesUpListener() {
            @Override
            public void timesUp() {
                Toast.makeText(MainActivity.this, "倒计时6时间到", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void timeRemind() {
                Toast.makeText(MainActivity.this, "倒计时6时间不足10秒", Toast.LENGTH_SHORT).show();
            }
        });

        //倒计时7，隐藏文字
        countDownView7.setCountDownTime(90L, CountDownConstant.CountDownTime.SECOND);
        countDownView7.showTitle(false);
        countDownView7.start(this);

        countDownView8.setCountDownTime(20L, CountDownConstant.CountDownTime.SECOND);
        countDownView8.getTvTitle().setText("可以得到这个TextView");
        countDownView8.getTvTitle().setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        countDownView8.start(this);
        countDownView8.setmTimesUpListener(new CountDownView.TimesUpListener() {
            @Override
            public void timesUp() {
                countDownView8.getTvCountTime().setText("时间到！");
                countDownView8.getTvCountTime().setTextColor(getResources().getColor(R.color.colorAccent));

            }

            @Override
            public void timeRemind() {

            }
        });
    }
}

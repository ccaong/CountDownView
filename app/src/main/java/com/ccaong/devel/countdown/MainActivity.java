package com.ccaong.devel.countdown;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ccaong.devel.countdownview.CountDownConstant;
import com.ccaong.devel.countdownview.CountDownView;

/**
 * @author devel
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CountDownView countDownView = findViewById(R.id.tv_time);

        countDownView.setStyle(true, true, true, true);
        countDownView.setCountDownTime(65L, CountDownConstant.CountDownTime.SECOND);
        countDownView.setShowRemindTime(20L, CountDownConstant.CountDownTime.SECOND);
        countDownView.setTimeInterval(1);
        countDownView.start(this);
        countDownView.setSeparatorStyle(CountDownConstant.CountDownStyle.EN);

        countDownView.setmTimesUpListener(new CountDownView.TimesUpListener() {
            @Override
            public void timesUp() {
                Toast.makeText(MainActivity.this, "时间到", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void timeRemind() {
                Toast.makeText(MainActivity.this, "时间不足20秒", Toast.LENGTH_SHORT).show();
            }
        });

        countDownView.getTvTitle().setTextColor(getResources().getColor(R.color.colorPrimaryDark));

    }
}

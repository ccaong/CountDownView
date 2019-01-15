package com.ccaong.devel.countdownview;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author devel
 */
public class CountDownView extends FrameLayout {

    private final static int MSG_TIME_INTERVAL = 1;
    private final static int MSG_ONE_SECOND = 2;

    private Activity activity;
    private Context mContext;
    /**
     * 开始的时间
     */
    private Long mStartTime;

    /**
     * 间隔时间
     */
    private int mTimeInterval;

    /**
     * 倒计时不足多少时提醒
     */
    private Long mRemindTime;
    /**
     * 显示 天
     */
    private int mTimeDD;
    /**
     * 显示  小时
     */
    private int mTimeHH;
    /**
     * 显示  分钟
     */
    private int mTimeMM;
    /**
     * 显示  秒
     */
    private int mTimeSS;

    private Boolean showDay = false;
    private Boolean showHour = false;
    private Boolean showMinute = true;
    private Boolean showSecond = true;

    private Boolean showRemind = false;

    private int showSeparatorStyle;
    private TimesUpListener mTimesUpListener;
    private TextView tvCountTime;
    private TextView tvTitle;


    public CountDownView(Context context) {
        this(context, null);
    }


    public CountDownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (null == activity) {
                //走到了onDestroy,则不再进行后续消息处理
                return false;
            }
            if (activity.isFinishing()) {
                //Activity正在停止，则不再后续处理
                return false;
            }

            if (msg.what == MSG_TIME_INTERVAL) {
                if (mStartTime <= 0) {
                    mTimesUpListener.timesUp();
                } else {
                    if (mTimeSS - mTimeInterval < 0) {
                        mTimeSS = 60 + (mTimeSS - mTimeInterval);
                        if (mTimeMM == 0) {
                            if (mTimeHH == 0) {
                                mTimeDD--;
                                mTimeHH = 23;
                                mTimeMM = 59;
                            } else {
                                mTimeHH--;
                                mTimeMM = 59;
                            }
                        } else {
                            mTimeMM--;
                        }
                    } else {
                        mTimeSS = mTimeSS - mTimeInterval;
                    }
                    if (mStartTime <= mRemindTime) {
                        if (showRemind) {
                            showRemind = false;
                            mTimesUpListener.timeRemind();
                            if (mTimeInterval != 1) {
                                mHandler.sendEmptyMessageDelayed(MSG_ONE_SECOND, 1 * 1000);
                            }
                        }
                    }
                    mStartTime = mStartTime - mTimeInterval * 1000;
                    mHandler.sendEmptyMessageDelayed(MSG_TIME_INTERVAL, mTimeInterval * 1000);
                    setTime();
                }
            } else if (msg.what == MSG_ONE_SECOND) {
                if (mStartTime <= 0) {
                    mTimesUpListener.timesUp();
                    setTime();

                    mHandler.removeMessages(MSG_TIME_INTERVAL);
                    mHandler.removeMessages(MSG_ONE_SECOND);
                    mHandler = null;
                } else {
                    mHandler.sendEmptyMessageDelayed(MSG_ONE_SECOND, 1 * 1000);
                }
            }
            return false;
        }
    });


    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.time, null);
        tvTitle = view.findViewById(R.id.textView);
        tvCountTime = view.findViewById(R.id.tv_time);
        mStartTime = 60 * 1000L;
        mRemindTime = 10L;
        mTimeInterval = 1;
        mTimeDD = 0;
        mTimeHH = 0;
        mTimeMM = 1;
        mTimeSS = 0;
        showSeparatorStyle = 0;
        this.addView(view);
    }

    public void showTitle(Boolean show) {
        if (show) {
            tvTitle.setVisibility(VISIBLE);
        } else {
            tvTitle.setVisibility(GONE);
        }
    }

    public void setTvTitle(String title) {
        if (title != null) {
            tvTitle.setText(title);
            tvTitle.setVisibility(VISIBLE);
        }
    }

    /**
     * 设置倒计时开始的时间
     *
     * @param time          时间
     * @param countDownTime 时间格式，天，小时，分钟，秒
     */
    public void setCountDownTime(Long time, CountDownConstant.CountDownTime countDownTime) {
        if (countDownTime == CountDownConstant.CountDownTime.DATE) {
            this.mStartTime = time * 24 * 60 * 60 * 1000;
        } else if (countDownTime == CountDownConstant.CountDownTime.HOUR) {
            this.mStartTime = time * 60 * 60 * 1000;
        } else if (countDownTime == CountDownConstant.CountDownTime.MINUTE) {
            this.mStartTime = time * 60 * 1000;
        } else if (countDownTime == CountDownConstant.CountDownTime.SECOND) {
            this.mStartTime = time * 1000;
        }

        getExamDuration(mStartTime);

    }


    private void getExamDuration(Long time) {
        try {
            long days = time / (1000 * 60 * 60 * 24);
            long hours = (time - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (time - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (time - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / (1000);
            System.out.println("" + days + "天" + hours + "小时" + minutes + "分");

            this.mTimeDD = (int) days;
            this.mTimeHH = (int) hours;
            this.mTimeMM = (int) minutes;
            this.mTimeSS = (int) seconds;

            if (!showDay) {
                mTimeHH = mTimeDD * 24 + mTimeHH;
            }
            if (!showHour) {
                mTimeMM = mTimeHH * 60 + mTimeMM;
            }

        } catch (Exception e) {

        }
    }

    /**
     * 设置显示样式
     *
     * @param day  是否显示天，
     * @param Hour 是否显示小时
     *             。。。。。。
     */
    public void setStyle(Boolean day, Boolean Hour, Boolean minute, Boolean second) {
        this.showDay = day;
        this.showHour = Hour;
        this.showMinute = minute;
        this.showSecond = second;

    }

    /**
     * 设置分隔符的样式，有两个选项：ZN_CH->1天00时15分01秒和 EN-> 1:00:15:01
     *
     * @param countDownStyle
     */
    public void setSeparatorStyle(CountDownConstant.CountDownStyle countDownStyle) {
        if (countDownStyle == CountDownConstant.CountDownStyle.EN) {
            this.showSeparatorStyle = 0;
        } else {
            this.showSeparatorStyle = 1;
        }
    }

    /**
     * 设置倒计时提醒的时间
     *
     * @param time
     * @param countDownTime
     */
    public void setShowRemindTime(Long time, CountDownConstant.CountDownTime countDownTime) {
        if (countDownTime == CountDownConstant.CountDownTime.DATE) {
            this.mRemindTime = time * 24 * 60 * 60 * 1000;
        } else if (countDownTime == CountDownConstant.CountDownTime.HOUR) {
            this.mRemindTime = time * 60 * 60 * 1000;
        } else if (countDownTime == CountDownConstant.CountDownTime.MINUTE) {
            this.mRemindTime = time * 60 * 1000;
        } else if (countDownTime == CountDownConstant.CountDownTime.SECOND) {
            this.mRemindTime = time * 1000;
        }

        showRemind = true;
    }


    /**
     * 设置倒计时的时间间隔  单位秒
     *
     * @param timeInterval
     */
    public void setTimeInterval(int timeInterval) {
        this.mTimeInterval = timeInterval;
    }


    public void start(Activity activity) {
        this.activity = activity;
        mHandler.sendEmptyMessageDelayed(MSG_TIME_INTERVAL, mTimeInterval * 1000);
    }

    /**
     * 停止
     */
    public void stop() {
    }


    /**
     * 暂停
     */
    public void pause() {

    }


    private void setTime() {
        String strTime = "";
        String[] styleEN = {":", ":", ":", ""};
        String[] styleZN_CH = {"天", "时", "分", "秒"};
        String[] useStyle;
        if (showSeparatorStyle == 0) {
            useStyle = styleEN;
        } else {
            useStyle = styleZN_CH;
        }

        if (mStartTime > 0) {
            if (showDay) {
                strTime = strTime + mTimeDD;
            }
            if (showHour) {
                strTime = strTime + useStyle[0] + (mTimeHH < 10 ? "0" + mTimeHH : mTimeHH);
            }
            if (showMinute) {
                strTime = strTime + useStyle[1] + (mTimeMM < 10 ? "0" + mTimeMM : mTimeMM);
            }
            if (showSecond) {
                strTime = strTime + useStyle[2] + (mTimeSS < 10 ? "0" + mTimeSS : mTimeSS) + useStyle[3];
            }

            if (!showDay) {
                strTime = strTime.substring(1, strTime.length());
            }
        } else {
            if (showDay) {
                strTime = strTime + "0";
            }
            if (showHour) {
                strTime = strTime + useStyle[0] + "0";
            }
            if (showMinute) {
                strTime = strTime + useStyle[1] + "00";
            }
            if (showSecond) {
                strTime = strTime + useStyle[2] + "00" + useStyle[3];
            }

            if (!showDay) {
                strTime = strTime.substring(1, strTime.length());
            }
        }


        tvCountTime.setText(strTime);
    }

    /**
     * 获取倒计时时间的文字，可以获取到然后自定义文字的大小，字体，颜色等
     *
     * @return
     */
    public TextView getTvCountTime() {
        return tvCountTime;
    }

    /**
     * 获取“倒计时”文字
     *
     * @return
     */
    public TextView getTvTitle() {
        return tvTitle;
    }

    public interface TimesUpListener {
        /**
         * 时间到
         */
        void timesUp();

        /**
         * 时间不足某个值时，回调
         */
        void timeRemind();
    }

    public void setmTimesUpListener(TimesUpListener timesUpListener) {
        this.mTimesUpListener = timesUpListener;
    }
}

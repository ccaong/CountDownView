# CountDownView
一款针对Android平台的倒计时插件。插件十分简单，是基于Handler来实现的倒计时功能。适合懒得自己去写倒计时的逻辑的用户。


[![](https://jitpack.io/v/ccaong/CountDownView.svg)](https://jitpack.io/#ccaong/CountDownView)

## 目录
-[项目特点](#项目特点)<br>
-[集成方式](#集成方式)<br>
-[如何使用](#如何使用)<br>
-[更新日志](#更新日志)<br>
-[更新计划](#更新计划)<br>

# 功能特点

* 1.简单，使用十分简单，只需要输入倒计时的开始时间，然后start()即可
* 2.样式较多，可配置性强,

![image](https://github.com/ccaong/CountDownView/tree/master/image/1.jpg)
![image](https://github.com/ccaong/CountDownView/tree/master/image/2.jpg)
![image](https://github.com/ccaong/CountDownView/tree/master/image/3.jpg)
![image](https://github.com/ccaong/CountDownView/tree/master/image/4.jpg)

## 集成方式

方式一 compile引入

```
dependencies {
    implementation 'com.github.ccaong:CountDownView:1.1.0'
}

```

项目根目录build.gradle加入

```
allprojects {
   repositories {
      jcenter()
      maven { url 'https://jitpack.io' }
   }
}
```

## 如何使用


******在布局中添加View******       
```
    <com.ccaong.devel.countdownview.CountDownView
        android:id="@+id/count_down_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
       
```

******在代码中初始化设置并开始倒计时******     
```
CountDownView countDownView = findViewById(R.id.count_down_view);
//设置倒计时开始时间，第一个参数为时间，第二个参数为时间格式，可以选择天，小时，分钟，秒（下面的配置为90秒）
countDownView.setCountDownTime(90L, CountDownConstant.CountDownTime.SECOND);
//开始倒计时，需要传入Activity，不可以传Fragment或者Context
countDownView.start(MainActivity.this)
       
```

******其他个性化设置******    
```
//设置倒计时开始时间，第一个参数为时间，第二个参数为时间格式，可以选择天，小时，分钟，秒（下面的配置为90秒）
countDownView.setCountDownTime(90L, CountDownConstant.CountDownTime.SECOND);
//设置倒计时显示的格式，
//第一个参数为是否显示天，第二个参数为是否显示小时，第三个参数为是否显示分秒，第四个参数为是否显示秒（如不设置，默认为只显示分秒和秒）
countDownView.setStyle(true, true, true, true);
//设置分隔符 目前有两种分隔符可选，一种是EN，显示为：倒计时1:02:03:04(如不设置，默认为EN)
//另一种是ZN_CH，显示为：倒计时1天02小时30分04秒
countDownView.setSeparatorStyle(CountDownConstant.CountDownStyle.ZN_CH);
//设置时间间隔，即每几秒刷新一次倒计时，如不设置，默认为1秒，下面设置为2秒更新一次
countDownView.setTimeInterval(2);

//设置倒计时不足多久时回调接口，参数同设置开始时间的方法
countDownView6.setShowRemindTime(10L, CountDownConstant.CountDownTime.SECOND);
//倒计时不足多久和倒计时时间到的接口
countDownView6.setmTimesUpListener(new CountDownView.TimesUpListener() {
     @Override
     public void timesUp() {
           //倒计时时间到
           Toast.makeText(MainActivity.this, "时间到", Toast.LENGTH_SHORT).show();
     }

     @Override
     public void timeRemind() {
           //倒计时已不足多久
           Toast.makeText(MainActivity.this, "倒计时时间不足10秒", Toast.LENGTH_SHORT).show();
      }
});
//隐藏"倒计时"文字
countDownView.showTitle(false);
//设置倒计时文字
countDownView.setTvTitle("倒计时文字");

//获取"倒计时"TextView  获取后可以对该TextView任意修改
 countDownView.getTvTitle();
 //获取倒计时TvxtView
 countDownView.getTvCountTime();
 
 //开始倒计时，需要传入Activity，不可以传Fragment或者Context
 countDownView.start(MainActivity.this)
 
```

## 更新日志

# 当前版本：
* v1.1.0
* 1.添加Readme

# 历史版本：
* v1.0.1
* 1.修复不设置提醒时间会崩溃的bug
* 2.将部分方法设置为私有属性

* v1.0.0
* 1.第一次上传


## 更新计划
* 1.添加样式
* 2.添加暂停和停止功能
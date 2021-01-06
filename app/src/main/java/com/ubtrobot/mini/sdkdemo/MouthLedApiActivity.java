package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.ubtrobot.commons.Priority;
import com.ubtrobot.lib.mouthledapi.MouthLedApi;

/**
 * MouthLedApi的测试方法
 */

public class MouthLedApiActivity extends Activity {
  private static final String TAG = DemoApp.DEBUG_TAG;
  private MouthLedApi mouthLedApi;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.mouth_led_api_layout);

    initRobot();
  }

  /**
   * 初始化接口类实例
   */
  private void initRobot() {
    mouthLedApi = MouthLedApi.get();
  }

  /**
   * 开灯
   */
  public void turnOn(View view) {
    mouthLedApi.turnOn(Priority.NORMAL, null);
    Log.i(TAG, "turnOn接口调用成功!");
  }

  /**
   * 关灯
   */
  public void turnOff(View view) {
    mouthLedApi.turnOff(Priority.NORMAL, null);
    Log.i(TAG, "turnOff接口调用成功!");
  }

  /**
   * 开灯,并指定颜色和时长
   */
  public void startNormalModel(View view) {
    mouthLedApi.startNormalModel(Color.argb(0, 255, 0, 0), 10000, Priority.NORMAL, null);
    Log.i(TAG, "startNormalModel接口调用成功!");
  }

  /**
   * 开灯,呼吸效果
   */
  public void startBreathModelOnce(View view) {
    mouthLedApi.startBreathModel(Color.argb(0, 0, 0, 255), 2000, 20000, Priority.NORMAL, null);
    Log.i(TAG, "startBreathModel接口调用成功!");
  }

  /**
   * 开灯,呼吸效果
   */
  public void startBreathModel(View view) {
    mouthLedApi.startBreathModel(Color.argb(0, 0, 255, 0), 10000, Priority.NORMAL, null);
    Log.i(TAG, "startNormalModel接口调用成功!");
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}

package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ubtrobot.commons.Priority;
import com.ubtrobot.express.ExpressApi;
import com.ubtrobot.express.listeners.AnimationListener;
import com.ubtrobot.express.protos.Express;
import java.util.List;

/**
 * ExpressApi的测试方法
 */

public class ExpressApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
  private ExpressApi expressApi;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.express_api_layout);

    initRobot();
  }

  /**
   * 初始化接口类实例
   */
  private void initRobot() {
    expressApi = ExpressApi.get();
  }

  /**
   * 获取表情列表
   */
  public void getExpressList(View view) {
    List<Express.ExpressInfo> expressList = expressApi.getExpressList();
    for (Express.ExpressInfo expressInfo : expressList) {
      Log.i(TAG, expressInfo.toString());
    }
    Log.i(TAG, "getExpressList接口调用成功！");
  }

  /**
   * 获取自定义表情列表, 自定义表情资源存放在/sdcard/customize/expresss目录
   *
   * 系统版本需v0.0.3以上
   */
  public void getCustomizeExpressList(View view) {
    List<Express.ExpressInfo> expressList = expressApi.getCustomizeExpressList();
    for (Express.ExpressInfo expressInfo : expressList) {
      Log.i(TAG, expressInfo.toString());
    }
    Log.i(TAG, "getExpressList接口调用成功！");
  }

  /**
   * 执行表情
   */
  public void doExpress(View view) {
    expressApi.doExpress("wakeup", 4, Priority.HIGH, new AnimationListener() {
      @Override public void onAnimationStart() {
        Log.i(TAG, "doExpress开始执行表情!");
      }

      @Override public void onAnimationEnd(int i) {
        Log.i(TAG, "doExpress表情执行结束!");
      }

      @Override public void onAnimationRepeat(int loopNumber) {
        Log.i(TAG, "doExpress重复执行表情,重复次数:" + loopNumber);
      }
    });
    Log.i(TAG, "doExpress接口调用成功!");
  }

  /**
   * 执行自定义表情
   */
  public void doCustomizeExpress(View view) {
      List<Express.ExpressInfo> expressList = expressApi.getCustomizeExpressList();
      if(expressList.size() == 0){
          Toast.makeText(getApplicationContext(),"no custom express found !",Toast.LENGTH_LONG).show();
      }else{
          expressApi.doCustomizeExpress(expressList.get(0).getName(), Priority.HIGH, new AnimationListener() {
              @Override public void onAnimationStart() {
                  Log.i(TAG, "doCustomizeExpress开始执行表情!");
              }

              @Override public void onAnimationEnd(int i) {
                  Log.i(TAG, "doCustomizeExpress表情执行结束!");
              }

              @Override public void onAnimationRepeat(int loopNumber) {
                  Log.i(TAG, "doCustomizeExpress重复执行表情,重复次数:" + loopNumber);
              }
          });
      }

    Log.i(TAG, "doCustomizeExpress接口调用成功!");
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}

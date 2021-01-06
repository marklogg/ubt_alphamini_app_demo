package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.ubtrobot.commons.Priority;
import com.ubtrobot.commons.ResponseListener;
import com.ubtrobot.motion.protos.Motion;
import com.ubtrobot.motor.MotorApi;
import com.ubtrobot.motor.RunMode;
import com.ubtrobot.motor.receiver.MotorStatus;
import com.ubtrobot.motor.receiver.MotorStatusReceiver;
import java.util.ArrayList;
import java.util.List;

/**
 * MotorApi的测试方法
 */

public class MotorApiActivity extends Activity {
  private static final String TAG = DemoApp.DEBUG_TAG;
  private MotorApi motorApi;
  private MotorStatusReceiver receiver;

  private static final int defaultMotorId = 1;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.motor_api_layout);

    initRobot();
  }

  /**
   * 初始化接口类实例
   */
  private void initRobot() {
    motorApi = MotorApi.get();
  }

  /**
   * 获取舵机列表
   */
  public void getMotorList(View view) {
    List<Motion.Motor> motorList = motorApi.getMotorList();
    for (Motion.Motor motor : motorList) {
      Log.i(TAG, motor.toString());
    }
    Log.i(TAG, "getMotorList接口调用成功!");
  }

  /**
   * 获取单个舵机
   */
  public void getMotor(View view) {
    Motion.Motor motor = motorApi.getMotor(defaultMotorId);
    Log.i(TAG, motor.toString());
  }

  /**
   * 获取单个舵机状态
   */
  public void getMotorState(View view) {
    boolean state = motorApi.checkServoStatus(defaultMotorId);
    Log.i(TAG, "checkServoStatus state======" + state);
  }

  /**
   * 获取多个舵机状态
   */
  public void getMotorStateByArray(View view) {
    int[] motorIds = new int[] { 1, 2, 3 };
    List<Motion.ServoStatus> motorStateList = motorApi.checkServoStatus(motorIds);
    for (Motion.ServoStatus motorState : motorStateList) {
      Log.i(TAG, "getMotorStateByArray state======" + motorState.toString());
    }
  }

  /**
   * 读取单个舵机角度
   */
  public void readAngle(View view) {
    Log.i(TAG, "readAngle return======" + motorApi.readAbsoluteAngle(defaultMotorId));
  }

  /**
   * 读取多个舵机角度
   */
  public void readAngleByArray(View view) {
    int[] motorIds = new int[] { 1, 2, 3 };
    List<Motion.MotorAngle> motorParamList = motorApi.readAbsoluteAngle(motorIds);
    for (Motion.MotorAngle param : motorParamList) {
      Log.i(TAG, "readAngleByArray getId======" + param.getId());
      Log.i(TAG, "readAngleByArray getAngle======" + param.getAngle());
    }
  }

  /**
   * 锁定单个舵机
   */
  public void lockMotor(View view) {
    motorApi.lockMotor(defaultMotorId, new ResponseListener<Boolean>() {
      @Override public void onResponseSuccess(Boolean aBoolean) {
        Log.i(TAG, "lockMotor onResponseSuccess======" + aBoolean);
      }

      @Override public void onFailure(int errorCode, @NonNull String errorMsg) {
        Log.i(TAG, "lockMotor onFailure======errorCode:" + errorCode + ",errorMsg:" + errorMsg);
      }
    });
    Log.i(TAG, "lockMotor接口调用成功!");
  }

  /**
   * 解锁单个舵机
   */
  public void unlockMotor(View view) {
    motorApi.unlockMotor(defaultMotorId, new ResponseListener<Boolean>() {
      @Override public void onResponseSuccess(Boolean aBoolean) {
        Log.i(TAG, "unlockMotor onResponseSuccess======" + aBoolean);
      }

      @Override public void onFailure(int errorCode, @NonNull String errorMsg) {
        Log.i(TAG, "unlockMotor onFailure======errorCode:" + errorCode + ",errorMsg:" + errorMsg);
      }
    });
    Log.i(TAG, "unlockMotor接口调用成功!");
  }

  /**
   * 锁定多个舵机
   */
  public void lockMotorByList(View view) {
    List<Integer> motorIds = new ArrayList<>();
    motorIds.add(1);
    motorIds.add(2);
    motorIds.add(3);
    motorApi.lockMotor(motorIds, Priority.NORMAL, new ResponseListener<Boolean>() {
      @Override public void onResponseSuccess(Boolean aBoolean) {
        Log.i(TAG, "lockMotorByList onResponseSuccess======" + aBoolean);
      }

      @Override public void onFailure(int errorCode, @NonNull String errorMsg) {
        Log.i(TAG,
            "lockMotorByList onFailure======errorCode:" + errorCode + ",errorMsg:" + errorMsg);
      }
    });
    Log.i(TAG, "lockMotorByList接口调用成功!");
  }

  /**
   * 解锁多个舵机
   */
  public void unlockMotorByList(View view) {
    List<Integer> motorIds = new ArrayList<>();
    motorIds.add(1);
    motorIds.add(2);
    motorIds.add(3);
    motorApi.unlockMotor(motorIds, Priority.NORMAL, new ResponseListener<Boolean>() {
      @Override public void onResponseSuccess(Boolean aBoolean) {
        Log.i(TAG, "unlockMotorByList onResponseSuccess======" + aBoolean);
      }

      @Override public void onFailure(int errorCode, @NonNull String errorMsg) {
        Log.i(TAG,
            "unlockMotorByList onFailure======errorCode:" + errorCode + ",errorMsg:" + errorMsg);
      }
    });
    Log.i(TAG, "unlockMotorByList接口调用成功!");
  }

  /**
   * 单个舵机移动到指定角度
   */
  public void moveToAngle(View view) {
    final int curAngle = MotorApi.get().readAbsoluteAngle(defaultMotorId);
    int angle = curAngle == 170 ? 10 : 170;
    int duration = 1000;
    motorApi.moveToAbsoluteAngle(defaultMotorId, angle, duration, RunMode.LINEAR, Priority.NORMAL,
        new ResponseListener<Void>() {

          @Override public void onResponseSuccess(Void aVoid) {
            Log.i(TAG, "moveToAngle舵机移动完成!");
            new Handler().postDelayed(new Runnable() {
              @Override public void run() {
                final int curAngle = MotorApi.get().readAbsoluteAngle(defaultMotorId);
                int angle = curAngle == 10 ? 170 : 10;
                int duration = 5000;
                motorApi.moveToAbsoluteAngle(defaultMotorId, angle, duration, RunMode.LINEAR,
                    Priority.NORMAL, new ResponseListener<Void>() {
                      @Override public void onResponseSuccess(Void aVoid) {
                        Log.i(TAG, "moveToAngle舵机移动完成!");
                      }

                      @Override public void onFailure(int errorCode, @NonNull String errorMsg) {
                        Log.i(TAG, "moveToAngle onFailure======errorCode:"
                            + errorCode
                            + ",errorMsg:"
                            + errorMsg);
                      }
                    });
              }
            }, 1000);
          }

          @Override public void onFailure(int errorCode, @NonNull String errorMsg) {
            Log.i(TAG,
                "moveToAngle onFailure======errorCode:" + errorCode + ",errorMsg:" + errorMsg);
          }
        });
  }

  /**
   * 多个舵机移动到指定角度
   */
  public void moveToAngleByList(View view) {
    int angle = 150;
    int duration = 1000;

    List<Motion.MotorArg> motorList = new ArrayList<>();
    Motion.MotorArg param1 = Motion.MotorArg.newBuilder()
        .setId(defaultMotorId)
        .setAngle(angle)
        .setRunMode(RunMode.LINEAR.intValue)
        .setRunTime(duration)
        .build();
    Motion.MotorArg param2 = Motion.MotorArg.newBuilder()
        .setId(4)
        .setAngle(angle)
        .setRunMode(RunMode.LINEAR.intValue)
        .setRunTime(duration)
        .build();
    motorList.add(param1);
    motorList.add(param2);

    motorApi.moveToAbsoluteAngle(motorList, new ResponseListener<Void>() {

      @Override public void onResponseSuccess(Void aVoid) {
        Log.i(TAG, "moveToAngle多个舵机移动到指定角度完成!");
      }

      @Override public void onFailure(int errorCode, @NonNull String errorMsg) {
        Log.i(TAG, "moveToAngle多个舵机移动到指定角度接口调用失败,errorCode======"
            + errorCode
            + ",errorMsg======"
            + errorMsg);
      }
    });
  }

  /**
   * 订阅舵机状态事件
   */
  public void subscribeMotorStatusEvent(View view) {
    receiver = new MotorStatusReceiver() {
      @Override protected void onMotorStatus(List<MotorStatus> list) {

      }
    };

    motorApi.subscribeMotorStatusEvent(receiver);
    Log.i(TAG, "subscribeMotorErrorEvent接口调用成功!");
  }

  /**
   * 取消订阅舵机错误事件
   */
  public void unsubscribeMotorStatusEvent(View view) {
    motorApi.unsubscribeMotorStatusEvent(receiver);
    Log.i(TAG, "unsubscribeMotorErrorEvent接口调用成功!");
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}

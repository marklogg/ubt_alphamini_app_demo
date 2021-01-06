package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ubtrobot.action.ActionApi;
import com.ubtrobot.action.receiver.ActionStoppedReceiver;
import com.ubtrobot.commons.ResponseListener;
import com.ubtrobot.motion.protos.Motion;

import java.util.List;

/**
 * ActionApi的测试方法
 */

public class ActionApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private ActionApi actionApi;
    private ActionStoppedReceiver receiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_api_layout);

        initRobot();
    }

    /**
     * 初始化接口类实例
     */
    private void initRobot() {
        actionApi = ActionApi.get();
    }

    /**
     * 获取动作列表
     *
     * @param view
     */
    public void getActionList(View view) {
        List<Motion.Action> actionList = actionApi.getActionList();
        for (Motion.Action actionInfo : actionList) {
            Log.i(TAG, actionInfo.toString());
        }
        Log.i(TAG, "getActionList接口调用成功!");
    }

    /**
     * 获取动作列表 ,自定义表情资源存放在/sdcard/customize/actions目录
     * <p>
     * 系统版本需v0.0.3以上
     *
     * @param view
     */
    public void getCustomizeActionList(View view) {
        List<Motion.Action> actionList = actionApi.getCustomizeActionList();
        for (Motion.Action actionInfo : actionList) {
            Log.i(TAG, actionInfo.toString());
        }
        Log.i(TAG, "getCustomizeActionList接口调用成功!");
    }

    /**
     * 执行动作,以dance_0002为例
     *
     * @param view
     */
    public void playAction(View view) {
        List<Motion.Action> actionList = actionApi.getActionList();
        if (actionList != null && actionList.size() != 0) {
            actionApi.playAction(actionList.get(0).getId(), new ResponseListener<Void>() {
                @Override
                public void onResponseSuccess(Void aVoid) {
                    Log.i(TAG, "playAction onResponseSuccess!");
                }

                @Override
                public void onFailure(int i, @NonNull String s) {
                    Log.i(TAG, "playAction onFailure !" + i + " ===== " + s);
                }
            });
        }
    }

    /**
     * 执行自定义动作
     *
     * @param view
     */
    public void playCustomizeAction(View view) {
        List<Motion.Action> actionList = actionApi.getCustomizeActionList();
        if(actionList == null || actionList.size() == 0){
            Toast.makeText(getApplicationContext(),"please copy action ubx to /sdcard/customize/actions ",Toast.LENGTH_LONG).show();
            return;
        }
        actionApi.playCustomizeAction(actionList.get(0).getId(), new ResponseListener<Void>() {
            @Override
            public void onResponseSuccess(Void aVoid) {
                Log.i(TAG, "playCustomizeAction onResponseSuccess!");
            }

            @Override
            public void onFailure(int i, @NonNull String s) {
                Log.i(TAG, "onFailure onFailure !" + i + " ===== " + s);
            }
        });
    }

    /**
     * 停止动作
     *
     * @param view
     */
    public void stopAction(View view) {
        List<Motion.Action> actionList = actionApi.getActionList();
        if (actionList != null && actionList.size() != 0) {
            actionApi.playAction(actionList.get(0).getId(), null);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    actionApi.stopAction(new ResponseListener<Void>() {
                        @Override
                        public void onResponseSuccess(Void aVoid) {
                            Log.i(TAG, "stopAction onResponseSuccess！");
                        }

                        @Override
                        public void onFailure(int errorCode, @NonNull String errorMsg) {
                            Log.i(TAG, "stopAction,errorCode:" + errorCode + ",errorMsg:" + errorMsg);
                        }
                    });
                }
            }, 1000);
        }
    }

    /**
     * 机器人是否在执行动作
     *
     * @param view
     */
    public void isPlayingAction(View view) {
        Log.i(TAG, "isPlayingAction result:" + actionApi.isPlaying());
    }

    /**
     * 是否是高危动作
     *
     * @param view
     */
    public void isDangerousAction(View view) {
        List<Motion.Action> actionList = actionApi.getActionList();
        if (actionList != null && actionList.size() != 0) {
            Motion.Action action = actionList.get(0);
            Log.i(TAG, "isDangerousAction接口调用,"+ action.getId()+"动作是否是高危动作:" + actionApi.unsafeAction(action.getId()));
        }
    }

    /**
     * 获取机器人正在执行的动作信息
     *
     * @param view
     */
    public void getCurrentActionInfo(View view) {
        List<Motion.Action> actionInfo = actionApi.currentAction();
        if (actionInfo != null) {
            Log.i(TAG, "getCurrentActionInfo接口调用成功!");
            Log.i(TAG, actionInfo.toString());
        } else {
            Log.i(TAG, "there are no actions playing！");
        }
    }

    /**
     * 订阅动作停止事件
     *
     * @param view
     */
    public void subscribeActionStoppedEvent(View view) {
        receiver = new ActionStoppedReceiver() {
            @Override
            protected void onActionStopped(String s, Cause cause) {
                Log.i(TAG, "subscribeActionStoppedEvent onActionStopped接收到Action停止事件,cause name:" + cause.name());
            }
        };

        actionApi.subscribeEvent(receiver);
        Log.i(TAG, "subscribeActionStoppedEvent接口调用成功!");
    }

    /**
     * 取消订阅动作停止事件
     *
     * @param view
     */
    public void unsubscribeActionStoppedEvent(View view) {
        if (receiver != null) {
            actionApi.unsubscribeEvent(receiver);
            Log.i(TAG, "unsubscribeActionStoppedEvent接口调用成功！");
        } else {
            Log.i(TAG, "receiver为null,不调用接口!");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

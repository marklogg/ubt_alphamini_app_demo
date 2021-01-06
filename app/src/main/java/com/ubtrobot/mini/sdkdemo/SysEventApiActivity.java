package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.ubtrobot.masterevent.protos.SysMasterEvent;
import com.ubtrobot.mini.sysevent.EventApi;
import com.ubtrobot.mini.sysevent.SysEventApi;
import com.ubtrobot.mini.sysevent.event.BatteryEvent;
import com.ubtrobot.mini.sysevent.event.CommonSystemEvent;
import com.ubtrobot.mini.sysevent.event.HeadEvent;
import com.ubtrobot.mini.sysevent.event.PowerButtonEvent;
import com.ubtrobot.mini.sysevent.event.VolumeEvent;
import com.ubtrobot.mini.sysevent.event.base.KeyEvent;
import com.ubtrobot.mini.sysevent.listener.publish.BaseEventPublishListener;
import com.ubtrobot.mini.sysevent.listener.subscribe.GetBatteryInfoListener;
import com.ubtrobot.mini.sysevent.receiver.BatteryEventReceiver;
import com.ubtrobot.mini.sysevent.receiver.CommonSystemEventReceiver;
import com.ubtrobot.mini.sysevent.receiver.KeyEventReceiver;
import com.ubtrobot.mini.sysevent.receiver.SingleClickReceiver;
import com.ubtrobot.transport.message.CallException;


/**
 * SysEventApi的测试方法
 */

public class SysEventApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private EventApi sysEventApi;
    private SingleClickReceiver mHeadReceiver;
    private BatteryEventReceiver mBatteryEventReceiver;
    private KeyEventReceiver mPowerButtonEventReceiver;
    private SingleClickReceiver mVolumeButtonEventReceiver;
    private CommonSystemEventReceiver mCommonSystemEventReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sysevent_api_layout);

        initRobot();
        subscribeAllEvents();
    }

    public void subscribeAllEvents(){
        /******************************* HEAD *******************************/
        mHeadReceiver = new SingleClickReceiver() {
            @Override
            public boolean onSingleClick(KeyEvent event) {
                Log.d(TAG, "HeadEvent=======onSingleClick");
                return true; /***** return false will pass event to next Priority! *****/
            }
        };
        SysEventApi.get().subscribe(HeadEvent.newInstance(), mHeadReceiver);

        /******************************* BATTERY *******************************/
        mBatteryEventReceiver = new BatteryEventReceiver() {
            @Override
            public boolean onReceive(BatteryEvent event) {
                Log.d(TAG, "BatteryEvent=======onReceive");
                return true;
            }
        };
        SysEventApi.get().subscribe(BatteryEvent.newInstance(), mBatteryEventReceiver);

        /******************************* POWER-BUTTON *******************************/
        mPowerButtonEventReceiver = new KeyEventReceiver() {

            @Override
            public boolean onSingleClick(KeyEvent event) {
                Log.d(TAG, "PowerButtonEvent=======onSingleClick");
                return true;
            }

            @Override
            public boolean onDoubleClick(KeyEvent event) {
                Log.d(TAG, "PowerButtonEvent=======onDoubleClick");
                return true;
            }

            @Override
            public boolean onLongClick(KeyEvent event) {
                Log.d(TAG, "PowerButtonEvent=======onLongClick");
                return true;
            }
        };
        SysEventApi.get().subscribe(PowerButtonEvent.newInstance(), mPowerButtonEventReceiver);

        /******************************* VOLUME-BUTTON *******************************/
        mVolumeButtonEventReceiver = new SingleClickReceiver() {
            @Override
            public boolean onSingleClick(KeyEvent event) {
                Log.d(TAG, "VolumeEvent=======onSingleClick");
                return true;
            }
        };
        SysEventApi.get().subscribe(VolumeEvent.newInstance(), mVolumeButtonEventReceiver);

        /******************************* SYSACTIVE*******************************/
        //TODO
        /******************************* 自定义事件*******************************/
        mCommonSystemEventReceiver = new CommonSystemEventReceiver() {
            @Override
            public boolean onReceive(CommonSystemEvent event) {
                Log.d(TAG, "CommonSystemEvent======onReceive======" + event.toString());
                return false;
            }
        };
        SysEventApi.get().subscribe(CommonSystemEvent.newInstance("action-test-1"), mCommonSystemEventReceiver);
    }

    /**
     * 初始化接口类实例
     */
    private void initRobot() {
        sysEventApi = SysEventApi.get();
    }

    /**
     * 异步获取当前电量信息
     *
     * @param view
     */
    public void getCurrentBatteryInfo(View view) {
        sysEventApi.getCurrentBatteryInfo(new GetBatteryInfoListener() {
            @Override
            public void onSuccess(SysMasterEvent.BatteryStatusData data) {
                Log.i(TAG, "getCurrentBatteryInfo接口调用成功！");
                Log.i(TAG, "BatteryStatusData getStatus======" + data.getStatus());
                Log.i(TAG, "BatteryStatusData getLevel======" + data.getLevel());
                Log.i(TAG, "BatteryStatusData getLevelStatus======" + data.getLevelStatus());

            }

            @Override public void onFiald(CallException e) {
                Log.i(TAG, "getCurrentBatteryInfo接口调用失败:" + e.getMessage());
            }

        });
    }

    /**
     * 同步获取当前电量信息
     *
     * @param view
     */
    public void getCurrentBatteryInfoSync(View view) {
        SysMasterEvent.BatteryStatusData data = sysEventApi.getCurrentBatteryInfoSync();
        Log.i(TAG, "getCurrentBatteryInfoSync接口调用成功！");
        Log.i(TAG, "getCurrentBatteryInfoSync getStatus======" + data.getStatus());
        Log.i(TAG, "getCurrentBatteryInfoSync getLevel======" + data.getLevel());
        Log.i(TAG, "getCurrentBatteryInfoSync getLevelStatus======" + data.getLevelStatus());
    }

    @Override
    protected void onDestroy() {
        unsubscribeAllReceivers();
        super.onDestroy();
    }

    private void unsubscribeAllReceivers() {
        if(mHeadReceiver!=null){
            sysEventApi.unsubscribe(mHeadReceiver);
        }
        if(mBatteryEventReceiver!=null){
            sysEventApi.unsubscribe(mBatteryEventReceiver);
        }
        if(mPowerButtonEventReceiver!=null){
            sysEventApi.unsubscribe(mPowerButtonEventReceiver);
        }
        if(mVolumeButtonEventReceiver!=null){
            sysEventApi.unsubscribe(mVolumeButtonEventReceiver);
        }
        if(mCommonSystemEventReceiver!=null){
            sysEventApi.unsubscribe(mCommonSystemEventReceiver);
        }
    }

    public void publishCustomEvent(View view) {
        SysEventApi.get().publishCommSysEvent(SysMasterEvent.CommSysEvent.newBuilder().setPolicy(SysMasterEvent.ActionReceivedPolicy.ONE_SHOT).setEventAction("action-test-1").setEventCode(666).setEventValue("我是自定义事件内容!").build(), new BaseEventPublishListener() {
            @Override
            public void onPublishSuccess() {
                Log.d(TAG,"Publish-Event "+"onPublishSuccess");
            }

            @Override
            public void onPublishFailure(Exception e) {
                Log.d(TAG,"Publish-Event "+"onPublishFailure: "+e);
            }
        });
    }
}

package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.ubtrobot.masterevent.protos.RobotGestures;
import com.ubtrobot.transport.message.CallException;
import com.ubtrobot.transport.message.Request;
import com.ubtrobot.transport.message.Response;
import com.ubtrobot.transport.message.ResponseCallback;
import ubtechinc.com.standupsdk.FallClimbEventReceiver;
import ubtechinc.com.standupsdk.GetRobotGestureCallback;
import ubtechinc.com.standupsdk.StandUpApi;

/**
 * Created by lulin.wu on 2018/6/19.
 */

public class StandupApiActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private FallClimbEventReceiver fallClimbEventReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.standup_api_layout);
        fallClimbEventReceiver = new FallClimbEventReceiver() {

            @Override public void onFallclimb(RobotGestures.FallClimbType fallClimbType) {
                Log.i(TAG,"fallClimbType===" + fallClimbType);
            }
            
        };
        StandUpApi.get().subscribeFallClimbEvent(fallClimbEventReceiver);
    }

    public void getRobotGesture(View view){
        RobotGestures.GestureType gestureType = StandUpApi.get().getRobotGesture();
        Log.i(TAG,"gestureType========" + gestureType);
    }
    public void getRobotGestureSync(View view){
        StandUpApi.get().getRobotGestureSync(new GetRobotGestureCallback() {
            @Override
            protected void getRobotGesture(RobotGestures.GestureType gestureType) {
                Log.i(TAG,"getRobotGesture success==== gestureType: "+gestureType);
            }

            @Override
            public void onFailure(Request request, CallException e) {
                Log.i(TAG,"getRobotGesture onFailure===="+e);
            }
        });
    }
    public void standup(View view){
        StandUpApi.get().standUp(new ResponseCallback() {
            @Override
            public void onResponse(Request request, Response response) {
                Log.i(TAG,"standup success====");
            }

            @Override
            public void onFailure(Request request, CallException e) {
                Log.i(TAG,"standup fail===="+e);
            }
        });
    }
    public void sitdown(View view){
        StandUpApi.get().sitdown(new ResponseCallback() {
            @Override
            public void onResponse(Request request, Response response) {
                Log.i(TAG,"sitdown success====");
            }

            @Override
            public void onFailure(Request request, CallException e) {
                Log.i(TAG,"sitdown fail===="+e);
            }
        });
    }
    public void squatdown(View view){
        StandUpApi.get().squatdown(new ResponseCallback() {
            @Override
            public void onResponse(Request request, Response response) {
                Log.i(TAG,"squatdown success====");
            }

            @Override
            public void onFailure(Request request, CallException e) {
                Log.i(TAG,"squatdown onFailure===="+e);
            }
        });
    }
    public void getRobotFallStatus(View view){
        RobotGestures.FallClimbType fallClimbType = StandUpApi.get().getRobotFallStatus();
        Log.i(TAG,"fallClimbType======" + fallClimbType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StandUpApi.get().unsubscribeFallClimbEvent(fallClimbEventReceiver);
    }
}

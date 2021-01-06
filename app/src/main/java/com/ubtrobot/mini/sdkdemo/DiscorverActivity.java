package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ubtrobot.communication.robot.CommChannelManager;
import com.ubtrobot.communication.robot.SocketChannelHub;
import com.ubtrobot.communication.robot.SocketConnection;
import com.ubtrobot.communication.robot.callback.ResultCallback;
import com.ubtrobot.discover.DiscoverManager;
import com.ubtrobot.lib.robot.proto.ProtoClass;
import com.ubtrobot.master.param.ProtoParam;

import java.util.ArrayList;

public class DiscorverActivity extends Activity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private SocketChannelHub mSocketChannelHub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discover_layout);
        CommChannelManager.init(this);
        DiscoverManager.init(this);
        mSocketChannelHub = CommChannelManager.getSocketChannel();
    }

    public void onStartDiscover(View view){
        CommChannelManager.startCommService();
        ProtoClass.ProtoText robotInfo = ProtoClass.ProtoText.newBuilder().setText("SN:12345678").build();
        DiscoverManager.startDiscoverService(robotInfo);
    }

    public void onSendMsgForLan(View view){
        ArrayList<SocketConnection> list = mSocketChannelHub.getSocketConnections();
        Log.i(TAG, "Send list:" + list);
        for(SocketConnection connection: list) {
            connection.push(ProtoParam.create(
                    ProtoClass.ProtoText.newBuilder().setText("xxxx").build()), new ResultCallback() {
                @Override
                public void onSuccess() {
                    Log.i(TAG, "Push msg by lan successful.");
                }

                @Override
                public void onFail(int code, String message) {
                    Log.e(TAG, "Push msg by lan fail. errCode:" + code + " errMsg:" + message);
                }
            });
        }
    }
}

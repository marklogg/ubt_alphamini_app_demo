package com.ubtrobot.mini.sdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ubtrobot.small.action.SmallAction;
import com.ubtrobot.small.action.SmallActionApi;

import java.util.List;

/**
 * tips: logcat: tag: SmallAction
 */
public class SmallActionActivity extends AppCompatActivity {
    private static final String TAG = DemoApp.DEBUG_TAG;
    private List<SmallAction> smallActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_action);
    }

    public void testListSmallActions(View view) {
        smallActions = SmallActionApi.get().listSmallActions();
        for (SmallAction sa:
        smallActions) {
            Log.d(TAG,"smallAction: "+sa.getName()+" ======== "+sa.getType());
        }
        Log.d(TAG," list smallAction 接口调用成功！");
    }

    public void testPlay(View view) {
        if(smallActions!=null && smallActions.size()>0) {
            SmallActionApi.get().play(smallActions.get(0));
            Log.d(TAG,"play 接口调用成功！");
        }else{
            Toast.makeText(getApplicationContext(),"no small action found!",Toast.LENGTH_LONG).show();
        }
    }

    public void testStop(View view) {
        if(smallActions!=null && smallActions.size()>0) {
            SmallActionApi.get().stop(smallActions.get(0));
            Log.d(TAG,"stop 接口调用成功！");
        }
    }

}

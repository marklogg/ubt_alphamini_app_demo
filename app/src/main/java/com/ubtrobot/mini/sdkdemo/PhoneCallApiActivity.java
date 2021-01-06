package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ubtrobot.mini.phonecall.CallComingUpEvent;
import com.ubtrobot.mini.phonecall.CallFinishedEvent;
import com.ubtrobot.mini.phonecall.CallStateListener;
import com.ubtrobot.mini.phonecall.CallWillStartEvent;
import com.ubtrobot.mini.phonecall.IPhoneCallListener;
import com.ubtrobot.mini.phonecall.PhoneCallApi;
import com.ubtrobot.phone.PhoneCall;
import com.ubtrobot.transport.message.CallException;

import java.util.List;

/**
 * PhoneCallApi usage
 */
public class PhoneCallApiActivity extends Activity implements CallStateListener,IPhoneCallListener {
    private static final String TAG = DemoApp.DEBUG_TAG;

    private PhoneCallApi mPhoneCallApi;

    private EditText mTelno;
    private EditText mTelname;
    private Button mCalltel;
    private Button mCallstatus;
    private TextView mCallstatustv;
    private Button mAnswer;
    private Button mHangUp;
    private Button mPlayRing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonecall);
        initView();
        initApi();
    }

    private void initView() {
        mTelno = findViewById(R.id.telno);
        mTelname = findViewById(R.id.telname);
        mCalltel = findViewById(R.id.calltel);
        mCallstatus = findViewById(R.id.callstatus);
        mCallstatustv = findViewById(R.id.callstatustv);
        mAnswer = findViewById(R.id.answer);
        mHangUp = findViewById(R.id.hang_up);
        mPlayRing=findViewById(R.id.playring);

        mCalltel.setOnClickListener(mClickListener);
        mCallstatus.setOnClickListener(mClickListener);
        mAnswer.setOnClickListener(mClickListener);
        mHangUp.setOnClickListener(mClickListener);
        mPlayRing.setOnClickListener(mClickListener);
    }


    private void initApi() {
        mPhoneCallApi = PhoneCallApi.get();
        mPhoneCallApi.start(this);
    }

    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.calltel:
                    makeCall();
                    break;
                case R.id.callstatus:
                    mCallstatustv.setText(mPhoneCallApi.isInCall()?"in call":"idle");
                    break;
                case R.id.answer://answer a call
                    mPhoneCallApi.answer();
                    Log.d(TAG,"answer execute");
                    break;
                case R.id.hang_up://hang up a call
                    mPhoneCallApi.hangup();
                    Log.d(TAG,"hangup execute");
                    break;
                case R.id.playring://play ring
                    mPhoneCallApi.playRing();
                    Log.d(TAG,"playRing execute");
                    break;
                default:
                    break;
            }
        }
    };


    private void makeCall() {
        String name = mTelname.getText().toString().trim();
        String phoneNumber = mTelno.getText().toString().trim();

        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(getApplicationContext(),"phone number is null!",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(name)){
            name = "android";
        }

        PhoneCall.Contact contact = PhoneCall.Contact.newBuilder()
                .setName(name)
                .setPhoneNumber(phoneNumber).build();
        mPhoneCallApi.call(contact,this);
    }

    /////// IPhoneCallListener method start //////
    @Override
    public void onFailue(CallException e) {
        Log.i(TAG, "call fail, code:" + e.getCode() + " subCode:"+ e.getSubCode() +  "  msg:"+e.getMessage());
    }

    @Override
    public void onSuccess(List<PhoneCall.Contact> contactList) {
        Log.i(TAG, "call success:" + contactList);
    }

    @Override
    public void onContactNotFound(List<PhoneCall.Contact> contactList) {
        //call by name , but not found the related phonenumber
        Log.i(TAG, "onContactNotFound:" + contactList);
    }

    @Override
    public void onMultiNumberFound(List<PhoneCall.Contact> contactList) {
        //call by name , but found mutil related phonenumber
        Log.i(TAG, "onMutilNumberFound:" + contactList);
    }
    /////// IPhoneCallListener method end //////


    /////// CallStateListener method start //////
    @Override
    public void phoneBookUpdate(List<String> names) {
        //local contacts has changed
        Log.i(TAG, "phoneBookUpdate: local contacts has changed:" + names);
    }

    @Override
    public void callIncoming(CallComingUpEvent event) {
        Log.i(TAG, "incoming a call:" + event);
    }

    @Override
    public void callWillStart(CallWillStartEvent event) {
        //phone call will start
        Log.i(TAG, "callWillStart: phone call will start:" +event);
    }

    @Override
    public void callFinished(CallFinishedEvent event) {
        // phone call finished
        Log.i(TAG, "callFinished: phone call finished:" + event);
    }

    @Override
    public void ringBreakByWakeup() {
        //ring is interrupted by robot wakeup, if you want to continue play ring , use mPhoneCallApi.playRing();
        Log.i(TAG, "ringBreakByWakeup: ring break");
    }

    @Override
    public void ringFinish() {
        Log.i(TAG, "ringFinish...");
    }
    /////// CallStateListener method end //////

    @Override
    protected void onDestroy() {
        mPhoneCallApi.stop();
        super.onDestroy();
    }
}

package com.ubtrobot.mini.sdkdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.protobuf.StringValue;
import com.ubtech.utilcode.utils.LogUtils;
import com.ubtech.utilcode.utils.thread.HandlerUtils;
import com.ubtechinc.skill.MiniMasterSkill;
import com.ubtechinc.skill.ProxySkill;
import com.ubtechinc.skill.SkillHelper;
import com.ubtechinc.skill.SkillType;
import com.ubtrobot.commons.ResponseListener;
import com.ubtrobot.master.Master;
import com.ubtrobot.master.annotation.Call;
import com.ubtrobot.master.context.ContextRunnable;
import com.ubtrobot.master.param.ProtoParam;
import com.ubtrobot.mini.sysevent.event.base.KeyEvent;
import com.ubtrobot.speech.protos.Speech;
import com.ubtrobot.transport.message.Request;
import com.ubtrobot.transport.message.Responder;

public class DemoSkill extends MiniMasterSkill {
    private final static String TAG = "DemoSkill";

    @Override
    protected boolean onHeadTapEvent(KeyEvent keyEvent) {
        return false;
    }

    @Override
    protected void onWakeUpEvent(Speech.WakeupParam wakeupParam) {
    }

    @Override
    protected boolean isNeedWakeUpEvent() {
        return false;
    }

    @Override
    protected boolean isNeedHeadTapEvent() {
        return false;
    }

    @Override
    protected void onSkillStart() {
        super.onSkillStart();
        Log.d(TAG,"onSkillStart......");
        HandlerUtils.runUITask(() ->
                SkillHelper.pushMessageToPhone(" {\"name\": \"John Doe\", \"age\": 18, \"address\": {\"country\" : \"china\", \"zip-code\": \"10000\"}}","1"), 3000);
    }

    @Call(path = "/demo/startSkill")
    public void onStartSkill(Request request, final Responder responder){
        Log.d(TAG,"onStartSkill---");
        //TODO:
        if (!request.getParam().isEmpty()){
            try {
                String content =  ProtoParam.from(request.getParam(), StringValue.class).getProtoMessage().getValue();
                Log.d(TAG,"onStartSkill content:"+content);
            } catch (ProtoParam.InvalidProtoParamException e) {
                e.printStackTrace();
            }
        }

        responder.respondSuccess(ProtoParam.create(StringValue.newBuilder().setValue("{\"name\": \"Dio\", \"age\": 26, \"address\": {\"country\" : \"china\", \"zip-code\": \"10000\"}}").build()));
    }

    public static void startThisSkill() {
        SkillHelper.startSkillByIntent("startSkill", null, new ResponseListener<Void>() {
            @Override
            public void onResponseSuccess(Void aVoid) {
                Log.d(TAG,"onResponseSuccess");
            }

            @Override
            public void onFailure(int i, @NonNull String s) {
                Log.d(TAG,"onFailure");
            }
        });
    }

    public static void stopThisSkill() {
        Master.get().execute(DemoSkill.class, new ContextRunnable<DemoSkill>() {
            @Override
            public void run(DemoSkill skill) {
                skill.stopSkill(new Bundle());
            }
        });
    }

}

package com.ubtrobot.mini.sdkdemo.skills;

import android.util.Log;

import com.ubtech.utilcode.utils.thread.HandlerUtils;
import com.ubtechinc.skill.ProxySkill;
import com.ubtechinc.skill.SkillHelper;
import com.ubtechinc.skill.SkillType;
import com.ubtrobot.master.annotation.Call;
import com.ubtrobot.master.skill.SkillStopCause;
import com.ubtrobot.mini.sysevent.event.base.KeyEvent;
import com.ubtrobot.speech.protos.Speech;
import com.ubtrobot.transport.message.Request;
import com.ubtrobot.transport.message.Responder;

/**
 * author：龙仲春
 * email:zhongchun.long@ubtrobot.com
 * date：2020-02-17 17:31
 * package：com.ubtrobot.mini.sdkdemo.skills
 * project：mini_libs_robot_sdk
 */
public class InterruptibleSkill extends ProxySkill {

    private static final String TAG = "InterruptibleSkill";

    @Override
    protected void onSkillStart() {
        super.onSkillStart();
        Log.d(TAG, "可打断技能启动 ");
        HandlerUtils.runUITask(() -> SkillHelper.pushMessageToPhone(" {\"name\": \"John Doe\", \"age\": 18, \"address\": {\"country\" : \"china\", \"zip-code\": \"10000\"}}","1"), 3000);
    }

    @Override
    protected void onSkillStop(SkillStopCause skillStopCause) {
        super.onSkillStop(skillStopCause);
        Log.d(TAG, "可打断技能停止："+skillStopCause);
    }

    @Call(path = "/demo_interruptible/startSkill")
    public void onStartSkill(Request request, final Responder responder){
        Log.d(TAG,"onStartSkill---");
        responder.respondSuccess();
    }

    @Override
    protected String getSkillName() {
        return "demo_interruptible_skill";
    }

    @Override
    protected SkillType getSkillType() {
        return SkillType.Interruptible;
    }

    @Override
    protected SkillType getSubSkillType() {
        return null;
    }

    @Override
    protected boolean onHeadTapEvent(KeyEvent keyEvent) {
        stopSkill();
        return false;
    }

    @Override
    protected void onWakeUpEvent(Speech.WakeupParam wakeupParam) {
        stopSkill();
    }

    @Override
    protected boolean isNeedWakeUpEvent() {
        return true;
    }

    @Override
    protected boolean isNeedHeadTapEvent() {
        return true;
    }
}

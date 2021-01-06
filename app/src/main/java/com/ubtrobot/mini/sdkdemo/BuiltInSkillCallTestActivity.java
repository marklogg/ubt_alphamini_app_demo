package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.ubtechinc.skill.SkillHelper;
import com.ubtrobot.transport.message.CallException;
import com.ubtrobot.transport.message.Request;
import com.ubtrobot.transport.message.Response;
import com.ubtrobot.transport.message.ResponseCallback;

public class BuiltInSkillCallTestActivity extends Activity {
  private static final String TAG = "BuiltInSkillCallTest";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_builtin_skill_calls);
  }

  public void upgrade(View view) {
    SkillHelper.startSkillByIntent("general_update", null, getListener());
  }

  public void factoryReset(View view) {
    SkillHelper.startSkillByPath("/robot/doFactoryReset", null, getListener());
  }

  public void shutdown(View view) {
    SkillHelper.startSkillByIntent("turn_off_robot", null, getListener());
  }

  @NonNull private ResponseCallback getListener() {
    return new ResponseCallback() {
      @Override public void onResponse(Request request, Response response) {
        Log.i(TAG, "start success.");
      }

      @Override public void onFailure(Request request, CallException e) {
        Log.i(TAG, e.getMessage());
      }
    };
  }

  public void exercise(View view) {
    SkillHelper.startSkillByIntent("exercise", null, getListener());
  }

  public void yoga(View view) {
    SkillHelper.startSkillByIntent("yoga", null, getListener());
  }

  public void kungfu(View view) {
    SkillHelper.startSkillByIntent("kungfu", null, getListener());
  }

  public void sit_down(View view) {
    SkillHelper.startSkillByIntent("sit_down", null, getListener());
  }

  public void fly(View view) {
    SkillHelper.startSkillByIntent("fly", null, getListener());
  }

  public void stand_up(View view) {
    SkillHelper.startSkillByIntent("stand_up", null, getListener());
  }

  public void raisinghands(View view) {
    SkillHelper.startSkillByIntent("raisinghands", null, getListener());
  }

  public void raisingrightleg(View view) {
    SkillHelper.startSkillByIntent("raisingrightleg", null, getListener());
  }

  public void raisingleftleg(View view) {
    SkillHelper.startSkillByIntent("raisingleftleg", null, getListener());
  }

  public void stoop(View view) {
    SkillHelper.startSkillByIntent("stoop", null, getListener());
  }

  public void Keep_turning_right(View view) {
    SkillHelper.startSkillByIntent("Keep_turning_right", null, getListener());
  }

  public void Keep_turning_left(View view) {
    SkillHelper.startSkillByIntent("Keep_turning_left", null, getListener());
  }

  public void Keep_moving_forward(View view) {
    SkillHelper.startSkillByIntent("Keep_moving_forward", null, getListener());
  }

  public void Keep_going_backwards(View view) {
    SkillHelper.startSkillByIntent("Keep_going_backwards", null, getListener());
  }

  public void crouch(View view) {
    SkillHelper.startSkillByIntent("crouch", null, getListener());
  }

  public void bow_step(View view) {
    SkillHelper.startSkillByIntent("bow_step", null, getListener());
  }

  public void say_hi(View view) {
    SkillHelper.startSkillByIntent("say_hi", null, getListener());
  }

  public void sneeze(View view) {
    SkillHelper.startSkillByIntent("sneeze", null, getListener());
  }

  public void burp(View view) {
    SkillHelper.startSkillByIntent("burp", null, getListener());
  }

  public void break_wind(View view) {
    SkillHelper.startSkillByIntent("break_wind", null, getListener());
  }

  public void scratch(View view) {
    SkillHelper.startSkillByIntent("scratch", null, getListener());
  }

  public void hand_kiss(View view) {
    SkillHelper.startSkillByIntent("hand_kiss", null, getListener());
  }

  public void hug(View view) {
    SkillHelper.startSkillByIntent("hug", null, getListener());
  }

  public void Stretch(View view) {
    SkillHelper.startSkillByIntent("Stretch", null, getListener());
  }

  public void doze(View view) {
    SkillHelper.startSkillByIntent("doze", null, getListener());
  }

  public void frighten(View view) {
    SkillHelper.startSkillByIntent("frighten", null, getListener());
  }

  public void shake_hand(View view) {
    SkillHelper.startSkillByIntent("shake_hand", null, getListener());
  }

  public void nod(View view) {
    SkillHelper.startSkillByIntent("nod", null, getListener());
  }

  public void shake_head(View view) {
    SkillHelper.startSkillByIntent("shake_head", null, getListener());
  }

  public void turn_head(View view) {
    SkillHelper.startSkillByIntent("turn_head", null, getListener());
  }

  public void be_cute(View view) {
    SkillHelper.startSkillByIntent("be_cute", null, getListener());
  }

  public void dancing(View view) {
    SkillHelper.startSkillByIntent("dancing", null, getListener());
  }

  //public void volume_turn_down(View view) {
  //  SkillHelper.startSkillByIntent("volume_turn_down", null, getListener());
  //}
  //
  //public void volume_turn_down_min(View view) {
  //  SkillHelper.startSkillByIntent("volume_turn_down_min", null, getListener());
  //}
  //
  //public void volume_turn_mid(View view) {
  //  SkillHelper.startSkillByIntent("volume_turn_mid", null, getListener());
  //}
  //
  //public void volume_turn_up(View view) {
  //  SkillHelper.startSkillByIntent("volume_turn_up", null, getListener());
  //}
  //
  //public void volume_turn_up_max(View view) {
  //  SkillHelper.startSkillByIntent("volume_turn_up_max", null, getListener());
  //}
}

package com.ubtrobot.mini.sdkdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ubtechinc.skill.Paths;
import com.ubtechinc.skill.SkillApi;
import com.ubtechinc.skill.SkillHelper;
import com.ubtrobot.commons.ResponseListener;
import com.ubtrobot.mini.sdkdemo.skills.InterruptibleSkill;
import com.ubtrobot.mini.sdkdemo.skills.LowpowerInterruptibleSkill;
import com.ubtrobot.mini.sdkdemo.skills.LowpowerUnInterruptibleSkill;
import com.ubtrobot.mini.sdkdemo.skills.NoRecordInterrutibleSkill;
import com.ubtrobot.mini.sdkdemo.skills.NoRecordUnInterrutibleSkill;
import com.ubtrobot.mini.sdkdemo.skills.ParallelSkill;
import com.ubtrobot.mini.sdkdemo.skills.UnInterruptibleSkill;

import java.util.List;

public class SkillApiActivity extends AppCompatActivity {
    List<SkillApi.SKILL_NAME> skill_types = SkillApi.get().loadAllSkills();
    private static final String TAG = DemoApp.DEBUG_TAG;
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_api);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"start " + skill_types.get(index));
                SkillApi.get().startSkill( skill_types.get(index), new ResponseListener<Void>() {
                    @Override
                    public void onResponseSuccess(Void aVoid) {
                        Log.i(TAG,"onResponseSuccess");
                    }

                    @Override
                    public void onFailure(int i, @NonNull String s) {
                        Log.i(TAG,"onFailure i = " + i);
                    }
                });
                index++;
                if(index >= skill_types.size()) {
                    index = 0;
                }

            }
        });

        findViewById(R.id.btn_startSkill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoSkill.startThisSkill();
                Log.d(TAG,"start skill");
            }
        });

        findViewById(R.id.btn_stopSkill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DemoSkill.stopThisSkill();
                Log.d(TAG,"stop skill");
            }
        });
    }

    public void startInterruptible(View view) {
        SkillHelper.startSkillByPath("/demo_interruptible/startSkill", null);
    }

    public void stopInterruptible(View view) {
        SkillHelper.stopSkill(InterruptibleSkill.class);
    }



    public void startUnInterruptible(View view) {
        SkillHelper.startSkillByPath("/demo_uninterruptible/startSkill", null);
    }

    public void stopUnInterruptible(View view) {
        SkillHelper.stopSkill(UnInterruptibleSkill.class);
    }



    public void startParallel(View view) {
        SkillHelper.startSkillByPath("/demo_parallel/startSkill", null);
    }

    public void stopParallel(View view) {
        SkillHelper.stopSkill(ParallelSkill.class);
    }



    public void startNoRecInterruptible(View view) {
        SkillHelper.startSkillByPath("/demo_norec_in_skill/startSkill", null);
    }

    public void stopNoRecInterruptible(View view) {
        SkillHelper.stopSkill(NoRecordInterrutibleSkill.class);
    }



    public void startNoRecUnInterruptible(View view) {
        SkillHelper.startSkillByPath("/demo_norec_unin_skill/startSkill", null);
    }

    public void stopNoRecUnInterruptible(View view) {
        SkillHelper.stopSkill(NoRecordUnInterrutibleSkill.class);
    }



    public void startLPInterruptible(View view) {
        SkillHelper.startSkillByPath("/demo_lpinterruptible/startSkill", null);
    }

    public void stopLPInterruptible(View view) {
        SkillHelper.stopSkill(LowpowerInterruptibleSkill.class);
    }



    public void startLPUnInterruptible(View view) {
        SkillHelper.startSkillByPath("/demo_lpuninterruptible/startSkill", null);
    }

    public void stopLPUnInterruptible(View view) {
        SkillHelper.stopSkill(LowpowerUnInterruptibleSkill.class);
    }
}

package com.ubtrobot.mini.sdkdemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ubtech.utilcode.utils.FileUtils;
import com.ubtrobot.commons.Priority;
import com.ubtrobot.mini.libs.behaviors.Behavior;
import com.ubtrobot.mini.libs.behaviors.BehaviorStopCaused;
import com.ubtrobot.mini.libs.behaviors.BehaviorUtils;
import com.ubtrobot.mini.properties.sdk.PropertiesApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class BehaviorActivity extends Activity {
  private static final String TAG = DemoApp.DEBUG_TAG;
  ArrayAdapter<String> adapter;
  Behavior behavior;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupBehaviorListView();

    findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (behavior != null) {
          behavior.stop();
        }
      }
    });
  }

  private void setupBehaviorListView() {
    ListView listView = findViewById(R.id.listView);
    adapter =
        new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    listView.setAdapter(adapter);
    new Thread(new Runnable() {
      @Override public void run() {
        //将系统内置目录/system/files/behaviors下所有配置文件列出
        List<File> files =
            FileUtils.listFilesInDirWithFilter(new File(PropertiesApi.getBehaviorDir()), ".xml",
                false);
        List<String> fileNames = new ArrayList<>();
        if (files != null) {
          for (int i = 0; i < files.size(); ++i) {
            fileNames.add(files.get(i).getName());
            Log.w(TAG, files.get(i).getName());
          }
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileNames.sort(new Comparator<String>() {
              @Override public int compare(String o1, String o2) {
                return o1.compareTo(o2);
              }
            });
          }
        }
        adapter.addAll(fileNames);
      }
    }).start();

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String fileName = adapter.getItem(position);
        if (fileName == null) return;
        try {
          behavior = BehaviorUtils.loadBehavior(fileName.replace(".xml", ""));
          behavior.setPriority(Priority.MAXHIGH);//设置优先级
          behavior.start();
          behavior.setBehaviorListener(new Behavior.BehaviorListener() {
            @Override public void onCompleted(BehaviorStopCaused caused) {
              Log.w(TAG, "behavior completed...");
            }
          });
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }
      }
    });
  }
}

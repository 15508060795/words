package com.hdl.words.base;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activityList=new ArrayList<>();
    public static void addActivity(Activity activity){
        activityList.add(activity);
        Log.i("ActivityCollector","增加:"+activity.getClass().getSimpleName()+",长度:"+activityList.size());
    }
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
        Log.i("ActivityCollector","移除:"+activity.getClass().getSimpleName()+",长度:"+activityList.size());

    }
    public static void finishAllActivity(){
        for (Activity activity:activityList){
            if(!activity.isFinishing()) {
                activity.finish();
                Log.i("ActivityCollector","移除所有的Activity,长度:"+activityList.size());
            }
        }
    }
    public static boolean isLastActivity() {
        if (activityList.size() == 1){
            Log.i("ActivityCollector", "当前Activity处于栈顶,长度:");
            return true;
        } else {
            Log.i("ActivityCollector", "当前Activity未处于栈顶,长度:"+activityList.size());
            return false;
        }
    }
}

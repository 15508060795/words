package com.hdl.words.base;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

public class FragmentCollector {
    public static List<SupportFragment> fragmentList=new ArrayList<>();
    public static void addFragment(SupportFragment fragment){
        fragmentList.add(fragment);
        Log.i("FragmentCollector","增加:"+fragment.getClass().getSimpleName()+",长度:"+fragmentList.size());
    }
    public static void removeFragment(SupportFragment fragment){
        fragmentList.remove(fragment);
        Log.i("FragmentCollector","移除:"+fragment.getClass().getSimpleName()+",长度:"+fragmentList.size());
    }
/*    public static void finishAllFragment(){
        for (Fragment fragment:fragmentList){
            if(!fragment.isDetached()) {
                fragment.onDestroy();
                Log.i("FragmentCollector","移除所有的Activity");
            }
        }
    }*/
    public static boolean isLastFragment() {
        if (fragmentList.size() == 1){
            Log.i("FragmentCollector", "当前Fragment处于栈顶,长度:"+fragmentList.size());
            return true;
        } else {
            Log.i("FragmentCollector", "当前Fragment未处于栈顶,长度:"+fragmentList.size());
            return false;
        }
    }
}

package com.hdl.words.fragment.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.utils.ToastHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by HDL on 2018/1/9.
 */

public class TranslateFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.et_input)
    TextView inputEt;
    public static TranslateFragment newInstance(){
        TranslateFragment fragment=new TranslateFragment();
        return fragment;
    }
    public static TranslateFragment newInstance(Bundle bundle){
        TranslateFragment fragment=new TranslateFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @OnClick({R.id.btn_translate})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_translate:
                ToastHelper.toastTime(getActivity(),"翻译",1000);
                break;
        }
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main_translate;
    }
    @Override
    public void initData() {


    }
    @Override
    public void initTopBar() {
        topBar.setBackgroundColor(getResources().getColor(R.color.black));
        topBar.setTitle(R.string.translate_choose_language).setTextColor(getResources().getColor(R.color.color_topBar_title_black));

//        topBar.addRightImageButton(R.mipmap.add, R.id.topbar_right_add_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String[] listItems = new String[]{
//                        "创建群聊",
//                        "加好友/群",
//                        "扫一扫",
//                        "面对面快传",
//                        "拍摄",
//                        "付款",
//                };
//                List<String> data = new ArrayList<>();
//                Collections.addAll(data, listItems);
//                ArrayAdapter adapter = new ArrayAdapter<>(mActivity, R.layout.simple_list_item, data);
//                qmuiListPopup = new QMUIListPopup(mActivity, QMUIPopup.DIRECTION_TOP, adapter);
//                qmuiListPopup.create(QMUIDisplayHelper.dp2px(mActivity, 120),
//                        QMUIDisplayHelper.dp2px(mActivity, Integer.MAX_VALUE), new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                switch (position) {
//                                    case 0:
//                                        ToastHelper.shortToast(mActivity,listItems[position]);
//                                        qmuiListPopup.dismiss();
//                                        break;
//                                    case 1:
//                                        ToastHelper.shortToast(mActivity,listItems[position]);
//                                        qmuiListPopup.dismiss();
//                                        break;
//                                    case 2:
//                                        ToastHelper.shortToast(mActivity,listItems[position]);
//                                        qmuiListPopup.dismiss();
//                                        break;
//                                    default:
//                                        qmuiListPopup.dismiss();
//                                        break;
//                                }
//                            }
//                        });
//                qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_RIGHT);
//                qmuiListPopup.show(topBar.getChildAt(0));
//            }
//        });
    }

    @Override
    public void initListener() {

    }

}

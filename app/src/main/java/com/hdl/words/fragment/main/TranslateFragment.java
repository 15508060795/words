package com.hdl.words.fragment.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.LanguageBean;
import com.hdl.words.Beans.TranslateResultBean;
import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.implement.GetTranslateRequest_Interface;
import com.hdl.words.utils.ToastHelper;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by HDL on 2018/1/9.
 */

public class TranslateFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.et_input)
    EditText inputEt;
    @BindView(R.id.tv_result)
    TextView resultTv;
    private String input,result,fromText="自动检测",toText="自动检测";
    private int from=0,to=0;
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
                input=inputEt.getText().toString().trim();
                if(!input.isEmpty()){

                }
                getData();
                break;
        }
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main_translate;
    }
    @Override
    public void initData() {
        LanguageBean.initData();
    }
    @Override
    public void initTopBar() {

        topBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        topBar.setTitle(fromText+"—"+toText).setTextColor(getResources().getColor(R.color.color_topBar_title_white));
        topBar.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        topBar.addLeftImageButton(R.mipmap.ic_launcher,0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter adapter = new ArrayAdapter<>(_mActivity, R.layout.simple_list_item, LanguageBean.getLanguage());
                final QMUIListPopup qmuiListPopup = new QMUIListPopup(_mActivity, QMUIPopup.DIRECTION_TOP, adapter);
                qmuiListPopup.create(QMUIDisplayHelper.dp2px(_mActivity, 120),
                        QMUIDisplayHelper.dp2px(_mActivity, 240), new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                fromText=LanguageBean.getLanguage().get(position);
                                from=position;
                                topBar.setTitle(fromText+"—"+toText).setTextColor(getResources().getColor(R.color.color_topBar_title_white));
                                qmuiListPopup.dismiss();
                            }
                        });
                qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_RIGHT);
                qmuiListPopup.show(topBar.getChildAt(0));
            }
        });
        topBar.addRightImageButton(R.mipmap.ic_launcher,0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter adapter = new ArrayAdapter<>(_mActivity, R.layout.simple_list_item, LanguageBean.getLanguage());
                final QMUIListPopup qmuiListPopup = new QMUIListPopup(_mActivity, QMUIPopup.DIRECTION_TOP, adapter);
                qmuiListPopup.create(QMUIDisplayHelper.dp2px(_mActivity, 120),
                        QMUIDisplayHelper.dp2px(_mActivity, 240), new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                toText=LanguageBean.getLanguage().get(position);
                                to=position;
                                topBar.setTitle(fromText+"—"+toText).setTextColor(getResources().getColor(R.color.color_topBar_title_white));
                                qmuiListPopup.dismiss();
                            }
                        });
                qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_RIGHT);
                qmuiListPopup.show(topBar.getChildAt(0));
            }
        });
    }

    @Override
    public void initListener() {
    }
    public void getData(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiBean.TRANSLATE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetTranslateRequest_Interface request=retrofit.create(GetTranslateRequest_Interface.class);
        Call<TranslateResultBean>call=request.getCall(
                "apple",
                "en",
                "zh",
                ApiBean.TRANSLATE_APPID,
                "123",
                "7476c00651cec41851c08e8c54359207");
        call.enqueue(new Callback<TranslateResultBean>() {
            @Override
            public void onResponse(Call<TranslateResultBean> call, Response<TranslateResultBean> response) {
                ToastHelper.shortToast(_mActivity,"请求成功");
                Log.e(TAG,response.body()+"");
                Log.e(TAG,response.body().getTrans_result().get(0).getDst()+"");
                resultTv.setText(response.body().getTrans_result().get(0).getDst()+" ");
            }

            @Override
            public void onFailure(Call<TranslateResultBean> call, Throwable t) {
                ToastHelper.shortToast(_mActivity,"请求失败");
            }
        });
    }
}

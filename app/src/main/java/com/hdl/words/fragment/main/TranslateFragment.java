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
    private String input,result,fromText,toText;
    private int from=0,to=0;
    private ArrayList<String>language;
    private ArrayList<String>languageCode;
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
/*                if(topBar.getVisibility()==View.VISIBLE){
                    topBar.setVisibility(View.INVISIBLE);
                }else{
                    topBar.setVisibility(View.VISIBLE);
                }*/

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
    public void initTopBar() {

        topBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        topBar.setTitle(fromText+"—"+toText).setTextColor(getResources().getColor(R.color.color_topBar_title));
        topBar.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,LanguageBean.getInstance().getLanguage().size()+"");
                Log.e(TAG,LanguageBean.getInstance().getLanguageCode().size()+"");
/*                LanguageBean.getInstance().getLanguage();
                LanguageBean.getInstance().getLanguageCode();*/
            }
        });
        /*topBar.addLeftImageButton(R.mipmap.ic_launcher,0).setOnClickListener(new View.OnClickListener() {
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
        });*/
    }
    @Override
    public void initData() {
        initList();
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
    private void initList(){
        language=new ArrayList<>();
        languageCode=new ArrayList<>();
        language.add("自动检测");
        language.add("中文");
        language.add("英语");
        language.add("粤语");
        language.add("文言文");
        language.add("日语");
        language.add("韩语");
        language.add("法语");
        language.add("西班牙语");
        language.add("泰语");
        language.add("阿拉伯语");
        language.add("俄语");
        language.add("葡萄牙语");
        language.add("德语");
        language.add("意大利语");
        language.add("希腊语");
        language.add("荷兰语");
        language.add("波兰语");
        language.add("保加利亚语");
        language.add("爱沙尼亚语");
        language.add("丹麦语");
        language.add("芬兰语");
        language.add("捷克语");
        language.add("罗马尼亚语");
        language.add("斯洛文尼亚语");
        language.add("瑞典语");
        language.add("匈牙利语");
        language.add("繁体中文");
        language.add("越南语");
        languageCode.add("auto");
        languageCode.add("zh");
        languageCode.add("en");
        languageCode.add("yue");
        languageCode.add("wyw");
        languageCode.add("jp");
        languageCode.add("kor");
        languageCode.add("fra");
        languageCode.add("spa");
        languageCode.add("th");
        languageCode.add("ara");
        languageCode.add("ru");
        languageCode.add("pt");
        languageCode.add("de");
        languageCode.add("it");
        languageCode.add("el");
        languageCode.add("nl");
        languageCode.add("pl");
        languageCode.add("bul");
        languageCode.add("est");
        languageCode.add("dan");
        languageCode.add("fin");
        languageCode.add("cs");
        languageCode.add("rom");
        languageCode.add("slo");
        languageCode.add("swe");
        languageCode.add("hu");
        languageCode.add("cht");
        languageCode.add("vie");
    }
}

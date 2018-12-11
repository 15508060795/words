package com.hdl.words.fragment.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by HDL on 2018/11/9.
 */

public class TranslateFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.et_input)
    EditText inputEt;
    @BindView(R.id.tv_result)
    TextView resultTv;
    private Button leftTopBarBtn,rightTopBarBtn;
    private ImageView titleTopBarImg;
    private String input,result,fromText,toText;
    private int fromPosition=0,toPosition=0;
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
                    translate(input);
                }
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
        leftTopBarBtn=topBar.addLeftTextButton(LanguageBean.getInstance().getLanguage().get(0),0);
        leftTopBarBtn.setTextColor(getResources().getColor(R.color.white));
        titleTopBarImg=new ImageView(_mActivity);
        titleTopBarImg.setImageResource(R.mipmap.ic_switch);
        topBar.setCenterView(titleTopBarImg);
        rightTopBarBtn =topBar.addRightTextButton(LanguageBean.getInstance().getLanguage().get(1),0);
        rightTopBarBtn.setTextColor(getResources().getColor(R.color.white));
    }
    @Override
    public void initData() {
        fromPosition=0;
        toPosition=1;
    }
    @Override
    public void initListener() {
        leftTopBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter adapter = new ArrayAdapter<>(_mActivity, R.layout.simple_list_item, LanguageBean.getInstance().getLanguage());
                final QMUIListPopup qmuiListPopup = new QMUIListPopup(_mActivity, QMUIPopup.DIRECTION_TOP, adapter);
                qmuiListPopup.create(QMUIDisplayHelper.dp2px(_mActivity, 120),
                        QMUIDisplayHelper.dp2px(_mActivity, 240), new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                fromText=LanguageBean.getInstance().getLanguage().get(position);
                                fromPosition=position;
                                leftTopBarBtn.setText(LanguageBean.getInstance().getLanguage().get(position));
                                qmuiListPopup.dismiss();
                            }
                        });
                qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_LEFT);
                qmuiListPopup.show(leftTopBarBtn);
            }
        });

        rightTopBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter adapter = new ArrayAdapter<>(_mActivity, R.layout.simple_list_item, LanguageBean.getInstance().getLanguage());
                final QMUIListPopup qmuiListPopup = new QMUIListPopup(_mActivity, QMUIPopup.DIRECTION_TOP, adapter);
                qmuiListPopup.create(QMUIDisplayHelper.dp2px(_mActivity, 120),
                        QMUIDisplayHelper.dp2px(_mActivity, 240), new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                toText=LanguageBean.getInstance().getLanguage().get(position);
                                toPosition=position;
                                rightTopBarBtn.setText(LanguageBean.getInstance().getLanguage().get(position));
                                qmuiListPopup.dismiss();
                            }
                        });
                qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_RIGHT);
                qmuiListPopup.show(rightTopBarBtn);
            }
        });
        titleTopBarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator=ObjectAnimator.ofFloat(titleTopBarImg,"rotation", 0f, 180f);
                animator.setDuration(500);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        titleTopBarImg.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        titleTopBarImg.setClickable(true);
                        int position=fromPosition;
                        fromPosition=toPosition;
                        toPosition=position;
                        leftTopBarBtn.setText(LanguageBean.getInstance().getLanguage().get(fromPosition));
                        rightTopBarBtn.setText(LanguageBean.getInstance().getLanguage().get(toPosition));
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();
            }
        });
    }

    private void translate(String input){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiBean.TRANSLATE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetTranslateRequest_Interface request=retrofit.create(GetTranslateRequest_Interface.class);
        String sign=ApiBean.getStringMD5(ApiBean.TRANSLATE_APP_ID+input+ApiBean.TRANSLATE_SALT+ApiBean.TRANSLATE_KEY);
        Call<TranslateResultBean>call=request.getCall(
                input,
                LanguageBean.getInstance().getLanguage().get(fromPosition),
                LanguageBean.getInstance().getLanguageCode().get(toPosition),
                ApiBean.TRANSLATE_APP_ID,
                ApiBean.TRANSLATE_SALT,
                sign);
        call.enqueue(new Callback<TranslateResultBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<TranslateResultBean> call, Response<TranslateResultBean> response) {
                ToastHelper.shortToast(_mActivity,"请求成功");
                Log.e(TAG,response.body()+"");
                Log.e(TAG,response.body().getTrans_result().get(0).getDst()+"");
                result=response.body().getTrans_result().get(0).getDst();
                resultTv.setText(result);
            }
            @Override
            public void onFailure(Call<TranslateResultBean> call, Throwable t) {
                ToastHelper.shortToast(_mActivity,"请求失败");
            }
        });
    }
/*    private void initList(){
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
        fromPositon=0;
        toPosition=1;
    }*/
}

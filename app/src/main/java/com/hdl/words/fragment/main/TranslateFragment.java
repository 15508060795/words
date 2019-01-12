package com.hdl.words.fragment.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private int fromPosition=0,toPosition=1;
    public static TranslateFragment newInstance(){
        return new TranslateFragment();
    }
    @OnClick({R.id.btn_translate})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_translate:
                String inputStr = inputEt.getText().toString().trim();
                if(!inputStr.isEmpty()){
                    translate(inputStr);
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
        leftTopBarBtn = topBar.addLeftTextButton(LanguageBean.getInstance().getLanguage().get(0),0);
        leftTopBarBtn.setTextColor(getResources().getColor(R.color.color_topBar_title));
        titleTopBarImg = new ImageView(_mActivity);
        titleTopBarImg.setImageResource(R.mipmap.ic_switch);
        topBar.setCenterView(titleTopBarImg);
        rightTopBarBtn = topBar.addRightTextButton(LanguageBean.getInstance().getLanguage().get(1),0);
        rightTopBarBtn.setTextColor(getResources().getColor(R.color.color_topBar_title));
    }
    @Override
    public void initData() {
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
                            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                fromPosition = pos;
                                leftTopBarBtn.setText(LanguageBean.getInstance().getLanguage().get(pos));
                                qmuiListPopup.dismiss();
                            }
                        });
                qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_LEFT);
                qmuiListPopup.show(leftTopBarBtn);
            }
        });
        titleTopBarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator animator=ObjectAnimator.ofFloat(titleTopBarImg,"rotation", 0f, 180f);
                animator.setDuration(250);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        titleTopBarImg.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        int position=fromPosition;
                        fromPosition=toPosition;
                        toPosition=position;
                        leftTopBarBtn.setText(LanguageBean.getInstance().getLanguage().get(fromPosition));
                        rightTopBarBtn.setText(LanguageBean.getInstance().getLanguage().get(toPosition));
                        titleTopBarImg.setClickable(true);
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
        rightTopBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayAdapter adapter = new ArrayAdapter<>(_mActivity, R.layout.simple_list_item, LanguageBean.getInstance().getLanguage());
                final QMUIListPopup qmuiListPopup = new QMUIListPopup(_mActivity, QMUIPopup.DIRECTION_TOP, adapter);
                qmuiListPopup.create(QMUIDisplayHelper.dp2px(_mActivity, 120),
                        QMUIDisplayHelper.dp2px(_mActivity, 240), new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                toPosition=position;
                                rightTopBarBtn.setText(LanguageBean.getInstance().getLanguage().get(position));
                                qmuiListPopup.dismiss();
                            }
                        });
                qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_RIGHT);
                qmuiListPopup.show(rightTopBarBtn);
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
                String resultStr=response.body().getTrans_result().get(0).getDst();
                resultTv.setText(resultStr);
            }
            @Override
            public void onFailure(Call<TranslateResultBean> call, Throwable t) {
                ToastHelper.shortToast(_mActivity,"请求失败");
            }
        });
    }

}

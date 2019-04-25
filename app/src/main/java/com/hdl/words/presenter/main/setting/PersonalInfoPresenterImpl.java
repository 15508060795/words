package com.hdl.words.presenter.main.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.InputType;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.PersonalInfoBean;
import com.hdl.words.Beans.SetPersonalBirthResultBean;
import com.hdl.words.Beans.SetPersonalHomeTownResultBean;
import com.hdl.words.Beans.SetPersonalNameResultBean;
import com.hdl.words.Beans.SetPersonalSexResultBean;
import com.hdl.words.Beans.SetPersonalSignResultBean;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.main.setting.PersonalInfoFragment;
import com.hdl.words.model.IGETPersonalInfoResult;
import com.hdl.words.model.IGETSetPersonalBirthResult;
import com.hdl.words.model.IGETSetPersonalHometownResult;
import com.hdl.words.model.IGETSetPersonalNameResult;
import com.hdl.words.model.IGETSetPersonalSexResult;
import com.hdl.words.model.IGETSetPersonalSignResult;
import com.hdl.words.model.PersonalInfoModelImpl;
import com.hdl.words.utils.TimeUtil;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.Objects;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date 2019/4/25 23:35
 * author hdl
 * Description:
 */
public class PersonalInfoPresenterImpl extends BasePresenter<PersonalInfoFragment> implements IPersonalInfoPresenter {
    private PersonalInfoModelImpl model;
    private Context mContent;
    private String username;

    public PersonalInfoPresenterImpl(PersonalInfoFragment view) {
        super(view);
        model = PersonalInfoModelImpl.getInstance();
        mContent = mView.getActivity();
        username = MySession.getUsername(mContent);
    }

    @Override
    public void setName(String str) {
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(mContent);
        builder.setTitle("昵称")
                .setDefaultText(str)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .setCanceledOnTouchOutside(false)
                .addAction("取消", (dialog, index) -> {
                    dialog.dismiss();
                })
                .addAction("确定", (dialog, index) -> {

                    String name = builder.getEditText().getText().toString();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiBean.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    IGETSetPersonalNameResult request = retrofit.create(IGETSetPersonalNameResult.class);
                    Call<SetPersonalNameResultBean> call = request.getCall(username, name);
                    call.enqueue(new Callback<SetPersonalNameResultBean>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<SetPersonalNameResultBean> call, @NonNull Response<SetPersonalNameResultBean> response) {
                            try {
                                if (response.body().isResult()) {
                                    model.getBean().setName(name);
                                    mView.refreshUI(model.getBean());
                                    dialog.dismiss();
                                }
                            } catch (NullPointerException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SetPersonalNameResultBean> call, @NonNull Throwable t) {
                            Log.e(TAG, "网络错误");
                        }
                    });

                })
                .create().show();
    }

    @Override
    public void setSex(String str) {
        OptionPicker optionPicker = new OptionPicker(Objects.requireNonNull(mView.getActivity()), new String[]{"女", "男"});
        optionPicker.setSelectedItem(str);
        optionPicker.setCanceledOnTouchOutside(true);
        optionPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int i, String s) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiBean.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                IGETSetPersonalSexResult request = retrofit.create(IGETSetPersonalSexResult.class);
                Call<SetPersonalSexResultBean> call = request.getCall(username, i);
                call.enqueue(new Callback<SetPersonalSexResultBean>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<SetPersonalSexResultBean> call, @NonNull Response<SetPersonalSexResultBean> response) {
                        try {
                            if (response.body().isResult()) {
                                model.getBean().setSex(i);
                                mView.refreshUI(model.getBean());
                            }
                        } catch (NullPointerException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SetPersonalSexResultBean> call, @NonNull Throwable t) {
                        Log.e(TAG, "网络错误");
                    }
                });
            }
        });
        optionPicker.show();
    }

    @Override
    public void setBirth(String str) {
        String[] date = str.split("-");
        DatePicker datePicker = new DatePicker(Objects.requireNonNull(mView.getActivity()));
        datePicker.setCanceledOnTouchOutside(true);
        datePicker.setRangeStart(1970, 1, 1);
        int year = Integer.parseInt(TimeUtil.getCurrentDate(TimeUtil.dateFormatYEAR));
        int month = Integer.parseInt(TimeUtil.getCurrentDate(TimeUtil.dateFormatMONTH));
        int day = Integer.parseInt(TimeUtil.getCurrentDate(TimeUtil.dateFormatDAY));
        datePicker.setRangeEnd(year, month, day);
        datePicker.setSelectedItem(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        datePicker.setOnDatePickListener((DatePicker.OnYearMonthDayPickListener) (s, s1, s2) -> {
                    String birth = s + "-" + s1 + "-" + s2;
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiBean.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    IGETSetPersonalBirthResult request = retrofit.create(IGETSetPersonalBirthResult.class);
                    Call<SetPersonalBirthResultBean> call = request.getCall(username, birth);
                    call.enqueue(new Callback<SetPersonalBirthResultBean>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<SetPersonalBirthResultBean> call, @NonNull Response<SetPersonalBirthResultBean> response) {
                            try {
                                if (response.body().isResult()) {
                                    model.getBean().setBirth(birth);
                                    mView.refreshUI(model.getBean());
                                }
                            } catch (NullPointerException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SetPersonalBirthResultBean> call, @NonNull Throwable t) {
                            Log.e(TAG, "网络错误");
                        }
                    });
                }
        );
        datePicker.show();
    }

    @Override
    public void setHometown(String str) {
        String[] hometowns = str.split("-");
        CityPicker cityPicker = new CityPicker.Builder(mView.getActivity())
                .textSize(18)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province(hometowns[0])
                .city(hometowns[1])
                .district(hometowns[2])
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(citySelected -> {
            //省份
            String province = citySelected[0];
            //城市
            String city = citySelected[1];
            //区域
            String district = citySelected[2];

            String hometown = province + "-" + city + "-" + district;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiBean.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IGETSetPersonalHometownResult request = retrofit.create(IGETSetPersonalHometownResult.class);
            Call<SetPersonalHomeTownResultBean> call = request.getCall(username, hometown);
            call.enqueue(new Callback<SetPersonalHomeTownResultBean>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<SetPersonalHomeTownResultBean> call, @NonNull Response<SetPersonalHomeTownResultBean> response) {
                    try {
                        if (response.body().isResult()) {
                            model.getBean().setHometown(hometown);
                            mView.refreshUI(model.getBean());
                        }
                    } catch (NullPointerException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<SetPersonalHomeTownResultBean> call, @NonNull Throwable t) {
                    Log.e(TAG, "网络错误");
                }
            });

        });
    }

    @Override
    public void setSign(String str) {
        QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(mView.getActivity());
        builder.setTitle("个性签名")
                .setDefaultText(str)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .setCanceledOnTouchOutside(false)
                .addAction("取消", (dialog, index) -> {
                    dialog.dismiss();
                })
                .addAction("确定", (dialog, index) -> {
                    String sign = builder.getEditText().getText().toString();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiBean.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    IGETSetPersonalSignResult request = retrofit.create(IGETSetPersonalSignResult.class);
                    Call<SetPersonalSignResultBean> call = request.getCall(username, sign);
                    call.enqueue(new Callback<SetPersonalSignResultBean>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(@NonNull Call<SetPersonalSignResultBean> call, @NonNull Response<SetPersonalSignResultBean> response) {
                            try {
                                if (response.body().isResult()) {
                                    model.getBean().setSign(sign);
                                    mView.refreshUI(model.getBean());
                                    dialog.dismiss();
                                }
                            } catch (NullPointerException e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<SetPersonalSignResultBean> call, @NonNull Throwable t) {
                            Log.e(TAG, "网络错误");
                        }
                    });

                })
                .create().show();
    }

    @Override
    public void requestDate(String username) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGETPersonalInfoResult request = retrofit.create(IGETPersonalInfoResult.class);
        Call<PersonalInfoBean> call = request.getCall(username);
        call.enqueue(new Callback<PersonalInfoBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<PersonalInfoBean> call, @NonNull Response<PersonalInfoBean> response) {
                try {
                    PersonalInfoBean.DataBean bean = response.body().getData();
                    PersonalInfoModelImpl.getInstance().setBean(bean);
                    mView.refreshUI(bean);
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PersonalInfoBean> call, @NonNull Throwable t) {
                Log.e(TAG, "网络错误");
            }
        });
    }

    @Override
    public void changeInfo(int pos) {


    }


}

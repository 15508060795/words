package com.hdl.words.fragment.main.recite;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdl.words.Beans.WordResultBean;
import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.model.WordModelImpl;
import com.hdl.words.presenter.main.recite.TypePresenterImpl;
import com.hdl.words.utils.ToastHelper;
import com.hdl.words.view.main.recite.ITypeView;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date 2019/4/12 16:11
 * author HDL
 * Mail 229101253@qq.com
 */
public class TypeFragment extends BaseFragment implements ITypeView {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.empty_view)
    QMUIEmptyView mEmptyView;
    @BindView(R.id.fl_main)
    FrameLayout mMainFl;
    @BindView(R.id.tv_word)
    TextView mWordTv;
    @BindView(R.id.tv_symbol)
    TextView mSymbolTv;
    @BindView(R.id.tv_mean)
    TextView mMeanTv;
    @BindView(R.id.btn_voice)
    Button mVoiceBtn;
    @BindView(R.id.btn_last)
    QMUIRoundButton mLastBtn;
    @BindView(R.id.btn_next)
    QMUIRoundButton mNextBtn;
    @BindView(R.id.img_vocab)
    ImageView mVocabImg;
    private TypePresenterImpl presenter;
    private TextToSpeech textToSpeech;
    private int mPosition = 0;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            presenter.refreshView(mPosition);
            Log.e(TAG, "界面刷新");
        }
    }

    @OnClick({R.id.btn_voice, R.id.btn_last, R.id.btn_next, R.id.img_vocab})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_voice:
                textToSpeech.speak(
                        WordModelImpl.getInstance().getDataList().get(mPosition).getWord(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                );
                break;
            case R.id.btn_last:
                presenter.skipLast(mPosition);
                break;
            case R.id.btn_next:
                presenter.skipNext(mPosition);
                break;
            case R.id.img_vocab:
                String username = MySession.getUsername(_mActivity);
                presenter.requestState(username, mPosition);
                break;
        }

    }

    public static TypeFragment newInstance(Bundle bundle) {
        TypeFragment fragment = new TypeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_recite_detail;
    }

    @Override
    public void initTopBar() {
        mTopBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        int[] titles = new int[]{
                R.string.cet_4,
                R.string.cet_6,
                R.string.everyday_word,
                R.string.vocab_word_list
        };
        String title = getString(titles[mBundle.getInt("type")]);
        mTopBar.setTitle(title);
        mTopBar.addRightImageButton(R.drawable.ic_details, 1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                start(WordListFragment.newInstance(bundle));
            }
        });
    }

    @Override
    public void initData() {
        setSwipeBackEnable(true);

        presenter = new TypePresenterImpl(this);
        showLoading();
        textToSpeech = new TextToSpeech(_mActivity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE && result != TextToSpeech.LANG_AVAILABLE) {
                        ToastHelper.shortToast(_mActivity, "暂不支持该语言");
                    }
                }
            }
        });
        textToSpeech.setLanguage(Locale.US);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void showLoading() {
        mEmptyView.setLoadingShowing(true);
        mEmptyView.setTitleText("数据加载中");
        presenter.getWords(mBundle.getInt("type"));
    }

    @Override
    public void dataRequestCompleted(WordResultBean.DataBean bean, int size) {
        mEmptyView.setVisibility(View.GONE);
        mMainFl.setVisibility(View.VISIBLE);
        changeWordView(mPosition, bean, size);
    }

    @Override
    public void showRequestFailDialog() {
        mEmptyView.setTitleText("数据请求失败");
        mEmptyView.setButton("点击重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getWords(mBundle.getInt("type"));
            }
        });
    }

    @Override
    public void changeWordView(int pos, WordResultBean.DataBean bean, int size) {
        Log.e(TAG, "mPosition:" + pos);
        this.mPosition = pos;
        mWordTv.setText(bean.getWord());
        mSymbolTv.setText(bean.getSymbol());
        mMeanTv.setText(bean.getMeans());
        if (bean.getState() == 0) {
            mVocabImg.setImageResource(R.mipmap.ic_like_false);
        } else {
            mVocabImg.setImageResource(R.mipmap.ic_like_true);
        }
        if (pos == 0 && pos == size - 1) {
            mLastBtn.setEnabled(false);
            mNextBtn.setEnabled(false);
        } else if (pos == 0) {
            mLastBtn.setEnabled(false);
        } else if (pos == size - 1) {
            mNextBtn.setEnabled(false);
        } else {
            mLastBtn.setEnabled(true);
            mNextBtn.setEnabled(true);
        }
    }

    @Override
    public void setLike(int pos) {
        mVocabImg.setImageResource(R.mipmap.ic_like_true);
    }

    @Override
    public void setDislike(int pos) {
        mVocabImg.setImageResource(R.mipmap.ic_like_false);
    }

    @Override
    public void showToast(String msg) {
        ToastHelper.shortToast(_mActivity, msg);
    }


}

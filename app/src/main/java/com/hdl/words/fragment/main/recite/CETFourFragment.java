package com.hdl.words.fragment.main.recite;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.model.CETFourWordModelImpl;
import com.hdl.words.presenter.main.recite.CETFourPresenterImpl;
import com.hdl.words.utils.ToastHelper;
import com.hdl.words.view.main.recite.ICETFourView;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.util.Locale;

import butterknife.BindView;

/**
 * Date 2019/4/12 16:11
 * author HDL
 * Mail 229101253@qq.com
 */
public class CETFourFragment extends BaseFragment implements ICETFourView {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.empty_view)
    QMUIEmptyView mEmptyView;
    @BindView(R.id.fl_main)
    FrameLayout mMainFl;
    CETFourPresenterImpl presenter;
    @BindView(R.id.tv_word)
    TextView mWordTv;
    @BindView(R.id.tv_symbol)
    TextView mSymbolTv;
    @BindView(R.id.tv_mean)
    TextView mMeanTv;
    @BindView(R.id.btn_last)
    QMUIRoundButton mLastBtn;
    @BindView(R.id.btn_next)
    QMUIRoundButton mNextBtn;
    private TextToSpeech textToSpeech;
    int position = 0;

    public static CETFourFragment newInstance(Bundle bundle) {
        CETFourFragment fragment = new CETFourFragment();
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
                R.string.wrong_word_list,
                R.string.right_word_list,
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
        presenter = new CETFourPresenterImpl(this);
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
        mWordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.speak(
                        CETFourWordModelImpl.getInstance().getDataList().get(position).getWord(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                );
            }
        });
        mLastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = position > 0 ? position - 1 : 0;
                mWordTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getWord());
                mSymbolTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getSymbol());
                mMeanTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getMeans());
            }
        });
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = position <= CETFourWordModelImpl.getInstance().getDataList().size() - 1 ? position + 1 : CETFourWordModelImpl.getInstance().getDataList().size();
                mWordTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getWord());
                mSymbolTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getSymbol());
                mMeanTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getMeans());
            }
        });
    }

    @Override
    public void showLoading() {
        mEmptyView.setLoadingShowing(true);
        mEmptyView.setTitleText("数据加载中");
        presenter.getAllWords();
    }

    @Override
    public void dataRequestCompleted() {
        mEmptyView.setVisibility(View.GONE);
        mMainFl.setVisibility(View.VISIBLE);
        mWordTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getWord());
        mSymbolTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getSymbol());
        mMeanTv.setText(CETFourWordModelImpl.getInstance().getDataList().get(position).getMeans());
    }

    @Override
    public void showRequestFailDialog() {
        mEmptyView.setTitleText("数据请求失败");
        mEmptyView.setButton("点击重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getAllWords();
            }
        });
    }

}

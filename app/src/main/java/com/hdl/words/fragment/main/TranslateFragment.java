package com.hdl.words.fragment.main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hdl.words.Beans.LanguageBean;
import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.presenter.main.TranslatePresenterImpl;
import com.hdl.words.utils.ToastHelper;
import com.hdl.words.view.main.ITranslateView;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;

import butterknife.BindView;


/**
 * Created by HDL on 2018/11/9.
 */

public class TranslateFragment extends BaseFragment implements ITranslateView {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.search_input)
    SearchView mSearchView;
    @BindView(R.id.tv_result)
    TextView mResultTv;
    private Button mLeftTopBarBtn, mRightTopBarBtn;
    private ImageView mTitleTopBarImg;
    private int mFrom = 0, mTo = 1;
    private String mInputStr;
    private TranslatePresenterImpl mPresenter;

    public static TranslateFragment newInstance() {
        return new TranslateFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_main_translate;
    }

    @Override
    public void initTopBar() {
        mLeftTopBarBtn = mTopBar.addLeftTextButton(LanguageBean.getInstance().getLanguageList().get(0), 0);
        mLeftTopBarBtn.setTextColor(getResources().getColor(R.color.color_topBar_title));
        mTitleTopBarImg = new ImageView(_mActivity);
        mTitleTopBarImg.setImageResource(R.mipmap.ic_switch);
        mTopBar.setCenterView(mTitleTopBarImg);
        mRightTopBarBtn = mTopBar.addRightTextButton(LanguageBean.getInstance().getLanguageList().get(1), 0);
        mRightTopBarBtn.setTextColor(getResources().getColor(R.color.color_topBar_title));
    }

    @Override
    public void initData() {
        mPresenter = new TranslatePresenterImpl(this);
    }

    @Override
    public void initListener() {
        mLeftTopBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.fromTypeChange();
            }
        });
        mTitleTopBarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.typeSwitch();
            }
        });
        mRightTopBarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toTypeChange();
            }
        });
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mInputStr = s;
                if (!s.isEmpty()) {
                    mPresenter.translate(mFrom, mTo, s);
                }
                return true;
            }
        });


    }

    @Override
    public void languageSwitch() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTitleTopBarImg, "rotation", 0f, 180f);
        animator.setDuration(250);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mTitleTopBarImg.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                int position = mFrom;
                mFrom = mTo;
                mTo = position;
                mLeftTopBarBtn.setText(LanguageBean.getInstance().getLanguageList().get(mFrom));
                mRightTopBarBtn.setText(LanguageBean.getInstance().getLanguageList().get(mTo));
                mTitleTopBarImg.setClickable(true);
                mPresenter.translate(mFrom, mTo, mInputStr);
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

    @Override
    public void translateResult(String result) {
        mResultTv.setText(result);
    }

    @Override
    public void fromTypeChange() {
        ArrayAdapter adapter = new ArrayAdapter<>(_mActivity, R.layout.simple_list_item, LanguageBean.getInstance().getLanguageList());
        final QMUIListPopup qmuiListPopup = new QMUIListPopup(_mActivity, QMUIPopup.DIRECTION_TOP, adapter);
        qmuiListPopup.create(QMUIDisplayHelper.dp2px(_mActivity, 120),
                QMUIDisplayHelper.dp2px(_mActivity, 240), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                        mFrom = pos;
                        mLeftTopBarBtn.setText(LanguageBean.getInstance().getLanguageList().get(pos));
                        mPresenter.translate(mFrom, mTo, mInputStr);
                        qmuiListPopup.dismiss();
                    }
                });
        qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_LEFT);
        qmuiListPopup.show(mLeftTopBarBtn);
    }

    @Override
    public void toTypeChange() {
        ArrayAdapter adapter = new ArrayAdapter<>(_mActivity, R.layout.simple_list_item, LanguageBean.getInstance().getLanguageList());
        final QMUIListPopup qmuiListPopup = new QMUIListPopup(_mActivity, QMUIPopup.DIRECTION_TOP, adapter);
        qmuiListPopup.create(QMUIDisplayHelper.dp2px(_mActivity, 120),
                QMUIDisplayHelper.dp2px(_mActivity, 240), new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mTo = position;
                        mRightTopBarBtn.setText(LanguageBean.getInstance().getLanguageList().get(position));
                        mPresenter.translate(mFrom, mTo, mInputStr);
                        qmuiListPopup.dismiss();
                    }
                });
        qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_RIGHT);
        qmuiListPopup.show(mRightTopBarBtn);
    }

    @Override
    public void showToast(int resId) {
        ToastHelper.shortToast(_mActivity, resId);
    }

}

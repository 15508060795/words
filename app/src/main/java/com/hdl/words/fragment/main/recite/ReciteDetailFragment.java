package com.hdl.words.fragment.main.recite;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;

/**
 * Date 2019/1/12 20:12
 * author HDL
 * Mail 229101253@qq.com
 */
public class ReciteDetailFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.empty_view)
    QMUIEmptyView mEmptyView;
    @BindView(R.id.imageView)
    ImageView mImageView;

    public static ReciteDetailFragment newInstance(Bundle bundle) {
        ReciteDetailFragment fragment = new ReciteDetailFragment();
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
    }

    @Override
    public void initData() {
        setSwipeBackEnable(true);

    }

    @Override
    public void initListener() {
        mEmptyView.setLoadingShowing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEmptyView.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }
}

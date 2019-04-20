package com.hdl.words.fragment.main.recite;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hdl.words.Beans.WordResultBean;
import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.base.BaseRecyclerAdapter;
import com.hdl.words.base.RecyclerViewHolder;
import com.hdl.words.decoration.RecycleViewDivider;
import com.hdl.words.weight.SlideBarSearchView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

import butterknife.BindView;

/**
 * Date 2019/1/12 20:12
 * author HDL
 * Mail 229101253@qq.com
 */
public class WordListFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.rv_word_list)
    RecyclerView mWordListRv;
    @BindView(R.id.rsv_list)
    SlideBarSearchView mListRSV;
    private ItemAdapter mAdapter;
    private List<WordResultBean.DataBean> mDataList;


    public static WordListFragment newInstance(Bundle bundle) {
        WordListFragment fragment = new WordListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_word_list;
    }

    @Override
    public void initTopBar() {
        mTopBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        mTopBar.setTitle("单词列表");
        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

    }

    @Override
    public void initData() {
        setSwipeBackEnable(true);
        mDataList = (List<WordResultBean.DataBean>) mBundle.getSerializable("list");
        //mDataList = CETFourWordModelImpl.getInstance().getDataList();
        mAdapter = new ItemAdapter(_mActivity, mDataList);
        mWordListRv.setLayoutManager(new LinearLayoutManager(
                        _mActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );
        mWordListRv.addItemDecoration(new RecycleViewDivider(
                _mActivity, LinearLayoutManager.VERTICAL, R.drawable.divider_mileage) );
        mWordListRv.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mListRSV.setOnItemTouchListener(new SlideBarSearchView.OnItemTouchListener() {
            @Override
            public void onItemTouch(int pos, String item) {
                if (mDataList != null) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        if (mDataList.get(i).getWord().toUpperCase().charAt(0) == item.charAt(0)) {
                            mWordListRv.scrollToPosition(i);
                            break;
                        }
                    }
                }

            }
        });
    }

    static class ItemAdapter extends BaseRecyclerAdapter<WordResultBean.DataBean> {

        public ItemAdapter(Context ctx, List<WordResultBean.DataBean> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_word_layout;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, WordResultBean.DataBean item) {
            holder.getTextView(R.id.tv_item_word).setText(item.getWord());
            holder.getTextView(R.id.tv_item_symbol).setText(item.getSymbol());
            holder.getTextView(R.id.tv_item_mean).setText(item.getMeans());
        }
    }
}

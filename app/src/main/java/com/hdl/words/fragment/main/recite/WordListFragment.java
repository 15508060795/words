package com.hdl.words.fragment.main.recite;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.hdl.words.Beans.WordResultBean;
import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.base.BaseRecyclerAdapter;
import com.hdl.words.base.RecyclerViewHolder;
import com.hdl.words.decoration.RecycleViewDivider;
import com.hdl.words.model.WordModelImpl;
import com.hdl.words.presenter.main.recite.WordListPresenterImpl;
import com.hdl.words.utils.ToastHelper;
import com.hdl.words.view.main.recite.IWordListView;
import com.hdl.words.weight.SlideBarSearchView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.List;

import butterknife.BindView;

/**
 * Date 2019/1/12 20:12
 * author HDL
 * Mail 229101253@qq.com
 */
public class WordListFragment extends BaseFragment implements IWordListView {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.rv_word_list)
    RecyclerView mWordListRv;
    @BindView(R.id.rsv_list)
    SlideBarSearchView mListRSV;
    private ItemAdapter mAdapter;
    private List<WordResultBean.DataBean> mDataList;
    private WordListPresenterImpl presenter;
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
        presenter = new WordListPresenterImpl(this);
        mDataList = WordModelImpl.getInstance().getDataList();
        mAdapter = new ItemAdapter(_mActivity, mDataList);
        mWordListRv.setLayoutManager(new LinearLayoutManager(
                        _mActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );
        mWordListRv.addItemDecoration(new RecycleViewDivider(
                _mActivity, LinearLayoutManager.VERTICAL, R.drawable.divider_mileage));
        mWordListRv.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {


            }
        });
        mAdapter.setOnWeightClick(new ItemAdapter.OnImgClick() {
            @Override
            public void onImgClick(View parent, final ImageView img, final int pos) {
                String username = MySession.getUsername(_mActivity);
                if (mDataList.get(pos).getState() == 0) {
                    presenter.requestLike(username,mDataList.get(pos).getWord(),img,pos);
                } else {
                    presenter.requestDisLike(username,mDataList.get(pos).getWord(),img,pos);
                }
            }
        });

        mListRSV.setOnItemTouchListener(new SlideBarSearchView.OnItemTouchListener() {
            @Override
            public void onItemTouch(int pos, String item) {
                if (mDataList != null) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        if (mDataList.get(i).getWord().toUpperCase().charAt(0) == item.charAt(0)) {
                            scrollList(i);
                            break;
                        }
                    }
                }

            }
        });
    }

    @Override
    public void scrollList(int pos) {
        mWordListRv.scrollToPosition(pos);
    }

    @Override
    public void setLike(ImageView img, int pos) {
        mDataList.get(pos).setState(1);
        img.setImageResource(R.mipmap.ic_like_true);
    }

    @Override
    public void setDislike(ImageView img, int pos) {
        mDataList.get(pos).setState(0);
        img.setImageResource(R.mipmap.ic_like_false);
    }

    @Override
    public void showToast(String msg) {
        ToastHelper.shortToast(_mActivity,msg);
    }


    static class ItemAdapter extends BaseRecyclerAdapter<WordResultBean.DataBean> {
        private OnImgClick onWeightClick;

        ItemAdapter(Context ctx, List<WordResultBean.DataBean> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_word_layout;
        }

        @Override
        public void bindData(final RecyclerViewHolder holder, final int position, final WordResultBean.DataBean item) {
            final ImageView img = holder.getImageView(R.id.img_state);
            if (item.getState() == 0) {
                img.setImageResource(R.mipmap.ic_like_false);
            } else {
                img.setImageResource(R.mipmap.ic_like_true);
            }
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onWeightClick != null) {
                        onWeightClick.onImgClick(holder.itemView, img, position);
                    }
                }
            });
            holder.getTextView(R.id.tv_item_word).setText(item.getWord());
            holder.getTextView(R.id.tv_item_symbol).setText(item.getSymbol());
            holder.getTextView(R.id.tv_item_mean).setText(item.getMeans());

        }

        void setOnWeightClick(OnImgClick onWeightClick) {
            this.onWeightClick = onWeightClick;
        }

        interface OnImgClick {
            void onImgClick(View parent, ImageView img, int pos);
        }
    }
}

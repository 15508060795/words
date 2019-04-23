package com.hdl.words.fragment.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hdl.words.Beans.ItemDescription;
import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.base.BaseRecyclerAdapter;
import com.hdl.words.base.RecyclerViewHolder;
import com.hdl.words.decoration.GridDividerItemDecoration;
import com.hdl.words.fragment.MainFragment;
import com.hdl.words.fragment.main.recite.TypeFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by HDL on 2018/11/9.
 */

public class ReciteFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar mTopBar;
    @BindView(R.id.rv_recite)
    RecyclerView mReciteRv;

    public static ReciteFragment newInstance() {
        return new ReciteFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_main_recite;
    }


    @Override
    public void initData() {
        int spanCount = 3;
        mReciteRv.setLayoutManager(new GridLayoutManager(_mActivity, spanCount));
        mReciteRv.addItemDecoration(new GridDividerItemDecoration(_mActivity, spanCount));
        List<ItemDescription> list = new ArrayList<>();
        int[] name = new int[]{
                R.string.cet_4,
                R.string.cet_6,
                R.string.everyday_word,
                R.string.vocab_word_list};
        int[] iconRes = new int[]{};
        for (int i = 0; i < name.length; i++) {
            ItemDescription description = new ItemDescription();
            description.setName(getString(name[i]));
            description.setIconRes(R.mipmap.ic_default_head);
            list.add(description);
        }
        ItemAdapter itemAdapter = new ItemAdapter(_mActivity, list);
        mReciteRv.setAdapter(itemAdapter);
        itemAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                Bundle bundle = new Bundle();
                bundle.putInt("type", pos);
                assert getParentFragment() != null;
                ((MainFragment) getParentFragment()).startBrotherFragment(TypeFragment.newInstance(bundle));
            }
        });

    }

    @Override
    public void initTopBar() {
        mTopBar.setTitle(R.string.recite);
    }

    @Override
    public void initListener() {

    }

    static class ItemAdapter extends BaseRecyclerAdapter<ItemDescription> {

        public ItemAdapter(Context ctx, List<ItemDescription> list) {
            super(ctx, list);
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.item_recite_layout;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, ItemDescription item) {
            holder.setText(R.id.tv_item_name, item.getName());
            if (item.getIconRes() != 0) {
                holder.getImageView(R.id.img_item_icon).setImageResource(item.getIconRes());
            }
        }
    }
}
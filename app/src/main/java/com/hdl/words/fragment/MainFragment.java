package com.hdl.words.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.fragment.main.ReciteFragment;
import com.hdl.words.fragment.main.SettingFragment;
import com.hdl.words.fragment.main.TranslateFragment;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.ashokvarma.bottomnavigation.BottomNavigationBar.BACKGROUND_STYLE_RIPPLE;

/**
 * Date 2018/8/2 9:56
 * auther HDL
 * Mail 229101253@qq.com
 */

public class MainFragment extends BaseFragment {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.bottomBar)//底部状态栏
    QMUITabSegment tabSegment;
    private BaseFragment[] fragments=new BaseFragment[3];
    private int[] tabs=new int[]{R.string.translate,R.string.recite,R.string.personal_center};
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static MainFragment newInstance(){
        MainFragment fragment=new MainFragment();
        return  fragment;
    }
    public static MainFragment newInstance(Bundle bundle){
        MainFragment fragment=new MainFragment();
        fragment.setArguments(bundle);
        return  fragment;
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_main;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(findChildFragment(TranslateFragment.class)==null){
            fragments[FIRST]=TranslateFragment.newInstance();
            fragments[SECOND]=ReciteFragment.newInstance();
            fragments[THIRD]=SettingFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_main,
                    FIRST,
                    fragments[FIRST],
                    fragments[SECOND],
                    fragments[THIRD]);
        }else{
            fragments[FIRST]=findChildFragment(TranslateFragment.class);
            fragments[SECOND]=findChildFragment(ReciteFragment.class);
            fragments[THIRD]=findChildFragment(SettingFragment.class);
        }
    }
    @Override
    public void initData() {
        tabSegment.setDefaultNormalColor(getResources().getColor(R.color.color_bottomBar_normal_bg));
        tabSegment.setDefaultSelectedColor(getResources().getColor(R.color.color_bottomBar_select_bg));
        tabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_translate),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_translate),
                getActivity().getResources().getString(R.string.translate), true));
        tabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_recite),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_recite),
                getActivity().getResources().getString(R.string.recite), true));
        tabSegment.addTab(new QMUITabSegment.Tab(
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_personal_center),
                ContextCompat.getDrawable(getContext(), R.mipmap.ic_bottom_personal_center),
                getActivity().getResources().getString(R.string.personal_center), true));
        tabSegment.selectTab(0);
        tabSelect(0);
        tabSegment.notifyDataChanged();
    }

    @Override
    public void initTopBar() {
        topBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        //topBar.setBackgroundColor(getThemeColor());
//        topBar.addRightImageButton(R.mipmap.add, R.id.topbar_right_add_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String[] listItems = new String[]{
//                        "创建群聊",
//                        "加好友/群",
//                        "扫一扫",
//                        "面对面快传",
//                        "拍摄",
//                        "付款",
//                };
//                List<String> data = new ArrayList<>();
//                Collections.addAll(data, listItems);
//                ArrayAdapter adapter = new ArrayAdapter<>(mActivity, R.layout.simple_list_item, data);
//                qmuiListPopup = new QMUIListPopup(mActivity, QMUIPopup.DIRECTION_TOP, adapter);
//                qmuiListPopup.create(QMUIDisplayHelper.dp2px(mActivity, 120),
//                        QMUIDisplayHelper.dp2px(mActivity, Integer.MAX_VALUE), new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                switch (position) {
//                                    case 0:
//                                        ToastHelper.shortToast(mActivity,listItems[position]);
//                                        qmuiListPopup.dismiss();
//                                        break;
//                                    case 1:
//                                        ToastHelper.shortToast(mActivity,listItems[position]);
//                                        qmuiListPopup.dismiss();
//                                        break;
//                                    case 2:
//                                        ToastHelper.shortToast(mActivity,listItems[position]);
//                                        qmuiListPopup.dismiss();
//                                        break;
//                                    default:
//                                        qmuiListPopup.dismiss();
//                                        break;
//                                }
//                            }
//                        });
//                qmuiListPopup.setAnimStyle(QMUIPopup.ANIM_GROW_FROM_RIGHT);
//                qmuiListPopup.show(topBar.getChildAt(0));
//            }
//        });
    }

    @Override
    public void initListener() {
        tabSegment.setOnTabClickListener(new QMUITabSegment.OnTabClickListener() {
            @Override
            public void onTabClick(int index) {
                tabSelect(index);
            }
        });
    }

    private void tabSelect(int position){
        topBar.setTitle(getString(tabs[position])).setTextColor(getResources().getColor(R.color.color_topBar_title_text));
        showHideFragment(fragments[position]);
    }


    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) { start(targetFragment); }
    public void startBrotherFragmentAndPop(SupportFragment targetFragment) { startWithPop(targetFragment); }

}


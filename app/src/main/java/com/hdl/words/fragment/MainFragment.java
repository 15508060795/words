package com.hdl.words.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.fragment.main.ContactsFragment;
import com.hdl.words.fragment.main.SettingFragment;
import com.hdl.words.fragment.main.TidingsFragment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.popup.QMUIListPopup;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.ashokvarma.bottomnavigation.BottomNavigationBar.BACKGROUND_STYLE_RIPPLE;

/**
 * Date 2018/8/2 9:56
 * auther HDL
 * Mail 229101253@qq.com
 */
public class MainFragment extends BaseFragment {
    @BindView(R.id.layout_fragment_main)
    LinearLayout fragment;
    @BindView(R.id.topbar)
    QMUITopBar topBar;
    @BindView(R.id.bottom_navigation_bar)//底部状态栏
            BottomNavigationBar bottomNavigationBar;
    private BaseFragment[] fragments=new BaseFragment[3];
    private int[] tabs=new int[]{R.string.tidings,R.string.contacts,R.string.setting};
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public MainFragment(){
        newInstance();
    }
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
        if(findChildFragment(TidingsFragment.class)==null){
            fragments[FIRST]=new TidingsFragment();
            fragments[SECOND]=new ContactsFragment();
            fragments[THIRD]=new SettingFragment();
            loadMultipleRootFragment(R.id.fl_main,
                    FIRST,
                    fragments[FIRST],
                    fragments[SECOND],
                    fragments[THIRD]);
        }else{
            fragments[FIRST]=findChildFragment(TidingsFragment.class);
            fragments[SECOND]=findChildFragment(ContactsFragment.class);
            fragments[THIRD]=findChildFragment(SettingFragment.class);
        }
    }
    @Override
    public void initData() {
        bottomNavigationBar
                .setBackgroundStyle(BACKGROUND_STYLE_RIPPLE)
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_left, R.string.tidings))
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_medium, R.string.contacts))
                .addItem(new BottomNavigationItem(R.mipmap.ic_bottom_setting, R.string.setting))
                .setBarBackgroundColor(R.color.white)
                .setInActiveColor(R.color.black)
                .setFirstSelectedPosition(0)
                .initialise();
        tabSelect(0);

    }

    @Override
    public void initTopBar() {
        topBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
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
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                tabSelect(position);
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
    }

    private void tabSelect(int position){
        topBar.setTitle(getString(tabs[position])).setTextColor(getResources().getColor(R.color.white));
        showHideFragment(fragments[position]);
    }


    /**
     * start other BrotherFragment
     */
    public void startBrotherFragment(SupportFragment targetFragment) { start(targetFragment); }
    public void startBrotherFragmentAndPop(SupportFragment targetFragment) { startWithPop(targetFragment); }

}


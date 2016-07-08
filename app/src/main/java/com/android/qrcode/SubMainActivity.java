package com.android.qrcode;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.adapter.MainActivityAdapter;
import com.android.base.BaseAppCompatActivity;
import com.android.qrcode.Account.AccountFragmet;
import com.android.qrcode.Card.CardFragmet;
import com.android.qrcode.Manage.ManageFragmet;
import com.android.qrcode.QuickCard.QuickCardFragment;
import com.android.qrcode.Setting.SettingFragmet;
import com.android.qrcode.SubManage.Account.SubAccountFragmet;
import com.android.qrcode.SubManage.AdPublish.SubAdPublishFragmet;
import com.android.qrcode.SubManage.Manage.SubManageFragmet;
import com.android.utils.Page;
import com.android.utils.Utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class SubMainActivity extends BaseAppCompatActivity implements
        TabLayout.OnTabSelectedListener{


    private SearchView searchView;
    private MainActivityAdapter mainActivityAdapter;
    private List<Fragment> fragmentList = new ArrayList<Fragment>();
    private Page flag = Page.MANAGE;
    @Bind(R.id.toolbar)
    Toolbar toolBar;

    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;

    @Bind(R.id.quick_img)
    ImageView quick_img;
    @Bind(R.id.quick_tx)
    TextView quick_tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    @Override
    public void initView() {
        toolBar.setTitle("");
        toolbar_title.setText(R.string.manage_title);
        setSupportActionBar(toolBar);

        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_search) {
                    Toast.makeText(SubMainActivity.this, R.string.menu_search, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_notification) {
                    Toast.makeText(SubMainActivity.this, R.string.menu_notifications, Toast.LENGTH_SHORT).show();

                } else if (menuItemId == R.id.action_settings) {
                    Toast.makeText(SubMainActivity.this, R.string.menu_settings, Toast.LENGTH_SHORT).show();

                }
                return true;
            }
        });




    }

    @Override
    public void initData() {
        //把页面加入到集合中
        fragmentList.add(new SubManageFragmet());
        fragmentList.add(new SubAdPublishFragmet());
        fragmentList.add(new SubAccountFragmet());
      //  fragmentList.add(new QuickCardFragment());
       /* fragmentList.add(new AccountFragmet());
        fragmentList.add(new SettingFragmet());*/

        //实例化适配器
        mainActivityAdapter = new MainActivityAdapter(
                getSupportFragmentManager(),
                fragmentList
        );

        //如果不设置，默认值为1，那么切换第四个页面的时候，第一个页面在下次展现的时候，会重新加载。
        viewPager.setOffscreenPageLimit(3);
        //绑定适配器
        viewPager.setAdapter(mainActivityAdapter);

        //tab与viewPage关联
        tabLayout.setupWithViewPager(viewPager);
        initTab();
        quick_tx.setText(this.getResources().getString(R.string.ad_str));
    }

    @Override
    public void setListener() {

        //设置页面切换监听
        tabLayout.setOnTabSelectedListener(this);


    }


    /**
     * 设置所有tab的图标和初始化第一个界面
     */
    private void initTab() {
        for (int i = 0; i < fragmentList.size(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            setTabIcon(fragmentList.get(i), tab);
            ViewGroup layout = (ViewGroup) tabLayout.getChildAt(0);
            setIconSpacing((ViewGroup) layout.getChildAt(i));

        }
    }

    /**
     * 添加具有切换效果的图标
     *
     * @param fragment
     * @param tab
     */
    private void setTabIcon(Fragment fragment, TabLayout.Tab tab) {
        Bundle bundle = fragment.getArguments();
        if (bundle != null) {
            int drawableId = bundle.getInt("icon");
            if(drawableId != 0){

                tab.setIcon(getResources().getDrawable(drawableId));
            }
        }
    }

    /**
     * 设置icon和文字的距离
     * @param viewGroup
     */
    private void setIconSpacing(ViewGroup viewGroup) {
        ImageView iconView = (ImageView) viewGroup.getChildAt(0);
        if (iconView != null) {
            ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) iconView.getLayoutParams());
            int bottomMargin = Utils.dpToPx(this, 4);
            if (bottomMargin != lp.bottomMargin) {
                lp.bottomMargin = bottomMargin;
                iconView.requestLayout();
            }
        }
    }


    /**
     * 加载菜单
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);//在菜单中找到对应控件的item
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());

        switch (tab.getPosition()){

            case 0:

                toolbar_title.setText(R.string.manage_title);
                changeQuickIconAndTextColor();
                flag = Page.MANAGE;
                break;
            case 1:
                quick_img.setImageDrawable(this.getResources().getDrawable(R.mipmap.owner_manage));
                quick_tx.setTextColor(this.getResources().getColor(R.color.black_text));
                toolbar_title.setText(R.string.ad_str);
                flag = Page.CARD;
                break;
            case 2:

                toolbar_title.setText(R.string.user_str);
                changeQuickIconAndTextColor();
                flag = Page.QUICKCARD;
                break;
           /* case 3:

                toolbar_title.setText(R.string.account_title);
                flag = Page.ACCOUNT;
                break;
            case 4:

                toolbar_title.setText(R.string.setting_title);
                flag = Page.SETTING;
                break;*/
            default:
                break;

        }
        //上面是给切换的页面赋值标示，但是因为每次点击一个Menu的时候，它就改变一次，点击tablayout不起作用，所以加了下面的方法
        invalidateOptionsMenu();


    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //为了在溢出菜单里添加icon
        setOverflowIconVisible(menu);

        // 显示和隐藏menu
        switch (flag){

            case MANAGE:
                isShowMenu(true,menu);
                break;
            case CARD:
                isShowMenu(false,menu);
                break;
            case QUICKCARD:
                isShowMenu(false,menu);
                break;
            case ACCOUNT:
                isShowMenu(false,menu);
                break;
            case SETTING:
                isShowMenu(false,menu);
                break;
            default:

                break;
        }


        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 利用反射让隐藏在Overflow中的MenuItem显示Icon图标
     * @param menu
     * onMenuOpened方法中调用
     */
    public static void setOverflowIconVisible( Menu menu) {
        if ( menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
    }

    public  void isShowMenu(boolean isShow,Menu menu){

        if(isShow){

            menu.findItem(R.id.action_search).setVisible(true);
            menu.findItem(R.id.action_notification).setVisible(true);
            menu.findItem(R.id.action_settings).setVisible(true);

        }else{

            menu.findItem(R.id.action_search).setVisible(false);
            menu.findItem(R.id.action_notification).setVisible(false);
            menu.findItem(R.id.action_settings).setVisible(false);
        }
    }


    public void changeQuickIconAndTextColor(){

        quick_img.setImageDrawable(this.getResources().getDrawable(R.mipmap.car_manage));
        quick_tx.setTextColor(this.getResources().getColor(R.color.gray_text));

    }
}

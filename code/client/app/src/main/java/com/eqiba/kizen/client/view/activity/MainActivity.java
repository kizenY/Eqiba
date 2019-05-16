package com.eqiba.kizen.client.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.ActivityContent;
import com.eqiba.kizen.client.adpter.dummy.MyContent;
import com.eqiba.kizen.client.adpter.dummy.NewsContent;
import com.eqiba.kizen.client.adpter.dummy.SessionContent;
import com.eqiba.kizen.client.bean.User;
import com.eqiba.kizen.client.contract.MainContract;
import com.eqiba.kizen.client.presenter.MainPresenterImpl;
import com.eqiba.kizen.client.view.fragment.NewsFragment;
import com.eqiba.kizen.client.view.fragment.RecycleListViewFragment;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MyContent.OnListFragmentInteractionListener,
        MainContract.MainView,
        View.OnClickListener {

    private RecycleListViewFragment currentFra;
    private Map<RecycleListViewFragment.TYPE_FRAGMENT,RecycleListViewFragment> fragments;
    private DrawerLayout drawerLayout;
    private TextView username;
    private boolean isOnline=false;
    private MainContract.MainPresenter mPresenter = new MainPresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    /**
     * 寄宿在该Activity下的Fragment中的listView的item的点击响应
     * @param item 被点击的条目
     */
    @Override
    public void onListFragmentInteraction(MyContent item) {
        if (item instanceof SessionContent)
        {
            SessionContent sessionContent = (SessionContent) item;
            Toast.makeText(this,sessionContent.name+"的信息",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ChattingActivity.class);
            intent.putExtra("name",sessionContent.name);
            intent.putExtra("head",sessionContent.resourceId);
            startActivity(intent);
        }
        else if (item instanceof ActivityContent)
        {
            Toast.makeText(this,"活动："+((ActivityContent) item).title,Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,ActivityActivity.class));
        }
        else if (item instanceof NewsFragment)
            Toast.makeText(this,"时闻："+((NewsContent)item).title,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        System.out.println(v.getId());
        ActionBar actionBar = getSupportActionBar();
        switch (v.getId())
        {
            case R.id.button_message_main:
            {

                showMessageBar(actionBar);
                if (!isOnline) {
                    hideConcurrentFragment();
                    break;
                }

                switchFragment(RecycleListViewFragment.TYPE_FRAGMENT.MESSAGE);
                break;
            }
            case R.id.button_activity_main:
            {
                showActivityBar(actionBar);

                switchFragment(RecycleListViewFragment.TYPE_FRAGMENT.ACTIVITY);

                break;
            }
            case R.id.button_news_activity:
            {
                showNewsBar(actionBar);

                if (!isOnline) {
                    hideConcurrentFragment();
                    break;
                }

                switchFragment(RecycleListViewFragment.TYPE_FRAGMENT.NEWS);
                break;
            }
            //bar
            case R.id.mine_button_bar:
            {
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            }
            case R.id.activity_button_bar:
            {
                startActivity(new Intent(this,MyActivityActivity.class));
                break;
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkOnlineStatus();
    }

    private void initView()
    {
        bindView();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        showMessageBar(getSupportActionBar());
    }

    private void initData()
    {
        fragments=new HashMap<>();
    }

    private void bindView()
    {
        findViewById(R.id.button_message_main).setOnClickListener(this);
        findViewById(R.id.button_activity_main).setOnClickListener(this);
        findViewById(R.id.button_news_activity).setOnClickListener(this);

        drawerLayout=findViewById(R.id.drawerLayout_main);
        NavigationView navigationView = findViewById(R.id.navigation);
        username= navigationView.getHeaderView(0).findViewById(R.id.username_header_navigation);
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId())
            {
                case R.id.homepage_item_navigation:
                {
                    break;
                }
                case R.id.interest_item_navigation:
                {
                    break;
                }
                case R.id.setting_item_navigation:
                {
                    break;
                }
                case R.id.logout_item_navigation:
                {
                    mPresenter.logout();
                    break;
                }
                default:return false;
            }
            return true;
        });
    }

    /**
     * 切换fragment
     * @param type 准备显示的fragment的类型
     */
    private void switchFragment(RecycleListViewFragment.TYPE_FRAGMENT type)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragments.get(type)==null)
            fragments.put(type,RecycleListViewFragment.newInstance(type));

        RecycleListViewFragment fragment = fragments.get(type);

        if (currentFra!=null) transaction.hide(currentFra);

        if (!fragment.isAdded())
            transaction.add(R.id.main_layout_frame,fragment);
        else
            transaction.show(fragment);

        currentFra=fragment;

        transaction.commit();
    }


    private void hideConcurrentFragment()
    {
        if (currentFra!=null) getSupportFragmentManager().beginTransaction().hide(currentFra).commit();
    }

    private void showMessageBar(ActionBar actionBar)
    {
        actionBar.setCustomView(R.layout.bar_message);
        actionBar.getCustomView().findViewById(R.id.mine_button_bar).setOnClickListener(this);
        actionBar.getCustomView().findViewById(R.id.friend_button_bar).setOnClickListener(this);
    }

    private void showActivityBar(ActionBar actionBar)
    {
        actionBar.setCustomView(R.layout.bar_activity);

        actionBar.getCustomView().findViewById(R.id.activity_button_bar).setOnClickListener(this);
    }

    private void showNewsBar(ActionBar actionBar)
    {
        actionBar.setCustomView(R.layout.bar_news);

        actionBar.getCustomView().findViewById(R.id.news_button_bar).setOnClickListener(this);
    }

    @Override
    public void switchToOnline(User user) {
        isOnline=true;
        toast(user.username);
        username.setText(user.username);
        showMessageBar(getSupportActionBar());
        switchFragment(RecycleListViewFragment.TYPE_FRAGMENT.MESSAGE);
    }

    @Override
    public void switchToOffline() {
        isOnline=false;
        username.setText("");
    }

    @Override
    public void goToLoginActivity() {
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}

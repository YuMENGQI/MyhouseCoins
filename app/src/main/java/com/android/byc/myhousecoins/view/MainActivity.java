package com.android.byc.myhousecoins.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.byc.myhousecoins.R;
import com.android.byc.myhousecoins.db.UserModelEntity;
import com.android.byc.myhousecoins.interfaces.IMyCoinsContract;
import com.android.byc.myhousecoins.presenter.MyCoinsPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IMyCoinsContract.View {

    private TextView tvCoinsCount;
    private Button titleBarGuideBack;
    private TextView tvShop;
    private TextView tvMytask;
    private TextView tvMyrankingList;
    private View myTaskBar;
    private View rankingListBar;
    NoScrollViewPager viewPager;
    private boolean fromAdevertisement = false;
    private IMyCoinsContract.Presenter presenter;
    private List<Fragment> fragments = new ArrayList<>();
    private MyTaskFragment myTasksFragment;
    private RankingListFragment rankingListFragment;
    private TextView tvRecordDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvCoinsCount = findViewById(R.id.tv_coins_count);
        titleBarGuideBack = findViewById(R.id.TitleBarGuideBack);
        tvShop = findViewById(R.id.tv_shop);
        tvRecordDetail = findViewById(R.id.tv_record_detail);
        tvMytask = findViewById(R.id.tv_mytask);
        tvMyrankingList = findViewById(R.id.tv_ranking_list);
        myTaskBar = findViewById(R.id.my_task_bar);
        rankingListBar = findViewById(R.id.ranking_list_bar);
        viewPager = findViewById(R.id.view_pager);
        tvCoinsCount.setOnClickListener(this);
        titleBarGuideBack.setOnClickListener(this);
        tvShop.setOnClickListener(this);
        tvRecordDetail.setOnClickListener(this);
        tvMytask.setOnClickListener(this);
        tvMyrankingList.setOnClickListener(this);
        myTaskBar.setOnClickListener(this);
        rankingListBar.setOnClickListener(this);
        viewPager.setOnClickListener(this);
        if (findFragment(MyTaskFragment.class) == null
                || findFragment(RankingListFragment.class) == null) {
            myTasksFragment = new MyTaskFragment();
            rankingListFragment = new RankingListFragment();
            fragments.add(myTasksFragment);
            fragments.add(rankingListFragment);
        } else {
            fragments.add(findFragment(MyTaskFragment.class));
            fragments.add(findFragment(RankingListFragment.class));
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        setResult(1);

        fromAdevertisement = getIntent().getBooleanExtra("fromAdvertisement", false);
        presenter = new MyCoinsPresenter(this);
        if (fromAdevertisement) {
            presenter.refreshCoins();
        }else {
            presenter.fetchDataFromLocal();
        }
    }

    protected  <T extends Fragment> T findFragment(Class<T> fragmentClass) {
        return FragmentUtil.getInstance().findStackFragment(fragmentClass, null, getSupportFragmentManager());
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mytask:
                viewPager.setCurrentItem(0, true);
                switchTabStatus(0);
                break;
            case R.id.tv_ranking_list:
                viewPager.setCurrentItem(1, true);
                switchTabStatus(1);
                break;
            case R.id.tv_record_detail:
                Intent intent = new Intent(this, RecordDetailActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_shop:
                Intent intent1 = new Intent(this, SecondActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void switchTabStatus(int i) {
        if (i == 0) {
            tvMytask.setTextColor(getResources().getColor(R.color.coins_main));
            myTaskBar.setVisibility(View.VISIBLE);
            tvMyrankingList.setTextColor(getResources().getColor(R.color.gray_no_effect));
            rankingListBar.setVisibility(View.INVISIBLE);
        } else if (i == 1) {
            tvMytask.setTextColor(getResources().getColor(R.color.gray_no_effect));
            myTaskBar.setVisibility(View.INVISIBLE);
            tvMyrankingList.setTextColor(getResources().getColor(R.color.coins_main));
            rankingListBar.setVisibility(View.VISIBLE);
        }
    }
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("fromAdvertisement", false);
        context.startActivity(intent);
    }
    public static void startActivity(Activity context, int requestCode) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("fromAdvertisement", true);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void refreshMyCoins(UserModelEntity userModelEntity) {
        tvCoinsCount.setText(String.valueOf(userModelEntity.getCurrencyAmount()));

    }

    private static class FragmentUtil {
        private static volatile FragmentUtil sInstance;

        private FragmentUtil (){}

        public static FragmentUtil getInstance(){
            if (sInstance == null){
                synchronized (FragmentUtil.class){
                    if (sInstance == null){
                        sInstance = new FragmentUtil();
                    }
                }
            }
            return sInstance;
        }

        public <T extends Fragment> T findStackFragment(Class<T> fragmentClass, String toFragmentTag, FragmentManager
                fragmentManager) {
            Fragment fragment = null;

            if (toFragmentTag == null) {
                List<Fragment> fragmentList = FragmentationHack.getActiveFragments(fragmentManager);
                if (fragmentList == null) {
                    return null;
                }

                int sizeChildFrgList = fragmentList.size();

                for (int i = sizeChildFrgList - 1; i >= 0; i--) {
                    Fragment brotherFragment = fragmentList.get(i);
                    if (brotherFragment.getClass().getName().equals(fragmentClass.getName())) {
                        fragment = brotherFragment;
                        break;
                    }
                }
            } else {
                fragment = fragmentManager.findFragmentByTag(toFragmentTag);
                if (fragment == null) {
                    return null;
                }
            }
            return (T) fragment;
        }

        private static class FragmentationHack {
            public static List<Fragment> getActiveFragments(FragmentManager fragmentManager) {
                return fragmentManager.getFragments();
            }
        }
    }

}

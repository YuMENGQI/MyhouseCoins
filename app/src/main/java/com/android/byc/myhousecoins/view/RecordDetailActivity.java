package com.android.byc.myhousecoins.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.byc.myhousecoins.R;
import com.android.byc.myhousecoins.adapter.RecordDetailViewPagerAdapter;
import com.android.byc.myhousecoins.db.ChatMessageIntegral;
import com.android.byc.myhousecoins.db.CurrencyRecordsEntity;
import com.android.byc.myhousecoins.interfaces.IRecordDetailContract;
import com.android.byc.myhousecoins.presenter.RecordDetailPresenter;

import java.util.List;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/21 16:35
 * @description
 */
public class RecordDetailActivity extends AppCompatActivity implements IRecordDetailContract.View, View.OnClickListener {

    ViewPager viewPager;
    TextView tvIncome;
    TextView tvExpense;
    View incomeBar;
    View expenseBar;
    private Button TitleBarGuideBack;
    private RecordDetailViewPagerAdapter viewPagerAdapter;
    private IRecordDetailContract.Presenter presenter;
    private List<CurrencyRecordsEntity> allList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);
        viewPager = findViewById(R.id.vp_record_detail);
        tvIncome = findViewById(R.id.tv_income);
        tvExpense = findViewById(R.id.tv_expense);
        incomeBar = findViewById(R.id.income_bar);
        expenseBar = findViewById(R.id.expense_bar);
        TitleBarGuideBack = findViewById(R.id.TitleBarGuideBack);
        viewPager.setOnClickListener(this);
        tvIncome.setOnClickListener(this);
        tvExpense.setOnClickListener(this);
        incomeBar.setOnClickListener(this);
        expenseBar.setOnClickListener(this);
        TitleBarGuideBack.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewPagerAdapter = new RecordDetailViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new PageChangeListener());
        presenter = new RecordDetailPresenter(this);
        presenter.fetchData();
        int type = getIntent().getIntExtra("type", ChatMessageIntegral.TYPE_INCOME);
        viewPager.setCurrentItem(type - 1);
    }

    @Override
    public void onClick(View v) {
        if (R.id.tv_income == v.getId()) {
            viewPager.setCurrentItem(0, true);
            switchTabStatus(0);
        } else if (R.id.tv_expense == v.getId()) {
            viewPager.setCurrentItem(1, true);
            switchTabStatus(1);
        }
        if (R.id.TitleBarGuideBack == v.getId()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void refreshList(List<CurrencyRecordsEntity> allList) {
        this.allList = allList;
        viewPagerAdapter.setDataSet(allList);

    }

    @Override
    public void refreshPartList(List<CurrencyRecordsEntity> partList) {
        this.allList.addAll(partList);
        refreshList(this.allList);

    }
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RecordDetailActivity.class);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, RecordDetailActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switchTabStatus(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void switchTabStatus(int position) {
        if (position == 0) {
            tvIncome.setTextColor(getResources().getColor(R.color.coins_main));
            incomeBar.setVisibility(View.VISIBLE);
            tvExpense.setTextColor(getResources().getColor(R.color.gray_no_effect));
            expenseBar.setVisibility(View.INVISIBLE);
        } else if (position == 1) {
            tvIncome.setTextColor(getResources().getColor(R.color.gray_no_effect));
            incomeBar.setVisibility(View.INVISIBLE);
            tvExpense.setTextColor(getResources().getColor(R.color.coins_main));
            expenseBar.setVisibility(View.VISIBLE);

        }
    }
}

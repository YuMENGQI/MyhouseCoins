package com.android.byc.myhousecoins.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.byc.myhousecoins.R;
import com.android.byc.myhousecoins.adapter.MyTaskAdapter;
import com.android.byc.myhousecoins.db.CurrencyTasksEntity;
import com.android.byc.myhousecoins.interfaces.IMyTaskContract;
import com.android.byc.myhousecoins.presenter.MyTaskPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * @author yu
 * @version 1.0
 * @date 2019/2/20 12:56
 * @description
 */
public class MyTaskFragment extends Fragment implements IMyTaskContract.View {
    @BindView(R.id.rv_my_task)
    RecyclerView rvMyTask;

    private MyTaskAdapter myTaskAdapter;
    private IMyTaskContract.Presenter presenter;
    private RecyclerView mRecyclerView;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_tasks, container, false);
        mRecyclerView = view.findViewById(R.id.rv_my_task);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        myTaskAdapter = new MyTaskAdapter();
        mRecyclerView.setAdapter(myTaskAdapter);
        presenter = new MyTaskPresenter(this);
        presenter.fetchDataFromLocal();
        presenter.fetchDataFromRemote();
    }

    @Override
    public void refreshTaskPanel(List<CurrencyTasksEntity> currencyTasks) {
        myTaskAdapter.setCurrencyTasks(currencyTasks);

    }
}

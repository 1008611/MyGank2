package com.wildwolf.mygank2.fragments;

import android.os.Bundle;
import android.widget.ListView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.wildwolf.mygank2.R;
import com.wildwolf.mygank2.adapter.AllAdapter;
import com.wildwolf.mygank2.base.BaseFragment;
import com.wildwolf.mygank2.bean.GanHuo;
import com.wildwolf.mygank2.event.SkinChangeEvent;
import com.wildwolf.mygank2.http.CallBack;
import com.wildwolf.mygank2.http.RequestManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ${wild00wolf} on 2016/12/7.
 */
public class AllFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    @Bind(R.id.swipe_target)
    ListView mListView;
    @Bind(R.id.swipeToLoadLayout)
    SwipeToLoadLayout mSwipeToLoadLayout;

    private AllAdapter adapter;
    private List<GanHuo> ganHuos = new ArrayList<>();

    private int page = 1;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_android;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EventBus.getDefault().register(this);
        initView();
        onRefresh();
    }

    @Subscribe
    public void onEvent(SkinChangeEvent event){
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        mSwipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(true);
            }
        });
        mSwipeToLoadLayout.setOnRefreshListener(this);
        mSwipeToLoadLayout.setOnLoadMoreListener(this);
        adapter = new AllAdapter(getActivity(), ganHuos);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        page =1;
        getData(true);
    }

    private void getData(final boolean isRefresh) {
        final int pageSize = 30;
        RequestManager.get(getName(), "http://gank.io/api/data/all/" +
                        String.valueOf(pageSize) + "/" +
                        String.valueOf(page), isRefresh, new CallBack<List<GanHuo>>() {
                    @Override
                    public void onSuccess(List<GanHuo> result) {
                        if (isRefresh){
                            ganHuos.clear();
                            page=2;
                        }else {
                            page++;
                        }
                        ganHuos.addAll(result);
                        adapter.notifyDataSetChanged();
                        if (mSwipeToLoadLayout!= null){
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        super.onFailure(message);
                        if (mSwipeToLoadLayout != null) {
                            mSwipeToLoadLayout.setRefreshing(false);
                            mSwipeToLoadLayout.setLoadingMore(false);
                        }
                    }
                }
        );
    }

    @Override
    public void onLoadMore() {
        getData(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}

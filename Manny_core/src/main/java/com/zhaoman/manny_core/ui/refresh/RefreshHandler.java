package com.zhaoman.manny_core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhaoman.manny_core.app.Manny;
import com.zhaoman.manny_core.net.RestClient;
import com.zhaoman.manny_core.net.callback.ISuccess;
import com.zhaoman.manny_core.ui.recycler.DataConverter;
import com.zhaoman.manny_core.ui.recycler.MultipleRecyclerAdapter;

/**
 * Author:zhaoman
 * Date:2018/11/14
 * Description:
 */
public class RefreshHandler  implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private  MultipleRecyclerAdapter mAdapter=null;
    private final DataConverter CONVERTOR;

    private RefreshHandler(SwipeRefreshLayout swipeRefreshLayout,RecyclerView recyclerView, DataConverter converter,PagingBean bean) {
        this.REFRESH_LAYOUT = swipeRefreshLayout;
        this.RECYCLERVIEW=recyclerView;
        this.CONVERTOR=converter;
        this.BEAN=bean;
        REFRESH_LAYOUT.setOnRefreshListener(this);
    }


    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter converter){

        return new RefreshHandler(swipeRefreshLayout,recyclerView,converter,new PagingBean());

    }

    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        Manny.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);

    }


    public void firstPage(String url){
        BEAN.setDelayed(1000);

        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        final JSONObject object=JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));
                        mAdapter=MultipleRecyclerAdapter.create(CONVERTOR.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}

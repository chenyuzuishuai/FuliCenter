package cn.ucai.fulicenter.controller.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.CollectsAdapter;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.CollectBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;
import cn.ucai.fulicenter.view.DisplayUtils;

public class CollectsActivity extends AppCompatActivity {
    private static final String TAG = CollectsActivity.class.getSimpleName();
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;

    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    IModelUser model;
    User user;
    int pageId = 1;
    CollectsAdapter mAdapter;
    GridLayoutManager gm;
    @BindView(R.id.rv)
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collects);
        ButterKnife.bind(this);
        user = FuLiCenterApplication.getUser();
    DisplayUtils.initBackWithTitle(this,"收藏列表");
        if (user == null) {
            finish();
        } else {
            L.e("<<<<<","CollectActivity");
            initView();
            initData(I.ACTION_DOWNLOAD);
            setListener();

        }
    }



    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }


    private void setPullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = gm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mAdapter.isMore() && lastPosition == mAdapter.getItemCount() - 1) {
                    pageId++;
                    initData(I.ACTION_DOWNLOAD);
                }
            }
        });
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                pageId=1;
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }


    private void initData(final int action) {
        L.e(TAG, "initData");
        model = new ModelUser();
        model.getCollects(this, user.getMuserName(), pageId, I.PAGE_SIZE_DEFAULT, new OnCompleteListener<CollectBean[]>() {

            @Override
            public void onSuccess(CollectBean[] result) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mAdapter.setMore(result!=null&& result.length>0);
                if (!mAdapter.isMore()) {
                    if (action == I.ACTION_PULL_DOWN) {
                        mAdapter.setFooter("没有跟多数据");
                    }
                    return;
                }
                mAdapter.setFooter("加载跟多数据");
                ArrayList<CollectBean> list = ConvertUtils.array2List(result);
                switch (action) {
                    case I.ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case I.ACTION_PULL_DOWN:
                        mAdapter.initData(list);
                        srl.setRefreshing(false);
                        tvRefresh.setVisibility(View.GONE);
                        mAdapter.initData(list);
                        break;
                    case I.ACTION_PULL_UP:
                        mAdapter.addData(list);
                        break;
                }




            }

            @Override
            public void onError(String error) {
                L.e(TAG, "error=" + error);
            }
        });
    }


    private void initView() {
        gm = new GridLayoutManager(this, I.COLUM_NUM);
        rv.addItemDecoration(new SpaceItemDecoration(12));
       rv.setLayoutManager(gm);
        rv.setHasFixedSize(true);
        mAdapter = new CollectsAdapter(this, new ArrayList<CollectBean>());
        rv.setAdapter(mAdapter);
        L.e(TAG, "mAdapter");
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        }
    }

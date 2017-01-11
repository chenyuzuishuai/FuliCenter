package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModerNewGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {
    final int ACTION_DOWNLOAD = 0;
    final int ACTION_PULL_DOWN = 1;
    final int ACTION_PULL_UP = 2;

    @BindView(R.id.tvnewgoodsrsl)
    TextView tvnewgoodsrsl;
    @BindView(R.id.newgoodssrl)
    SwipeRefreshLayout newgoodssrl;
    @BindView(R.id.newgoodsrv)
    RecyclerView newgoodsrv;
  GridLayoutManager gm;
    IModelNewGoods mModel;
    GoodsAdapter mAdapter;
    int pageId;


    ArrayList<NewGoodsBean> mList = new ArrayList<>();
    public NewGoodsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_new_goods, container, false);
        ButterKnife.bind(this, layout);
        initView();
        mModel = new ModerNewGoods();
        setListener();
        initData();
        return layout;
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
       newgoodsrv.setOnScrollListener(new RecyclerView.OnScrollListener() {
           @Override
           public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
               super.onScrollStateChanged(recyclerView, newState);
      mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
             // int lastPosition = gm.findLastVisibleItemPosition();
               if (newState == RecyclerView.SCROLL_STATE_IDLE&&mAdapter.isMore()){
                   pageId++;
                   downNewGoods(ACTION_PULL_UP,pageId);
               }
           }
       });
    }

    private void setPullDownListener() {
        newgoodssrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newgoodssrl.setRefreshing(true);
                tvnewgoodsrsl.setText(View.VISIBLE);
                pageId = 1;
                downNewGoods(ACTION_DOWNLOAD,pageId);

            }
        });
    }

    private void initData() {
        int pageId = 1;
      downNewGoods(ACTION_DOWNLOAD,1);
    }
    private void downNewGoods(final int action,int pageId){
        mModel.downNewGoods(getContext(), I.CAT_ID, pageId,
                new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                            mAdapter.setMore(result != null && result.length > 0);
                        if (!mAdapter.isMore()){
                            mAdapter.setFooter("没有更多数据");
                        }
                        mAdapter.setFooter("上拉加载更多数据");
                        ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                        switch (action){
                            case ACTION_DOWNLOAD:
                                mAdapter.initData(list);
                                break;
                            case ACTION_PULL_DOWN:
                                newgoodssrl.setRefreshing(false);
                                tvnewgoodsrsl.setVisibility(View.GONE);
                                mAdapter.initData(list);
                                break;
                            case ACTION_PULL_UP:
                                mAdapter.addData(list);
                                break;
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });

    }

    private void initView() {
        newgoodsrv.addItemDecoration(new SpaceItemDecoration(2));
        newgoodssrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        gm = new GridLayoutManager(getContext(), I.COLUM_NUM);
        newgoodsrv.setLayoutManager(gm);
        newgoodsrv.setHasFixedSize(true);
        mAdapter = new GoodsAdapter(getContext(),mList);
        newgoodsrv.setAdapter(mAdapter);
    }
}

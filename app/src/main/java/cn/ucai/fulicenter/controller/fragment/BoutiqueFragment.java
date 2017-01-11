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
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.net.IModelBoutique;
import cn.ucai.fulicenter.model.net.IModelNewGoods;
import cn.ucai.fulicenter.model.net.ModelBoutique;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

import static cn.ucai.fulicenter.R.id.newgoodssrl;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoutiqueFragment extends Fragment {

    final int ACTION_DOWNLOAD = 0;
    final int ACTION_PULL_DOWN = 1;
    final int ACTION_PULL_UP = 2;
    LinearLayoutManager lm;
    IModelBoutique mModel;
    BoutiqueAdapter mAdapter;
    int pageId;


    ArrayList<BoutiqueBean> mList = new ArrayList<>();
    @BindView(R.id.tvboutiquesrl)
    TextView tvboutiquesrl;
    @BindView(R.id.boutiquesrl)
    SwipeRefreshLayout boutiquesrl;
    @BindView(R.id.recyBoutique)
    RecyclerView recyBoutique;

    public BoutiqueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_boutique, container, false);
        ButterKnife.bind(this, layout);
        initView();
        mModel = new ModelBoutique();
        setListener();
        initData();
        return layout;
    }

    private void setListener() {
        setPullDownListener();
        setPullUpListener();
    }

    private void setPullUpListener() {
        recyBoutique.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mAdapter.setDragging(newState == RecyclerView.SCROLL_STATE_DRAGGING);
                if (newState==RecyclerView.SCROLL_STATE_DRAGGING&&mAdapter.isMore()){
                    pageId++;
                    downBoutique(ACTION_PULL_UP,pageId);
                }
            }
        });
    }

    private void setPullDownListener() {
        boutiquesrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boutiquesrl.setRefreshing(true);
                tvboutiquesrl.setText(View.VISIBLE);
                pageId =1;
                downBoutique(ACTION_PULL_DOWN,pageId);
            }
        });

    }

    private void initData() {
        pageId = 1;
        downBoutique(ACTION_DOWNLOAD,pageId);
    }

    private void downBoutique(final int action, int pageId) {
        mModel.downloadBoutique(getContext(), new OnCompleteListener<BoutiqueBean[]>() {
            @Override
            public void onSuccess(BoutiqueBean[] result) {
                mAdapter.setMore(result != null && result.length > 0);
                if (!mAdapter.isMore()){
                    mAdapter.setFooter("没有更多数据");
                }
                mAdapter.setFooter("上拉加载更多数据");
                ArrayList<BoutiqueBean> list = ConvertUtils.array2List(result);
                switch (action){
                    case ACTION_DOWNLOAD:
                        mAdapter.initData(list);
                        break;
                    case ACTION_PULL_DOWN:
                        boutiquesrl.setRefreshing(false);
                        tvboutiquesrl.setVisibility(View.GONE);
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
        recyBoutique.addItemDecoration(new SpaceItemDecoration(1));
        boutiquesrl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        lm = new LinearLayoutManager(getContext());
        recyBoutique.setLayoutManager(lm);
        recyBoutique.setHasFixedSize(true);
        mAdapter = new BoutiqueAdapter(getContext(),mList);
        recyBoutique.setAdapter(mAdapter);
    }

}

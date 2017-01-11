package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.GoodsAdapter;
import cn.ucai.fulicenter.model.bean.NewGoodsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModeNewGoods;
import cn.ucai.fulicenter.model.net.ModerNewGoods;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.ConvertUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewGoodsFragment extends Fragment {


    /*@BindView(R.id.tvnewgoodsrsl)
    TextView tvnewgoodsrsl;*/
    @BindView(R.id.newgoodssrl)
    SwipeRefreshLayout newgoodssrl;
    @BindView(R.id.newgoodsrv)
    RecyclerView newgoodsrv;
  GridLayoutManager gm;
    IModeNewGoods mModel;
    GoodsAdapter mAdapter;
    int pageId = 1;
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
        initData();
        return layout;
    }

    private void initData() {
        mModel.downNewGoods(getContext(), I.CAT_ID, pageId,
                new OnCompleteListener<NewGoodsBean[]>() {
                    @Override
                    public void onSuccess(NewGoodsBean[] result) {
                   ArrayList<NewGoodsBean> list = ConvertUtils.array2List(result);
                        Log.e("main", Arrays.toString(result));

                        //mList.addAll(list);
                        mAdapter.initData(list);
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
    }

    private void initView() {
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

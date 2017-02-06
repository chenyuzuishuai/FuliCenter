package cn.ucai.fulicenter.controller.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.application.FuLiCenterApplication;
import cn.ucai.fulicenter.application.I;
import cn.ucai.fulicenter.controller.adapter.BoutiqueAdapter;
import cn.ucai.fulicenter.controller.adapter.CartAdapter;
import cn.ucai.fulicenter.model.bean.BoutiqueBean;
import cn.ucai.fulicenter.model.bean.CartBean;
import cn.ucai.fulicenter.model.bean.GoodsDetailsBean;
import cn.ucai.fulicenter.model.bean.User;
import cn.ucai.fulicenter.model.net.IModelBoutique;
import cn.ucai.fulicenter.model.net.IModelUser;
import cn.ucai.fulicenter.model.net.ModelBoutique;
import cn.ucai.fulicenter.model.net.ModelUser;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.L;
import cn.ucai.fulicenter.model.utils.MFGT;
import cn.ucai.fulicenter.model.utils.SpaceItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {
    private static final String TAG = "CartFragment";
    LinearLayoutManager lm;
    User user;
    IModelUser model;
   CartAdapter mAdapter;
    ArrayList<CartBean> cartList = new ArrayList<>();
    UpdateCartReceiver mReceiver;

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tv_nothing)
    TextView tvNothing;
    @BindView(R.id.tvRefresh)
    TextView tvRefresh;
    @BindView(R.id.tv_cart_sum_price)
    TextView tvCartSumPrice;
    @BindView(R.id.tv_cart_save_price)
    TextView tvCartSavePrice;
   // UpdateCartReceiver mReceiver;


    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);
        initView();
        model = new ModelUser();
        user = FuLiCenterApplication.getUser();
        initData(I.ACTION_DOWNLOAD);
        setPullDownListener();
        setReceiverListener();
        return view;
    }

    private void initView() {
        lm = new LinearLayoutManager(getContext());
        rv.addItemDecoration(new SpaceItemDecoration(12));
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        mAdapter = new CartAdapter(getContext(),cartList);
        rv.setAdapter(mAdapter);
        srl.setVisibility(View.GONE);
        tvNothing.setVisibility(View.VISIBLE);
    }

    private void setReceiverListener() {
        mReceiver = new UpdateCartReceiver();
        IntentFilter filter = new IntentFilter(I.BROADCAST_UPDATA_CART);
        getContext().registerReceiver(mReceiver,filter);
    }

    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                initData(I.ACTION_PULL_DOWN);
            }
        });
    }

    private void initData(final int action) {
        if (user ==null) {
            MFGT.gotoLogin(getActivity());
        }else {
            model.getCart(getContext(), user.getMuserName(), new OnCompleteListener<CartBean[]>() {
                @Override
                public void onSuccess(CartBean[] result) {
                    srl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    srl.setVisibility(View.VISIBLE);
                    tvNothing.setVisibility(View.GONE);
                    if (result!=null&&result.length>0){
                        ArrayList<CartBean> list = ConvertUtils.array2List(result);
                        L.e("CartFragment","list.size"+list.size());
                        cartList.addAll(list);
                        if (action == I.ACTION_DOWNLOAD || action ==I.ACTION_PULL_DOWN){
                            mAdapter.initData(list);
                        }else {
                           mAdapter.addData(list);
                        }
                    }else {
                        srl.setVisibility(View.GONE);
                        tvNothing.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError(String error) {
                    srl.setRefreshing(false);
                    tvRefresh.setVisibility(View.GONE);
                    srl.setVisibility(View.GONE);
                    tvNothing.setVisibility(View.VISIBLE);
                    CommonUtils.showLongToast(error);

                }
            });
        }
    }
    @OnClick(R.id.tv_nothing)
    public void onClick() {
        initData(I.ACTION_DOWNLOAD);
    }

    @Override
    public void onResume() {
        super.onResume();
        setPrice();
    }

    private  void setPrice(){
        int sum = 0;
        int save = 0;
     if (cartList !=null&& cartList.size()>0){
      for (CartBean cart : cartList){
          GoodsDetailsBean goods = cart.getGoods();
          if (cart.isChecked()&&goods!=null){
              sum += cart.getCount() *getPrice(goods.getCurrencyPrice());
              save += cart.getCount() * (getPrice(goods.getCurrencyPrice())-getPrice(goods.getRankPrice()));
              goods.getRankPrice();
          }
      }
     }
        tvCartSumPrice.setText("合计: ￥" +sum);
        tvCartSavePrice.setText("节省: ￥" + save);
    }
    int getPrice(String price){
        int p = 0;
        p = Integer.valueOf(price.substring(price.indexOf("￥")+1));
        return p;
    }
   class UpdateCartReceiver extends BroadcastReceiver{

       @Override
       public void onReceive(Context context, Intent intent) {
           setPrice();
       }
   }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver !=null){
            getContext().unregisterReceiver(mReceiver);
        }
    }
}
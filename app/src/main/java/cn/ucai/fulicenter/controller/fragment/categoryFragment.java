package cn.ucai.fulicenter.controller.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.adapter.CategoryAdapter;
import cn.ucai.fulicenter.model.bean.CategoryChildBean;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;
import cn.ucai.fulicenter.model.net.IModelCategory;
import cn.ucai.fulicenter.model.net.ModelCategory;
import cn.ucai.fulicenter.model.net.OnCompleteListener;
import cn.ucai.fulicenter.model.utils.CommonUtils;
import cn.ucai.fulicenter.model.utils.ConvertUtils;
import cn.ucai.fulicenter.model.utils.OkHttpUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    IModelCategory model;
    CategoryAdapter mAdapter;

    ArrayList<CategoryGroupBean> mGroupBean = new ArrayList<>();
    ArrayList<ArrayList<CategoryChildBean>> mChildBean = new ArrayList<>();
    @BindView(R.id.elv_category)
    ExpandableListView elvCategory;
    @BindView(R.id.tv_nomore)
    TextView tvNomore;

    int groupCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        mAdapter = new CategoryAdapter(getContext(), mGroupBean, mChildBean);
        elvCategory.setAdapter(mAdapter);
        elvCategory.setGroupIndicator(null);
        initView(false);
        initData();
        return layout;
    }

    private void initView(boolean hasData) {
        tvNomore.setVisibility(hasData ? View.GONE : View.VISIBLE);
        elvCategory.setVisibility(hasData ? View.VISIBLE : View.GONE);
    }

    private void initData() {
        model = new ModelCategory();
        model.downData(getContext(), new OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null) {
                    initView(true);
                    ArrayList<CategoryGroupBean> list = ConvertUtils.array2List(result);
                    mGroupBean.addAll(list);
                    for (int i = 0; i < list.size(); i++) {
                        mChildBean.add(new ArrayList<CategoryChildBean>());
                        downloadChildData(list.get(i).getId(),i);
                    }
                } else {
                    initView(false);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("main", "error" + error);
            }
        });


    }

    private void downloadChildData(int id,final int index) {
        model.downData(getContext(), id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if (result != null) {
                    ArrayList<CategoryChildBean> list = ConvertUtils.array2List(result);
                      mChildBean.set(index,list);
                    Log.i("main",list.toString());
                }
                if (groupCount == mGroupBean.size()) {
                    mAdapter.initData(mGroupBean, mChildBean);
                }
            }

            @Override
            public void onError(String error) {
          groupCount++;
                Log.e("main", "error" + error);
            }
        });
    }

}

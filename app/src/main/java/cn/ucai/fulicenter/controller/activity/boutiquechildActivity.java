package cn.ucai.fulicenter.controller.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;
import cn.ucai.fulicenter.model.utils.MFGT;

public class BoutiqueChildActivity extends AppCompatActivity {

Context mContext;
    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_boutiquechild);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NewGoodsFragment())
                .commit();
      tvCommonTitle.setText(getTitle().toString());
    }


    @OnClick(R.id.ivback)
    public void onClick() {
        MFGT.finish(this);
    }
}

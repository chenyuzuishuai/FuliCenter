package cn.ucai.fulicenter.controller.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.controller.fragment.NewGoodsFragment;

public class MainActivity extends AppCompatActivity {
    RadioButton rbNewGoods, rbBoutique, rbCategory, rbCart, rbPersonalCenter;
    RadioButton[] rbs =  new RadioButton[5];
    int index, currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rbNewGoods = (RadioButton) findViewById(R.id.layout_new_goods);
        rbBoutique = (RadioButton) findViewById(R.id.layout_boutique);
        rbCategory = (RadioButton) findViewById(R.id.layout_category);
        rbCart = (RadioButton) findViewById(R.id.layout_cart);
        rbPersonalCenter = (RadioButton) findViewById(R.id.layout_personal_center);

        rbs[0] = rbNewGoods;
        rbs[1] = rbBoutique;
        rbs[2] = rbCart;
        rbs[3] = rbCategory;
        rbs[4] = rbPersonalCenter;
getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new NewGoodsFragment()).commit();
    }

    public void onCheckedChange(View view) {
        switch (view.getId()) {
            case R.id.layout_new_goods:
                index = 0;
                break;
            case R.id.layout_boutique:
                index = 1;
                break;
            case R.id.layout_cart:
                index = 2;
                break;
            case R.id.layout_category:
                index = 3;
                break;
            case R.id.layout_personal_center:
                index = 4;
                break;
        }
        if (index != currentIndex) {
            setRadioStatus();
        }
    }

    private void setRadioStatus() {
        for (int i = 0; i < rbs.length; i++) {
            if (index != i) {
                rbs[i].setChecked(false);
            } else {
                rbs[i].setChecked(true);
            }
        }
        currentIndex = index;
    }


}
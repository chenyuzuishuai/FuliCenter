package cn.ucai.fulicenter.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import cn.ucai.fulicenter.R;
import cn.ucai.fulicenter.model.bean.CategoryGroupBean;

/**
 * Created by yu chen on 2017/1/16.
 */

public class CateFilterButton extends Button {
    boolean isExpan;
    public CateFilterButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
  public  void initCateFilter(String groupName, ArrayList<CategoryGroupBean> list){
      this.setText(groupName);
      setCateFilterButtonListener();
  }

    private void setCateFilterButtonListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
     if (!isExpan){

     }else {

     }
                setArrow();
            }
        });
    }

    private void setArrow(){
        Drawable right;
        if (isExpan){
            right = getResources().getDrawable(R.mipmap.arrow2_up);
        }else {
            right = getResources().getDrawable(R.mipmap.arrow2_down);
        }
        right.setBounds(0,0,right.getIntrinsicWidth(),right.getIntrinsicHeight());
         this.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,right,null);
        isExpan = !isExpan;
    }
}

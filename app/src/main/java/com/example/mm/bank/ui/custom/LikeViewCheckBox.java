package com.example.mm.bank.ui.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.example.mm.bank.R;

public class LikeViewCheckBox extends AppCompatCheckBox {

    private final Context context;

    public LikeViewCheckBox(Context context) {
        super(context);
        this.context = context;
    }

    public LikeViewCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void setChecked(boolean t){
        if(t) {
            this.setBackground(getResources().getDrawable(R.drawable.ic_like_clicked));
            //this.setTextColor(Color.WHITE);
        } else {
            this.setBackground(getResources().getDrawable(R.drawable.ic_like_a));
            //this.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }
        super.setChecked(t);
    }
}

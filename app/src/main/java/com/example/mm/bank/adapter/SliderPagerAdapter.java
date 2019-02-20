package com.example.mm.bank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mm.bank.R;

public class SliderPagerAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImageId = new int[]{R.drawable.slider_a, R.drawable.slider_b};

    public SliderPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mImageId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mImageId[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
package com.eqiba.kizen.client.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.eqiba.kizen.client.R;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    private List<Bitmap> pics;
    private Context context;
    public MyViewPagerAdapter(List<Bitmap> pics, Context context)
    {
        this.pics =pics;
        this.context=context;
    }
    @Override
    public int getCount() {
        return pics.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(pics.get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }


}

package com.eqiba.kizen.client.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.MyViewPagerAdapter;
import com.eqiba.kizen.client.view.ScalePageTransformer;

import java.util.ArrayList;
import java.util.List;

public class ActivityActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        List<Bitmap> pics = new ArrayList<>(3);
        Resources resources =getResources();
        pics.add(BitmapFactory.decodeResource(resources,R.drawable.pic_activity_1));
        pics.add(BitmapFactory.decodeResource(resources,R.drawable.pic_activity_3));
        pics.add(BitmapFactory.decodeResource(resources,R.drawable.pic_activity_2));
        viewPager=findViewById(R.id.pics_activity);
        viewPager.setAdapter(new MyViewPagerAdapter(pics,this));
        viewPager.setPageTransformer(true,new ScalePageTransformer());


    }


}

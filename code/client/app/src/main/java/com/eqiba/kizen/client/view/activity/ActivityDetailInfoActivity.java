package com.eqiba.kizen.client.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import com.eqiba.kizen.client.R;

public class ActivityDetailInfoActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        findViewById(R.id.detail_layout).setOnClickListener(this);

        if(getIntent().getBooleanExtra("isClosed",false))
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("活动已结束！");
            builder.setMessage("活动已结束，是否进入经历信息界面？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(ActivityDetailInfoActivity.this,ExperienceActivity.class));
                }
            });
            builder.setNegativeButton("继续浏览", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,ActivityActivity.class));
    }
}

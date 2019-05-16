package com.eqiba.kizen.client.view.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.MyActivityContent;
import com.eqiba.kizen.client.adpter.viewHolder.MyActivityViewHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyActivityActivity extends AppCompatActivity {

    private RecyclerView readyAct,runningAct,closedAct,experience;
    private List<MyActivityContent> readyValue,runningValue,closedValue;
    private List<MyActivityContent> experienceValue;
    private enum TYPE_ITEM{READY_ACTIVITY,RUNNING_ACTIVITY,CLOSED_ACTIVITY,EXPERIENCE_ITEM}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity);

        readyAct = findViewById(R.id.ready_activity_list_my_activity);
        runningAct=findViewById(R.id.running_activity_list_my_activity);
        closedAct =findViewById(R.id.closed_activity_list_my_activity);

        experience=findViewById(R.id.experience_activity_my_activity);

        readyValue = new ArrayList<>();
        runningValue=new ArrayList<>();
        closedValue =new ArrayList<>();

        experienceValue = new ArrayList<>();

        //activity item
        MyActivityContent activity1 = new MyActivityContent("创建者","篮球活动");
        MyActivityContent activity2 = new MyActivityContent("管理者","大学生创业讲座");
        MyActivityContent activity3 = new MyActivityContent("成员","软件工程学术探讨");

        readyValue.add(activity1);
        readyValue.add(activity2);
        readyValue.add(activity3);
        runningValue.add(new MyActivityContent("成员","草莓园音乐节"));
        runningValue.add(new MyActivityContent("成员","大活摇滚节"));
        runningValue.add(new MyActivityContent("成员","梁子湖烧烤春游"));
        closedValue.add(new MyActivityContent("test","test"));
        closedValue.add(new MyActivityContent("test","test"));
        closedValue.add(new MyActivityContent("test","test"));

        //experience item
        MyActivityContent experience1 = new MyActivityContent("2015-09-5","湖北工业大学足球联赛");
        MyActivityContent experience2 = new MyActivityContent("2016-8-15","华中大学生创业讲坛");
        MyActivityContent experience3 = new MyActivityContent("2019-3-16","信科一班春游记");

        experienceValue.add(experience1);
        experienceValue.add(experience2);
        experienceValue.add(experience3);
        experienceValue.add(new MyActivityContent("2019-4-1","16届街舞快闪"));
        experienceValue.add(new MyActivityContent("2019-5-3","武汉LOL联赛"));
        experienceValue.add(new MyActivityContent("2019-6-5","全球读书节湖工大站"));

        readyAct.setAdapter(new MyActivityAdapter(TYPE_ITEM.READY_ACTIVITY,readyValue));
        readyAct.setLayoutManager(new LinearLayoutManager(this));
        runningAct.setAdapter(new MyActivityAdapter(TYPE_ITEM.RUNNING_ACTIVITY,runningValue));
        runningAct.setLayoutManager(new LinearLayoutManager(this));
        closedAct.setAdapter(new MyActivityAdapter(TYPE_ITEM.CLOSED_ACTIVITY,closedValue));
        closedAct.setLayoutManager(new LinearLayoutManager(this));
        experience.setAdapter(new MyActivityAdapter(TYPE_ITEM.EXPERIENCE_ITEM,experienceValue));
        experience.setLayoutManager(new LinearLayoutManager(this));
    }

    private void itemOnClickListener(TYPE_ITEM type)
    {

        Intent activityIntent = new Intent(this,ActivityDetailInfoActivity.class);
        switch (type)
        {
            case EXPERIENCE_ITEM:startActivity(new Intent(this,ExperienceActivity.class));break;
            case CLOSED_ACTIVITY:{
                activityIntent.putExtra("isClosed",true);
            }default:
            startActivity(activityIntent);
        }
    }

    private class MyActivityAdapter extends RecyclerView.Adapter<MyActivityViewHolder>
    {

        private TYPE_ITEM type;
        private List<MyActivityContent> mValues;

        public MyActivityAdapter(TYPE_ITEM type,List<MyActivityContent> mValues) {
            this.type = type;
            this.mValues=mValues;
        }

        @NonNull
        @Override
        public MyActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_my_activity, viewGroup, false);
            return new MyActivityViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyActivityViewHolder viewHolder, int i) {
            viewHolder.setContent(mValues.get(i));
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemOnClickListener(type);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}

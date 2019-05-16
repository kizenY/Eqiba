package com.eqiba.kizen.client.view.fragment;

import com.eqiba.kizen.client.adpter.dummy.ActivityContent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityFragment extends RecycleListViewFragment<ActivityContent> {


    @Override
    protected List<ActivityContent> initContent() {
        List<ActivityContent> contents = new ArrayList<>(3);
        ActivityContent content1= new ActivityContent("日常足球","下午两点西区操场，踢足球的约起来！",0,new Date(System.currentTimeMillis()+1000*3600));
        ActivityContent content2 = new ActivityContent("线下篮球赛","附近有没有高手，下午组个队打几场",1,new Date(System.currentTimeMillis()+1000*3600));
        ActivityContent content3=new ActivityContent("有学软件的同学吗？","想做个小项目，找几个学软件的同学一起组个队！",2,new Date(System.currentTimeMillis()+1000*3600));
        contents.add(content1);
        contents.add(content2);
        contents.add(content3);
        return contents;
    }
}

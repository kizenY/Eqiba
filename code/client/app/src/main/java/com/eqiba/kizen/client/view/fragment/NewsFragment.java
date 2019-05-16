package com.eqiba.kizen.client.view.fragment;

import com.eqiba.kizen.client.adpter.dummy.NewsContent;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends RecycleListViewFragment<NewsContent> {
    @Override
    protected List<NewsContent> initContent() {
        List<NewsContent> contents = new ArrayList<>(5);
        NewsContent content1 = new NewsContent("[季后赛]快船135-131勇士");
        NewsContent content2 = new NewsContent("尤文备战欧冠次回合比赛 C罗自信心爆棚");
        NewsContent content3 = new NewsContent("华强北永不眠！中国二手机江湖真相调查：5千家山寨机烟消云散");
        NewsContent content4 = new NewsContent("官方揭秘OpenAI如何打败人类：10个月训练4.5万年");
        NewsContent content5 = new NewsContent("50亿点扫描和100GB数据 3D激光让巴黎圣母院修复有望");
        contents.add(content1);
        contents.add(content2);
        contents.add(content3);
        contents.add(content4);
        contents.add(content5);
        return contents;
    }
}

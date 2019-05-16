package com.eqiba.kizen.client.adpter.dummy;

import java.util.Date;

public class ActivityContent implements MyContent {

    public String title,content;
    //0:准备 1：进行 2：结束
    public int status;
    public Date nextStageTime;

    public ActivityContent(String title, String content, int status, Date nextStageTime) {
        this.title = title;
        this.content = content;
        this.status = status;
        this.nextStageTime = nextStageTime;
    }
}

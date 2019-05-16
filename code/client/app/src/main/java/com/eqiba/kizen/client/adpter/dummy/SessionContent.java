package com.eqiba.kizen.client.adpter.dummy;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class SessionContent implements MyContent{

    public String name,content;
    public Date time;
    public int resourceId;

    public SessionContent(String name, String content, Date time,int resourceId) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.resourceId=resourceId;
    }
}

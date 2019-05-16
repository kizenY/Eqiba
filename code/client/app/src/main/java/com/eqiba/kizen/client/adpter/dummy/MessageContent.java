package com.eqiba.kizen.client.adpter.dummy;

import android.graphics.Bitmap;

public class MessageContent implements MyContent {

    public Bitmap head;
    public String content;
    public boolean isFromMe;

    public MessageContent(Bitmap head, String content,boolean isFromMe) {
        this.head = head;
        this.content = content;
        this.isFromMe = isFromMe;
    }
}

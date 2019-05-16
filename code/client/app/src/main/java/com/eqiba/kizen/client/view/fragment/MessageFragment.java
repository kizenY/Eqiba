package com.eqiba.kizen.client.view.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.SessionContent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MessageFragment extends RecycleListViewFragment<SessionContent> {

    @Override
    protected List<SessionContent> initContent() {
        long now = new Date().getTime();
        List<SessionContent> contents = new ArrayList<>(3);
        SessionContent c1 = new SessionContent("小明","你好！我是小明！",new Date(now-1*1000*60), R.drawable.ic_head1);
        SessionContent c2 = new SessionContent("小红","你好！我是小红！",new Date(now-2*1000*60), R.drawable.ic_head2);
        SessionContent c3 = new SessionContent("小刚","你好！我是小刚！",new Date(now-3*1000*60), R.drawable.ic_head3);
        contents.add(c1);
        contents.add(c2);
        contents.add(c3);
        return contents;
    }

}

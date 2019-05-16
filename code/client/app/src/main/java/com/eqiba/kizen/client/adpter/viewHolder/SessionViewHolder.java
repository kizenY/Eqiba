package com.eqiba.kizen.client.adpter.viewHolder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.MyContent;
import com.eqiba.kizen.client.adpter.dummy.SessionContent;
import com.eqiba.kizen.client.view.CircleImageView;

import java.text.SimpleDateFormat;

public class SessionViewHolder extends MyViewHolder
{

    public final TextView name,time,content;
    public final CircleImageView head;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

    public SessionViewHolder(@NonNull View itemView) {
        super(itemView);
        name =  view.findViewById(R.id.name_item_session);
        time =  view.findViewById(R.id.time_item_session);
        content = view.findViewById(R.id.content_item_session);
        head = view.findViewById(R.id.head_session_item);
    }

    @Override
    protected void loadContent(MyContent content) {
        SessionContent mItem = (SessionContent)content;
        name.setText(mItem.name);
        time.setText(dateFormat.format(mItem.time));
        head.setImageBitmap(BitmapFactory.decodeResource(view.getResources(),mItem.resourceId));
        this.content.setText(mItem.content);
    }

}

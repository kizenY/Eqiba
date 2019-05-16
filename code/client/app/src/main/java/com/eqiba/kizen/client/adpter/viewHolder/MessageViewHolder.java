package com.eqiba.kizen.client.adpter.viewHolder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.MessageContent;
import com.eqiba.kizen.client.adpter.dummy.MyContent;
import com.eqiba.kizen.client.view.CircleImageView;

public class MessageViewHolder  extends MyViewHolder{

    private final CircleImageView head;
    private final TextView content;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        head = view.findViewById(R.id.head_message);
        content=view.findViewById(R.id.content_message);
    }

    @Override
    protected void loadContent(MyContent content) {
        MessageContent item = (MessageContent)content;
        head.setImageBitmap(item.head);
        this.content.setText(item.content);
    }
}

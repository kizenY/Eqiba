package com.eqiba.kizen.client.adpter.viewHolder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.MyActivityContent;
import com.eqiba.kizen.client.adpter.dummy.MyContent;

public class MyActivityViewHolder extends MyViewHolder{


    private TextView first,second;

    public MyActivityViewHolder(@NonNull View itemView) {
        super(itemView);
        first = view.findViewById(R.id.first_my_activity_item);
        second= view.findViewById(R.id.second_my_activity_item);
    }

    @Override
    protected void loadContent(MyContent content) {

        MyActivityContent item = (MyActivityContent)content;
        first.setText(item.first);
        second.setText(item.second);
    }
}

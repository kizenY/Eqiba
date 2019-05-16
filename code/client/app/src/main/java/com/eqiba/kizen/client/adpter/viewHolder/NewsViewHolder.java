package com.eqiba.kizen.client.adpter.viewHolder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.MyContent;
import com.eqiba.kizen.client.adpter.dummy.NewsContent;

public class NewsViewHolder extends MyViewHolder {

    private TextView title;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        title=view.findViewById(R.id.title_item_news);
    }

    @Override
    protected void loadContent(MyContent content) {
        NewsContent nContent = (NewsContent) content;
        title.setText(nContent.title);
    }
}

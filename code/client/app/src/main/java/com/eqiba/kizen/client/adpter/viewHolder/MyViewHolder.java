package com.eqiba.kizen.client.adpter.viewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.eqiba.kizen.client.adpter.dummy.MyContent;

public abstract class MyViewHolder extends RecyclerView.ViewHolder {
    public View view;
    private MyContent content;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        view=itemView;
    }



    /**
     * load the content data on view
     * @param content
     */
    protected abstract void loadContent(MyContent content);

    public void setContent(MyContent content)
    {
        this.content = content;
        loadContent(content);
    }

    public MyContent getContent()
    {
        return content;
    }
}

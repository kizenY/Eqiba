package com.eqiba.kizen.client.adpter.viewHolder;

import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.AlbumContent;
import com.eqiba.kizen.client.adpter.dummy.MyContent;

public class AlbumViewHolder extends MyViewHolder {

    private ImageView imageView;
    public AlbumViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image_album_item);
    }

    @Override
    protected void loadContent(MyContent content) {
        imageView.setImageBitmap(BitmapFactory.decodeResource(view.getResources(),((AlbumContent)content).resourceId));
    }
}

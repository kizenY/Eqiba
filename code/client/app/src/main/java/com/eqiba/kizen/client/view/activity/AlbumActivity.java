package com.eqiba.kizen.client.view.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.AlbumContent;
import com.eqiba.kizen.client.adpter.viewHolder.AlbumViewHolder;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    private List<AlbumContent> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        images = new ArrayList<>(3);
        Resources resources =getResources();
        AlbumContent content1 = new AlbumContent(R.drawable.pic_activity_1);
        AlbumContent content2 = new AlbumContent(R.drawable.pic_activity_2);
        AlbumContent content3 = new AlbumContent(R.drawable.pic_activity_3);
        images.add(content1);
        images.add(content2);
        images.add(content3);

        RecyclerView recyclerView = findViewById(R.id.grid_album_activity);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(new AlbumAdapter());
    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {


        @NonNull
        @Override
        public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            return new AlbumViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_album, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumViewHolder albumViewHolder, int i) {
            albumViewHolder.setContent(images.get(i));
        }

        @Override
        public int getItemCount() {
            return images.size();
        }
    }
}

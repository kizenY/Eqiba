package com.eqiba.kizen.client.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import com.eqiba.kizen.client.R;

public class ExperienceActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience);

        LinearLayout activityAlbumButton = findViewById(R.id.activity_album_button);
        LinearLayout personalAlbumButton = findViewById(R.id.personal_album_button);

        activityAlbumButton.setOnClickListener(this);
        personalAlbumButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,AlbumActivity.class));
    }
}

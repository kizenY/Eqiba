package com.eqiba.kizen.client.adpter.viewHolder;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.TextView;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.ActivityContent;
import com.eqiba.kizen.client.adpter.dummy.MyContent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityViewHolder extends MyViewHolder {

    private final TextView title,content,status,nextStageTime;
    private DateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");

    public ActivityViewHolder(@NonNull View itemView) {
        super(itemView);
        title=view.findViewById(R.id.title_activity_item);
        content=view.findViewById(R.id.content_activity_item);
        status=view.findViewById(R.id.status_activity_item);
        nextStageTime=view.findViewById(R.id.time_next_stage_activity_item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void loadContent(MyContent content) {
        ActivityContent aContent = (ActivityContent)content;
        title.setText(aContent.title);
        this.content.setText(aContent.content);
        Resources resources=view.getResources();
        switch (aContent.status)
        {
            case 0:
                status.setText(R.string.status_ready);
                status.setTextColor(resources.getColor(R.color.color_green,null));
                break;
            case 1:
                status.setText(R.string.status_running);
                status.setTextColor(resources.getColor(R.color.color_orange,null));
                break;
            case 2:
                status.setText(R.string.status_closing);
                status.setTextColor(resources.getColor(R.color.color_brown,null));
                break;
        }
        if (aContent.status!=2) nextStageTime.setText(dateFormat.format(aContent.nextStageTime));
    }
}

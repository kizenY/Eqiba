
package com.eqiba.kizen.client.view.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.eqiba.kizen.client.R;
import com.eqiba.kizen.client.adpter.dummy.MessageContent;
import com.eqiba.kizen.client.adpter.viewHolder.MessageViewHolder;
import com.eqiba.kizen.client.contract.ChattingContract;
import com.eqiba.kizen.client.presenter.ChattingPresenterImpl;
import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends AppCompatActivity implements ChattingContract.ChattingView, View.OnClickListener {

    private RecyclerView recyclerView;
    private EditText content;
    private Bitmap myHead,chatterHead;
    private List<MessageContent> mValues;

    private ChattingContract.ChattingPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        initData();
        initView();
    }

    @Override
    public void addMessage(MessageContent content) {
        if (content.head==null)
            content.head=chatterHead;
        mValues.add(content);
        recyclerView.getAdapter().notifyItemInserted(mValues.size()-1);
        recyclerView.scrollToPosition(mValues.size()-1);
    }

    @Override
    public String getContent() {
        return content.getText().toString();
    }

    @Override
    public void clearContent() {
        content.setText("");
    }


    private void initData()
    {
        Resources resources = getResources();
        chatterHead = BitmapFactory.decodeResource(resources,getIntent().getIntExtra("head",R.drawable.ic_mine));
        myHead = BitmapFactory.decodeResource(resources,R.drawable.ic_mine);

        mValues = new ArrayList<>();
        presenter = new ChattingPresenterImpl(this);
        generateListData();
    }

    private void initView()
    {
        recyclerView=findViewById(R.id.recyclerView_chatting);
        content=findViewById(R.id.message_chatting);

        findViewById(R.id.send_button_chatting).setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new ChattingListAdapter());

        ActionBar bar = getSupportActionBar();
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        bar.setCustomView(R.layout.bar_chatting);
        ((TextView)bar.getCustomView().findViewById(R.id.name_bar_chatting)).setText(getIntent().getStringExtra("name"));
    }

    private List<MessageContent> generateListData()
    {

        MessageContent content1 = new MessageContent(myHead,"你好！我是Kizen!",true);
        MessageContent content2 = new MessageContent(chatterHead,"你好！",false);
        mValues.add(content1);
        mValues.add(content2);
        return mValues;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.send_button_chatting:
            {
                presenter.sendMessage();
                break;
            }
        }
    }

    private class ChattingListAdapter extends RecyclerView.Adapter<MessageViewHolder>{


        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            int resource;
            if (i==1) resource = R.layout.item_message_reverse; else  resource=R.layout.item_message;
            return new MessageViewHolder(getLayoutInflater().inflate(resource,viewGroup,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
            MessageContent content = mValues.get(i);
            if (content.isFromMe) content.head=myHead;
            else content.head=chatterHead;
            messageViewHolder.setContent(content);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        @Override
        public int getItemViewType(int position) {
            MessageContent content = mValues.get(position);
            if (content.isFromMe) return 1; else return 0;
        }
    }
}

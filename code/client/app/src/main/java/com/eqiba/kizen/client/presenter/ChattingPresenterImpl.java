package com.eqiba.kizen.client.presenter;

import com.eqiba.kizen.client.adpter.dummy.MessageContent;
import com.eqiba.kizen.client.contract.ChattingContract;
import com.eqiba.kizen.client.view.MVPBaseView;

public class ChattingPresenterImpl extends MVPBasePresenter implements ChattingContract.ChattingPresenter {

    private ChattingContract.ChattingView chattingView;

    public ChattingPresenterImpl(MVPBaseView view) {
        super(view);
        chattingView=(ChattingContract.ChattingView)view;
    }

    @Override
    public boolean sendMessage() {
        MessageContent content = new MessageContent(null,chattingView.getContent(),true);
        chattingView.addMessage(content);
        chattingView.clearContent();
        //添加回复
        MessageContent reply = new MessageContent(null,"这是回复！",false);
        chattingView.addMessage(reply);
        return true;
    }

}

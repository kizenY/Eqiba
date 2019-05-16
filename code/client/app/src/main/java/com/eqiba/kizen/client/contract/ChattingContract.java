package com.eqiba.kizen.client.contract;

import com.eqiba.kizen.client.adpter.dummy.MessageContent;
import com.eqiba.kizen.client.view.MVPBaseView;


public interface ChattingContract {
    interface ChattingView extends MVPBaseView
    {
        void addMessage(MessageContent content);
        String getContent();
        void clearContent();
    }
    interface ChattingPresenter
    {
        boolean sendMessage();
    }
}

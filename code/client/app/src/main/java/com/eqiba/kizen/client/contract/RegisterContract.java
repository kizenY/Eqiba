package com.eqiba.kizen.client.contract;

import com.eqiba.kizen.client.view.MVPBaseView;

public interface RegisterContract {
    interface RegisterView extends MVPBaseView {
        String getUsername();
        String getPassword();
        boolean verify();
        boolean repeat();
        void flushContent();
        void toast(String msg);
        void finishRegister();
    }
    interface RegisterPresenter
    {
        void register();
    }
}

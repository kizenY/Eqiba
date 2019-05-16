package com.eqiba.kizen.client.contract;

import com.eqiba.kizen.client.view.MVPBaseView;

public interface LoginContract {

    interface LoginView extends MVPBaseView {

        String getUsername();
        String getPassword();
        void waitDialog();
        void finishWait();
        void flushPassword();
        void toast(String msg);
        void toMain();
    }

    interface LoginPresenter{
        void login();
    }

}

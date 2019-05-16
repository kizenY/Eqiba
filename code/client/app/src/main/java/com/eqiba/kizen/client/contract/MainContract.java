package com.eqiba.kizen.client.contract;

import com.eqiba.kizen.client.bean.User;
import com.eqiba.kizen.client.view.MVPBaseView;

public interface MainContract {
    interface MainView extends MVPBaseView {
        void switchToOnline(User user);
        void switchToOffline();
        void goToLoginActivity();
        void toast(String msg);
    }
    interface MainPresenter{
        void logout();
        void checkOnlineStatus();
    }
}

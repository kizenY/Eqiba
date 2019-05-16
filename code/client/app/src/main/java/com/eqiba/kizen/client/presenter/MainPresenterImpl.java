package com.eqiba.kizen.client.presenter;

import com.eqiba.kizen.client.RegistSingleton;
import com.eqiba.kizen.client.bean.User;
import com.eqiba.kizen.client.contract.MainContract;
import com.eqiba.kizen.client.dto.EqibaResult;
import com.eqiba.kizen.client.model.MyModel;
import com.eqiba.kizen.client.model.UserModel;
import com.eqiba.kizen.client.model.impl.UserModelImpl;
import com.eqiba.kizen.client.view.MVPBaseView;

import java.io.IOException;

public class MainPresenterImpl extends MVPBasePresenter implements MainContract.MainPresenter {
    MainContract.MainView mainView = null;
    UserModel userModel = (UserModel) RegistSingleton.getInstance(UserModelImpl.class.getName());

    public MainPresenterImpl(MVPBaseView view) {
        super(view);
        mainView = (MainContract.MainView) view;
    }

    @Override
    public void logout() {

        User user=UserModelImpl.user;
        if (user!=null)
        {
            new SimpleAsynchronousRequest<EqibaResult>()
            {

                @Override
                protected EqibaResult doRequest() throws IOException, MyModel.NullBodyException {
                    return userModel.logout(UserModelImpl.sessionId);
                }

                @Override
                protected void completeRequest(EqibaResult result) {
                    UserModelImpl.user=null;
                    UserModelImpl.sessionId=null;
                    mainView.toast(result.message);
                    mainView.goToLoginActivity();
                }

                @Override
                protected void handleException(Throwable e) {
                    mainView.toast("网络通信出现错误！无法登出！");
                }
            }.begin();
        }
        else mainView.goToLoginActivity();
    }

    @Override
    public void checkOnlineStatus() {
        User user = UserModelImpl.user;
        if (user!=null)
            mainView.switchToOnline(user);
        else mainView.switchToOffline();
    }


}

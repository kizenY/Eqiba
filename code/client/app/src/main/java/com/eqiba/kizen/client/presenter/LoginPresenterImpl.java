package com.eqiba.kizen.client.presenter;

import com.eqiba.kizen.client.RegistSingleton;
import com.eqiba.kizen.client.bean.User;
import com.eqiba.kizen.client.contract.LoginContract;
import com.eqiba.kizen.client.dto.EqibaResult;
import com.eqiba.kizen.client.model.MyModel;
import com.eqiba.kizen.client.model.UserModel;
import com.eqiba.kizen.client.model.impl.UserModelImpl;
import com.eqiba.kizen.client.view.MVPBaseView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;


public class LoginPresenterImpl extends MVPBasePresenter implements LoginContract.LoginPresenter {


    public LoginPresenterImpl(MVPBaseView view) {
        super(view);
    }

    private UserModel userModel = (UserModelImpl) RegistSingleton.getInstance(UserModelImpl.class.getName());

    @Override
    public void login() {

        LoginContract.LoginView view = (LoginContract.LoginView) super.view;
        view.waitDialog();
        String username = view.getUsername();
        String password = view.getPassword();

        new SimpleAsynchronousRequest<EqibaResult<User>>()
        {

            @Override
            protected EqibaResult<User> doRequest() throws IOException, MyModel.NullBodyException {
                return userModel.login(username,password);
            }

            @Override
            protected void completeRequest(EqibaResult<User> result) {
                if (!result.code.equals("110")) view.toast(result.message);
                else
                {
                    System.out.println(result.toString());
                    System.out.println(result.data.get(0).toString());
                    UserModelImpl.user = result.data.get(0);
                    view.finishWait();
                    view.toMain();
                }
            }

            @Override
            protected void handleException(Throwable e) {
                view.toast("登录失败！网络通信出现错误");
            }
        }.begin();

    }


}

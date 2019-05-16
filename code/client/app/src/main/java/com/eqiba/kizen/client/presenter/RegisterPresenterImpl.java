package com.eqiba.kizen.client.presenter;

import com.eqiba.kizen.client.RegistSingleton;
import com.eqiba.kizen.client.contract.RegisterContract;
import com.eqiba.kizen.client.dto.EqibaResult;
import com.eqiba.kizen.client.model.MyModel;
import com.eqiba.kizen.client.model.UserModel;
import com.eqiba.kizen.client.model.impl.UserModelImpl;
import com.eqiba.kizen.client.view.MVPBaseView;

import java.io.IOException;

public class RegisterPresenterImpl extends MVPBasePresenter implements RegisterContract.RegisterPresenter {

    public RegisterPresenterImpl(MVPBaseView view) {
        super(view);
    }

    private UserModel userModel = (UserModelImpl)RegistSingleton.getInstance(UserModelImpl.class.getName());

    @Override
    public void register() {
        RegisterContract.RegisterView view = (RegisterContract.RegisterView)super.view;
        if (!view.repeat())
        {
            view.toast("两次密码不相同！");
            view.flushContent();
        }
        else if (!view.verify())
        {
            view.toast("验证码错误！");
            view.flushContent();
        }else {
            new SimpleAsynchronousRequest<EqibaResult>() {
                @Override
                protected EqibaResult doRequest() throws IOException, MyModel.NullBodyException {
                    return userModel.register(view.getUsername(),view.getPassword());
                }

                @Override
                protected void completeRequest(EqibaResult result) {
                    if (result.code.equals("100"))
                        view.finishRegister();
                    else
                        view.flushContent();
                    view.toast(result.message);
                }

                @Override
                protected void handleException(Throwable e) {
                    view.toast("注册失败！网络通信出现错误！");
                }
            }.begin();

        }


    }
}

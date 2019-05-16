package com.eqiba.kizen.client.presenter;

import com.eqiba.kizen.client.view.MVPBaseView;

public class MVPBasePresenter {

    protected MVPBaseView view;
    public MVPBasePresenter(MVPBaseView view)
    {
        this.view=view;
    }
}

package com.eqiba.kizen.client.presenter;

import com.eqiba.kizen.client.model.MyModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.io.IOException;

public abstract class SimpleAsynchronousRequest<T> {

    protected abstract T doRequest() throws IOException, MyModel.NullBodyException;

    protected abstract void completeRequest(T result);

    protected abstract void handleException(Throwable e);

    public void begin()
    {
        new Observable<T>()
        {
            @Override
            protected void subscribeActual(Observer<? super T> observer) {
                try {
                    observer.onNext(doRequest());
                } catch (IOException e) {
                    e.printStackTrace();
                    observer.onError(e);
                } catch (MyModel.NullBodyException e) {
                    e.printStackTrace();
                    observer.onError(e);
                }
            }
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        completeRequest(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        handleException(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

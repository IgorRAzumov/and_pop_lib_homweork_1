package ru.geekbrains.android3_1.presenter;

import android.support.annotation.NonNull;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_1.model.CounterModel;
import ru.geekbrains.android3_1.view.MainView;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    CounterModel model;

    public MainPresenter(CounterModel model) {
        this.model = model;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
    }


    public void buttonOneClick() {
        calculate(0);
    }

    public void buttonTwoClick() {
        calculate(1);
    }

    public void buttonThreeClick() {
        calculate(2);
    }

    public void calculate(int index) {
        model.getCalculateObservable(index)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver(index));
    }


    public DisposableObserver<Integer> getObserver(final int index) {
        return new DisposableObserver<Integer>() {

            @Override
            public void onNext(@NonNull Integer value) {
                switch (index) {
                    case 0: {
                        getViewState().setButtonOneText(value + "");
                        break;
                    }
                    case 1: {
                        getViewState().setButtonTwoText(value + "");
                        break;
                    }
                    case 2: {
                        getViewState().setButtonThreeText(value + "");
                        break;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }
}

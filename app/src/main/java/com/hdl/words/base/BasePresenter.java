package com.hdl.words.base;

/**
 * Date 2019/3/2 16:57
 * author HDL
 * Description:
 */
public abstract class BasePresenter<T> {
    public final String TAG = this.getClass().getSimpleName();
    protected T mView;

    private BasePresenter() {
    }

    public BasePresenter(T view) {
        attach(view);
    }

    private void attach(T view) {
        mView = view;
    }

    public void detach(T view) {
        mView = null;
    }

    public T getView() {
        return mView;
    }
}

package com.example.zuo81.meng.ui.dictionary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.zuo81.meng.presenter.dictionary.DictionaryPresenterImpl;
import com.example.zuo81.meng.ui.dictionary.view.DictionaryView;
import com.orhanobut.logger.Logger;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zuo81 on 2017/10/30.
 */

public class BaseDictionayFragment extends SupportFragment implements DictionaryView {

    protected DictionaryPresenterImpl mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mPresenter == null) {
            mPresenter = new DictionaryPresenterImpl();
        }
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if(mPresenter != null) {
            mPresenter.dettachView();
        }
        super.onDestroyView();
    }
}

package com.example.zuo81.meng.ui.dictionary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zuo81.meng.base.BaseFragment;
import com.example.zuo81.meng.model.http.HttpHelper;
import com.example.zuo81.meng.presenter.dictionary.DictionaryPresenterImpl;
import com.example.zuo81.meng.ui.dictionary.view.DictionaryView;
import com.orhanobut.logger.Logger;

import io.realm.Realm;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by zuo81 on 2017/10/30.
 */

public abstract class BaseDictionayFragment extends BaseFragment implements DictionaryView {

    protected DictionaryPresenterImpl mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Realm.init(getContext());
        if (mPresenter == null) {
            mPresenter = new DictionaryPresenterImpl();
        }
        mPresenter.attachView(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if(mPresenter != null) {
            mPresenter.dettachView();
        }
        super.onDestroyView();
    }
}

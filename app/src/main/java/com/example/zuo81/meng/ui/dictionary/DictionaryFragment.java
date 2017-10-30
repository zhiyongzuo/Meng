package com.example.zuo81.meng.ui.dictionary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.DictionaryBean;
import com.example.zuo81.meng.presenter.dictionary.DictionaryPresenterImpl;
import com.example.zuo81.meng.ui.dictionary.fragment.BaseDictionayFragment;
import com.example.zuo81.meng.ui.dictionary.view.DictionaryView;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends BaseDictionayFragment {

    private RecyclerView rvDictionary;
    private DictionaryAdapter mDictionaryAdapter;
    private List<DictionaryBean> mList;
    private DictionaryPresenterImpl presenter;

    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);
        rvDictionary = view.findViewById(R.id.rv_dictionary);

        /*mList = mPresenter.getList(name);
        mDictionaryAdapter = new DictionaryAdapter(getContext(), mList);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayout.setStackFromEnd(true);
        rvDictionary.setLayoutManager(linearLayout);
        rvDictionary.setAdapter(mDictionaryAdapter);*/

        return view;
    }

}

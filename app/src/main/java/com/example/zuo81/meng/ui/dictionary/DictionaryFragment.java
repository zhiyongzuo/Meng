package com.example.zuo81.meng.ui.dictionary;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.ShanBeiBean;
import com.example.zuo81.meng.ui.dictionary.fragment.BaseDictionayFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends BaseDictionayFragment {

    private boolean RV_INITED;
    private RecyclerView rvDictionary;
    private TextView tvDictionary;
    private DictionaryAdapter mDictionaryAdapter;
    private List<RealmDictionaryBean> mList = new ArrayList<>();

    public DictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);
        rvDictionary = view.findViewById(R.id.rv_dictionary);
        tvDictionary = view.findViewById(R.id.tv_dictionary);

        mList = mPresenter.getAllRealmDictionary();
        Logger.d(mList.size());
        if (mList!=null && mList.size() > 0) {
            init();
        } else {
            tvDictionary.setVisibility(View.VISIBLE);
            tvDictionary.setText("kong");
        }
        return view;
    }

    private void init() {
        Logger.d("init");
        mDictionaryAdapter = new DictionaryAdapter(getContext(), mList);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayout.setStackFromEnd(true);
        rvDictionary.setLayoutManager(linearLayout);
        rvDictionary.setAdapter(mDictionaryAdapter);
        RV_INITED = true;
    }

    @Override
    public void updateList(ShanBeiBean bean) {
        if (bean.getData().getDefinition() != null) {
            mPresenter.addToRealmDictionary(bean.getData().getContent(), bean.getData().getDefinition());
            mList.add(mPresenter.getFirstRealmDictionary());
            if(RV_INITED) {
                mDictionaryAdapter.notifyDataSetChanged();
            } else{
                tvDictionary.setVisibility(View.GONE);
                init();
            }
        } else {
            Toast.makeText(getContext(), "没有查询到", Toast.LENGTH_SHORT).show();
        }
    }
}

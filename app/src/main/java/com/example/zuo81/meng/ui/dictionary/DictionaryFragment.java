package com.example.zuo81.meng.ui.dictionary;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseFragment;
import com.example.zuo81.meng.base.contract.dictionary.Dictionary;
import com.example.zuo81.meng.model.bean.realm.RealmDictionaryBean;
import com.example.zuo81.meng.model.bean.shanbei.ShanBeiBean;
import com.example.zuo81.meng.presenter.dictionary.DictionaryPresenterImpl;
import com.example.zuo81.meng.widget.DefaultItemTouchHelpCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryFragment extends MVPBaseFragment<DictionaryPresenterImpl> implements Dictionary.View {

    @BindView(R.id.rv_dictionary)
    RecyclerView rvDictionary;
    @BindView(R.id.tv_dictionary)
    TextView tvDictionary;

    private DictionaryAdapter mDictionaryAdapter;
    private List<RealmDictionaryBean> mList = new ArrayList<>();
    private DefaultItemTouchHelpCallback mCallback;
    LinearLayoutManager mLinearLayoutManager;

    public static DictionaryFragment newInstance() {
        return new DictionaryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dictionary;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        mList = presenter.getAllRealmDictionary();
        if (mList!=null && mList.size() > 0) {
            init();
        } else {
            tvDictionary.setVisibility(View.VISIBLE);
            tvDictionary.setText("kong");
        }

        mCallback = new DefaultItemTouchHelpCallback(new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                if(mList!=null) {
                    presenter.deleteDictionaryData(mList.get(adapterPosition).getId());
                    mList.remove(adapterPosition);
                    mDictionaryAdapter.notifyItemRemoved(adapterPosition);
                }
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                return false;
            }
        });
        mCallback.setDragEnable(true);
        mCallback.setSwipeEnable(true);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(rvDictionary);
    }

    private void init() {
        mDictionaryAdapter = new DictionaryAdapter(getContext(), mList);
        mLinearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        mLinearLayoutManager.setStackFromEnd(true);
        rvDictionary.setLayoutManager(mLinearLayoutManager);
        rvDictionary.setAdapter(mDictionaryAdapter);
    }

    @Override
    public void addItem(ShanBeiBean bean) {
        if (bean.getData().getDefinition() != null) {
            tvDictionary.setVisibility(View.GONE);
            Toast.makeText(getContext(), bean.getData().getDefinition(), Toast.LENGTH_SHORT).show();
            RealmDictionaryBean b = presenter.addToRealmDictionary(bean.getData().getContent(), bean.getData().getDefinition());
            mList.add(b);
            mDictionaryAdapter.notifyDataSetChanged();
            mLinearLayoutManager.scrollToPositionWithOffset(mList.size() - 1, 0);
        } else {
            Toast.makeText(getContext(), "没有查询到", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}

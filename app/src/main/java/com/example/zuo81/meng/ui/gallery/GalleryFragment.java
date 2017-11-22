package com.example.zuo81.meng.ui.gallery;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseFragment;
import com.example.zuo81.meng.base.contract.gallery.Gallery;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.presenter.gallery.GalleryPresenter;
import com.example.zuo81.meng.ui.gallery.adapter.GalleryAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import cn.qqtheme.framework.picker.NumberPicker;

import static com.example.zuo81.meng.app.Constants.EXTRA_NAME_DETAIL_PIC_ACTIVITY;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends MVPBaseFragment<GalleryPresenter> implements Gallery.View
        , SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, GalleryAdapter.OnItemClickListener {

    @BindView(R.id.fab_fragment_gallery)
    FloatingActionButton fab;
    @BindView(R.id.rv_fragment_gallery)
    RecyclerView rvGallery;
    @BindView(R.id.swipe_refresh_layout_fragment_gallery)
    SwipeRefreshLayout swipeRefreshLayout;

    private GalleryAdapter adapter;
    private List<RealmPhotoBean> list;
    private boolean isLoadingMore = false;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void initEventAndData() {
        fab.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        final StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //fix issue #52 https://github.com/codeestX/GeekNews/issues/52
        //mStaggeredGridLayoutManager.setItemPrefetchEnabled(false);

        rvGallery.setLayoutManager(mStaggeredGridLayoutManager);
        list = presenter.getData();
        adapter = new GalleryAdapter(getContext(), list);
        adapter.setOnItemClickListener(this);
        rvGallery.setAdapter(adapter);
        rvGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] i = mStaggeredGridLayoutManager.findLastVisibleItemPositions(null);
                int lastItemPosition = Math.max(i[0], i[1]);
                if(lastItemPosition > adapter.getItemCount()-2 && !isLoadingMore && dy>0) {
                    isLoadingMore = true;
                    presenter.loadMoreData();
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        presenter.refreshData();
    }

    @Override
    public void refresh(List<RealmPhotoBean> mList) {
        //没有图片时isLoadingMore为true，会导致不能再加载，重置一下
        if(isLoadingMore) {
            isLoadingMore = false;
        }
        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        list.clear();
        list.addAll(mList);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "refresh success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore(List<RealmPhotoBean> mList) {
        Toast.makeText(getContext(), "load more", Toast.LENGTH_SHORT).show();
        if (mList.size() >0) {
            list.addAll(mList);
            adapter.notifyDataSetChanged();
            isLoadingMore = false;
        } else {
            Toast.makeText(getContext(), "no more pic", Toast.LENGTH_SHORT).show();
            isLoadingMore = true;
        }
    }

    @Override
    public void jump(List<RealmPhotoBean> mList) {
        //没有图片时isLoadingMore为true，会导致不能再加载，重置一下
        if(isLoadingMore) {
            isLoadingMore = false;
        }
        if (mList.size() > 0) {
            list.clear();
            list.addAll(mList);
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "jump success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(int position, List<RealmPhotoBean> list) {
//        start(DetailPicActivity.newInstance(position, list));
        Intent intent = new Intent(getActivity(), DetailPicActivity.class);
        intent.putExtra(EXTRA_NAME_DETAIL_PIC_ACTIVITY, list.get(position).getPhotoUrl());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.fab_fragment_gallery:
                NumberPicker picker = new NumberPicker(getActivity());
                picker.setWidth(picker.getScreenWidthPixels());
                picker.setCycleDisable(false);
                picker.setDividerVisible(false);
                picker.setOffset(2);//偏移量
                picker.setRange(1, 200, 4);//数字范围
                picker.setSelectedItem(5);
                picker.setLabel("页");
                picker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
                    @Override
                    public void onNumberPicked(int index, Number item) {
                        Logger.d("index=" + index + ", item=" + item.intValue());
                        presenter.jumpData(item.intValue());
                    }
                });
                picker.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}

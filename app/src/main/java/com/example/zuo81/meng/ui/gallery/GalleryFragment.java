package com.example.zuo81.meng.ui.gallery;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.presenter.gallery.GalleryPresenter;
import com.example.zuo81.meng.ui.gallery.adapter.GalleryAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.widget.WheelView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends SupportFragment implements GalleryView
        , SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, GalleryAdapter.OnItemClickListener {
    private RecyclerView rvGallery;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fab;
    private GalleryAdapter adapter;
    private GalleryPresenter presenter;
    private List<RealmPhotoBean> list;
    private boolean isLoadingMore = false;

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        fab = view.findViewById(R.id.fab_fragment_gallery);
        rvGallery = view.findViewById(R.id.rv_fragment_gallery);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_fragment_gallery);

        fab.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        presenter = new GalleryPresenter(this, getContext());
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
        return view;
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
        list.clear();
        list.addAll(mList);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "jump success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int position, List<RealmPhotoBean> list) {
        //start(DetailPicFragment.newInstance(position, list));
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
}

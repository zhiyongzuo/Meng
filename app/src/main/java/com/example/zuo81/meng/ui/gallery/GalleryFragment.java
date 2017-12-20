package com.example.zuo81.meng.ui.gallery;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseFragment;
import com.example.zuo81.meng.base.contract.gallery.Gallery;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.example.zuo81.meng.presenter.gallery.GalleryPresenter;
import com.example.zuo81.meng.ui.gallery.activity.DetailGalleryPicActivity;
import com.example.zuo81.meng.ui.gallery.adapter.GalleryAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
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
    private List<RealmPhotoBean> mList;
    private boolean isLoadingMore = false;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

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
        mList = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(this);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mStaggeredGridLayoutManager.setItemPrefetchEnabled(false);

        rvGallery.setLayoutManager(mStaggeredGridLayoutManager);
        adapter = new GalleryAdapter(getContext(), mList);
        adapter.setOnItemClickListener(this);
        rvGallery.setAdapter(adapter);
        rvGallery.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleCount = mStaggeredGridLayoutManager.getChildCount();
                int totalCount = mStaggeredGridLayoutManager.getItemCount();
                int[] firstCounts = mStaggeredGridLayoutManager.findFirstVisibleItemPositions(null);
                int firstCount = Math.max(firstCounts[0], firstCounts[1]);
                if(firstCount + visibleCount >= totalCount && !isLoadingMore) {
                    isLoadingMore = true;
                    presenter.loadMoreData();
                }
            }
        });
        presenter.getData();
    }

    @Override
    public void onRefresh() {
        presenter.refreshData();
    }

    @Override
    public void showContent(List<RealmPhotoBean> list) {
        mList.clear();
        mList.addAll(list);
        adapter.notifyDataSetChanged();
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
        this.mList.clear();
        this.mList.addAll(mList);
        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "refresh success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMore(List<RealmPhotoBean> mList) {
        Toast.makeText(getContext(), "load more", Toast.LENGTH_SHORT).show();
        if (mList.size() >0) {
            this.mList.addAll(mList);
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
            this.mList.clear();
            this.mList.addAll(mList);
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "jump success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(int position, List<RealmPhotoBean> list) {
        Intent intent = new Intent(getActivity(), DetailGalleryPicActivity.class);
        intent.putExtra(EXTRA_NAME_DETAIL_PIC_ACTIVITY, list.get(position).getPhotoUrl());
        getActivity().startActivity(intent);
    }

    @Override
    public void onLongClick(final int position) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setItems(R.array.items_content, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0:
                                Logger.d(position);
                                presenter.delete(mList.get(position));
                                mList.remove(position);
                                adapter.notifyDataSetChanged();//stay last
                                break;
                            case 1:
                                Logger.d("save");
                                break;
                        }
                    }
                })
                .create();
        dialog.show();
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

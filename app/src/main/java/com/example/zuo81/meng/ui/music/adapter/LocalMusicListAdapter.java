package com.example.zuo81.meng.ui.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by zuo81 on 2017/11/5.
 */

public class LocalMusicListAdapter extends RecyclerView.Adapter<LocalMusicListAdapter.LocalMusicListViewHolder> {

    private List<LocalMusicBean> list;
    private Context context;

    public LocalMusicListAdapter(List<LocalMusicBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public LocalMusicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_local_music, parent, false);
        return new LocalMusicListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalMusicListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class LocalMusicListViewHolder extends RecyclerView.ViewHolder {
        public LocalMusicListViewHolder(View itemView) {
            super(itemView);
        }
    }
}

package com.example.zuo81.meng.ui.gallery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.GlideApp;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;

import java.util.List;

/**
 * Created by zuo81 on 2017/11/9.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{
    private Context context;
    private List<RealmPhotoBean> list;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onClick(int position, List<RealmPhotoBean> list);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public GalleryAdapter(Context context, List<RealmPhotoBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gallery_adapter, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, final int position) {
        //Glide.with(context).load(list.get(position).getPhotoUrl()).into(holder.iv);
        GlideApp.with(context).load(list.get(position).getPhotoUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position, list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_item_gallery_adapter);
        }
    }
}

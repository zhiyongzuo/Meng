package com.example.zuo81.meng.ui.music.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;
import com.example.zuo81.meng.model.event.SearchEvent;

import java.util.List;

import static com.example.zuo81.meng.app.Constants.TYPE_PLAY_MUSIC;

/**
 * Created by zuo81 on 2017/11/3.
 */

public class OnlineMusicListAdapter extends RecyclerView.Adapter<OnlineMusicListAdapter.SearchViewHolder> {
    private Context context;
    private List<BaiDuMusicSearchBean> mList;
    private OnDetailClickListener listener;
    public interface OnDetailClickListener {
        void click();
    }
    public void setOnDeleteClickListener(OnDetailClickListener listener) {
           this.listener = listener;
    }

    public OnlineMusicListAdapter(Context context, List<BaiDuMusicSearchBean> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final BaiDuMusicSearchBean.SongListBean songListBean = mList.get(position).getSong_list().get(0);
        holder.tvSongName.setText(songListBean.getTitle());
        holder.tvAuthorAlbum.setText(songListBean.getAuthor() + " - " + songListBean.getAlbum_title());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RXBus.getInstance().post(new SearchEvent(songListBean.getSong_id(), TYPE_PLAY_MUSIC));
            }
        });
        holder.iv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context)
                        .setTitle(songListBean.getTitle())
                        .setItems(R.array.search_music_dialog, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch(i) {
                                    case 0:
                                        //share
                                        break;
                                    case 1:
                                        //ringtone
                                        break;
                                    case 2:
                                        //delete
                                        //setOnDetailClickListener(listener);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSongName;
        private TextView tvAuthorAlbum;
        private ImageView iv_detail;
        public SearchViewHolder(View itemView) {
            super(itemView);
            tvSongName = itemView.findViewById(R.id.tv_song_name);
            tvAuthorAlbum = itemView.findViewById(R.id.tv_author_album);
            iv_detail = itemView.findViewById(R.id.iv_detail);
        }
    }
}

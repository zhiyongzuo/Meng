package com.example.zuo81.meng.ui.music.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.component.RXBus;
import com.example.zuo81.meng.model.bean.music.LocalMusicBean;
import com.example.zuo81.meng.model.event.SearchEvent;
import com.example.zuo81.meng.utils.MusicUtils;

import static com.example.zuo81.meng.app.Constants.TYPE_PLAY_MUSIC;

/**
 * Created by zuo81 on 2017/11/24.
 */

public class LocalMusicListAdapter extends RecyclerView.Adapter<LocalMusicListAdapter.ViewHolder> {
    private Context context;
    private OnDeleteClickListener listener;
    public interface OnDeleteClickListener {
        void click();
    }
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.listener = listener;
    }

    public LocalMusicListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LocalMusicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        return new LocalMusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final LocalMusicBean songListBean = MusicUtils.getLocalMusicList().get(position);
        MusicUtils.getPlayService().showUI(songListBean);
        holder.tvSongName.setText(songListBean.getTitle());
        holder.tvAuthorAlbum.setText(songListBean.getArtist() + " - " + songListBean.getAlbum());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //RXBus.getInstance().post(new SearchEvent(songListBean.getSong_id(), TYPE_PLAY_MUSIC));
                MusicUtils.getPlayService().playMusic(songListBean);
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

        return MusicUtils.getLocalMusicList().size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSongName;
        private TextView tvAuthorAlbum;
        private ImageView iv_detail;
        private ConstraintLayout cl;
        public ViewHolder(View itemView) {
            super(itemView);
            tvSongName = itemView.findViewById(R.id.tv_song_name);
            tvAuthorAlbum = itemView.findViewById(R.id.tv_author_album);
            iv_detail = itemView.findViewById(R.id.iv_detail);
        }
    }
}

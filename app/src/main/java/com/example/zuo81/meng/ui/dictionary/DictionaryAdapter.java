package com.example.zuo81.meng.ui.dictionary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.DictionaryBean;
import com.example.zuo81.meng.utils.LetterTileProvider;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zuo81 on 2017/10/27.
 */

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryViewHolder> {
    private List<DictionaryBean> list;
    private Context context;
    private LetterTileProvider mLetterTileProvider;

    public DictionaryAdapter(Context context, List<DictionaryBean> list) {
        this.list = list;
        this.context = context;
        mLetterTileProvider = new LetterTileProvider(context);
    }

    @Override
    public DictionaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dictionary_item, null);
        DictionaryViewHolder holder = new DictionaryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final DictionaryViewHolder holder, final int position) {
        holder.mCircleImageView.setImageBitmap(mLetterTileProvider.getLetterTile(list.get(position).getData().getDefinition()));
        holder.mTextView.setText(list.get(position).getData().getDefinition());
        holder.mCircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mTextView.setText(list.get(position).getData().getContent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class DictionaryViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mCircleImageView;
        private TextView mTextView;
        public DictionaryViewHolder(View itemView) {
            super(itemView);
            mCircleImageView = itemView.findViewById(R.id.civ_item_dictionary);
            mTextView = itemView.findViewById(R.id.tv_item_dictionary);
        }
    }
}

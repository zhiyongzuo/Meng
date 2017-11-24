package com.example.zuo81.meng.ui.gallery.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.app.GlideApp;
import com.example.zuo81.meng.base.NoMVPBaseActivity;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.List;

import butterknife.BindView;

import static com.example.zuo81.meng.app.Constants.EXTRA_NAME_DETAIL_PIC_ACTIVITY;

public class DetailGalleryPicActivity extends NoMVPBaseActivity {

    @BindView(R.id.photo_view)
    PhotoView photoView;

    private int position;
    private List<RealmPhotoBean> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail_pic;
    }

    @Override
    protected void initEventAndData() {
        String url = getIntent().getStringExtra(EXTRA_NAME_DETAIL_PIC_ACTIVITY);
        GlideApp.with(this).load(url).into(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}

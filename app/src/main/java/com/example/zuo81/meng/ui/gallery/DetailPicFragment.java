package com.example.zuo81.meng.ui.gallery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.model.bean.realm.RealmPhotoBean;

import java.util.ArrayList;
import java.util.List;

import static com.example.zuo81.meng.app.Constants.DETAIL_FRAGMENT_PIC_LIST;
import static com.example.zuo81.meng.app.Constants.DETAIL_FRAGMENT_PIC_POSITION;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPicFragment extends Fragment {
    private int position;
    private List<RealmPhotoBean> list;


    public static DetailPicFragment newInstance(int position, ArrayList<RealmPhotoBean> list) {
        DetailPicFragment fragment = new DetailPicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DETAIL_FRAGMENT_PIC_POSITION, position);
        //bundle.putParcelableArrayList(DETAIL_FRAGMENT_PIC_LIST, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_pic, container, false);
    }

}

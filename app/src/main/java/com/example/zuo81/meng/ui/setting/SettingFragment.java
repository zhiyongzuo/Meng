package com.example.zuo81.meng.ui.setting;


import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.MVPBaseFragment;
import com.example.zuo81.meng.base.contract.setting.SettingContact;
import com.example.zuo81.meng.presenter.setting.SettingPresenter;
import com.example.zuo81.meng.utils.SPUtils;

import butterknife.OnClick;

import static com.example.zuo81.meng.app.Constants.DICTIONARY_FRAGMENT;
import static com.example.zuo81.meng.app.Constants.GALLERY_FRAGMENT;
import static com.example.zuo81.meng.app.Constants.MUSIC_FRAGMENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends MVPBaseFragment<SettingPresenter> implements SettingContact.View {

    public String[] fragmentArray = {"Dictionary", "Music", "Gallery"};

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(SettingFragment.this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.setting_fragment;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick(R.id.cv_selector_setting_fragment)
    public void choseFragment() {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.load_first)
                .setItems(fragmentArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i) {
                            case DICTIONARY_FRAGMENT:
                                break;
                            case MUSIC_FRAGMENT:
                                SPUtils.setFirstLoadFragment(MUSIC_FRAGMENT);
                                break;
                            case GALLERY_FRAGMENT:
                                SPUtils.setFirstLoadFragment(GALLERY_FRAGMENT);
                                break;
                        }
                    }
                }).create();
        dialog.show();
        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMsg(String msg) {

    }
}

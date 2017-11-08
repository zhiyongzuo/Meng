package com.example.zuo81.meng.ui.share;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zuo81.meng.R;
import com.example.zuo81.meng.base.BaseActivity;
import com.example.zuo81.meng.presenter.share.SharePresenter;
import com.orhanobut.logger.Logger;

import static android.content.Intent.EXTRA_STREAM;

public class SharePicActivity extends BaseActivity<SharePresenter> implements ShareView, View.OnClickListener {
    private TextView tvShareUrl;
    private Button btnOk;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_pic);
        tvShareUrl = findViewById(R.id.tv_share);

        presenter = new SharePresenter(this);

        Intent intent = getIntent();
        if(intent == null) {
            Logger.d("intent is null");
        } else {
            Bundle bundle = intent.getExtras();
            if(bundle == null) {
                Logger.d("bundle is null");
            } else {
                if(bundle.containsKey(EXTRA_STREAM)) {
                    Uri uri = (Uri)bundle.getParcelable(EXTRA_STREAM);
                    if (uri != null) {
                        Logger.d(uri.getPath());//  /storage/emulated/0/Android/data/com.null
                        String path = getRealPathFromURI(this, uri);
                        Logger.d(path);//  /storage/emulated/0/Android/data/com.null
                    } else {
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor actualimagecursor = managedQuery(uri, proj, null, null, null);
                        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor.getString(actual_image_column_index);
                        Logger.d(img_path);
                    }

                }
                switch(intent.getType()) {
                    case "text/plain":
                        Logger.d(bundle.get(Intent.EXTRA_TITLE) + "   " + bundle.get(Intent.EXTRA_TEXT));
                        String s = (String)bundle.get(Intent.EXTRA_TEXT);
                        tvShareUrl.setText(s);
                        break;
                    case "image/*":
                        Logger.d("image/*");
                        Logger.d(bundle.get(Intent.EXTRA_TITLE) + "   " + bundle.get(Intent.EXTRA_TEXT));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public String getRealPathFromURI(Activity act, Uri contentUri) {


        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = act.managedQuery(contentUri, proj, // Which columns to return

                null, // WHERE clause; which rows to return (all rows)

                null, // WHERE clause selection arguments (none)

                null); // Order-by clause (ascending by name)

        if (cursor==null) {

            String path = contentUri.getPath();

            return path;

        }

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_activity_menu, menu);
        MenuItem item = menu.findItem(R.id.share_ok);
        btnOk = (Button)item.getActionView();
        //btnOk.setOnClickListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        Logger.d("onClick");
        if (!TextUtils.isEmpty(url)) {
            presenter.inserIntoPicUrlDB(url);
        } else {
            Toast.makeText(this, "url is null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void closeActivity() {
        finish();
    }
}

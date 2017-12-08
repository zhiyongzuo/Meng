package com.example.zuo81.meng.presenter.main;

import com.example.zuo81.meng.base.contract.main.MainContract;
import com.example.zuo81.meng.base.presenter.RxBasePresenter;
import com.example.zuo81.meng.model.DataManager;
import com.example.zuo81.meng.utils.FileUtils;
import com.orhanobut.logger.Logger;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

import io.realm.Realm;

import static com.example.zuo81.meng.app.Constants.DB_PATH;
import static com.example.zuo81.meng.app.Constants.KEY_REALM_DB_NAME;
import static com.example.zuo81.meng.utils.FileUtils.restoreFromBackUp;
import static com.example.zuo81.meng.utils.QiniuUtil.getUpToken;
import static com.example.zuo81.meng.utils.QiniuUtil.getUploadManagerInstance;

/**
 * Created by zuo81 on 2017/12/1.
 */

public class MainPresenter extends RxBasePresenter<MainContract.View> implements MainContract.Presenter {
    private DataManager mDataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void backupRealmDB() {
        File backUpFile = new File(FileUtils.getExternalFileDir("BackUp"), KEY_REALM_DB_NAME);
        if(backUpFile.exists()) {
            backUpFile.delete();
        }
        /*try {
            //java.lang.IllegalArgumentException: The destination file must not exist
            backUpFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Realm realm = Realm.getDefaultInstance();
        realm.writeCopyTo(backUpFile);

        UploadOptions upOptions = new UploadOptions(null, null, false, new UpProgressHandler() {
            @Override
            public void progress(String s, double v) {
                //Logger.d(s + " / " + v);
            }
        }, new UpCancellationSignal() {
            @Override
            public boolean isCancelled() {
                return false;
            }
        });

        getUploadManagerInstance().put(backUpFile, KEY_REALM_DB_NAME, getUpToken(KEY_REALM_DB_NAME), new UpCompletionHandler() {
            @Override
            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                if(responseInfo.isOK()) {
                    Logger.d("ok");
                    view.toastSuccess("backup success");
                } else {
                    Logger.d(responseInfo.error);
                }
            }
        }, upOptions);
    }

    @Override
    public void restoreRealmDB() {
        restoreFromBackUp(FileUtils.getExternalFileDir("BackUp") + File.separator + KEY_REALM_DB_NAME, DB_PATH);
            /*final String restore_url = TEST_DOMAIN + KEY_REALM_DB_NAME + "?v=" + UUID.randomUUID().getLeastSignificantBits();
            Realm realm = Realm.getDefaultInstance();
            if (!realm.isClosed()) {
                realm.close();
            }
            String restorePath = Environment.getExternalStorageDirectory() + "/Meng/Restore";
            final File restorePathFile = new File(restorePath);
            if(!restorePathFile.exists()) {
                restorePathFile.mkdirs();
            }

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            File restoreFile = new File(restorePathFile, KEY_REALM_DB_NAME);
                            if(restoreFile.exists()) {
                                restoreFile.delete();
                            }
                            //restoreFile.createNewFile();
                            URL url = new URL(restore_url);
                            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
                            is = urlConn.getInputStream();
                            Logger.d("xxxx");
                            OutputStream os = new FileOutputStream(restoreFile);
                            byte[] b = new byte[1024];
                            while(is.read(b) != -1) {
                                os.write(b);
                            }
                            os.flush();
                            is.close();
                            os.close();

                            FileChannel inChannel = new FileInputStream(restoreFile).getChannel();
                            FileChannel outChannel = new FileOutputStream(new File(DB_PATH)).getChannel();
                            inChannel.transferTo(0, inChannel.size(), outChannel);
                            inChannel.close();
                            outChannel.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            view.toastSuccess("restore success");
    }

    @Override
    public void detachView() {
        super.detachView();
        mDataManager.closeRealm();
    }
}

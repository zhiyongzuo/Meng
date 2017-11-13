package com.example.zuo81.meng.utils;

import com.example.zuo81.meng.app.Constants;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;

import static com.example.zuo81.meng.app.Constants.BUCKET_NAME;

/**
 * Created by zuo81 on 2017/11/8.
 */

public class QiniuUtil {

    public static UploadManager getUploadManagerInstance() {
        return T.uploadManager;
    }

    static class T{
        static UploadManager uploadManager = new UploadManager(new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .recorder(null)           // recorder分片上传时，已上传片记录器。默认null
                .recorder(null, null)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(AutoZone.autoZone)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build());
    }

    //获取token(开发中放到业务服务器)
    public static String getUpToken(String key) {
        return Auth.create(Constants.ak, Constants.sk).uploadToken(BUCKET_NAME, key);
    }
}

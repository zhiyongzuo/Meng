package com.example.zuo81.meng.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bilibili.socialize.share.core.ui.BaseWXEntryActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.example.zuo81.meng.app.Constants.AppID;

/**
 * 这个类是微信回调的类
 */
public class WXEntryActivity extends BaseWXEntryActivity {

    @Override
    protected String getAppId() {
        return AppID;
    }
}

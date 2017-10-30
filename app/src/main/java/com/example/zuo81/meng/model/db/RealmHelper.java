package com.example.zuo81.meng.model.db;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zuo81 on 2017/10/27.
 */

public class RealmHelper implements DBHelper {

    private static final String DB_NAME = "myRealm.realm";
    private Realm mRealm;

    public RealmHelper() {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());
    }
}

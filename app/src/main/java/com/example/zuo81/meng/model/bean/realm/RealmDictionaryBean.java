package com.example.zuo81.meng.model.bean.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zuo81 on 2017/10/31.
 */

public class RealmDictionaryBean extends RealmObject {

    @PrimaryKey
    private long id;
    private String english;
    private String chinese;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getEnglish() {
        return english;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }


}

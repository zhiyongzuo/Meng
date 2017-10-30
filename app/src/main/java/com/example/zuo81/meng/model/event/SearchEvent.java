package com.example.zuo81.meng.model.event;

/**
 * Created by zuo81 on 2017/10/30.
 */

public class SearchEvent {
    private String query;
    private int type;

    public SearchEvent(String query, int type) {
        this.query = query;
        this.type = type;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

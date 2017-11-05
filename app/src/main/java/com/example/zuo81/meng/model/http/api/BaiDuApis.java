package com.example.zuo81.meng.model.http.api;

import com.example.zuo81.meng.model.bean.music.BaiDuPlayBean;
import com.example.zuo81.meng.model.bean.music.BaiDuMusicSearchBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zuo81 on 2017/11/2.
 */

public interface BaiDuApis {

//http://tingapi.ting.baidu.com/v1/restserver/ting?method=baidu.ting.search.common&query=%E7%88%B1%E6%88%91%E8%BF%98%E6%98%AF%E4%BB%96&page_size=30&page_no=1&format=xml

    String Host = "http://tingapi.ting.baidu.com/v1/restserver/";
    String Method_search = "baidu.ting.search.common";
    String Method_play = "baidu.ting.song.play";

    @GET("ting")
    Flowable<List<BaiDuMusicSearchBean>> fetchMusicListInfo(@Query("method") String method, @Query("query") String query, @Query("page_size") String pageSize, @Query("format") String format);

    @GET("ting")
    Flowable<BaiDuPlayBean> playMusicLink(@Query("method") String method, @Query("songid") String songId);
}

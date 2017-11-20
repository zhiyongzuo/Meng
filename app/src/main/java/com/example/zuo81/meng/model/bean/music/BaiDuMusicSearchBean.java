package com.example.zuo81.meng.model.bean.music;

import java.util.List;

/**
 * Created by zuo81 on 2017/11/2.
 */

public class BaiDuMusicSearchBean {

    /**
     * query : 爱我还是他
     * is_artist : 0
     * is_album : 0
     * rs_words :
     * pages : {"total":"2","rn_num":"2"}
     * song_list : [{"title":"爱我还是他","song_id":"31197196","author":"洪卓立","artist_id":"913","all_artist_id":"913","album_title":"Neway SearchMusic Live X 洪卓立音乐会","appendix":"现场","album_id":"31199665","lrclink":"/data2/lrc/65425384/65425384.lrc","resource_type":"0","content":"","relate_status":0,"havehigh":2,"copy_type":"1","del_status":"0","all_rate":"24,64,128,192,256,320,flac","has_mv":0,"has_mv_mobile":0,"mv_provider":"0000000000","charge":0,"toneid":"0","info":"","data_source":0,"learn":0},{"title":"爱我还是他","song_id":"2109208","author":"群星","artist_id":"313607","all_artist_id":"313607","album_title":"LOVE 国语情歌集","appendix":"","album_id":"7311955","lrclink":"/data2/lrc/13910062/13910062.lrc","resource_type":"0","content":"","relate_status":1,"havehigh":2,"copy_type":"0","del_status":"0","all_rate":"64,128,256,320","has_mv":0,"has_mv_mobile":1,"mv_provider":"1100000000","charge":0,"toneid":"0","info":"","data_source":0,"learn":1},{"title":"空气稀薄","song_id":"13903108","author":"杨杰荃","artist_id":"13903099","all_artist_id":"13903099","album_title":"新新人类","appendix":"","album_id":"13903112","lrclink":"/data2/lrc/241835653/241835653.lrc","resource_type":"0","content":"遇到分岔\n是爱我还是他\n不要再用双眼泛起的泪光\n当作是你的回答\n\n空气稀薄吗\n怎么连见面都变得尴尬\n满腮的胡渣\n是我太过潇洒\n还是太邋遢OHYEYE\n\n紧随的步伐\n经不起风沙\n在中途就","relate_status":0,"havehigh":2,"copy_type":"0","del_status":"0","all_rate":"128","has_mv":0,"has_mv_mobile":0,"mv_provider":"0000000000","charge":0,"toneid":"","info":"","data_source":0,"learn":0},{"title":"累了就散了","song_id":"116791887","author":"王惠杰","artist_id":"116789723","all_artist_id":"116789723","album_title":"","appendix":"","album_id":"0","lrclink":"/data2/lrc/116791806/116791806.txt","resource_type":"0","content":"谎话\n你到底爱我还是他\n你爱我还是他\n啊可笑可笑可笑吧\n我值得值得我值得吗\n我是对了还是错了吗\n谁能给我个回答\n谁能给我个回答\n给我个回答\n啊散了散了散了吧\n我忘了忘了我忘","relate_status":1,"havehigh":2,"copy_type":"1","del_status":"0","all_rate":"128","has_mv":0,"has_mv_mobile":0,"mv_provider":"0000000000","charge":0,"toneid":"","info":"","data_source":0,"learn":0},{"title":"我我我他他他","song_id":"32996797","author":"王绎龙","artist_id":"1810","all_artist_id":"1810","album_title":"DJ万万岁","appendix":"混音","album_id":"32996738","lrclink":"/data2/lrc/65430904/65430904.lrc","resource_type":"0","content":"诉我你到底爱我还是他\n为何心里明明有他还要对我说那些情话\n现在请你不要犹豫说你心里真实的回答\n离开的人是我还是他Please Baby\n你爱的人是我我我我吗\n别再说爱着他他他的话\n你","relate_status":0,"havehigh":2,"copy_type":"0","del_status":"0","all_rate":"128","has_mv":0,"has_mv_mobile":0,"mv_provider":"0000000000","charge":0,"toneid":"","info":"","data_source":0,"learn":0}]
     */

    private String query;
    private int is_artist;
    private int is_album;
    private String rs_words;
    private PagesBean pages;
    private List<SongListBean> song_list;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getIs_artist() {
        return is_artist;
    }

    public void setIs_artist(int is_artist) {
        this.is_artist = is_artist;
    }

    public int getIs_album() {
        return is_album;
    }

    public void setIs_album(int is_album) {
        this.is_album = is_album;
    }

    public String getRs_words() {
        return rs_words;
    }

    public void setRs_words(String rs_words) {
        this.rs_words = rs_words;
    }

    public PagesBean getPages() {
        return pages;
    }

    public void setPages(PagesBean pages) {
        this.pages = pages;
    }

    public List<SongListBean> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<SongListBean> song_list) {
        this.song_list = song_list;
    }

    public static class PagesBean {
        /**
         * total : 2
         * rn_num : 2
         */

        private String total;
        private String rn_num;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getRn_num() {
            return rn_num;
        }

        public void setRn_num(String rn_num) {
            this.rn_num = rn_num;
        }
    }

    public static class SongListBean {
        /**
         * title : 爱我还是他
         * song_id : 31197196
         * author : 洪卓立
         * artist_id : 913
         * all_artist_id : 913
         * album_title : Neway SearchMusic Live X 洪卓立音乐会
         * appendix : 现场
         * album_id : 31199665
         * lrclink : /data2/lrc/65425384/65425384.lrc
         * resource_type : 0
         * content :
         * relate_status : 0
         * havehigh : 2
         * copy_type : 1
         * del_status : 0
         * all_rate : 24,64,128,192,256,320,flac
         * has_mv : 0
         * has_mv_mobile : 0
         * mv_provider : 0000000000
         * charge : 0
         * toneid : 0
         * info :
         * data_source : 0
         * learn : 0
         */

        private String title;
        private String song_id;
        private String author;
        private String artist_id;
        private String all_artist_id;
        private String album_title;
        private String appendix;
        private String album_id;
        private String lrclink;
        private String resource_type;
        private String content;
        private int relate_status;
        private int havehigh;
        private String copy_type;
        private String del_status;
        private String all_rate;
        private int has_mv;
        private int has_mv_mobile;
        private String mv_provider;
        private int charge;
        private String toneid;
        private String info;
        private int data_source;
        private int learn;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSong_id() {
            return song_id;
        }

        public void setSong_id(String song_id) {
            this.song_id = song_id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(String artist_id) {
            this.artist_id = artist_id;
        }

        public String getAll_artist_id() {
            return all_artist_id;
        }

        public void setAll_artist_id(String all_artist_id) {
            this.all_artist_id = all_artist_id;
        }

        public String getAlbum_title() {
            return album_title;
        }

        public void setAlbum_title(String album_title) {
            this.album_title = album_title;
        }

        public String getAppendix() {
            return appendix;
        }

        public void setAppendix(String appendix) {
            this.appendix = appendix;
        }

        public String getAlbum_id() {
            return album_id;
        }

        public void setAlbum_id(String album_id) {
            this.album_id = album_id;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getRelate_status() {
            return relate_status;
        }

        public void setRelate_status(int relate_status) {
            this.relate_status = relate_status;
        }

        public int getHavehigh() {
            return havehigh;
        }

        public void setHavehigh(int havehigh) {
            this.havehigh = havehigh;
        }

        public String getCopy_type() {
            return copy_type;
        }

        public void setCopy_type(String copy_type) {
            this.copy_type = copy_type;
        }

        public String getDel_status() {
            return del_status;
        }

        public void setDel_status(String del_status) {
            this.del_status = del_status;
        }

        public String getAll_rate() {
            return all_rate;
        }

        public void setAll_rate(String all_rate) {
            this.all_rate = all_rate;
        }

        public int getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(int has_mv) {
            this.has_mv = has_mv;
        }

        public int getHas_mv_mobile() {
            return has_mv_mobile;
        }

        public void setHas_mv_mobile(int has_mv_mobile) {
            this.has_mv_mobile = has_mv_mobile;
        }

        public String getMv_provider() {
            return mv_provider;
        }

        public void setMv_provider(String mv_provider) {
            this.mv_provider = mv_provider;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public String getToneid() {
            return toneid;
        }

        public void setToneid(String toneid) {
            this.toneid = toneid;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getData_source() {
            return data_source;
        }

        public void setData_source(int data_source) {
            this.data_source = data_source;
        }

        public int getLearn() {
            return learn;
        }

        public void setLearn(int learn) {
            this.learn = learn;
        }
    }
}

package com.ahulproject.webnime.otakuanime;

public class Websites {

    private int _id;
    private String _url;
    private String title;

    public Websites(){
        // emptry constructor
    }

    public Websites(String url){
        this._url = url;
    }

    public Websites(String url, String title){
        this._url = url;
        this.title = title;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

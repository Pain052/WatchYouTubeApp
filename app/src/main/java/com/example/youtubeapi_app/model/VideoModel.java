package com.example.youtubeapi_app.model;

public class VideoModel {
    private String idVideo;
    private String title;
    private String urlImage;
    private String chanelName;

    public VideoModel() {
    }

    public VideoModel(String idVideo, String title, String urlImage, String chanelName) {
        this.idVideo = idVideo;
        this.title = title;
        this.urlImage = urlImage;
        this.chanelName = chanelName;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getChanelName() {
        return chanelName;
    }

    public void setChanelName(String chanelName) {
        this.chanelName = chanelName;
    }
}

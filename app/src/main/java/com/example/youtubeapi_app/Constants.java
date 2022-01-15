package com.example.youtubeapi_app;

public class Constants {
    public static String YOUTUBE_API_KEY = "AIzaSyDClgE-KIuK71dOxsYEsrQl_e-z5qg9-p4";
    public static String ID_PLAYLIST = "PL6XRrncXkMaW5p7muaR2s2IqjouQh4jqS";
    public static int REQUEST_VIDEO  = 100;
    public static String URL_PLAYLIST =
            "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&" +
                    "playlistId=" + ID_PLAYLIST +
                    "&key=" + YOUTUBE_API_KEY +
                    "&maxResults=10000";
}

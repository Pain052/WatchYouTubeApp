package com.example.youtubeapi_app;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapi_app.Adapter.VideoAdapter;
import com.example.youtubeapi_app.model.VideoModel;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailVideoActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    TextView tvTitle, tvTime, tvChanelName;

    ListView listViewVideoDetail;
    ArrayList<VideoModel> videoModelLists;
    VideoAdapter adapter;

    YouTubePlayerView youTubePlayerView;
    String idVideo = "", titleVideo = "", channelTitle = "", publishedAtTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);

        youTubePlayerView = findViewById(R.id.mYouTube);
        tvTitle = findViewById(R.id.tvTitleDetail);
        tvTime = findViewById(R.id.tvTime);
        tvChanelName = findViewById(R.id.tvChanelName);
        listViewVideoDetail = findViewById(R.id.listViewVideoDetail);

        //Get data from Home sender for this
        Intent intent = getIntent();
        idVideo = intent.getStringExtra("ID_VIDEO");
        titleVideo = intent.getStringExtra("TITLE_VIDEO");
        publishedAtTime = intent.getStringExtra("PUBLISH_AT");
        channelTitle = intent.getStringExtra("CHANNEL_NAME");

        setDataOnTextView(titleVideo, publishedAtTime, channelTitle);

        //init array list
        videoModelLists = new ArrayList<>();

        //init Adapter
        adapter = new VideoAdapter(this, R.layout.row_video_youtube, videoModelLists);
        listViewVideoDetail.setAdapter(adapter);

        youTubePlayerView.initialize(Constants.YOUTUBE_API_KEY, this);
        getJsonYoutube(Constants.URL_PLAYLIST);

        listViewVideoDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DetailVideoActivity.this, DetailVideoActivity.class);
                intent.putExtra("ID_VIDEO", videoModelLists.get(i).getIdVideo());
                intent.putExtra("TITLE_VIDEO", videoModelLists.get(i).getTitle());
                intent.putExtra("PUBLISH_AT", videoModelLists.get(i).getPublishAt());
                intent.putExtra("CHANNEL_NAME", videoModelLists.get(i).getChanelName());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(idVideo);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(this, Constants.REQUEST_VIDEO);
        }else{
            Toast.makeText(this, "AN ERROR HAS OCCURRED!", Toast.LENGTH_SHORT).show();
            Log.e("Error", youTubeInitializationResult.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_VIDEO){
            youTubePlayerView.initialize(Constants.YOUTUBE_API_KEY, DetailVideoActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getJsonYoutube(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonItems = response.getJSONArray("items");

                            String title = "", channelTitle = "", publishedAt = "", urlImageVideo = "", videoId= "";

                            for (int i = 0; i < jsonItems.length(); i++){
                                JSONObject jsonObject = jsonItems.getJSONObject(i);
                                JSONObject jsonSnippet = jsonObject.getJSONObject("snippet");

                                //Get Information important
                                title = jsonSnippet.getString("title");
                                channelTitle = jsonSnippet.getString("channelTitle");
                                publishedAt = jsonSnippet.getString("publishedAt");

                                //Get Url Image Video
                                JSONObject thumbnails = jsonSnippet.getJSONObject("thumbnails");
                                JSONObject medium = thumbnails.getJSONObject("medium");
                                urlImageVideo = medium.getString("url");

                                //Get ID Video
                                JSONObject resourceId = jsonSnippet.getJSONObject("resourceId");
                                videoId = resourceId.getString("videoId");

                                videoModelLists.add(new VideoModel(videoId, title, urlImageVideo, channelTitle, publishedAt));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                Toast.makeText(HomeActivity.this, "response " + response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailVideoActivity.this, "ERROR " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void setDataOnTextView(String titleVideo, String publishAtVideo, String channelNameOfVideo){
        tvTitle.setText(titleVideo);
        tvTime.setText(publishAtVideo);
        tvChanelName.setText(channelNameOfVideo + " NEWS");
    }

    //redirect to HomeActivity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
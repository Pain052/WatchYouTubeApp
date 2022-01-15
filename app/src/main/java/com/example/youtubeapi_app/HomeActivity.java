package com.example.youtubeapi_app;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ListView lvVideo;
    ArrayList<VideoModel> videoModelLists;
    VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lvVideo = findViewById(R.id.listViewVideo);
        videoModelLists = new ArrayList<>();

        //init Adapter
        adapter = new VideoAdapter(this, R.layout.row_video_youtube, videoModelLists);
        lvVideo.setAdapter(adapter);

        getJsonYoutube(Constants.URL_PLAYLIST);

        lvVideo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HomeActivity.this, DetailVideoActivity.class);
                intent.putExtra("ID_VIDEO", videoModelLists.get(i).getIdVideo());
                intent.putExtra("TITLE_VIDEO", videoModelLists.get(i).getTitle());
                intent.putExtra("PUBLISH_AT", videoModelLists.get(i).getPublishAt());
                intent.putExtra("CHANNEL_NAME", videoModelLists.get(i).getChanelName());
                startActivity(intent);
            }
        });
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
                Toast.makeText(HomeActivity.this, "ERROR " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
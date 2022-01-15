package com.example.youtubeapi_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubeapi_app.R;
import com.example.youtubeapi_app.model.VideoModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<VideoModel> videoModels;

    public VideoAdapter(Context context, int layout, List<VideoModel> videoModels) {
        this.context = context;
        this.layout = layout;
        this.videoModels = videoModels;
    }

    @Override
    public int getCount() {
        return videoModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvTitle = view.findViewById(R.id.tvTitle);
            holder.tvChanel = view.findViewById(R.id.tvChanel);
            holder.ivImageVideo = view.findViewById(R.id.ivImageVideo);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        VideoModel videoModel = videoModels.get(i);

        holder.tvTitle.setText(videoModel.getTitle());
        holder.tvChanel.setText(videoModel.getChanelName());
        Picasso.get().load(videoModel.getUrlImage()).placeholder(R.drawable.ic_youtube_240px).into(holder.ivImageVideo);
        return view;
    }

    private class ViewHolder{
        ImageView ivImageVideo;
        TextView tvTitle, tvChanel;
    }
}

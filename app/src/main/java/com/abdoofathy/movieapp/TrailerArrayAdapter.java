package com.abdoofathy.movieapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TrailerArrayAdapter extends ArrayAdapter<MovieTrailerUrl> {
    List<MovieTrailerUrl> trailersUrls;
    Context context;

    public TrailerArrayAdapter(Context context, int resource, List<MovieTrailerUrl> trailersUrls) {
        super(context, resource, trailersUrls);
        this.trailersUrls = trailersUrls;
        this.context = context;
    }

    @Override
    public int getCount() {
        return trailersUrls.size();
    }

    @Override
    public MovieTrailerUrl getItem(int position) {
        return trailersUrls.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.trailer_item,parent,false);
            viewHolder.trailerNameTextView = (TextView)convertView.findViewById(R.id.trailerTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.trailerNameTextView.setText(trailersUrls.get(position).getTrailerName());
        return convertView;
    }

    static class ViewHolder{
        TextView trailerNameTextView;
    }
}

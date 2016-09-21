package com.abdoofathy.movieapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    Context context;
    private List<Movie> moviesList;
    public ImageAdapter(Context context, List<Movie> moviesList){
        this.context = context;
        this.moviesList = moviesList;
    }

    public void updateData(List<Movie> list){
        moviesList = list;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int i) {
        return moviesList.get(i);
    }

    @Override
    public long getItemId(int i) { // no idea what to do with this
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            view = ((Activity)context).getLayoutInflater().inflate(R.layout.grid_item,viewGroup,false);
            viewHolder.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Picasso.with(context).load(moviesList.get(i).getPosterImageURL()).into(viewHolder.thumbnail);
        return view;
    }

    static class ViewHolder{
        ImageView thumbnail;
    }
}

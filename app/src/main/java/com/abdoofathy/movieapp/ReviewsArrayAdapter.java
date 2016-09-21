package com.abdoofathy.movieapp;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ReviewsArrayAdapter extends ArrayAdapter {
    Context context;
    List reviewsList;

    public ReviewsArrayAdapter(Context context, int resource, List reviewsList) {
        super(context, resource, reviewsList);
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @Override
    public int getCount() {
        return reviewsList.size();
    }

    @Override
    public Review getItem(int position) {
        return (Review)reviewsList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = ((Activity)context).getLayoutInflater().inflate(R.layout.review_item,parent,false);
            viewHolder.reviewAuthorTextView = (TextView)convertView.findViewById(R.id.reviewAuthorTextView);
            viewHolder.reviewContentTextView = (TextView)convertView.findViewById(R.id.reviewContentTextView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.reviewAuthorTextView.setText(((Review)reviewsList.get(position)).getAuthor());
        viewHolder.reviewContentTextView.setText(((Review)reviewsList.get(position)).getContent());
        return convertView;
    }

    static class ViewHolder{
        TextView reviewAuthorTextView;
        TextView reviewContentTextView;
    }
}

package com.agoogler.rafi.w3news;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by rafi on 6/10/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, ArrayList<News> word)
    {
        super(context, 0, word);


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }



        // Get the {@link AndroidFlavor} object located at this position in the list
        News currentNews = getItem(position);


        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        // Format the magnitude to show 1 decimal place
        String title = currentNews.getTitle();
        // Display the magnitude of the current earthquake in that TextView
        titleView.setText(title);

        TextView descView = (TextView) listItemView.findViewById(R.id.desc);
        // Format the magnitude to show 1 decimal place
        String desc = currentNews.getDesc();
        // Display the magnitude of the current earthquake in that TextView
        descView.setText(desc);

        ImageView imageurl = (ImageView) listItemView.findViewById(R.id.image);
        String imagelink = currentNews.getUrlToImage();
        Context c = getContext();
        Glide.with(c)
                .load(imagelink)
                .into(imageurl);



        // Return the list item view that is now showing the appropriate data
        return listItemView;

    }

}

package com.agoogler.rafi.w3news;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by rafi on 6/16/17.
 */

public class NewsSearchLoader extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    public NewsSearchLoader(Context context, String url) {

        super(context);
        mUrl = url;
        Log.i(LOG_TAG, "News Loader constructer executing");
    }

    @Override
    protected void onStartLoading() {

        Log.i(LOG_TAG, "onstartloading executing");

        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {

        Log.i(LOG_TAG, "Load in backgroud executing");
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<News> news = QuerySearchUtils.fetchNewseData(mUrl);
        return news;
    }
}

package com.agoogler.rafi.w3news;


import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.agoogler.rafi.w3news.MainActivity.LOG_TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class WSJFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {


    private static final int NEWS_LOADER_ID = 1;
    private static String REQUEST_URL;
    private NewsAdapter mAdapter;

    private TextView mEmptyStateTextView;
    private View rootView;
    private WebView mWebView;
    private ProgressBar progress;


    public WSJFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.newsview, container, false);

        mWebView = (WebView) rootView.findViewById(R.id.webview);
        mWebView.setVisibility(View.GONE);
        progress = (ProgressBar) rootView.findViewById(R.id.loading_spinner);

        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        ListView NewsListView = (ListView) rootView.findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        NewsListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_view);
        NewsListView.setEmptyView(mEmptyStateTextView);

        if (isConnected) {


            LoaderManager loaderManager = getActivity().getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).

            Bundle b = new Bundle();
            REQUEST_URL = "https://newsapi.org/v1/articles?source=the-wall-street-journal&sortBy=top&apiKey=1e4b682134774d0bb89f6d12a78f9028";

            b.putString("REQUEST_URL", REQUEST_URL);

            Log.i(LOG_TAG, "REQUEST URL 1 executing");
            loaderManager.initLoader(NEWS_LOADER_ID, b, this);


        } else {
            progress.setVisibility(View.INVISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet);
        }

        NewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                News currentNews = mAdapter.getItem(position);


                String newsUrl = currentNews.getUrl();

                progress.setVisibility(View.VISIBLE);

                mWebView.setVisibility(View.VISIBLE);

                mWebView.loadUrl(newsUrl);

                mWebView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(mWebView, url);
                        progress.setVisibility(View.GONE);

                    }

                });


            }


        });


        return rootView;
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader executing");

        String tmp = args.getString("REQUEST_URL");

        return new NewsLoader(getActivity(), tmp);


    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        Log.i(LOG_TAG, "onFinishLoader executing");
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }

        mEmptyStateTextView.setText(R.string.no_news);
        ProgressBar progress = (ProgressBar) rootView.findViewById(R.id.loading_spinner);
        progress.setVisibility(View.INVISIBLE);
        Log.i(LOG_TAG, "INVISIBLE");
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.

        Log.i(LOG_TAG, "onResetLoader executing");
        mAdapter.clear();
    }

}

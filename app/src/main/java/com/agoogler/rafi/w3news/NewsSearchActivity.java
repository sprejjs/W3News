package com.agoogler.rafi.w3news;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
 * Created by rafi on 6/16/17.
 */

public class NewsSearchActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private final int NEWS_LOADER_ID = 6;
    private String REQUEST_URL;
    private NewsAdapter mAdapter;

    private TextView mEmptyStateTextView;

    private WebView mWebView;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsview);
        // Get the Intent that started this activity and extract the string

        handleIntent(getIntent());
        //Intent intent = getIntent();
        //String message = intent.getStringExtra("EXTRA_MESSAGE");
        //execute_search(message);


    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    /**
     * Handling intent data
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            execute_search(query);

            setTitle(query); // setting action bar title as search query


        }

    }


    void execute_search(String query) {

        mWebView = (WebView) findViewById(R.id.xwebview);
        mWebView.setVisibility(View.GONE);
        progress = (ProgressBar) findViewById(R.id.loading_spinner);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        ListView NewsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        NewsListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        NewsListView.setEmptyView(mEmptyStateTextView);

        if (isConnected) {


            LoaderManager loaderManager = this.getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).

            Bundle b = new Bundle();
            REQUEST_URL = "https://content.guardianapis.com/search?q=" + query + "&api-key=test&show-fields=thumbnail,trailText,body&order-by=relevance&page-size=20";

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
                News currentNews = mAdapter.getItem(position);
                String newsUrl = currentNews.getUrl();

                Intent detailsIntent = new Intent(getApplicationContext(), ArticleDetailsActivity.class);
                detailsIntent.putExtra(ArticleDetailsActivity.KEY_URL, newsUrl);
                startActivity(detailsIntent);
            }


        });


    }

    @Override
    public void onBackPressed() {
        mWebView = (WebView) findViewById(R.id.xwebview);
        if (mWebView.getVisibility() == View.VISIBLE) {
            mWebView.setVisibility(View.GONE);
            mWebView.loadUrl("about:blank");
        } else {

            super.onBackPressed();
        }

        return;
    }


    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle args) {
        Log.i(LOG_TAG, "onCreateLoader executing");

        String tmp = args.getString("REQUEST_URL");

        return new NewsSearchLoader(this, tmp);


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

        mEmptyStateTextView.setText(" No data returned");
        ProgressBar progress = (ProgressBar) findViewById(R.id.loading_spinner);
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

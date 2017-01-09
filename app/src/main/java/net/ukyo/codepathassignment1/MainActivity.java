package net.ukyo.codepathassignment1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ListAdapter mListAdapter;
    private ProgressBar progressBar;
    private String mApiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private MovieGson mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setProgress();
        network(mApiUrl);
    }

    private void setProgress() {
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    private void setList() {
        mListView = (ListView) findViewById(R.id.list);
        mListAdapter = new ListAdapter(this, mData, getScreenWidth());
        mListView.setAdapter(mListAdapter);
    }

    private void network(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", throwable.toString());
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                progressBar.setVisibility(View.GONE);
                Gson gson = new Gson();
                mData = gson.fromJson(responseString, MovieGson.class);
                setList();
            }
        });
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}

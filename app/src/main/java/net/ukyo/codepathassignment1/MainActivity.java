package net.ukyo.codepathassignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private ListAdapter mListAdapter;
    private String mApiUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private MovieGson mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        network(mApiUrl);
    }

    private void setList() {
        mListView = (ListView) findViewById(R.id.list);
        mListAdapter = new ListAdapter(this, mData, getScreenWidth());
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mData != null) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, DetailActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("image", mData.results.get(position).backdrop_path);
                    bundle.putString("title", mData.results.get(position).title);
                    bundle.putString("release_date", mData.results.get(position).release_date);
                    bundle.putFloat("rating", mData.results.get(position).vote_average);
                    bundle.putString("overview", mData.results.get(position).overview);

                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void network(String url) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", throwable.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
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

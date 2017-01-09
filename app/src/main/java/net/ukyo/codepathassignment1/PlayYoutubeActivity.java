package net.ukyo.codepathassignment1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by ukyo on 2017/1/9.
 * Play Youtube video
 */

public class PlayYoutubeActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "your youtube api key";
    private YouTubePlayerView youTubePlayerView;
    private String mVideoId;
    private String mVideoKey;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        findViews();
        dataInit();
        network();
    }

    private void findViews() {
        setContentView(R.layout.activity_play_youtube);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);
    }

    private void dataInit() {
        Bundle bundle = getIntent().getExtras();
        mVideoId = bundle.getString("videoId");
    }

    private void network() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.get(getApiUrl(mVideoId), params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e("ERROR", throwable.toString());
                Toast.makeText(PlayYoutubeActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                mVideoKey = gson.fromJson(responseString, YoutubeGson.class).results.get(0).key;

                //launch youtube player if load api success
                loadYoutube(YOUTUBE_API_KEY);
            }
        });
    }

    private void loadYoutube(String key) {
        youTubePlayerView.initialize(key, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {

                youTubePlayer.cueVideo(mVideoKey);
            }

            @Override
            public void onInitializationFailure(
                    YouTubePlayer.Provider provider,
                    YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    private String getApiUrl(String videoId) {
        return "https://api.themoviedb.org/3/movie/"
                + videoId + "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    }
}
package br.com.livetouch.twitterlivetouch;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.BaseAdapter;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TimelineActivity extends ListActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);


        userName = getIntent().getStringExtra("userName");
        Log.d("TwitterKit", userName);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(userName)
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();

        setListAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        swipeRefreshLayout.setRefreshing(true);
                        adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                            @Override
                            public void success(Result<TimelineResult<Tweet>> result) {
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                // Toast or some other action
                            }
                        });
                    }
                });
    }

    private Context getContext() {
        return this;
    }
}

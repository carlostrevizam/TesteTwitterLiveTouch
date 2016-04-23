package br.com.livetouch.twitterlivetouch;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.BaseAdapter;

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

        loadListAdapter();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener());
    }

    private Context getContext() {
        return this;
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener(){
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadListAdapter();

                ((BaseAdapter) getListAdapter()).notifyDataSetChanged();

                swipeRefreshLayout.setRefreshing(false);
            }
        };
    }

    private void loadListAdapter() {

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(userName)
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();

        setListAdapter(adapter);
    }
}

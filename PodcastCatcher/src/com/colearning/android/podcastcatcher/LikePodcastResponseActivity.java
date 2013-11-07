package com.colearning.android.podcastcatcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class LikePodcastResponseActivity extends Activity {
	public static final String DOES_LIKE_PODCASTS = "com.colearning.android.podcastcatcher.doesLikePodcasts";
	private TextView txtLikePodcastResponse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_like_podcast_response);
		txtLikePodcastResponse = (TextView) findViewById(R.id.txtLikePodcastResponse);

		boolean doesLikePodcasts = getIntent().getBooleanExtra(DOES_LIKE_PODCASTS, false);
		String response = "I have no clue if you like podcasts or not...";
		if (doesLikePodcasts) {
			response = "I am so happy that you do like podcasts";
		} else {
			response = "You are not going to like this application...";
		}

		txtLikePodcastResponse.setText(response);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.like_podcast_response, menu);
		return true;
	}

}

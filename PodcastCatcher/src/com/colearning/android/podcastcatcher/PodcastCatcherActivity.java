package com.colearning.android.podcastcatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class PodcastCatcherActivity extends Activity {
	private static final String TAG = "PodcastCatcherActivity";

	private Button btnYes;
	private Button btnNo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_podcast_catcher);

		Button btnYes = (Button) findViewById(R.id.btnYes);
		btnYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "Yes was clicked!!!");
				handleStartingLikePodcastResponseActivity(true);
			}
		});

		Button btnNo = (Button) findViewById(R.id.btnNo);
		btnNo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.i(TAG, "No was clicked!!!");
				handleStartingLikePodcastResponseActivity(false);
			}
		});
	}

	public void handleStartingLikePodcastResponseActivity(boolean doesLikePodcasts) {
		Intent intent = new Intent(PodcastCatcherActivity.this, LikePodcastResponseActivity.class);
		intent.putExtra(LikePodcastResponseActivity.DOES_LIKE_PODCASTS, doesLikePodcasts);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.podcast_catcher, menu);
		return true;
	}

	@Override
	protected void onPause() {
		Log.i(TAG, "onPause...");
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.i(TAG, "onResume...");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.i(TAG, "onStart...");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.i(TAG, "onStop...");
		super.onStop();
	}

}

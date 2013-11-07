package com.colearning.android.podcastpoll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public class PodcastPollActivity extends FragmentActivity {
	private static final String TAG = "PodcastPollActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);

		FragmentManager fm = getSupportFragmentManager();

		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
			fragment = new PodcastPollFragment();
			//@formatter:off
			fm
				.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
			//@formatter:on
		}

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

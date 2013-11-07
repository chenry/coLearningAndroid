package com.colearning.android.podcastpoll;

import android.support.v4.app.Fragment;
import android.util.Log;

public class PodcastPollActivity extends OneFragmentFragmentActivity {
	private static final String TAG = "PodcastPollActivity";

	@Override
	public Fragment createFragment() {
		return new PodcastPollFragment();
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

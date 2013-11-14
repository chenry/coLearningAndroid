package com.colearning.android.podcastcatcher.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;

public class UpdatePodcastSubscriptionService extends IntentService {

	private static final String TAG = "UpdatePodcastSubscriptionService";

	public UpdatePodcastSubscriptionService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (!isNetworkingAvailable()) {
			return;
		}

		Log.i(TAG, "Starting..." + intent);
		PodcastCatcherManager podcastManager = PodcastCatcherManager.create(getApplicationContext());
		podcastManager.checkSubscriptionFeedsAndUpdateIfNecessary();
	}

	@SuppressWarnings("deprecation")
	private boolean isNetworkingAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
	}
}

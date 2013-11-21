package com.colearning.android.podcastcatcher.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.util.Log;

import com.colearning.android.podcastcatcher.contract.PodcastCatcherContract;
import com.colearning.android.podcastcatcher.feed.FeedParser;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;
import com.colearning.android.podcastcatcher.model.Subscription;

public class UpdatePodcastSubscriptionService extends IntentService {

	private static final String TAG = "UpdatePodcastSubscriptionService";
	private FeedParser mFeedParser;

	public UpdatePodcastSubscriptionService() {
		super(TAG);
		mFeedParser = new FeedParser();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (!isNetworkingAvailable()) {
			return;
		}

		Log.i(TAG, "Starting..." + intent);
		PodcastCatcherManager podcastManager = PodcastCatcherManager.create(getApplicationContext());

		ContentResolver cr = getContentResolver();

		//@formatter:off
		Cursor subscriptions = cr.query(
				PodcastCatcherContract.Subscription.CONTENT_URI, 
				PodcastCatcherContract.Subscription.PROJECTION_ALL, 
				null /* selection */, 
				null /* selectionArgs */, 
				null /* sortOrder */);
		//@formatter:on

		while (subscriptions.moveToNext()) {
			long id = subscriptions.getLong(subscriptions.getColumnIndex(PodcastCatcherContract.Subscription.Columns._ID));
			Subscription updatedSubscription = mFeedParser.parseSubscription(subscriptions.getString(subscriptions
					.getColumnIndex(PodcastCatcherContract.Subscription.Columns.FEED_URL)));
			Uri updatedSubscriptionUri = Uri.withAppendedPath(PodcastCatcherContract.Subscription.CONTENT_URI, String.valueOf(id));
			cr.update(updatedSubscriptionUri, podcastManager.toContentValues(updatedSubscription), null, null);

		}

	}

	@SuppressWarnings("deprecation")
	private boolean isNetworkingAvailable() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getBackgroundDataSetting() && cm.getActiveNetworkInfo() != null;
	}
}

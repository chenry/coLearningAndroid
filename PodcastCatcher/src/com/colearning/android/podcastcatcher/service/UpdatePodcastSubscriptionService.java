package com.colearning.android.podcastcatcher.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
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
	private static final long UPDATE_INTERVAL = 1000 * 60 * 5; // 5 minutes
	private FeedParser mFeedParser;
	private PodcastCatcherManager podcastManager;

	public UpdatePodcastSubscriptionService() {
		super(TAG);
		mFeedParser = new FeedParser();

	}

	public static void setServiceAlarm(Context context, boolean isOn) {
		Intent intent = new Intent(context, UpdatePodcastSubscriptionService.class);
		PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		if (isOn) {
			alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), UPDATE_INTERVAL, pendingIntent);
		} else {
			alarmManager.cancel(pendingIntent);
			pendingIntent.cancel();
		}
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (!isNetworkingAvailable()) {
			return;
		}

		updateFeeds(intent);
	}

	private void updateFeeds(Intent intent) {
		Log.i(TAG, "Starting..." + intent);
		ContentResolver cr = getContentResolver();

		//@formatter:off
		Cursor subscriptions = cr.query(
				PodcastCatcherContract.Subscription.CONTENT_URI, 
				PodcastCatcherContract.Subscription.PROJECTION_ALL, 
				null /* selection */, 
				null /* selectionArgs */, 
				null /* sortOrder */);
		//@formatter:on

		if (subscriptions == null) {
			return;
		}

		Log.i(TAG, "Found " + subscriptions.getCount() + " subscriptions to update...");
		podcastManager = PodcastCatcherManager.create(getApplicationContext());
		while (subscriptions.moveToNext()) {
			long id = subscriptions.getLong(subscriptions.getColumnIndex(PodcastCatcherContract.Subscription.Columns._ID));
			String feedUrl = subscriptions.getString(subscriptions.getColumnIndex(PodcastCatcherContract.Subscription.Columns.FEED_URL));

			Log.i(TAG, "SubscriptionId: " + id + " feedUrl: " + feedUrl);
			Subscription updatedSubscription = mFeedParser.parseSubscription(feedUrl);
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

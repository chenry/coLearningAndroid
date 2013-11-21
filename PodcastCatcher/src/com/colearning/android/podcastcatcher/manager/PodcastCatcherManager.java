package com.colearning.android.podcastcatcher.manager;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper.SubscriptionCursor;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper.SubscriptionItemCursor;
import com.colearning.android.podcastcatcher.feed.FeedParser;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionItem;

public class PodcastCatcherManager {

	private static final String TAG = "PodcastCatcherManager";

	private static PodcastCatcherManager podcastCatcherManager;

	private PodcastCatcherDatabaseHelper podcastDBHelper;
	private FeedParser feedParser;

	private PodcastCatcherManager(Context context) {
		podcastDBHelper = new PodcastCatcherDatabaseHelper(context);
		podcastDBHelper.deleteAll();
		podcastDBHelper.insertTestSubscriptions();

		feedParser = new FeedParser();
	}

	public static PodcastCatcherManager create(Context context) {
		if (podcastCatcherManager == null) {
			podcastCatcherManager = new PodcastCatcherManager(context.getApplicationContext());
		}
		return podcastCatcherManager;
	}

	public void insertSubscription(Subscription subscription) {
		long subscriptionId = podcastDBHelper.insertSubscription(subscription);
		List<SubscriptionItem> subscriptionItems = subscription.getSubscriptionItems();
		if (subscriptionItems == null) {
			return;
		}

		insertSubscriptionItemForSubcription(subscriptionId, subscriptionItems);
	}

	public void insertSubscriptionItemForSubcription(long subscriptionId, List<SubscriptionItem> subscriptionItems) {
		for (SubscriptionItem currSubscriptionItem : subscriptionItems) {
			insertSubscriptionItem(subscriptionId, currSubscriptionItem);
		}
	}

	public void insertSubscriptionItem(long subscriptionId, SubscriptionItem currSubscriptionItem) {
		currSubscriptionItem.setSubscriptionId(subscriptionId);
		podcastDBHelper.insertSubscriptionItem(subscriptionId, currSubscriptionItem);
	}

	public SubscriptionCursor querySubscription() {
		return podcastDBHelper.querySubscription();
	}

	public Subscription findSubscriptionById(long subscriptionId) {
		SubscriptionCursor cursor = podcastDBHelper.findSubscriptionById(subscriptionId);
		cursor.moveToFirst();
		Subscription subscription = null;
		if (!cursor.isAfterLast()) {
			subscription = cursor.getSubscription();
		}
		cursor.close();
		return subscription;
	}

	public SubscriptionItemCursor querySubscriptionItems(long subscriptionId) {
		return podcastDBHelper.findSubscriptionItemsBySubscriptionId(subscriptionId);
	}

	public void checkSubscriptionFeedsAndUpdateIfNecessary() {
		SubscriptionCursor subscriptionCursor = podcastDBHelper.querySubscription();

		subscriptionCursor.moveToFirst();
		while (subscriptionCursor.isAfterLast() == false) {
			Subscription subscription = subscriptionCursor.getSubscription();
			Subscription feedSubscription = feedParser.parseSubscription(subscription.getFeedUrl());

			updateSubscriptionIfNecessary(subscription, feedSubscription);

			subscriptionCursor.moveToNext();
		}
	}

	private void updateSubscriptionIfNecessary(Subscription ps, Subscription fs) {
		Log.i(TAG, "feedSubscription: " + fs);

		ps.setAuthor((fs.getAuthor() == null) ? null : fs.getAuthor());
		ps.setCategory(fs.getCategory() == null ? null : fs.getCategory());
		ps.setImageUrl(fs.getImageUrl() == null ? null : fs.getImageUrl());
		ps.setLastPubDate(fs.getLastPubDate() == null ? null : fs.getLastPubDate());
		ps.setLastSyncDate(new Date());
		ps.setSubTitle(fs.getSubTitle() == null ? null : fs.getSubTitle());
		ps.setSummary(fs.getSummary() == null ? null : fs.getSummary());
		ps.setTitle(fs.getTitle() == null ? null : fs.getTitle());

		podcastDBHelper.updateSubscription(ps);

	}

	public PodcastCatcherDatabaseHelper getPodcastDBHelper() {
		return podcastDBHelper;
	}
}

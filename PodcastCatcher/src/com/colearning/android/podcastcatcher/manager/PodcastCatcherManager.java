package com.colearning.android.podcastcatcher.manager;

import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.colearning.android.podcastcatcher.db.PodcastCatcherDatasource;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatasource.SubscriptionCursor;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatasource.SubscriptionItemCursor;
import com.colearning.android.podcastcatcher.feed.FeedParser;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionItem;

public class PodcastCatcherManager {

	private static final String TAG = "PodcastCatcherManager";

	private static PodcastCatcherManager podcastCatcherManager;

	private FeedParser feedParser;

	private PodcastCatcherDatasource podcastDatasource;

	private PodcastCatcherManager(Context context) {
		podcastDatasource = new PodcastCatcherDatasource(context);
		feedParser = new FeedParser();
	}

	public static PodcastCatcherManager create(Context context) {
		if (podcastCatcherManager == null) {
			podcastCatcherManager = new PodcastCatcherManager(context.getApplicationContext());
		}
		return podcastCatcherManager;
	}

	public void insertSubscription(Subscription subscription) {
		long subscriptionId = podcastDatasource.insertSubscription(subscription);
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
		podcastDatasource.insertSubscriptionItem(subscriptionId, currSubscriptionItem);
	}

	public SubscriptionCursor querySubscription() {
		return podcastDatasource.querySubscription();
	}

	public Subscription findSubscriptionById(long subscriptionId) {
		SubscriptionCursor cursor = podcastDatasource.findSubscriptionById(subscriptionId);
		cursor.moveToFirst();
		Subscription subscription = null;
		if (!cursor.isAfterLast()) {
			subscription = cursor.getSubscription();
		}
		cursor.close();
		return subscription;
	}

	public SubscriptionItemCursor querySubscriptionItems(long subscriptionId) {
		return podcastDatasource.findSubscriptionItemsBySubscriptionId(subscriptionId);
	}

	public void checkSubscriptionFeedsAndUpdateIfNecessary() {
		SubscriptionCursor subscriptionCursor = podcastDatasource.querySubscription();

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

		podcastDatasource.updateSubscription(ps);

	}

	public PodcastCatcherDatasource getPodcastDatasource() {
		return podcastDatasource;
	}

	public ContentValues toContentValues(Subscription subscription) {
		return podcastDatasource.toContentValues(subscription);
	}

	public ContentValues toContentValues(long subscriptionId, SubscriptionItem subscriptionItem) {
		return podcastDatasource.toContentValues(subscriptionId, subscriptionItem);
	}

}

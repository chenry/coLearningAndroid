package com.colearning.android.podcastcatcher.manager;

import java.util.List;

import android.content.Context;

import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper.SubscriptionCursor;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionItem;

public class PodcastCatcherManager {

	private PodcastCatcherDatabaseHelper podcastDBHelper;
	private static PodcastCatcherManager podcastCatcherManager;

	private PodcastCatcherManager(Context context) {
		podcastDBHelper = new PodcastCatcherDatabaseHelper(context);
		podcastDBHelper.deleteAll();
		podcastDBHelper.insertTestSubscriptions();
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

}

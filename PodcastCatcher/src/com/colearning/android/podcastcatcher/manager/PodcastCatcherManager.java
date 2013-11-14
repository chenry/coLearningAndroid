package com.colearning.android.podcastcatcher.manager;

import java.util.List;
import java.util.UUID;

import android.content.Context;

import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionItem;
import com.colearning.android.podcastcatcher.model.SubscriptionList;

public class PodcastCatcherManager {

	private SubscriptionList subscriptionList;
	private PodcastCatcherDatabaseHelper podcastDBHelper;

	private PodcastCatcherManager(Context context) {
		subscriptionList = SubscriptionList.create();
		podcastDBHelper = new PodcastCatcherDatabaseHelper(context);
		podcastDBHelper.deleteAll();
		podcastDBHelper.insertTestSubscriptions();
	}

	public static PodcastCatcherManager create(Context context) {
		return new PodcastCatcherManager(context.getApplicationContext());
	}

	public List<Subscription> getSubscriptions() {
		return subscriptionList.getSubscriptions();
	}

	public Subscription findSubscription(UUID subscriptionId) {
		return subscriptionList.findSubscription(subscriptionId);
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

}

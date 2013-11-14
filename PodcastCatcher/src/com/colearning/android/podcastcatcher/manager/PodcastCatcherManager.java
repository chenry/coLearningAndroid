package com.colearning.android.podcastcatcher.manager;

import java.util.List;
import java.util.UUID;

import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionList;

public class PodcastCatcherManager {

	private SubscriptionList subscriptionList;

	private PodcastCatcherManager() {
		subscriptionList = SubscriptionList.create();
	}

	public static PodcastCatcherManager create() {
		return new PodcastCatcherManager();
	}

	public List<Subscription> getSubscriptions() {
		return subscriptionList.getSubscriptions();
	}

	public Subscription findSubscription(UUID subscriptionId) {
		return subscriptionList.findSubscription(subscriptionId);
	}
}

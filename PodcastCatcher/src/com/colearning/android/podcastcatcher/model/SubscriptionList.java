package com.colearning.android.podcastcatcher.model;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionList {

	private List<Subscription> subscriptions;
	private static SubscriptionList subscriptionList;

	private SubscriptionList() {
		subscriptions = new ArrayList<Subscription>();
		for (int i = 0; i < 20; i++) {
			Subscription subscription = new Subscription("Title #" + i);
			subscriptions.add(subscription);
		}
	}

	public static SubscriptionList create() {
		if (subscriptionList == null) {
			subscriptionList = new SubscriptionList();
		}
		return subscriptionList;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}
}

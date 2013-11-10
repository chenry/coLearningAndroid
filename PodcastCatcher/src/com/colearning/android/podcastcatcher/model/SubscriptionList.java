package com.colearning.android.podcastcatcher.model;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionList {

	private List<Subscription> subscriptions;
	private static SubscriptionList subscriptionList;

	private SubscriptionList() {
		subscriptions = new ArrayList<Subscription>();
		for (int i = 0; i < 20; i++) {
			Subscription subscription = new Subscription("Title #" + i, "Subtitle: " + i);
			subscription.setAuthor("Peter Pan " + i);
			subscription.setCategory("Tech");
			subscription.setImageUrl("https://www.google.com/images/srpr/logo11w.png");
			subscription.setLink("http://agiletoolkit.libsyn.com");
			subscription
					.setSummary(i
							+ "The Empire Podcast is the last word on movies from the biggest movie magazine on the planet, including each week's news, reviews and interviews");
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

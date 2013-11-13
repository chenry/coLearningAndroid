package com.colearning.android.podcastcatcher.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubscriptionList {

	private List<Subscription> subscriptions;
	private static SubscriptionList subscriptionList;

	private SubscriptionList() {
		subscriptions = new ArrayList<Subscription>();
		for (int i = 0; i < 20; i++) {
			Subscription subscription = new Subscription();
			subscription.setTitle("Title #" + i);
			subscription.setSubTitle("Subtitle: " + i);
			subscription.setAuthor("Peter Pan " + i);
			subscription.setCategory("Tech");
			subscription.setImageUrl("https://www.google.com/images/srpr/logo11w.png");
			subscription.setLink("http://agiletoolkit.libsyn.com");
			//@formatter:off
			subscription
					.setSummary(i
							+ "This is the most awesome podcast that you can ever imagine.  We talk about all kinds of great stuff...and you will love it. " +
							"We are so much fun and I cannot think of anything else to write here so I am just going to keep going and going and going and going and going " +
							" and going and going and going and going and going and going and going and going and going and going and going and going and going and going" +
							" and going and going and going and going and going and going and going and going and going and going and going and going and going and going and going" +
							" and going and going and going and going and going and going and going and going and going and going and going and going and going and going and going" +
							" and going and going and going and going and going and going and going and going and going and going and going and going and going and going and going" +
							" and going and going and going and going and going and going and going and going and going and going and going and going and going and going and going" +
							" and going and going and going and going and going and going and going and going and going and going and going and going and going and going and going" +
							" and going and going and going and going and going and going and going and going and going and going and going and going and going and going and going");
			subscriptions.add(subscription);
			//@formatter:on
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

	public Subscription findSubscription(UUID subscriptionId) {
		Subscription matchingSubscription = null;
		for (Subscription currSubscription : subscriptions) {
			if (subscriptionId.equals(currSubscription.getId())) {
				matchingSubscription = currSubscription;
				break;
			}
		}
		return matchingSubscription;
	}
}

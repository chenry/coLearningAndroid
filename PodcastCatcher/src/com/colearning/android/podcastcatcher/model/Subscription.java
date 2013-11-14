package com.colearning.android.podcastcatcher.model;

import java.util.ArrayList;
import java.util.List;

public class Subscription {

	private long id;
	private String title;
	private String subTitle;
	private String author;
	private String summary;
	private String category;
	private String feedUrl;
	private String imageUrl;

	private List<SubscriptionItem> subscriptionItems;

	public Subscription() {
		this.id = -1L;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFeedUrl() {
		return feedUrl;
	}

	public void setFeedUrl(String link) {
		this.feedUrl = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<SubscriptionItem> getSubscriptionItems() {
		return subscriptionItems;
	}

	public void setSubscriptionItems(List<SubscriptionItem> subscriptionItems) {
		this.subscriptionItems = subscriptionItems;
	}

	public void addSubscriptionItem(SubscriptionItem subscriptionItem) {
		if (this.subscriptionItems == null) {
			this.subscriptionItems = new ArrayList<SubscriptionItem>();
		}
		this.subscriptionItems.add(subscriptionItem);
	}

}

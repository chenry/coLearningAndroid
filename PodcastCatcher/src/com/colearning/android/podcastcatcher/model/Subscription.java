package com.colearning.android.podcastcatcher.model;

public class Subscription {

	private String title;
	private String subTitle;

	public Subscription(String title, String subTitle) {
		this.title = title;
		this.subTitle = subTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public String getTitle() {
		return title;
	}
}

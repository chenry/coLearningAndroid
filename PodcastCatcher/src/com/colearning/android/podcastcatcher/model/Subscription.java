package com.colearning.android.podcastcatcher.model;

import java.util.UUID;

public class Subscription {

	private UUID id;
	private String title;
	private String subTitle;
	private String author;
	private String summary;
	private String category;
	private String link;
	private String imageUrl;

	public Subscription(String title, String subTitle) {
		this.id = UUID.randomUUID();
		this.title = title;
		this.subTitle = subTitle;
	}

	public UUID getId() {
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}

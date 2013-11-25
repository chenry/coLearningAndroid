package com.colearning.android.podcastcatcher.model;

import java.util.Date;

public class SubscriptionItem {

	private long id;
	private long subscriptionId;
	private String title;
	private String guidId;
	private String linkUrl;
	private String thumbnailUrl;
	private String itemDesc;
	private String mediaUrl;
	private String fileLocation;
	private Date pubDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGuidId() {
		return guidId;
	}

	public void setGuidId(String guidId) {
		this.guidId = guidId;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	@Override
	public String toString() {
		return "SubscriptionItem [id=" + id + ", subscriptionId=" + subscriptionId + ", title=" + title + ", guidId=" + guidId + ", linkUrl=" + linkUrl
				+ ", thumbnailUrl=" + thumbnailUrl + ", itemDesc=" + itemDesc + ", mediaUrl=" + mediaUrl + ", fileLocation=" + fileLocation + ", pubDate="
				+ pubDate + "]";
	}

}

package com.colearning.android.podcastcatcher.contract;

public final class PodcastCatcherContract {
	
	public static final class Subscription {
		public static final class Columns {
			public static final String _ID = "_id";
			public static final String FEED_URL = "feed_url";
			public static final String TITLE = "title";
			public static final String SUBTITLE = "subtitle";
			public static final String AUTHOR = "author";
			public static final String SUMMARY = "summary";
			public static final String CATEGORY = "category";
			public static final String IMAGE_URL = "image_url";
			public static final String LAST_SYNC_TIME = "last_sync_time";
			public static final String LAST_PUB_DATE = "last_pub_date";
		}
	}
	
	public static final class SubscriptionItem {
		public static final class Columns {
			public static final String _ID = "_id";
			public static final String SUBSCRIPTION_ID = "subscription_id";
			public static final String TITLE = "title";
			public static final String GUID_ID = "guid_id";
			public static final String LINK_URL = "link_url";
			public static final String THUMBNAIL_URL = "thumbnail_url";
			public static final String ITEM_DESC = "item_desc";
			public static final String MEDIA_URL = "media_url";
			public static final String FILE_LOCATION = "file_location";
			
		}
		
	}
	
}

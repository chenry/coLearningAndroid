package com.colearning.android.podcastcatcher.contract;

import android.content.ContentResolver;
import android.net.Uri;

public final class PodcastCatcherContract {
	public static final String AUTHORITY = "com.colearning.android.podcastcatcher";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

	public static final class Subscription {
		public static final String BASE_PATH = "subscriptions";
		public static final Uri CONTENT_URI = Uri.withAppendedPath(PodcastCatcherContract.CONTENT_URI, BASE_PATH);
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "." + BASE_PATH;
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "." + BASE_PATH;

		public static final String TABLE_NAME = "subscription";

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

		public static final String[] PROJECTION_ALL = { Columns._ID, Columns.FEED_URL, Columns.TITLE, Columns.SUBTITLE, Columns.AUTHOR, Columns.SUMMARY,
				Columns.CATEGORY, Columns.IMAGE_URL, Columns.LAST_SYNC_TIME, Columns.LAST_PUB_DATE };
	}

	public static final class SubscriptionItem {
		public static final String BASE_PATH = "subscriptionItems";
		public static final Uri CONTENT_URI = Uri.withAppendedPath(PodcastCatcherContract.CONTENT_URI, BASE_PATH);
		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "." + BASE_PATH;
		public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "." + BASE_PATH;
		public static final String TABLE_NAME = "subscription_item";

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

		public static final String[] PROJECTION_ALL = { Columns._ID, Columns.SUBSCRIPTION_ID, Columns.TITLE, Columns.GUID_ID, Columns.LINK_URL,
				Columns.THUMBNAIL_URL, Columns.ITEM_DESC, Columns.MEDIA_URL, Columns.FILE_LOCATION };
	}

}

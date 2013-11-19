package com.colearning.android.podcastcatcher.db;

import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.colearning.android.podcastcatcher.contract.PodcastCatcherContract;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionItem;

public class PodcastCatcherDatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "podcastCatcher.sqlite";
	private static final int VERSION = 1;

	public static final String TABLE_SUBSCRIPTION = "subscription";
	public static final String TABLE_SUBSCRIPTION_ITEM = "subscription_item";

	public PodcastCatcherDatabaseHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//@formatter:off
		db.execSQL(
				"create table subscription (" +
				"_id integer primary key autoincrement, " +
				"feed_url varchar(200), " +
				"title varchar(200), " +
				"subtitle varchar(200), " +
				"author varchar(200), " +
				"summary varchar(200), " +
				"category varchar(200), " +
				"image_url varchar(200), " +
				"last_sync_time integer, " +
				"last_pub_date integer" +
				")"
		);
		//@formatter:on

		//@formatter:off
		db.execSQL(
				"create table subscription_item (" +
				"_id integer primary key autoincrement, " +
				"subscription_id integer, " +
				"title varchar(200)," +
				"guid_id varchar(200), " +
				"link_url varchar(200), " +
				"thumbnail_url varchar(200), " +
				"item_desc varchar(400), " +
				"media_url varchar(200), " +
				"file_location varchar(200))"
		);
		//@formatter:on
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public long insertSubscription(Subscription subscription) {
		return getWritableDatabase().insert(TABLE_SUBSCRIPTION, null, toContentValues(subscription));
	}

	public void insertSubscriptionItems(long subscriptionId, List<SubscriptionItem> subscriptionItems) {
		for (SubscriptionItem currSubscriptionItem : subscriptionItems) {
			insertSubscriptionItem(subscriptionId, currSubscriptionItem);
		}
	}

	public void insertSubscriptionItem(long subscriptionId, SubscriptionItem currSubscriptionItem) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(PodcastCatcherContract.SubscriptionItem.Columns.FILE_LOCATION, currSubscriptionItem.getFileLocation());
		contentValues.put(PodcastCatcherContract.SubscriptionItem.Columns.GUID_ID, currSubscriptionItem.getGuidId());
		contentValues.put(PodcastCatcherContract.SubscriptionItem.Columns.ITEM_DESC, currSubscriptionItem.getItemDesc());
		contentValues.put(PodcastCatcherContract.SubscriptionItem.Columns.LINK_URL, currSubscriptionItem.getLinkUrl());
		contentValues.put(PodcastCatcherContract.SubscriptionItem.Columns.MEDIA_URL, currSubscriptionItem.getMediaUrl());
		contentValues.put(PodcastCatcherContract.SubscriptionItem.Columns.SUBSCRIPTION_ID, subscriptionId);
		contentValues.put(PodcastCatcherContract.SubscriptionItem.Columns.THUMBNAIL_URL, currSubscriptionItem.getThumbnailUrl());
		contentValues.put(PodcastCatcherContract.SubscriptionItem.Columns.TITLE, currSubscriptionItem.getTitle());

		getWritableDatabase().insert(TABLE_SUBSCRIPTION_ITEM, null, contentValues);
	}

	public void deleteAll() {
		getWritableDatabase().delete(TABLE_SUBSCRIPTION, null, null);
		getWritableDatabase().delete(TABLE_SUBSCRIPTION_ITEM, null, null);
	}

	public void insertTestSubscriptions() {
		insertSubscription(createSubscription("http://feeds.feedburner.com/javaposse"));
		insertSubscription(createSubscription("http://feeds.feedburner.com/AndroidCentralPodcast"));
	}

	private SubscriptionItem toSubscriptionItem(int i) {
		SubscriptionItem item = new SubscriptionItem();
		item.setFileLocation(null);
		item.setGuidId(null);
		item.setItemDesc("Item Description " + i);
		item.setLinkUrl("someUrl " + i);
		item.setMediaUrl("someMediaUrl " + i);
		item.setThumbnailUrl("someUrl" + i);
		item.setTitle("Very Awesome..." + i);

		return item;
	}

	private Subscription createSubscription(String urlPath) {
		Subscription subscription = new Subscription();
		subscription.setTitle("Title #");
		subscription.setSubTitle("A subtitle ");
		subscription.setAuthor("Carlus Henry");
		subscription.setCategory("Tech");
		subscription.setSummary("This is just something silly");
		subscription.setFeedUrl(urlPath);
		return subscription;
	}

	public SubscriptionCursor querySubscription() {
		Cursor cursor = getReadableDatabase().query(TABLE_SUBSCRIPTION, null, null, null, null, null, null);
		return new SubscriptionCursor(cursor);
	}

	public SubscriptionCursor findSubscriptionById(long id) {
		//@formatter:off
		Cursor cursor = getReadableDatabase().query(
				TABLE_SUBSCRIPTION, 
				null, 
				PodcastCatcherContract.Subscription.Columns._ID + " = ?", 
				new String[] { String.valueOf(id) }, 
				null, 
				null, 
				null, 
				"1");
		//@formatter:on
		return new SubscriptionCursor(cursor);
	}

	public SubscriptionItemCursor findSubscriptionItemsBySubscriptionId(long subscriptionId) {
		//@formatter:off
		Cursor cursor = getReadableDatabase().query(
				TABLE_SUBSCRIPTION_ITEM, 
				null, 
				PodcastCatcherContract.SubscriptionItem.Columns.SUBSCRIPTION_ID + " = ?", 
				new String[] { String.valueOf(subscriptionId) }, 
				null, 
				null, 
				null, 
				null);
		//@formatter:on
		return new SubscriptionItemCursor(cursor);
	}

	public static class SubscriptionCursor extends CursorWrapper {
		public SubscriptionCursor(Cursor cursor) {
			super(cursor);
		}

		public Subscription getSubscription() {

			if (isBeforeFirst() || isAfterLast()) {
				return null;
			}

			Subscription subscription = new Subscription();
			long subscriptioinId = getLong(getColumnIndex(PodcastCatcherContract.Subscription.Columns._ID));
			String author = getString(getColumnIndex(PodcastCatcherContract.Subscription.Columns.AUTHOR));
			String category = getString(getColumnIndex(PodcastCatcherContract.Subscription.Columns.CATEGORY));
			String feedUrl = getString(getColumnIndex(PodcastCatcherContract.Subscription.Columns.FEED_URL));
			String imageUrl = getString(getColumnIndex(PodcastCatcherContract.Subscription.Columns.IMAGE_URL));
			String subTitle = getString(getColumnIndex(PodcastCatcherContract.Subscription.Columns.SUBTITLE));
			String summary = getString(getColumnIndex(PodcastCatcherContract.Subscription.Columns.SUMMARY));
			String title = getString(getColumnIndex(PodcastCatcherContract.Subscription.Columns.TITLE));
			long lastSyncTimeLong = getLong(getColumnIndex(PodcastCatcherContract.Subscription.Columns.LAST_SYNC_TIME));
			long lastPubDateLong = getLong(getColumnIndex(PodcastCatcherContract.Subscription.Columns.LAST_PUB_DATE));

			subscription.setId(subscriptioinId);
			subscription.setAuthor(author);
			subscription.setCategory(category);
			subscription.setFeedUrl(feedUrl);
			subscription.setImageUrl(imageUrl);
			subscription.setSubTitle(subTitle);
			subscription.setSummary(summary);
			subscription.setTitle(title);
			subscription.setLastSyncDate(new Date(lastSyncTimeLong));
			subscription.setLastPubDate(new Date(lastPubDateLong));

			return subscription;

		}
	}

	public static class SubscriptionItemCursor extends CursorWrapper {
		public SubscriptionItemCursor(Cursor cursor) {
			super(cursor);
		}

		public SubscriptionItem getSubscriptionItem() {

			if (isBeforeFirst() || isAfterLast()) {
				return null;
			}

			SubscriptionItem si = new SubscriptionItem();

			long id = getLong(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns._ID));
			String fileLocation = getString(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns.FILE_LOCATION));
			String guidId = getString(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns.GUID_ID));
			long subscriptionId = getLong(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns.SUBSCRIPTION_ID));
			String itemDesc = getString(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns.ITEM_DESC));
			String linkUrl = getString(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns.LINK_URL));
			String mediaUrl = getString(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns.MEDIA_URL));
			String thumbnailUrl = getString(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns.THUMBNAIL_URL));
			String title = getString(getColumnIndex(PodcastCatcherContract.SubscriptionItem.Columns.TITLE));

			si.setId(id);
			si.setFileLocation(fileLocation);
			si.setGuidId(guidId);
			si.setItemDesc(itemDesc);
			si.setLinkUrl(linkUrl);
			si.setMediaUrl(mediaUrl);
			si.setSubscriptionId(subscriptionId);
			si.setThumbnailUrl(thumbnailUrl);
			si.setTitle(title);

			return si;

		}
	}

	public void updateSubscription(Subscription ps) {
		getWritableDatabase().update(TABLE_SUBSCRIPTION, toContentValues(ps), "_id = ?", new String[] { String.valueOf(ps.getId()) });
	}

	private ContentValues toContentValues(Subscription subscription) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(PodcastCatcherContract.Subscription.Columns.AUTHOR, subscription.getAuthor());
		contentValues.put(PodcastCatcherContract.Subscription.Columns.CATEGORY, subscription.getCategory());
		contentValues.put(PodcastCatcherContract.Subscription.Columns.FEED_URL, subscription.getFeedUrl());
		contentValues.put(PodcastCatcherContract.Subscription.Columns.IMAGE_URL, subscription.getImageUrl());
		contentValues.put(PodcastCatcherContract.Subscription.Columns.SUBTITLE, subscription.getSubTitle());
		contentValues.put(PodcastCatcherContract.Subscription.Columns.SUMMARY, subscription.getSummary());
		contentValues.put(PodcastCatcherContract.Subscription.Columns.TITLE, subscription.getTitle());
		Long lastSyncTime = (subscription.getLastSyncDate() == null) ? null : subscription.getLastSyncDate().getTime();
		Long lastPubDate = (subscription.getLastPubDate() == null) ? null : subscription.getLastPubDate().getTime();
		contentValues.put(PodcastCatcherContract.Subscription.Columns.LAST_SYNC_TIME, lastSyncTime);
		contentValues.put(PodcastCatcherContract.Subscription.Columns.LAST_PUB_DATE, lastPubDate);
		return contentValues;
	}

}

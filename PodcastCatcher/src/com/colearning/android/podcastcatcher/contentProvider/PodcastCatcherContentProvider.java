package com.colearning.android.podcastcatcher.contentProvider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.colearning.android.podcastcatcher.contract.PodcastCatcherContract;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatasource;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;

public class PodcastCatcherContentProvider extends ContentProvider {

	private static final int SUBSCRIPTIONS_LIST = 1;
	private static final int SUBSCRIPTION_ID = 2;
	private static final int SUBSCRIPTION_ITEMS_LIST = 3;
	private static final int SUBSCRIPTION_ITEM_ID = 4;

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private PodcastCatcherDatasource mPodcastDatasource;

	static {
		sURIMatcher.addURI(PodcastCatcherContract.AUTHORITY, PodcastCatcherContract.Subscription.BASE_PATH, SUBSCRIPTIONS_LIST);
		sURIMatcher.addURI(PodcastCatcherContract.AUTHORITY, PodcastCatcherContract.Subscription.BASE_PATH + "/#", SUBSCRIPTION_ID);
		sURIMatcher.addURI(PodcastCatcherContract.AUTHORITY, PodcastCatcherContract.SubscriptionItem.BASE_PATH, SUBSCRIPTION_ITEMS_LIST);
		sURIMatcher.addURI(PodcastCatcherContract.AUTHORITY, PodcastCatcherContract.SubscriptionItem.BASE_PATH + "/#", SUBSCRIPTION_ITEM_ID);
	}

	@Override
	public boolean onCreate() {
		mPodcastDatasource = PodcastCatcherManager.create(getContext()).getPodcastDatasource();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case SUBSCRIPTION_ID:
			queryBuilder.setTables(PodcastCatcherContract.Subscription.TABLE_NAME);
			queryBuilder.appendWhere(PodcastCatcherContract.Subscription.Columns._ID + "=" + uri.getLastPathSegment());
			break;
		case SUBSCRIPTIONS_LIST:
			// no filter
			queryBuilder.setTables(PodcastCatcherContract.Subscription.TABLE_NAME);
			break;
		case SUBSCRIPTION_ITEM_ID:
			queryBuilder.setTables(PodcastCatcherContract.SubscriptionItem.TABLE_NAME);
			queryBuilder.appendWhere(PodcastCatcherContract.Subscription.Columns._ID + "=" + uri.getLastPathSegment());
			break;
		case SUBSCRIPTION_ITEMS_LIST:
			queryBuilder.setTables(PodcastCatcherContract.SubscriptionItem.TABLE_NAME);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI");
		}
		Cursor cursor = queryBuilder.query(mPodcastDatasource.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);
		int updateCount = 0;

		String where = null;
		String additionalWhere = null;
		SQLiteDatabase db = mPodcastDatasource.getWritableDatabase();
		switch (uriType) {
		case SUBSCRIPTION_ID:
			additionalWhere = (TextUtils.isEmpty(selection)) ? "" : " AND (" + selection + ")";
			where = PodcastCatcherContract.Subscription.Columns._ID + " = " + uri.getLastPathSegment() + additionalWhere;
			updateCount = db.update(PodcastCatcherContract.Subscription.TABLE_NAME, values, where, selectionArgs);
			break;

		case SUBSCRIPTIONS_LIST:
			db.update(PodcastCatcherContract.Subscription.TABLE_NAME, values, selection, selectionArgs);
			break;

		case SUBSCRIPTION_ITEM_ID:
			additionalWhere = (TextUtils.isEmpty(selection)) ? "" : " AND (" + selection + ")";
			where = PodcastCatcherContract.SubscriptionItem.Columns._ID + " = " + uri.getLastPathSegment() + additionalWhere;
			updateCount = db.update(PodcastCatcherContract.Subscription.TABLE_NAME, values, where, selectionArgs);

			break;

		case SUBSCRIPTION_ITEMS_LIST:
			db.update(PodcastCatcherContract.SubscriptionItem.TABLE_NAME, values, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unsupported uri: " + uri + " Type: " + getType(uri));
		}

		if (updateCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return updateCount;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int deletedRecordsCount = 0;
		int uriType = sURIMatcher.match(uri);
		String additionalWhere = null;
		String whereClause = null;
		switch (uriType) {
		case SUBSCRIPTION_ID:
			additionalWhere = (TextUtils.isEmpty(selection)) ? "" : " AND (" + selection + ")";
			whereClause = PodcastCatcherContract.Subscription.Columns._ID + " = " + uri.getLastPathSegment() + additionalWhere;
			deletedRecordsCount = mPodcastDatasource.getWritableDatabase().delete(PodcastCatcherContract.Subscription.TABLE_NAME, whereClause, selectionArgs);
			break;
		case SUBSCRIPTIONS_LIST:
			mPodcastDatasource.getWritableDatabase().delete(PodcastCatcherContract.Subscription.TABLE_NAME, selection, selectionArgs);
			break;

		case SUBSCRIPTION_ITEM_ID:
			additionalWhere = (TextUtils.isEmpty(selection)) ? "" : " AND (" + selection + ")";
			whereClause = PodcastCatcherContract.Subscription.Columns._ID + " = " + uri.getLastPathSegment() + additionalWhere;
			deletedRecordsCount = mPodcastDatasource.getWritableDatabase().delete(PodcastCatcherContract.SubscriptionItem.TABLE_NAME, whereClause,
					selectionArgs);
			break;
		case SUBSCRIPTION_ITEMS_LIST:
			mPodcastDatasource.getWritableDatabase().delete(PodcastCatcherContract.SubscriptionItem.TABLE_NAME, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unrecognized uri: " + uri);
		}

		if (deletedRecordsCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return deletedRecordsCount;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int uriType = sURIMatcher.match(uri);
		long insertedId = -1L;
		Uri insertedUri = null;
		switch (uriType) {
		case SUBSCRIPTIONS_LIST:
			insertedId = mPodcastDatasource.getWritableDatabase().insert(PodcastCatcherContract.Subscription.TABLE_NAME, null, values);
			insertedUri = uriForId(uri, insertedId);

			break;
		case SUBSCRIPTION_ITEMS_LIST:
			insertedId = mPodcastDatasource.getWritableDatabase().insert(PodcastCatcherContract.SubscriptionItem.TABLE_NAME, null, values);
			insertedUri = uriForId(uri, insertedId);
			break;
		default:
			throw new IllegalArgumentException("Unsupported operation for this uri: " + uri);
		}

		if (insertedUri != null) {
			getContext().getContentResolver().notifyChange(insertedUri, null);
		}

		return insertedUri;

	}

	private Uri uriForId(Uri uri, long id) {
		if (id <= 0) {
			throw new IllegalArgumentException("Encountered problems while inserting into uri: " + uri);
		}
		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public String getType(Uri uri) {
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case SUBSCRIPTION_ID:
			return PodcastCatcherContract.Subscription.CONTENT_ITEM_TYPE;
		case SUBSCRIPTIONS_LIST:
			return PodcastCatcherContract.Subscription.CONTENT_TYPE;
		case SUBSCRIPTION_ITEM_ID:
			return PodcastCatcherContract.SubscriptionItem.CONTENT_TYPE;
		case SUBSCRIPTION_ITEMS_LIST:
			return PodcastCatcherContract.SubscriptionItem.CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		}
	}

}

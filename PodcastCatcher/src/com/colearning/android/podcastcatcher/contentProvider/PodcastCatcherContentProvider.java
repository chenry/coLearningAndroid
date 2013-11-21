package com.colearning.android.podcastcatcher.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.colearning.android.podcastcatcher.contract.PodcastCatcherContract;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatasource;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;

public class PodcastCatcherContentProvider extends ContentProvider {

	private static final int SUBSCRIPTIONS_LIST = 1;
	private static final int SUBSCRIPTION_ID = 2;

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private PodcastCatcherDatasource mPodcastDatasource;

	static {
		sURIMatcher.addURI(PodcastCatcherContract.AUTHORITY, PodcastCatcherContract.Subscription.BASE_PATH, SUBSCRIPTIONS_LIST);
		sURIMatcher.addURI(PodcastCatcherContract.AUTHORITY, PodcastCatcherContract.Subscription.BASE_PATH + "/#", SUBSCRIPTION_ID);
	}

	@Override
	public boolean onCreate() {
		mPodcastDatasource = PodcastCatcherManager.create(getContext()).getPodcastDatasource();
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(PodcastCatcherContract.Subscription.TABLE_NAME);
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case SUBSCRIPTION_ID:
			queryBuilder.appendWhere(PodcastCatcherContract.Subscription.Columns._ID + "=" + uri.getLastPathSegment());
			break;
		case SUBSCRIPTIONS_LIST:
			// no filter
			break;
		default:
			throw new IllegalArgumentException("Unknown URI");
		}
		Cursor cursor = queryBuilder.query(mPodcastDatasource.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int delete(Uri uri, String string, String[] stringArgs) {
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int uriType = sURIMatcher.match(uri);

		if (uriType != SUBSCRIPTION_ID) {
			throw new IllegalArgumentException("Unsupported uri: " + uri + " Type: " + getType(uri));
		}

		String id = uri.getLastPathSegment();
		String where = PodcastCatcherContract.Subscription.Columns._ID + " = " + id;

		SQLiteDatabase db = mPodcastDatasource.getWritableDatabase();
		int updateCount = db.update(PodcastCatcherContract.Subscription.TABLE_NAME, values, where, null);

		if (updateCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return updateCount;
	}

	@Override
	public String getType(Uri uri) {
		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
		case SUBSCRIPTION_ID:
			return PodcastCatcherContract.Subscription.CONTENT_ITEM_TYPE;
		case SUBSCRIPTIONS_LIST:
			return PodcastCatcherContract.Subscription.CONTENT_TYPE;
		default:
			throw new IllegalArgumentException("Unsupported uri: " + uri);
		}
	}

}

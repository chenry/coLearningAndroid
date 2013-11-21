package com.colearning.android.podcastcatcher.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.colearning.android.podcastcatcher.contract.PodcastCatcherContract;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;

public class PodcastCatcherContentProvider extends ContentProvider {

	public static final int SUBSCRIPTIONS = 100;
	public static final int SUBSCRIPTION_ID = 110;

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	private PodcastCatcherDatabaseHelper mPodcastDBHelper;
	static {
		sURIMatcher.addURI(PodcastCatcherContract.AUTHORITY, "subscriptions", SUBSCRIPTIONS);
		sURIMatcher.addURI(PodcastCatcherContract.AUTHORITY, "subscription/#", SUBSCRIPTION_ID);
	}

	@Override
	public boolean onCreate() {
		mPodcastDBHelper = PodcastCatcherManager.create(getContext()).getPodcastDBHelper();
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
		case SUBSCRIPTIONS:
			// no filter
			break;
		default:
			throw new IllegalArgumentException("Unknown URI");
		}
		Cursor cursor = queryBuilder.query(mPodcastDBHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int delete(Uri uri, String string, String[] stringArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}

}

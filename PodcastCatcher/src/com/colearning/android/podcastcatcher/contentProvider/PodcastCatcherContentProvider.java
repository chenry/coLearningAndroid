package com.colearning.android.podcastcatcher.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}

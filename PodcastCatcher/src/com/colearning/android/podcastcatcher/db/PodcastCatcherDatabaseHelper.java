package com.colearning.android.podcastcatcher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PodcastCatcherDatabaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "podcastCatcher.sqlite";
	private static final int VERSION = 1;

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

}

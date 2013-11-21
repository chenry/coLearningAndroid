package com.colearning.android.podcastcatcher;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;

import com.colearning.android.podcastcatcher.contract.PodcastCatcherContract;
import com.colearning.android.podcastcatcher.service.UpdatePodcastSubscriptionService;

public class SubscriptionListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final String TAG = "SubscriptionListFragment";
	private static final int SUBSCRIPTION_LIST_LOADER = 20;
	private SubscriptionItemSelectedListener itemSelectedListener;
	private SimpleCursorAdapter cursorAdapter;

	public interface SubscriptionItemSelectedListener {
		public void subscriptionSelected(long subscriptionId);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[] uiBindFrom = { PodcastCatcherContract.Subscription.Columns.TITLE, PodcastCatcherContract.Subscription.Columns.SUBTITLE };
		int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };

		getLoaderManager().initLoader(SUBSCRIPTION_LIST_LOADER, null, this);
		cursorAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), android.R.layout.simple_list_item_2, null, uiBindFrom, uiBindTo,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(cursorAdapter);

		UpdatePodcastSubscriptionService.setServiceAlarm(getActivity(), true);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		itemSelectedListener.subscriptionSelected(id);
	}

	@Override
	public void onDestroy() {
		// subscriptionCursor.close();
		super.onDestroy();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		itemSelectedListener = (SubscriptionItemSelectedListener) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		itemSelectedListener = null;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = { PodcastCatcherContract.Subscription.Columns._ID, PodcastCatcherContract.Subscription.Columns.TITLE,
				PodcastCatcherContract.Subscription.Columns.SUBTITLE };
		//@formatter:off
		return new CursorLoader(
				getActivity().getApplicationContext(), 
				PodcastCatcherContract.Subscription.CONTENT_URI, 
				projection, 
				null, 
				null, 
				null);
		//@formatter:on
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		cursorAdapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		cursorAdapter.swapCursor(null);
	}

}

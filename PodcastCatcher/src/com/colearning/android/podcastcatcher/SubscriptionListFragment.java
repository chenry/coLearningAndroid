package com.colearning.android.podcastcatcher;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.colearning.android.podcastcatcher.contract.PodcastCatcherContract;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.service.UpdatePodcastSubscriptionService;

public class SubscriptionListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
	private static final String TAG = "SubscriptionListFragment";
	private static final int SUBSCRIPTION_LIST_LOADER = 10;
	private static final String DIALOG_FEED_URL = "DIALOG_FEED_URL";
	private static final int REQUEST_FEED_URL = 0;
	private SubscriptionItemSelectedListener itemSelectedListener;
	private SimpleCursorAdapter cursorAdapter;
	private PodcastCatcherManager mPodcastCatcherManager;

	public interface SubscriptionItemSelectedListener {
		public void subscriptionSelected(long subscriptionId);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mPodcastCatcherManager = PodcastCatcherManager.create(getActivity());
		fillData();
	}

	private void fillData() {
		String[] uiBindFrom = { PodcastCatcherContract.Subscription.Columns.TITLE, PodcastCatcherContract.Subscription.Columns.SUBTITLE };
		int[] uiBindTo = { android.R.id.text1, android.R.id.text2 };
		getLoaderManager().initLoader(SUBSCRIPTION_LIST_LOADER, null, this);
		cursorAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_2, null, uiBindFrom, uiBindTo,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(cursorAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		itemSelectedListener.subscriptionSelected(id);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_subscription_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_new_subscription:
			handleNewSubscription();
			return true;
		case R.id.menu_item_refresh_feeds:
			handleRefreshFeeds();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void handleNewSubscription() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		FeedUrlDialog dialog = new FeedUrlDialog();
		dialog.setTargetFragment(this, REQUEST_FEED_URL);
		dialog.show(fm, DIALOG_FEED_URL);
	}

	@Override
	public void onDestroy() {
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
				getActivity(), 
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

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		if (requestCode == REQUEST_FEED_URL) {
			Subscription subscription = new Subscription();
			String feedUrl = (String) data.getSerializableExtra(FeedUrlDialog.EXTRA_FEED_URL);
			subscription.setFeedUrl(feedUrl);

			ContentResolver contentResolver = getActivity().getContentResolver();
			contentResolver.insert(PodcastCatcherContract.Subscription.CONTENT_URI, mPodcastCatcherManager.toContentValues(subscription));
			handleRefreshFeeds();

		}

	}

	private void handleRefreshFeeds() {
		Intent intent = new Intent(getActivity(), UpdatePodcastSubscriptionService.class);
		getActivity().startService(intent);
	}

}

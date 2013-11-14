package com.colearning.android.podcastcatcher;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper.SubscriptionCursor;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;
import com.colearning.android.podcastcatcher.model.Subscription;

public class SubscriptionListFragment extends ListFragment {
	private static final String TAG = "SubscriptionListFragment";
	private SubscriptionItemSelectedListener itemSelectedListener;
	private PodcastCatcherManager podcastCatcherManager;
	private SubscriptionCursor cursor;

	public interface SubscriptionItemSelectedListener {
		public void itemSelected(Subscription subscription);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		podcastCatcherManager = PodcastCatcherManager.create(getActivity());

		cursor = podcastCatcherManager.querySubscription();
		SubscriptionCursorAdapter subscriptionCursorAdapter = new SubscriptionCursorAdapter(getActivity(), cursor);
		setListAdapter(subscriptionCursorAdapter);

		// String[] columns = new String[] {PodcastCatcherDatabaseHelper.};
		// int[] to = new int[] { android.R.id.text1, android.R.id.text2 };
		// new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,
		// cursor, columns, to, CursorAdapter.FLAG_AUTO_REQUERY);
		// setListAdapter(new SubscriptionListAdapter(subscriptions));

		/*
		 * FeedParser feedParser = new FeedParser(); String urlPath1 =
		 * "http://feeds.feedburner.com/javaposse"; String urlPath2 =
		 * "http://feeds.feedburner.com/AndroidCentralPodcast"; Subscription
		 * javaPosseSubscription = feedParser.parseSubscription(urlPath1);
		 * Log.i(TAG, "Subscription: Title: " + javaPosseSubscription.getTitle()
		 * + ", sub: " + javaPosseSubscription.getSubTitle()); Subscription
		 * androidPodcastSubscription = feedParser.parseSubscription(urlPath2);
		 * Log.i(TAG, "Subscription: Title: " +
		 * androidPodcastSubscription.getTitle() + ", sub: " +
		 * androidPodcastSubscription.getSubTitle());
		 */
	}

	private static class SubscriptionCursorAdapter extends CursorAdapter {

		private SubscriptionCursor subscriptionCursor;

		public SubscriptionCursorAdapter(Context context, SubscriptionCursor cursor) {
			super(context, cursor, 0);
			this.subscriptionCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			Subscription subscription = subscriptionCursor.getSubscription();

			((TextView) view.findViewById(android.R.id.text1)).setText(subscription.getTitle());
			((TextView) view.findViewById(android.R.id.text2)).setText(subscription.getSubTitle());
		}
	}

	// @Override
	// public void onListItemClick(ListView l, View v, int position, long id) {
	// Subscription item = ((SubscriptionListAdapter)
	// getListAdapter()).getItem(position);
	// itemSelectedListener.itemSelected(item);
	// }
	//
	/*
	 * private class SubscriptionListAdapter extends ArrayAdapter<Subscription>
	 * { public SubscriptionListAdapter(List<Subscription> subscriptions) {
	 * super(getActivity(), 0, subscriptions); }
	 * 
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) { if (convertView == null) { convertView =
	 * getActivity().getLayoutInflater
	 * ().inflate(android.R.layout.simple_list_item_2, null); }
	 * 
	 * Subscription currSubscription = getItem(position);
	 * 
	 * TextView txtView1 = (TextView)
	 * convertView.findViewById(android.R.id.text1);
	 * txtView1.setText(currSubscription.getTitle());
	 * 
	 * TextView txtView2 = (TextView)
	 * convertView.findViewById(android.R.id.text2);
	 * txtView2.setText(currSubscription.getSubTitle());
	 * 
	 * return convertView; }
	 * 
	 * }
	 */

	@Override
	public void onDestroy() {
		cursor.close();
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

}

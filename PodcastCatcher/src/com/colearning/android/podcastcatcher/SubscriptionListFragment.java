package com.colearning.android.podcastcatcher;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionList;

public class SubscriptionListFragment extends ListFragment {
	private static final String TAG = "SubscriptionListFragment";
	private List<Subscription> subscriptions;
	private SubscriptionItemSelectedListener itemSelectedListener;

	public interface SubscriptionItemSelectedListener {
		public void itemSelected(Subscription subscription);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		subscriptions = SubscriptionList.create().getSubscriptions();
		setListAdapter(new SubscriptionListAdapter(subscriptions));

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

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Subscription item = ((SubscriptionListAdapter) getListAdapter()).getItem(position);
		itemSelectedListener.itemSelected(item);
	}

	private class SubscriptionListAdapter extends ArrayAdapter<Subscription> {
		public SubscriptionListAdapter(List<Subscription> subscriptions) {
			super(getActivity(), 0, subscriptions);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(android.R.layout.simple_list_item_2, null);
			}

			Subscription currSubscription = getItem(position);

			TextView txtView1 = (TextView) convertView.findViewById(android.R.id.text1);
			txtView1.setText(currSubscription.getTitle());

			TextView txtView2 = (TextView) convertView.findViewById(android.R.id.text2);
			txtView2.setText(currSubscription.getSubTitle());

			return convertView;
		}

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

package com.colearning.android.podcastcatcher;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		subscriptions = SubscriptionList.create().getSubscriptions();

		setListAdapter(new SubscriptionListAdapter(subscriptions));
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i(TAG, "ListView: " + l);
		Log.i(TAG, "View: " + v);
		Log.i(TAG, "position: " + position);
		Log.i(TAG, "id: " + id);
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

			return convertView;
		}

	}

}

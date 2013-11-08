package com.colearning.android.podcastcatcher;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionList;

public class SubscriptionListFragment extends ListFragment {

	private List<Subscription> subscriptions;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		subscriptions = SubscriptionList.create().getSubscriptions();

		setListAdapter(new SubscriptionListAdapter(subscriptions));
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

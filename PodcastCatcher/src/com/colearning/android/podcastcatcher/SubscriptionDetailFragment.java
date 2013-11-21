package com.colearning.android.podcastcatcher;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.colearning.android.podcastcatcher.db.PodcastCatcherDatasource.SubscriptionItemCursor;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionItem;

public class SubscriptionDetailFragment extends Fragment {
	public static final String SUBSCRIPTION_ID = "com.colearning.android.podcastcatcher.subscription_id";
	private Subscription subscription;
	private PodcastCatcherManager podcastCatcherManager;
	private SubscriptionItemCursor subscriptionItemsCursor;

	public static SubscriptionDetailFragment create(Long subscriptionId) {
		SubscriptionDetailFragment fragment = new SubscriptionDetailFragment();

		Bundle args = new Bundle();
		args.putLong(SUBSCRIPTION_ID, subscriptionId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		podcastCatcherManager = PodcastCatcherManager.create(getActivity());

		Long subscriptionId = getArguments().getLong(SUBSCRIPTION_ID);
		subscription = podcastCatcherManager.findSubscriptionById(subscriptionId);
		subscriptionItemsCursor = podcastCatcherManager.querySubscriptionItems(subscriptionId);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_subscription_detail, container, false);

		((TextView) view.findViewById(R.id.txtTitleValue)).setText(subscription.getTitle());
		((TextView) view.findViewById(R.id.txtSubTitleValue)).setText(subscription.getSubTitle());
		((TextView) view.findViewById(R.id.txtAuthorValue)).setText(subscription.getAuthor());
		((TextView) view.findViewById(R.id.txtCategoryValue)).setText(subscription.getCategory());
		((TextView) view.findViewById(R.id.txtLinkValue)).setText(subscription.getFeedUrl());
		((TextView) view.findViewById(R.id.txtSummaryValue)).setText(subscription.getSummary());

		ListView lvSubscriptionItems = (ListView) view.findViewById(R.id.listViewSubscriptionItems);

		lvSubscriptionItems.setAdapter(new SubscriptionItemsAdapter(getActivity(), subscriptionItemsCursor));

		return view;
	}

	private static class SubscriptionItemsAdapter extends CursorAdapter {

		private SubscriptionItemCursor subscriptionItemCursor;

		public SubscriptionItemsAdapter(Context context, SubscriptionItemCursor cursor) {
			super(context, cursor, 0);
			this.subscriptionItemCursor = cursor;
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			SubscriptionItem subscriptionItem = subscriptionItemCursor.getSubscriptionItem();

			((TextView) view.findViewById(android.R.id.text1)).setText(subscriptionItem.getTitle());
			((TextView) view.findViewById(android.R.id.text2)).setText(subscriptionItem.getItemDesc());
		}
	}
}

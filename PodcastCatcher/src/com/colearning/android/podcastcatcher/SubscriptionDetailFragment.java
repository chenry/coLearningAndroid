package com.colearning.android.podcastcatcher;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.colearning.android.podcastcatcher.contract.PodcastCatcherContract;
import com.colearning.android.podcastcatcher.db.PodcastCatcherDatasource.SubscriptionItemCursor;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.model.SubscriptionItem;

public class SubscriptionDetailFragment extends Fragment {
	public static final String SUBSCRIPTION_ID = "com.colearning.android.podcastcatcher.subscription_id";
	private static final String TAG = "SubscriptionDetailFragment";
	private Subscription subscription;
	private PodcastCatcherManager podcastCatcherManager;
	private SubscriptionItemCursor subscriptionItemsCursor;
	private long subscriptionId;

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
		setHasOptionsMenu(true);
		podcastCatcherManager = PodcastCatcherManager.create(getActivity());

		subscriptionId = getArguments().getLong(SUBSCRIPTION_ID);
		subscription = podcastCatcherManager.findSubscriptionById(subscriptionId);
		subscriptionItemsCursor = podcastCatcherManager.querySubscriptionItems(subscriptionId);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_subscription_detail, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_item_delete_subscription:
			handleDeleteSubscription();
			getActivity().finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void handleDeleteSubscription() {
		ContentResolver cr = getActivity().getContentResolver();
		// delete the subscription items
		int deletedSubscriptionItemCount = cr.delete(PodcastCatcherContract.SubscriptionItem.CONTENT_URI,
				PodcastCatcherContract.SubscriptionItem.Columns.SUBSCRIPTION_ID + " = ?", new String[] { String.valueOf(subscriptionId) });
		Log.i(TAG, "Deleted Subscription Item Count: " + deletedSubscriptionItemCount);

		// delete the subscription
		Uri uri = Uri.withAppendedPath(PodcastCatcherContract.Subscription.CONTENT_URI, String.valueOf(subscription.getId()));
		int deleteCount = cr.delete(uri, null, null);
		Log.i(TAG, "Deleted Subscription Count: " + deleteCount);
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
			return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			SubscriptionItem subscriptionItem = subscriptionItemCursor.getSubscriptionItem();

			((TextView) view.findViewById(android.R.id.text1)).setText(subscriptionItem.getTitle());
		}
	}
}

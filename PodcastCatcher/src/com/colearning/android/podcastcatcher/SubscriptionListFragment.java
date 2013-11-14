package com.colearning.android.podcastcatcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.colearning.android.podcastcatcher.db.PodcastCatcherDatabaseHelper.SubscriptionCursor;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;
import com.colearning.android.podcastcatcher.model.Subscription;
import com.colearning.android.podcastcatcher.service.UpdatePodcastSubscriptionService;

public class SubscriptionListFragment extends ListFragment {
	private static final String TAG = "SubscriptionListFragment";
	private SubscriptionItemSelectedListener itemSelectedListener;
	private PodcastCatcherManager podcastCatcherManager;
	private SubscriptionCursor subscriptionCursor;

	public interface SubscriptionItemSelectedListener {
		public void subscriptionSelected(long subscriptionId);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		podcastCatcherManager = PodcastCatcherManager.create(getActivity());

		subscriptionCursor = podcastCatcherManager.querySubscription();
		FragmentActivity activity = getActivity();
		SubscriptionCursorAdapter subscriptionCursorAdapter = new SubscriptionCursorAdapter(activity, subscriptionCursor);
		setListAdapter(subscriptionCursorAdapter);

		Intent intent = new Intent(activity, UpdatePodcastSubscriptionService.class);
		activity.startService(intent);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		itemSelectedListener.subscriptionSelected(id);
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

	@Override
	public void onDestroy() {
		subscriptionCursor.close();
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

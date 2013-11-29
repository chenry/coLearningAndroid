package com.colearning.android.podcastcatcher;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colearning.android.podcastcatcher.loader.DataLoader;
import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;
import com.colearning.android.podcastcatcher.model.SubscriptionItem;

public class SubscriptionItemDetailFragment extends Fragment implements
		LoaderCallbacks<SubscriptionItem> {
	public static final String SUBSCRIPTION_ITEM_ID = "subscriptionItemId";

	public static SubscriptionItemDetailFragment create(
			long subscriptionItemDetailId) {
		SubscriptionItemDetailFragment fragment = new SubscriptionItemDetailFragment();

		Bundle args = new Bundle();
		args.putLong(SUBSCRIPTION_ITEM_ID, subscriptionItemDetailId);
		fragment.setArguments(args);

		return fragment;
	}

	private PodcastCatcherManager podcastManager;
	private long subscriptionItemId;
	private Context context;
	private TextView txtItemTitle;
	private SubscriptionItem mSubscriptionItem;
	private TextView txtItemFileUrl;
	private TextView txtItemLinkUrl;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		context = getActivity();
		podcastManager = PodcastCatcherManager.create(getActivity());

		subscriptionItemId = getArguments().getLong(SUBSCRIPTION_ITEM_ID);
		getActivity().getSupportLoaderManager().initLoader(0, null, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_subscription_item_detail, container, false);
		
		txtItemTitle = (TextView) view.findViewById(R.id.txtItemTitleValue);
		txtItemFileUrl = (TextView) view.findViewById(R.id.txtItemFileUrlValue);
		txtItemLinkUrl = (TextView) view.findViewById(R.id.txtItemLinkUrlValue);
		
		updateUI();
		
		return view;
	}
	
	private void updateUI() {
		if (mSubscriptionItem == null) {
			return;
		}
		
		txtItemTitle.setText(mSubscriptionItem.getTitle());
		txtItemFileUrl.setText(mSubscriptionItem.getFileLocation());
		txtItemLinkUrl.setText(mSubscriptionItem.getLinkUrl());
		
	}

	@Override
	public Loader<SubscriptionItem> onCreateLoader(int arg0, Bundle arg1) {
		return new SubscriptionItemLoader(context);
	}

	@Override
	public void onLoadFinished(Loader<SubscriptionItem> loader,
			SubscriptionItem subscriptionItem) {
		mSubscriptionItem = subscriptionItem;
		updateUI();
	}

	@Override
	public void onLoaderReset(Loader<SubscriptionItem> arg0) {
		// do nothing.
	}

	private class SubscriptionItemLoader extends DataLoader<SubscriptionItem> {

		public SubscriptionItemLoader(Context context) {
			super(context);
		}

		@Override
		public SubscriptionItem loadInBackground() {
			return podcastManager.findSubscriptionItem(subscriptionItemId);
		}

	}
}

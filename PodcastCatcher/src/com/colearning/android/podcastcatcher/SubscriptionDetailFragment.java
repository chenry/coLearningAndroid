package com.colearning.android.podcastcatcher;

import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.colearning.android.podcastcatcher.manager.PodcastCatcherManager;
import com.colearning.android.podcastcatcher.model.Subscription;

public class SubscriptionDetailFragment extends Fragment {
	public static final String SUBSCRIPTION_ID = "com.colearning.android.podcastcatcher.subscription_id";
	private Subscription subscription;
	private PodcastCatcherManager podcastCatcherManager;

	public static SubscriptionDetailFragment create(UUID subscriptionId) {
		SubscriptionDetailFragment fragment = new SubscriptionDetailFragment();

		Bundle args = new Bundle();
		args.putSerializable(SUBSCRIPTION_ID, subscriptionId);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		podcastCatcherManager = PodcastCatcherManager.create(getActivity());

		UUID subscriptionId = (UUID) getArguments().getSerializable(SUBSCRIPTION_ID);
		subscription = podcastCatcherManager.findSubscription(subscriptionId);
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

		return view;
	}
}

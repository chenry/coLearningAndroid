package com.colearning.android.podcastcatcher;

import java.util.UUID;

import android.support.v4.app.Fragment;

public class SubscriptionDetailActivity extends OneFragmentFragmentActivity {

	@Override
	public Fragment createFragment() {
		UUID subscriptionId = (UUID) getIntent().getSerializableExtra(SubscriptionDetailFragment.SUBSCRIPTION_ID);
		return SubscriptionDetailFragment.create(subscriptionId);
	}

}

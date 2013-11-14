package com.colearning.android.podcastcatcher;

import android.support.v4.app.Fragment;

public class SubscriptionDetailActivity extends OneFragmentFragmentActivity {

	@Override
	public Fragment createFragment() {
		Long subscriptionId = (Long) getIntent().getLongExtra(SubscriptionDetailFragment.SUBSCRIPTION_ID, -1L);
		return SubscriptionDetailFragment.create(subscriptionId);
	}

}

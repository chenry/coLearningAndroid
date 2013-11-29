package com.colearning.android.podcastcatcher;

import android.support.v4.app.Fragment;

public class SubscriptionItemDetailActivity extends OneFragmentFragmentActivity {

	@Override
	public Fragment createFragment() {
		long subscriptionItemDetailId = getIntent().getLongExtra(SubscriptionItemDetailFragment.SUBSCRIPTION_ITEM_ID, -1L);
		return SubscriptionItemDetailFragment.create(subscriptionItemDetailId);
	}

}

package com.colearning.android.podcastcatcher;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class SubscriptionDetailActivity extends OneFragmentFragmentActivity implements SubscriptionDetailFragment.SubscriptionItemSelectedListener {

	@Override
	public Fragment createFragment() {
		Long subscriptionId = (Long) getIntent().getLongExtra(SubscriptionDetailFragment.SUBSCRIPTION_ID, -1L);
		return SubscriptionDetailFragment.create(subscriptionId);
	}

	@Override
	public void subscriptionItemSelected(long subscriptionItemId) {
		Intent intent = new Intent(this, SubscriptionItemDetailActivity.class);
		intent.putExtra(SubscriptionItemDetailFragment.SUBSCRIPTION_ITEM_ID, subscriptionItemId);
		startActivity(intent);
	}

}

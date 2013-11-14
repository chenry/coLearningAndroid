package com.colearning.android.podcastcatcher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

public class SubscriptionListActivity extends OneFragmentFragmentActivity implements SubscriptionListFragment.SubscriptionItemSelectedListener {

	private static final String TAG = "SubscriptionListActivity";

	@Override
	public Fragment createFragment() {
		return new SubscriptionListFragment();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(TAG, "onResume");
	}

	@Override
	public void subscriptionSelected(long subscriptionId) {
		Intent intent = new Intent(this, SubscriptionDetailActivity.class);
		intent.putExtra(SubscriptionDetailFragment.SUBSCRIPTION_ID, subscriptionId);
		startActivity(intent);
	}
}

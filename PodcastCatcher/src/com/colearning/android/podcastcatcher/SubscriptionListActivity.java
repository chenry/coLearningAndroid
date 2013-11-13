package com.colearning.android.podcastcatcher;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.colearning.android.podcastcatcher.model.Subscription;

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
	public void itemSelected(Subscription subscription) {
		Log.i(TAG, "Selected title: " + subscription.getTitle());

		Intent intent = new Intent(this, SubscriptionDetailActivity.class);
		intent.putExtra(SubscriptionDetailFragment.SUBSCRIPTION_ID, subscription.getId());

		startActivity(intent);
	}
}

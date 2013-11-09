package com.colearning.android.podcastcatcher;

import android.support.v4.app.Fragment;
import android.util.Log;

public class SubscriptionListActivity extends OneFragmentFragmentActivity {

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
}

package com.colearning.android.podcastcatcher;

import android.support.v4.app.Fragment;

public class SubscriptionListActivity extends OneFragmentFragmentActivity {

	@Override
	public Fragment createFragment() {
		return new SubscriptionListFragment();
	}
}

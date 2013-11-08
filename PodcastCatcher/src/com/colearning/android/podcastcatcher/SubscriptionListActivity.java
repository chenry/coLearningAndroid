package com.colearning.android.podcastcatcher;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class SubscriptionListActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_fragment);

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
			fragment = new SubscriptionListFragment();
			//@formatter:off
			fm
				.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
			//@formatter:on
		}
	}

}

package com.colearning.android.podcastpoll;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class OneFragmentFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_fragment);

		FragmentManager fm = getSupportFragmentManager();

		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
			fragment = createFragment();
			//@formatter:off
			fm
				.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
			//@formatter:on
		}

	}

	public abstract Fragment createFragment();

}

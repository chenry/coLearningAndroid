package com.colearning.android.podcastpoll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class LikePodcastResponseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_like_podcast_response);

		setContentView(R.layout.activity_fragment);

		FragmentManager fm = getSupportFragmentManager();

		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
			Intent intent = getIntent();
			boolean doesLikePodcast = intent.getBooleanExtra(LikePodcastResponseFragment.DOES_LIKE_PODCASTS, false);
			fragment = LikePodcastResponseFragment.create(doesLikePodcast);
			//@formatter:off
			fm
				.beginTransaction()
				.add(R.id.fragmentContainer, fragment)
				.commit();
			//@formatter:on
		}

	}
}

package com.colearning.android.podcastpoll;

import android.content.Intent;
import android.support.v4.app.Fragment;

public class LikePodcastResponseActivity extends OneFragmentFragmentActivity {

	@Override
	public Fragment createFragment() {
		Intent intent = getIntent();
		boolean doesLikePodcast = intent.getBooleanExtra(LikePodcastResponseFragment.DOES_LIKE_PODCASTS, false);
		return LikePodcastResponseFragment.create(doesLikePodcast);
	}
}

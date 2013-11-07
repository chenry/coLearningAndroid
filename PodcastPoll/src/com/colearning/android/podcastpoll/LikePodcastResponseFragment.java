package com.colearning.android.podcastpoll;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LikePodcastResponseFragment extends Fragment {

	public static final String DOES_LIKE_PODCASTS = "com.colearning.android.podcastpoll.doesLikePodcasts";
	protected static final String CHANGED_MIND = "com.colearning.android.podcastpoll.changeMind";

	private TextView txtLikePodcastResponse;
	private Button btnChangeMindNo;
	private Button btnChangeMindYes;

	public static LikePodcastResponseFragment create(boolean doesLikePodcast) {
		LikePodcastResponseFragment fragment = new LikePodcastResponseFragment();
		Bundle args = new Bundle();
		args.putBoolean(DOES_LIKE_PODCASTS, doesLikePodcast);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_like_podcast_response, container, false);

		txtLikePodcastResponse = (TextView) view.findViewById(R.id.txtLikePodcastResponse);
		btnChangeMindNo = (Button) view.findViewById(R.id.btnChangeMindNo);
		btnChangeMindYes = (Button) view.findViewById(R.id.btnChangeMindYes);

		boolean doesLikePodcasts = getActivity().getIntent().getBooleanExtra(DOES_LIKE_PODCASTS, false);
		String response = "I have no clue if you like podcasts or not...";
		if (doesLikePodcasts) {
			response = "I am so happy that you do like podcasts.";
		} else {
			response = "You are not going to like this application...";
		}

		response += "Do you want to change your mind?";
		txtLikePodcastResponse.setText(response);

		btnChangeMindNo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				handleChangeMind(false);
			}
		});

		btnChangeMindYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				handleChangeMind(true);
			}
		});
		return view;
	}

	public void handleChangeMind(boolean didChangeMind) {
		Intent data = new Intent();
		data.putExtra(CHANGED_MIND, didChangeMind);
		getActivity().setResult(Activity.RESULT_OK, data);
	}

}

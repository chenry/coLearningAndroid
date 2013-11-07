package com.colearning.android.podcastcatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LikePodcastResponseActivity extends Activity {
	public static final String DOES_LIKE_PODCASTS = "com.colearning.android.podcastcatcher.doesLikePodcasts";
	protected static final String CHANGED_MIND = "com.colearning.android.podcastcatcher.changeMind";
	private TextView txtLikePodcastResponse;
	private Button btnChangeMindNo;
	private Button btnChangeMindYes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_like_podcast_response);
		txtLikePodcastResponse = (TextView) findViewById(R.id.txtLikePodcastResponse);
		btnChangeMindNo = (Button) findViewById(R.id.btnChangeMindNo);
		btnChangeMindYes = (Button) findViewById(R.id.btnChangeMindYes);

		boolean doesLikePodcasts = getIntent().getBooleanExtra(DOES_LIKE_PODCASTS, false);
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
	}

	public void handleChangeMind(boolean didChangeMind) {
		Intent data = new Intent();
		data.putExtra(CHANGED_MIND, didChangeMind);
		setResult(RESULT_OK, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.like_podcast_response, menu);
		return true;
	}

}

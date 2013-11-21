package com.colearning.android.podcastcatcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class FeedUrlDialog extends DialogFragment {
	public static final String EXTRA_FEED_URL = "extra_feed_url";
	private String mFeedUrl;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_feed_url, null);

		mFeedUrl = "http://feeds.feedburner.com/javaposse";
		EditText txtFeedUrl = (EditText) view.findViewById(R.id.editTxt_feed_url);
		txtFeedUrl.setText(mFeedUrl);
		txtFeedUrl.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mFeedUrl = s.toString();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		//@formatter:off
		return new AlertDialog.Builder(getActivity())
			.setView(view)
			.setTitle(R.string.feed_url)
			.setPositiveButton(android.R.string.ok, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					sendResult(Activity.RESULT_OK);
				}
			})
			.create();
		
		//@formatter:on
	}

	private void sendResult(int resultCode) {
		if (getTargetFragment() == null) {
			return;
		}

		Intent i = new Intent();
		i.putExtra(EXTRA_FEED_URL, mFeedUrl);

		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
	}

}

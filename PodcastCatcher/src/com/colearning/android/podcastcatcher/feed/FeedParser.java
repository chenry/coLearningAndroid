package com.colearning.android.podcastcatcher.feed;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.colearning.android.podcastcatcher.model.Subscription;

public class FeedParser {

	private static final String TAG = "FeedParser";

	public Subscription parseSubscription(String urlPath) {
		String xml = parseFeedToXmlString(urlPath);
		Subscription subscription = new Subscription();
		try {
			XmlPullParser parser = createXmlPullParser();
			parser.setInput(new StringReader(xml));

			int eventType = parser.next();
			boolean hasSeenItem = false;
			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (hasSeenItem) {
					eventType = parser.next();
					continue;
				}

				if (eventType == XmlPullParser.START_TAG) {
					// Log.i(TAG, "Name: " + parser.getName());
					if ("item".equals(parser.getName())) {
						hasSeenItem = true;
						eventType = parser.next();
						continue;
					}

					if ("title".equals(parser.getName())) {
						subscription.setTitle(getTextIfAvailable(parser));
					}

					if ("itunes:subtitle".equals(parser.getName())) {
						subscription.setSubTitle(getTextIfAvailable(parser));
					}
				}
				eventType = parser.next();
			}
		} catch (Exception e) {

		}

		return subscription;
	}

	private String getTextIfAvailable(XmlPullParser parser) throws Exception {
		int eventType = parser.next();
		if (eventType == XmlPullParser.TEXT) {
			return parser.getText();
		}
		return null;
	}

	private XmlPullParser createXmlPullParser() throws Exception {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		return factory.newPullParser();
	}

	public String parseFeedToXmlString(String urlPath) {

		try {
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			InputStream is = connection.getInputStream();

			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = is.read(buffer)) > 0) {
				baos.write(buffer, 0, bytesRead);
			}
			baos.flush();
			baos.close();
			return new String(baos.toByteArray());
		} catch (Exception e) {
			return null;
		}
	}
}

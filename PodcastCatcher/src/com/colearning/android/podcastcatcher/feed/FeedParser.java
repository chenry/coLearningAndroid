package com.colearning.android.podcastcatcher.feed;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

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

					// private Date lastPubDate;

					if ("title".equals(parser.getName())) {
						subscription.setTitle(getTextIfAvailable(parser));
					}

					if ("pubDate".equals(parser.getName())) {
						subscription.setLastPubDate(getDateIfAvailable(parser));
					}

					if ("image/url".equals(parser.getName())) {
						// FIXME CH: implement this later
					}

					if ("media:category".equals(parser.getName())) {
						subscription.setCategory(getTextIfAvailable(parser));
					}

					if ("description".equals(parser.getName())) {
						subscription.setSummary(getTextIfAvailable(parser));
					}

					if ("itunes:author".equals(parser.getName())) {
						subscription.setAuthor(getTextIfAvailable(parser));
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

	private Date getDateIfAvailable(XmlPullParser parser) throws Exception {
		String dateAsString = getTextIfAvailable(parser);
		if (dateAsString == null) {
			return null;
		}

		String betterDateAsString = dateAsString.split("\\+")[0];

		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		return formatter.parse(betterDateAsString, new ParsePosition(0));
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

package com.colearning.android.podcastcatcher.feed;

import java.io.StringWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.colearning.android.podcastcatcher.model.Subscription;

public class FeedParser {

	private static final String TAG = "FeedParser";

	public Subscription parseSubscription(String urlPath) {
		try {
			SAXParserFactory saxPF = SAXParserFactory.newInstance();
			SAXParser parser = saxPF.newSAXParser();
			XMLReader xmlReader = parser.getXMLReader();
			URL url = new URL(urlPath);
			/**
			 * Create the Handler to handle each of the XML tags.
			 **/
			SubscriptionHandler handler = new SubscriptionHandler();
			xmlReader.setContentHandler(handler);
			xmlReader.parse(new InputSource(url.openStream()));
			return handler.getSubscription();
		} catch (Exception e) {
			Log.e(TAG, "Encountered problems....");
		}
		return null;
	}

	private class SubscriptionHandler extends DefaultHandler {
		private Subscription subscription;
		private boolean foundItem = false;
		private StringWriter valueSw = null;
		private boolean startNewStringBuffer = true;

		@Override
		public void startDocument() throws SAXException {
			subscription = new Subscription();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if (foundItem) {
				return;
			}

			if ("item".equals(localName)) {
				foundItem = true;
				return;
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			if (foundItem) {
				return;
			}

			startNewStringBuffer = true;

			String value = valueSw.toString().trim();
			if ("title".equals(localName)) {
				subscription.setTitle(value);
			}
			if ("pubDate".equals(localName)) {
				subscription.setLastPubDate(getDateIfAvailable(value));
			}

			if ("image/url".equals(localName)) {
				// FIXME CH: implement this later
			}

			if ("media:category".equals(qName)) {
				subscription.setCategory(value);
			}

			if ("description".equals(localName)) {
				subscription.setSummary(value);
			}

			if ("itunes:author".equals(qName)) {
				subscription.setAuthor(value);
			}

			if ("itunes:subtitle".equals(qName)) {
				subscription.setSubTitle(value);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (startNewStringBuffer) {
				valueSw = new StringWriter();
				startNewStringBuffer = false;
			}

			valueSw.append(new String(ch, start, length));
		}

		public Subscription getSubscription() {
			return subscription;
		}
	}

	private Date getDateIfAvailable(String dateAsString) {
		if (dateAsString == null) {
			return null;
		}

		String betterDateAsString = dateAsString.split("\\+")[0];

		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		return formatter.parse(betterDateAsString, new ParsePosition(0));

	}

}

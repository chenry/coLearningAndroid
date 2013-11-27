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
import com.colearning.android.podcastcatcher.model.SubscriptionItem;

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

	private Date getDateIfAvailable(String dateAsString) {
		if (dateAsString == null) {
			return null;
		}

		String betterDateAsString = dateAsString.split("\\+")[0];

		DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
		return formatter.parse(betterDateAsString, new ParsePosition(0));

	}

	private class SubscriptionHandler extends DefaultHandler {
		private Subscription subscription;
		private SubscriptionItem currSubscriptionItem;
		private boolean inItem = false;
		private boolean inImage = false;
		private StringWriter valueSw = null;
		private boolean startNewStringBuffer = true;

		@Override
		public void startDocument() throws SAXException {
			subscription = new Subscription();
		}

		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			if ("item".equals(localName)) {
				inItem = true;
				inImage = false;
				currSubscriptionItem = new SubscriptionItem();
				subscription.addSubscriptionItem(currSubscriptionItem);
			}

			if (!inItem && "image".equals(localName)) {
				inImage = true;
			}

			if ("enclosure".equals(localName)) {
				currSubscriptionItem.setMediaUrl(attributes.getValue("url"));
			}
		}

		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			startNewStringBuffer = true;

			if ("item".equals(localName)) {
				currSubscriptionItem = new SubscriptionItem();
				return;
			}

			String value = valueSw.toString().trim();
			if (inItem) {
				endSubscriptionItemElement(uri, localName, qName, value);
			} else {
				endSubscriptionElement(uri, localName, qName, value);
			}

			if (inImage) {
				inImage = false;
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

		public void endSubscriptionElement(String uri, String localName, String qName, String value) {
			if ("title".equals(localName)) {
				subscription.setTitle(value);
			}
			if ("pubDate".equals(localName)) {
				subscription.setLastPubDate(getDateIfAvailable(value));
			}

			if (inImage && "url".equals(localName)) {
				subscription.setImageUrl(value);
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

		private void endSubscriptionItemElement(String uri, String localName, String qName, String value) {
			if ("title".equals(localName)) {
				currSubscriptionItem.setTitle(value);
			} else if ("pubDate".equals(localName)) {
				currSubscriptionItem.setPubDate(getDateIfAvailable(value));
			} else if ("guid".equals(localName)) {
				currSubscriptionItem.setGuidId(value);
			} else if ("link".equals(localName)) {
				currSubscriptionItem.setLinkUrl(value);
			} else if ("description".equals(localName)) {
				currSubscriptionItem.setItemDesc(value);
			}
		}

		public Subscription getSubscription() {
			return subscription;
		}

	}

}

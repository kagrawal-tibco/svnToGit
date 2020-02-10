package com.tibco.cep.dashboard.plugin.beviews.mal;

public class ExternalURL {

	public static final String KEY = ExternalURL.class.getName();

	private String name;

	private String url;

	public ExternalURL(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public final String getName() {
		return name;
	}

	public final String getURL() {
		return url;
	}
}
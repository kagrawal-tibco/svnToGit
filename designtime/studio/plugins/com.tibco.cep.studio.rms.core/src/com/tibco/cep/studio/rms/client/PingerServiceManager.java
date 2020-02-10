package com.tibco.cep.studio.rms.client;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.studio.rms.core.RMSCorePlugin;

public class PingerServiceManager {
	
	private static final Map<InetSocketAddress, Pinger> pingers = new HashMap<InetSocketAddress, Pinger>();
	
	/**
	 * 
	 * @param url
	 * @param delay
	 * @return
	 */
	public static Pinger getPinger(String url, int delay) {
		URL rmsUrl;
		try {
			rmsUrl = new URL(url);
			//Get host port
			String host = rmsUrl.getHost();
			int port = rmsUrl.getPort();
			InetSocketAddress socketAddress = new InetSocketAddress(host, port);
			Pinger pinger = null;
			if (!pingers.containsKey(socketAddress)) {
				pinger = new Pinger(host, port, delay);
				pingers.put(socketAddress, pinger);
				return pinger;
			}
			return pinger = pingers.get(socketAddress);
		} catch (MalformedURLException e) {
			RMSCorePlugin.log(e);
			return null;
		}
	}
	
	/**
	 * 
	 * @param url
	 */
	public static void stopPinger(String url) {
		URL rmsUrl;
		try {
			rmsUrl = new URL(url);
			//Get host port
			String host = rmsUrl.getHost();
			int port = rmsUrl.getPort();
			InetSocketAddress socketAddress = new InetSocketAddress(host, port);
			if (pingers.containsKey(socketAddress)) {
				Pinger pinger = pingers.get(socketAddress);
				if (pinger.isRunning()) {
					RMSCorePlugin.debug(PingerServiceManager.class.getName(), "Stopping pinger for [host {0} : port {1}]", host, port);
					pinger.stop();
					pingers.remove(socketAddress);
				}
			}
		} catch (MalformedURLException e) {
			RMSCorePlugin.log(e);
		}
	}
}

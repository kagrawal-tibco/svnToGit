package com.tibco.cep.dashboard.psvr.plugin;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class PlugInUtils {

	public static Collection<Builder> getBuilders() {
		List<PlugIn> plugIns = PluginFinder.getInstance().getPlugins();
		List<Builder> builders = new ArrayList<Builder>();
		for (PlugIn plugIn : plugIns) {
			Builder builder = plugIn.getBuilder();
			if (builder != null) {
				builders.add(builder);
			}
		}
		builders.add(PlugInsService.getInstance().getDefaultPlugin().getBuilder());
		return builders;
	}

	public static Map<String,URL> getActionConfigURLs() {
		List<PlugIn> plugIns = PluginFinder.getInstance().getPlugins();
		Map<String,URL> urls = new LinkedHashMap<String, URL>();
		PlugIn defaultPlugin = PlugInsService.getInstance().getDefaultPlugin();
		URL actionConfigURL = defaultPlugin.getActionConfigURL();
		if (actionConfigURL != null) {
			urls.put(defaultPlugin.getId(), actionConfigURL);
		}		
		for (PlugIn plugIn : plugIns) {
			actionConfigURL = plugIn.getActionConfigURL();
			if (actionConfigURL != null) {
				urls.put(plugIn.getId(),actionConfigURL);
			}
		}
		return urls;
	}

}

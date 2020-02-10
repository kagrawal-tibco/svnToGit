package com.tibco.cep.studio.dashboard.ui.images;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetricField;
import com.tibco.cep.studio.dashboard.ui.DashboardUIPlugin;

public class ContributableImageRegistry extends ImageRegistry {

	private ResourceManager manager;

	private List<ImageContributor> contributors;

	private Map<String,Entry> contributedEntries;

	public ContributableImageRegistry() {
		this(Display.getCurrent());
	}

	public ContributableImageRegistry(Display display) {
		this(JFaceResources.getResources(display));
	}

	public ContributableImageRegistry(ResourceManager manager) {
		super(manager);
		this.manager = manager;
		contributedEntries = new HashMap<String, Entry>();
		contributors = new ArrayList<ImageContributor>();
		IConfigurationElement[] configurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(DashboardUIPlugin.PLUGIN_ID, "imageContributor");
		for (IConfigurationElement configurationElement : configurationElements) {
			String attribute = configurationElement.getAttribute("contributor");
			if (attribute != null) {
				try {
					Object executableExtension = configurationElement.createExecutableExtension("contributor");
					if (executableExtension instanceof ImageContributor) {
						contributors.add((ImageContributor) executableExtension);
					}
				} catch (CoreException e) {
					DashboardUIPlugin.getInstance().getLog().log(new Status(IStatus.WARNING,DashboardUIPlugin.PLUGIN_ID,"could not load image contributor extension "+attribute,e));
				}
			}
		}
	}

	@Override
	public Image get(String key) {
		Image image = super.get(key);
		if (image != null){
			return image;
		}
		Entry contributedEntry = getContributedEntry(key);
		if (contributedEntry != null) {
			if (contributedEntry.image == null) {
				contributedEntry.image = manager.createImageWithDefault(contributedEntry.descriptor);
			}
			return contributedEntry.image;
		}
		return super.get(KnownImageKeys.IMG_KEY_PLACE_HOLDER);
	}

	@Override
	public ImageDescriptor getDescriptor(String key) {
		ImageDescriptor descriptor = super.getDescriptor(key);
		if (descriptor != null){
			return descriptor;
		}
		Entry contributedEntry = getContributedEntry(key);
		if (contributedEntry != null) {
			return contributedEntry.descriptor;
		}
		return super.getDescriptor(KnownImageKeys.IMG_KEY_PLACE_HOLDER);
	}

	public Image get(LocalElement element){
		if (element instanceof LocalMetric){
			return get("metric_16x16.gif");
		}
		if (element instanceof LocalMetricField){
			return get("field_16x16.gif");
		}
		if (element instanceof LocalConfig){
			LocalConfig viewsElement = (LocalConfig) element;
			String viewsElementType = viewsElement.getInsightType();
			String imageKey = viewsElementType.toLowerCase()+"_16x16.gif";
			if (contains(imageKey) == false) {
				imageKey = viewsElementType.toLowerCase()+"_16x16.png";
			}
			return get(imageKey);
		}
		return get(KnownImageKeys.IMG_KEY_PLACE_HOLDER);
	}

	private Entry getContributedEntry(String key) {
		Entry entry = contributedEntries.get(key);
		if (entry == null){
			for (ImageContributor contributor : contributors) {
				ImageDescriptor descriptor = contributor.getDescriptor(key);
				if (descriptor != null){
					entry = new Entry();
					entry.descriptor = descriptor;
					contributedEntries.put(key, entry);
					break;
				}
			}
		}
		return entry;
	}

	private boolean contains(String key) {
		if (super.getDescriptor(key) != null) {
			return true;
		}
		if (getContributedEntry(key) != null) {
			return true;
		}
		return false;
	}

	@Override
	public void dispose() {
		if (contributedEntries != null) {
			contributedEntries.clear();
		}
		super.dispose();
	}

    private static class Entry {
    	/** the image */
        protected Image image;

        /** the descriptor */
        protected ImageDescriptor descriptor;
    }

}

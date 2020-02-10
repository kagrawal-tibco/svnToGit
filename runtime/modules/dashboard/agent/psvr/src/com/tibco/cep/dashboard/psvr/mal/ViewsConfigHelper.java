package com.tibco.cep.dashboard.psvr.mal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.config.GlobalConfiguration;
import com.tibco.cep.dashboard.config.LocaleFinder;
import com.tibco.cep.dashboard.config.TimeZoneFinder;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.alerts.AlertEvalutorsIndex;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponentColorSet;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALFlowLayout;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.mal.model.MALSkin;
import com.tibco.cep.dashboard.psvr.mal.model.MALView;
import com.tibco.cep.dashboard.psvr.mal.model.MALViewLocale;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class ViewsConfigHelper {

	private static final int DEFAULT_COMPONENT_HEIGHT = 140;

	private static final int DEFAULT_COMPONENT_WIDTH = 200;

	protected static Logger LOGGER;
	protected static ExceptionHandler EXCEPTION_HANDLER;
	protected static MessageGenerator MESSAGE_GENERATOR;

	protected SecurityToken token;
	protected MALView viewsConfig;
	protected Locale locale;
	protected TimeZone timeZone;
	protected DateFormat timeFormat;

	protected List<MALPage> unmodifiablePageList;
	protected MALPage currentPage;

	private Map<String, ComponentUsageTracker> compByIdMap;
	private Map<String, MALComponent> compByNameMap;
	private Map<String, Set<MALComponent>> compsByTypeMap;

	protected MALSkinIndexer skinIndexer;

	protected List<ElementChangeListener> listeners;

	private InternalViewsConfigHelperListener internalListener;

	private long lastModifiedTime;

	protected AlertEvalutorsIndex evalutorsIndex;

	protected int defaultComponentHeightUnit = DEFAULT_COMPONENT_HEIGHT;

	protected int defaultComponentWidthUnit = DEFAULT_COMPONENT_WIDTH;

	protected ViewsConfigHelper(){
	}

	public ViewsConfigHelper(SecurityToken token, AlertEvalutorsIndex evalutorsIndex, MALView viewsConfig) {
		if (viewsConfig == null) {
			throw new IllegalArgumentException("views config cannot be null");
		}
		this.token = token;
		this.viewsConfig = viewsConfig;
		MALViewLocale malLocale = this.viewsConfig.getLocale();
		locale = new LocaleFinder(LOGGER, malLocale == null ? null : malLocale.getLocale(), GlobalConfiguration.getInstance().getLocale()).getLocale();
		timeZone = new TimeZoneFinder(LOGGER, malLocale == null ? null : malLocale.getTimeZone(), GlobalConfiguration.getInstance().getTimeZone()).getTimeZone();

		if (malLocale == null) {
			locale = GlobalConfiguration.getInstance().getLocale();
			timeZone = GlobalConfiguration.getInstance().getTimeZone();
			timeFormat = GlobalConfiguration.getInstance().getDateTimeFormat();
		} else {
			locale = new LocaleFinder(LOGGER, malLocale.getLocale(), GlobalConfiguration.getInstance().getLocale()).getLocale();
			timeZone = new TimeZoneFinder(LOGGER, malLocale.getTimeZone(), GlobalConfiguration.getInstance().getTimeZone()).getTimeZone();
			try {
				timeFormat = new SimpleDateFormat(malLocale.getTimeFormat());
			} catch (IllegalArgumentException e) {
				timeFormat = GlobalConfiguration.getInstance().getDateTimeFormat();
				MessageGeneratorArgs args = new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), null, malLocale.getTimeFormat(), timeFormat);
				String message = MESSAGE_GENERATOR.getMessage("viewsconfig.helper.invaliddatetimeformatpatterndefinedmsg", args);
				LOGGER.log(Level.WARN, message);

			}
		}
		unmodifiablePageList = Collections.unmodifiableList(Arrays.asList(this.viewsConfig.getAccessiblePage()));
		compByIdMap = new HashMap<String, ComponentUsageTracker>();
		compByNameMap = new HashMap<String, MALComponent>();
		compsByTypeMap = new HashMap<String, Set<MALComponent>>();
		skinIndexer = MALSkinCache.getInstance().getInsightSkinIndexer(this.viewsConfig.getSkin());
		this.evalutorsIndex = evalutorsIndex;
		indexComponents();
		computeDefaultDimensionUnits();
		currentPage = viewsConfig.getDefaultPage();
		listeners = new LinkedList<ElementChangeListener>();
		internalListener = new InternalViewsConfigHelperListener(this);
		addElementChangeListener(internalListener);
	}

	public DateFormat getTimeFormat() {
		return timeFormat;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	public Locale getLocale() {
		return locale;
	}

	public List<MALPage> getPages() {
		return unmodifiablePageList;
	}

	public MALView getViewsConfig() {
		return viewsConfig;
	}

	public MALPage getDefaultPage(){
		return viewsConfig.getDefaultPage();
	}

	public void setCurrentPageID(String pageID) {
		if (StringUtil.isEmptyOrBlank(pageID) == true) {
			throw new IllegalArgumentException("page id cannot be null");
		}
		MALPage[] pages = this.viewsConfig.getAccessiblePage();
		for (MALPage page : pages) {
			if (page.getId().equals(pageID) == true) {
				currentPage = page;
				return;
			}
		}
		throw new IllegalArgumentException("could not find a page with id as " + pageID + " in " + viewsConfig);
	}

	public MALPage getCurrentPage() {
		return currentPage;
	}

	public MALPage getPageByID(String pageID) {
		if (StringUtil.isEmptyOrBlank(pageID) == true) {
			throw new IllegalArgumentException("page id cannot be null or blank");
		}
		for (MALPage page : viewsConfig.getAccessiblePage()) {
			if (page.getId().equals(pageID) == true) {
				return page;
			}
		}
		return null;
	}

	public MALPage[] getPagesByType(String pageType) {
		List<MALPage> pages = new ArrayList<MALPage>();
		for (MALPage page : viewsConfig.getAccessiblePage()) {
			if (page.getDefinitionType().equals(pageType) == true) {
				pages.add(page);
			}
		}
		return pages.toArray(new MALPage[pages.size()]);
	}

	public MALPage getPageByType(String pageType){
		MALPage[] pages = getPagesByType(pageType);
		if (pages.length > 0){
			return pages[0];
		}
		return null;
	}

	public MALPanel getPanelInPage(MALPage page, String panelID) {
		if (page == null) {
			throw new IllegalArgumentException("page cannot be null");
		}
		if (StringUtil.isEmptyOrBlank(panelID) == true) {
			throw new IllegalArgumentException("panel id cannot be null or blank");
		}
		for (MALPartition partition : page.getPartition()) {
			for (MALPanel panel : partition.getPanel()) {
				if (panel.getId().equals(panelID) == true) {
					return panel;
				}
			}
		}
		return null;
	}

	protected void indexComponents() {
		for (MALPage page : viewsConfig.getAccessiblePage()) {
			for (MALPartition partition : page.getPartition()) {
				for (MALPanel panel : partition.getPanel()) {
					for (MALComponent component : panel.getComponent()) {
						addComponentToIndex(component);
					}
				}
			}
		}
	}

	protected void computeDefaultDimensionUnits() {
		for (MALPage page : viewsConfig.getAccessiblePage()) {
			for (MALPartition partition : page.getPartition()) {
				for (MALPanel panel : partition.getPanel()) {
					if (panel.getLayout() instanceof MALFlowLayout) {
						MALFlowLayout flowLayout = (MALFlowLayout) panel.getLayout();
						defaultComponentHeightUnit = flowLayout.getComponentHeight();
						defaultComponentWidthUnit = flowLayout.getComponentWidth();
						return;
					}
				}
			}
		}
	}

	public void addTransientComponent(MALComponent component){
		if (getComponentById(component.getId()) != null) {
			throw new IllegalArgumentException(component+" already exists in "+viewsConfig);
		}
		addComponentToIndex(component);
	}

	protected void addComponentToIndex(MALComponent component) {
		if (component == null) {
			throw new IllegalArgumentException("component cannot be null");
		}
		String id = component.getId();
		ComponentUsageTracker tracker = compByIdMap.get(id);
		if (tracker == null) {
			String name = component.getName();
			if (compByNameMap.containsKey(name) == true) {
				throw new IllegalArgumentException("Duplicate name detected between " + component + " and " + compByNameMap.get(name));
			}
			compByNameMap.put(name, component);
			tracker = new ComponentUsageTracker();
			tracker.component = component;
			tracker.usageCounter = 0;
			compByIdMap.put(id, tracker);
			Set<MALComponent> comps = compsByTypeMap.get(component.getDefinitionType());
			if (comps == null) {
				comps = new HashSet<MALComponent>();
				compsByTypeMap.put(component.getDefinitionType(), comps);
			}
			comps.add(component);
			try {
				evalutorsIndex.indexEvaluators(component);
			} catch (PluginException e) {
				MessageGeneratorArgs args = new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), e, component.toString());
				MESSAGE_GENERATOR.getMessage("viewconfig.helper.alertindexing.failure", args);
				EXCEPTION_HANDLER.handleException(e);
			}
			if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
				LOGGER.log(Level.DEBUG, "Indexing " + component + " under " + viewsConfig + " for " + token);
			}
		} else {
			if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
				LOGGER.log(Level.DEBUG, "Tracking existing " + component + " under " + viewsConfig + " for " + token);
			}
		}
		tracker.usageCounter++;
		lastModifiedTime = System.currentTimeMillis();
	}

	protected boolean removeComponentFromIndex(MALComponent component) {
		if (component == null) {
			throw new IllegalArgumentException("component cannot be null");
		}
		String id = component.getId();
		ComponentUsageTracker tracker = compByIdMap.get(id);
		if (tracker != null) {
			tracker.usageCounter--;
			if (tracker.usageCounter == 0) {
				if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
					LOGGER.log(Level.DEBUG, "Deindexing " + component + " under " + viewsConfig + " for " + token);
				}
				compByIdMap.remove(id);
				compByNameMap.remove(component.getName());
				Set<MALComponent> comps = compsByTypeMap.get(component.getDefinitionType());
				comps.remove(component);
				evalutorsIndex.deindexEvaluators(component);
				lastModifiedTime = System.currentTimeMillis();
				return true;
			}
		} else {
			if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
				LOGGER.log(Level.DEBUG, "Detracking existing " + component + " under " + viewsConfig + " for " + token);
			}
		}
		return false;
	}

	protected void replaceComponentInIndex(MALComponent component, MALComponent replacement) {
		if (component == null) {
			throw new IllegalArgumentException("component cannot be null");
		}
		if (replacement == null) {
			throw new IllegalArgumentException("replacement cannot be null");
		}
		if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
			LOGGER.log(Level.DEBUG, "Replacing existing " + component + " with new " + replacement + " under " + viewsConfig + " for " + token);
		}
		// check if the old component and new component have the same id
		if (component.getId().equals(replacement.getId()) == true) {
			// this is just a reference replacement
			ComponentUsageTracker tracker = compByIdMap.get(component.getId());
			evalutorsIndex.deindexEvaluators(tracker.component);
			tracker.component = replacement;
			try {
				evalutorsIndex.indexEvaluators(component);
			} catch (PluginException e) {
				MessageGeneratorArgs args = new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), e, component.toString());
				MESSAGE_GENERATOR.getMessage("viewconfig.helper.alertindexing.failure", args);
				EXCEPTION_HANDLER.handleException(e);
			}
		} else {
			// this is a brand new component
			int existingUsageCount = compByIdMap.get(component.getId()).usageCounter;
			compByIdMap.get(component.getId()).usageCounter = 1;
			removeComponentFromIndex(component);
			addComponentToIndex(replacement);
			compByIdMap.get(replacement.getId()).usageCounter = existingUsageCount;
		}
	}

	public MALComponent getComponentById(String componentID) {
		ComponentUsageTracker tracker = compByIdMap.get(componentID);
		if (tracker != null) {
			return tracker.component;
		}
		return null;
	}

	public MALComponent getComponentByName(String componentName) {
		return compByNameMap.get(componentName);
	}

	public Collection<MALComponent> getComponentsByType(String componentType) {
		if (compsByTypeMap.containsKey(componentType) == false) {
			return Collections.emptyList();
		}
		return compsByTypeMap.get(componentType);
	}

	public int getComponentIndexInPanel(MALPanel panel, String componentid) {
		MALComponent[] components = panel.getComponent();
		for (int i = 0; i < components.length; i++) {
			MALComponent component = components[i];
			if (component.getId().equals(componentid) == true) {
				return i;
			}
		}
		return -1;
	}

	public MALComponent searchComponentIn(MALPage page, String componentid) {
		for (MALPartition partition : page.getPartition()) {
			for (MALPanel panel : partition.getPanel()) {
				for (MALComponent component : panel.getComponent()) {
					if (component.getId().equals(componentid) == true) {
						return component;
					}
				}
			}
		}
		return null;
	}

	public List<MALPanel> getPanelsContaining(MALPage page, MALComponent component) {
		List<MALPanel> panels = new LinkedList<MALPanel>();
		for (MALPartition partition : page.getPartition()) {
			for (MALPanel panel : partition.getPanel()) {
				for (MALComponent componentInPanel : panel.getComponent()) {
					if (componentInPanel.getId().equals(component.getId()) == true) {
						panels.add(panel);
						continue;
					}
				}
			}
		}
		return panels;
	}

	public List<MALPanel> getPanelsContaining(MALComponent component) {
		List<MALPanel> panels = new LinkedList<MALPanel>();
		for (MALPage page : viewsConfig.getAccessiblePage()) {
			for (MALPartition partition : page.getPartition()) {
				for (MALPanel panel : partition.getPanel()) {
					for (MALComponent componentInPanel : panel.getComponent()) {
						if (componentInPanel.getId().equals(component.getId()) == true) {
							panels.add(panel);
							continue;
						}
					}
				}
			}
		}
		return panels;
	}

	public int getComponentHeightUnit(String componentType) {
		Collection<MALComponent> components = getComponentsByType(componentType);
		if (components.isEmpty() == false) {
			MALComponent component = components.iterator().next();
			List<MALPanel> panels = getPanelsContaining(component);
			if (panels.isEmpty() == false) {
				for (MALPanel panel : panels) {
					if (panel.getLayout() instanceof MALFlowLayout) {
						return ((MALFlowLayout) panel.getLayout()).getComponentHeight();
					}
				}
			}
		}
		return defaultComponentHeightUnit;
	}

	public int getComponentWidthUnit(String componentType) {
		Collection<MALComponent> components = getComponentsByType(componentType);
		if (components.isEmpty() == false) {
			MALComponent component = components.iterator().next();
			List<MALPanel> panels = getPanelsContaining(component);
			if (panels.isEmpty() == false) {
				for (MALPanel panel : panels) {
					if (panel.getLayout() instanceof MALFlowLayout) {
						return ((MALFlowLayout) panel.getLayout()).getComponentWidth();
					}
				}
			}
		}
		return defaultComponentWidthUnit;
	}

	public MALSkin getInsightSkin() {
		return skinIndexer.getInsightSkin();
	}

	public MALComponentColorSet getComponentColorSet(MALComponent component, int componentColorSetIndex) {
		return skinIndexer.getColorSet(component, componentColorSetIndex);
	}

	public void addElementChangeListener(ElementChangeListener listener) {
		if (listeners.contains(listener) == false) {
			listeners.add(listener);
		}
	}

	public void removeElementChangeListener(ElementChangeListener listener) {
		listeners.remove(listener);
	}

	public void firePrepareForChange(MALElement element) {
		if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
			LOGGER.log(Level.DEBUG, "Firing prepare for change event for " + element + " under " + viewsConfig + " for " + token);
		}
		for (ElementChangeListener listener : listeners) {
			listener.prepareForChange(element);
		}
	}

	public void firePreOp(String parentPath, MALElement child, MALElement replacement, ElementChangeListener.OPERATION operation) {
		if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
			LOGGER.log(Level.DEBUG, "Firing pre " + operation + " event for " + child + " with replacement as " + replacement + " under " + viewsConfig + "/" + parentPath + " for " + token);
		}
		if (operation == ElementChangeListener.OPERATION.REPLACE) {
			if (replacement == null) {
				throw new IllegalArgumentException("Replacement cannot be null for " + operation);
			}
			// check if child and replacement are same
			if (child.getDefinitionType().equals(replacement.getDefinitionType()) == false) {
				// no check if replacement is sub class of child
				if (child.getClass().isAssignableFrom(replacement.getClass()) == false) {
					// no check if child and replacement share the same base
					// class
					if (child.getClass().getSuperclass().isAssignableFrom(replacement.getClass()) == false) {
						// no bail out
						throw new IllegalArgumentException(replacement + " is not a valid replacement for " + child);
					}
				}
			}
		}
		for (ElementChangeListener listener : listeners) {
			listener.preOp(parentPath, child, replacement, operation);
		}
	}

	public void firePostOp(String parentPath, MALElement child, MALElement replacement, ElementChangeListener.OPERATION operation) {
		if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
			LOGGER.log(Level.DEBUG, "Firing post " + operation + " event for " + child + " with replacement as " + replacement + " under " + viewsConfig + "/" + parentPath + " for " + token);
		}
		if (operation == ElementChangeListener.OPERATION.REPLACE) {
			if (replacement == null) {
				throw new IllegalArgumentException("Replacement cannot be null for " + operation);
			}
			// check if child and replacement are same
			if (child.getDefinitionType().equals(replacement.getDefinitionType()) == false) {
				// no check if replacement is sub class of child
				if (child.getClass().isAssignableFrom(replacement.getClass()) == false) {
					// no check if child and replacement share the same base
					// class
					if (child.getClass().getSuperclass().isAssignableFrom(replacement.getClass()) == false) {
						// no bail out
						throw new IllegalArgumentException(replacement + " is not a valid replacement for " + child);
					}
				}
			}
		}
		for (ElementChangeListener listener : listeners) {
			listener.postOp(parentPath, child, replacement, operation);
		}
	}

	public void fireChangeCompleted(MALElement element) {
		if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
			LOGGER.log(Level.DEBUG, "Firing change complete event for " + element + " under " + viewsConfig + " for " + token);
		}
		for (ElementChangeListener listener : listeners) {
			listener.changeComplete(element);
		}
	}

	public void fireChangeAborted(MALElement element) {
		if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
			LOGGER.log(Level.DEBUG, "Firing change aborted event for " + element + " under " + viewsConfig + " for " + token);
		}
		for (ElementChangeListener listener : listeners) {
			listener.changeAborted(element);
		}
	}

	public long getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void destroy() {
		removeElementChangeListener(internalListener);
		internalListener = null;
		this.listeners.clear();
		this.compByIdMap.clear();
		this.compByNameMap.clear();
		this.compsByTypeMap.clear();
		this.currentPage = null;
		this.skinIndexer = null;
		this.unmodifiablePageList = null;
		this.viewsConfig = null;
	}

	class ComponentUsageTracker {
		MALComponent component;
		int usageCounter;
	}

}
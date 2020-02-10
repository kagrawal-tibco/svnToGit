package com.tibco.cep.dashboard.plugin.beviews;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.tibco.cep.dashboard.psvr.mal.ElementChangeListener.OPERATION;
import com.tibco.cep.dashboard.psvr.mal.MALComponentGallery;
import com.tibco.cep.dashboard.psvr.mal.MALElementsCollector;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALSession;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.managers.MALChartComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALPageSelectorComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALPageVisualizationManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALPanelManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALPartitionManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALQueryManagerComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALRelatedPageManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchPageManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchViewComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALTextComponentManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALActionRule;
import com.tibco.cep.dashboard.psvr.mal.model.MALBEViewsElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALCategoryGuidelineConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALChartComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALDataSource;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPageSelectorComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPageVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.mal.model.MALQueryManagerComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALRelatedPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALSearchPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALSearchViewComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.MALView;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.mal.model.types.DisplayModeEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.PanelStateEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.PartitionStateEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.ScrollBarConfigEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.SortEnum;
import com.tibco.cep.dashboard.psvr.plugin.Builder;
import com.tibco.cep.dashboard.psvr.plugin.BuilderResult;
import com.tibco.cep.dashboard.psvr.plugin.BuilderResult.SEVERITY;
import com.tibco.cep.dashboard.psvr.plugin.PlugIn;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;

//PORT cross check logic
public class BEViewsBuilder extends Builder {

	private Set<String> warningsIssuedComponentIds;

	BEViewsBuilder(PlugIn plugIn) {
		super(plugIn);
		warningsIssuedComponentIds = new HashSet<String>();
	}

	@Override
	public List<BuilderResult> build(TokenRoleProfile profile) throws MALException {
		List<BuilderResult> results = new ArrayList<BuilderResult>();
		ViewsConfigHelper viewsConfigHelper = profile.getViewsConfigHelper();
		//build page set selector component
		results.addAll(updatePageSetSelectorComponents(profile.getMALSession(), viewsConfigHelper));
		//scan and build category axis sort for all components in the profile
		if ((Boolean)BEViewsProperties.AUTO_ADJUST_CATEGORY_AXIS_SORT.getValue(plugIn.getProperties()) == true) {
			results.addAll(scanAndBuildCategoryAxisSort(viewsConfigHelper,profile.getComponentGallery()));
		}
		//add search/drill-down page if needed
		results.addAll(scanAndAddSearchPage(profile.getMALSession(),viewsConfigHelper));
		//add related view page if needed
		results.addAll(scanAndAddRelatedPage(profile.getMALSession(),viewsConfigHelper));
		//scan and add orphan related elements
		results.addAll(scanAndAddRelatedCharts(profile));
		return results;
	}

	private List<BuilderResult> updatePageSetSelectorComponents(MALSession session, ViewsConfigHelper viewsConfigHelper) throws MALException {
		List<BuilderResult> results = new LinkedList<BuilderResult>();
		MALView viewsConfig = viewsConfigHelper.getViewsConfig();
		//isolate all the pages by presence of the psc
		MALPage[] pages = viewsConfig.getAccessiblePage();
		//all pages containing psscs
		List<MALPage> pagesContainingPSSC = new LinkedList<MALPage>();
		//all pages NOT containing psscs
		List<MALPage> pagesNotContainingPSSC = new LinkedList<MALPage>();
		//existing psscs
		Set<MALPageSelectorComponent> existingPSCs = new HashSet<MALPageSelectorComponent>();
		for (MALPage page : pages) {
			boolean found = false;
			for (MALPartition partition : page.getPartition()) {
				for (MALPanel panel : partition.getPanel()) {
					for (MALComponent component : panel.getComponent()) {
						if (component instanceof MALPageSelectorComponent){
							found = true;
							existingPSCs.add((MALPageSelectorComponent) component);
							break;
						}
					}
				}
			}
			if (found == false){
				pagesNotContainingPSSC.add(page);
			}
			else {
				pagesContainingPSSC.add(page);
			}
		}
		//if there are no existing PSCs and there is only one page, then skip
		if (existingPSCs.isEmpty() == true && pages.length == 1){
			return results;
		}
		//we either have existing psc's or more then one page
		String viewsConfigURI = URIHelper.getURI(viewsConfig);
		//check if any psc's exist in the views config
		if (existingPSCs.isEmpty() == true){
			//we are dealing with non existent psc
			//check if there is one in the entire system globally
			List<MALElement> globalPageSetSelectorComponents = session.getElementsByType(MALPageSelectorComponentManager.DEFINITION_TYPE);
			viewsConfigHelper.firePrepareForChange(viewsConfig);
			viewsConfigHelper.firePreOp(URIHelper.getURI(viewsConfig), null, null, OPERATION.ADD);
			if (globalPageSetSelectorComponents != null && globalPageSetSelectorComponents.isEmpty() == false){
				//we found a global version, we will reuse that
				MALPageSelectorComponent globalPSSC = (MALPageSelectorComponent) globalPageSetSelectorComponents.get(0);
				existingPSCs.add(globalPSSC);
				results.add(new BuilderResult(SEVERITY.INFO,"Reusing \""+URIHelper.getURI(globalPSSC)+"\" as the template page selector component in "+viewsConfigURI,viewsConfig));
			}
			else {
				//we don't have a page set selector component , we need to create a new psc
				MALPageSelectorComponent newPSSC = (MALPageSelectorComponent) session.getManager(MALPageSelectorComponentManager.DEFINITION_TYPE).create(null, "PSC_Runtime_Template_Component");
				newPSSC.setDisplayName("Page Selector");
				newPSSC.setAutoGenerated(true);
				existingPSCs.add(newPSSC);
				results.add(new BuilderResult(SEVERITY.INFO,"Created \""+URIHelper.getURI(newPSSC)+"\" as the template page selector component in "+viewsConfigURI,viewsConfig));
			}
			viewsConfigHelper.firePostOp(URIHelper.getURI(viewsConfig), existingPSCs.iterator().next(), null, OPERATION.ADD);
			viewsConfigHelper.fireChangeCompleted(viewsConfig);
		}
		else {
			//we have existing psc's , to keep them intact we will clone them
			Set<MALPageSelectorComponent> clonedPSSCs = new HashSet<MALPageSelectorComponent>();
			for (MALPageSelectorComponent pageSelectorComponent : existingPSCs) {
				clonedPSSCs.add((MALPageSelectorComponent) session.getManager(MALPageSelectorComponentManager.DEFINITION_TYPE).clone(pageSelectorComponent, new MALElementsCollector()));
			}
			existingPSCs = clonedPSSCs;
		}
		//we will add page visualizations to each cloned psc
		for (MALPageSelectorComponent existingPSSC : existingPSCs) {
			addPageVisualizationsTo(session, existingPSSC, pages);
			results.add(new BuilderResult(SEVERITY.INFO,"Added "+pages.length+" page visualizations to the template page selector component \""+URIHelper.getURI(existingPSSC)+"\" in "+viewsConfigURI,viewsConfig));
		}
		//replace the existing psc with the cloned psc in pages containing existing psc
		for (MALPage page : pagesContainingPSSC) {
			for (MALPageSelectorComponent clonedPSSC : existingPSCs) {
				List<MALPanel> panels = viewsConfigHelper.getPanelsContaining(page, clonedPSSC);
				if (panels.isEmpty() == false){
					for (MALPanel panel : panels) {
						//we need to do an index based replacement since we are replacing original psc with a cloned psc
						MALComponent originalPSSC = panel.getComponent(0);
						//fire panel change preparation event
						viewsConfigHelper.firePrepareForChange(panel);
						//fire component replacement pre-event
						viewsConfigHelper.firePreOp(URIHelper.getURI(panel), originalPSSC, clonedPSSC, OPERATION.REPLACE);
						//remove component from panel
						panel.removeComponent(originalPSSC);
						//remove the reference to the panel in original component
						originalPSSC.removeReference(panel);
						//add new component to the panel
						panel.addComponent(0,clonedPSSC);
						//we do not add panel as reference to the new component, since we have copied over the reference set during cloning
						//existingPSSC.addReference(panel);
						//fire component replacement post-event
						viewsConfigHelper.firePostOp(URIHelper.getURI(panel), originalPSSC, clonedPSSC, OPERATION.REPLACE);
						//fire panel change completion event
						viewsConfigHelper.fireChangeCompleted(panel);
					}
				}
			}
		}
		if (pagesNotContainingPSSC.isEmpty() == false) {
			MALPageSelectorComponent idealPSSC = existingPSCs.iterator().next();
			//add one existing psc to the pages not containing any psc's
			for (MALPage page : pagesNotContainingPSSC) {
				MALPartition partition = (MALPartition) session.getManager(MALPartitionManager.DEFINITION_TYPE).create(page, "PSC_Runtime_Partition");
				partition.setDisplayName("Dashboard Switcher Partition");
				//INFO Default Builder will adjust the spans for us
				partition.setSpan("1%");
				partition.setState(PartitionStateEnum.OPEN);
				partition.setVertical(true);
				partition.setAutoGenerated(true);
				page.addPartition(0, partition);

				//create a panel and add it to the partition
				MALPanel panel = (MALPanel) session.getManager(MALPanelManager.DEFINITION_TYPE).create(partition, "PSC_Runtime_Panel");
				panel.setDisplayName("Dashboard Switcher");
				panel.setSpan("100%");
				panel.setMaximizable(false);
				panel.setMinimizable(false);
				panel.setScrollBar(ScrollBarConfigEnum.AUTO);
				panel.setState(PanelStateEnum.NORMAL);
				panel.setAutoGenerated(true);
				//add panel to partition
				partition.addPanel(panel);

				//add component to the panel
				panel.addComponent(idealPSSC);
				idealPSSC.addReference(panel);

				results.add(new BuilderResult(SEVERITY.INFO, "Updated " + URIHelper.getURI(page, true, viewsConfig.getName())[0] + " to add the template pageset selector component \"" + URIHelper.getURI(idealPSSC) + "\" in "
						+ viewsConfigURI, viewsConfig));
			}
		}
		results.add(new BuilderResult(SEVERITY.INFO, "Updated " + existingPSCs.size() + " page selector component(s) to access " + pages.length + " page(s) in "+viewsConfigURI, viewsConfig));
		return results;
	}

	private void addPageVisualizationsTo(MALSession session,MALPageSelectorComponent pageSetSelectorComponent, MALPage[] accessiblePages) throws MALException{
		MALPageVisualizationManager pagesetVizMgr = (MALPageVisualizationManager) session.getManager(MALPageVisualizationManager.DEFINITION_TYPE);
		for (MALPage accessiblePage : accessiblePages) {
			MALPageVisualization visualization = accessiblePage.getVisualization();
			if (visualization == null) {
				visualization = (MALPageVisualization) pagesetVizMgr.create(pageSetSelectorComponent,accessiblePage.getName() + "_runtime_template_visualization");
				visualization.setAutoGenerated(true);
			}
			else {
				visualization = (MALPageVisualization) pagesetVizMgr.clone(visualization, new MALElementsCollector());
			}
			pageSetSelectorComponent.addVisualization(visualization);
			//INFO Replacing MALPageSet as parent of MALPageVisualization with MALPageSelectorComponent
			visualization.setParent(pageSetSelectorComponent);
			//INFO Adding MALPageSet as related element to new/cloned MALPageVisualization instead of it being the parent (as in the persisted state)
			visualization.addRelatedElement(accessiblePage);
		}
	}

	private List<BuilderResult> scanAndBuildCategoryAxisSort(ViewsConfigHelper viewsConfigHelper, MALComponentGallery componentGallery){
		LinkedList<BuilderResult> results = new LinkedList<BuilderResult>();
		Collection<MALComponent> components = viewsConfigHelper.getComponentsByType(MALChartComponentManager.DEFINITION_TYPE);
		List<String> ids = new LinkedList<String>();
		for (MALComponent component : components) {
			//build category axis sort for chart component
			BuilderResult result = buildCategoryAxisSort(component);
			if (result != null){
				String id = component.getId();
				if (warningsIssuedComponentIds.contains(id) == false){
					results.add(result);
					warningsIssuedComponentIds.add(id);
				}
				ids.add(id);
			}
		}
		components = viewsConfigHelper.getComponentsByType(MALTextComponentManager.DEFINITION_TYPE);
		for (MALComponent component : components) {
			//build category axis sort for text component
			BuilderResult result = buildCategoryAxisSort(component);
			if (result != null) {
				String id = component.getId();
				if (warningsIssuedComponentIds.contains(id) == false) {
					results.add(result);
					warningsIssuedComponentIds.add(id);
				}
				ids.add(id);
			}
		}
		Iterator<String> componentIDs = componentGallery.getComponentIDs();
		while (componentIDs.hasNext()) {
			String componentID = (String) componentIDs.next();
			if (ids.contains(componentID) == false){
				MALComponent component = componentGallery.searchComponent(componentID);
				//build category axis sort for chart/text component in the gallery
				BuilderResult result = buildCategoryAxisSort(component);
				if (result != null) {
					if (warningsIssuedComponentIds.contains(componentID) == false) {
						results.add(result);
						warningsIssuedComponentIds.add(componentID);
					}
					ids.add(componentID);
				}
			}
		}
		return results;
	}

	private BuilderResult buildCategoryAxisSort(MALComponent component){
		BuilderResult builderResult = null;
		MALCategoryGuidelineConfig categoryGuideLineConfig = getCategoryGuideLineConfig(component);
		if (categoryGuideLineConfig == null) {
			builderResult = new BuilderResult(SEVERITY.WARNING, component + " does not have category axis config, will default category axis ordering to '" + SortEnum.UNSORTED + "'", component);
		} else {
			SortEnum sortEnum = categoryGuideLineConfig.getSortOrder();
			if (sortEnum == null || sortEnum.getType() == SortEnum.UNSORTED_TYPE) {
				MALVisualization[] visualizations = component.getVisualization();
				for (int i = 0; i < visualizations.length; i++) {
					MALVisualization visualization = visualizations[i];
					MALSeriesConfig[] seriesConfigs = visualization.getSeriesConfig();
					for (int j = 0; j < seriesConfigs.length; j++) {
						MALSeriesConfig seriesConfig = seriesConfigs[j];
						if (dataSourceHasOrderByCond(seriesConfig) == false) {
							if (sortEnum == null) {
								builderResult = new BuilderResult(SEVERITY.INFO, component
										+ " does not have datasource(s) with order by clause as well as no category axis ordering, defaulting category axis ordering to '" + SortEnum.ASCENDING + "'",
										component);
							} else {
								builderResult = new BuilderResult(SEVERITY.INFO, component + " does not have datasource(s) with order by clause but has '" + SortEnum.UNSORTED
										+ "' category axis ordering, defaulting category axis ordering to '" + SortEnum.ASCENDING + "'", component);
							}
							sortEnum = SortEnum.ASCENDING;
							break;
						}
					}
				}
				categoryGuideLineConfig.setSortOrder(sortEnum);
			}
		}
		return builderResult;
	}

	private MALCategoryGuidelineConfig getCategoryGuideLineConfig(MALComponent component) {
		MALCategoryGuidelineConfig categoryGuideLineConfig = null;
		if ((component.getDefinitionType().equals("ChartComponent") == true) || (component.getDefinitionType().equals("TrendChartComponent") == true)) {
			categoryGuideLineConfig = ((MALChartComponent) component).getCategoryGuidelineConfig();
		} else if (component.getDefinitionType().equals("TextComponent") == true) {
			MALTextComponent textComponent = (MALTextComponent) component;
			categoryGuideLineConfig = ((MALTextVisualization) textComponent.getVisualization(0)).getCategoryGuidelineConfig();
		}
		return categoryGuideLineConfig;
	}

	private boolean dataSourceHasOrderByCond(MALSeriesConfig seriesConfig) {
		MALActionRule actionRule = seriesConfig.getActionRule();
		if (actionRule != null){
			MALDataSource dataSource = actionRule.getDataSource();
			if (dataSource != null){
				String query = dataSource.getQuery();
				//INFO assuming we can say finding 'order by' in the query string as == query having order by
				if (query.indexOf("order by") != -1){
					return true;
				}
			}
		}
		return false;
	}

	private List<BuilderResult> scanAndAddSearchPage(MALSession session,ViewsConfigHelper viewsConfigHelper){
		MALSearchPage searchPage = null;
		LinkedList<BuilderResult> results = new LinkedList<BuilderResult>();
		if (viewsConfigHelper.getPageByType(MALSearchPageManager.DEFINITION_TYPE) == null){
			BuilderResult result = null;
			MALView viewsConfig = viewsConfigHelper.getViewsConfig();
			try {
				if (searchPage == null){
					searchPage = createSearchPage(session);
				}
				searchPage.addReference(viewsConfig);
				viewsConfig.addAccessiblePage(searchPage);
				result = new BuilderResult(SEVERITY.INFO,"Added a drilldown page to "+viewsConfig,viewsConfig);
			} catch (MALException e) {
				result = new BuilderResult(SEVERITY.WARNING,"Could not create a drilldown page in "+viewsConfig,viewsConfig);
				e.printStackTrace();
			}
			results.add(result);
		}
		return results;
	}

	private MALSearchPage createSearchPage(MALSession session) throws MALException{
		boolean addQueryManager = (Boolean) BEViewsProperties.QUERY_MANAGER_ENABLE.getValue(plugIn.getProperties());
		int partitionSpan = addQueryManager == true ? 80 : 100;
		MALSearchPage searchPage = (MALSearchPage) session.getManager(MALSearchPageManager.DEFINITION_TYPE).create(null,"DrillDownPage");
		searchPage.setDisplayMode(DisplayModeEnum.MODALDIALOG);
		searchPage.setDisplayName("Drilldown Page");
		searchPage.setOwner(session.getIdentity());
		searchPage.setAutoGenerated(true);
		if (addQueryManager == true){
			//query manager partition
			MALPartition partition = (MALPartition) session.getManager(MALPartitionManager.DEFINITION_TYPE).create(searchPage,"querymgrpartition");
			partition.setDisplayName("Partition");
			partition.setSpan((100-partitionSpan)+"%");
			partition.setState(PartitionStateEnum.OPEN);
			partition.setVertical(true);
			partition.setAutoGenerated(true);
			//add partition to search page
			searchPage.addPartition(partition);
			//query manager panel
			MALPanel panel = (MALPanel) session.getManager(MALPanelManager.DEFINITION_TYPE).create(partition,"querymgrpanel");
			panel.setDisplayName("Query Manager");
			panel.setSpan("100%");
			panel.setMaximizable(false);
			panel.setMinimizable(false);
			panel.setScrollBar(ScrollBarConfigEnum.AUTO);
			panel.setState(PanelStateEnum.NORMAL);
			panel.setAutoGenerated(true);
			//add query manager panel to query manager partition
			partition.addPanel(panel);
			//query manager component
			MALQueryManagerComponent queryMgrComponent = (MALQueryManagerComponent) session.getManager(MALQueryManagerComponentManager.DEFINITION_TYPE).create(null, "querymgrcomponent");
			queryMgrComponent.setTopLevelElement(true);
			queryMgrComponent.setAutoGenerated(true);
			//add query manger component to search view panel
			panel.addComponent(queryMgrComponent);
			queryMgrComponent.addReference(panel);
		}
		//search view partition
		MALPartition partition = (MALPartition) session.getManager(MALPartitionManager.DEFINITION_TYPE).create(searchPage,"searchviewpartition");
		partition.setDisplayName("Partition");
		partition.setSpan(partitionSpan+"%");
		partition.setState(PartitionStateEnum.OPEN);
		partition.setVertical(true);
		partition.setAutoGenerated(true);
		//add partition to search page
		searchPage.addPartition(partition);
		//search view panel
		MALPanel panel = (MALPanel) session.getManager(MALPanelManager.DEFINITION_TYPE).create(partition,"searchviewpanel");
		panel.setDisplayName("Drilldown Results");
		panel.setSpan("100%");
		panel.setMaximizable(false);
		panel.setMinimizable(false);
		panel.setScrollBar(ScrollBarConfigEnum.AUTO);
		panel.setState(PanelStateEnum.NORMAL);
		panel.setAutoGenerated(true);
		//add search view panel to search view partition
		partition.addPanel(panel);
		//search view component
		MALSearchViewComponent drillDownComponent = (MALSearchViewComponent) session.getManager(MALSearchViewComponentManager.DEFINITION_TYPE).create(null,"searchviewcomponent");
		drillDownComponent.setTopLevelElement(true);
		drillDownComponent.setAutoGenerated(true);
		//add search view component to search view panel
		panel.addComponent(drillDownComponent);
		drillDownComponent.addReference(panel);
		return searchPage;
	}

	private List<BuilderResult> scanAndAddRelatedPage(MALSession session,ViewsConfigHelper viewsConfigHelper){
		MALRelatedPage relatedAssetsPage = null;
		LinkedList<BuilderResult> results = new LinkedList<BuilderResult>();
		if (viewsConfigHelper.getPageByType(MALRelatedPageManager.DEFINITION_TYPE) == null){
			BuilderResult result = null;
			MALView viewsConfig = viewsConfigHelper.getViewsConfig();
			try {
				if (relatedAssetsPage == null){
					relatedAssetsPage = createRelatedPage(session);
				}
				relatedAssetsPage.addReference(viewsConfig);
				viewsConfig.addAccessiblePage(relatedAssetsPage);
				result = new BuilderResult(SEVERITY.INFO,"Added a related assets page to "+viewsConfig,viewsConfig);
			} catch (MALException e) {
				result = new BuilderResult(SEVERITY.WARNING,"Could not create a related assets page in "+viewsConfig,viewsConfig);
				e.printStackTrace();
			}
			results.add(result);
		}
		return results;
	}

	private MALRelatedPage createRelatedPage(MALSession session) throws MALException {
		MALRelatedPage relatedAssetsPage = (MALRelatedPage) session.getManager(MALRelatedPageManager.DEFINITION_TYPE).create(null,"RelatedAssetsPage");
		relatedAssetsPage.setDisplayMode(DisplayModeEnum.MODALDIALOG);
		relatedAssetsPage.setDisplayName("Related Page");
		relatedAssetsPage.setOwner(session.getIdentity());
		relatedAssetsPage.setAutoGenerated(true);
		//single partition
		MALPartition partition = (MALPartition) session.getManager(MALPartitionManager.DEFINITION_TYPE).create(relatedAssetsPage,"relatedviewpartition");
		partition.setDisplayName("Partition");
		partition.setSpan("100%");
		partition.setState(PartitionStateEnum.OPEN);
		partition.setVertical(true);
		partition.setAutoGenerated(true);
		//add partition to page
		relatedAssetsPage.addPartition(partition);
		//search view panel
		MALPanel panel = (MALPanel) session.getManager(MALPanelManager.DEFINITION_TYPE).create(partition,"relatedviewpanel");
		panel.setDisplayName("Related Assets");
		panel.setSpan("100%");
		panel.setMaximizable(false);
		panel.setMinimizable(false);
		panel.setScrollBar(ScrollBarConfigEnum.AUTO);
		panel.setState(PanelStateEnum.NORMAL);
		panel.setAutoGenerated(true);
		//add to partition
		partition.addPanel(panel);
		//related component
		MALSearchViewComponent drillDownComponent = (MALSearchViewComponent) session.getManager(MALSearchViewComponentManager.DEFINITION_TYPE).create(null,"relatedviewcomponent");
		drillDownComponent.setTopLevelElement(true);
		drillDownComponent.setAutoGenerated(true);
		//add search view component to search view panel
		panel.addComponent(drillDownComponent);
		drillDownComponent.addReference(panel);
		return relatedAssetsPage;
	}

	private List<BuilderResult> scanAndAddRelatedCharts(TokenRoleProfile profile) {
		ViewsConfigHelper viewsConfigHelper = profile.getViewsConfigHelper();
		MALComponentGallery componentGallery = profile.getComponentGallery();
		LinkedList<BuilderResult> results = new LinkedList<BuilderResult>();
		LinkedList<MALComponent> components = new LinkedList<MALComponent>(viewsConfigHelper.getComponentsByType(MALChartComponentManager.DEFINITION_TYPE));
		for (MALComponent component : components) {
			MALBEViewsElement[] relatedElements = component.getRelatedElement();
			for (MALBEViewsElement relatedElement : relatedElements) {
				MALComponent relatedComponent = viewsConfigHelper.getComponentById(relatedElement.getId());
				if (relatedComponent == null){
					relatedComponent = componentGallery.searchComponent(relatedElement.getId());
					if (relatedComponent == null){
						viewsConfigHelper.addTransientComponent((MALComponent) relatedElement);
						results.add(new BuilderResult(SEVERITY.WARNING,relatedElement+" is missing in the profile for "+profile.getPreferredPrincipal()+", adding it...",relatedComponent));
					}
				}
			}
		}
		components = new LinkedList<MALComponent>(viewsConfigHelper.getComponentsByType(MALTextComponentManager.DEFINITION_TYPE));
		for (MALComponent component : components) {
			MALBEViewsElement[] relatedElements = component.getRelatedElement();
			for (MALBEViewsElement relatedElement : relatedElements) {
				MALComponent relatedComponent = viewsConfigHelper.getComponentById(relatedElement.getId());
				if (relatedComponent == null){
					relatedComponent = componentGallery.searchComponent(relatedElement.getId());
					if (relatedComponent == null){
						viewsConfigHelper.addTransientComponent((MALComponent) relatedElement);
						results.add(new BuilderResult(SEVERITY.WARNING,relatedComponent+" is missing in the profile for "+profile.getPreferredPrincipal()+", adding it...",relatedComponent));
					}
				}
			}
		}
		return results;
	}
}

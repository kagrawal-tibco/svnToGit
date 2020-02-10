package com.tibco.cep.studio.dashboard.ui.chartcomponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPreviewableComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSeriesConfig;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartSubType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartType;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.types.ChartTypeRegistry;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartEditingController extends BaseChartController {

	private ChartType originalChartType;

	private ControllablePage masterPage;

	private List<SlavePage> slavePages;

	private ControllablePage activePage;

//	private Map<String,PropertyValuePreserver> valuePreservers;

	public ChartEditingController() {
		this.slavePages = new LinkedList<SlavePage>();
//		valuePreservers = new HashMap<String, PropertyValuePreserver>();
	}

	public void setOriginalElement(LocalUnifiedComponent originalElement) throws Exception {
//		if (this.originalElement != null) {
//			LocalElementSubscriptionHelper.unsubscribeAll(this.originalElement, elementChangeListener);
//		}
//		this.originalElement = originalElement;
//		this.originalElementIdentifier = this.originalElement.getFolder()+this.originalElement.getName()+".chart";
//		this.originalElementGUID = originalElement.getID();
		super.setOriginalElement(originalElement);
		originalElement.setBulkOperation(true);
		try {
			//process element
			this.originalChartType = processElement(this.originalElement);
			//set the element in edit mode
			this.originalElement.setEditModeOn(true);
			//set the original element to be existing
			this.originalElement.setInternalStatus(InternalStatusEnum.StatusExisting, true);
//		elementChangeListener = new UnifiedComponentChangeListener();
//		//subscribe for all changes
//		LocalElementSubscriptionHelper.subscribeAll(this.originalElement, elementChangeListener);

			//update all slave pages
			for (SlavePage slavePage : slavePages) {
				updateTypeAndSubType(slavePage);
			}
		} finally {
			originalElement.setBulkOperation(false);
		}
	}

	private ChartType processElement(LocalUnifiedComponent unifiedComponent) throws Exception{
		// update chart type and sub type
		ChartType chartType = null;
		Collection<ChartType> knownTypes = ChartTypeRegistry.getInstance().getTypes();
		for (ChartType knownType : knownTypes) {
			if (knownType.getProcessor().isAcceptable(unifiedComponent) == true) {
				chartType = knownType;
				unifiedComponent.setPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE, knownType.getId());
				unifiedComponent.setPropertyValues(LocalUnifiedComponent.PROP_KEY_SUBTYPES, Arrays.asList(knownType.getProcessor().getSubTypes(unifiedComponent)));
				//allow the chart type processor to prepare the original element for editing
				//originalChartType.getProcessor().prepareForEditing(originalElement);
				break;
			}
		}
		if (chartType == null) {
			throw new Exception(unifiedComponent.getName() + " is an unknown chart type");
		}
		return chartType;
	}

	public ChartType getOriginalChartType() {
		return originalChartType;
	}

	public ChartType getCurrentChartType() throws Exception {
		return ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
	}

	public void setMasterPage(ControllablePage masterPage) {
		this.masterPage = masterPage;
		this.masterPage.setExceptionHandler(this);
	}

	public ControllablePage getMasterPage() {
		return masterPage;
	}

	public void addSlavePage(SlavePage page) throws Exception {
		slavePages.add(page);
		page.setExceptionHandler(this);
		if (originalElement != null) {
			updateTypeAndSubType(page);
		}
	}

	public void setActivePage(ControllablePage page){
		try {
			this.activePage = page;
			page.refresh();
		} catch (Exception e) {
			logAndAlert("Page Refresh", new Status(IStatus.ERROR,getPluginId(),"could not refresh page",e));
		}
	}

	public ControllablePage getActivePage() {
		return activePage;
	}

	private void updateTypeAndSubType(SlavePage page) throws Exception {
		ChartType chartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
		if (chartType.equals(page.getChartType()) == false) {
			page.setChartType(chartType);
		}
		updateSubType(page, chartType);
	}

	private void updateSubType(SlavePage page, ChartType chartType) throws Exception {
		SynProperty property = (SynProperty) originalElement.getProperty(LocalUnifiedComponent.PROP_KEY_SUBTYPES);
		List<String> subTypeIds = property.getValues();
		ChartSubType[] subTypes = new ChartSubType[subTypeIds.size()];
		for (int i = 0; i < subTypes.length; i++) {
			subTypes[i] = chartType.getSubType(subTypeIds.get(i));
		}
		page.setChartSubTypes(subTypes);
	}

	public List<SlavePage> getSlavePages() {
		return slavePages;
	}

	public void sync(LocalElement element) throws Exception {
		//create a new local unified element
		LocalUnifiedComponent newUnifiedComponent = new LocalUnifiedComponent(element.getParent(), (LocalPreviewableComponent) element);
		ChartType incomingChartType = processElement(newUnifiedComponent);
		ChartType existingChartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
		//check the type families
		if (incomingChartType.getFamily().equals(existingChartType.getFamily()) == false) {
			translate(newUnifiedComponent, incomingChartType, existingChartType);
		}
		originalElement.sync(newUnifiedComponent);
		//reset the original Element
	}

	protected void processElementAddition(IMessageProvider parent, IMessageProvider newElement) {
		try {
			if (newElement instanceof LocalSeriesConfig){
				ChartType currChartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
				currChartType.getProcessor().seriesAdded(originalElement, (LocalElement) newElement);
			}
		} catch (Exception e) {
			logAndAlert("Series Addition", new Status(IStatus.ERROR,getPluginId(),"could not add new series",e));
		}
	}

	protected void processElementRemoval(IMessageProvider parent, IMessageProvider removedElement) {
		try {
			if (removedElement instanceof LocalSeriesConfig){
				ChartType currChartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
				currChartType.getProcessor().seriesRemoved(originalElement, (LocalElement) removedElement);
			}
		} catch (Exception e) {
			logAndAlert("Series Removal", new Status(IStatus.ERROR,getPluginId(),"could not removing existing series",e));
		}
	}

	protected List<Status> processPropertyChange(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
		List<Status> statuses = new ArrayList<Status>();
		try {
			if (provider instanceof LocalUnifiedComponent && property.getName().equals(LocalUnifiedComponent.PROP_KEY_TYPE) == true) {
				//chart type has changed
				ChartType oldChartType = ChartTypeRegistry.getInstance().get(String.valueOf(oldValue));
				ChartType newChartType = ChartTypeRegistry.getInstance().get(String.valueOf(newValue));
				if (oldChartType.getFamily().equals(newChartType.getFamily()) == false) {
					translate(originalElement, oldChartType, newChartType);
				}
				//allow new chart type processor to prepare original element for editing
				newChartType.getProcessor().prepareForEditing(originalElement);
				//update chart sub type
				String[] subTypes = newChartType.getProcessor().getSubTypes(originalElement);
				originalElement.setPropertyValues(LocalUnifiedComponent.PROP_KEY_SUBTYPES, Arrays.asList(subTypes));
				//let each slave page handle the chart type change
				for (SlavePage slavePage : slavePages) {
					try {
						slavePage.setChartType(newChartType);
					} catch (Exception e) {
						statuses.add(new Status(IStatus.ERROR,getPluginId(),"An exception occured while setting chart type to "+newChartType.getName()+" on "+slavePage.getTitle()+" for "+originalElement.getFullPath(),e));
					}
				}
				//let each slave page also handle the chart sub type change
				for (SlavePage slavePage : slavePages) {
					updateSubType(slavePage, newChartType);
				}
			}
			else if (provider instanceof LocalUnifiedComponent && property.getName().equals(LocalUnifiedComponent.PROP_KEY_SUBTYPES) == true) {
				//chart type has changed
				ChartType currChartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
				ChartSubType oldSubType = currChartType.getSubType(String.valueOf(oldValue));
				ChartSubType newSubType = currChartType.getSubType(String.valueOf(newValue));
				//allow new chart type process to handle sub type change
				currChartType.getProcessor().subTypeChanged(originalElement, oldSubType, newSubType);
				for (SlavePage slavePage : slavePages) {
					updateSubType(slavePage, currChartType);
				}
			}
		} catch (Exception e) {
			statuses.add(new Status(IStatus.ERROR,getPluginId(),e.getMessage(),e));
		}
		return statuses;
	}


//	private class UnifiedComponentChangeListener implements ISynElementChangeListener {
//
//		@Override
//		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) throws Exception {
//			LocalElement newLocalElement = (LocalElement) newElement;
//			if (newElement instanceof LocalSeriesConfig){
//				ChartType currChartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
//				currChartType.getProcessor().seriesAdded(originalElement, newLocalElement);
//			}
//			LocalElementSubscriptionHelper.subscribeAll(newLocalElement, elementChangeListener);
//			if (chartPreviewBuilder.isPreviewable(parent.getProviderType(), newLocalElement.getElementType(), InternalStatusEnum.StatusNew) == true) {
//				refreshPreview(activePage, true);
//			}
//		}
//
//		@Override
//		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) throws Exception {
//			//do nothing
//		}
//
//		@Override
//		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) throws Exception {
//			LocalElement newLocalElement = (LocalElement) removedElement;
//			if (removedElement instanceof LocalSeriesConfig){
//				ChartType currChartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
//				currChartType.getProcessor().seriesRemoved(originalElement, newLocalElement);
//			}
//			LocalElementSubscriptionHelper.unsubscribeAll(newLocalElement, elementChangeListener);
//			if (chartPreviewBuilder.isPreviewable(parent.getProviderType(), newLocalElement.getElementType(), InternalStatusEnum.StatusRemove) == true) {
//				refreshPreview(activePage, true);
//			}
//		}
//
//		@Override
//		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) throws Exception {
//			//do nothing
//		}
//
//		@Override
//		public String getName() throws Exception {
//			return "ChartType Property Change Listener";
//		}
//
//		@Override
//		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) throws Exception {
//			System.out.println("UnifiedComponentChangeListener.propertyChanged("+provider+","+property+","+oldValue+","+newValue+")");
//			//set the bulk mode on to prevent event propagation
//			originalElement.setBulkOperation(true);
//			try {
//				List<Status> statuses = new ArrayList<Status>();
//				if (provider instanceof LocalUnifiedComponent && property.getName().equals(LocalUnifiedComponent.PROP_KEY_TYPE) == true) {
//					//chart type has changed
//					ChartType oldChartType = ChartTypeRegistry.getInstance().get(String.valueOf(oldValue));
//					ChartType newChartType = ChartTypeRegistry.getInstance().get(String.valueOf(newValue));
//					if (oldChartType.getFamily().equals(newChartType.getFamily()) == false) {
//						translate(originalElement, oldChartType, newChartType);
//					}
//					//allow new chart type processor to prepare original element for editing
//					newChartType.getProcessor().prepareForEditing(originalElement);
//					//update chart sub type
//					String[] subTypes = newChartType.getProcessor().getSubTypes(originalElement);
//					originalElement.setPropertyValues(LocalUnifiedComponent.PROP_KEY_SUBTYPES, Arrays.asList(subTypes));
//					//let each slave page handle the chart type change
//					for (SlavePage slavePage : slavePages) {
//						try {
//							slavePage.setChartType(newChartType);
//						} catch (Exception e) {
//							statuses.add(new Status(IStatus.ERROR,getPluginId(),"An exception occured while setting chart type to "+newChartType.getName()+" on "+slavePage.getTitle()+" for "+originalElement.getFullPath(),e));
//						}
//					}
//					//let each slave page also handle the chart sub type change
//					for (SlavePage slavePage : slavePages) {
//						updateSubType(slavePage, newChartType);
//					}
//				}
//				else if (provider instanceof LocalUnifiedComponent && property.getName().equals(LocalUnifiedComponent.PROP_KEY_SUBTYPES) == true) {
//					//chart type has changed
//					ChartType currChartType = ChartTypeRegistry.getInstance().get(originalElement.getPropertyValue(LocalUnifiedComponent.PROP_KEY_TYPE));
//					ChartSubType oldSubType = currChartType.getSubType(String.valueOf(oldValue));
//					ChartSubType newSubType = currChartType.getSubType(String.valueOf(newValue));
//					//allow new chart type process to handle sub type change
//					currChartType.getProcessor().subTypeChanged(originalElement, oldSubType, newSubType);
//					for (SlavePage slavePage : slavePages) {
//						updateSubType(slavePage, currChartType);
//					}
//				}
//				if (statuses.isEmpty() == false){
//					log(new MultiStatus(getPluginId(),IStatus.ERROR,statuses.toArray(new Status[statuses.size()]),"An exception occured while processing changes to "+property.getName()+" in "+originalElement.getFullPath(),null));
//				}
//			} finally {
//				originalElement.setBulkOperation(false);
//			}
//			if (chartPreviewBuilder.isPreviewable(property.getParent().getElementType(), property.getName(), InternalStatusEnum.StatusModified) == true) {
//				refreshPreview(activePage, true);
//			}
//		}
//	}

	private void translate(LocalUnifiedComponent localUnifiedComponent, ChartType oldChartType, ChartType newChartType) throws Exception {
		String oldFamily = oldChartType.getFamily();
		String newFamily = newChartType.getFamily();
		//get translator
		Translator translator = null;
		if (oldFamily.equals(BEViewsElementNames.CHART_COMPONENT) == true && newFamily.equals(BEViewsElementNames.TEXT_COMPONENT) == true) {
			translator = new GraphToGridTranslator();
		}
		else if (oldFamily.equals(BEViewsElementNames.TEXT_COMPONENT) == true && newFamily.equals(BEViewsElementNames.CHART_COMPONENT) == true) {
			translator = new GridToGraphTranslator();
		}
		if (translator == null){
			//log a warning and move on
			log(new Status(IStatus.WARNING, getPluginId(), "No translator found for translating "+localUnifiedComponent+" to "+newChartType.getName()+" from "+oldChartType.getName()));
			return;
		}
//		//preserve values of old family
//		PropertyValuePreserver oldFamilyPropertyValuePreserver = this.valuePreservers.get(oldFamily);
//		if (oldFamilyPropertyValuePreserver == null){
//			oldFamilyPropertyValuePreserver = new PropertyValuePreserver(oldFamily);
//			this.valuePreservers.put(oldFamily, oldFamilyPropertyValuePreserver);
//		}
//		//preserve the values of old family
//		oldFamilyPropertyValuePreserver.preserve(originalElement);
//		//restore the values of new family, if present
//		PropertyValuePreserver newFamilyPropertyValuePreserver = this.valuePreservers.get(newFamily);
//		if (newFamilyPropertyValuePreserver != null){
//			newFamilyPropertyValuePreserver.restore(originalElement);
//		}
		//translate from old family to new family
		translator.translate(localUnifiedComponent);
	}

	@Override
	public void log(IStatus status) {
		super.log(status);
		if (status.getSeverity() == IStatus.ERROR) {
			disableAllPages();
		}
	}

	@Override
	public void logAndAlert(String title, IStatus status) {
		if (status.getSeverity() == IStatus.ERROR) {
			disableAllPages();
		}
		super.log(status);
	}

	public void disableAllPages() {
		masterPage.disable();
		for (SlavePage page : slavePages) {
			page.disable();
		}
	}

}
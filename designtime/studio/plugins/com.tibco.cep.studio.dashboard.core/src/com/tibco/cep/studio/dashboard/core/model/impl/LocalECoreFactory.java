package com.tibco.cep.studio.dashboard.core.model.impl;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationFactory;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.DashboardPage;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataSource;
import com.tibco.cep.designtime.core.model.beviewsconfig.Header;
import com.tibco.cep.designtime.core.model.beviewsconfig.Login;
import com.tibco.cep.designtime.core.model.beviewsconfig.PageSelectorComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.RolePreference;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.beviewsconfig.StateMachineComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.View;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.ResourceHelper;
import com.tibco.cep.studio.dashboard.core.DashboardCorePlugIn;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalChartComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalChartComponentColorSet;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDashboardPage;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalHeader;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalLogin;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalPageSelectorComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRolePreference;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSeriesColor;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalStateMachineComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalTextComponent;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalTextComponentColorSet;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalView;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.impl.metric.LocalMetric;
import com.tibco.cep.studio.dashboard.core.util.LocalECoreUtils;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

/**
 * @author anpatil
 *
 */
public class LocalECoreFactory extends LocalEntity {

	private static List<String> SEEDED_ELEMENT_TYPES = Arrays.asList(
			BEViewsElementNames.SERIES_COLOR,
			BEViewsElementNames.CHART_COLOR_SET,
			BEViewsElementNames.TEXT_COLOR_SET,
			BEViewsElementNames.SKIN);

	private static Map<String, LocalECoreFactory> instances = new HashMap<String, LocalECoreFactory>();

	public static final LocalElement toLocalElement(LocalECoreFactory localECoreFactory, IFile file) {
		EObject persistedObject = IndexUtils.loadEObject(ResourceHelper.getLocationURI(file));
		return toLocalElement(localECoreFactory, persistedObject);
	}

	public static final LocalElement toLocalElement(LocalECoreFactory localECoreFactory, EObject persistedObject) {
		if (localECoreFactory == null) {
			throw new IllegalArgumentException("local ecore factory cannot be null");
		}
		if (persistedObject == null) {
			throw new IllegalArgumentException("persisted object cannot be null");
		}
		if (persistedObject instanceof Metric) {
			return new LocalMetric(localECoreFactory, (Metric) persistedObject);
		}
		if (persistedObject instanceof Login) {
			return new LocalLogin(localECoreFactory, (Login) persistedObject);
		}
		if (persistedObject instanceof Header) {
			return new LocalHeader(localECoreFactory, (Header) persistedObject);
		}
		if (persistedObject instanceof SeriesColor) {
			return new LocalSeriesColor(localECoreFactory, (SeriesColor) persistedObject);
		}
		if (persistedObject instanceof ChartComponentColorSet) {
			return new LocalChartComponentColorSet(localECoreFactory, (ChartComponentColorSet) persistedObject);
		}
		if (persistedObject instanceof TextComponentColorSet) {
			return new LocalTextComponentColorSet(localECoreFactory, (TextComponentColorSet) persistedObject);
		}
		if (persistedObject instanceof DashboardPage) {
			return new LocalDashboardPage(localECoreFactory, (DashboardPage) persistedObject);
		}
		if (persistedObject instanceof ChartComponent) {
			return new LocalChartComponent(localECoreFactory, (ChartComponent) persistedObject);
		}
		if (persistedObject instanceof TextComponent) {
			return new LocalTextComponent(localECoreFactory, (TextComponent) persistedObject);
		}
		if (persistedObject instanceof PageSelectorComponent) {
			return new LocalPageSelectorComponent(localECoreFactory, (PageSelectorComponent) persistedObject);
		}
		if (persistedObject instanceof View) {
			return new LocalView(localECoreFactory, (View) persistedObject);
		}
		if (persistedObject instanceof Skin) {
			return new LocalSkin(localECoreFactory, (Skin) persistedObject);
		}
		if (persistedObject instanceof RolePreference) {
			return new LocalRolePreference(localECoreFactory, (RolePreference) persistedObject);
		}
		if (persistedObject instanceof DataSource) {
			return new LocalDataSource(localECoreFactory, (DataSource) persistedObject);
		}
		if (persistedObject instanceof StateMachineComponent) {
			return new LocalStateMachineComponent(localECoreFactory, (StateMachineComponent) persistedObject);
		}
		throw new UnsupportedOperationException("Unknown persisted object of type " + persistedObject.getClass().getName());
	}

	static final LocalElement toEmptyLocalElement(LocalECoreFactory localECoreFactory, IFile file) {
		String type = BEViewsElementNames.getType(file.getFileExtension());
		String name = file.getName();
		if (type == null) {
			if (file.getFileExtension().equals("system") == true) {
				type = BEViewsElementNames.SKIN;
				name = "Default";
			}
		}
		LocalElement element = null;
		if (type.equals(BEViewsElementNames.METRIC)) {
			element = new LocalMetric(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.LOGIN)) {
			element = new LocalLogin(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.HEADER)) {
			element = new LocalHeader(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.SERIES_COLOR)) {
			element = new LocalSeriesColor(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.CHART_COLOR_SET)) {
			element = new LocalChartComponentColorSet(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.TEXT_COLOR_SET)) {
			element = new LocalTextComponentColorSet(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.DASHBOARD_PAGE)) {
			element = new LocalDashboardPage(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.CHART_COMPONENT)) {
			element = new LocalChartComponent(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.TEXT_COMPONENT)) {
			element = new LocalTextComponent(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.PAGE_SELECTOR_COMPONENT)) {
			element = new LocalPageSelectorComponent(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.VIEW)) {
			element = new LocalView(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.SKIN)) {
			element = new LocalSkin(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.ROLE_PREFERENCE)) {
			element = new LocalRolePreference(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.DATA_SOURCE)) {
			element = new LocalDataSource(localECoreFactory, name);
		} else if (type.equals(BEViewsElementNames.STATE_MACHINE_COMPONENT)) {
			element = new LocalStateMachineComponent(localECoreFactory, name);
		} else {
			throw new UnsupportedOperationException("Unknown file type " + file.toString());
		}
		element.setRemoved();
		return element;
	}

	public static final synchronized LocalECoreFactory getInstance(IProject project) {
		if (project == null) {
			throw new IllegalArgumentException("project cannot be null");
		}
		LocalECoreFactory instance = instances.get(project.getName());
		if (instance == null) {
			instance = new LocalECoreFactory(project);
			// Add the listeners for change in project
			instances.put(project.getName(), instance);
		}
		return instance;
	}

	private IProject project;

	private List<ElementChangeSubscription> elementChangeSubscriptions;

	private ResourceComparator resourceComparator; //we need this to sort the members while scanning similar to StudioNavigatorViewerSorter

	private LocalECoreFactory(IProject project) {
		super();
		//We need to wait till the index has loaded , since we use the index to get the contents in toLocalElement(...)
		IndexUtils.getIndex(project);
		this.project = project;
		this.elementChangeSubscriptions = new LinkedList<ElementChangeSubscription>();
		project.getWorkspace().addResourceChangeListener(new ProjectChangesListener(this));
		this.resourceComparator = new ResourceComparator();
		scanAndLoadSystemElements(project);
	}

	void scanAndLoadSystemElements(IContainer folder) {
		try {
			for (IResource resource : folder.members()) {
				if (resource instanceof IContainer) {
					scanAndLoadSystemElements((IContainer) resource);
				} else if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					if (file.getName().endsWith(".system")) {
						loadSystemElements(ResourceHelper.getLocationURI(file).toString());
					}
				}
			}
		} catch (CoreException e) {
			DashboardCorePlugIn.getDefault().getLog().log(e.getStatus());
			throw new RuntimeException(e);
		}
	}

	public void loadSystemElements(String fileName) {
		URI uri = URI.createURI(fileName);
		// using XMI instead of XML
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(uri, true);
		EList<EObject> elements = resource.getContents();
		for (EObject object : elements) {
			LocalConfig element = (LocalConfig) toLocalElement(this, object);
			addElement(element.getElementType(), element);
		}
	}

	public IProject getProject() {
		return project;
	}

	@Override
	public LocalElement createLocalElement(String elementType) {
		LocalElement childElement = null;
		if (elementType == BEViewsElementNames.METRIC) {
			childElement = new LocalMetric(this, (String) null);
		} else if (elementType == BEViewsElementNames.LOGIN) {
			childElement = new LocalLogin(this, (String) null);
		} else if (elementType == BEViewsElementNames.SERIES_COLOR) {
			childElement = new LocalSeriesColor(this, (String) null);
		} else if (elementType == BEViewsElementNames.HEADER) {
			childElement = new LocalHeader(this, (String) null);
		} else if (elementType == BEViewsElementNames.TEXT_COLOR_SET) {
			childElement = new LocalTextComponentColorSet(this, (String) null);
		} else if (elementType == BEViewsElementNames.CHART_COLOR_SET) {
			childElement = new LocalChartComponentColorSet(this, (String) null);
		} else if (elementType == BEViewsElementNames.DASHBOARD_PAGE) {
			childElement = new LocalDashboardPage(this, (String) null);
		} else if (elementType == BEViewsElementNames.TEXT_COMPONENT) {
			childElement = new LocalTextComponent(this, (String) null);
		} else if (elementType == BEViewsElementNames.CHART_COMPONENT) {
			childElement = new LocalChartComponent(this, (String) null);
		} else if (elementType == BEViewsElementNames.VIEW) {
			childElement = new LocalView(this, (String) null);
		} else if (elementType == BEViewsElementNames.SKIN) {
			childElement = new LocalSkin(this, (String) null);
		} else if (elementType == BEViewsElementNames.PAGE_SELECTOR_COMPONENT) {
			childElement = new LocalPageSelectorComponent(this, (String) null);
		} else if (elementType == BEViewsElementNames.ROLE_PREFERENCE) {
			childElement = new LocalRolePreference(this, (String) null);
		} else if (elementType == BEViewsElementNames.DATA_SOURCE) {
			childElement = new LocalDataSource(this, (String) null);
		} else if (elementType == BEViewsElementNames.STATE_MACHINE_COMPONENT) {
			childElement = new LocalStateMachineComponent(this, (String) null);
		} else {
			throw new IllegalArgumentException("Unknown element type " + elementType);
		}
		return childElement;
	}

	@Override
	public EObject createMDChild(LocalElement localElement) {
		if (localElement instanceof LocalMetric) {
			return ElementFactory.eINSTANCE.createMetric();
		} else if (localElement instanceof LocalConfig) {
			LocalConfig localConfig = (LocalConfig) localElement;
			String insightType = localConfig.getInsightType();
			EClassifier classifier = BEViewsConfigurationPackage.eINSTANCE.getEClassifier(insightType);
			return BEViewsConfigurationFactory.eINSTANCE.create((EClass) classifier);
		}
		return null;
	}

	@Override
	public void deleteMDChild(LocalElement localElement) {
		// do nothing
	}

	@Override
	public void loadChild(String childrenType, String childName) {
		loadChildren(childrenType);
	}

	@Override
	public void loadChildByID(String childrenType, String childID) {
		loadChildren(childrenType);
	}

	@Override
	public void loadChildren(String childrenType) {
		scanAndLoadChildren(childrenType, project);
		getParticle(childrenType).setInitialized(true);
	}

	private void scanAndLoadChildren(String childrenType, IContainer folder) {
		try {
			List<IResource> members = Arrays.asList(folder.members());
			Collections.sort(members, resourceComparator);
			for (IResource resource : members) {
				if (resource instanceof IContainer) {
					scanAndLoadChildren(childrenType, (IContainer) resource);
				} else if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					String fileExtension = file.getFileExtension();
					if (BEViewsElementNames.getTopLevelElementExtensions().contains(fileExtension) == true) {
						if (BEViewsElementNames.getChartOrTextComponentTypes().contains(childrenType) == true && BEViewsElementNames.EXT_TEXT_OR_CHART_COMPONENT.equals(fileExtension) == true) {
							// we are dealing with charts (which can be text or chart)
							LocalElement localElement = toLocalElement(this, file);
							if (localElement.getElementType().equals(childrenType) == true) {
								addElement(childrenType, localElement);
							}
						} else if (BEViewsElementNames.isTypeOf(LocalECoreUtils.fileToElementType(file), childrenType) == true) {
							addElement(childrenType, toLocalElement(this, file));
						}
					}
				}
			}
		} catch (CoreException e) {
			DashboardCorePlugIn.getDefault().getLog().log(e.getStatus());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setupProperties() {
		for (String childElementName : BEViewsElementNames.getTopLevelElementNames()) {
			addParticle(new LocalParticle(childElementName));
		}
		// PATCH do I need this ? addParticle(new LocalParticle(BEViewsElementNames.PAGE));
	}

	@Override
	public String getElementType() {
		return "ROOT";
	}

	@Override
	public Object cloneThis() throws Exception {
		throw new UnsupportedOperationException("cloneThis");
	}

	// TODO we should think about having more directed refresh events
	public void addElementChangeSubscription(ISynElementChangeListener elementRefreshListener, List<String> interestingElementTypes) {
		synchronized (this) {
			ElementChangeSubscription changeListener = new ElementChangeSubscription(elementRefreshListener, interestingElementTypes == null ? new ArrayList<String>() : interestingElementTypes);
			elementChangeSubscriptions.add(changeListener);
		}
	}

	public void removeElementChangeSubscription(ISynElementChangeListener elementRefreshListener) {
		synchronized (this) {
			ListIterator<ElementChangeSubscription> listIterator = elementChangeSubscriptions.listIterator();
			while (listIterator.hasNext()) {
				ElementChangeSubscription elementChangeSubscription = listIterator.next();
				if (elementChangeSubscription.listener == elementRefreshListener) {
					listIterator.remove();
					break;
				}
			}
		}
	}

	@Override
	public List<LocalElement> getChildren(String particleName, boolean includeInactive) {
		if (BEViewsElementNames.TEXT_OR_CHART_COMPONENT.equals(particleName) == true) {
			LinkedList<LocalElement> elements = new LinkedList<LocalElement>(super.getChildren(BEViewsElementNames.CHART_COMPONENT, includeInactive));
			elements.addAll(super.getChildren(BEViewsElementNames.TEXT_COMPONENT, includeInactive));
			// TODO sort the elements by name
			return elements;
		}
		if (BEViewsElementNames.OTHER_COMPONENT.equals(particleName) == true) {
			LinkedList<LocalElement> elements = new LinkedList<LocalElement>(super.getChildren(BEViewsElementNames.PAGE_SELECTOR_COMPONENT, includeInactive));
			elements.addAll(super.getChildren(BEViewsElementNames.STATE_MACHINE_COMPONENT, includeInactive));
			// TODO sort the elements by name
			return elements;
		}
		if (BEViewsElementNames.COMPONENT.equals(particleName) == true) {
			LinkedList<LocalElement> elements = new LinkedList<LocalElement>(super.getChildren(BEViewsElementNames.CHART_COMPONENT, includeInactive));
			elements.addAll(super.getChildren(BEViewsElementNames.TEXT_COMPONENT, includeInactive));
			elements.addAll(super.getChildren(BEViewsElementNames.PAGE_SELECTOR_COMPONENT, includeInactive));
			elements.addAll(super.getChildren(BEViewsElementNames.STATE_MACHINE_COMPONENT, includeInactive));
			// TODO sort the elements by name
			return elements;
		}
		return super.getChildren(particleName, includeInactive);
	}

	@Override
	public void refresh(String particleName) {
		System.out.println("LocalECoreFactory.refresh(" + particleName + ")");
		if (BEViewsElementNames.TEXT_OR_CHART_COMPONENT.equals(particleName) == true) {
			super.refresh(BEViewsElementNames.CHART_COMPONENT);
			super.refresh(BEViewsElementNames.TEXT_COMPONENT);
			return;
		}
		super.refresh(particleName);
		if (SEEDED_ELEMENT_TYPES.contains(particleName) == true) {
			scanAndLoadSystemElements(project);
			return;
		}
	}

	void fireElementAdded(LocalElement localElement) {
		System.out.println("LocalECoreFactory.fireElementAdded(" + localElement + ")");
		List<ElementChangeSubscription> localList = new ArrayList<ElementChangeSubscription>();
		synchronized (this) {
			localList.addAll(elementChangeSubscriptions);
		}
		for (ElementChangeSubscription listener : localList) {
			if (listener.isInterested(localElement.getElementType()) == true) {
				try {
					listener.listener.elementAdded(this, localElement);
				} catch (Exception e) {
					Status status = new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not notify all open editors about new element addition[" + localElement + "]", e);
					DashboardCorePlugIn.getDefault().getLog().log(status);
				}
			}
		}
	}

	void fireElementRemoved(LocalElement localElement) {
		System.out.println("LocalECoreFactory.fireElementRemoved(" + localElement + ")");
		List<ElementChangeSubscription> localList = new ArrayList<ElementChangeSubscription>();
		synchronized (this) {
			localList.addAll(elementChangeSubscriptions);
		}
		for (ElementChangeSubscription listener : localList) {
			if (listener.isInterested(localElement.getElementType()) == true) {
				try {
					listener.listener.elementRemoved(this, localElement);
				} catch (Exception e) {
					Status status = new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not notify all open editors about existing element deletion[" + localElement + "]", e);
					DashboardCorePlugIn.getDefault().getLog().log(status);
				}
			}
		}
	}

	void fireElementChanged(LocalElement localElement) {
		System.out.println("LocalECoreFactory.fireElementChanged(" + localElement + ")");
		List<ElementChangeSubscription> localList = new ArrayList<ElementChangeSubscription>();
		synchronized (this) {
			localList.addAll(elementChangeSubscriptions);
		}
		for (ElementChangeSubscription listener : localList) {
			if (listener.isInterested(localElement.getElementType()) == true) {
				try {
					listener.listener.elementChanged(this, localElement);
				} catch (Exception e) {
					Status status = new Status(IStatus.ERROR, DashboardCorePlugIn.PLUGIN_ID, "could not notify all open editors about existing element changes[" + localElement + "]", e);
					DashboardCorePlugIn.getDefault().getLog().log(status);
				}
			}
		}
	}

	class ElementChangeSubscription {

		private List<String> types;

		private ISynElementChangeListener listener;

		ElementChangeSubscription(ISynElementChangeListener listener, List<String> types) {
			super();
			this.types = types;
			this.listener = listener;
		}

		boolean isInterested(String type) {
			if (types.isEmpty() == true) {
				return true;
			}
			return types.contains(type);
		}
	}

	class ResourceComparator implements Comparator<IResource> {

		Collator collator = Collator.getInstance();

		@Override
		public int compare(IResource o1, IResource o2) {
			return collator.compare(o1.getName() == null ? "" : o1.getName(), o2.getName() == null ? "" : o2.getName());

		}

	}

}
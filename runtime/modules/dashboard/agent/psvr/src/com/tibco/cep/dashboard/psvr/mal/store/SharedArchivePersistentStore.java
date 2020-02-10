package com.tibco.cep.dashboard.psvr.mal.store;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.ComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor;
import com.tibco.cep.designtime.core.model.beviewsconfig.Skin;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.provider.SharedArchiveResourceProvider;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.repo.emf.DeployedEMFProject;

public class SharedArchivePersistentStore extends XMIPersistentStore {

	private DeployedEMFProject project;
	private DesignerProject index;
	private SharedArchiveResourceProvider sharedArchiveResourceProvider;

	private SharedArchiveResourceSet resourceSet;

	// PORT we need to make sure that our root URI can hold against project renaming & ear renaming, but how ?
	private URI rootURI = URI.createURI("sar://${deployedear}/");

	public SharedArchivePersistentStore(PersistentStore parent, Identity identity) {
		super(parent, identity);
	}

	protected void init() throws MALException {
		resourceSet = new SharedArchiveResourceSet();
		project = (DeployedEMFProject) serviceContext.getRuleServiceProvider().getProject();

		sharedArchiveResourceProvider = project.getSharedArchiveResourceProvider();
		index = (DesignerProject) project.getRuntimeIndex(AddOnType.CORE);
		for (EntityElement entityElement : index.getEntityElements()) {
			Entity entity = entityElement.getEntity();
			if (entity instanceof BEViewsElement) {
				// we will create an copy since we modify the resource URI in
				// the object
				entity = (Entity) EcoreUtil.copy(entity);
				// get the original URI
				URI originalURI = getURI(entity).trimFragment();
				if (originalURI.fileExtension().equals("system") == true) {
					Skin skin = (Skin) entity;
					// we will force resolve the system elements
					if (entity.eIsProxy() == true) {
						try {
							//Added to fix BE-102066 12/02/2010 - START
							URI sarRootURI = null;
							if (originalURI.isFile() == true) {
								sarRootURI = URI.createFileURI(index.getRootPath());
							}
							else {
								sarRootURI = URI.createURI(index.getRootPath());
							}
							//Added to fix BE-102066 12/02/2010 - END
							URI deresolvedURI = originalURI.deresolve(sarRootURI);
							byte[] bytes = sharedArchiveResourceProvider.getResourceAsByteArray("/"+deresolvedURI.toString());
							Resource resource = resourceSet.createResource(originalURI);
							resource.load(new ByteArrayInputStream(bytes), Collections.emptyMap());
							for (EObject eObject : resource.getContents()) {
								if (eObject instanceof Skin) {
									skin = (Skin) eObject;
								}
							}
						} catch (IOException e) {
							throw new MALException("could not load " + originalURI + " from shared archive", e);
						}
					}
					updateURI(entity, generateReferenceableURI(skin));
					cacheIt(skin.getGUID(), skin.getName(), skin);
					// traverse all skin elements
					for (ComponentColorSet colorSet : skin.getComponentColorSet()) {
						updateURI(colorSet, generateReferenceableURI(colorSet));
						cacheIt(colorSet.getGUID(), colorSet.getName(), colorSet);
						if (colorSet instanceof ChartComponentColorSet) {
							ChartComponentColorSet chartColorSet = (ChartComponentColorSet) colorSet;
							for (SeriesColor seriesColor : chartColorSet.getSeriesColor()) {
								updateURI(seriesColor, generateReferenceableURI(seriesColor));
								cacheIt(seriesColor.getGUID(), seriesColor.getName(), seriesColor);
							}
						}
					}
				} else {
					// update the URI
					updateURI(entity, generateReferenceableURI(entity));
					// cache the entity with the updated URI
					cacheIt(entity.getGUID(), entity.getName(), entity);
				}
			}
			else if (entity instanceof Metric){
				//Added to fix BE-10206 by Anand - 01/04/2011
				//create a new instance of the metric
				Entity metricCopy = (Entity) ElementFactory.eINSTANCE.create(entity.eClass());
				//update the URI
				((InternalEObject) metricCopy).eSetProxyURI(generateReferenceableURI(entity));
				//transfer all top level attributes
				transferAttributes(entity,metricCopy);
				//cache the metric copy using the entity guid and name
				cacheIt(entity.getGUID(), entity.getName(), metricCopy);
			}
			else if (entity instanceof Scorecard) {
				//skip it
			}
			else if (entity instanceof Concept){
				//create a new instance of the concept
				Entity conceptCopy = (Entity) ElementFactory.eINSTANCE.create(entity.eClass());
				//update the URI
				((InternalEObject) conceptCopy).eSetProxyURI(generateReferenceableURI(entity));
				//transfer all top level attributes
				transferAttributes(entity,conceptCopy);
				//cache the concept copy using the entity guid and name
				cacheIt(entity.getGUID(), entity.getName(), conceptCopy);
			}
			else if (entity instanceof StateMachine){
				//Added to fix BE-10206 by Anand - 01/04/2011
				//create a new instance of a state machine
				Entity stateMachineCopy = (Entity) StatesFactory.eINSTANCE.create(entity.eClass());
				//update the URI
				((InternalEObject) stateMachineCopy).eSetProxyURI(generateReferenceableURI(entity));
				//transfer all top level attributes
				transferAttributes(entity,stateMachineCopy);
				//cache the metric copy using the entity guid and name
				cacheIt(entity.getGUID(), entity.getName(), stateMachineCopy);
			}
		}
	}

	private void transferAttributes(EObject fromObject, EObject toObject) {
		List<EAttribute> attributes = fromObject.eClass().getEAllAttributes();
		for (EAttribute attribute : attributes) {
			toObject.eSet(attribute, fromObject.eGet(attribute,false));
		}
	}

	@Override
	protected ResourceSet getResourceSet() {
		return resourceSet;
	}

	@Override
	public Entity createElement(String definitionType) {
		// INFO We do not support create elements in the archive
		throw new UnsupportedOperationException("createElement");
	}

	@Override
	public void deleteElementById(String id) {
		// INFO We do not support deleting elements in the archive
		throw new UnsupportedOperationException("deleteElementById");
	}

	@Override
	protected Entity saveElement(Entity entity) throws MALException {
		// INFO We do not support changing/updating/saving elements in the archive
		throw new UnsupportedOperationException("saveElement");
	}

	@Override
	protected boolean deleteElement(Entity entity) {
		throw new UnsupportedOperationException("deleteElement");
	}

	@Override
	protected void setDefaultProperties(Entity entity) {
		throw new UnsupportedOperationException("setDefaultProperties");
	}

	@Override
	protected URI generateStorageURI(Entity entity) {
		throw new UnsupportedOperationException("generateStorageURI");
	}

	@Override
	protected URI getReferenceableRootURI() {
		return rootURI;
	}

	private class SharedArchiveResourceSet extends ResourceSetImpl {

		@Override
		protected Resource delegatedGetResource(URI uri, boolean loadOnDemand) {
			Resource resource = resourceSet.createResource(uri);
			try {
				byte[] resourceAsByteArray = sharedArchiveResourceProvider.getResourceAsByteArray(uri.deresolve(rootURI).toString());
				if (resourceAsByteArray == null) {
					return null;
				}
				resource.load(new ByteArrayInputStream(resourceAsByteArray), Collections.emptyMap());
				return resource;
			} catch (IOException e) {
				handleDemandLoadException(resource, e);
				return super.delegatedGetResource(uri, loadOnDemand);
			}
		}

	}

}

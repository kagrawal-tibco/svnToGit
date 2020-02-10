package com.tibco.cep.studio.core.compare.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Folder;
import com.tibco.cep.decisionproject.ontology.Ontology;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.core.compare.handler.DecisionTableResourceHandler;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class CoreCompareUtils {



	public static int getFeatureId(EObject object) {
		if (object instanceof Concept) {
			return ElementPackage.CONCEPT;
		}
		if (object instanceof Event) {
			return EventPackage.EVENT;
		}
		if (object instanceof Channel) {
			return ChannelPackage.CHANNEL;
		}
		if (object instanceof Destination) {
			return ChannelPackage.DESTINATION;
		}
		if (object instanceof Table) {
			return DtmodelPackage.TABLE;
		}
		if (object instanceof StateMachine) {
			return StatesPackage.STATE_MACHINE;
		}
		if (object instanceof StateSimple) {
			 return StatesPackage.STATE_SIMPLE;
		}
		if (object instanceof StateComposite) {
			StateComposite stateComposite = (StateComposite)object;
			if(stateComposite.isConcurrentState()){
				return StatesPackage.STATE_COMPOSITE__CONCURRENT_STATE;
			}
			if(stateComposite.isRegion()){
				return StatesPackage.STATE_COMPOSITE__REGIONS;
			}
			if(stateComposite instanceof StateSubmachine){
				return StatesPackage.STATE_SUBMACHINE;
			}
			return StatesPackage.STATE_COMPOSITE;
		}
		if (object instanceof StateStart) {
			return StatesPackage.STATE_START;
		}
		if (object instanceof StateEnd) {
			return StatesPackage.STATE_END;
		}
		if (object instanceof State) {
			return StatesPackage.STATE_SIMPLE;
		}
		//TODO: for other entities
		return -1;
	}

	public static String getExtension(EObject object) {
		if (object instanceof Entity) {
			return IndexUtils.getFileExtension((Entity)object);
		}
		if (object instanceof Table) {
			return "rulefunctionimpl";
		}
		return null;
	}

	public static String getModelName(EObject object) {
		if (object instanceof Entity) {
			return ((Entity)object).getName();
		}
		if (object instanceof Table) {
			return ((Table)object).getName();
		}
		return null;
	}

	/**
	 * Get the path to the persisted table file on the file system.<br/> Note:
	 * this method assumes that the <code>Table</code> instance is on the
	 * local file system, and does not look in the remote RMS directories
	 * 
	 * @param table
	 * @return
	 */
	public static String getTableFilePath(Table table) {
		if (table.eResource() != null && table.eResource().getURI() != null) {
			// just use the URI of the resource
			URI uri = table.eResource().getURI();
			String path = uri.toFileString();
			return path;
		}
		return null;
	}

	/**
	 * @param abs
	 * @param implementsValue
	 * @return
	 */
	public static AbstractResource getTemplate(AbstractResource abs, String implementsValue) {
		List<AbstractResource> ruleFunctionList = new ArrayList<AbstractResource>();
		getRuleFunctionList(abs, ruleFunctionList);
		if (ruleFunctionList != null) {
			for (AbstractResource ruleFunction : ruleFunctionList) {
				String folderName = ruleFunction.getFolder();
				if (folderName != null) {
					if ((folderName + ruleFunction.getName())
							.equals(implementsValue)) {
						return ruleFunction;
					}
				} else {
					if (ruleFunction.getName().equals(implementsValue)) {
						return ruleFunction;
					}
				}
			}
		}
	
		return null;
	
	}

	private static void getRuleFunctionList(AbstractResource abs, List<AbstractResource> list) {
		if (abs instanceof Ontology) {
			Ontology ont = (Ontology) abs;
			for (AbstractResource ab : ont.getResource()) {
				getRuleFunctionList(ab, list);
			}
		} else if (abs instanceof Folder) {
			Folder folder = (Folder) abs;
			for (AbstractResource ab : folder.getResource()) {
				getRuleFunctionList(ab, list);
			}
		} else if (abs instanceof RuleFunction) {
			list.add(abs);
	
		}
	
	}

	/**
	 * Serialize an {@code EObject} and return its <tt>byte[]</tt>
	 * implementation<br/> Uses the resource factory for the eObject's URI, if
	 * available
	 * 
	 * @param eObject
	 * @return string implementation of the <tt>EObject</tt>
	 * @throws IOException
	 */
	public static byte[] getEObjectContents(final EObject eObject) throws IOException {
		Resource resource = null;
		Resource oldResource = eObject.eResource();
		if (eObject.eResource() != null && eObject.eResource().getURI() != null) {
			// Factory factory =
			// eObject.eResource().getResourceSet().getResourceFactoryRegistry().getFactory(eObject.eResource().getURI());
			Factory factory = Resource.Factory.Registry.INSTANCE
					.getFactory(eObject.eResource().getURI());
			resource = factory.createResource(eObject.eResource().getURI());
		}
		if (resource == null) {
			resource = new XMLResourceImpl(URI.createURI(""));
		}
		resource.getContents().add(eObject);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			Map<String, Object> options = new HashMap<String, Object>();
			options.put(XMLResource.OPTION_RESOURCE_HANDLER,
					DecisionTableResourceHandler.INSTANCE);
			resource.save(os, options);
			return os.toByteArray();
		} catch (Exception e) {
		} finally {
			try {
				os.close();
				if (oldResource != null) {
					oldResource.getContents().add(eObject);
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Load a model from an {@link java.io.InputStream InputStream} in a given {@link ResourceSet}.
	 * <p>
	 * This will return the first root of the loaded model, other roots can be accessed via the resource's
	 * content.
	 * </p>
	 * 
	 * @param stream
	 *            The inputstream to load from
	 * @param fileName
	 *            The original filename
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The loaded model
	 * @throws IOException
	 *             If the given file does not exist.
	 */
	public static EObject load(InputStream stream, String fileName, ResourceSet resourceSet) throws IOException {
		if (stream == null)
			throw new NullPointerException(CompareMessages.getString("CompareUtils.NullInputStream")); //$NON-NLS-1$
		EObject result = null;
		final Resource modelResource = createResource(URI.createURI(fileName), resourceSet);
		modelResource.load(stream, Collections.emptyMap());
		if (modelResource.getContents().size() > 0)
			result = modelResource.getContents().get(0);
		return result;
	}

	/**
	 * @param stream
	 * @param fileName
	 * @param resourceSet
	 * @return
	 * @throws IOException
	 */
	public static EObject loadTable(InputStream stream, String fileName, ResourceSet resourceSet) throws IOException {
		if (stream == null) {
			throw new NullPointerException(CompareMessages.getString("CompareUtils.NullInputStream")); //$NON-NLS-1$
		}
		EObject result = null;
		final Resource modelResource = createResource(URI.createURI(fileName), resourceSet);
		Map<String, String> options = new HashMap<String, String>();
		options.put("RELOAD", "true");
		modelResource.load(stream, options);
		if (modelResource.getContents().size() > 0) {
			result = modelResource.getContents().get(0);
			Table table = (Table)result;
			if (result instanceof Table) {
				Table resolvedTable = (Table) modelResource.getContents().remove(
						modelResource.getContents().size() - 1);
				table.setDecisionTable(resolvedTable.getDecisionTable());
				table.setExceptionTable(resolvedTable.getExceptionTable());
			}
		}
		return result;
	}

	/**
	 * This will create a {@link Resource} given the model extension it is intended for and a ResourceSet.
	 * 
	 * @param modelURI
	 *            {@link org.eclipse.emf.common.util.URI URI} where the model is stored.
	 * @param resourceSet
	 *            The {@link ResourceSet} to load the model in.
	 * @return The {@link Resource} given the model extension it is intended for.
	 */
	public static Resource createResource(URI modelURI, ResourceSet resourceSet) {
		String fileExtension = modelURI.fileExtension();
		if (fileExtension == null || fileExtension.length() == 0) {
			fileExtension = Resource.Factory.Registry.DEFAULT_EXTENSION;
		}
	
		// First search the resource set for our resource factory
		Resource.Factory.Registry registry = resourceSet.getResourceFactoryRegistry();
		Object resourceFactory = registry.getExtensionToFactoryMap().get(fileExtension);
		if (resourceFactory == null) {
			// then the global registry
			registry = Resource.Factory.Registry.INSTANCE;
			resourceFactory = registry.getExtensionToFactoryMap().get(fileExtension);
			if (resourceFactory != null) {
				resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(fileExtension,
						resourceFactory);
			}
		}
		return resourceSet.createResource(modelURI);
	}

	

	/**
	 * Generate <tt>byte[]</tt> corresponding to an {@link EObject}
	 * @param eobject
	 * @return a <tt>byte[]</tt> representation of the {@link EObject}
	 * @throws IOException
	 */
	public static byte[] getBytesForEObject(EObject eobject) throws IOException {
		if (eobject == null)
			return new byte[0];
		Resource oldResource = eobject.eResource();
		XMIResource resource = new XMIResourceImpl();
		OutputStream outputStream = new ByteArrayOutputStream();
		BufferedOutputStream bos = 
			new BufferedOutputStream(outputStream); 
		resource.getContents().add(eobject);
		resource.save(bos, null);
		if (oldResource != null) {
			oldResource.getContents().add(eobject);
		}
		return ((ByteArrayOutputStream) outputStream).toByteArray();
	}

}

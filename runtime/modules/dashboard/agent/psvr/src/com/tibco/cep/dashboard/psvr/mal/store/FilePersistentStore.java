package com.tibco.cep.dashboard.psvr.mal.store;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALProperties;
import com.tibco.cep.dashboard.psvr.mal.MISSING_ORIGINAL_ELEMENT_LOAD_POLICY;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.impl.ModelPackageImpl;
import com.tibco.cep.kernel.service.logging.Level;

public class FilePersistentStore extends XMIPersistentStore {

	private File storeRoot;
	private String namespace;

	private ResourceSet resourceSet;

	private MISSING_ORIGINAL_ELEMENT_LOAD_POLICY validationPolicy;

	public FilePersistentStore(PersistentStore parent, Identity identity) {
		super(parent, identity);
	}

	@Override
	protected void init() throws MALException {
		validationPolicy = MISSING_ORIGINAL_ELEMENT_LOAD_POLICY.valueOf(MALProperties.ORIG_ELEMENT_LOADING_POLICY.getValue(properties).toString().toUpperCase());
		resourceSet = new ResourceSetImpl();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		BEViewsConfigurationPackage.eINSTANCE.eClass();
		ModelPackageImpl.eINSTANCE.eClass();

//		// set the document builder factory property
//		if (StringUtil.isEmptyOrBlank(MALProperties.XML_PARSERS_FACTORY.getRawValue(properties)) == false) {
//			String xmlParserFactory = (String) MALProperties.XML_PARSERS_FACTORY.getValue(properties);
//			System.setProperty(MALProperties.XML_PARSERS_FACTORY.getName(), xmlParserFactory);
//		}

		// compute base folder path
		String root = (String) MALProperties.FILE_STORAGE_ROOT.getValue(properties);
		if (root == null) {
			throw new IllegalStateException("No value specified for " + MALProperties.FILE_STORAGE_ROOT.getName());
		}
		String dbName = (String) MALProperties.FILE_STORAGE_DB_NAME.getValue(properties);
		String folderPath = root + File.separator + dbName + File.separator;

		if (identity instanceof GlobalIdentity) {
			// folderpath/global
			namespace = "global" + File.separator;
		} else if (identity instanceof RoleIdentity) {
			// folderpath/roles/<rolename>
			namespace = "roles" + File.separator + identity.getId() + File.separator;
		} else if (identity instanceof UserIdentity) {
			// folderpath/users/<userid>/<rolename>
			namespace = "users" + File.separator + identity.getId() + File.separator + identity.getParent().getId() + File.separator;
		} else {
			throw new IllegalArgumentException("Identity other than role or user is not supported");
		}

		storeRoot = new File(folderPath + namespace);
		if (parent == null && storeRoot.exists() == false) {
			throw new MALException("Non existent store root [" + storeRoot + " for " + identity);
		}
		if (storeRoot.exists()) {
			loadContents(storeRoot);
		} else {
			storeRoot.mkdir();
		}

	}

	private void loadContents(File file) throws MALException {
		if (file.isDirectory() == true) {
			if (file.getName().equals(".svn") == true) {
				return;
			} else {
				// Process folder here
				File[] files = file.listFiles();
				for (File childFile : files) {
					loadContents(childFile);
				}
			}
		} else if (file.getName().equals(".project")) {
			// Not to process this
		} else if (file.getName().endsWith(".metric")) {
			// Not to process this
		} else {
			// Process file here
			URI fileURI = URI.createFileURI(file.getAbsolutePath());
			Resource resource = getResourceSet().getResource(fileURI, true);
			EList<EObject> contents = resource.getContents();
			for (EObject object : contents) {
				Entity entity = (Entity) object;
				boolean addToCache = checkForMissingOriginalElement(entity);
				if (addToCache == true) {
					updateURI(entity, generateReferenceableURI(entity));
					cacheIt(entity.getGUID(), entity.getName(), entity);
				}
			}
		}
	}

	private boolean checkForMissingOriginalElement(Entity entity) throws MALException {
		if (entity instanceof BEViewsElement) {
			BEViewsElement viewsElement = (BEViewsElement) entity;
			//get the original identifier
			String originalElementIdentifier = viewsElement.getOriginalElementIdentifier();
			Entity parentEntity = parent.loadElementByIdentitfier(originalElementIdentifier);
			//check if the parentEntity exists
			if (parentEntity == null) {
				//parentEntity does not exist
				String entityToString = toString(viewsElement);
				MessageGeneratorArgs msgArgs = new MessageGeneratorArgs("",identity.getId(),identity.getParent().getId(),null,null,entityToString,originalElementIdentifier);
				if (validationPolicy.compareTo(MISSING_ORIGINAL_ELEMENT_LOAD_POLICY.EXCEPTION) == 0) {
					//loading policy is exception, report exception and bail out
					throw new MALException(messageGenerator.getMessage("element.personalized.nonexistent.originalelement.ex", msgArgs));
				}
				//log a warning message
				if (validationPolicy.compareTo(MISSING_ORIGINAL_ELEMENT_LOAD_POLICY.DROP) == 0) {
					//loading policy is drop, we do not load the element
					logger.log(Level.WARN, messageGenerator.getMessage("element.personalized.nonexistent.originalelement.drop", msgArgs));
					return false;
				}
				//loading policy is retain, log message and let the system handle it
				logger.log(Level.WARN, messageGenerator.getMessage("element.personalized.nonexistent.originalelement.retain", msgArgs));
				return true;
			}
			return true;
		}
		return false;
	}

	private String toString(BEViewsElement element){
		StringBuilder sb = new StringBuilder(element.eClass().getName());
		sb.append("[id=");
		sb.append(element.getGUID());
		sb.append(",name=");
		sb.append(element.getName());
		sb.append("]");
		return sb.toString();
	}

	@Override
	protected ResourceSet getResourceSet() {
		return resourceSet;
	}

	@Override
	protected boolean deleteElement(Entity entity) {
		String extension = getExtension(entity);
		String filePath = entity.getNamespace() + File.separator + entity.getName() + "." + extension;
		File file = new File(storeRoot, filePath);
		if (logger.isEnabledFor(Level.DEBUG) == true){
			logger.log(Level.DEBUG,"Attempting to delete "+entity+" stored as "+file.getAbsolutePath());
		}
		boolean delete = file.delete();
		if (logger.isEnabledFor(Level.DEBUG) == true){
			if (delete == true){
				logger.log(Level.DEBUG,"Deleted "+file.getAbsolutePath());
			}
			else {
				logger.log(Level.DEBUG,"Failed to delete "+file.getAbsolutePath());
			}
		}
		return delete;
	}

	@Override
	protected void setDefaultProperties(Entity entity) {
		// INFO setting newly created entity's folder as identity.getId()
		entity.setFolder(namespace);
		// INFO setting newly created entity's namespace as computed namespace
		entity.setNamespace(namespace);
	}

	@Override
	protected URI generateStorageURI(Entity entity) {
		StringBuilder sb = new StringBuilder(storeRoot.toURI().toString());
		if (sb.charAt(sb.length()-1) != '/') {
			sb.append("/");
		}
//		sb.append(namespace);
//		if (sb.charAt(sb.length()-1) != '/') {
//			sb.append("/");
//		}
		sb.append(entity.getName());
		sb.append(".");
		sb.append(getExtension(entity));
		return URI.createURI(sb.toString());
	}

	@Override
	protected URI generateReferenceableURI(Entity entity) {
		return generateStorageURI(entity);
	}

	@Override
	protected URI getReferenceableRootURI() {
		throw new UnsupportedOperationException("getReferenceableRootURI");
	}

}
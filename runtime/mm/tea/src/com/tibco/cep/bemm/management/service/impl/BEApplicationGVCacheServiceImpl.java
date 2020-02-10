package com.tibco.cep.bemm.management.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipInputStream;

import org.xml.sax.InputSource;

import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.Constants.DD;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.service.BEApplicationGVCacheService;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.VFileHelper;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileFactory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/**
 * Implementation of BEApplicationGVCacheService
 * 
 * @author dijadhav
 *
 */
public class BEApplicationGVCacheServiceImpl extends AbstractStartStopServiceImpl
		implements BEApplicationGVCacheService<File> {
	private static final XiParser PARSER = XiParserFactory.newInstance();
	private Map<String, List<GlobalVariableDescriptor>> gvCache = new ConcurrentHashMap<String, List<GlobalVariableDescriptor>>();
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEApplicationGVCacheService.class);

	/**
	 * Default Constructor
	 */
	public BEApplicationGVCacheServiceImpl() {
	}

	@Override
	public void cacheGlobalDescriptorDetails(String applicationName, String earPath) throws Exception {
		LOGGER.log(Level.DEBUG, "Caching global variables data");
		gvCache.put(applicationName, collectServiceSettableGlobalVariables(earPath));
	}

	@Override
	public List<GlobalVariableDescriptor> getServiceSettableGlobalVariables(String applicationName,
			String applicationDataStoreLocation) throws Exception {
		LOGGER.log(Level.DEBUG, "Fetched cached GV data");
		if (!gvCache.containsKey(applicationName)) {
			String earPath = applicationDataStoreLocation + File.separator + applicationName + File.separator
					+ applicationName + ".ear";
			cacheGlobalDescriptorDetails(applicationName, earPath);
		}
		return gvCache.get(applicationName);

	}

	/**
	 * Get all the Service Set-able global variables
	 * 
	 * @param earPath
	 * @return
	 * @throws Exception
	 */
	private List<GlobalVariableDescriptor> collectServiceSettableGlobalVariables(String earPath) throws Exception {
		VFileFactory vfileFactory = VFileHelper.createVFileFactory(earPath, null);
		// Get the default global variables
		Collection<GlobalVariableDescriptor> defaultGlobalVariables = getDefaultGlobalVariables(vfileFactory);
		// Get the GV resource VFile
		VFile gvVFile = scanBARArchiveForGVResource(vfileFactory);
		// Get all the global variables
		Collection<GlobalVariableDescriptor> allGlobalVariables = getGlobalVariables("Runtime Variables",
				(VFileStream) gvVFile);
		// Add the default & service set-able global variables to the result
		// list
		List<GlobalVariableDescriptor> serviceSetableGlobalVariables = new ArrayList<>(defaultGlobalVariables);
		for (GlobalVariableDescriptor gvDescriptor : allGlobalVariables) {
			// Exclude CDD & PUID
			if ("CDD".equals(gvDescriptor.getName()) || "PUID".equals(gvDescriptor.getName())) {
				continue;
			}
			serviceSetableGlobalVariables.add(gvDescriptor);
		}
		return serviceSetableGlobalVariables;
	}

	private Collection<GlobalVariableDescriptor> getDefaultGlobalVariables(VFileFactory archiveFileFactory)
			throws Exception {
		// Get the GV resource VFile
		VFile gvVFile = scanEARArchiveForGVResource(archiveFileFactory);
		// Get all the Global variables
		Collection<GlobalVariableDescriptor> allGlobalVariables = getGlobalVariables("Global Variables",
				(VFileStream) gvVFile);
		// Get only the default global variables
		Collection<GlobalVariableDescriptor> defaultGlobalVariables = new ArrayList<>();
		for (GlobalVariableDescriptor gvDescriptor : allGlobalVariables) {
			if ("Deployment".equals(gvDescriptor.getName()) || "Domain".equals(gvDescriptor.getName())
					|| "MessageEncoding".equals(gvDescriptor.getName())) {
				defaultGlobalVariables.add(gvDescriptor);
			}
		}
		return defaultGlobalVariables;
	}

	private Collection<GlobalVariableDescriptor> getGlobalVariables(String headerName, VFileStream gvFileStream)
			throws Exception {
		final XiNode doc = PARSER.parse(new InputSource(gvFileStream.getInputStream()));
		final XiNode root = doc.getFirstChild();
		Map<String, GlobalVariableDescriptor> globalVariablesMap = new LinkedHashMap<String, GlobalVariableDescriptor>();
		for (Iterator<?> itr = XiChild.getIterator(root, Constants.DD.XNames.NAME_VALUE_PAIRS); itr.hasNext();) {
			final XiNode node = (XiNode) itr.next();
			final String name = XiChild.getChild(node, Constants.DD.XNames.NAME).getStringValue();
			if (headerName.equalsIgnoreCase(name)) {
				collectGlobalVariables(node, Constants.DD.XNames.NAME_VALUE_PAIR, globalVariablesMap);
				collectGlobalVariables(node, Constants.DD.XNames.NAME_VALUE_PAIR_INTEGER, globalVariablesMap);
				collectGlobalVariables(node, Constants.DD.XNames.NAME_VALUE_PAIR_BOOLEAN, globalVariablesMap);
				collectGlobalVariables(node, Constants.DD.XNames.NAME_VALUE_PAIR_PASSWORD, globalVariablesMap);
			}
		}
		return globalVariablesMap.values();
	}

	private void collectGlobalVariables(XiNode globalVariables, ExpandedName en,
			Map<String, GlobalVariableDescriptor> globalVariablesMap) {
		for (Iterator<?> it = XiChild.getIterator(globalVariables, en); it.hasNext();) {
			final XiNode gvNode = (XiNode) it.next();
			String name = XiChild.getString(gvNode, DD.XNames.NAME);
			String type = XiChild.getString(gvNode, DD.XNames.TYPE);
			if (type == null) {
				if (DD.XNames.NAME_VALUE_PAIR == en) {
					type = "String";
				} else if (DD.XNames.NAME_VALUE_PAIR_PASSWORD == en) {
					type = "Password";
				} else if (DD.XNames.NAME_VALUE_PAIR_BOOLEAN == en) {
					type = "Boolean";
				} else if (DD.XNames.NAME_VALUE_PAIR_INTEGER == en) {
					type = "Integer";
				}
			}
			final String value = XiChild.getString(gvNode, DD.XNames.VALUE);
			final boolean deploymentSettable = XiChild.getBoolean(gvNode, DD.XNames.DEPLOYMENT_SETTABLE, true);
			final boolean serviceSettable = XiChild.getBoolean(gvNode, DD.XNames.SERVICE_SETTABLE, true);
			final long modTime = XiChild.getLong(gvNode, DD.XNames.MOD_TIME, 0);
			final String description = "";
			final String constraint = "";

			int idx = name.lastIndexOf("/");
			String actualPath = "";
			if (idx != -1) {
				actualPath = name.substring(0, idx + 1);
				name = name.substring(idx + 1);
			}
			final GlobalVariableDescriptor gv = new GlobalVariableDescriptor(name, actualPath, value, type,
					deploymentSettable, serviceSettable, modTime, description, constraint);

			String key = gv.getFullName();
			if (globalVariablesMap.containsKey(key)) {
				gv.setOverridden(true);
			}
			globalVariablesMap.put(key, gv);
		}
	}

	private VFile scanEARArchiveForGVResource(VFileFactory vfileFactory) throws ObjectRepoException {
		VFileDirectory rootDirectory = vfileFactory.getRootDirectory();
		// Get the VFile for GV File (TIBCO.xml)
		VFile gvVFile = null;
		for (Iterator<?> itr = rootDirectory.getChildren(); itr.hasNext();) {
			VFile vff = (VFile) itr.next();
			if (vff instanceof VFileStream) {
				String fileURI = vff.getFullURI().toLowerCase();
				if (fileURI.endsWith("tibco.xml")) {
					gvVFile = vff;
					break;
				}
			}
		}
		return gvVFile;
	}

	private VFile scanBARArchiveForGVResource(VFileFactory vfileFactory) throws ObjectRepoException {
		VFileDirectory rootDirectory = vfileFactory.getRootDirectory();
		VFile gvVFile = null;
		for (Iterator<?> i = rootDirectory.getChildren(); i.hasNext();) {
			VFile vff = (VFile) i.next();
			if (vff instanceof VFileStream) {
				String uri = vff.getFullURI().toLowerCase();
				if (uri.endsWith(".bar")) {
					gvVFile = scanBARArchiveResource(vff);
					break;
				}
			}
		}
		return gvVFile;
	}

	private VFile scanBARArchiveResource(VFile barArchiveResourceVFile) throws ObjectRepoException {
		ZipVFileFactory vFileFactory = new ZipVFileFactory(
				new ZipInputStream(((VFileStream) barArchiveResourceVFile).getInputStream()));
		VFileDirectory rootDirectory = vFileFactory.getRootDirectory();
		VFile gvVFile = null;
		for (Iterator<?> itr = rootDirectory.getChildren(); itr.hasNext();) {
			VFile vff = (VFile) itr.next();
			if (vff instanceof VFileStream) {
				String fileURI = vff.getFullURI().toLowerCase();
				if (fileURI.endsWith("tibco.xml")) {
					gvVFile = vff;
					break;
				}
			}
		}
		return gvVFile;
	}

	@Override
	public void removeConfiguration(String applicationName) throws Exception {
		LOGGER.log(Level.DEBUG, "Remove configuration from cache for " + applicationName);
		gvCache.remove(applicationName);
	}
}

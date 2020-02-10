/**
 * 
 */
package com.tibco.cep.studio.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.archive.AdvancedEntitySetting;
import com.tibco.cep.designtime.core.model.archive.BusinessEventsArchiveResource;
import com.tibco.cep.designtime.core.model.archive.CACHE_MODE;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.util.EntityHolder;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.index.utils.RuleCreator;
import com.tibco.cep.studio.core.nature.StudioProjectNature;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;

/**
 * @author rmishra
 * 
 */
public class CommonUtil {
	
	public static final char FOLDER_SEPARATOR_CHAR = '/';
	public static final String DELIMITER = ",";

	/**
	 * TODO Aditya -> This should be unmodifiable from outside
	 * Also each object here should be weakly reachable
	 * 
	 */
	static final EnumMap<ELEMENT_TYPES, String> extensionsMap = 
					new EnumMap<ELEMENT_TYPES, String>(ELEMENT_TYPES.class);
	static {
		
		extensionsMap.put(ELEMENT_TYPES.CONCEPT, ".concept");
		extensionsMap.put(ELEMENT_TYPES.SCORECARD, ".scorecard");
		extensionsMap.put(ELEMENT_TYPES.SIMPLE_EVENT, ".event");		
		extensionsMap.put(ELEMENT_TYPES.TIME_EVENT, ".time");
		extensionsMap.put(ELEMENT_TYPES.RULE, ".rule");
		extensionsMap.put(ELEMENT_TYPES.RULE_FUNCTION, ".rulefunction");
		extensionsMap.put(ELEMENT_TYPES.RULE_SET, ".ruleset");
		extensionsMap.put(ELEMENT_TYPES.CHANNEL, ".channel");
		
	}

	public static XiNode getRootNode (final XiFactory xiFactory , final String absolutePath){
		XiNode node = xiFactory.createDocument(absolutePath);
		if (node != null){
			return node.getRootNode();
		}
		return null;
	}
	/**
	 * populated root nodes for all BE artifacts inside a parent resource
	 * @param parent
	 * @param entityRootNodeList
	 * @param docBuilder
	 * @throws Exception
	 */
	public static void getEntitiesRootNodeList(final IResource parent ,List<Node> entityRootNodeList , 
			                       final DocumentBuilder docBuilder)throws Exception{
		if (entityRootNodeList == null) throw new IllegalArgumentException("Entity Root Node List supplied is : Null");
		String root = parent.getWorkspace().getRoot().getLocation().toOSString();
		String fullPath = root + parent.getFullPath().toOSString();
		if (parent instanceof IProject){
			IProject project = (IProject)parent;
			for (IResource resource : project.members()){
				getEntitiesRootNodeList(resource, entityRootNodeList, docBuilder);
			}
		}
		else if (parent instanceof IFolder){
			IFolder folder = (IFolder)parent;
			for (IResource resource : folder.members()){
				getEntitiesRootNodeList(resource, entityRootNodeList , docBuilder);
			}
		}
		else if (parent instanceof IFile){
			IFile file = (IFile)parent;
			String extension = file.getFileExtension();
			//TODO Is there a need for separate check for each extension here?
			if ("concept".equalsIgnoreCase(extension)){
				Node node = getNode(fullPath, docBuilder);
				if (node != null){
					entityRootNodeList.add(node);
				}
				
				
			}
			else if ("event".equalsIgnoreCase(extension)){
				Node node = getNode(fullPath, docBuilder);
				if (node != null){
					entityRootNodeList.add(node);
				}
			}
			/*
			 * persistence is changed so will modify after it's populated inside ontology
			else if ("rule".equalsIgnoreCase(extension)){
				Node node = getNode(fullPath, docBuilder);
				if (node != null){
					entityRootNodeList.add(node);
				}
			}
			else if ("rulefunction".equalsIgnoreCase(extension)){
				Node node = getNode(fullPath, docBuilder);
				if (node != null){
					entityRootNodeList.add(node);
				}
			} */
			else if ("scorecard".equalsIgnoreCase(extension)){
				Node node = getNode(fullPath, docBuilder);
				if (node != null){
					entityRootNodeList.add(node);
				}
			}
			else if ("channel".equalsIgnoreCase(extension)){
				Node node = getNode(fullPath, docBuilder);
				if (node != null){
					entityRootNodeList.add(node);
				}
			}
			
		}
	}
	/**
	 * get root node for the document
	 * @param fullPath
	 * @param docBuilder
	 * @return
	 * @throws Exception
	 */
	private static Node getNode(final String fullPath , final DocumentBuilder docBuilder)throws Exception{
		InputSource source = new InputSource(new FileInputStream(fullPath));				
		Document doc = docBuilder.parse(source);
		if (doc == null) return null;
		Node rootNode = doc.getDocumentElement();
		return rootNode;
	}
	
	/**
	 * it returns entity as XML node
	 * @param file
	 * @param docBuilder
	 * @throws Exception
	 */
	
	public static Node getNodeForEntity(final File file , final DocumentBuilder docBuilder)throws Exception{
		if (file == null || docBuilder == null) throw new IllegalArgumentException("Either file or DocumentBuilder is null");
		String absPath = file.getAbsolutePath();
		if (absPath == null) return null;
		/*
		if (absPath.endsWith(".rule") || absPath.endsWith(".rulefunction")){
			return getNodeForRuleElements(file,docBuilder);
		}
		else {
		*/	InputSource source = new InputSource(new FileInputStream(file));				
			Document doc = docBuilder.parse(source);
			if (doc == null) return null;
			Node rootNode = doc.getDocumentElement();
			return rootNode;
			
		//}		
		
	}
	
	/**
	 * 
	 * @param file
	 * @param docBuilder
	 * @return
	 * @throws Exception
	 */
	public static Node getNodeForRuleElements(final File file , final Document doc)throws Exception{
		RuleCreator ruleCreator = new RuleCreator(true);
		Compilable compilable = ruleCreator.createRule(file, null);
		if (compilable instanceof RuleFunction){
			//Document doc = docBuilder.newDocument();
			return getRuleFunctionNode(file ,(RuleFunction)compilable, doc);
		}
		else if (compilable instanceof Rule){
			//Document doc = docBuilder.newDocument();
			return getRuleNode(file,(Rule)compilable,doc);
		}
		return null;
	}
	
	/**
	 * 
	 * @param file
	 * @param rf
	 * @param docbBuilder
	 * @return
	 * @throws Exception
	 */
	public static Node getRuleFunctionNode (final File file , final RuleFunction rf , final Document doc) throws Exception{
		
		Element ruleFunctionNode = doc.createElement("ruleFunction");
		//rulesNode.appendChild(ruleNode);
		String name = rf.getName();
		ruleFunctionNode.setAttribute("xmlns", "");
		ruleFunctionNode.setAttribute("name", name);
		String des = rf.getDescription();
		ruleFunctionNode.setAttribute("description", des);
		//String guid = rf.getGUID();
		//ruleFunctionNode.setAttribute("guild", guid);
		String folderName = rf.getFolder();
		ruleFunctionNode.setAttribute("folder", folderName);
		ruleFunctionNode.setAttribute("bindings", "");
		ruleFunctionNode.setAttribute("namespace", "");
		ruleFunctionNode.setAttribute("virtual", Boolean.toString(rf.isVirtual()));

		Element lastModifiedNode = doc.createElement("lastModified");
		ruleFunctionNode.appendChild(lastModifiedNode);
		lastModifiedNode.setTextContent(rf.getLastModified());
		Element extNode = doc.createElement("extendedProperties");
		ruleFunctionNode.appendChild(extNode);
		// May be there are extended properties of rules in future then we need to populate them too
		Element hiddenPropNode = doc.createElement("hiddenProperties");
		ruleFunctionNode.appendChild(hiddenPropNode);	
		Element validityNode = doc.createElement("validity");
		validityNode.setTextContent(rf.getValidity().getLiteral());
		ruleFunctionNode.appendChild(validityNode);
		Element returnTypeNode = doc.createElement("returnType");
		returnTypeNode.setTextContent(rf.getReturnType());
		ruleFunctionNode.appendChild(returnTypeNode);		
		// Declarations Node
		Element declsNode = doc.createElement("arguments");
		ruleFunctionNode.appendChild(declsNode);
		// populate declarations node		
		populateRFArgumentsNode(rf.getSymbols().getSymbolList(), declsNode, doc);

		// Body Node
		Element bodyNode = doc.createElement("body");
		ruleFunctionNode.appendChild(bodyNode);
		bodyNode.setTextContent(rf.getActionText());	
		return ruleFunctionNode;
	}
	/**
	 * 
	 * @param file
	 * @param rule
	 * @param docbBuilder
	 * @return
	 * @throws Exception
	 */
	public static Node getRuleNode (final File file , final Rule rule , final Document doc) throws Exception{
//		String folder = rule.getFolder();
		/*
		int indx = folder.lastIndexOf('/');
		String ruleSetName = null;
		if (indx != -1){
			ruleSetName = folder.substring(indx+1);
		}
		String ruleSetFolder = folder.substring(0,indx);
		Document doc = docBuilder.newDocument(); 
		Element ruleSetNode = doc.createElement("ruleset");
		doc.appendChild(ruleSetNode);
		ruleSetNode.setAttribute("name", ruleSetName);
		ruleSetNode.setAttribute("description", "");
		ruleSetNode.setAttribute("namespace", "");
		ruleSetNode.setAttribute("folder", ruleSetFolder);
		Element rulesNode = doc.createElement("rules");
		ruleSetNode.appendChild(rulesNode);		
		*/
		Element ruleNode = doc.createElement("rule");
		//rulesNode.appendChild(ruleNode);
		String name = rule.getName();
		ruleNode.setAttribute("name", name);
		String des = rule.getDescription();
		ruleNode.setAttribute("description", des);
		//String guid = rule.getGUID();
		//ruleNode.setAttribute("guild", guid);
		String folderName = rule.getFolder();
		ruleNode.setAttribute("folder", folderName);
		ruleNode.setAttribute("bindings", "");
		ruleNode.setAttribute("namespace", "");
		ruleNode.setAttribute("isAFunction", Boolean.toString(rule.isFunction()));
		ruleNode.setAttribute("isAConditionFunction", Boolean.toString(rule.isConditionFunction()));
		ruleNode.setAttribute("compilationStatus", ""+rule.getCompilationStatus());
		Element lastModifiedNode = doc.createElement("lastModified");
		ruleNode.appendChild(lastModifiedNode);
		lastModifiedNode.setTextContent(rule.getLastModified());
		Element extNode = doc.createElement("extendedProperties");
		ruleNode.appendChild(extNode);
		// May be there are extended properties of rules in future then we need to populate them too
		Element hiddenPropNode = doc.createElement("hiddenProperties");
		ruleNode.appendChild(hiddenPropNode);
		Element priorityNode = doc.createElement("priority");
		ruleNode.appendChild(priorityNode);
		priorityNode.setTextContent(""+rule.getPriority());
		Element testIntervalNode = doc.createElement("testInterval");
		testIntervalNode.setTextContent(rule.getTestInterval()+"");
		ruleNode.appendChild(testIntervalNode);
		Element startTimeNode = doc.createElement("startTime");
		startTimeNode.setTextContent(rule.getStartTime()+"");
		ruleNode.appendChild(startTimeNode);
		Element doesRequeueNode = doc.createElement("doesRequeue");
		doesRequeueNode.setTextContent(Boolean.toString(rule.isRequeue()));
		ruleNode.appendChild(doesRequeueNode);
		Element maxRulesNode = doc.createElement("maxRules");
		ruleNode.appendChild(maxRulesNode);
		maxRulesNode.setTextContent(rule.getMaxRules()+"");
		Element forwardChainNode = doc.createElement("fwdChain");
		ruleNode.appendChild(forwardChainNode);
		forwardChainNode.setTextContent(Boolean.toString(rule.isForwardChain()));
		Element backwardChainNode = doc.createElement("bwdChain");
		ruleNode.appendChild(backwardChainNode);
		backwardChainNode.setTextContent(Boolean.toString(rule.isBackwardChain()));
		// Declarations Node
		Element declsNode = doc.createElement("declarations");
		ruleNode.appendChild(declsNode);
		// populate declarations node
		populateRuleDeclarationsNode(rule.getSymbols().getSymbolList(), declsNode, doc);
		// Condition Node
		Element conditionNode = doc.createElement("condition");
		ruleNode.appendChild(conditionNode);
		conditionNode.setTextContent(rule.getConditionText());
		// Action Node
		Element actionNode = doc.createElement("action");
		ruleNode.appendChild(actionNode);
		actionNode.setTextContent(rule.getActionText());
		
		Element reqVarsNode = doc.createElement("requeueVars");
		ruleNode.appendChild(reqVarsNode);
		StringBuilder sb = new StringBuilder("");
		int counter =1 ;
		int size = rule.getRequeueVars().size();
		for (String reqVar : rule.getRequeueVars()){
			sb.append(reqVar);
			if (counter == size) continue;
			sb.append(";");			
			counter ++ ;
		}		
		reqVarsNode.setTextContent(sb.toString());
		
		Element templateNode = doc.createElement("template");
		ruleNode.appendChild(templateNode);
		// FIXME if required
		templateNode.setTextContent("");
		
		Element authorNode = doc.createElement("author");
		ruleNode.appendChild(authorNode);
		String author = rule.getAuthor();
		author = author == null ? "" : author;
		authorNode.setTextContent(author);
		
	
		return ruleNode;
	}
	/**
	 * 
	 * @param symbolList
	 * @param declsNode
	 * @param docBuilder
	 */
	
	public static void populateRuleDeclarationsNode(final Collection<Symbol> symbolList ,final Element declsNode, final Document doc ){
		
		if (symbolList == null || declsNode == null || doc == null) throw new IllegalArgumentException("one of parameters is -->null");
		
		for (Symbol symbol : symbolList){
			String identifier = symbol.getIdName();
			String entity = symbol.getType();
			String entityType = symbol.getTypeExtension();
			entityType = entityType == null ? "" :entityType;
			Element declNode = doc.createElement("declaration");
			declsNode.appendChild(declNode);
			declNode.setAttribute("identifier", identifier);
			declNode.setAttribute("entity", entity);
			declNode.setAttribute("entityType", entityType);
			Domain domain = symbol.getDomain();
			if (domain != null){
				Element domainNode = doc.createElement("domain");
				declNode.appendChild(domainNode);
				Element entriesNode = doc.createElement("entries");
				domainNode.appendChild(entriesNode);
				/**
				 * @FIXME put domain entries
				 */
//				for (DomainEntry domainEntry : domain.getEntries()){
//					
//				}
			}
			
			
		}
		
		
	}
	
	public static void populateRFArgumentsNode (final Collection<Symbol> symbolList ,final Element declsNode, final Document doc){
		if (symbolList == null || declsNode == null || doc == null) throw new IllegalArgumentException("one of parameters is -->null");
		
		for (Symbol symbol : symbolList){
			String identifier = symbol.getIdName();
			String entity = symbol.getType();
			String entityType = symbol.getTypeExtension();
			entityType = entityType == null ? "" :entityType;
			Element declNode = doc.createElement("argument");
			declsNode.appendChild(declNode);
			declNode.setAttribute("identifier", identifier);
			declNode.setAttribute("entity", entity);
			declNode.setAttribute("entityType", entityType);
			Domain domain = symbol.getDomain();
			if (domain != null){
				Element domainNode = doc.createElement("domain");
				declNode.appendChild(domainNode);
				Element entriesNode = doc.createElement("entries");
				domainNode.appendChild(entriesNode);
				/**
				 * @FIXME put domain entries
				 */
//				for (DomainEntry domainEntry : domain.getEntries()){
//					
//				}
			}
			
			
		}
	}
	/**
	 * populates all the rules inside a project
	 * @param parent
	 * @param ruleSetList
	 * @throws Exception
	 */
	public static void populateRuleSetList(final IResource parent , Set<String> ruleSets) throws Exception{
		if (ruleSets == null) throw new IllegalArgumentException("rule Set list supplied is : Null");
		if (parent instanceof IProject){
			IProject project = (IProject)parent;
			for (IResource resource : project.members()){
				populateRuleSetList(resource, ruleSets);
			}
		}
		else if (parent instanceof IFolder){
			IFolder folder = (IFolder)parent;
			for (IResource resource : folder.members()){
				populateRuleSetList(resource, ruleSets);
			}
		}
		else if (parent instanceof IFile){
			IFile file = (IFile)parent;			
			String extension = file.getFileExtension();
			if ("rule".equalsIgnoreCase(extension)){
				IContainer container = file.getParent();
				if (parent != null){
					String parentPath = container.getFullPath().toOSString();
					// remove all leading slashes
					while (parentPath.startsWith(File.separator)){
						parentPath = parentPath.substring(File.separator.length());
					}
					String projectName = file.getProject().getName();
					if (projectName != null){
						parentPath = parentPath.substring(projectName.length()).trim();
					}
					ruleSets.add(parentPath);
				}
				
			}
		}
		
	}
	/*
	public static String getFileContent(final String fullPath){
		if (fullPath == null) return null;
		ResourceSet rs = new ResourceSetImpl();
		URI uri = URI.createFileURI(fullPath);
		Resource resource = rs.getResource(uri, true);
		return null;
	}
	*/
	/**
	 * returns Adavance setting as a Node from business events archive resource
	 */
	public static Node getAdvanceSettingNode(final BusinessEventsArchiveResource bear , final Document doc){
		List<AdvancedEntitySetting> advanceSettings = bear.getAdvancedSettings();
		Element omtgAdvanceSettings = null;
		for (AdvancedEntitySetting setting : advanceSettings){
			if (omtgAdvanceSettings == null){
				omtgAdvanceSettings = doc.createElementNS("","omtgAdvancedEntitySettings");
			}
			Element row = doc.createElement("row");
			omtgAdvanceSettings.appendChild(row);			
			CACHE_MODE cacheMode = setting.getCacheMode();
			String entityURI = setting.getEntity();
			String recoveryFunction = setting.getRecoveryFunction();
			boolean isDeployed = setting.isDeployed();
			Element entity = doc.createElement("uri");
			entity.setTextContent(entityURI);
			row.appendChild(entity);
			Element deployed = doc.createElement("deployed");
			deployed.setTextContent(Boolean.toString(isDeployed));
			row.appendChild(deployed);
			Element chMode = doc.createElement("cacheMode");
			chMode.setTextContent(cacheMode.getLiteral());
			row.appendChild(chMode);
			Element recovFn = doc.createElement("recoveryFunction");
			recovFn.setTextContent(recoveryFunction);
			row.appendChild(recovFn);
			
		}
		
		return omtgAdvanceSettings;
	}
	/**
	 * get Entity holder by Path recursively  
	 * @param parent
	 * @param path
	 * @param entityHolder
	 */
	/*
	public static void getEntityHolderByPath(final IResource parent,
			final String path, final EntityHolder entityHolder) {
	
		try {
			if (parent instanceof IProject) {
				IProject project = (IProject) parent;
				for (IResource member : project.members()) {
					getEntityHolderByPath(member, path, entityHolder);
				}
			} else if (parent instanceof IFolder) {
				IFolder folder = (IFolder) parent;
				for (IResource member : folder.members()) {
					getEntityHolderByPath(member, path, entityHolder);
				}
			} else if (parent instanceof IFile) {
				IFile file = (IFile) parent;
				// load that file
				String ext = file.getFileExtension();
				String relPath = file.getFullPath().toOSString();
				String str = null;
				int indx = relPath.lastIndexOf(ext);
				if (indx != -1){
					str = relPath.substring(0,indx-1);
				}
				else 
				{
					str = relPath;
				}
				while (str.startsWith(File.separator)){
					str = str.substring(File.separator.length());
				}
				str = str.substring(file.getProject().getName().length());
				
				if (str.equals(path)) {
					String root = file.getWorkspace().getRoot().getLocation()
							.toOSString();
					String absPath = root + relPath;
					EObject eobject = loadEntity(absPath);
					if (eobject instanceof Entity) {
						Entity entity = (Entity) eobject;
						if (entityHolder != null) {
							entityHolder.getEntity().add(entity);
						}
					}

				}

			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	*/
	/**
	 * return Entity relative to the parent , 
	 * @param parent
	 * @param path --> relative path with respect to parent
	 * @param entityType , concept , event etc
	 */
	
	public static Entity getEntityByPath (final IResource parent ,String path ,final ELEMENT_TYPES entityType) {
		if (parent == null || path == null || path.trim().equals("") || entityType == null) return null;
		path = path.replace('/', File.separatorChar);
		// remove trailing slashes if any
		if (path.endsWith(File.separator)){
			while (path.endsWith(File.separator)){
				path = path.substring(0,path.length()-(File.separator.length()));
			}
		}
		// check if entity type is rule function or rule 
		if (ELEMENT_TYPES.RULE_FUNCTION == entityType || ELEMENT_TYPES.RULE == entityType){
//			StudioModelManager dm = StudioCorePlugin.getDesignerModelManager();
			StudioProjectCache dm = StudioProjectCache.getInstance();
			if (dm != null){
				if (!(parent instanceof IProject)) throw new IllegalArgumentException("parent, IProject expected **");
				DesignerProject dsgProject = dm.getIndex(parent.getName());
				if (dsgProject != null){
					List<RuleElement> ruleElements = dsgProject.getRuleElements();
					for (RuleElement re : ruleElements){
						String folder = re.getFolder();
						String name = re.getName();
						String ruleElementPath = folder + name ;
						if (path.equals(ruleElementPath)){
							Compilable compilable = re.getRule();
							return compilable ;
						}
					}
				}
			}
		}
		
		String fileExtension = IndexUtils.getFileExtension(entityType);		
		if (fileExtension == null) throw new IllegalArgumentException("Not Supported operation for path :"+path);
		String relPath = path + "." +fileExtension;
		if (parent instanceof IProject){
			IProject project = (IProject)parent;			
			IFile file = project.getFile(new Path(relPath));
			if (file != null){
				String root = file.getWorkspace().getRoot().getLocation().toOSString();
				EObject eobject = loadEntity(root + file.getFullPath().toOSString());
				if (eobject instanceof Entity){
					return (Entity)eobject;
				}
				
			}
	
		}
		else if (parent instanceof IFolder){
			IFolder folder = (IFolder)parent;
			IFile file = folder.getFile(new Path(relPath));
			if (file != null){
				String root = file.getWorkspace().getRoot().getLocation().toOSString();
				EObject eobject = loadEntity(root + file.getFullPath().toOSString());
				if (eobject instanceof Entity){
					return (Entity)eobject;
				}
				
			}
		}
		else if (parent instanceof IFile){
			String resPath = parent.getProjectRelativePath().toOSString();
			if (path.equals(resPath)){
				String root = parent.getWorkspace().getRoot().getLocation().toOSString();				
				EObject eobject = loadEntity(root + parent.getFullPath().toOSString());
				if (eobject instanceof Entity){
					return (Entity)eobject;
				}
			}
		}
		return null;
		
		
	}
	
	public static EObject loadEObject(IFile file){
		if (file == null) return null;
		return loadEObject(file.getLocation().toOSString());
	}

	public static EObject loadEObject(String absPath) {
		URI uri = URI.createFileURI(absPath);
		ResourceSet rs = new ResourceSetImpl();
		Resource res = rs.getResource(uri, true);
		for (EObject eobject : res.getContents()){
			return eobject;
		}
		return null;
	}
	/**
	 * loads entity by absolute Path
	 */
	public static EObject loadEntity (final String absPath){
		if (absPath == null) return null;
		URI uri = URI.createFileURI(absPath);
		ResourceSet rs = new ResourceSetImpl();
		Resource res = rs.getResource(uri, true);
		for (EObject eobject : res.getContents()){
			if (eobject instanceof Entity){
				return (Entity)eobject;
				
			}
		}
		return null;
	}
	/**
	 * populate Entity holder by name . It searches recursively
	 * @param parent
	 * @param entityName
	 * @param entityHolder
	 */
	public static void getEntityHolderByName(final IResource parent,
			final String entityName, final EntityHolder entityHolder) {
		try {
			if (parent instanceof IProject) {
				IProject project = (IProject) parent;
				for (IResource member : project.members()) {
					getEntityHolderByName(member, entityName, entityHolder);
				}
			} else if (parent instanceof IFolder) {
				IFolder folder = (IFolder) parent;
				for (IResource member : folder.members()) {
					getEntityHolderByName(member, entityName, entityHolder);
				}
			} else if (parent instanceof IFile) {
				IFile file = (IFile) parent;
				// load that file
				String relPath = file.getFullPath().toOSString();
				String extension = file.getFileExtension();
				String root = file.getWorkspace().getRoot().getLocation()
						.toOSString();
				String absPath = root + relPath;
				if ("concept".equalsIgnoreCase(extension)) {
					int lastIndx = relPath.lastIndexOf(File.separatorChar);
					String nameWithExtension = relPath.substring(lastIndx + 1);
					int extLength = ".concept".length();
					int reqlength = nameWithExtension.length() - extLength;
					String name = nameWithExtension.substring(0, reqlength);
					if (name != null && name.equals(entityName)) {
						EObject eobject = loadEntity(absPath);
						if (eobject instanceof Entity) {
							Entity entity = (Entity) eobject;

							if (entityHolder != null) {
								entityHolder.getEntity().add(entity);
							}
						}
						
					}

				} else if ("scorecard".equalsIgnoreCase(extension)) {
					int lastIndx = relPath.lastIndexOf(File.separatorChar);
					String nameWithExtension = relPath.substring(lastIndx + 1);
					int extLength = ".scorecard".length();
					int reqlength = nameWithExtension.length() - extLength;
					String name = nameWithExtension.substring(0, reqlength);
					if (name != null && name.equals(entityName)) {
						EObject eobject = loadEntity(absPath);
						if (eobject instanceof Entity) {
							Entity entity = (Entity) eobject;

							if (entityHolder != null) {
								entityHolder.getEntity().add(entity);
							}
						}

					}
					
				} else if ("event".equalsIgnoreCase(extension)) {
					int lastIndx = relPath.lastIndexOf(File.separatorChar);
					String nameWithExtension = relPath.substring(lastIndx + 1);
					int extLength = ".event".length();
					int reqlength = nameWithExtension.length() - extLength;
					String name = nameWithExtension.substring(0, reqlength);
					if (name != null && name.equals(entityName)) {
						EObject eobject = loadEntity(absPath);
						if (eobject instanceof Entity) {
							Entity entity = (Entity) eobject;

							if (entityHolder != null) {
								entityHolder.getEntity().add(entity);
							}
						}

					}
					
				} 
				 else if (CommonIndexUtils.TIME_EXTENSION.equalsIgnoreCase(extension)) {
						int lastIndx = relPath.lastIndexOf(File.separatorChar);
						String nameWithExtension = relPath.substring(lastIndx + 1);
						int extLength = ("."+CommonIndexUtils.TIME_EXTENSION).length();
						int reqlength = nameWithExtension.length() - extLength;
						String name = nameWithExtension.substring(0, reqlength);
						if (name != null && name.equals(entityName)) {
							EObject eobject = loadEntity(absPath);
							if (eobject instanceof Entity) {
								Entity entity = (Entity) eobject;

								if (entityHolder != null) {
									entityHolder.getEntity().add(entity);
								}
							}

						}
						
					}
				else if ("rule".equalsIgnoreCase(extension)) {
					int lastIndx = relPath.lastIndexOf(File.separatorChar);
					String nameWithExtension = relPath.substring(lastIndx + 1);
					int extLength = ".rule".length();
					int reqlength = nameWithExtension.length() - extLength;
					String name = nameWithExtension.substring(0, reqlength);
					if (name != null && name.equals(entityName)) {
						EObject eobject = loadEntity(absPath);
						if (eobject instanceof Entity) {
							Entity entity = (Entity) eobject;

							if (entityHolder != null) {
								entityHolder.getEntity().add(entity);
							}

						}

					}
					
				} else if ("rulefunction".equalsIgnoreCase(extension)) {
					int lastIndx = relPath.lastIndexOf(File.separatorChar);
					String nameWithExtension = relPath.substring(lastIndx + 1);
					int extLength = ".rulefunction".length();
					int reqlength = nameWithExtension.length() - extLength;
					String name = nameWithExtension.substring(0, reqlength);
					if (name != null && name.equals(entityName)) {
						EObject eobject = loadEntity(absPath);

						if (eobject instanceof Entity) {
							Entity entity = (Entity) eobject;

							if (entityHolder != null) {
								entityHolder.getEntity().add(entity);

							}
						}
					}
					
				} else if ("channel".equalsIgnoreCase(extension)) {
					int lastIndx = relPath.lastIndexOf(File.separatorChar);
					String nameWithExtension = relPath.substring(lastIndx + 1);
					int extLength = ".channel".length();
					int reqlength = nameWithExtension.length() - extLength;
					String name = nameWithExtension.substring(0, reqlength);
					if (name != null && name.equals(entityName)) {
						EObject eobject = loadEntity(absPath);
						if (eobject instanceof Entity) {
							Entity entity = (Entity) eobject;
							if (entityHolder != null) {
								entityHolder.getEntity().add(entity);
							}

						}
					}
					
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	/**
	 * get concepts by name , it searches recursively
	 * @param parent
	 * @param name
	 * @param entityHolder
	 */
	public static void getConcept(final IResource parent, String name,
			EntityHolder entityHolder) {
		try {
			if (entityHolder == null)
				entityHolder = new EntityHolder();
			if (parent instanceof IProject) {
				IProject project = (IProject) parent;
				for (IResource member : project.members()) {
					getConcept(member, name, entityHolder);
				}
			} else if (parent instanceof IFolder) {
				IFolder folder = (IFolder) parent;
				for (IResource member : folder.members()) {
					getConcept(member, name, entityHolder);
				}
			} else if (parent instanceof IFile) {
				IFile file = (IFile) parent;
				// load that file
				String relPath = file.getFullPath().toOSString();
				String extension = file.getFileExtension();
				String root = file.getWorkspace().getRoot().getLocation()
						.toOSString();
				String absPath = root + relPath;
				if ("concept".equalsIgnoreCase(extension)) {
					int lastIndx = relPath.lastIndexOf(File.separatorChar);
					String nameWithExtension = relPath.substring(lastIndx + 1);
					int extLength = ".concept".length();
					int reqlength = nameWithExtension.length() - extLength;
					String conceptName = nameWithExtension.substring(0, reqlength);
					if (conceptName.equals(name)){
						EObject eobject = loadEntity(absPath);
						Entity entity = (Entity) eobject;
						if (entityHolder != null) {
							entityHolder.getEntity().add(entity);
						}
					}
					
		

				}

			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	/**
	 * get events by name , it searches recursively
	 * @param parent
	 * @param name
	 * @param entityHolder
	 */
	public static void getEvent(final IResource parent, final String name,
			EntityHolder entityHolder) {
		try {
			if (entityHolder == null)
				entityHolder = new EntityHolder();
			if (parent instanceof IProject) {
				IProject project = (IProject) parent;
				for (IResource member : project.members()) {
					getEvent(member, name, entityHolder);
				}
			} else if (parent instanceof IFolder) {
				IFolder folder = (IFolder) parent;
				for (IResource member : folder.members()) {
					getEvent(member, name, entityHolder);
				}
			} else if (parent instanceof IFile) {
				IFile file = (IFile) parent;
				// load that file
				String relPath = file.getFullPath().toOSString();
				String extension = file.getFileExtension();
				String root = file.getWorkspace().getRoot().getLocation()
						.toOSString();
				String absPath = root + relPath;
				if ("event".equalsIgnoreCase(extension)) {
					int lastIndx = relPath.lastIndexOf(File.separatorChar);
					String nameWithExtension = relPath.substring(lastIndx + 1);
					int extLength = ".event".length();
					int reqlength = nameWithExtension.length() - extLength;
					String eventName = nameWithExtension.substring(0, reqlength);
					if (eventName.equals(name)){
						EObject eobject = loadEntity(absPath);
						Entity entity = (Entity) eobject;
						if (entityHolder != null) {
							entityHolder.getEntity().add(entity);
						}
					}

				}

			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * populate all rule functions inside a parent resource
	 * @param parent
	 * @param ruleFunctionList
	 */
	/*
	public static void populateRuleFunctionList(final IResource parent,
			final List<RuleFunction> ruleFunctionList) {
		try {
		
			if (parent instanceof IProject) {
				IProject project = (IProject) parent;
				for (IResource member : project.members()) {
					populateRuleFunctionList(member, ruleFunctionList);
				}
			} else if (parent instanceof IFolder) {
				IFolder folder = (IFolder) parent;
				for (IResource member : folder.members()) {
					populateRuleFunctionList(member, ruleFunctionList);
				}
			} else if (parent instanceof IFile) {
				IFile file = (IFile)parent;				
				String ext = file.getFileExtension();
				String relPath = file.getFullPath().toOSString();
				String root = parent.getWorkspace().getRoot().getLocation().toOSString();
				if ("rulefunction".equals(ext)){
					EObject eobject = loadEntity(root + relPath);
					if (eobject instanceof RuleFunction){
						ruleFunctionList.add((RuleFunction)eobject);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	/**
	 * it return all rule fnction inside ontology
	 * @param project
	 * @return
	 */
	
	public static List<RuleFunction> getRuleFunctionList (final IProject project ){
		if (project == null) return null;
		List<RuleFunction> ruleFunctionList = new ArrayList<RuleFunction>();
		DesignerProject dsgProject = StudioProjectCache.getInstance().getIndex(project.getName());
		if (dsgProject != null){
			List<RuleElement> ruleElements = dsgProject.getRuleElements();
			for (RuleElement re : ruleElements){
				ELEMENT_TYPES elemntType = re.getElementType();
				if (ELEMENT_TYPES.RULE_FUNCTION == elemntType){
					Compilable compilable = re.getRule();
					if (compilable instanceof RuleFunction){
						ruleFunctionList.add((RuleFunction)compilable);
					}
				}

			}
		}
		return ruleFunctionList;
	}
	
	/**
	 * get all rules inside ontology
	 * @param project
	 * @return
	 */
	public static List<Rule> getRuleList (final IProject project){
		if (project == null) return null;
		List<Rule> ruleList = new ArrayList<Rule>();
		DesignerProject dsgProject = StudioProjectCache.getInstance().getIndex(project.getName());
		if (dsgProject != null){
			List<RuleElement> ruleElements = dsgProject.getRuleElements();
			for (RuleElement re : ruleElements){
				ELEMENT_TYPES elemntType = re.getElementType();
				if (ELEMENT_TYPES.RULE == elemntType){
					Compilable compilable = re.getRule();
					if (compilable instanceof Rule){
						ruleList.add((Rule)compilable);
					}
				}

			}
		}
		return ruleList;
	}
	
	/**
	 * populate all entities inside a parent resource
	 * @param parent
	 * @param entityList
	 */
	public static void populateAllEntities(final IResource parent , final List<Entity> entityList,boolean fIncludeBodyText){
		if (entityList == null || parent == null) return ;
		try {
			if (parent instanceof IProject) {
				IProject project = (IProject) parent;
				for (IResource member : project.members()) {
					populateAllEntities(member, entityList,fIncludeBodyText);
				}
			} else if (parent instanceof IFolder) {
				IFolder folder = (IFolder) parent;
				for (IResource member : folder.members()) {
					populateAllEntities(member, entityList,fIncludeBodyText);
				}
			} else if (parent instanceof IFile) {
				IFile file = (IFile) parent;
				// load that file
				String relPath = file.getFullPath().toOSString();
				String extension = file.getFileExtension();
				String root = file.getWorkspace().getRoot().getLocation()
						.toOSString();
				String absPath = root + relPath;
				if ("concept".equalsIgnoreCase(extension)) {		
					EObject eobject = loadEntity(absPath);
					if (eobject instanceof Entity) {
						Entity entity = (Entity) eobject;
						entityList.add(entity);
					}
		
			
				} else if ("scorecard".equalsIgnoreCase(extension)) {
					EObject eobject = loadEntity(absPath);
					if (eobject instanceof Entity) {
						Entity entity = (Entity) eobject;
						entityList.add(entity);
					}
				} else if ("event".equalsIgnoreCase(extension)) {
					EObject eobject = loadEntity(absPath);
					if (eobject instanceof Entity) {
						Entity entity = (Entity) eobject;
						entityList.add(entity);
					}
				} 
				 else if (CommonIndexUtils.TIME_EXTENSION.equalsIgnoreCase(extension)) {
						EObject eobject = loadEntity(absPath);
						if (eobject instanceof Entity) {
							Entity entity = (Entity) eobject;
							entityList.add(entity);
						}
					} 
				else if ("rule".equalsIgnoreCase(extension)) {
					/*
					EObject eobject = loadEntity(absPath);
					if (eobject instanceof Entity) {
						Entity entity = (Entity) eobject;
						entityList.add(entity);
					}
					*/
					Compilable compilable = new RuleCreator(fIncludeBodyText).createRule(file);
					entityList.add(compilable);
				} else if ("rulefunction".equalsIgnoreCase(extension)) {
					/*
					EObject eobject = loadEntity(absPath);
					if (eobject instanceof Entity) {
						Entity entity = (Entity) eobject;
						entityList.add(entity);
					}
					*/
					Compilable compilable = new RuleCreator(fIncludeBodyText).createRule(file);
					entityList.add(compilable);
				} else if ("channel".equalsIgnoreCase(extension)) {
					EObject eobject = loadEntity(absPath);
					if (eobject instanceof Entity) {
						Entity entity = (Entity) eobject;
						entityList.add(entity);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	/**
	 * populate all rules/rule functions inside a parent resource
	 * @param parent
	 * @param entityList
	 */
	public static void populateRuleEntities(final IResource parent , final List<Entity> entityList,boolean fIncludeBodyText){
		if (entityList == null || parent == null) return ;
		try {
			if (parent instanceof IProject) {
				IProject project = (IProject) parent;
				for (IResource member : project.members()) {
					populateRuleEntities(member, entityList,fIncludeBodyText);
				}
			} else if (parent instanceof IFolder) {
				IFolder folder = (IFolder) parent;
				for (IResource member : folder.members()) {
					populateRuleEntities(member, entityList,fIncludeBodyText);
				}
			} else if (parent instanceof IFile) {
				IFile file = (IFile) parent;
				// load that file			
				String extension = file.getFileExtension();				
				if ("rule".equalsIgnoreCase(extension)) {			
					Compilable compilable = new RuleCreator(fIncludeBodyText).createRule(file);
					entityList.add(compilable);
				} else if ("rulefunction".equalsIgnoreCase(extension)) {	
					Compilable compilable = new RuleCreator(fIncludeBodyText).createRule(file);
					entityList.add(compilable);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	/**
	 * populate properties for a concept
	 * @param project
	 * @param concept
	 * @param propDefList
	 */
	public static void getAllConceptProperties(final IProject project , Concept concept , final List<PropertyDefinition> propDefList){
		if (concept == null) return;
		propDefList.addAll(concept.getProperties());
		String superConceptPath = concept.getSuperConceptPath();		
		if (superConceptPath == null) return;		
		while (superConceptPath != null && !superConceptPath.trim().equals("")){
			superConceptPath = superConceptPath.replace('/',File.separatorChar);
			//EntityHolder entityHolder = new EntityHolder();
			//getEntityHolderByPath(project, superConceptPath, entityHolder);
			Entity entity = getEntityByPath(project, superConceptPath, ELEMENT_TYPES.CONCEPT);
			if (entity instanceof Concept){
				Concept	superConcept = (Concept)entity;
				propDefList.addAll(superConcept.getProperties());
				superConceptPath = superConcept.getSuperConceptPath();
			}
		}
	}

	/**
	 * populate properties for an event
	 * @param project
	 * @param event
	 * @param propDefList
	 */
	public static void getAllEventProperties(final IProject project , Event event , final List<PropertyDefinition> propDefList){
		if (event == null) return;
		propDefList.addAll(event.getProperties());
		String superEventPath = event.getSuperEventPath();		
		superEventPath = superEventPath.replace('/', File.separatorChar);
		ELEMENT_TYPES entityType = IndexUtils.getElementType(event);
		if (superEventPath == null) return;
		while (superEventPath != null && !superEventPath.trim().equals("")){
			//EntityHolder entityHolder = new EntityHolder();
			//getEntityHolderByPath(project, superEventPath, entityHolder);
			Entity entity = getEntityByPath(project, superEventPath, entityType);
			if (entity instanceof Event){
				Event superEvent = (Event)entity;
				propDefList.addAll(superEvent.getProperties());
				superEventPath = superEvent.getSuperEventPath();
			}
		}
	}

	/**
	 * get Entity by Path
	 * @param entityPath with Extension , no need for folders
	 * @param project
	 * @return
	 */
	/*
	public static Entity getEntity(String entityPath , final IProject project){
		if (entityPath == null || project == null) return null;
		entityPath = entityPath.replace('/', File.separatorChar);
		//EntityHolder entityHolder = new EntityHolder();
		Entity entity = getEntityByPath(project, entityPath);
		return entity;
	}
	*/
	/**
	 * return entity by path with respect to parent
	 * @param parent
	 * @param path -> relative path to parent , with extension , if extension is not provided
	 * then it will look for possible extensions and return the first matching one
	 * @return
	 */
	
	public static Entity getEntityByPath(final IResource parent , String path){
		if (parent == null || path == null) return null;
		path = path.replace('/', File.separatorChar);
		// remove all leading and trailing slashes
		if (path.endsWith(File.separator)){
			while (path.endsWith(File.separator)){
				path = path.substring(0,path.length()-(File.separator.length()));
			}
		}	
		if (path.startsWith(File.separator)){
			while (path.startsWith(File.separator)){
				path = path.substring(File.separator.length());
			}
		}
		StringTokenizer st = new StringTokenizer(path , File.separator);
		IResource next = parent;	
		Collection<String> extensionList = extensionsMap.values();
		while (st.hasMoreTokens()){
			String token = st.nextToken().trim();
			if (next instanceof IProject){
				IProject project = (IProject)next;
				next = project.findMember(token);
				if (next == null){
					// it's a file but extension is not known 
					// so try with all extensions
					
					for (String ext : extensionList){
						next = project.findMember(token + ext);
						if (next != null) break;
					}
				}
				
			}
			else if (next instanceof IFolder){
				IFolder folder = (IFolder)next;				
				next = folder.findMember(token);	
				// it's a file but extension is not known 
				// so try with all extensions
				if (next == null){
					for (String ext : extensionList){
						next = folder.findMember(token + ext);
						if (next != null) break;
					}
				}
			}
			
		}
		if (next instanceof IFile){
			IFile file = (IFile)next;			
			String root = file.getWorkspace().getRoot().getLocation().toOSString();
			EObject eobject = loadEntity(root + file.getFullPath().toOSString());
			if (eobject instanceof Entity){
				return (Entity)eobject;
			}				
			
		}
		return null;
	}

	public static List<EntityElement> getDesignerElementsByName(String path ,IProject project){
		List<EntityElement> entList = new ArrayList<EntityElement>();
		if (path == null || project == null) return entList;
		DesignerProject dp = StudioProjectCache.getInstance().getIndex(project.getName());
		if (dp == null) return entList;
		List<EntityElement> entityElements = dp.getEntityElements();
		for (EntityElement ee : entityElements){			
			if (path.equals(ee.getName())){
				entList.add(ee);
			}
		}
		return entList;
	}
	
	/**
	 * Convenience method to return all projects in the workspace with the Studio nature
	 * @return
	 */
	public static IProject[] getAllStudioProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<IProject> studioProjects = new ArrayList<IProject>();
		for (int i = 0; i < projects.length; i++) {
			if (CommonUtil.isStudioProject(projects[i])) {
				studioProjects.add(projects[i]);
			}
		}
		return studioProjects.toArray(new IProject[studioProjects.size()]);
	}
	
	/**
	 * Convenience method to return names of all projects in the workspace with the Studio nature
	 * @return
	 */
	public static ArrayList<String> getAllStudioProjectNames() {
		IProject projects[] = getAllStudioProjects();
		ArrayList<String> projectNamesList = new ArrayList<String>();
		for (IProject project: projects) {
			projectNamesList.add(project.getName());
		}
		return projectNamesList;
	}
	
	/**
	 * 
	 * @param container
	 * @return
	 */
	public static Object[] getResources(IContainer container) {
		try {
			if (container == null || !container.isAccessible()) {
				return new Object[0];
			}
			IResource[] members = container.members();
			Object[] returnRes = new Object[members.length];
			System.arraycopy(members, 0, returnRes, 0, returnRes.length);
			return returnRes;
		} catch (CoreException e) {
			return new Object[0];
		}
	}
	
	/**
	 * @param container
	 * @param extension
	 * @param resourceList
	 */
	public static void getResourcesByExtension(IContainer container, String extension, List<IFile> resourceList) {
		try {
			if (container == null || !container.isAccessible()) {
				return;
			}
			IResource[] resources = container.members();
			for (IResource resource: resources) {
				if (resource instanceof IContainer) {
					getResourcesByExtension((IFolder)resource, extension, resourceList);
				}
				if (resource instanceof IFile) {
					String resourceExtension = resource.getFileExtension();
					if (resourceExtension != null && resourceExtension.equals(extension)) {
						if (resourceExtension.equals(CommonIndexUtils.JAVA_EXTENSION)) {
							if (!StudioJavaUtil.isInsideJavaSourceFolder(((IFile)resource).getFullPath().toString(), resource.getProject().getName())) {
								continue;
							}
						}
						resourceList.add((IFile)resource);
					}
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param container
	 * @param extension
	 * @return
	 */
	public static ArrayList<String> getResourcesByExtension(IContainer container, String extension) {
		List<IFile> resourceList = new ArrayList<IFile>();
		getResourcesByExtension(container, extension, resourceList);
		ArrayList<String> filesList = new ArrayList<String>();
		for (IFile resource: resourceList) {
			filesList.add(resource.getProjectRelativePath().toString());
		}
		return filesList; 
	}
	
	/**
	 * @param path
	 * @return
	 */
	public static IResource getResourcePathFromContainerPath(IPath path) {
		IResource resource = null;
		switch (path.segmentCount()) {
			case 0 :
				resource = ResourcesPlugin.getWorkspace().getRoot();
				break;
			case 1 :
				resource = ResourcesPlugin.getWorkspace().getRoot().getProject(path.lastSegment());
				break;
			default :
				resource = ResourcesPlugin.getWorkspace().getRoot().getFolder(path);
		}
		return resource;
	}

	/**
	 * @param folder
	 * @param fileName
	 * @param duplicateFile
	 * @return
	 */
	public static boolean isDuplicateBEResource(IResource folder, String fileName, StringBuilder duplicateFile) {
		Object[] object = getResources((IContainer)folder);
		for(Object obj : object){
			if(obj instanceof IFile){
				DesignerElement element = IndexUtils.getElement((IFile)obj);
				if(element != null){
					if(!(element instanceof ElementContainer) && element.getName().equalsIgnoreCase(fileName)){
						duplicateFile.append(((IFile)obj).getName());
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * @param folder
	 * @param fileName
	 * @param duplicateFile
	 * @return
	 */
	public static boolean isDuplicateResource(IResource folder, String fileName, StringBuilder duplicateFile) {
		Object[] object = getResources((IContainer)folder);
		for(Object obj : object){
			if(obj instanceof IFile){
				IFile file = (IFile)obj;
				String name = file.getName().replace("." + file.getFileExtension(), "");
				if(file != null){
					if(name.equalsIgnoreCase(fileName)){
						duplicateFile.append(file.getName());
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void refresh(IResource resource, int depth, boolean wait) {
		IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
		ISchedulingRule rule = ruleFactory.refreshRule(resource);
		Job existingRefreshJob = getExistingRefreshJob(rule);
		if (existingRefreshJob != null) {
			if (wait) {
				try {
					existingRefreshJob.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			RefreshResourceJob job = new RefreshResourceJob(resource, depth);
			job.setRule(rule);
			job.schedule();
		}
	}
	
	private static Job getExistingRefreshJob(ISchedulingRule rule) {

        IJobManager jobManager = Job.getJobManager();
        Job[] refreshJobs = jobManager.find(RefreshResourceJob.REFRESH_JOB);
        for (int i = 0; i < refreshJobs.length; i++) {
            if (refreshJobs[i] instanceof RefreshResourceJob) {
            	RefreshResourceJob refreshJob = (RefreshResourceJob) refreshJobs[i];
            	if (refreshJob.getRule().contains(rule)) {
            		return refreshJob;
            	}
            }
        }
        return null;
	}

	/**
	 * @param str
	 * @param toBeRaplaced
	 * @param byReplaced
	 * @return
	 */
	public static String replace (String str, String toBeRaplaced, String byReplaced) {   
		int start = str.indexOf(toBeRaplaced);
		if (start == -1) return str;
		int lf = toBeRaplaced.length();
		char [] targetChars = str.toCharArray();
		StringBuffer buffer = new StringBuffer();
		int copyFrom = 0;
		while (start != -1) {
			buffer.append (targetChars, copyFrom, start-copyFrom);
			buffer.append (byReplaced);
			copyFrom = start + lf;
			start = str.indexOf (toBeRaplaced, copyFrom);
		}
		buffer.append (targetChars, copyFrom, targetChars.length - copyFrom);
		return buffer.toString();
	}


	public static boolean isTestDataResource(IResource resource){
		Collection<String> extns = getTestDataExtensions();
		if(resource instanceof IFile){
			IFile file = (IFile)resource;
			if(extns.contains(file.getFileExtension())){
				return true;
			}
		}
		return false;
	}

	public static  boolean isSharedResource(IResource resource) {
		Collection<String> extns = getSharedResourceExtensions();
		if(resource instanceof IFile){
			IFile file = (IFile)resource;
			if(extns.contains(file.getFileExtension())){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNonEntityResource(IResource resource) {
		Collection<String> extns = getSharedResourceExtensions();
		extns.addAll(getTestDataExtensions());
		extns.add("cdd");

		if(resource instanceof IFile){
			IFile file = (IFile)resource;
			if(extns.contains(file.getFileExtension())){
				return true;
			}
		}
		return false;
	}
	
	public static Collection<String> getTestDataExtensions(){
		return StudioCore.getValidLibraryExtensions(new String[] {StudioCore.TEST_DATA_EXTENSIONS});
	}
	
	public static Collection<String> getSharedResourceExtensions(){
		return StudioCore.getValidLibraryExtensions(new String[] {
				StudioCore.SHARED_RESOURCE_EXTENSIONS});
	}
	
	/**
	 * @param se
	 * @param conceptPath
	 * @param projectName
	 */
	public static void updateStateMachineRuleSymbols(StateEntity se, String projectName){
		if (se instanceof StateSubmachine){
			StateSubmachine ssm = (StateSubmachine)se;
			updateStateRulesSymbols(ssm, projectName);
		} 
		else if (se instanceof StateComposite){
			StateComposite sc = (StateComposite)se;
			updateStateRulesSymbols(sc, projectName);
			for (StateEntity s : sc.getStateEntities()){
				updateStateMachineRuleSymbols(s, projectName);
			}
		} else if (se instanceof StateStart){
			StateStart start = (StateStart)se;
			if(start.getExitAction() != null){
				start.getExitAction().getSymbols().getSymbolMap().clear();
			}
			
		} else if (se instanceof StateEnd){
			StateEnd end = (StateEnd)se;
			if(end.getEntryAction()!= null){
				end.getEntryAction().getSymbols().getSymbolMap().clear();
			}
			
		} else if (se instanceof State){			
			State s = (State)se;
			updateStateRulesSymbols(s, projectName);
		}
	}
	
	/**
	 * @param s
	 * @param conceptPath
	 * @param projectName
	 */
	public static void updateStateRulesSymbols(State s, String projectName){
		if (s.isInternalTransitionEnabled()){
			if(s.getInternalTransitionRule() !=null){
				s.getInternalTransitionRule().getSymbols().getSymbolMap().clear();
			}
		}
		if(s.getEntryAction()!=null){
			s.getEntryAction().getSymbols().getSymbolMap().clear();
		}
		if(s.getExitAction() != null){
			s.getExitAction().getSymbols().getSymbolMap().clear();
		}
		if(s.getTimeoutAction() !=null){
			s.getTimeoutAction().getSymbols().getSymbolMap().clear();
		}
		if(s.getTimeoutExpression()!=null){
			s.getTimeoutExpression().getSymbols().getSymbolMap().clear();
		}
	}

	/**
	 * @param sm
	 * @param oldConceptPath
	 * @param newConceptPath
	 * @param projectName
	 */
	public static void updateStateTransitionRulesSymbols(StateMachine sm, 
			                                             String oldConceptPath, 
			                                             String newConceptPath, 
			                                             String projectName){
		for (StateTransition stateTransition : sm.getStateTransitions()){
			if(stateTransition.getGuardRule() !=null){
				removeTransitionRule(stateTransition, oldConceptPath);
			}
		}
	}
	
	/**
	 * @param stateTransition
	 * @param conceptPath
	 */
	public static void removeTransitionRule(StateTransition stateTransition, String conceptPath){
		List<Symbol> symbols = new ArrayList<Symbol>();
		for(Symbol symbol:stateTransition.getGuardRule().getSymbols().getSymbolList()){
			if(symbol.getType().equals(conceptPath)){
				symbols.add(symbol);
			}
		}
		Symbol[] symbolArr = new Symbol[symbols.size()];
		symbols.toArray(symbolArr);
		if(stateTransition.getGuardRule().getSymbols().getSymbolMap().size() > 0){
			for(Symbol symbol:symbolArr){
				stateTransition.getGuardRule().getSymbols().getSymbolMap().remove(symbol.getIdName());
			}
		}
	}
	
	public static boolean isStudioProject(IProject project) {
		try {
			if (project.hasNature(StudioProjectNature.STUDIO_NATURE_ID)) {
				return true;
			}
		} catch (CoreException ce) {
		}
		return false;
	}
	
	/**
	 *
	 * @param contents -> The contents to parse to {@link DomainEntry}
	 * @param dataType
	 * @return
	 */
	public static DomainEntry parseValuesToDomainEntry(String contents,
			                                           DOMAIN_DATA_TYPES dataType) {
		if (contents == null)
			return null;
		if (DOMAIN_DATA_TYPES.STRING.equals(dataType)) {
			// it will be a single value in case of String
			Single singleValue = DomainFactory.eINSTANCE.createSingle();
			// if (!(cellValue.startsWith("\"") && cellValue.endsWith("\""))){
			// cellValue = "\"" + cellValue + "\"";
			// }
			singleValue.setValue(contents);
			return singleValue;
		} else if (DOMAIN_DATA_TYPES.BOOLEAN.equals(dataType)) {
			// boolean type can be only single valued
			Single singleValue = DomainFactory.eINSTANCE.createSingle();
			singleValue.setValue(contents.trim());
			return singleValue;
		} else {
			// all other data types can be range valued, for example , int ,
			// long , double
			// parse cell value for operator
			// if both ">" && "<" operators are present
			String trimmedValue = contents.trim();
			int index1 = trimmedValue.indexOf(">");
			int index2 = trimmedValue.indexOf("<");
			int index3 = trimmedValue.indexOf("&&");
			if (index1 != -1 && index2 != -1 && index3 != -1) {
				// range value
				// split
				String[] splValues = contents.split("&&");
				if (splValues == null || splValues.length != 2) {
					// not a valid Range Value
					return null;
}
				String leftVal = splValues[0].trim();
				String rightVal = splValues[1].trim();
				boolean isLowerBound = false;
				Range rangeVal = DomainFactory.eINSTANCE.createRange();

				if (leftVal.startsWith(">")) {
					isLowerBound = true;
					boolean isLBIncluded = false;
					leftVal = leftVal.substring(1).trim();
					if (leftVal.startsWith("=")) {
						leftVal = leftVal.substring(1).trim();
						isLBIncluded = true;
					}
					rangeVal.setLower(leftVal);
					rangeVal.setLowerInclusive(isLBIncluded);
					// rangeVal.set
				} else if (leftVal.startsWith("<")) {
					boolean isUBIncluded = false;
					leftVal = leftVal.substring(1).trim();
					if (leftVal.startsWith("=")) {
						leftVal = leftVal.substring(1).trim();
						isUBIncluded = true;
					}
					rangeVal.setUpper(leftVal);
					// rangeVal.setMultiple(true);
					rangeVal.setUpperInclusive(isUBIncluded);

				} else {
					// not a right cell value
					return null;
				}

				if (rightVal.startsWith(">")) {
					if (isLowerBound) {
						// not a valid range value
						return null;
					} else {
						boolean isLBIncluded = false;
						rightVal = rightVal.substring(1).trim();
						if (rightVal.startsWith("=")) {
							rightVal = rightVal.substring(1).trim();
							isLBIncluded = true;
						}
						rangeVal.setLower(rightVal);
						rangeVal.setLowerInclusive(isLBIncluded);
						// rangeVal.setMultiple(true);
						return rangeVal;
					}
				} else if (rightVal.startsWith("<")) {
					if (!isLowerBound) {
						// not a valid range value
						return null;
					} else {

						boolean isUBIncluded = false;
						rightVal = rightVal.substring(1).trim();
						if (rightVal.startsWith("=")) {
							rightVal = rightVal.substring(1).trim();
							isUBIncluded = true;
						}
						rangeVal.setUpper(rightVal);
						rangeVal.setUpperInclusive(isUBIncluded);
						// rangeVal.setMultiple(true);
						return rangeVal;
					}

				} else {
					// not a valid range value
					return null;
				}

			} else if (index1 != -1 || index2 != -1) {
				// range value
				String val = null;
				if (index1 != -1) {
					val = trimmedValue.substring(index1 + 1).trim();
					boolean lbIncluded = false;
					if (val.startsWith("=")) {
						val = val.substring(1);
						lbIncluded = true;
					}
					val = val.trim();
					Range rangeVal = DomainFactory.eINSTANCE.createRange();
					rangeVal.setLower(val);
					rangeVal.setUpper("Undefined");
					// rangeVal.setMultiple(true);
					rangeVal.setLowerInclusive(lbIncluded);
					return rangeVal;

				} else {
					val = trimmedValue.substring(index2 + 1).trim();
					boolean upBoundIncluded = false;
					if (val.startsWith("=")) {
						val = val.substring(1);
						upBoundIncluded = true;
					}
					val = val.trim();
					Range rangeVal = DomainFactory.eINSTANCE.createRange();
					rangeVal.setUpper(val);
					rangeVal.setLower("Undefined");
					// rangeVal.setMultiple(true);
					rangeVal.setUpperInclusive(upBoundIncluded);
					return rangeVal;
				}
			} else {
				// single value
				Single singleValue = DomainFactory.eINSTANCE.createSingle();
				singleValue.setValue(contents);
				return singleValue;
			}
		}
	}
	
	/**
	 * @return
	 */
	public static boolean showWarnings() {
		return StudioCorePlugin.getDefault().getPluginPreferences().getBoolean(StudioCorePreferenceConstants.STUDIO_SHOW_WARNINGS);
	}
	
	/**
	 * @param extension
	 * @return
	 */
	public static boolean isWarningPattern(String extension) {
		String value = StudioCorePlugin.getDefault().getPluginPreferences().getString(StudioCorePreferenceConstants.STUDIO_SHOW_WARNINGS_PATTERNS);
		//TODO find a way to add process extension in prefernce
		if(extension.equals(CommonIndexUtils.PROCESS_EXTENSION))
			return true;
		
		return getUpdatedExtension(value).contains(extension);
	}
	
	/**
	 * @param severity
	 * @return
	 */
	public static boolean showWarnings(int severity, String extension) {
		if (severity == IMarker.SEVERITY_WARNING) {
			if (showWarnings() && isWarningPattern(extension)) {
				return true;
			}
			return false;
		}
		return true;
	}

	/**
	 * Any Zar file copy one dir to another
	 * @param inputFile
	 * @param outputFile
	 * @return
	 */
	public static boolean copyFile(File inputFile, File outputFile){
		try {
			FileInputStream fis = new FileInputStream(inputFile);
			ZipInputStream zis = new ZipInputStream(fis);
			FileOutputStream fos = new FileOutputStream(outputFile);
			ZipOutputStream zos = new ZipOutputStream(fos);
			ZipEntry ze = null;
			byte[] buf = new byte[1024];
			while ((ze = zis.getNextEntry()) != null) {
				StudioCorePlugin.debug("Next entry "+ze.getName()+" "+ze.getSize());
				zos.putNextEntry(ze);
				int len;
				while ((len = zis.read(buf)) > 0) {
					zos.write(buf, 0, len);
				}
			}
			zos.close();
			fos.close();
			zis.close();
			fis.close();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	/**
	 * Delete given directory contents
	 * @param fDir
	 * @return boolean
	 */
	public static boolean deleteDirContent(File fDir)  {
		boolean retval = false;
		if (fDir != null && fDir.isDirectory()) {
			File[] files = fDir.listFiles();
			if (files != null) {
				retval = true;
				boolean dirDeleted;
				for (int index = 0; index < files.length; index++)
				{
					if (files[index].isDirectory()) {
						dirDeleted = deleteDirContent(files[index]);
						if (dirDeleted) {
							retval = retval && files[index].delete();
						} else {
							retval = false;
						}
					} else {
						retval = retval && files[index].delete();
					}
				}
			}
		}
		return retval;
	}
	
	/**
	 * Check file whether locked for any other process
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static boolean isLocked(File file) throws Exception {
		FileChannel channel = new RandomAccessFile(file, "rw").getChannel();
		FileLock lock = null;
		boolean locked = false;
		try {
			lock = channel.tryLock();
			if (lock == null) {
				locked = true;
			}
		} catch (OverlappingFileLockException e) {
			locked = true;
		} finally {
			if (lock != null) {
				lock.release();
			}
			if (channel.isOpen()) {
				channel.close();
			}
		}
		return locked;
	}
	
	/**
	 * This traverses read only directories till the given ear path 
	 * @param fDir
	 * @param path
	 * @return
	 */
	public static boolean traverseReadOnlyDirectory(File fDir, String path) {
		if (fDir != null && fDir.exists() && fDir.isDirectory()) {
			if (!fDir.canWrite()) {
				return true;
			}
			if (fDir.getParentFile()!= null 
					&& fDir.getParentFile().getAbsolutePath().equals(path)) {
				if (!fDir.getParentFile().canWrite()) {
					return true;	
				}
				return false;
			}
		    traverseReadOnlyDirectory(fDir.getParentFile(), path);
		}
		return false;
	}

	/**
	 * @return
	 */
	public static List<String> getAllExtensions() {  
		List<String> extns = new ArrayList<String>();
		extns.add(CommonIndexUtils.RULE_EXTENSION);
		extns.add(CommonIndexUtils.RULEFUNCTION_EXTENSION);
		extns.add(CommonIndexUtils.RULE_FN_IMPL_EXTENSION);
		extns.add(CommonIndexUtils.TIME_EXTENSION);
		extns.add(CommonIndexUtils.CONCEPT_EXTENSION);
		extns.add(CommonIndexUtils.SCORECARD_EXTENSION);
		extns.add(CommonIndexUtils.DOMAIN_EXTENSION);
		extns.add(CommonIndexUtils.CHANNEL_EXTENSION);
		extns.add(CommonIndexUtils.EVENT_EXTENSION);
		extns.add(CommonIndexUtils.STATEMACHINE_EXTENSION);
		extns.add(CommonIndexUtils.SITE_TOPOLOGY_EXTENSION);
		extns.add(CommonIndexUtils.JAVA_EXTENSION);
		extns.addAll(getSharedResourceExtensions());
		extns.addAll(getTestDataExtensions());
		extns.add("cdd");
		extns.addAll(getViewsExtensions());
		extns.add(CommonIndexUtils.XSD_EXTENSION);
		extns.add(CommonIndexUtils.WSDL_EXTENSION);
		return extns;
	}
	
	//PATCH we should introduce extension points to get this list 
	private static Collection<? extends String> getViewsExtensions() {
		Set<String> extns = new HashSet<String>();
		extns.add("chart");
		return extns;
	}
	
	/**
	 * @param set
	 * @return
	 */
	public static String getUpdatedValues(Collection<String> set) {
		String[] st = new String[set.size()];
		set.toArray(st);
		if (st.length == 0) {
			return "NA";
		}
		String initialVal = st[0];
		for (int index = 1; index < st.length; index++) {
			initialVal = initialVal + DELIMITER + st[index];
		}
		return initialVal;
	}
	
	/**
	 * @param val
	 * @return
	 */
	public static Set<String> getUpdatedExtension(String val) {
		Set<String> set = new HashSet<String>();
		String[] values = val.split(DELIMITER, -1);
		for (String v: values) {
			set.add(v);
		}
		return set;
	}
	
	/**
	 * @param projectName
	 * @return
	 */
	public static IProject getStudioProject(String projectName) {
		for (IProject project: getAllStudioProjects()) {
			if (project.getName().equalsIgnoreCase(projectName)) {
				return project;
			}
		}
		return null;
	}
	
	public static boolean isValidName(String input) {
		return input.matches("([A-Za-z]+[A-Za-z0-9\\_]*)");
	}
	
	public static boolean isStringNumeric(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Integer.parseInt(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	public static boolean isStringDouble(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Double.parseDouble(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	public static boolean isStringLong(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Long.parseLong(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	public static boolean isNumeric(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Integer.parseInt(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	public static boolean isDouble(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Double.parseDouble(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	public static boolean isLong(String checkStr) {
		try {
			if (!checkStr.trim().equalsIgnoreCase(""))
				Long.parseLong(checkStr);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param type
	 * @return
	 */
	public static String getType(int type){
		switch(type){
		case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_INTEGER:
			return "INTEGER";
		case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_REAL:
			return "REAL";
		case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_LONG:
			return "LONG";
		case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
			return "BOOLEAN";
		case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_DATETIME:
			return "DATETIME";
		case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_STRING:
			return "STRING";
		case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_CONCEPT:
			return "CONTAINEDCONCEPT";
		case com.tibco.cep.designtime.model.element.PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
			return "CONCEPTREFERENCE";
		}
		return null;
	}
	
	
}
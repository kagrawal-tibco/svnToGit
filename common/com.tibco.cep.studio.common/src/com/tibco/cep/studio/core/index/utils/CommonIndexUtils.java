package com.tibco.cep.studio.core.index.utils;

import static com.tibco.cep.studio.core.utils.ModelUtils.getPersistenceOptions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.be.util.GUIDGenerator;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.impl.DomainInstanceImpl;
import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.XsltFunction;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.configuration.StudioProjectConfigurationCache;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.resolution.GlobalVariableExtension;
import com.tibco.cep.studio.core.rules.CommonRulesParserManager;
import com.tibco.cep.studio.core.rules.ast.RuleCreatorASTVisitor;
import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class CommonIndexUtils {
	
	public static final String PATH_SEPARATOR = new String(new char[]{Path.SEPARATOR});
	public static final String DOT = ".";
	public static final String IDX_EXTENSION = ".idx";

	public final static HashMap<String, ELEMENT_TYPES> fileExtToElementType = new HashMap<String, ELEMENT_TYPES>();

	public static final String RULE_EXTENSION 			= "rule";    //$NON-NLS-1$
	public static final String RULEFUNCTION_EXTENSION 	= "rulefunction";    //$NON-NLS-1$
	public static final String RULE_FN_IMPL_EXTENSION 	= "rulefunctionimpl";    //$NON-NLS-1$
	public static final String RULE_TEMPLATE_EXTENSION 	= "ruletemplate";    //$NON-NLS-1$
	public static final String RULE_TEMPLATE_VIEW_EXTENSION = "ruletemplateview";    //$NON-NLS-1$
	public static final String RULE_TEMPLATE_INSTANCE_EXTENSION = "ruletemplateinstance";    //$NON-NLS-1$
	public static final String EAR_EXTENSION 			= "archive";    //$NON-NLS-1$
	public static final String TIME_EXTENSION 			= "time";    //$NON-NLS-1$
	public static final String STATEMACHINE_EXTENSION 	= "statemachine";    //$NON-NLS-1$
	public static final String CONCEPT_EXTENSION 		= "concept";    //$NON-NLS-1$
	public static final String EVENT_EXTENSION  		= "event";    //$NON-NLS-1$
    public static final String SCORECARD_EXTENSION  	= "scorecard";    //$NON-NLS-1$
    public static final String AESCHEMA_EXTENSION 		= "aeschema";    //$NON-NLS-1$
    public static final String XSD_EXTENSION 			= "xsd";    //$NON-NLS-1$
    public static final String DTD_EXTENSION 			= "dtd";    //$NON-NLS-1$
    public static final String WSDL_EXTENSION			= "wsdl";    //$NON-NLS-1$
    public static final String DOMAIN_EXTENSION 		= "domain";    //$NON-NLS-1$
    public static final String DISPLAY_EXTENSION 		= "display";    //$NON-NLS-1$
	public static final String CHANNEL_EXTENSION  		= "channel";    //$NON-NLS-1$
	public static final String GLOBAL_VAR_EXTENSION     = "substvar";   //$NON-NLS-1$

	//Later we will decide for actual extension for query composer files
	public static final String QUERY_EXTENSION          = "query";   //$NON-NLS-1$
	public static final String BE_QUERY_EXTENSION       = "bequery";   //$NON-NLS-1$
	public static final String METRIC_EXTENSION         = "metric";   //$NON-NLS-1$
	public static final String PROCESS_EXTENSION        = "beprocess";   //$NON-NLS-1$
	public static final String JAVA_EXTENSION        	= "java";   //$NON-NLS-1$
	public static final String SITE_TOPOLOGY_EXTENSION     = "st";   //$NON-NLS-1$
	public static final String XSLT_FUNCTION_EXTENSION     = "xsltfunction";   //$NON-NLS-1$
	public static final String PROJECT_LIBRARY_EXTENSION   = "projlib";   //$NON-NLS-1$
	public static final String ANNOTATION_BPMN_JAVA_CLASS_TASK = "JavaTask";//$NON-NLS-1$
	
	public static final String ANNOTATION_JAVA_CUSTOM_FUNCTION_CLASS = "BEPackage";//$NON-NLS-1$
	public static final String ANNOTATION_JAVA_CUSTOM_FUNCTION_CLASS_METHOD = "BEFunction";//$NON-NLS-1$
	
	public static String[] tnsExtensions = new String[] {
    	CONCEPT_EXTENSION,
    	EVENT_EXTENSION,
    	TIME_EXTENSION,
    	SCORECARD_EXTENSION,
    	RULEFUNCTION_EXTENSION,
    	XSLT_FUNCTION_EXTENSION
    };
	
	public static String[] schemaExtensions = new String[] {    	
		AESCHEMA_EXTENSION,
    	XSD_EXTENSION,
    	DTD_EXTENSION,
    	WSDL_EXTENSION
    };
	
	public static String[] globalVarExtensions = new String[] {
		GLOBAL_VAR_EXTENSION
	};

	private static boolean sunParserAvailable = true;
	private static boolean sunParserCheckComplete = false;
	
	static {
		
		
		fileExtToElementType.put(CHANNEL_EXTENSION, ELEMENT_TYPES.CHANNEL);
		fileExtToElementType.put(CONCEPT_EXTENSION, ELEMENT_TYPES.CONCEPT);
//		fileExtToElementType.put("destination", ELEMENT_TYPES.DESTINATION);
		fileExtToElementType.put(EAR_EXTENSION, ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
		fileExtToElementType.put(RULE_EXTENSION, ELEMENT_TYPES.RULE);
		fileExtToElementType.put(RULEFUNCTION_EXTENSION, ELEMENT_TYPES.RULE_FUNCTION);
		fileExtToElementType.put(SCORECARD_EXTENSION, ELEMENT_TYPES.SCORECARD);
		fileExtToElementType.put(EVENT_EXTENSION, ELEMENT_TYPES.SIMPLE_EVENT);
		fileExtToElementType.put(STATEMACHINE_EXTENSION, ELEMENT_TYPES.STATE_MACHINE);
		fileExtToElementType.put(TIME_EXTENSION, ELEMENT_TYPES.TIME_EVENT);
		fileExtToElementType.put(RULE_FN_IMPL_EXTENSION, ELEMENT_TYPES.DECISION_TABLE);
		fileExtToElementType.put(RULE_TEMPLATE_EXTENSION, ELEMENT_TYPES.RULE_TEMPLATE);
		fileExtToElementType.put(RULE_TEMPLATE_VIEW_EXTENSION, ELEMENT_TYPES.RULE_TEMPLATE_VIEW);
		fileExtToElementType.put(METRIC_EXTENSION, ELEMENT_TYPES.METRIC);
		fileExtToElementType.put(DOMAIN_EXTENSION, ELEMENT_TYPES.DOMAIN);
		fileExtToElementType.put(XSLT_FUNCTION_EXTENSION, ELEMENT_TYPES.XSLT_FUNCTION);
		fileExtToElementType.put(PROCESS_EXTENSION, ELEMENT_TYPES.PROCESS);
		fileExtToElementType.put(JAVA_EXTENSION, ELEMENT_TYPES.JAVA_SOURCE);
		// unused
		fileExtToElementType.put("instance", ELEMENT_TYPES.INSTANCE);
		// unused
		fileExtToElementType.put("ruleset", ELEMENT_TYPES.RULE_SET);
		
		/** 
		 * BEGIN: BE Views Element Types
		 */
		fileExtToElementType.put("chart",ELEMENT_TYPES.CHART_COMPONENT);
		fileExtToElementType.put("smcomponent",ELEMENT_TYPES.STATE_MACHINE_COMPONENT);
		fileExtToElementType.put("pageselector",ELEMENT_TYPES.PAGE_SELECTOR_COMPONENT);
		fileExtToElementType.put("alert",ELEMENT_TYPES.ALERT_COMPONENT);
		fileExtToElementType.put("contextactionruleset",ELEMENT_TYPES.CONTEXT_ACTION_RULE_SET);
		fileExtToElementType.put("blueprint",ELEMENT_TYPES.BLUE_PRINT_COMPONENT);
		fileExtToElementType.put("querymanager",ELEMENT_TYPES.QUERY_MANAGER_COMPONENT);
		fileExtToElementType.put("searchview",ELEMENT_TYPES.SEARCH_VIEW_COMPONENT);
		fileExtToElementType.put("relatedassets",ELEMENT_TYPES.RELATED_ASSETS_COMPONENT);
		fileExtToElementType.put("drilldown",ELEMENT_TYPES.DRILLDOWN_COMPONENT);
		fileExtToElementType.put("query",ELEMENT_TYPES.QUERY);
		fileExtToElementType.put("view",ELEMENT_TYPES.VIEW);
		fileExtToElementType.put("dashboardpage",ELEMENT_TYPES.DASHBOARD_PAGE);
		fileExtToElementType.put("assetpage",ELEMENT_TYPES.ASSET_PAGE);
		fileExtToElementType.put("searchpage",ELEMENT_TYPES.SEARCH_PAGE);
		fileExtToElementType.put("pageset",ELEMENT_TYPES.PAGE_SET);
		fileExtToElementType.put("seriescolor",ELEMENT_TYPES.SERIES_COLOR);
		fileExtToElementType.put("textcolorset",ELEMENT_TYPES.TEXT_COMPONENT_COLOR_SET);
		fileExtToElementType.put("chartcolorset",ELEMENT_TYPES.CHART_COMPONENT_COLOR_SET);
		fileExtToElementType.put("header",ELEMENT_TYPES.HEADER);
		fileExtToElementType.put("login",ELEMENT_TYPES.LOGIN);
		fileExtToElementType.put("skin",ELEMENT_TYPES.SKIN);
		fileExtToElementType.put("rolepreference",ELEMENT_TYPES.ROLE_PREFERENCE);
		fileExtToElementType.put("datasource",ELEMENT_TYPES.DATA_SOURCE);
		fileExtToElementType.put("system",ELEMENT_TYPES.SKIN);
		/** 
		 * END: BE Views Element Types
		 */
	}
	/**
	 * returns the designer emf project based on the name
	 * @since 4.0
	 * @param indexName
	 * @return
	 */
	private static DesignerProject getIndex(String indexName) {
//		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(indexName);
		// need a way to wait for loading to complete here, without referencing the job manager
		return StudioProjectCache.getInstance().getIndex(indexName);
	}
	
	public static String getIndexRootPath(String indexName) {
		DesignerProject proj = getIndex(indexName);
		if (proj == null) {
			return null;
		}
		return proj.getRootPath();
	}
	
	/**
	 * checks if the file is a emf type
	 * @since 4.0
	 * @param fileExtension
	 * @return
	 */
	public static boolean isEMFType(String fileExtension) {
		if (isRuleType(fileExtension) || isQueryType(fileExtension)) {
			return false;
		}
		if (isEntityType(fileExtension) || isArchiveType(fileExtension) || isImplementationType(fileExtension)) {
			return true;
		}
		return false;
	}
	
	/**
	 * checks if the file is a global variable file
	 * @param extension
	 * @return
	 */
	public static boolean isGlobalVarType(String fileExtension) {
		if (GLOBAL_VAR_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * checks if the file is a target namespace cache resource 
	 * @since 4.0
	 * @param ext
	 * @return
	 */
	public static boolean isTnsCacheResource(String ext) {
		// needs to be case insensitive...
		if (Arrays.binarySearch(tnsExtensions, ext) >= 0) {
			return true;
		}
		// do a case insensitive search.  Slow, but necessary
		for (String ex : tnsExtensions) {
			if (ex.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * checks if the file is a xml Schema tns resource 
	 * @since 4.0
	 * @param ext
	 * @return
	 */
	public static boolean isXmlSchemaResource(String ext) {
		// needs to be case insensitive...
		if (Arrays.binarySearch(schemaExtensions, ext) >= 0) {
			return true;
		}
		// do a case insensitive search.  Slow, but necessary
		for (String ex : schemaExtensions) {
			if (ex.equalsIgnoreCase(ext)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * checks if the file is a valid ontology type
	 * @since 4.0
	 * @param fileExtension
	 * @return
	 */
	public static boolean isOntologyElement(String fileExtension) {
		if (isRuleType(fileExtension)) {
			return true;
		}
		if (isArchiveType(fileExtension)) {
			return true;
		}
		if (isImplementationType(fileExtension)) {
			return true;
		}
		// we know it is not a rule, an impl, or an archive, if
		// it exists in the extensions map, it must be
		// an entity type
		if (fileExtToElementType.containsKey(fileExtension)) {
			return true;
		}
		return false;
	}
	/**
	 * checks if the file is a valid entity type
	 * @param fileExtension
	 * @return
	 */
	public static boolean isEntityType(String fileExtension) {
		if (isRuleType(fileExtension)) {
			return false;
		}
		if (isArchiveType(fileExtension)) {
			return false;
		}
		if (isImplementationType(fileExtension)) {
			return false;
		}
		if (isJavaType(fileExtension)) {
			return false;
		}
		// we know it is not a rule, an impl, or an archive, if
		// it exists in the extensions map, it must be
		// an entity type
		if (fileExtToElementType.containsKey(fileExtension)) {
			return true;
		}
		return false;
	}
	/**
	 * checks if the element type is a valid entity type
	 * @since 4.0
	 * @param type
	 * @return
	 */
	public static boolean isEntityType(ELEMENT_TYPES type) {
		if (isRuleType(type)) {
			return false;
		}
		if (isArchiveType(type)) {
			return false;
		}
		return true;
	}
	/**
	 * checks if the file extension is a archive type
	 * @since 4.0
	 * @param fileExtension
	 * @return
	 */
	public static boolean isArchiveType(String fileExtension) {
		if (EAR_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	/**
	 * checks if the file extension is a implementation type
	 * @since 4.0
	 * @param fileExtension
	 * @return
	 */
	public static boolean isImplementationType(String fileExtension) {
		if (RULE_FN_IMPL_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	/**
	 * checks if the element type is a implementation type
	 * @since 4.0
	 * @param type
	 * @return
	 */
	public static boolean isImplementationType(ELEMENT_TYPES type) {
		return type == ELEMENT_TYPES.DECISION_TABLE;
	}
	/**
	 * checks if the element tyoe is a domain instance type
	 * @since 4.0
	 * @param type
	 * @return
	 */
	public static boolean isDomainInstanceType(ELEMENT_TYPES type) {
		return type == ELEMENT_TYPES.DOMAIN_INSTANCE;
	}
	/**
	 * checks if the element type is a archive type
	 * @since 4.0
	 * @param type
	 * @return
	 */
	public static boolean isArchiveType(ELEMENT_TYPES type) {
		return (type == ELEMENT_TYPES.ADAPTER_ARCHIVE 
				|| type == ELEMENT_TYPES.BE_ARCHIVE_RESOURCE
				|| type == ELEMENT_TYPES.ENTERPRISE_ARCHIVE
				|| type == ELEMENT_TYPES.PROCESS_ARCHIVE
				|| type == ELEMENT_TYPES.SHARED_ARCHIVE);
	}
	/**
	 * checks if the file extension is a rule type
	 * @since 4.0
	 * @param fileExtension
	 * @return
	 */
	public static boolean isRuleType(String fileExtension) {
		if (RULE_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		if (RULEFUNCTION_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		if (XSLT_FUNCTION_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		if (RULE_TEMPLATE_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	
	public static boolean isJavaType(String fileExtension) {
		if (JAVA_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	
	public static boolean isJavaResourceType(String projectName, String fullPath, String fileExtension, File projectPath) {
		if (!CommonIndexUtils.isJavaType(fileExtension) && !CommonIndexUtils.isEMFType(fileExtension) && !CommonIndexUtils.isRuleType(fileExtension)) {
			fullPath = fullPath.replaceAll("\\\\", "/");
			int idx = fullPath.lastIndexOf('/');
			String folder = fullPath;
			if (idx >= 0) {
				folder = fullPath.substring(0, idx);
			}
			String sourceFolder = getJavaSourceFolder(projectName, folder,projectPath);
			return sourceFolder != null;
		}
		return false;
	}
	
	public static boolean isProcessType(String fileExtension) {
		if (PROCESS_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	
	public static boolean isQueryType(String fileExtension) {
		if (QUERY_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		if (BE_QUERY_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	
	public static boolean isProjectLibraryType(String fileExtension) {
		if(PROJECT_LIBRARY_EXTENSION.equalsIgnoreCase(fileExtension)){
			return true;
		}
		return false;
	}
	
	public static boolean isProjectLibraryURI(java.net.URI uri) {
		return uri.getScheme().equalsIgnoreCase(PROJECT_LIBRARY_EXTENSION);
	}
	
	/**
	 * checks if the file extension is a rule function type
	 * @since 4.0
	 * @param fileExtension
	 * @return
	 */
	public static boolean isRuleFunctionType(String fileExtension) {
		if (RULEFUNCTION_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	/**
	 * checks if the file extension is a xslt function type
	 * @since 4.0
	 * @param fileExtension
	 * @return
	 */
	public static boolean isXsltFunctionType(String fileExtension) {
		if (XSLT_FUNCTION_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return true;
		}
		return false;
	}
	/**
	 * checks if the element type is a rule type
	 * @since 4.0
	 * @param type
	 * @return
	 */
	public static boolean isRuleType(ELEMENT_TYPES type) {
		return (type == ELEMENT_TYPES.RULE || 
				type == ELEMENT_TYPES.RULE_FUNCTION	|| 
				type == ELEMENT_TYPES.XSLT_FUNCTION	|| 
				type == ELEMENT_TYPES.RULE_TEMPLATE);
	}
	/**
	 * return the entity element type
	 * @since 4.0
	 * @param entity
	 * @return
	 */
	public static ELEMENT_TYPES getElementType(Entity entity) {
		if (entity instanceof Channel) return ELEMENT_TYPES.CHANNEL;
		if (entity instanceof Scorecard) return  ELEMENT_TYPES.SCORECARD;
		if (entity instanceof Metric) return  ELEMENT_TYPES.METRIC;
		if (entity instanceof Concept) return  ELEMENT_TYPES.CONCEPT;
		if (entity instanceof Destination) return  ELEMENT_TYPES.DESTINATION;
		if (entity instanceof TimeEvent) return  ELEMENT_TYPES.TIME_EVENT;
		if (entity instanceof SimpleEvent) return  ELEMENT_TYPES.SIMPLE_EVENT;
		if (entity instanceof RuleTemplate) return  ELEMENT_TYPES.RULE_TEMPLATE;
		if (entity instanceof Rule) return  ELEMENT_TYPES.RULE;
		if (entity instanceof XsltFunction) return  ELEMENT_TYPES.XSLT_FUNCTION;
		if (entity instanceof RuleFunction) return  ELEMENT_TYPES.RULE_FUNCTION;
		if (entity instanceof RuleSet) return  ELEMENT_TYPES.RULE_SET;
		if (entity instanceof StateMachine) return  ELEMENT_TYPES.STATE_MACHINE;
		if (entity instanceof Domain) return  ELEMENT_TYPES.DOMAIN;
		if (entity instanceof JavaSource) return  ELEMENT_TYPES.JAVA_SOURCE;
		if (entity instanceof JavaResource) return  ELEMENT_TYPES.JAVA_RESOURCE;
		ELEMENT_TYPES otherElementType = ELEMENT_TYPES.getByName(entity.eClass().getName());
		if (otherElementType != null) {
			return otherElementType;
		}
		return ELEMENT_TYPES.UNKNOWN;
	}
	
	/**
	 * Find the first element with the fullElementPath, regardless of type<br>
	 * Note: this check is case <b>insensitive</b>
	 * @since 4.0
	 * @param projectName
	 * @param fullElementPath
	 * @return
	 */
	public static DesignerElement getElement(String projectName, String fullElementPath) {
		return getElement(projectName, fullElementPath, false);
	}
	
	/**
	 * Find the first element with the fullElementPath, regardless of type
	 * @since 4.0
	 * @param projectName
	 * @param fullElementPath
	 * @param caseSensitive - whether the check should be case sensitive or case insensitive
	 * @return
	 */
	public static DesignerElement getElement(String projectName, String fullElementPath, boolean caseSensitive) {
		waitForUpdate();
		if (fullElementPath == null) {
			return null;
		}
		Path path = new Path(fullElementPath);
		String elementName = null;
		elementName = path.lastSegment();
		path = path.removeLastSegments(1);
		DesignerProject index = getIndex(projectName);
		ElementContainer containingFolder = null;
		if (path.segmentCount() == 0) {
			containingFolder = index;
		} else {
			containingFolder = getFolder(index, path);
		}
		if (containingFolder == null) {
			return internalGetElement(projectName, fullElementPath, caseSensitive);
		}

		Folder folder = null;
		EList<DesignerElement> entries = containingFolder.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			DesignerElement element = entries.get(i);
			boolean equals = caseSensitive ? element.getName().equals(elementName) : element.getName().equalsIgnoreCase(elementName);
			if (equals) {
				if (element instanceof Folder) {
//					System.out.println("CommonIndexUtils::getElement()::got folder instead");
					folder = (Folder) element;
					continue;
				}
				return element;
			}
		}
		
		if (folder != null) {
			// we did not find any other element with this name in the parent directory, return the folder
			return folder;
		}
		
		// if we can't find it in the above fashion, try doing it the brute force way
		return internalGetElement(projectName, fullElementPath, caseSensitive);
	}

	/**
	 * Find the first shared element with the fullElementPath, regardless of type
	 * @since 5.0
	 * @param projectName
	 * @param fullElementPath
	 * @return
	 */
	public static SharedElement getSharedElement(String projectName, String fullElementPath) {
		waitForUpdate();
		if (fullElementPath == null) {
			return null;
		}
		Path path = new Path(fullElementPath);
		String elementName = null;
		path = path.removeFileExtension();
		elementName = path.lastSegment();
		path = path.removeLastSegments(1);
		DesignerProject index = getIndex(projectName);
		EList<DesignerProject> referencedProjects = index.getReferencedProjects();
		for (int i = 0; i < referencedProjects.size(); i++) {
			DesignerProject designerProject = referencedProjects.get(i);
			ElementContainer containingFolder = null;
			if (path.segmentCount() == 0) {
				containingFolder = index;
			} else {
				containingFolder = getFolder(designerProject, path, false, true);
			}
			if (containingFolder == null) {
				continue;
			}
			EList<DesignerElement> entries = containingFolder.getEntries();
			for (int j = 0; j < entries.size(); j++) {
				DesignerElement element = entries.get(j);
				boolean equals = element.getName().equals(elementName);
				if (equals) {
					if (element instanceof Folder) {
						continue;
					}
					return (SharedElement) element;
				}
			}
		}
		
		return null;
	}
	
	private static DesignerElement internalGetElement(String projectName,
			String fullElementPath, boolean caseSensitive) {
		// brute force method -- hopefully is not used
		if (fullElementPath.endsWith("/")) {
			fullElementPath = fullElementPath.substring(0, fullElementPath.lastIndexOf('/'));
		}
		int idx = fullElementPath.lastIndexOf('/');
		if (idx >= 0) {
			idx++;
			String folder = fullElementPath.substring(0, idx);
			String elmName = fullElementPath.substring(idx);
			for (ELEMENT_TYPES type : ELEMENT_TYPES.values()) {
				DesignerElement element = getElement(projectName, folder, elmName, type, caseSensitive);
				if (element != null) {
					return element;
				}
			}
		}
		for (ELEMENT_TYPES type : ELEMENT_TYPES.values()) {
			DesignerElement element = getElement(projectName, "", fullElementPath, type, caseSensitive);
			if (element != null) {
				return element;
			}
		}
		return null;
	}
	
	/**
	 * Get the first entity that matches the <code>fullPath</code>
	 * @param projectName
	 * @param fullPath - the full path to the entity
	 * @return the first entity that matches the <code>fullPath</code>
	 */
	public static Entity getEntity(String projectName, String fullPath) {
		DesignerElement el = getElement(projectName, fullPath);
		if (el instanceof EntityElement) {
			return ((EntityElement) el).getEntity();
		}
		if (fullPath == null) {
			return null;
		}
		if (fullPath.endsWith("/")) {
			fullPath = fullPath.substring(0, fullPath.lastIndexOf('/'));
		}
		int idx = fullPath.lastIndexOf('/');
		if (idx >= 0) {
			idx++;
			String folder = fullPath.substring(0, idx);
			String elementName = fullPath.substring(idx);
			return getEntity(projectName, folder, elementName);
		}
		//return getElement(projectName, "", fullElementPath, type);
		// modified if any element is at root level then folder is "/" to return the correct result
		return getEntity(projectName, "/", fullPath);
	}
	
	/**
	 * Get the first entity that matches the <code>folder</code> and <code>name</code>
	 * @param projectName
	 * @param folder
	 * @param name
	 * @return the first entity that matches the <code>folder</code> and <code>name</code>
	 */
	public static Entity getEntity(String projectName, String folder, String name) {
		List<Entity> allEntities = getAllEntities(projectName, ELEMENT_TYPES.values());
		for (Entity entity : allEntities) {
			if (folder.equalsIgnoreCase(entity.getFolder()) && name.equals(entity.getName())) {
				return entity;
			}
		}
		return null;
	}
	
	public static Entity getEntity(String projectName, String fullElementPath, ELEMENT_TYPES type) {
		DesignerElement element = getElement(projectName, fullElementPath, type);
		if (element instanceof EntityElement) {
			return ((EntityElement) element).getEntity();
		}
		return null;
	}
	
	public static DesignerElement getElement(String projectName, String fullElementPath, ELEMENT_TYPES type) {
		if (fullElementPath == null) {
			return null;
		}
		if (fullElementPath.endsWith("/")) {
			fullElementPath = fullElementPath.substring(0, fullElementPath.lastIndexOf('/'));
		}
		int idx = fullElementPath.lastIndexOf('/');
		if (idx >= 0) {
			idx++;
			String folder = fullElementPath.substring(0, idx);
			String elementName = fullElementPath.substring(idx);
			return getElement(projectName, folder, elementName, type, true);
		}
		//return getElement(projectName, "", fullElementPath, type);
		// modified if any element is at root level then folder is "/" to return the correct result
		return getElement(projectName, "/", fullElementPath, type, true);
	}
	
	/**
	 * @param <T>
	 * @param projectName
	 * @param fullPath
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends BaseInstance> List<InstanceElement<T>> getInstances(String projectName, 
			                                                 String fullPath, 
			                                                 ELEMENT_TYPES type) {
		if (projectName == null || fullPath == null) {
			return null;
		}
		List<InstanceElement<T>> instances = new ArrayList<InstanceElement<T>>(0);
		List<DesignerElement> elements = getAllElements(projectName, type);
		for (DesignerElement element : elements) {
			if (element instanceof InstanceElement) {
				//Get the wrapped instance
				InstanceElement<T> instanceElement = (InstanceElement<T>)element;
				EObjectResolvingEList<T> instanceList = (EObjectResolvingEList<T>) instanceElement.getInstances();
				for(BaseInstance instance : instanceList) {
					if(instance instanceof DomainInstanceImpl) {
						DomainInstanceImpl domainInstance = (DomainInstanceImpl) instance;
						if(domainInstance.getResourcePath().equals(fullPath)) {
							instances.add(instanceElement);
						}
					}
				}
				
//				T wrapped = instanceElement.getInstance();
//				String path = wrapped.getResourcePath();
//				if (fullPath.intern() == path.intern()) {
//					instances.add(instanceElement);
//				}
			}
		}
		return instances;
	}

	private static DesignerElement getElement(String projectName, String folder,
			String elementName, ELEMENT_TYPES type, boolean caseSensitive) {
		if (projectName == null || elementName == null) {
			return null;
		}
		DesignerProject index = getIndex(projectName);
		DesignerElement el = searchIndex(projectName, folder, elementName, type, index, caseSensitive);
		if (el != null) {
			return el;
		}
		
		// try finding it the brute force method
		List<DesignerElement> elements = getAllElements(projectName, type);
		for (DesignerElement element : elements) {
			if (element instanceof TypeElement) {
				TypeElement typeElement = (TypeElement) element;
				boolean equals = caseSensitive ? folder.equals(typeElement.getFolder()) : folder.equalsIgnoreCase(typeElement.getFolder());
				if (!equals) {
					continue;
				}
			}
			boolean equals = caseSensitive ? elementName.equals(element.getName()) : elementName.equalsIgnoreCase(element.getName());
			if (equals) {
				return element;
			}
		}
		return null;
	}

	private static DesignerElement searchIndex(String projectName, String folder,
			String elementName, ELEMENT_TYPES type, DesignerProject index, boolean caseSensitive) {
		Path path = new Path(folder);
		ElementContainer containingFolder = null;
		if (path.segmentCount() == 0) {
			containingFolder = index;
		} else {
			containingFolder = getFolder(index, path);
		}
		if (containingFolder != null) {
			EList<DesignerElement> entries = containingFolder.getEntries();
			for (int i = 0; i < entries.size(); i++) {
				DesignerElement element = entries.get(i);
				if (element.getElementType() == type) {
					boolean equals = caseSensitive ? elementName.equals(element.getName()) : elementName.equalsIgnoreCase(element.getName());
					if (equals) {
						return element;
					}
				}
			}
		}
		if (index == null) {
			return null;
		}
		EList<DesignerProject> referencedProjects = index.getReferencedProjects();
		for (int i = 0; i < referencedProjects.size(); i++) {
			DesignerProject project = referencedProjects.get(i);
			DesignerElement element = searchIndex(projectName, folder, elementName, type, project, caseSensitive);
			if (element != null) {
				return element;
			}
		}
		return null;
	}

	public static Compilable getRule(String projectName, String rulePath, String ruleName, ELEMENT_TYPES ruleType) {
		RuleElement ruleElement = getRuleElement(projectName, rulePath, ruleName, ruleType);
		if (ruleElement != null) {
			return ruleElement.getRule();
		}
		return null;
	}
	
	public static RuleElement getRuleElement(String projectName, String rulePath, String ruleName, ELEMENT_TYPES ruleType) {
		waitForUpdate();
		DesignerProject index = getIndex(projectName);
		return internalFindRuleElement(rulePath, ruleName, ruleType, index);
	}

	private static RuleElement internalFindRuleElement(String rulePath,
			String ruleName, ELEMENT_TYPES ruleType, DesignerProject index) {
		EList<RuleElement> entities = index.getRuleElements();
		for (int i = 0; i < entities.size(); i++) {
			RuleElement ruleElement = entities.get(i);
			if (ruleElement.getElementType() == ruleType 
					&& rulePath.equalsIgnoreCase(ruleElement.getFolder()) 
					&& ruleName.equalsIgnoreCase(ruleElement.getName())) {
				return ruleElement;
			}
		}
		EList<DesignerProject> referencedProjects = index.getReferencedProjects();
		for (int i=0; i<referencedProjects.size(); i++) {
			DesignerProject designerProject = referencedProjects.get(i);
			RuleElement element = internalFindRuleElement(rulePath, ruleName, ruleType, designerProject);
			if (element != null) {
				return element;
			}
		}
		return null;
	}
	
	private static void waitForUpdate() {
		// does nothing in this context, updates not possible
		// if callers need to wait for index updates, they should
		// call IndexUtils equivalent methods
	}

	public static Compilable getRule(String projectName, String fullRulePath, ELEMENT_TYPES ruleType) {
		RuleElement ruleElement = getRuleElement(projectName, fullRulePath, ruleType);
		if (ruleElement != null) {
			return ruleElement.getRule();
		}
		return null;
	}
	
	public static RuleElement getRuleElement(String projectName, String fullRulePath, ELEMENT_TYPES ruleType) {
		if (fullRulePath == null) {
			return null;
		}
		if (fullRulePath.endsWith("/")) {
			fullRulePath = fullRulePath.substring(0, fullRulePath.lastIndexOf('/'));
		}
		if (!fullRulePath.startsWith("/")) {
			fullRulePath = "/"+fullRulePath;
		}
		int idx = fullRulePath.lastIndexOf('/');
		String folder = "";
		String ruleName = fullRulePath;
		if (idx >= 0) {
			idx++;
			folder = fullRulePath.substring(0, idx);
			ruleName = fullRulePath.substring(idx);
		}

		return getRuleElement(projectName, folder, ruleName, ruleType);
	}
	
	public static List<DesignerElement> getAllElements(String projectName, String elementName) {
		waitForUpdate();

		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();

		if (!sunClassAvailable()) {
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
		}

		try {
			DesignerProject index = getIndex(projectName);
			List<DesignerElement> filteredEntities = new ArrayList<DesignerElement>();
			if (index == null) {
				return filteredEntities;
			}
			ElementFinderVisitor visitor = new ElementFinderVisitor((ELEMENT_TYPES[])null, elementName, true);
			index.accept(visitor);
			return visitor.getFoundElements();
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
		
	}
	
	public static List<DesignerElement> getAllElements(String projectName, ELEMENT_TYPES type) {
		return getAllElements(projectName, type, true);
	}
	
	public static List<DesignerElement> getAllElements(String projectName, ELEMENT_TYPES type , boolean waitForUpdate) {
		if (waitForUpdate){
			waitForUpdate();
		}

		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();

		if (!sunClassAvailable()) {
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
		}

		try {
			DesignerProject index = getIndex(projectName);
			return getAllElements(index, type, true);
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
	}

	private static List<DesignerElement> getAllElements(DesignerProject index,
			ELEMENT_TYPES type, boolean includeTimeEvents) {
		List<DesignerElement> filteredEntities = new ArrayList<DesignerElement>();
		if (index == null) {
			return filteredEntities;
		}
		internalGetAllElements(index, type, filteredEntities, includeTimeEvents);
		return filteredEntities;
	}

	private static void internalGetAllElements(DesignerProject index,
			ELEMENT_TYPES type, List<DesignerElement> filteredEntities, boolean includeTimeEvents) {
		List<? extends DesignerElement> children;
		if (isRuleType(type)) {
			children = index.getRuleElements();
		} else if (isArchiveType(type)) {
			children = index.getArchiveElements();
		} else if (isImplementationType(type)) {
			children = index.getDecisionTableElements();
		} else if (isDomainInstanceType(type)) {
			children = index.getDomainInstanceElements();
			if(children.isEmpty()) {
			// BE-22269 - this should not be computed here.  The index is read only at this point
			// Add the domain instance entries elsewhere
//				List<? extends DesignerElement> entityElements = index.getEntityElements();
//				for(DesignerElement entity :  entityElements) {
//					addDomainInstanceEntries(index, entity);
//				}
			}
		} else {
			children = index.getEntityElements();
		}
		for (int i=0; i<children.size(); i++) {
			DesignerElement designerElement = children.get(i);
			if (designerElement.getElementType() == type) {
				filteredEntities.add(designerElement);
			} else if (type == ELEMENT_TYPES.SIMPLE_EVENT && designerElement.getElementType() == ELEMENT_TYPES.TIME_EVENT) {
				// special case time events
				filteredEntities.add(designerElement);
			}
		}
		EList<DesignerProject> referencedProjects = index.getReferencedProjects();
		for (int i=0; i<referencedProjects.size(); i++) {
			DesignerProject designerProject = referencedProjects.get(i);
			internalGetAllElements(designerProject, type, filteredEntities, includeTimeEvents);
		}
	}
	
	public static List<DesignerElement> getAllElements(String projectName, ELEMENT_TYPES[] types) {
		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		if (!sunClassAvailable()) {
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
		}
		
		try {
			DesignerProject index = getIndex(projectName);
			return getAllElements(index, types);
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
	}

	/**
	 * Get all Destinations from the Current Project
	 * @param projectName
	 * @return
	 */
	public static Map<String, Destination> getAllDestinationsURIMaps(String projectName) {
		Map<String, Destination> map = new HashMap<String, Destination>();		
		try{
			List<Entity> channels = getAllEntities(projectName, ELEMENT_TYPES.CHANNEL);
			for(Entity entity:channels){
				Channel channel = (Channel)entity;
				for(Destination dest:channel.getDriver().getDestinations()){
					StringBuilder sBuilder = new StringBuilder();
					String path = sBuilder.append(channel.getFullPath())
					/*.append(".channel")*/
					.append("/")
					.append(dest.getName())
					.toString();
					if(!map.containsKey(path)){
						map.put(path, dest);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	public static List<DesignerElement> getAllElements(DesignerProject index,
			ELEMENT_TYPES[] types) {
		List<DesignerElement> filteredEntities = new ArrayList<DesignerElement>();
		if (index == null) {
			return filteredEntities;
		}
		boolean includeTimeEvents = true;
		for (ELEMENT_TYPES type : types) {
			if (type == ELEMENT_TYPES.TIME_EVENT) {
				includeTimeEvents = false; // do not special case time events, as they will be added directly
			}
		}
		for (ELEMENT_TYPES type : types) {
			filteredEntities.addAll(getAllElements(index, type, includeTimeEvents));
		}
		return filteredEntities;
	}
	
	public static List<Entity> getAllEntities(String projectName, ELEMENT_TYPES type) {
		waitForUpdate();

		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		if (!sunClassAvailable()) {
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
		}

		try {
			DesignerProject index = getIndex(projectName);
			List<Entity> filteredEntities = internalGetAllEntities(type, index, true);
			return filteredEntities;
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
		
	}

	private static List<Entity> internalGetAllEntities(ELEMENT_TYPES type,
			DesignerProject index, boolean includeTimeEvents) {
		List<Entity> filteredEntities = new ArrayList<Entity>();
		if(index != null){
			EList<EntityElement> entities = index.getEntityElements();
			
			for (int i=0; i<entities.size(); i++) {
				EntityElement entityEntry = entities.get(i);
				if (entityEntry.getElementType() == type && entityEntry.getEntity() != null) {
					filteredEntities.add(entityEntry.getEntity());
				}
				// special case TimeEvents
				if (entityEntry.getElementType() == ELEMENT_TYPES.TIME_EVENT && type == ELEMENT_TYPES.SIMPLE_EVENT && includeTimeEvents) {
					filteredEntities.add(entityEntry.getEntity());
				}
			}
			EList<DesignerProject> referencedProjects = index.getReferencedProjects();
			for (int i=0; i<referencedProjects.size(); i++) {
				DesignerProject designerProject = referencedProjects.get(i);
				filteredEntities.addAll(internalGetAllEntities(type, designerProject, includeTimeEvents));
			}
		}
		return filteredEntities;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param projectName
	 * @param types
	 * @param waitForUpdate --> waits for update if it's true
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Entity> List<T> getAllEntities(String projectName, ELEMENT_TYPES[] types , boolean waitForUpdate) {
		if (waitForUpdate){
			waitForUpdate();
		}
		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		if (!sunClassAvailable()) {
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
		}
		
		try {
			DesignerProject index = getIndex(projectName);
			List<T> filteredEntities = new ArrayList<T>();
			boolean includeTimeEvents = true;
			for (ELEMENT_TYPES type : types) {
				if (type == ELEMENT_TYPES.TIME_EVENT) {
					includeTimeEvents = false; // do not special case time events, as they will be added directly
					break;
				}
			}
			
			if (index != null) {
				for (ELEMENT_TYPES type : types) {
					filteredEntities.addAll((Collection<? extends T>) internalGetAllEntities(type, index, includeTimeEvents));
				}
			}
			return filteredEntities;
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
	}
	
	
	public static <T extends Entity> List<T> getAllEntities(String projectName, ELEMENT_TYPES[] types) {
		return getAllEntities(projectName, types, true);
	}
	
	public static boolean containsElementType(ELEMENT_TYPES elementType, ELEMENT_TYPES[] types) {
		for (ELEMENT_TYPES type : types) {
			if (elementType == type) {
				return true;
			}
		}
		return false;
	}
	
	public static Concept getConcept(String projectName, String conceptPath) {
		Concept concept =  (Concept) getEntity(projectName, conceptPath, ELEMENT_TYPES.CONCEPT);
		if(concept != null) {
			return concept;
		} else {
			concept =  (Concept) getEntity(projectName, conceptPath, ELEMENT_TYPES.SCORECARD);
			if (concept != null) {
				return concept;
			} else {
				concept =  (Concept) getEntity(projectName, conceptPath, ELEMENT_TYPES.METRIC);
				if (concept != null) {
					return concept;
				}
			}
		}
		return null;
	}
	
	private static void addDomainInstanceEntries(DesignerProject index, DesignerElement designerElement) {
		Entity entity = null;
		if(designerElement instanceof EntityElement) {
			entity = ((EntityElement) designerElement).getEntity();
		}
		if (!(entity instanceof Concept) && !(entity instanceof Event)) {
			return;
		}
		EList<PropertyDefinition> properties = null;
		if (entity instanceof Concept) {
			properties = ((Concept) entity).getProperties();
		} else if (entity instanceof Event) {
			properties = ((Event) entity).getProperties();
		}
		if (properties == null) {
			return;
		}
		EList<InstanceElement<?>> elements = index.getDomainInstanceElements();
		InstanceElement<?> resourceEntry = null;
		String fullPath = entity.getFullPath();
		for (int i=0; i<elements.size(); i++) {
			InstanceElement<?> instanceEntry = elements.get(i);
			if (instanceEntry.getEntityPath().equals(fullPath)) {
				resourceEntry = instanceEntry;
			}
		}
		if (resourceEntry == null) {
			resourceEntry = IndexFactory.eINSTANCE.createInstanceElement();
			resourceEntry.setEntityPath(fullPath);
			resourceEntry.setElementType(ELEMENT_TYPES.DOMAIN_INSTANCE);
			resourceEntry.setName(entity.getName());
		}
		for (int i = 0; i < properties.size(); i++) {
			PropertyDefinition propDef = properties.get(i);
			EList<DomainInstance> domainInstances = propDef.getDomainInstances();
			for (int j = 0; j < domainInstances.size(); j++) {
				DomainInstance domainInstance = domainInstances.get(j);
				EList instances = resourceEntry.getInstances();
				instances.add(domainInstance);
			}
		}
		if (resourceEntry.getInstances().size() > 0) {
			elements.add(resourceEntry);
		}
	}
	/**
	 * Get index of {@link Domain} from the ontology index.
	 * @param projectName
	 * @param domainPath
	 * @return
	 */
	public static Domain getDomain(String projectName, String domainPath) {
		return (Domain) getEntity(projectName, domainPath, ELEMENT_TYPES.DOMAIN);
	}
	
	public static List<Event> getAllEvents(String projectName) {
		return getAllEntities(projectName, 
				new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.TIME_EVENT });
	}
	
	public static Event getSimpleEvent(String projectName, String eventPath) {
		return (Event) getEntity(projectName, eventPath, ELEMENT_TYPES.SIMPLE_EVENT);
	}
	
	public static Event getTimeEvent(String projectName, String eventPath) {
		return (Event) getEntity(projectName, eventPath, ELEMENT_TYPES.TIME_EVENT);
	}
	
	public static Channel getChannel(String projectName, String channelPath) {
		return (Channel) getEntity(projectName, channelPath, ELEMENT_TYPES.CHANNEL);
	}
	
	public static String getFileExtension(Entity entity) {
		return getFileExtension(getElementType(entity));
	}

	public static EObject loadEObject(java.net.URI fileURI) {
		return loadEObject(fileURI, true);
	}

	public static EObject loadEObject(java.net.URI fileURI,ResourceSet resourceSet, boolean showErrorMessage) {
		URI uri = URI.createURI(fileURI.toString());
		// using XMI instead of XML
//		ResourceSet resourceSet = new ResourceSetImpl();
		try {
			Thread thread = Thread.currentThread();
			ClassLoader loader = thread.getContextClassLoader();
			if (!sunClassAvailable()) {
				System.setProperty("javax.xml.parsers.SAXParserFactory",
						"org.apache.xerces.jaxp.SAXParserFactoryImpl");	               
				thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
			}
			Resource resource = null;
			try {
				resource = resourceSet.getResource(uri,true);
			} finally {
				thread.setContextClassLoader(loader);
				// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
				CommonIndexUtils.sunClassAvailable();
			}
			
			EList<EObject> contents = resource.getContents();
			if (contents != null && contents.size() > 0) {
				//TODO this is to take of the BEViews System Elements which contains more then one element
				return (EObject) contents.get(contents.size()-1);
			}
		} catch (Exception e) {
			if (showErrorMessage) {
				System.err.println("Could not load "+fileURI.toString());
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Loads EMF Objects like Concept,Events
	 * @param fileURI
	 * @param showErrorMessage
	 * @return
	 */
	public static EObject loadEObject(java.net.URI fileURI, boolean showErrorMessage) {
		String filePath = fileURI.toString();
		URI uri = URI.createURI(filePath);
		// using XMI instead of XML
		ResourceSet resourceSet = new ResourceSetImpl();
		try {
    		Thread thread = Thread.currentThread();
    		ClassLoader loader = thread.getContextClassLoader();
    		if (!sunClassAvailable()) {
    			System.setProperty("javax.xml.parsers.SAXParserFactory",
    				"org.apache.xerces.jaxp.SAXParserFactoryImpl");	               
    			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
    		}
    		Resource resource = null;
    		try {
    			resource = resourceSet.getResource(uri,true);
    		} finally {
    			thread.setContextClassLoader(loader);
    			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
    			CommonIndexUtils.sunClassAvailable();
    		}

			EList<EObject> contents = resource.getContents();
			if (contents != null && contents.size() > 0) {
				//TODO this is to take of the BEViews System Elements which contains more then one element
				return (EObject) contents.get(contents.size()-1);
			}
		} catch (Exception e) {
			if (showErrorMessage) {
				System.err.println("Could not load "+fileURI.toString());
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public synchronized static boolean sunClassAvailable() {
		if(System.getProperty("os.name").equals("AIX") || System.getProperty("os.arch").equals("s390x")){
			//BE-19680: Sun parsers are provided by AIX since JDK 1.7 but causes ClassCastException, so avoid loading sun classes on AIX  
			sunParserCheckComplete = true;
			sunParserAvailable = false;
			return sunParserAvailable;
		}
		if (sunParserCheckComplete) {
			if (sunParserAvailable) {
				// just set it here so that it doesn't need to be set in all places that perform this check
    			System.setProperty("javax.xml.parsers.SAXParserFactory",
    				"com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
    			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
    				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
			}
			return sunParserAvailable;
		}
		try {
			sunParserAvailable = Class.forName("com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl") != null;
		} catch (ClassNotFoundException e) {
			sunParserAvailable = false;
		}
		sunParserCheckComplete = true;
		if (sunParserAvailable) {
			// just set it here so that it doesn't need to be set in all places that perform this check
			System.setProperty("javax.xml.parsers.SAXParserFactory",
				"com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
			System.setProperty("javax.xml.parsers.DocumentBuilderFactory",
				"com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		}
		return sunParserAvailable;
	}

	public static EObject loadEObject(String uri,InputStream is) {
		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		if (!sunClassAvailable()) {
			System.setProperty("javax.xml.parsers.SAXParserFactory",
				"org.apache.xerces.jaxp.SAXParserFactoryImpl");	               
			thread.setContextClassLoader(CommonIndexUtils.class.getClassLoader());
		}
		ResourceSet resourceSet = new ResourceSetImpl();
		try {
            File file = new File(uri);
            URI resourceUri;
            //If this is a file create file uri
            resourceUri = (file.exists()) ? URI.createFileURI(uri) : URI.createURI(uri);

            Resource resource = resourceSet.createResource(resourceUri);
			Map options = new HashMap();
			options.put( XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD );
			resource.load(is, options);
			EList<EObject> contents = resource.getContents();
			if (contents != null && contents.size() > 0) {
				//TODO this is to take of the BEViews System Elements which contains more then one element
				return (EObject) contents.get(contents.size()-1);
			}
		} catch (Exception e) {
			System.err.println("Could not load "+uri);
			e.printStackTrace();
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available), so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
		return null;
	}

	public static EObject deserializeEObjectFromString(String serialized)
			throws Exception {
		if (serialized == null || serialized.length() == 0) {
			throw new IllegalArgumentException("Argument passed invalid");
		}
		byte[] bytes = serialized.getBytes("UTF-8");
		return deserializeEObject(bytes);
	}
	
	/**
	 * 
	 * @param contents
	 * @return
	 * @throws Exception
	 */
	public static EObject deserializeEObject(byte[] contents) throws Exception {
		return deserializeEObject(contents, null);
	}
	
	/**
	 * 
	 * @param contents
	 * @param options
	 * @return
	 * @throws Exception
	 */
	public static EObject deserializeEObject(byte[] contents, Map<?, ?> options) throws Exception {
		if (contents == null || contents.length == 0) {
			throw new IllegalArgumentException("Argument passed invalid");
		}
		ByteArrayInputStream bis = new ByteArrayInputStream(contents);
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"*", new XMIResourceFactoryImpl());
		XMIResource resource = new XMIResourceImpl();
		resource.load(bis, options);
		EObject eobject = resource.getContents().get(0);
		return eobject;
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static EObject deserializeEObject(InputStream is) throws Exception {
		return deserializeEObject(is, null);
	}
	
	/**
	 * 
	 * @param is
	 * @param options
	 * @return
	 * @throws Exception
	 */
	public static EObject deserializeEObject(InputStream is, Map<?, ?> options) throws Exception {
		if (is == null ) {
			throw new IllegalArgumentException("Argument passed invalid");
		}	
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"*", new XMIResourceFactoryImpl());
		XMIResource resource = new XMIResourceImpl();
		resource.load(is, options);
		EObject eobject = resource.getContents().get(0);		
		return eobject;
	}
	
	public static StateEntity getStateEntity(StateMachine machine, String fullPath) {
		StringTokenizer st = new StringTokenizer(fullPath, "/");
		return getStateEntity(machine, st);
	}

	private static StateEntity getStateEntity(StateComposite composite,
			StringTokenizer st) {
		EList<StateEntity> stateEntities = composite.getStateEntities();
		String pathEntry = st.nextToken();
		for (StateEntity stateEntity : stateEntities) {
			if (stateEntity.getName().equals(pathEntry)) {
				if (st.hasMoreTokens()) {
					if (stateEntity instanceof StateComposite) {
						return getStateEntity((StateComposite)stateEntity, st);
					}
				} else {
					return stateEntity;
				}
			}
		}
		return null;
	}
	
	public static String getStateEntityFullPath(StateEntity entity, boolean includeStateMachineName) {
		String fullPath = entity.getName();
		while (entity.eContainer() != null) {
			entity = (StateEntity) entity.eContainer();
			if (entity instanceof StateMachine && !includeStateMachineName) {
				break;
			}
			fullPath = entity.getName() + "/" + fullPath;
		}
		return fullPath;
	}
	
	public static String getStateEntityFullPath(StateEntity entity) {
		return getStateEntityFullPath(entity, false);
	}
	
	public static StateTransition getStateTransition(StateMachine machine, String transitionName) {
		EList<StateTransition> stateTransitions = machine.getStateTransitions();
		for (StateTransition stateTransition : stateTransitions) {
			if (transitionName.equals(stateTransition.getName())) {
				return stateTransition;
			}
		}
		return null;
	}

	public static String getFileExtension(ELEMENT_TYPES type) {
		//HACK 
		if (type.compareTo(ELEMENT_TYPES.TEXT_COMPONENT) == 0){
			type = ELEMENT_TYPES.CHART_COMPONENT;
		}
		Set<String> keySet = fileExtToElementType.keySet();
		for (String fileExt : keySet) {
			ELEMENT_TYPES entityType = fileExtToElementType.get(fileExt);
			if (entityType.equals(type)) {
				return fileExt;
			}
		}
		
		return "";
	}
	
	/**
	 * Parse a rule function file and get the {@link RuleFunction}
	 * @param projectName -> The name of the project to which this rf belongs
	 * @param ruleFuncFile -> Absolute path of the rule function file
	 * @return
	 */
	public static RuleFunction parseRuleFunctionFile(String projectName, String ruleFuncFile) {
		RulesASTNode tree = (RulesASTNode) CommonRulesParserManager.parseRuleFile(projectName, ruleFuncFile);
		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(projectName);
		tree.accept(visitor);
		com.tibco.cep.designtime.core.model.rule.RuleFunction newRF = 
			(com.tibco.cep.designtime.core.model.rule.RuleFunction) visitor.getRule();
		return newRF;
	}

	/**
	 * @param projectName
	 * @param compilableFile
	 * @return
	 */
	public static Compilable parseCompilableFile(String projectName, String compilableFile) {
		RulesASTNode tree = (RulesASTNode) CommonRulesParserManager.parseRuleFile(projectName, compilableFile);
		RuleCreatorASTVisitor visitor = new RuleCreatorASTVisitor(projectName);
		tree.accept(visitor);
		com.tibco.cep.designtime.core.model.rule.Compilable compilable = 
			(com.tibco.cep.designtime.core.model.rule.Compilable) visitor.getRule();
		return compilable;
	}

	
	public static EObject getRootContainer(EObject var) {
		while (var.eContainer() != null) {
			var = var.eContainer();
		}
		return var;
	}
	
	public static DesignerElement getVariableContext(EObject var) {
		while (var != null && !(var.eContainer() instanceof RuleElement)
				&& !(var.eContainer() instanceof StateMachineElement)
				&& !(var.eContainer() instanceof EventElement)) {
			if (var.eContainer() == null) {
				return null;
			}
			var = var.eContainer();
		}
		if (var == null) {
			return null;
		}
		return (DesignerElement) var.eContainer();
	}

	public static DesignerProject getDesignerProject(DesignerElement element) {
		while (element.eContainer() != null) {
			if (element.eContainer() instanceof DesignerProject) {
				return (DesignerProject) element.eContainer();
			}
			element = (DesignerElement) element.eContainer();
		}
		return null;
	}

	public static String getFullPath(Entity entity) {
		if (entity == null) {
			return "";
		}
		if (entity.getFolder() == null || entity.getFolder().length() == 0) {
			return "/" + entity.getName();
		}
		return entity.getFolder() + entity.getName();
	}

	/**
	 * Returns true if the two Entities' name, folder, and class are equal, false otherwise
	 * @param entity1
	 * @param entity2
	 * @return
	 */
	public static boolean areEqual(Entity entity1, Entity entity2) {
		if (entity1 instanceof PropertyDefinition && entity2 instanceof PropertyDefinition) {
			if (!entity1.getName().equals(entity2.getName())) {
				return false;
			}
			String ownerPath1 = ((PropertyDefinition)entity1).getOwnerPath();
			String ownerPath2 = ((PropertyDefinition)entity2).getOwnerPath();
			return ownerPath1.equals(ownerPath2);
		} else if (entity1.getName() != null && entity1.getName().equals(entity2.getName())
				&& (entity1.getFolder() != null && entity1.getFolder().equals(entity2.getFolder())
						|| (entity1.getFolder() == null && entity2.getFolder() == null))
				&& entity1.getClass().equals(entity2.getClass())) {
			return true;
		}
		return false;
	}

	public static String getVariableContextProjectName(EObject var) {
		if (var instanceof GlobalVariableExtension) {
			return ((GlobalVariableExtension) var).getProjectName();
		}
		DesignerElement element = getVariableContext(var);
		if (element instanceof RuleElement) {
			return ((RuleElement) element).getIndexName();
		} else if (element instanceof StateMachineElement) {
			return ((StateMachineElement) element).getIndexName();
		} else if (element instanceof EntityElement) {
			return ((EntityElement) element).getEntity().getOwnerProjectName();
		}
		return null;
	}

	/**
	 * returns the destination
	 * @since 4.0
	 * @param projectName
	 * @param destinationURI
	 * @return
	 */
	public static Destination getDestination(String projectName,
			String destinationURI) {
		// destinationURI is in the format /MyFolder/MyChannel.channel/MyDestination
		int index = destinationURI.lastIndexOf(".channel");
		if (index == -1) {
			return null;
		}
		String fullChannelPath = destinationURI.substring(0, index);
		String destinationName = destinationURI.substring(index+9); // ".channel".length() + 1
		Channel channel = getChannel(projectName, fullChannelPath);
		if (channel == null) {
			// System.err.println("Could not find channel "+fullChannelPath);
			return null;
		}
		if (channel.getDriver() == null) {
			return null;
		}
		EList<Destination> destinations = channel.getDriver().getDestinations();
		for (Destination destination : destinations) {
			if (destination.getName().equalsIgnoreCase(destinationName)) {
				return destination;
			}
		}
		return null;
	}
	/**
	 * returns the element type
	 * @since 4.0
	 * @param extension
	 * @return
	 */
	public static ELEMENT_TYPES getElementType(final String extension){
		if (extension == null || extension.trim().length() == 0) return null;
		return fileExtToElementType.get(extension);
	}
	/**
	 * get xmi resource content bytes
	 * @since 4.0
	 * @param eObject
	 * @return
	 * @throws IOException
	 */
	public static byte[] getEObjectContents(final String containerPath, final EObject eObject)
	throws IOException {
		final URI baseURI = containerPath == null ? null : URI.createFileURI(containerPath);
//		System.out.println("CommonIndexUtils.getEObjectContents():Using base URI as "+baseURI);
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
			resource = new XMIResourceImpl(URI.createURI(""));
		}
		resource.getContents().add(eObject);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			HashMap persistenceOptions = new HashMap(getPersistenceOptions());
			persistenceOptions.put(XMIResource.OPTION_URI_HANDLER, new XMIResource.URIHandler(){

				@Override
				public URI deresolve(URI uri) {
//					System.out.println("XMIResource.URIHandler.deresolve("+uri+")");
					if (uri.isRelative() == false && baseURI != null){
						URI resolvedURI = uri.deresolve(baseURI);
//						System.out.println("XMIResource.URIHandler.deresolved to "+resolvedURI+"");						
						return resolvedURI;
					}
					return uri;
				}

				@Override
				public URI resolve(URI uri) {
					throw new UnsupportedOperationException("resolve");
				}

				@Override
				public void setBaseURI(URI uri) {
					//do nothing
				}
				
			});
			resource.save(os, persistenceOptions);
			return os.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public static InputStream getEObjectInputStream(EObject eObject)
	throws IOException {
		eObject = EcoreUtil.copy(eObject);
		Resource resource = new XMIResourceImpl(URI.createURI(""));
		resource.getContents().add(eObject);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			resource.save(os, ModelUtils.getPersistenceOptions());
			return new ByteArrayInputStream(os.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * get jar entry text
	 * @since 4.0
	 * @param jarFile
	 * @param entry
	 * @return
	 */
	public static String getJarEntryText(JarFile jarFile, JarEntry entry) {
		byte[] contents = getJarEntryContents(jarFile, entry);
		String documentString;
		try {
			documentString = new String(contents, ModelUtils.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			documentString = new String(contents);
		}
		return documentString;
	}
	/**
	 * get Jar entry contents
	 * @since 4.0
	 * @param jarFile
	 * @param entry
	 * @return
	 */
	public static byte[] getJarEntryContents(JarFile jarFile, JarEntry entry) {
		try {
			InputStream stream = jarFile.getInputStream(entry);
			int avail = stream.available();
			byte[] bytes = new byte[avail];
			int offset = stream.read(bytes);
			while (stream.available() > 0) {
				avail = stream.available();
				offset += stream.read(bytes, offset, avail);
			}
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}
	/**
	 * returns a referenced designer project
	 * @since 4.0
	 * @param dgp
	 * @param libRef
	 * @return
	 */
	public static DesignerProject getReferencedDesignerProject(DesignerProject dgp , String libRef){
		if (dgp == null || libRef == null || libRef.trim().length() == 0) return null;
		for (DesignerProject d: dgp.getReferencedProjects()){
			String archivePath = d.getArchivePath();
			if (archivePath == null) continue;
			if (libRef.equals(archivePath)){
				return d;
			}
		}
		return null;
	}
	/**
	 * returns the folder for the given file
	 * @since 4.0
	 * @param projectName
	 * @param fullPath
	 * @return
	 */
	public static ElementContainer getFolderForFile(String projectName, String fullPath) {
		DesignerProject index = getIndex(projectName);
		Path path = new Path(fullPath);
		return getFolder(index, path);
	}
	/**
	 * returns the folder for the given file
	 * @since 4.0
	 * @param index
	 * @param fullPath
	 * @param createIfAbsent
	 * @return
	 */
	public static ElementContainer getFolderForFile(DesignerProject index, String fullPath, boolean createIfAbsent) {
		return getFolderForFile(index, fullPath, createIfAbsent, false);
	}
	/**
	 * returns the folder for the given file
	 * @since 4.0
	 * @param index
	 * @param fullPath
	 * @param createIfAbsent
	 * @return
	 */
	public static ElementContainer getFolderForFile(DesignerProject index, String fullPath, boolean createIfAbsent, boolean isBinaryFile) {
		Path path = new Path(fullPath);
		return getFolder(index, path, createIfAbsent, isBinaryFile);
	}
	/**
	 * return the containing folder for the given path
	 * @since 4.0
	 * @param container
	 * @param path
	 * @return
	 */
	public static ElementContainer getFolder(ElementContainer container, Path path) {
		return getFolder(container, path, false, false);
	}

	public static boolean isProjectLibType(String Folderpath) {
		String projLibFolder="."+"projlib";
		if(Folderpath.contains(projLibFolder)){
			return true;
		}
		return false;
	}
	
	/**
	 * returns the element container
	 * @since 4.0
	 * @param container - the container of the file
	 * @param path - the path to the file
	 * @param createIfAbsent - create the folder if it does not already exist
	 * @param isReferenceFile - whether this file is a referenced/binary file (i.e. in a project library)
	 * @return
	 */
	protected static ElementContainer getFolder(ElementContainer container, Path path, boolean createIfAbsent, boolean isReferencedFile) {
		Path originalPath = path;
		if (container == null) {
			return null;
		}
		EList<DesignerElement> entries = container.getEntries();
		if(path.segment(0) == null) {
			return container;
		}
		
		for (DesignerElement element : entries) {
			if (element instanceof ElementContainer && element.getName() != null
					&& element.getName().equals(path.segment(0))) {
				if (path.segmentCount() == 1) {
					return (Folder) element;
				}
				path = path.removeFirstSegments(1);
				ElementContainer folder = getFolder((Folder) element, path, createIfAbsent, isReferencedFile);
				if (folder != null) {
					return folder;
				}
			}
		}
		if (container instanceof DesignerProject && isReferencedFile) { // do not look at referenced projects if this is not a referenced/binary file
			EList<DesignerProject> referencedProjects = ((DesignerProject) container).getReferencedProjects();
			for (DesignerProject refProj : referencedProjects) {
				ElementContainer folder = getFolder(refProj, originalPath, createIfAbsent, isReferencedFile);
				if (folder != null) {
					return folder;
				}
			}
		}
		if (!createIfAbsent) {
			return null;
		}
		// folder has not yet been created
		Folder folder = IndexFactory.eINSTANCE.createFolder();
		folder.setName(originalPath.segment(0));
		folder.setElementType(ELEMENT_TYPES.FOLDER);
		container.getEntries().add(folder);
		if (originalPath.segmentCount() == 1) {
			return folder;
		}
		originalPath = originalPath.removeFirstSegments(1);
		return getFolder(folder, originalPath, createIfAbsent, isReferencedFile);
	}
	/**
	 * returns the relative file path from the project root for the given element
	 * @since 4.0
	 * @param element
	 * @return
	 */
	public static String getRelativePath(TypeElement element) {
		String fileName = element.getFolder()+"/"+element.getName()+"."+getFileExtension(element.getElementType());
		return fileName;
	}
	/**
	 * returns the file for the given index name and element
	 * @since 4.0
	 * @param indexName
	 * @param element
	 * @return
	 */
	public static String getFilePath(String indexName, TypeElement element) {
		DesignerProject index = getIndex(indexName);
		if (index == null) return null;
		String rootPath = index.getRootPath();
		String fileName = getRelativePath(element);
		
		if (!fileName.startsWith("/") && !fileName.startsWith("\\")) {
			fileName = "/"+fileName;
		}
		String path = rootPath + fileName;
		return path;
	}
	/**
	 * returns the file for the given index name and element
	 * @since 4.0
	 * @param indexName
	 * @param element
	 * @return
	 */
	public static File getJavaFile(String indexName, TypeElement element) {
		String filePath = getFilePath(indexName, element);
		if (filePath == null) {
			return null;
		}
		File file = new File(filePath);
		if (file.exists()) {
			return file;
		}
		return null;
	}
	/**
	 * returns the element type of the given file
	 * @since 4.0
	 * @param file
	 * @return
	 */
	public static ELEMENT_TYPES getIndexType(Path file) {
		if (fileExtToElementType.containsKey(file.getFileExtension())) {
			return ELEMENT_TYPES.get(getFileType(file).getValue());
		}
		if (EAR_EXTENSION.equalsIgnoreCase(file.getFileExtension())) {
			return ELEMENT_TYPES.ENTERPRISE_ARCHIVE;
		}
		return ELEMENT_TYPES.UNKNOWN;
	}
	/**
	 * returns the element type of the given file
	 * @since 4.0
	 * @param file
	 * @return
	 */
	public static ELEMENT_TYPES getFileType(Path file) {
		String fileExtension = file.getFileExtension();
		return fileExtToElementType.get(fileExtension);
	}
	/**
	 * returns the Folder path of a given file
	 * @since 4.0
	 * @param projectPath
	 * @param file
	 * @return
	 */
	public static String getFileFolder(Path projectPath,Path file) {
		Path path = file;
		Path projPath = projectPath;
		path = path.removeFirstSegments(projPath.segmentCount());
		path = path.setDevice("");
		path = path.removeLastSegments(1);
		if (path.isEmpty()) {
			return "/";
		}
		return "/"+path.toOSString().replaceAll("\\\\", "/")+"/";
	}
	/**
	 * returns the relative file path from the project root
	 * @since 4.0
	 * @param projectPath
	 * @param file
	 * @return
	 */
	public static String getRelativeFilePath(Path projectPath,Path file){
		Path path = file;
		Path projPath = projectPath;
		path = path.removeFirstSegments(projPath.segmentCount());
		path = path.setDevice("");
		if (path.isEmpty()) {
			return "/";
		}
		return "/"+path.toOSString().replaceAll("\\\\", "/");
	}
	
	/**
	 * returns the last modified date
	 * @since 4.0
	 * @param ontologyName
	 * @return
	 */
	public static Date getLastModifiedDate(String ontologyName) {
		DesignerProject index = getIndex(ontologyName);
		return index.getLastModified();
	}
	/**
	 * returns the last persisted date
	 * @since 4.0
	 * @param ontologyName
	 * @return
	 */
	public static Date getLastPersistedDate(String ontologyName) {
		DesignerProject index = getIndex(ontologyName);
		return index.getLastModified();
	}

	/**
	 * @param name
	 * @param typeURI
	 * @return the newly created Symbol
	 */
	public static Symbol createSymbol(String name, String typeURI) {
		Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
		symbol.setIdName(name);
		symbol.setType(typeURI);
		return symbol;
	}

	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param superConceptPath
	 * @param autoNameOnConflict
	 * @return
	 */
	public static Concept createConcept(String projectName, 
			String folderPath, 
			String namespace,
			String name, 
			String superConceptPath, 
			boolean autoNameOnConflict) {
					Concept concept = ElementFactory.eINSTANCE.createConcept();
					populateEntity(projectName, folderPath,namespace, name, concept);
					concept.setSuperConceptPath(superConceptPath);
			//		persistEntity(projectName, concept, new NullProgressMonitor());	
					return concept;
				}
	
	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param superConceptPath
	 * @param autoNameOnConflict
	 * @return
	 */
	public static Scorecard createScoreCard(String projectName, 
			String folderPath,
			String namespace, 
			String name, 
			String superConceptPath, 
			boolean autoNameOnConflict) {
				Scorecard scorecard = ElementFactory.eINSTANCE.createScorecard();
				populateEntity(projectName, folderPath,namespace, name, scorecard);
				scorecard.setSuperConceptPath(superConceptPath);
				scorecard.setScorecard(true);
				return scorecard;
			}

	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param entity
	 */
	protected static void populateEntity(String projectName, 
			String folderPath,
			String namespace, 
			String name, 
			Entity entity) {
				entity.setName(name);
				entity.setDescription("");
				entity.setFolder(folderPath);
				entity.setNamespace(namespace);
				entity.setGUID(GUIDGenerator.getGUID());
				entity.setOwnerProjectName(projectName);
				PropertyMap value = ModelFactory.eINSTANCE.createPropertyMap();
				entity.setExtendedProperties(value);
			}

	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param ttl
	 * @param ttlUnits
	 * @param renameOnConflict
	 * @return
	 */
	public static SimpleEvent createEvent(String projectName, 
			String folderPath, 
			String namespace,
			String name, 
			String ttl, 
			TIMEOUT_UNITS ttlUnits, 
			boolean renameOnConflict) {
				SimpleEvent event = EventFactory.eINSTANCE.createSimpleEvent();
				populateEntity(projectName, folderPath, namespace, name, event);
				event.setSuperEventPath("");
				event.setTtl(ttl);
				event.setTtlUnits(ttlUnits);		
				return event;
			}

	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @param ttl
	 * @param ttlUnits
	 * @param interval
	 * @param intervalUnits
	 * @param scheduleType
	 * @param renameOnConflict
	 * @return
	 */
	public static TimeEvent createTimeEvent(
			String projectName, 
			String folderPath,
			String namespace, 
			String name, 
			String ttl, 
			TIMEOUT_UNITS ttlUnits, 
			String interval,
			TIMEOUT_UNITS intervalUnits, 
			EVENT_SCHEDULE_TYPE scheduleType, 
			boolean renameOnConflict) {
				TimeEvent event = EventFactory.eINSTANCE.createTimeEvent();
				populateEntity(projectName, folderPath, namespace, name, event);
				event.setSuperEventPath("");
				event.setTtl(ttl);
				event.setTtlUnits(ttlUnits);
				event.setInterval(interval);
				event.setIntervalUnit(intervalUnits);
				event.setScheduleType(scheduleType);
				return event;
			}

	/**
	 * @param concept
	 * @param name
	 * @param type
	 * @param ownerPath
	 * @param historyPolicy
	 * @param historySize
	 * @param isArray
	 * @param defaultValue
	 * @return
	 */
	public static PropertyDefinition createPropertyDefinition(Concept concept, 
			String name,
			PROPERTY_TYPES type, 
			String ownerPath, 
			int historyPolicy, 
			int historySize, 
			boolean isArray,
			String defaultValue) {
				PropertyDefinition propertyDefinition = ElementFactory.eINSTANCE.createPropertyDefinition();
				concept.getProperties().add(propertyDefinition);
				propertyDefinition.setName(name);
				propertyDefinition.setType(type);
				//set owner path type 
				propertyDefinition.setOwnerPath(ownerPath);
				propertyDefinition.setHistoryPolicy(historyPolicy);
				propertyDefinition.setHistorySize(historySize);
				propertyDefinition.setArray(isArray);
				propertyDefinition.setDefaultValue(defaultValue);
				propertyDefinition.setOwnerProjectName(concept.getOwnerProjectName());
				PropertyMap createPropertyMap = ModelFactory.eINSTANCE.createPropertyMap();
				propertyDefinition.setExtendedProperties(createPropertyMap);
				
				return propertyDefinition;
			}
	/**
	 * @param concept
	 * @param name
	 * @param type
	 * @param ownerPath
	 * @param historyPolicy
	 * @param historySize
	 * @param isArray
	 * @param defaultValue
	 * @return
	 */
	public static PropertyDefinition createAttributeDefinition(Concept concept, 
			String name,
			PROPERTY_TYPES type, 
			String ownerPath, 
			int historyPolicy, 
			int historySize, 
			boolean isArray,
			String defaultValue,
			String path) {
				PropertyDefinition propertyDefinition = ElementFactory.eINSTANCE.createPropertyDefinition();
				propertyDefinition.setName(name);
				propertyDefinition.setType(type);
				//set owner path type 
				propertyDefinition.setOwnerPath(ownerPath);
				propertyDefinition.setHistoryPolicy(historyPolicy);
				propertyDefinition.setHistorySize(historySize);
				propertyDefinition.setArray(isArray);
				propertyDefinition.setDefaultValue(defaultValue);
				propertyDefinition.setOwnerProjectName(concept.getOwnerProjectName());
				PropertyMap createPropertyMap = ModelFactory.eINSTANCE.createPropertyMap();
				propertyDefinition.setExtendedProperties(createPropertyMap);
				if(type == PROPERTY_TYPES.CONCEPT || type == PROPERTY_TYPES.CONCEPT_REFERENCE) {
					//pd.setConceptTypePath(getFullPath().substring(0, getFullPath().lastIndexOf("."))); //removed .beprocess extension
					propertyDefinition.setConceptTypePath(path);
				}
				return propertyDefinition;
			}

	/**
	 * @param event
	 * @param name
	 * @param type
	 * @param ownerPath
	 * @param defaultValue
	 * @return
	 */
	public static PropertyDefinition createPropertyDefinition(Event event, 
			String name,
			PROPERTY_TYPES type, 
			String ownerPath, 
			String defaultValue) {
				PropertyDefinition propertyDefinition = ElementFactory.eINSTANCE.createPropertyDefinition();
				event.getProperties().add(propertyDefinition);
				propertyDefinition.setName(name);
				propertyDefinition.setType(type);
				//set owner path type 
				propertyDefinition.setOwnerPath(ownerPath);
				propertyDefinition.setDefaultValue(defaultValue);
				propertyDefinition.setOwnerProjectName(event.getOwnerProjectName());
				PropertyMap createPropertyMap = ModelFactory.eINSTANCE.createPropertyMap();
				propertyDefinition.setExtendedProperties(createPropertyMap);
				
				return propertyDefinition;
			}

	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static Rule createRule(String projectName, 
			String folderPath, 
			String namespace,
			String name) throws Exception {
				com.tibco.cep.designtime.core.model.rule.Rule rule = RuleFactory.eINSTANCE.createRule();
				populateEntity(projectName, folderPath,namespace, name, rule);
				return rule;
			}

	/**
	 * @param projectName
	 * @param folderPath
	 * @param namespace
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static RuleFunction createRulefunction(String projectName, 
			String folderPath,
			String namespace, 
			String name) throws Exception {
				com.tibco.cep.designtime.core.model.rule.RuleFunction rulefunction = RuleFactory.eINSTANCE.createRuleFunction();
				populateEntity(projectName, folderPath,namespace, name, rulefunction);		
				return rulefunction;
			}

	/**
	 * Parse the java file to get the package information
	 * fill the JavaSource Object with source and package information.
	 * @param resource
	 * @return
	 */
	public static JavaSource loadJavaObject(File resource) {
		/*
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
		JavaSource js = JavaFactory.eINSTANCE.createJavaSource();
		js.setFolder(CommonIndexUtils.getFileFolder(file));
		final String className = cu.getElementName();
		js.setName(className.substring(0, className.indexOf(DOT)));
		try {
			js.setFullSourceText(cu.getSource().getBytes("UTF-8"));
			IPackageDeclaration[] packages = cu.getPackageDeclarations();
			if(packages.length > 0) {
				js.setPackageName(packages[0].getElementName());
			}
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}
		 */
		throw new UnsupportedOperationException();
	}
	public static JavaResource loadJavaResource(File resource) {
		/*
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(file);
		JavaSource js = JavaFactory.eINSTANCE.createJavaSource();
		js.setFolder(CommonIndexUtils.getFileFolder(file));
		final String className = cu.getElementName();
		js.setName(className.substring(0, className.indexOf(DOT)));
		try {
			js.setFullSourceText(cu.getSource().getBytes("UTF-8"));
			IPackageDeclaration[] packages = cu.getPackageDeclarations();
			if(packages.length > 0) {
				js.setPackageName(packages[0].getElementName());
			}
		} catch (Exception e) {
			StudioCorePlugin.log(e);
		}
		*/
		throw new UnsupportedOperationException();
	}

	/**
	 * Converts a eclipse projlib linked resource URI to a File System URI
	 * @param locationURI
	 * @return
	 * @throws URISyntaxException 
	 */
	public static String getLinkedResourcePath(java.net.URI locationURI) throws URISyntaxException{
		if(locationURI.getScheme().equals("projlib")){
			String path = locationURI.getPath();
			path = path.replaceAll(" ", "%20");
			path = path.startsWith(PATH_SEPARATOR) ? path.substring(1):path;
			java.net.URI uri = new java.net.URI(path);
			File f = new File(uri);
			Path p = new Path(f.getPath());
			return p.toPortableString();
		}
		return null;
	}

	
	public static List<PropertyDefinition> getAllConceptProperties(Concept concept){
		if(concept==null){
			return null;
		}
		List<PropertyDefinition> propertyList = new ArrayList<PropertyDefinition>();
		Concept superConcept = concept.getSuperConcept();
		if(superConcept!=null){
			propertyList.addAll(getAllConceptProperties(superConcept));
		}
		propertyList.addAll(concept.getProperties());
		return propertyList;		
	}

	public static String getJavaSourceFolder(String projectName, String folder, File projectPath) {
		StudioProjectConfiguration config = StudioProjectConfigurationCache.getInstance().get(projectName);
		if(config==null){
			try {
				config= IndexBuilder.getProjectConfig(projectPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(config !=null){
			EList<JavaSourceFolderEntry> entries = config.getJavaSourceFolderEntries();
			for (JavaSourceFolderEntry javaSourceFolderEntry : entries) {
				if (folder.startsWith(javaSourceFolderEntry.getPath(true))) {
					return javaSourceFolderEntry.getPath(true);
				}
			}
		}
		return null;
	}
	
	
}

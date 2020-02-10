package com.tibco.cep.studio.core.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.antlr.stringtemplate.StringTemplate;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.event.ImportRegistryEntry;
import com.tibco.cep.designtime.core.model.event.NamespaceEntry;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.rule.ActionContextSymbol;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.designtime.core.model.states.InternalStateTransition;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.rdf.EMFRDFTnsFlavor;
import com.tibco.xml.data.primitive.ExpandedName;

public class ModelUtils {
	public static final char FOLDER_SEPARATOR_CHAR = '/';
	public static final String UTF8_ENCODING = "UTF-8";
	public static final String DEFAULT_ENCODING = UTF8_ENCODING;
	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
//	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);

	private static final String OVERSIZE_FN_CLASS_NAME_SUFFIX = "$oversizeName";
	private static HashMap<String, String> fTemplates = new HashMap<String, String>();
	private static HashMap<Object, Object> fPersistenceOptions;
	
	/**
	 * Getting Persistence option for XMI resource
	 * @return
	 */
	public synchronized static HashMap<Object, Object> getPersistenceOptions() {
		fPersistenceOptions = new HashMap<Object, Object>();
		fPersistenceOptions.put(XMIResource.OPTION_ENCODING, DEFAULT_ENCODING);
		return fPersistenceOptions;
	}
	
	/**
	 * Save Option and load option adding for XML Resource
	 * @param resource
	 */
	public synchronized static HashMap<Object, Object> getPersistenceOptions(XMLResource resource) {
		resource.getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, "UTF-8");
	    resource.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
	    resource.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
	    resource.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
	    resource.getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);
	    return null;
	}
	
	
	public static boolean saveEObject(EObject obj) throws IOException {
		if (obj.eResource() != null) {
			obj.eResource().save(getPersistenceOptions());
			return true;
		}
		return false;
	}
	
    public static boolean IsEmptyString(String string) {
        return (string == null || string.trim().length() == 0);
    }

    public static boolean StringsAreEqual(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 != null) {
            return s1.equals(s2);
        }
        /** s1 is null, s2 is not */
        else {
            return false;
        }
    }

    /**
     * Removes leading and trailing slashes, and converts all slashes to dots.
     *
     * @param path
     * @return a String
     */
    
    public static String convertPathToPackage(String path) {
        return convertPathToPackage(path, FOLDER_SEPARATOR_CHAR);
    }
    
    public static String convertPathToPackage(String path, char separator) {
        if (!IsEmptyString(path)) {
            path = (path.charAt(0) == separator) ? path.substring(1) : path;
            path = path.replace(separator, '.');
            if (path.endsWith(".")) {
                path = path.substring(0, path.length() - 1);
            }
        }

        return path;
    }


    public static String convertPackageToPath(String pack) {
        if (!IsEmptyString(pack)) {
            pack = pack.replace('.', FOLDER_SEPARATOR_CHAR);
            if (pack.charAt(0) != FOLDER_SEPARATOR_CHAR) {
            	pack = FOLDER_SEPARATOR_CHAR + pack;
            }
        }

        return pack;
    }
    
	public static String getExpandedNameAsString(ExpandedName name) {
		StringBuffer buf = new StringBuffer();
		if (name.namespaceURI != null) {
			buf.append(name.namespaceURI);
			buf.append('.');
		}
		buf.append(name.localName);
		return buf.toString();
	}
	
  
	@SuppressWarnings("rawtypes")
	public static boolean areConceptConstructorArgsOversize(Concept cept) {
        List<PropertyDefinition> properties = cept.getProperties();
        int numProps = properties.size();
        if(numProps + 1 > 255) return true;
        int numBytes = 0;
        for(Iterator it = properties.iterator(); it.hasNext();) {
            numBytes += numFunctionArgBytes((PropertyDefinition)it.next());
        }
        //add one byte for extID
        numBytes++;
        return numBytes > 255;
    }
    public static boolean areEventConstructorArgsOversize(Event evt) {
        List<PropertyDefinition> properties = evt.getProperties();
        int numProps = properties.size();
        if(numProps + 1 > 255) return true;
        int numBytes = 0;
        for(PropertyDefinition usProperty : properties) {
            numBytes += numFunctionArgBytes(usProperty);
        }
        //add one byte for extID
        numBytes++;
        return numBytes > 255;
    }
    protected static int numFunctionArgBytes(PropertyDefinition propDef) {
        PROPERTY_TYPES type = propDef.getType();
        if(type == PROPERTY_TYPES.DOUBLE|| type == PROPERTY_TYPES.LONG) {
            return 2;
        } else {
            return 1;
        }
    }
    public static String getParentFullPath(final String fileFullPath ){     
    	String fullParentPath = null;
    	if (fileFullPath != null){
    		if (fileFullPath.indexOf(File.separatorChar) != -1){
    			fullParentPath = fileFullPath.substring(0, fileFullPath.lastIndexOf(File.separatorChar));
    			
    		}
    	}
    	return fullParentPath;
    }

	protected static StringTemplate TEMPLATE_FOR_UNIT_TEST;
	/*static {
		InputStream is = ModelUtils.class.getClassLoader().getResourceAsStream("beunittest.st");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[2048];
		try {
			for (int i = is.read(buf); i != -1; i = is.read(buf)) {
				baos.write(buf, 0, i);
			}

		} catch (IOException e) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			} finally {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
		}
		TEMPLATE_FOR_UNIT_TEST = new StringTemplate(new String(baos.toByteArray()));
	}*/
	
    public static String getUnitTestSource(Map<String, String> attributes, boolean testRuleOrder, boolean testEventFired, boolean testConceptModification, boolean testWM, boolean testRuleExecution) {
    	String template = getSourceTemplate("beunittest.st");
    	
    	TEMPLATE_FOR_UNIT_TEST = new StringTemplate(new String(template));
    	
    	
    	TEMPLATE_FOR_UNIT_TEST.reset();
    	TEMPLATE_FOR_UNIT_TEST.setAttributes(attributes);
    	if (testRuleOrder) {
    		TEMPLATE_FOR_UNIT_TEST.setAttribute("testRuleOrder", 1);
    	}
    	if (testEventFired) {
    		TEMPLATE_FOR_UNIT_TEST.setAttribute("testEventFired", 1);
    	}
    	if (testConceptModification) {
    		TEMPLATE_FOR_UNIT_TEST.setAttribute("testConceptModification", 1);
    	}
    	if (testRuleExecution) {
    		TEMPLATE_FOR_UNIT_TEST.setAttribute("testRuleExecution", 1);
    	}
    	if (testWM) {
    		TEMPLATE_FOR_UNIT_TEST.setAttribute("testWorkingMemory", 1);
    	}
    	
    	return TEMPLATE_FOR_UNIT_TEST.toString();
    }

    /**
     * @param javares
     * @param javaSrcFolderName
     * @param customfunction
     * @param javaTask
     * @return
     */
    public static String getJavaResourceAsSource(JavaResource javares,
    		                                     String javaSrcFolderName,
    		                                     boolean customfunction, 
    		                                     boolean javaTask) {
    	
    	String template = "";
    	if (javaTask) {
    		template = getSourceTemplate("javatask.template");
    	} else if (customfunction) {
    		template = getSourceTemplate("customfunction.template");
    	}
    	String packageName = javares.getFolder();
    	if (packageName.startsWith("/")) {
    		packageName = packageName.replaceFirst("/", "");
    	}
    	if (packageName.endsWith("/")) {
    		packageName = packageName.substring(0, packageName.lastIndexOf("/"));
    	}
    	packageName = packageName.replaceAll("/", ".");
    	if (packageName!= null && !packageName.equals(javaSrcFolderName)) {
    		packageName = packageName.substring(javaSrcFolderName.length()+1);
    	} else {
    		packageName = "";
    	}
    	String newJavaResContent  = template;
    	if (packageName != null && packageName.isEmpty()) {
    		 newJavaResContent = newJavaResContent.replace("%packageKeyWord%", "");
    		 newJavaResContent = newJavaResContent.replace("%package_name%", "\n");
    	} else {
    		newJavaResContent = newJavaResContent.replace("%packageKeyWord%", "package ");
    		newJavaResContent = newJavaResContent.replace("%package_name%", packageName+";");
    	}
    	newJavaResContent = newJavaResContent.replace("%class_name% ", javares.getName());
    	
    	return newJavaResContent;
    }


	public static String getRuleFunctionAsSource(RuleFunction convertedRuleFn) {
		String template = null;
		if (convertedRuleFn.isVirtual()) {
			template = getSourceTemplate("virtualrulefunction.template");
		} else {
			template = getSourceTemplate("rulefunction.template");
		}
		if (template.trim().equals("")) {
			return ("");
		}
		String newRuleContents = template.replace("%RULE_NAME%", convertPath(convertedRuleFn.getFolder()) + convertedRuleFn.getName());
//		EMap<String,Symbol> symbolMap = convertedRuleFn.getSymbols().getSymbolMap();
//		int size = symbolMap.size();
		StringBuilder declarations = new StringBuilder();
		for (Symbol symbol: convertedRuleFn.getSymbols().getSymbolList()) {
//			Symbol symbol = symbolMap.get(i).getValue();
			String type = convertPath(symbol.getType());
			declarations.append(type);
			if (symbol.isArray()) {
				declarations.append("[]");
			}
			declarations.append("\t");
			declarations.append(symbol.getIdName());
			declarations.append(";\r\n\t\t");
		}
//		Collection<Symbol> symbols = convertedRuleFn.getSymbols().getSymbolMap().values();
		/**
		 * Added this logic to make the rule function generated in sync with the 
		 * grammar file. This can be revisited later.
		 */
//		for (Symbol symbol : symbols) {
//			String type = convertPath(symbol.getType());
//			declarations.append(type);
//			declarations.append("\t");
//			declarations.append(symbol.getIdName());
//			declarations.append(";\r\n\t\t");
//		}
		//declarations = declarations.trim();
		
		newRuleContents = newRuleContents.replace("%DESCRIPTION%",convertedRuleFn.getDescription()== null?"":
				convertedRuleFn.getDescription().replaceAll("\n", "\n * ").trim());
		if (convertedRuleFn.getReturnType() != null) {
			String retType = convertedRuleFn.getReturnType();
			retType = convertPath(retType);
			newRuleContents = newRuleContents.replace("%RETURN_TYPE%", retType);
		} else {
			newRuleContents = newRuleContents.replace("%RETURN_TYPE%", "void");
		}
		newRuleContents = newRuleContents.replace("%VALIDITY%", convertedRuleFn.getValidity().getLiteral());
		newRuleContents = newRuleContents.replace("%SCOPE%", declarations.toString().trim());
		newRuleContents = newRuleContents.replace("%BODY%", convertedRuleFn.getActionText().replaceAll("\n", "\n\t\t").trim());
		if (convertedRuleFn.getAlias() != null) {
			newRuleContents = newRuleContents.replace("%ALIAS_STATEMENT%", "\r\n\t\talias = \""+convertedRuleFn.getAlias()+"\";");
		} else {
			newRuleContents = newRuleContents.replace("%ALIAS_STATEMENT%", "");
		}
		return newRuleContents;
	}
	
	public static String getRuleTemplateAsSource(RuleTemplate convertedRule) {
		String template = getSourceTemplate("ruletemplate.template");
		String newRuleContents = template.replace("%RULE_NAME%", convertPath(convertedRule.getFolder()) + convertedRule.getName());
		StringBuilder declarations = new StringBuilder();
		for (Symbol symbol: convertedRule.getSymbols().getSymbolList()) {
			String type = convertPath(symbol.getType());
			declarations.append(type);
			declarations.append("\t");
			declarations.append(symbol.getIdName());
			declarations.append(";\r\n\t\t");
		}
		
		if (convertedRule.getActionContext() != null && convertedRule.getActionContext().getActionContextSymbols() != null) {
			Symbols symbols = convertedRule.getActionContext().getActionContextSymbols();
			StringBuilder actionContext = new StringBuilder();
			for (Symbol symbol: symbols.getSymbolList()) {
				if (symbol instanceof ActionContextSymbol) {
					String actionType = ((ActionContextSymbol) symbol).getActionType().getLiteral();
					actionContext.append(actionType);
					actionContext.append(" ");
				}
				String type = convertPath(symbol.getType());
				actionContext.append(type);
				actionContext.append("\t");
				actionContext.append(symbol.getIdName());
				actionContext.append(";\r\n\t\t");
			}
		}
		
		newRuleContents = newRuleContents.replace("%DESCRIPTION%", convertedRule.getDescription()!= null ?
				convertedRule.getDescription().replaceAll("\n", "\n * ").trim():"");
		newRuleContents = newRuleContents.replace("%PRIORITY%", String.valueOf(convertedRule.getPriority()));
		newRuleContents = newRuleContents.replace("%FORWARD_CHAIN%", String.valueOf(convertedRule.isForwardChain()));
		if (convertedRule.getRank() != null) {
			String rank = String.valueOf(ModelUtils.convertPathToPackage(convertedRule.getRank()));
			newRuleContents = newRuleContents.replace("%RANK_FUNCTION%", "\r\n\t\trank = "+rank+";");
		} else {
			newRuleContents = newRuleContents.replace("%RANK_FUNCTION%", "");
		}
		newRuleContents = newRuleContents.replace("%AUTHOR%", String.valueOf(convertedRule.getAuthor()));
		newRuleContents = newRuleContents.replace("%DECLARATIONS%", declarations.toString());
		newRuleContents = newRuleContents.replace("%CONDITIONS%", getBalancedSource(convertedRule.getConditionText()));
		newRuleContents = newRuleContents.replace("%ACTIONS%", getBalancedSource(convertedRule.getActionText()));
		return newRuleContents;
	}

	public static String getRuleAsSource(Rule convertedRule) {
		String template = getSourceTemplate("rule.template");
		String newRuleContents = template.replace("%RULE_NAME%", convertPath(convertedRule.getFolder()) + convertedRule.getName());
//		EList<Symbol> symbols = convertedRule.getSymbols().getSymbolList();
//		int size = symbolMap.size();
		StringBuilder declarations = new StringBuilder();
		for (Symbol symbol: convertedRule.getSymbols().getSymbolList()) {
//			Symbol symbol = symbolMap.get(i).getValue();
			String type = convertPath(symbol.getType());
			declarations.append(type);
			declarations.append("\t");
			declarations.append(symbol.getIdName());
			declarations.append(";\r\n\t\t");
		}

//		Collection<Symbol> symbols = convertedRule.getSymbols().getSymbolMap().values();
//		for (Symbol symbol : symbols) {
//			String type = convertPath(symbol.getType());
//			declarations += type + "\t" + symbol.getIdName() + ";\r\n\t\t";
//		}
		newRuleContents = newRuleContents.replace("%DESCRIPTION%", convertedRule.getDescription()!= null ?
				convertedRule.getDescription().replaceAll("\n", "\n * ").trim():"");
		newRuleContents = newRuleContents.replace("%PRIORITY%", String.valueOf(convertedRule.getPriority()));
		newRuleContents = newRuleContents.replace("%FORWARD_CHAIN%", String.valueOf(convertedRule.isForwardChain()));
		if (convertedRule.getRank() != null) {
			String rank = String.valueOf(ModelUtils.convertPathToPackage(convertedRule.getRank()));
			newRuleContents = newRuleContents.replace("%RANK_FUNCTION%", "\r\n\t\trank = "+rank+";");
		} else {
			newRuleContents = newRuleContents.replace("%RANK_FUNCTION%", "");
		}
		newRuleContents = newRuleContents.replace("%AUTHOR%", String.valueOf(convertedRule.getAuthor()));
		newRuleContents = newRuleContents.replace("%DECLARATIONS%", declarations.toString());
		newRuleContents = newRuleContents.replace("%CONDITIONS%", getBalancedSource(convertedRule.getConditionText()));
		newRuleContents = newRuleContents.replace("%ACTIONS%", getBalancedSource(convertedRule.getActionText()));
//		newRuleContents = newRuleContents.replace("%CONDITIONS%", convertedRule.getConditionText().replaceAll("\n", "\n\t\t").trim());
//		newRuleContents = newRuleContents.replace("%ACTIONS%", convertedRule.getActionText().replaceAll("\n", "\n\t\t").trim());
		return newRuleContents;
	}

	/**
	 * Somewhat of a hack to balance multi-line comments in the source if they are unclosed.
	 * Otherwise, code generation for state machines will fail, as the unclosed comment will
	 * consume the "State Machine source" when it is converted to the SM source code style syntax.
	 * 
	 * @param actionText
	 * @return
	 */
	protected static CharSequence getBalancedSource(String source) {
		if (source == null || source.length() == 0) {
			return source;
		}
		boolean unclosed = false;
		boolean inString = false;
		byte[] bytes;
		try {
			bytes = source.getBytes(ModelUtils.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			bytes = source.getBytes();
		}
		for (int i=bytes.length-1; i>=0; i--) {
			if (bytes[i] == '/' && i > 0) {
				if (bytes[i-1] == '*') {
					if (!inString) {
						// found a closing comment tag, break
						break;
					}
				}
			}
			if (bytes[i] == '*' && i > 0) {
				if (bytes[i-1] == '/') {
					if (i > 1 && bytes[i-2] == '/') {
						// this was buried in a single line comment, continue
						continue;
					}
					if (!inString) {
						// found an unclosed comment tag, mark as unclosed and break
						unclosed = true;
						break;
					}
				}
			}
			if (bytes[i] == '"' && i > 0) {
				if (bytes[i-1] == '\\') {
					continue;
				}
				inString = !inString;
			}
		}
		if (unclosed) {
			source += "*/"; // append a closing comment tag
		}
		source = source.replaceAll("\n", "\n\t\t").trim();
		return source;
	}


	protected static void writeFile(String newRuleContents, File ruleFile) throws Exception{
			if (!ruleFile.exists()) {
				if (!ruleFile.getParentFile().exists()) {
					ruleFile.getParentFile().mkdirs();
				}
				if (!ruleFile.createNewFile()) {
					return;
				}
			}
        final FileOutputStream fos = new FileOutputStream(ruleFile);
        try {
			fos.write(newRuleContents.getBytes(ModelUtils.DEFAULT_ENCODING));
			fos.flush();
		} catch (FileNotFoundException e) {
			   throw e;
		} catch (IOException e) {
				throw e;
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
				throw e;
			}
		}
	}

	protected static String convertPath(String path) {
		if (path == null) {
			return "";
		}
		if (path.length() > 0 && path.charAt(0) == '/') {
			path = path.substring(1);
		}
		path = path.replaceAll("/", ".");

		return path;
	}

	protected static String getSourceTemplate(String fileName) {
		if (fTemplates.get(fileName) != null) {
			return fTemplates.get(fileName);
		}
		/*
		Bundle bundle = Platform.getBundle(StudioCorePlugin.PLUGIN_ID);
		URL templateEntry = bundle.getEntry(fileName);
		*/
		InputStream stream = ModelUtils.class.getClassLoader().getResourceAsStream(fileName);
		if (stream == null) {
			System.err.println("Template not found");
			return "";
		}
		String templateContents = null;
		try {
			//stream = templateEntry.openStream();
			byte[] arr = new byte[stream.available()];
			stream.read(arr);
			templateContents = new String(arr);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		fTemplates.put(fileName, templateContents);
		return templateContents;
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
		eobject = EcoreUtil.copy(eobject);
		Resource oldResource = eobject.eResource();
		XMIResource resource = new XMIResourceImpl();
		OutputStream outputStream = new ByteArrayOutputStream();
		BufferedOutputStream bos = 
			new BufferedOutputStream(outputStream); 
		resource.getContents().add(eobject);
		resource.save(bos, ModelUtils.getPersistenceOptions());
		if (oldResource != null) {
			oldResource.getContents().add(eobject);
		}
		return ((ByteArrayOutputStream) outputStream).toByteArray();
	}
	
	public static boolean isEMFType(File file) {
		String fileName = file.getAbsolutePath();
		String extension = fileName.substring(fileName.lastIndexOf('.') + 1, fileName.length());
		
		if (isRuleType(file, extension)) {
			return false;
		}
		if (isEntityType(file, extension) 
//				|| isArchiveType(file, extension)
				|| isImplementationType(file, extension)) {
			return true;
		}
		return false;
	}

	public static boolean isEntityType(File file, String fileExt) {
		if (isRuleType(file, fileExt)) {
			return false;
		}
		if (isArchiveType(file, fileExt)) {
			return false;
		}
		if (isImplementationType(file, fileExt)) {
			return false;
		}
		

		// we know it is not a rule, an impl, or an archive, if
		// it exists in the extensions map, it must be
		// an entity type
		if (CommonIndexUtils.fileExtToElementType.containsKey(fileExt)) {
			return true;
		}
		return false;
	}
	
	public static boolean isEntityType(ELEMENT_TYPES type) {
		if (isRuleType(type)) {
			return false;
		}
		if (CommonIndexUtils.isArchiveType(type)) {
			return false;
		}
		return true;
	}
	
	public static boolean isArchiveType(File file, String fileExt) {
		if (CommonIndexUtils.EAR_EXTENSION.equalsIgnoreCase(fileExt)) {
			return true;
		}
		return false;
	}

	public static boolean isImplementationType(File file, String fileExt) {
		if (CommonIndexUtils.RULE_FN_IMPL_EXTENSION.equalsIgnoreCase(fileExt)) {
			return true;
		}
		return false;
	}
	
	public static boolean isImplementationType(ELEMENT_TYPES type) {
		return type == ELEMENT_TYPES.DECISION_TABLE;
	}
	
    public static boolean isRuleType(File file, String fileExt) {
		if (CommonIndexUtils.RULE_EXTENSION.equalsIgnoreCase(fileExt)) {
			return true;
		}
		if (CommonIndexUtils.RULEFUNCTION_EXTENSION.equalsIgnoreCase(fileExt)) {
			return true;
		}
		return false;
	}

	public static boolean isRuleType(ELEMENT_TYPES type) {
		return (type == ELEMENT_TYPES.RULE || type == ELEMENT_TYPES.RULE_FUNCTION);
	}

    public static String ruleFnClassFSName(RuleFunction fn) {
        return ModelNameUtil.modelPathToExternalForm(fn.getFolder()) + ModelNameUtil.RULE_SEPARATOR_CHAR + ruleFnClassShortName(fn);
    }

    //Designer limits resource names to 200 unicode characters or the filesystem limit if it's less.
    //Since ext2 has a max filename size of 255 bytes it's possible that the resource name + ".java"
    //is exactly 255 bytes and there won't be enough extra space for this suffix.
    public static String ruleFnClassShortName(RuleFunction fn) {
        String name = ruleFnClassShortNameWithArgTypes(fn);
        if(isNameOversize(name)) {
            name = "";
            String retType = fn.getReturnType();
            //do this to avoid having to deal with the difference between void and non-void return types
            //and to avoid boxing and unboxing non-object return values
            if(retType == null) {
                name += null;
            } else {
                RDFUberType rdfType = RDFTypes.getType(retType);
                if(rdfType != null && (RDFTypes.BOOLEAN == rdfType || RDFTypes.DOUBLE == rdfType || RDFTypes.INTEGER == rdfType || RDFTypes.LONG == rdfType)) {
                    name += rdfType.toString();
                }
            }
            name += fn.getName();
            name = ModelNameUtil.escapeIdentifier(name) +  OVERSIZE_FN_CLASS_NAME_SUFFIX;
        }

        return name;
    }

    protected static boolean isNameOversize(String name) {
        //this is a best effort to see if the genereated class filename is too long for the filesystem.
        //other things could cause an IOException that are not related to the size of the filename
        //85 is 255 / 3 where 255 bytes is the filename length limit on most file systems (excepting NTFS)
        //and 3 bytes per character is a reasonable worst case under UTF-8 (which is not guaranteed
        //to be in use, and UTF could potentially have more than 3 bytes per character, but this is a best effort).
        return name.length() > 85;
    }

    protected static String ruleFnClassShortNameWithArgTypes(RuleFunction fn) {
        StringBuilder sb = new StringBuilder();
        sb.append(fn.getReturnType());
        sb.append(fn.getName());
//        Collection<Symbol> args = fn.getSymbols().getSymbolMap().values();
        for(Symbol symbol: fn.getSymbols().getSymbolList()) {
//            final Symbol symbol = (Symbol) it.next();
            String type = symbol.getType();
            if(type == null) type = void.class.getName();
            sb.append(type);
        }
        //$ suffix is so that a user can't create a resource whose class name overlaps w/ a rule function class
        String name = ModelNameUtil.escapeIdentifier(sb.toString()) + "$";
        
        return name;
    }
    
    /**
     * Put decision table related utility methods here
     * @author aathalye
     *
     */
    public static final class DecisionTableUtils {
    	
    	public static TableRuleInfo getTableRuleInfoFromId(String ruleId, Table tableEModel) {
    		TableRuleSet dtSet = tableEModel.getDecisionTable();
    		TableRuleSet etSet = tableEModel.getExceptionTable();
    		TableRuleInfo tableRuleInfo = getTableRuleInfoFromId(ruleId, dtSet);
    		if (tableRuleInfo == null) {
    			tableRuleInfo = getTableRuleInfoFromId(ruleId, etSet);
    			if (tableRuleInfo != null) {
    				tableRuleInfo.setTableTypes(TableTypes.EXCEPTION_TABLE);
    			}
    		} else {
    			tableRuleInfo.setTableTypes(TableTypes.DECISION_TABLE);
    		}
    		return tableRuleInfo;
    	}
    	
    	
    	
    	public static String getContainingRuleId(String trvId) {
    		String ruleId = null;
    		ruleId = trvId.substring(0, trvId.indexOf('_'));
    		return ruleId;
    	}
    	
    	public static class TableRuleInfo {
    		TableRuleSet trs;
    		
    		TableRule tableRule;
    		
    		TableTypes tableTypes;

    		/**
    		 * @param tableType
    		 * @param tableRule
    		 */
    		public TableRuleInfo(TableRuleSet trs, TableRule tableRule) {
    			super();
    			this.trs = trs;
    			this.tableRule = tableRule;
    		}

    		/**
    		 * @return the tableType
    		 */
    		public final TableRuleSet getTrs() {
    			return trs;
    		}

    		/**
    		 * @return the tableRule
    		 */
    		public final TableRule getTableRule() {
    			return tableRule;
    		}

			/**
			 * @return the tableTypes
			 */
			public final TableTypes getTableTypes() {
				return tableTypes;
			}

			/**
			 * @param tableTypes the tableTypes to set
			 */
			public final void setTableTypes(TableTypes tableTypes) {
				this.tableTypes = tableTypes;
			}
     	}
    	
    	public static TableRuleInfo getTableRuleInfoFromId(String ruleId, TableRuleSet trs) {
    		for (TableRule tr : trs.getRule()) {
    			if (tr.getId().equals(ruleId)) {
    				return new TableRuleInfo(trs, tr);
    			}
    		}
    		return null;
    	}
    }

	public static RuleFunction generateDefaultTimeoutExpression(State state) {
		RuleFunction timeoutExpression = RuleFactory.eINSTANCE.createRuleFunction();
		String body = "return " + state.getTimeout() + ";"; 
		timeoutExpression.setActionText(body);
		timeoutExpression.setValidity(Validity.CONDITION);
		timeoutExpression.setReturnType(RDFTypes.LONG.getName());
		return timeoutExpression;
	}
	
	public static Compilable findCompilable(State composite,
			String ruleName) {
		if (composite == null || ruleName == null) {
			return null;
		}
		if (composite.getEntryAction() != null && ruleName.equals(composite.getEntryAction().getName())) {
			return composite.getEntryAction();
		}
		if (composite.getExitAction() != null && ruleName.equals(composite.getExitAction().getName())) {
			return composite.getExitAction();
		}
		if (composite.getInternalTransitionRule() != null && ruleName.equals(composite.getInternalTransitionRule().getName())) {
			return composite.getInternalTransitionRule();
		}
		if (composite.getTimeoutAction() != null && ruleName.equals(composite.getTimeoutAction().getName())) {
			return composite.getTimeoutAction();
		}
		if (composite.getTimeoutExpression() != null && ruleName.equals(composite.getTimeoutExpression().getName())) {
			return composite.getTimeoutExpression();
		}
		if (composite instanceof StateTransition) {
			Rule guardRule = ((StateTransition) composite).getGuardRule();
			if (guardRule != null && ruleName.equals(guardRule.getName())) {
				return guardRule;
			}
		}
		if (composite instanceof InternalStateTransition) {
			Rule transRule = ((InternalStateTransition) composite).getRule();
			if (transRule != null && ruleName.equals(transRule.getName())) {
				return transRule;
			}
		}
		
		if (composite instanceof StateMachine) {
			EList<StateTransition> stateTransitions = ((StateMachine) composite).getStateTransitions();
			for (StateTransition stateTransition : stateTransitions) {
				Rule guardRule = ((StateTransition) stateTransition).getGuardRule();
				if (guardRule != null && ruleName.equals(guardRule.getName())) {
					return guardRule;
				}
			}
		}

		if (composite instanceof StateComposite) {
			EList<StateEntity> stateEntities = ((StateComposite) composite).getStateEntities();
			for (StateEntity stateEntity : stateEntities) {
				if (stateEntity instanceof State) {
					Compilable c = findCompilable((State) stateEntity, ruleName);
					if (c != null) {
						return c;
					}
				} else {
					System.out.println("something else");
				}
			}
		}
		return null;
	}
	
	public static boolean matchNsEntry(NamespaceEntry nsEntry, String fullEntityPath) {
		if (nsEntry == null) {
			return false;
		}
		String namespace = nsEntry.getNamespace();
		if (namespace.startsWith(EMFRDFTnsFlavor.BE_NAMESPACE)) {
			String entityName = namespace.substring(EMFRDFTnsFlavor.BE_NAMESPACE.length());
			if (fullEntityPath.equals(entityName)) {
				return true;
			}
		}
		return false;
	}

	public static NamespaceEntry getNamespaceEntry(EList<NamespaceEntry> namespaceEntries, String pfx) {
		for (int i = 0; i < namespaceEntries.size(); i++) {
			NamespaceEntry namespaceEntry = namespaceEntries.get(i);
			if (pfx.equals(namespaceEntry.getPrefix())) {
				return namespaceEntry;
			}
		}
		return null;
	}

	public static ImportRegistryEntry getImportRegistryEntry(EList<ImportRegistryEntry> importEntries, String namespace) {
		for (int i = 0; i < importEntries.size(); i++) {
			ImportRegistryEntry namespaceEntry = importEntries.get(i);
			if (namespace.equals(namespaceEntry.getNamespace())) {
				return namespaceEntry;
			}
		}
		return null;
	}
	
	/**
	 * Save {@link Domain} to file system.
	 * @param domain
	 * @param PathToFolder -> Absolute path in which to save the domain model.
	 */
	public static void saveDomain(Domain domain, 
			                      String pathToFolder) throws Exception {
		//Conjoin the 2
		String pathToSave = 
			new StringBuilder(pathToFolder).
			append(domain.getName()).
			append(".domain").toString();
		//Convert to URI
		URI fileURI = URI.createFileURI(pathToSave);
		//Create empty resourceset
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(fileURI);
		//Add the emodel
		resource.getContents().add(domain);
		saveEObject(domain);
	}
	
	public static SimpleDateFormat SIMPLE_DATE_FORMAT() {
		return new SimpleDateFormat(DATE_TIME_PATTERN);
	}

	/**
	 * @param model
	 * @return
	 */
	public static String javaFnClassFSName(JavaSource model) {
		StringBuilder sb = new StringBuilder();
		sb.append(model.getPackageName());
		if(!model.getPackageName().endsWith(".")){
			sb.append(".");
		}
		sb.append(model.getName());
		return sb.toString();
	}
	
	public static List<Concept> collectConceptAncestors(Concept entity, boolean includeSuperConcepts) {
		String projectName = entity.getOwnerProjectName();
		List<Concept> ancestorConcepts = new ArrayList<Concept>();
		List<DesignerElement> allEvents = CommonIndexUtils.getAllElements(projectName, 
				new ELEMENT_TYPES[] { ELEMENT_TYPES.CONCEPT, ELEMENT_TYPES.SCORECARD });
		if (includeSuperConcepts) {
			Concept superConcept = entity.getSuperConcept();
			while (superConcept != null) {
				if (!ancestorConcepts.contains(superConcept)) {
					ancestorConcepts.add(superConcept);
				}
				superConcept = superConcept.getSuperConcept();
			}
		}
		for (DesignerElement designerElement : allEvents) {
			Concept concept = (Concept) ((EntityElement)designerElement).getEntity();
			if (isSubConcept(entity, concept)) {
				if (!ancestorConcepts.contains(concept)) {
					ancestorConcepts.add(concept);
				}
			}
		}
		return ancestorConcepts;
	}

	public static boolean isSubConcept(Concept parentConcept, Concept concept) {
		Concept superConcept = concept.getSuperConcept();
		while (superConcept != null) {
			if (parentConcept.equals(superConcept)) {
				return true;
			}
			superConcept = superConcept.getSuperConcept();
		}
		return false;
	}

	public static List<? extends Entity> collectAncestorEntities(Entity entity, boolean includeSuperEntities) {
		if (entity instanceof Concept) {
			return collectConceptAncestors((Concept)entity, includeSuperEntities);
		}
		if (entity instanceof Event) {
			return collectEventAncestors((Event)entity, includeSuperEntities);
		}
		return null;
	}

	public static List<Event> collectEventAncestors(Event entity, boolean includeSuperEvents) {
		String projectName = entity.getOwnerProjectName();
		List<Event> ancestorEvents = new ArrayList<Event>();
		List<DesignerElement> allEvents = CommonIndexUtils.getAllElements(projectName, 
				new ELEMENT_TYPES[] { ELEMENT_TYPES.SIMPLE_EVENT, ELEMENT_TYPES.TIME_EVENT });
		if (includeSuperEvents) {
			Event superEvent = entity.getSuperEvent();
			while (superEvent != null) {
				if (!ancestorEvents.contains(superEvent)) {
					ancestorEvents.add(superEvent);
				}
				superEvent = superEvent.getSuperEvent();
			}
		}
		for (DesignerElement designerElement : allEvents) {
			Event event = (Event) ((EntityElement)designerElement).getEntity();
			if (isSubEvent(entity, event)) {
				if (!ancestorEvents.contains(event)) {
					ancestorEvents.add(event);
				}
			}
		}
		return ancestorEvents;
	}

	public static boolean isSubEvent(Event parentEvent, Event event) {
		Event superEvent = event.getSuperEvent();
		while (superEvent != null) {
			if (parentEvent.equals(superEvent)) {
				return true;
			}
			superEvent = superEvent.getSuperEvent();
		}
		return false;
	}

}

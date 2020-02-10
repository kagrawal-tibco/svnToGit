package com.tibco.cep.repo.provider.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.InputSource;

import com.tibco.be.util.XiSupport;
import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.Constants.DD;
import com.tibco.be.util.packaging.Constants.RepoTypes;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.provider.GlobalVariablesProvider;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.io.xml.XMLStringUtilities;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmFactory;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.build.MutableSupport;
import com.tibco.xml.schema.build.MutableType;
import com.tibco.xml.schema.flavor.XSDL;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 7, 2004
 * Time: 3:48:24 PM
 * To change this template use Options | File Templates.
 */
public class GlobalVariablesProviderImpl implements GlobalVariablesProvider {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(GlobalVariablesProvider.class);
	
    protected static final Pattern GLOBAL_VARIABLE_PATTERN = Pattern.compile("%%(.*?)%%");
    protected static final String PRE_APPENDREPLACEMENT_PATTERN_STR = "([\\\\\\$])";
    protected static final String PRE_APPENDREPLACEMENT_REPLACE = "\\\\$1";

    protected static final XiParser PARSER = XiParserFactory.newInstance();

    protected volatile XiNode globalVarsNode;

//    protected XiFactory factory = XiFactoryFactory.newInstance();
    public LinkedHashMap<String, Object> globalVariables = new LinkedHashMap<String, Object>();
    protected LinkedHashMap<String, String> packagedComponents = new LinkedHashMap<String, String>();
    public Map<String, GlobalVariableDescriptor> nameToFullGV = new LinkedHashMap<String, GlobalVariableDescriptor>();
    public List<String> gvNames=new ArrayList<String>();
	protected String projectName;
    protected String projectVersion;
    protected String projectOwner;


    public GlobalVariablesProviderImpl() {
    }


    private String GetGvPath(String fullUriToSubstVarFile, Project project) {
        final String rootGvUri = "defaultVars/";
        if (!fullUriToSubstVarFile.startsWith(rootGvUri)) {
        	return null;
        }
        final int filenameIndex = fullUriToSubstVarFile.length() - "defaultVars.substvar".length();
        return fullUriToSubstVarFile.substring(rootGvUri.length() - 1, filenameIndex);
    }


    public int getVariableAsInt(String varName, int defaultValue) {
        Object obj = globalVariables.get(varName);
        if (obj == null) {
            return defaultValue;
        } else {
            if (obj instanceof Integer) return ((Integer) obj).intValue();
            else {
                try {
                    return Integer.parseInt(obj.toString());
                }
                catch (Exception e) {
                    return defaultValue;
                }
            }
        }
    }


    public long getVariableAsLong(String varName, long defaultValue) {
        Object obj = globalVariables.get(varName);
        if (obj == null) {
            return defaultValue;
        } else {
            if (obj instanceof Long) return ((Long) obj).longValue();
            else {
                try {
                    return Long.parseLong(obj.toString());
                }
                catch (Exception e) {
                    return defaultValue;
                }
            }
        }
    }


    public double getVariableAsDouble(String varName, double defaultValue) {
        Object obj = globalVariables.get(varName);
        if (obj == null) {
            return defaultValue;
        } else {
            if (obj instanceof Double) return ((Double) obj).doubleValue();
            else {
                try {
                    return Double.parseDouble(obj.toString());
                }
                catch (Exception e) {
                    return defaultValue;
                }
            }
        }
    }


    public String getVariableAsString(String varName, String defaultValue) {
        Object obj = globalVariables.get(varName);
        if (obj == null) {
            return defaultValue;
        } else {
            return obj.toString();
        }
    }


    public boolean getVariableAsBoolean(String varName, boolean defaultValue) {
        Object obj = globalVariables.get(varName);
        if (obj == null) {
            return defaultValue;
        } else {
            if (obj instanceof Boolean) return ((Boolean) obj).booleanValue();
            else {
                try {
                    final String stringValue = obj.toString();
                    return "1".equals(stringValue) || Boolean.valueOf(obj.toString());
                }
                catch (Exception e) {
                    return defaultValue;
                }
            }
        }
    }


    public XiNode toXiNode() throws Exception {
        if (globalVarsNode == null) {
            initGlobalVarsNode(globalVariables);
        }
        return globalVarsNode;
    }//toXiNode


    synchronized protected void initGlobalVarsNode(LinkedHashMap _globalVariables) {
        if (globalVarsNode != null) return;
        XiNode gvNode = XiSupport.getXiFactory().createElement(ExpandedName.makeName(NAME));
        LinkedHashMap vars = new LinkedHashMap();
        for (Map.Entry<String, String> e : (Set<Map.Entry>) _globalVariables.entrySet()) {
            final String name = e.getKey();
            final String value = e.getValue();
            final String[] path = name.split("/");
            XiNode node = gvNode;
            StringBuffer pathstr = new StringBuffer();
            for (int ii = 0; ii < path.length; ii++) {
                pathstr.append(path[ii]);
                //this ensures that folder's keys in the vars map always end in /
                if (ii < path.length - 1) pathstr.append("/");
                XiNode temp = (XiNode) vars.get(pathstr.toString());
                if (ii == path.length - 1) {
                    if (temp == null) {
                        node = node.appendElement(ExpandedName.makeName(path[ii]));
                        node.appendText(value);
                        vars.put(pathstr.toString(), node);
                    } else {
                        temp.appendText(value);
                    }
                } else {
                    if (temp == null) {
                        node = node.appendElement(ExpandedName.makeName(path[ii]));
                        vars.put(pathstr.toString(), node);
                    } else {
                        node = temp;
                    }
                }
            }
        }//for
        globalVarsNode = gvNode;
    }


    /**
     * Substitutes all known Global Variables with their values in a CharSequence.
     *
     * @param text a CharSequence (i.e. a String, a StringBuffer, etc) that may contain Global Variables.
     * @return a CharSequence equivalent to substituting all global variables with their values in the text parameter,
     *         or "" if the parameter was null.
     */
    public CharSequence substituteVariables(CharSequence text) {
        if (null == text) {
            return "";
        }//if

        final Matcher matcher = GLOBAL_VARIABLE_PATTERN.matcher(text);
        final StringBuffer substitutedText = new StringBuffer();
        while (matcher.find()) {
            final String variableValue = this.getVariableAsString(matcher.group(1), matcher.group(0))
                    .replaceAll(PRE_APPENDREPLACEMENT_PATTERN_STR, PRE_APPENDREPLACEMENT_REPLACE);
            matcher.appendReplacement(substitutedText, variableValue);
        }//while
        matcher.appendTail(substitutedText);

        return substitutedText;
    }//substituteVariables


    public void buildGlobalVariablesUsingEARFile(String path, XiNode root) throws Exception {

        for (Iterator itr = XiChild.getIterator(root, DD.XNames.NAME_VALUE_PAIRS); itr.hasNext();) {
            final XiNode node = (XiNode) itr.next();
            final String name = XiChild.getChild(node, DD.XNames.NAME).getStringValue();
            if ("Global Variables".equalsIgnoreCase(name)) {
                collectGlobalVariables(node, path, DD.XNames.NAME_VALUE_PAIR);
                collectGlobalVariables(node, path, DD.XNames.NAME_VALUE_PAIR_INTEGER);
                collectGlobalVariables(node, path, DD.XNames.NAME_VALUE_PAIR_BOOLEAN);
                collectGlobalVariables(node, path, DD.XNames.NAME_VALUE_PAIR_PASSWORD);
            }
        }
    }


    public void buildGlobalVariablesUsingRemoteRepository(String path, XiNode root, String projectName) throws Exception {
        final XiNode globalVariables = XiChild.getChild(root, RepoTypes.XNames.GLOBAL_VARIABLES);
        if (null == globalVariables) {
            return;
        }
        if (path.length() > 0 && path.charAt(0) == '/') {
            path = path.substring(1);
        }
        for (Iterator it = XiChild.getIterator(globalVariables, Constants.RepoTypes.XNames.GLOBAL_VARIABLE); it.hasNext();) {
            final XiNode gvNode = (XiNode) it.next();
            String name = XiChild.getString(gvNode, RepoTypes.XNames.NAME);
            final String type = XiChild.getString(gvNode, RepoTypes.XNames.TYPE);
            final String value = XiChild.getString(gvNode, RepoTypes.XNames.VALUE);
            final boolean deploymentSettable = XiChild.getBoolean(gvNode, RepoTypes.XNames.DEPLOYMENT_SETTABLE, true);
            final boolean serviceSettable = XiChild.getBoolean(gvNode, RepoTypes.XNames.SERVICE_SETTABLE, true);
            final long modTime = XiChild.getLong(gvNode, RepoTypes.XNames.MOD_TIME, 0);
            final String description = ""; //TODO NP find the node name and get the data
            final String constraint = XiChild.getString(gvNode, RepoTypes.XNames.CONSTRAINT);

            final GlobalVariableDescriptor gv = new GlobalVariableDescriptor(name, path, value, type,
                    deploymentSettable, serviceSettable, modTime, description, constraint,projectName);
            String key = gv.getFullName();
            gvNames.add(key);
            if(this.nameToFullGV.containsKey(key)) {
            	gv.setOverridden(true);
            }
            this.globalVariables.put(key, value);
            this.nameToFullGV.put(key, gv);
        }
    }
    
	public void validateGlobalVariables() {
		nameToFullGV.forEach((key, gv) -> validateGlobalVariable(gv));
	}

	private void validateGlobalVariable(GlobalVariableDescriptor gv) {
		try {
			if (gv != null && gv.getConstraint() != null && !gv.getConstraint().equals("")) {
				if (gv.getType().equalsIgnoreCase("String")) {
					List<String> possibleValues = Arrays.asList(gv.getConstraint().replaceAll(" ", "").split(","));
					if (possibleValues != null && !possibleValues.contains(gv.getValueAsString())) {
						LOGGER.log(Level.WARN, "Global Variable '%s' constraint condition not met.", gv.getFullName());
					}
				}
				if (gv.getType().equalsIgnoreCase("Integer")) {
					int start = Integer.parseInt(gv.getConstraint().split("-")[0].replaceAll(" ", ""));
					int end = Integer.parseInt(gv.getConstraint().split("-")[1].replaceAll(" ", ""));
					int value = Integer.parseInt(gv.getValueAsString());
					if (value < start || value > end) {
						LOGGER.log(Level.WARN, "Global Variable '%s' constraint condition not met.", gv.getFullName());
					}
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, "Global Variable '%s' invalid constraint condition.", gv.getFullName());
		}
	}


    private void collectGlobalVariables(XiNode globalVariables, String path, ExpandedName en) {
        this.globalVarsNode = null;

        for (Iterator it = XiChild.getIterator(globalVariables, en); it.hasNext();) {
            final XiNode gvNode = (XiNode) it.next();
            String name = XiChild.getString(gvNode, DD.XNames.NAME);
            String type = XiChild.getString(gvNode, DD.XNames.TYPE);
            /**
             * Modified By rmishra to populate type of Global Variable on 13/03/09
             */
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
            final String description = ""; //TODO NP find the node name and get the data
            final String constraint = "";

            int idx = name.lastIndexOf("/");
            String actualPath = "";
            if (idx != -1) {
            	actualPath = name.substring(0, idx+1);
            	name = name.substring(idx+1);
            }
            final GlobalVariableDescriptor gv = new GlobalVariableDescriptor(name, actualPath, value, type,
                    deploymentSettable, serviceSettable, modTime, description, constraint);

            String key = gv.getFullName();
            if(this.nameToFullGV.containsKey(key)) {
            	gv.setOverridden(true);
            }
            this.globalVariables.put(key, value);
            this.nameToFullGV.put(key, gv);
        }
    }


    public void overwriteGlobalVariables(Properties props) {
        this.globalVarsNode = null;

        for (Map.Entry<Object, Object> objectObjectEntry : props.entrySet()) {
            Map.Entry e = (Map.Entry) objectObjectEntry;
            String key = (String) e.getKey();
            GlobalVariableDescriptor existValue = null;
            if (key.startsWith("tibco.clientVar.")) {
                String value = (String) e.getValue();
                String name = key.substring("tibco.clientVar.".length());
                existValue = (GlobalVariableDescriptor) nameToFullGV.get(name);
                if (existValue == null) {
                    this.globalVariables.put(name, value);
                    this.nameToFullGV.put(name, new GlobalVariableDescriptor(name, "", value, "String", true, true,
                            System.currentTimeMillis(), "", ""));
                } else {
                    this.globalVariables.put(name, value);
                    existValue.overwriteValue(value);
                }
            } else if (key.equals(SystemProperty.ENGINE_NAME.getPropertyName())) {
                String value = (String) e.getValue();
                existValue = (GlobalVariableDescriptor) nameToFullGV.get(key);
                if (existValue == null) {
                    this.globalVariables.put(key, value);
                    this.nameToFullGV.put(key, new GlobalVariableDescriptor(key, "", value, "String", true, true,
                            System.currentTimeMillis(), "", ""));
                } else {
                    this.globalVariables.put(key, value);
                    existValue.overwriteValue(value);
                }
            }
        }
    }


    public GlobalVariableDescriptor getVariable(String name) {
        return (GlobalVariableDescriptor) nameToFullGV.get(name);
    }


    public Collection<GlobalVariableDescriptor> getVariables() {
        return nameToFullGV.values();
    }


    public void setVariables(Map<String, GlobalVariableDescriptor> vars) {
        this.nameToFullGV = vars;
        final LinkedHashMap<String, Object> p = new LinkedHashMap<String, Object>();
        for (Map.Entry<String, GlobalVariableDescriptor> entry : vars.entrySet()) {
            p.put(entry.getKey(), entry.getValue().getValueAsString());
        }
        this.globalVariables = p;
    }


    public int deserializeResource(String uri, InputStream is, Project project, VFileStream stream) throws Exception {
        if (uri.endsWith("TIBCO.xml")) {
            final XiNode doc = PARSER.parse(new InputSource(is));
            final XiNode node = doc.getFirstChild();
            this.buildNameVersionOwner(uri, node);
            this.buildComponentVersion(node);
            buildGlobalVariablesUsingEARFile(uri, node);
            return CONTINUE;

        } else if (uri.endsWith("defaultVars.substvar")) {
        	String gvPath = this.GetGvPath(stream.getURI(), project);
        	if (gvPath == null) {
        		// this file is not located in the expected defaultVars/ folder, skip it
        		return STOP;
        	}
            final XiNode doc = PARSER.parse(new InputSource(is));
            final XiNode node = doc.getFirstChild();
            buildGlobalVariablesUsingRemoteRepository(gvPath, node,project.getName());
        }

        return STOP;

    }


    private void buildComponentVersion(XiNode node) {
        final XiNode startAsOneOf = XiChild.getChild(node, DD.XNames.START_AS_ONE_OF);
        if (startAsOneOf == null) return;

        final XiNode componentNode = XiChild.getChild(startAsOneOf, DD.XNames.COMPONENT_SOFTWARE_REFERENCE);
        if (componentNode == null) return;

        final String componentSWName = this.safeGetChildNodeStringValue(componentNode, DD.XNames.COMPONENT_SOFTWARE_NAME);
        final String configVersion = this.safeGetChildNodeStringValue(componentNode, DD.XNames.CONFIG_VERSION);

        packagedComponents.put(componentSWName, configVersion); //In a Ear can we have only one packaged engine for a version???
    }


    protected void buildNameVersionOwner(String uri, XiNode root) {
        this.projectName = this.safeGetChildNodeStringValue(root, DD.XNames.NAME);
        if (this.projectVersion == null) {//Read projectVersion only if not already read.
        	this.projectVersion = this.safeGetChildNodeStringValue(root, DD.XNames.VERSION);
        }
        this.projectOwner = this.safeGetChildNodeStringValue(root, DD.XNames.OWNER);
    }


    private String safeGetChildNodeStringValue(XiNode parent, ExpandedName childNodeName) {
        if ((null != parent) && (null != childNodeName)) {
            final XiNode childNode = XiChild.getChild(parent, childNodeName);
            if (null != childNode) {
                return childNode.getStringValue();
            }
        }
        return null;
    }


    public String getProjectName() {
        return this.projectName;
    }


    public String getProjectVersion() {
        return this.projectVersion;
    }


    public String getProjectOwner() {
        return this.projectOwner;
    }


    public Collection getPackagedComponents() {
        return packagedComponents.keySet();
    }


    public String getPackagedComponentVersion(String componentName) {
        return (String) packagedComponents.get(componentName);
    }


    public boolean supportsResource(String uri) {

        String suffix = uri.substring(uri.lastIndexOf(".") + 1);
        if ("substvar".equalsIgnoreCase(suffix)) return true;
        if (uri.endsWith("TIBCO.xml")) return true;
        return false;

    }


    public List getChangeList() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public void init() throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }


    public boolean isDirty() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public void clearDirty() {

    }


    public void clear() {
        this.globalVariables.clear();
        this.nameToFullGV.clear();
        this.gvNames.clear();
        gVarsElement = null;
    }


    public void merge(GlobalVariables vars) {

        GlobalVariablesProviderImpl provider = (GlobalVariablesProviderImpl) vars;
        this.globalVariables.putAll(provider.globalVariables);
        this.globalVarsNode = null;
    }


    SmElement gVarsElement = null;
    static String GLOBAL_VARS_NAMESPACE = "http://www.tibco.com/be/DeployedVarsType";


    public SmElement toSmElement(boolean includeDebuggerVars) {
        if (gVarsElement != null) return gVarsElement;

        SmFactory factory = SmFactory.newInstance();
        MutableSchema schema = factory.createMutableSchema();
        schema.setFlavor(com.tibco.xml.schema.flavor.XSDL.FLAVOR);
        schema.setNamespace(GLOBAL_VARS_NAMESPACE);
        MutableType deployedVarsType = MutableSupport.createType(schema, "DeployedVars");

        Collection<GlobalVariableDescriptor> gVars = this.getVariables();
        LinkedHashMap groupTypes = new LinkedHashMap();
        for (GlobalVariableDescriptor gvd : gVars) {

            String name = gvd.getFullName();
            if (name.startsWith("/")) name = name.substring(1);
            String type = gvd.getType();

            if ("password".equalsIgnoreCase(type)) continue;

            //if (!isValidName(name)) continue;

            SmType gvType = getTypeForGVType(type);

            int slash = name.lastIndexOf('/');
            if (slash > -1) {
                MutableType parentType = getParentType(name.substring(0, slash), deployedVarsType, groupTypes, schema);
                MutableSupport.addRequiredLocalElement(parentType, name.substring(slash + 1), gvType);
            } else {
                MutableSupport.addRequiredLocalElement(deployedVarsType, name, gvType);
            }
        }

        if (includeDebuggerVars) {
            // Add debugger Variables
            MutableType debuggerType = MutableSupport.createType(schema, "Debugger");
            MutableSupport.addRequiredLocalElement(deployedVarsType, "Debugger", debuggerType);
            MutableSupport.addRequiredLocalElement(debuggerType, "sessionCounter", XSDL.INTEGER);
            // profile data
            MutableType profileType = MutableSupport.createType(schema, "profile");
            MutableSupport.addRequiredLocalElement(debuggerType, "profile", profileType);
            MutableSupport.addRequiredLocalElement(profileType, "name", XSDL.STRING);
            MutableSupport.addRequiredLocalElement(profileType, "beHome", XSDL.STRING);
            MutableSupport.addRequiredLocalElement(profileType, "hostName", XSDL.STRING);
            MutableSupport.addRequiredLocalElement(profileType, "portNumber", XSDL.STRING);
            MutableSupport.addRequiredLocalElement(profileType, "workingDir", XSDL.STRING);
            MutableSupport.addRequiredLocalElement(profileType, "repoUrl", XSDL.STRING);
        }


        gVarsElement = MutableSupport.createElement(schema, "GlobalVariables", deployedVarsType);
        return gVarsElement;

    }


    public SmElement toSmElement() {
        return toSmElement(true);
    }


    /**
     * Gets the corresponding xml type of the global variable.
     *
     * @param gvTypeName The global variable type string (seems like a randomly assigned name...), returns string for null or unknown.
     * @return The SmType for that.
     */
    private SmType getTypeForGVType(String gvTypeName) {
        if (gvTypeName == null) {
            return XSDL.STRING; // just in case.
        }
        // GV type caps --- seem to be mixed case.
        if (gvTypeName.equalsIgnoreCase("String")) {
            return XSDL.STRING;
        }
        if (gvTypeName.equalsIgnoreCase("Integer")) {
            return XSDL.INTEGER;
        }
        if (gvTypeName.equalsIgnoreCase("Boolean")) {
            return XSDL.BOOLEAN;
        }
        if (gvTypeName.equalsIgnoreCase("Password")) {
            // Treat 'password' as a string.
            return XSDL.STRING;
        }
        // Unknown, don't fail, just return a string.
        return XSDL.STRING;
    }


    private boolean isValidName(String varName) {
        int index = varName.indexOf('/');
        if (index > -1) {
            String firstPart = varName.substring(0, index);
            String lastPart = varName.substring(index + 1);
            return (XMLStringUtilities.isNCName(firstPart) && isValidName(lastPart));
        } else {
            return XMLStringUtilities.isNCName(varName);
        }
    }


    private MutableType getParentType(String varName, MutableType topType, LinkedHashMap groupTypes, MutableSchema schema) {
        if (groupTypes.containsKey(varName)) {
            return (MutableType) groupTypes.get(varName);
        } else {
            MutableType result;
            int slash = varName.lastIndexOf('/');
            if (slash > -1) {
                String nextGroupName = varName.substring(0, slash);
                String thisName = varName.substring(slash + 1);
                MutableType myParent = getParentType(nextGroupName, topType, groupTypes, schema);
                result = MutableSupport.createType(schema, thisName);
                MutableSupport.addRequiredLocalElement(myParent, thisName, result);
            } else {
                result = MutableSupport.createType(schema, varName);
                MutableSupport.addRequiredLocalElement(topType, varName, result);
            }
            groupTypes.put(varName, result);
            return result;
        }
    }


    public Map<String, GlobalVariableDescriptor> getNamesMap() {
        return nameToFullGV;
    }


    public void setNamesMap(Map<String, GlobalVariableDescriptor> map) {
        nameToFullGV = map;
    }
    
    public List<String> getGvNames() {
		return gvNames;
	}


}

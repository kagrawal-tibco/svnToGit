package com.tibco.cep.studio.dashboard.core.insight.model.helpers;

import java.util.List;
import java.util.Map;

import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.util.EnumHelper;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;

/**
 * ASSUMPTION: (1) The path of properties can have intermediate objects. (2) The path of
 * properties can be prefixed by '@' to denote a reference. (3) The path of properties can have
 * subscripts. (4) Intermediate object are separated by curly bracket followed by ':'.
 */
public class LocalPropertyConfig {

    SynProperty property;

    String originalPath;

    public Map<String,String> enumPair;

    String path = null;
    
    private List<String> referencers;

    public String[] pathTokens = new String[0];

    public String[] typeTokens = new String[0];

    public Subscript[] subscripts = new Subscript[0];

    public boolean isEmptyPath = false;

    public String leaf = null;

    String typeAboveLeaf = null;

    String type = null;

    public boolean isElementRef = false;

    public boolean isNameRef = false;

    public boolean isMDConfigType = true;

    LocalPropertyConfig(SynProperty property, String path, Map<String,String> enumPair, String type, String referencers) throws Exception {
        this.property = property;
        this.originalPath = path;
        this.enumPair = enumPair;
        this.referencers = StringUtil.toList(referencers);
        this.init();
        this.parseType(type);
    }

    private void init() throws Exception {
        this.parsePath(this.originalPath);
        this.isEmptyPath = (this.pathTokens.length == 0);
        this.leaf = (this.isEmptyPath ? null : (String) this.pathTokens[pathTokens.length - 1]);
        this.typeAboveLeaf = ((this.isEmptyPath || typeTokens.length < 2) ? null : this.typeTokens[pathTokens.length - 2]);
    }

    /**
     * Parse a path into a list of tokens. (1) @ sign should be at the last part of path. @ is
     * elementRef (by-reference using id). # is nameRef (by-reference using scope name) (2) If a
     * path has intermediate type name, remove them. (3) The start, intermediate and leaf types
     * are all MDConfig.
     * @param path
     * @return
     */
    private void parsePath(String path) throws Exception {

        StringBuffer result = new StringBuffer();
        // only the last part of the path can have @ or # sign.
        String[] tokens = StringUtil.split(path, ViewsConfigReader.SEPARATOR);
        pathTokens = new String[tokens.length];
        typeTokens = new String[tokens.length];
        subscripts = new Subscript[tokens.length];
        // remove intermediate type name
        for (int i = 0; i < tokens.length; i++) {
            if (i > 0) {
                result.append(ViewsConfigReader.SEPARATOR);
            }
            String[] subTokens = StringUtil.split(tokens[i], ViewsConfigReader.SUB_SEPARATOR);
            if (subTokens.length <= 0 || subTokens.length > 2) {
                throw new Exception("the format of path is wrong:" + path);
            }
            // the first part may have subscript
            // the first part may have elementRef(id) or nameRef(scopeName).
            int start, end;
            start = subTokens[0].indexOf(ViewsConfigReader.SUBSCRIPT_START);
            if (start > 0) {
                end = subTokens[0].indexOf(ViewsConfigReader.SUBSCRIPT_END, start);
                if (end < 0) {
                    throw new Exception("the format of path is wrong:" + path);
                }
                String nextPath = null;
                if (i == tokens.length - 1) {
                    // only the last path may have @ and #.
                    if (subTokens[0].startsWith(ViewsConfigReader.ELEMENTREF)) {
                        this.isElementRef = true;
                        nextPath = subTokens[0].substring(1, start);
                    } else if (subTokens[0].startsWith(ViewsConfigReader.NAMEREF)) {
                        this.isNameRef = true;
                        nextPath = subTokens[0].substring(1, start);
                    } else {
                        nextPath = subTokens[0].substring(0, start);
                    }
                } else {
                    nextPath = subTokens[0].substring(0, start);
                }
                result.append(nextPath);
                pathTokens[i] = nextPath;
                subscripts[i] = new Subscript(subTokens[0].substring(start + 1, end));
            } else {
                // no subscripts
                String nextPath = null;
                start = subTokens[0].length();
                if (i == tokens.length - 1) {
                    // only the last path may have @ and #.
                    if (subTokens[0].startsWith(ViewsConfigReader.ELEMENTREF)) {
                        this.isElementRef = true;
                        nextPath = subTokens[0].substring(1, start);
                    } else if (subTokens[0].startsWith(ViewsConfigReader.NAMEREF)) {
                        this.isNameRef = true;
                        nextPath = subTokens[0].substring(1, start);
                    } else {
                        nextPath = subTokens[0].substring(0, start);
                    }
                } else {
                    nextPath = subTokens[0].substring(0, start);
                }
                result.append(nextPath);
                pathTokens[i] = nextPath;
                subscripts[i] = new Subscript("-1");
            }
            // the second part just have type
            if (subTokens.length > 1) {
                start = subTokens[1].indexOf(ViewsConfigReader.TYPE_START);
                if (start < 0) {
                    throw new Exception("the format of path is wrong:" + path);
                }
                end = subTokens[1].indexOf(ViewsConfigReader.TYPE_END, start);
                if (end < 0) {
                    throw new Exception("the format of path is wrong:" + path);
                }
                typeTokens[i] = subTokens[1].substring(start + 1, end);
            } else {
                // only the leaf does not have type.
                if (i == tokens.length - 1) {
                    // It is okay.
                } else {
                    throw new Exception("should not happen.");
                }
            }
        }
        this.path = result.toString();
    }

    private void parseType(String type) throws Exception {

        // MD Type are enclosed by curly bracket.
        int start = type.indexOf(ViewsConfigReader.TYPE_START);
        if (start >= 0) {
            int end = type.indexOf(ViewsConfigReader.TYPE_END, start);
            if (end <= start) {
                throw new Exception("invalid frormat" + path);
            }
            this.isMDConfigType = false;
            this.type = type.substring(start + 1, end);
        } else {
            this.isMDConfigType = true;
            this.type = type;
        }
    }

	public int getLeafIndex() {
		Subscript subscript = subscripts[subscripts.length - 1];
		if (subscript.isIndex() == true){
			return subscript.getIndex();
		}
		throw new IllegalStateException("Invalid leaf subscript in "+originalPath);		
	}

	public String getPath() {
		return path;
	}

	public String getType() {
		return type;
	}
	
	public String[] getPropertyEnumValues() {
		String[] enumValues = new String[0];
        if (this.enumPair != null) {
        	enumValues = EnumHelper.getLocalNames(this.enumPair);
        }
        return enumValues;
	}
	
	public List<String> getReferences(){
		return referencers;
	}
}


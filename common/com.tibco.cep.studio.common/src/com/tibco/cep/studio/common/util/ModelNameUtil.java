package com.tibco.cep.studio.common.util;

//import com.tibco.cep.designtime.model.Entity;
//import com.tibco.cep.designtime.model.Folder;
//import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * 
 * @author rmishra
 *
 */
public class ModelNameUtil {
    //private static final char modelSep = Folder.FOLDER_SEPARATOR_CHAR;
	//FIXME
	private static final char modelSep = '/';
    public static final char RULE_SEPARATOR_CHAR = '.';
    private static final BEProperties beprops = BEProperties.loadDefault();

    //todo add this to a properties file
    public static final String GENERATED_PACKAGE_PREFIX = beprops.getString("be.codegen.rootPackage", "be.gen");
    public static final String INNER_CLASS_PREFIX = "$1z";
    public static final String MEMBER_PREFIX = "$2z";
    public static final String SCOPE_IDENTIFIER_PREFIX = "$3z";
    public static final String STATEMACHINE_PREFIX = "$";
    public static final String VRF_IMPLS_PKG_NAME = "$vrf_impls$";
    /**
     * Takes the fully specified name of a generated class and returns the corresponding path in the model.
     * This is done entirely by substitution and no attempt is made to verify that the input class exists
     * or that the output path is valid
     * Returned paths will never have a trailing '/'
     * @param className
     * @return
     */
    public static String generatedClassNameToModelPath(String className) {
        //className = removeGeneratedPackagePrefix(className);
        return modelPathFromExternalForm(className);
    }

    public static String generatedClassNameToStateMachineModelPath(String className) {
        //className = removeGeneratedPackagePrefix(className);
        String uri= modelPathFromExternalForm(className);
        String conceptURI=uri.substring(0,uri.indexOf('$'));
        String smURI= uri.substring(uri.indexOf('$'));
        smURI=smURI.substring(smURI.lastIndexOf('$')+1);
        //smURI=smURI.replace('$', '/');
        return conceptURI + "/" + smURI;
    }

    /**
     * Takes a model path and returns the fully specified name of the corresponding generated class.
     * This is done entirely by substitution and no attempt is made to verify that modelPath exists
     * or refers to an object that will be generated
     * @param modelPath
     * @return
     */
    public static String modelPathToGeneratedClassName(String modelPath) {
        modelPath = modelPathToExternalForm(modelPath);
        return modelPath; //prependGeneratedPackagePrefix(modelPath);
    }

    public static String ruleFnClassFSName(RuleFunction fn) {
    	String parentFullPath = ModelUtils.getParentFullPath(fn.getFullPath());
        return modelPathToExternalForm(parentFullPath) + RULE_SEPARATOR_CHAR + ruleFnClassShortName(fn);
    }

    //Designer limits resource names to 200 unicode characters or the filesystem limit if it's less.
    //Since ext2 has a max filename size of 255 bytes it's possible that the resource name + ".java"
    //is exactly 255 bytes and there won't be enough extra space for this suffix.
    private static final String OVERSIZE_FN_CLASS_NAME_SUFFIX = "$oversizeName";
    private static final String JAVA_FILE_EXTENSION = beprops.getString("be.codegen.javaFileExtension", ".java");
    
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
    
    private static boolean isNameOversize(String name) {
        //this is a best effort to see if the genereated class filename is too long for the filesystem.
        //other things could cause an IOException that are not related to the size of the filename
        //85 is 255 / 3 where 255 bytes is the filename length limit on most file systems (excepting NTFS)
        //and 3 bytes per character is a reasonable worst case under UTF-8 (which is not guaranteed
        //to be in use, and UTF could potentially have more than 3 bytes per character, but this is a best effort).
        return name.length() > 85;
    }
    
    private static String ruleFnClassShortNameWithArgTypes(RuleFunction fn) {
        StringBuilder sb = new StringBuilder();
        sb.append(fn.getReturnType());
        sb.append(fn.getName());
        //final Symbols args = fn.getArguments();
        for(Symbol symbol : fn.getSymbols().getSymbolList()) {
           // final Symbol symbol = (Symbol) it.next();
            String type = symbol.getType();
            if(type == null) type = void.class.getName();
            sb.append(type);
        }
        //$ suffix is so that a user can't create a resource whose class name overlaps w/ a rule function class
        String name = ModelNameUtil.escapeIdentifier(sb.toString()) + "$";
        
        return name;
    }

    public static boolean endsWithOversizeRuleFnClassNameSuffix(String str) {
        return str.endsWith(OVERSIZE_FN_CLASS_NAME_SUFFIX);
    }
    
    public static boolean ruleFunctionHasOversizeClassName(RuleFunction fn) {
        return endsWithOversizeRuleFnClassNameSuffix(ruleFnClassShortName(fn));
    }

    //generate the name of a VRF impl as vrf path.vrf name$vrf_impls$.vrf class name.implName
    //Example impl named Impl of VRF /Functions/Virtual where Virtual returns void and takes and int argument 
    // would be be.gen.Functions.Virtual$vrf_impls$.nullVirtualint.Impl
    public static String vrfImplClassFSName(RuleFunction vrf, String implName) {
        return (new StringBuilder(vrfImplPkg(vrf)).append(".").append(vrfImplClassShortName(implName))).toString();
    }
    public static String vrfImplClassShortName(String implName) {
        return implName;
    }
    public static String vrfImplPkg(RuleFunction vrf) {
        return new StringBuilder(modelPathToExternalForm(vrf.getFolder()))
                .append(".").append(vrf.getName())
                .append(VRF_IMPLS_PKG_NAME)
                .append(".").append(ruleFnClassShortName(vrf)).toString();
    }
    //convert a vrf impl's FQN to it's virtual function's URI
    public static String vrfImplClassFQNToVRFURI(String fqn) {
        if(fqn == null) return null;
        int lastIndex = fqn.lastIndexOf(VRF_IMPLS_PKG_NAME);
        if(lastIndex <= 0) return null;
        return generatedClassNameToModelPath(fqn.substring(0, lastIndex));
    }

    //convert a vrf impl's FQN to it's impls's name
    public static String vrfImplClassFQNToImplName(String fqn) {
        if(fqn == null) return null;
        int lastIndexDot = fqn.lastIndexOf(".");
        if(lastIndexDot <= 0) return null;
        return fqn.substring(lastIndexDot);
    }
    
    //the name of the function in the VRF Impl class that implements the VRF
    public static String vrfImplClassFQNToFnName(String fqn) {
        String vrfURI = vrfImplClassFQNToVRFURI(fqn);
        return getShortNameFromPath(vrfURI);
    }
    
    //returns (VRF URI, impl name} or null if argument doesn't resemble a VRF impl FQN
    public static String[] decodeVRFImplFQN(String fqn) {
        String vrfURI = vrfImplClassFQNToVRFURI(fqn);
        if(vrfURI == null) return null;
        String implName = vrfImplClassFQNToImplName(fqn);
        if(implName == null) return null;
        return new String[]{vrfURI, implName};
    }
    
    /**
     * Converts a path in the model to a fully specified Java package or class name
     * that will be used during code generation step of deployment.
     * This happens purely by substitution; the input name isn't verified to
     * exist in the model and the output name isn't verified to be
     * acutally generated (a folder that doesn't contain concepts or events
     * won't have a package created for it during code generation).
     * Characters in the path that aren't allowed in Java will be escaped.
     * They can be unescaped with modelPathFromExternalForm()
     * example: passing /folder1/folder2/ would return folder1.folder2
     */
    /*
     * The algorithm:
     * If the input is null return GENERATED_PACKAGE_PREFIX
     * Split the input on the '/' character
     * return each non-empty element of the resulting array
     * separated by the '.' with a leading GENERATED_PACKAGE_PREFIX + '.'
     */
    public static String modelPathToExternalForm(String path) {
        //String sepStr = String.valueOf(modelSep);
        if(path == null || path.length() <= 0) return GENERATED_PACKAGE_PREFIX;

        //the external form always starts with GENERATED_PACKAGE_PREFIX
        StringBuilder escaped = new StringBuilder(GENERATED_PACKAGE_PREFIX);

        String[] identifiers = path.split("/");
        for(int ii = 0; ii < identifiers.length; ii++) {
            String id = identifiers[ii];
            if(id.length() > 0) {
                escaped.append(RULE_SEPARATOR_CHAR);
                escaped.append(escapeIdentifier(id));
            }
        }

        return escaped.toString();
    }


    /**
     * Converts a fully specified Java package or class name from to a path in the model.
     * This is only meant to work with package and class names that were created by
     * the code generation step during deployment.
     * Any escape codes in the external form are also unescaped.
     * This happens purely by substitution the input string isn't verified
     * to be the result of code generation and the output
     * path isn't verified to exist in the model.
     * The output never has a trailing '/'.
     * example: passing folder1.folder2 would return /folder1/folder2
     * @param str
     * @return
     */
    /*
     * The algorithm:
     * if the input string was null return "/" (the path of the root folder)
     * if the string equals GENERATED_PACKAGE_PREFIX return "/"
     * if the string begins with GENERATED_PACKAGE_PREFIX + '.', remove this prefix
     * split the input on the '.' character and return
     * return each non-empty element of the resulting array unescaped and separated by the '/' as well as a leading '/'
     */
    public static String modelPathFromExternalForm(String str) {
        //default is to return "/", the path of the root folder
        if(str == null || str.length() <= 0) return String.valueOf(modelSep);
        if(str.equals(GENERATED_PACKAGE_PREFIX)) return String.valueOf(modelSep);
        if(str.startsWith(GENERATED_PACKAGE_PREFIX + RULE_SEPARATOR_CHAR)) str = str.substring((GENERATED_PACKAGE_PREFIX + RULE_SEPARATOR_CHAR).length());

        //paths start with a leading slash
        StringBuilder path = new StringBuilder();
        String[] identifiers = str.split("\\.");
        for(int ii = 0; ii < identifiers.length; ii++) {
            String id = identifiers[ii];
            if(id.length() > 0) {
                path.append(String.valueOf(modelSep));
                path.append(unescapeIdentifier(id));
            }
        }
        return path.toString();
    }

    /**
     * @param str
     * @return the input escaped such that it is a valid identifier in the rules language
     */
    /*
    * The algorithm:
    * For each character in the input:
    * if it is not a valid character in a Java identifier or it is a '$'
    * then replace it with a $xxxx where xxxx are
    * four hex digits that represents that character's numerical value
    */
    public static String escapeIdentifier(String str) {
        StringBuilder escaped = new StringBuilder();
        char[] chars = str.toCharArray();

        boolean idStart = true;
        for(int ii = 0; ii < chars.length; ii++) {
            char chr = chars[ii];
            boolean invalidChar;
            if(!idStart || (idStart = false)) {
                //don't use the isIdentifierStart function because for this fuction
                //it doesn't matter what is legal in the rule language, only
                //what is legal for Java identifiers
                invalidChar = !Character.isJavaIdentifierPart(chr) || (chr == '$');
            } else {
                invalidChar = !Character.isJavaIdentifierStart(chr) || (chr == '$');
            }

            if(invalidChar) {
                escaped.append('$');
                String hex = Integer.toHexString(chr);
                //append extra zeros to make sure the '$' is followed by exactly four hex digits
                for(int jj = 0; jj < 4 - hex.length(); jj++) {
                    escaped.append('0');
                }
                escaped.append(hex);
            } else {
                escaped.append(chr);
            }
        }
        return escaped.toString();
    }
    /**
     * @param str a string previously escaped by the escapeIdentifier method
     * @return a string equivalent to the original input to escapeIdentifier
     */
    /*
    * The algorithm:
    * for each character in the input:
    * if the character is a '$' and it is followed by four hex digits
    * replace those 5 characters ($hhhh) with the character whose numeric value
    * is the value of hhhh.
    */
    public static String unescapeIdentifier(String str) {
        StringBuilder unescaped = new StringBuilder();
        char[] chars = str.toCharArray();
        for(int ii = 0; ii < chars.length; ii++) {
            char chr = chars[ii];
            if(chr == '$' && ((ii + 4) < chars.length)){
                char[] unicodeHex = {chars[++ii], chars[++ii], chars[++ii], chars[++ii]};
                String hexString = new String(unicodeHex);
                try {
                    unescaped.append((char)Integer.parseInt(hexString, 16));
                    continue;
                    //if the four characters following the '$'
                    //weren't a hex numeral, then the continue statement
                    //will be skipped, the original value of ii before the
                    //creation of the unicodeHex array will be restored,
                    //and the '$' and the subsequent characters will be treated normally.
                } catch (NumberFormatException nfe) {
                    ii -= 4;
                }
            }
            unescaped.append(chr);
        }
        return unescaped.toString();
    }

    /**
     * given a fully specified name in Java format (folder1.Concept)
     * this will prepend a prefix to reflect the package all generated classes
     * belong to.
     * example: folder1.folder2.Concept becomes gen.prefix.folder1.folder2.Concept
     * @param str
     * @return
     */
    public static String prependGeneratedPackagePrefix(String str) {
        if(str == null || str.equals("")) return GENERATED_PACKAGE_PREFIX;
        return GENERATED_PACKAGE_PREFIX + RULE_SEPARATOR_CHAR + str;
    }

    /**
     * if the argument starts with the package prefix for generated classes,
     * the return value will be the argument with the prefix removed.
     * @param str
     * @return
     */
    public static String removeGeneratedPackagePrefix(String str) {
        if(str == null || str.length() <= 0 || str.equals(GENERATED_PACKAGE_PREFIX)) {
            return "";
        }
        if(str.startsWith(GENERATED_PACKAGE_PREFIX + RULE_SEPARATOR_CHAR)) {
            return str.substring((GENERATED_PACKAGE_PREFIX + RULE_SEPARATOR_CHAR).length());
        }
        return str;
    }

    /**
     *
     * @param cept
     * @param sm
     * @return the generated class name for a statemachine
     */
    public static String modelNametoStateMachineClassName(Concept cept, StateMachine sm) {
        return ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath()) + "." + cept.getName() + STATEMACHINE_PREFIX + sm.getName();
    }

    public static String modelNametoStateMachinePropertyClassName(Concept cept, StateMachine sm) {
        return ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath()) + "." + ModelNameUtil.generatedStateMachinePropertyClassName(cept,sm);
    }

    public static String modelNameToStateMachineVirtualMethod(StateMachine sm) {
        return "get$5z" + sm.getName();
    }

    /**
     *
     * @param cept
     * @param sm
     * @return
     */
    public static String generatedStateMachineClassName(Concept cept, StateMachine sm) {
        return cept.getName() + STATEMACHINE_PREFIX + sm.getName();
    }

    /**
     *
     * @param cept
     * @param sm
     * @return
     */
    public static String generatedStateMachinePropertyMemberName(Concept cept, StateMachine sm) {
        return ModelNameUtil.generatedMemberName (cept.getName() + STATEMACHINE_PREFIX + sm.getName());
    }

    /**
     *
     * @param cept
     * @param sm
     * @return
     */
    public static String generatedStateMachinePropertyName(Concept cept, StateMachine sm) {
        return cept.getName() + STATEMACHINE_PREFIX + sm.getName();
    }

    /**
     *
     * @param cept
     * @param sm
     * @return
     */
    public static String generatedStateMachinePropertyClassName(Concept cept, StateMachine sm) {
        return ModelNameUtil.generatedMemberClassName((cept.getName() + STATEMACHINE_PREFIX + sm.getName()));
    }

    /**
     * Given the name of a concept's property, this method will
     * return the name of the inner class that will correspond to that property in the generated
     * concept class. 
     * This method does not escape characters that are invalid in the rules langauge.
     * @param memberName
     * @return
     */
    public static String generatedMemberClassName(String memberName) {
        return INNER_CLASS_PREFIX + memberName;
    }

    /**
     * Given the name of a concept or event property, this method will
     * return the name of the member variable that references that property
     * in the generated concept or event class.
     * This method does not escape characters that are invalid in the rules langauge.
     * @param memberName
     * @return
     */
    public static String generatedMemberName(String memberName) {
        return MEMBER_PREFIX + memberName;
    }

    /**
     * Transform a rule scope variable name, a local variable name or a function argument name
     * in the rules language into the name it will have in the generated Java code.
     * @param varName name of scope variable, local variable, or function argument
     * @return
     */
    public static String generatedScopeVariableName(String varName) {
        return SCOPE_IDENTIFIER_PREFIX + varName;
    }

    /**
     * @param ch
     * @return true if ch may be used as the first character of an identifier in the rules language
     */
    public static boolean isIdentifierStart(char ch) {
        return ch != '$' && Character.isJavaIdentifierStart(ch);
    }

    /**
     * @param ch
     * @return true if ch may be used as any character but the first of an identifier in the rules language 
     */
    public static boolean isIdentifierPart(char ch) {
        return ch != '$' && Character.isJavaIdentifierPart(ch);
    }

    /**
     * @param ch
     * @return true if ch may be used as any character but the first of an identifier in the rules language 
     */
    public static boolean isProjectIdentifierPart(char ch) {
        return (ch == '-') || (ch != '$' && Character.isJavaIdentifierPart(ch));
    }
    
    /**
     * @param identifier
     * @return true if identifier may be used as an identifier in the rules language
     */
    public static boolean isValidIdentifier(String identifier) {
        if(identifier == null || identifier.length() <= 0) return false;
        boolean start = true;
        for(int ii = 0; ii < identifier.length(); ii++) {
            char ch = identifier.charAt(ii);
            if(!start || (start = false)) {
                if(!isIdentifierPart(ch)) return false;
            } else {
                if(!isIdentifierStart(ch)) return false;
            }
        }
        return true;
    }
    
    /**
     * @param ch
     * @param index
     * @param maxlength
     * @return
     */
    public static boolean isISharedResourceIdentifierPart(char ch, int index, int maxlength) {
    	if(ch == ' ' && (index > 0 && index != maxlength -1)) return true;
    	if(ch == '.' && (index > 0 && index != maxlength -1)) return true;
        return ch != '$' && Character.isJavaIdentifierPart(ch);
    }
    
    /**
     * @param identifier
     * @return true if identifier may be used as an identifier in the rules language
     */
    public static boolean isValidSharedResourceIdentifier(String identifier) {
        if(identifier == null || identifier.length() <= 0) return false;
        boolean start = true;
        for(int ii = 0; ii < identifier.length(); ii++) {
            char ch = identifier.charAt(ii);
            if(!start || (start = false)) {
                if(!isISharedResourceIdentifierPart(ch, ii, identifier.length())) return false;
            } else {
                if(!isISharedResourceIdentifierPart(ch, ii, identifier.length())) return false;
            }
        }
        return true;
    }

    /**
     * @param identifier
     * @return true if identifier may be used as an identifier in the rules language
     */
    public static boolean isValidProjectIdentifier(String identifier) {
        if(identifier == null || identifier.length() <= 0) return false;
        boolean start = true;
        for(int ii = 0; ii < identifier.length(); ii++) {
            char ch = identifier.charAt(ii);
            if(!start || (start = false)) {
                if(!isProjectIdentifierPart(ch)) return false;
            } else {
                if(!isProjectIdentifierPart(ch)) return false;
            }
        }
        return true;
    }
    
    /**
     * @param name
     * @return assuming name has the form identifier.identifer . . . will return true if all the identifiers return true for isValidIdentifier
     */
    public static boolean isValidFSName(String name) {
        String[] ids = name.split("\\.");
        for(int ii = 0; ii < ids.length; ii++) {
            if(!isValidIdentifier(ids[ii])) {
                return false;
            }
        }
        return true;
    }

    /**
     * for paths in the form of /xxx/yyy/zzz, this returns the substring of the path that occurrs after the last "/", in this case "zzz"
     * @param path
     * @return the last name in a path, ie the short name of the target of the path
     */
    public static String getShortNameFromPath(String path) {
        if(path == null || path.length() <= 0) return "";
        if(path.charAt(path.length() - 1) == modelSep) path = path.substring(0, path.length() - 1);
        int lastIndex = path.lastIndexOf(modelSep);
        if(lastIndex < 0) return path;
        return path.substring(lastIndex + 1);
    }

    /**
     * for paths in the form of /xxx/yyy/zzz, this returns the substring of the path that occurrs before the last "/", in this case "/xxx/yyy/"
     * @param path
     * @return everything but the last name in a path (ie the enclosing folder of the target of the path)
     */
    public static String getFolderFromPath(String path) {
    	
        if(path == null || path.length() <= 0) //FIXMEreturn String.valueOf(Folder.FOLDER_SEPARATOR_CHAR);
        	return String.valueOf('/');
        if(path.charAt(path.length() - 1) == modelSep) path = path.substring(0, path.length() - 1);
        int lastIndex = path.lastIndexOf(modelSep);
        if(lastIndex < 0) return path;
        return path.substring(0, lastIndex + 1);
    }


    public static String getSMContextName(Entity entity) {
        return  entity.getName().toLowerCase();
    }

    /**
     * @param path
     * @return assuming a valid model path is passed in, will return a type literal in the rules language for that path
     */
    //public static String pathToPathLiteral(String path) {
    //    return "#" + path + "#";
    //}

    /**
     * @param pathLit
     * @return if pathLit looks like a path literal, returns the path represented by pathLit, otherwise returns pathLit
     */
    //public static String pathFromPathLiteral(String pathLit) {
    //    if(pathLit.startsWith("#") && pathLit.endsWith("#")) {
    //        return pathLit.substring(1, pathLit.length() - 1);
    //    } else {
    //        return pathLit;
    //    }
    //}


    public static String getStateRuleName(StateEntity state, String qualifier) {
        String  ruleName = "_" + qualifier;
        StateMachine sm = state.getOwnerStateMachine();
        State  root = sm;
        StateEntity  currentNode = state;
        while (currentNode != root && currentNode != null) {
            ruleName = "_" + currentNode.getName () + ruleName;
            currentNode = currentNode.getParent ();
        }//endwhile
        String ownerSMname = sm == null ? getOwnerStateMachineNameFromPath(state.getOwnerStateMachinePath()) : state.getOwnerStateMachine ().getName ();
        ruleName = ownerSMname + ruleName;
        return ruleName;
    }
    
    public static String getTransitionRuleName(StateTransition trans) {
		StateMachine sm = trans.getOwnerStateMachine();
        String ownerSMname = sm == null ? getOwnerStateMachineNameFromPath(trans.getOwnerStateMachinePath()) : trans.getOwnerStateMachine ().getName ();
        return ownerSMname + "_" + trans.getName();
    }

    private static String getOwnerStateMachineNameFromPath(
			String ownerStateMachinePath) {
    	if(ownerStateMachinePath == null) {
    		return "";
    	}
    	if(ownerStateMachinePath.indexOf(modelSep) != -1) {
    		return ownerStateMachinePath.substring(ownerStateMachinePath.lastIndexOf(modelSep)+1);
    	}
    	if(!ownerStateMachinePath.isEmpty()) {
    		return ownerStateMachinePath;
    	}
    	return "";
	}

	
}

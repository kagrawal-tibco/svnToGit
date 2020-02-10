package com.tibco.be.model.util;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.State;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.designtime.model.element.stategraph.StateTransition;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.Symbol;
import com.tibco.cep.designtime.model.rule.Symbols;


/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 2, 2004
 * Time: 2:38:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelNameUtil {
    private static final char modelSep = Folder.FOLDER_SEPARATOR_CHAR;
    public static final char RULE_SEPARATOR_CHAR = '.';

    //todo add this to a properties file
    public static final String GENERATED_PACKAGE_PREFIX = System.getProperty("be.codegen.rootPackage", "be.gen");
    public static final String INNER_CLASS_PREFIX = "$1z";
    public static final String MEMBER_PREFIX = "$2z";
    public static final String SCOPE_IDENTIFIER_PREFIX = "$3z";
    public static final String RT_IDENTIFIER_PREFIX = "$4z";
    public static final String STATEMACHINE_PREFIX = "$";
    public static final String VRF_IMPLS_PKG_NAME = "$vrf_impls$";
    public static final String PROCESS_MARKER = "$5zproc";
    private static final String OVERSIZE_FN_CLASS_NAME_SUFFIX = "$oversizeName";

    public static final boolean EXTENDED_ESCAPE = Boolean.parseBoolean(System.getProperty("be.codegen.extended_escape", "true"));
    public static final Pattern UNESCAPE_PATTERN = EXTENDED_ESCAPE ? Pattern.compile("\\$9y([0-9a-fA-F]{4})") : Pattern.compile("\\$([0-9a-fA-F]{4})");
    
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
    
    public static String generatedClassNameToStateMachineOwnerURI(String className) {
    	//pasted from generatedClassNameToStateMachineModelPath 
    	String uri= modelPathFromExternalForm(className);
        String conceptURI=uri.substring(0,uri.indexOf('$'));
        return conceptURI;
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
        return modelPathToExternalForm(fn.getFolder().getFullPath()) + RULE_SEPARATOR_CHAR + ruleFnClassShortName(fn);
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
        final Symbols args = fn.getArguments();
        for(Iterator it = args.values().iterator(); it.hasNext();) {
            final Symbol symbol = (Symbol) it.next();
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

    public static boolean isVRFImplClass(String className) {
        return className != null && className.contains(VRF_IMPLS_PKG_NAME);
    }
    
    public static boolean isProcessClass(String className) {
    	return className != null && className.endsWith(PROCESS_MARKER);
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
        return new StringBuilder(modelPathToExternalForm(vrf.getFolderPath()))
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
        return fqn.substring(lastIndexDot + 1);
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
            if(!idStart) {
                //don't use the isIdentifierStart function because for this fuction
                //it doesn't matter what is legal in the rule language, only
                //what is legal for Java identifiers
                invalidChar = !Character.isJavaIdentifierPart(chr) || (chr == '$');
            } else {
            	idStart = false;
                invalidChar = !Character.isJavaIdentifierStart(chr) || (chr == '$');
            }

            if(invalidChar) {
                if(EXTENDED_ESCAPE) {
                    escaped.append("$9y");
                } else {
                    escaped.append('$');
                }
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
    * replace $hhhh or $9yhhhh (depending on value of EXTENDED_ESCAPE)
    * with the character whose numeric value
    * is the value of hhhh.
    */
    public static String unescapeIdentifier(String str) {
        Matcher m = UNESCAPE_PATTERN.matcher(str);
        StringBuffer unescaped = new StringBuffer();
        while (m.find()) {
            try {
                char chr = (char)Integer.parseInt(m.group(1), 16);
                //$ and \ are special characters for appendReplacement
                if(chr == '$') {
                    m.appendReplacement(unescaped, "\\$");
                } else if(chr =='\\') {
                    m.appendReplacement(unescaped, "\\\\");
                } else {
                    m.appendReplacement(unescaped, String.valueOf(chr));
                }
            } catch (NumberFormatException nfe) {
                m.appendReplacement(unescaped, "");
            }
        }
        m.appendTail(unescaped);
        
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
    
    public static String stateMachineClassloaderClassName(Concept cept, StateMachine sm) {
        return ModelNameUtil.modelPathToGeneratedClassName(cept.getFullPath()) + "$" + cept.getName() + STATEMACHINE_PREFIX + sm.getName();
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

    public static String generatedRuleTemplateItemName(String name) {
        return RT_IDENTIFIER_PREFIX + name;
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
     * @param identifier
     * @return true if identifier may be used as an identifier in the rules language
     */
    public static boolean isValidIdentifier(String identifier) {
        if(identifier == null || identifier.length() <= 0) return false;
        boolean start = true;
        for(int ii = 0; ii < identifier.length(); ii++) {
            char ch = identifier.charAt(ii);
            if(!start) {
                if(!isIdentifierPart(ch)) return false;
            } else {
            	start = false;
                if(!isIdentifierStart(ch)) return false;
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
        if(path == null || path.length() <= 0) return String.valueOf(Folder.FOLDER_SEPARATOR_CHAR);
        if(path.charAt(path.length() - 1) == modelSep) path = path.substring(0, path.length() - 1);
        int lastIndex = path.lastIndexOf(modelSep);
        if(lastIndex < 0) return path;
        return path.substring(0, lastIndex + 1);
    }


    public static String getSMContextName(Entity entity) {
        return  entity.getName().toLowerCase();
    }


    public static String getSMContextName(String entityPath) {
        if (null == entityPath) {
            return null;
        }
        int indexOfChar = entityPath.lastIndexOf(Folder.FOLDER_SEPARATOR_CHAR);
        if (indexOfChar >=0) {
            entityPath = entityPath.substring(indexOfChar + 1);
        }
        indexOfChar = entityPath.lastIndexOf(".");
        if (indexOfChar >=0) {
            entityPath = entityPath.substring(0, indexOfChar);
        }
        return entityPath.toLowerCase();        
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
//        if (null == sm) {
//            return null;
//        }
        State  root = sm.getMachineRoot ();
        StateEntity  currentNode = state;
        while (currentNode != root && currentNode != null) {
            ruleName = "_" + currentNode.getName () + ruleName;
            currentNode = currentNode.getParent ();
        }//endwhile
        ruleName = state.getOwnerStateMachine ().getName () + ruleName;
        return ruleName;
    }

    public static String getTransitionRuleName(StateTransition trans) {
        return trans.getOwnerStateMachine().getName () + "_" + trans.getName();
    }
}

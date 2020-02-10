/**
 * @author ishaan
 * @version Jul 20, 2004, 12:05:12 PM
 */
package com.tibco.cep.designtime.model;

import java.util.Collection;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.parser.codegen.CGConstants;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;


/**
 * This class contains various methods that users of the the Model API will find useful.
 */
public class ModelUtils {


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
        return convertPathToPackage(path, Folder.FOLDER_SEPARATOR_CHAR);
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
            pack = pack.replace('.', Folder.FOLDER_SEPARATOR_CHAR);
            if(pack.charAt(0)!= Folder.FOLDER_SEPARATOR_CHAR) {
            	pack = Folder.FOLDER_SEPARATOR_CHAR + pack;
            }
        }

        return pack;
    }

    //limit on args is 255, longs and double count as two args and implicit this for methods counts as one
    public static boolean areConceptConstructorArgsOversize(Concept cept) {
        Collection<PropertyDefinition> properties = cept.getPropertyDefinitions(false);
        //+1 for extId
        int size = properties.size() + 1;
        if(size <= 127 ) return false;
        if(size > 255) return true;
        
        size = 1;
        for(PropertyDefinition propDef : properties) {
            if(propDef == null) size += 2;
            else if(propDef.isArray()) ++size;
            else size += fnArgSize(propDef.getType());
        }
        return size > 255;
    }
    
    public static int fnArgSize(int rdfTypesTypeId) {
        switch(rdfTypesTypeId) {
            case RDFTypes.LONG_TYPEID:
            case RDFTypes.DOUBLE_TYPEID:
                return 2;
            default:
                return 1;
        }
    }
}
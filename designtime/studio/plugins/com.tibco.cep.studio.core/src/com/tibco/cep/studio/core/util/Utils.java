package com.tibco.cep.studio.core.util;


import java.io.UnsupportedEncodingException;

import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.tibco.be.util.packaging.descriptors.DeploymentDescriptor;
import com.tibco.be.util.packaging.descriptors.NameValuePair;
import com.tibco.be.util.packaging.descriptors.impl.NameValuePairBoolean;
import com.tibco.be.util.packaging.descriptors.impl.NameValuePairImpl;
import com.tibco.be.util.packaging.descriptors.impl.NameValuePairInteger;
import com.tibco.be.util.packaging.descriptors.impl.NameValuePairPassword;
import com.tibco.be.util.packaging.descriptors.impl.NameValuePairs;
import com.tibco.objectrepo.object.SubstitutionVariable;
import com.tibco.util.StringUtilities;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 15, 2007
 * Time: 8:59:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class Utils {

	private final static String DECISION_MANAGER_ENABLE_FLAG = "ACTIVATE_BUSINESS_USER"; 
	
    private static NameValuePair getSubstitutionVariableAsNVPair(SubstitutionVariable var) {
        NameValuePair nvp = null;
        final String type = var.getType();
        final String name = var.getName();
        final String value = var.getValue();
        final String description = var.getDescription();
        final String constraint = var.getConstraint();
        final boolean isDeploymentSettable = var.isDeploymentSettable();
        if ("Boolean".equals(type)) {
            nvp = new NameValuePairBoolean(name, Boolean.valueOf(value).booleanValue(), description,
                    isDeploymentSettable);
        } else if ("Integer".equals(type)) {
            int intValue = 0;
        //    try {
            	if(!(value.isEmpty())&&value!=null){
            		intValue = Integer.parseInt(value);
            	}
        //    } catch (Exception ex) {
       //     }
            nvp = new NameValuePairInteger(name, intValue, description, isDeploymentSettable);
            if (constraint != null && !"".equals(constraint)) {
                try {
                    int range[] = parseRange(constraint);
                    if (range != null) {
                        ((NameValuePairInteger) nvp).setMinInclusive(new Integer(range[0]));
                        ((NameValuePairInteger) nvp).setMaxInclusive(new Integer(range[1]));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if ("Password".equals(type)) {
            nvp = new NameValuePairPassword(name, value.toCharArray(), description, isDeploymentSettable);
        } else {
            nvp = new NameValuePairImpl(name, value, description, isDeploymentSettable);
//            if (constraint != null && !"".equals(constraint)) { //todo?
//                final String enumeration[] = parseEnumeration(constraint);
//                final int max = enumeration.length;
//                for (int i = 0; i < max; i++) {
//                    ((NameValuePair) nvp).addEnumerationElement(enumeration[i]);
//                }
//            }
        }
        if (!isDeploymentSettable && !var.isServiceSettable()) {
            nvp.setConfigurableAtDeployment(false);
        }
        return nvp;
    }


    public static String[] getUrisAsStringArrayFromStringProperty(String encodedUris) {
        final List uris = new ArrayList();
        if (null == encodedUris) {
            return new String[0];

        } else {
            final StringTokenizer tokenizer = new StringTokenizer(encodedUris, ",", false);
            try {
                while (tokenizer.hasMoreTokens()) {
                    final String encodedURI = tokenizer.nextToken();
                    if (!"".equals(encodedURI)) {
                        final String uri = URLDecoder.decode(encodedURI, "UTF-16BE");
                        uris.add(uri);
                    }//if
                }//while
            } catch (UnsupportedEncodingException cannotHappen) {
                cannotHappen.printStackTrace();
            }//catch
            return (String[]) uris.toArray(new String[0]);
        }
    }


    public static DeploymentDescriptor getSubstitutionVariablesAsDescriptor(Collection substVars, String descriptorName) {
        NameValuePairs retVal = null;
        if (substVars.size() > 0) {
            retVal = new NameValuePairs(descriptorName);
            for (Iterator it = substVars.iterator(); it.hasNext();) {
                final SubstitutionVariable var = (SubstitutionVariable) it.next();
                retVal.addNameValuePair(getSubstitutionVariableAsNVPair(var));
            }
        }
        return retVal;
    }


    public static Map getUrisAsMapFromStringProperty(String urisString) {
        final Map uris = new HashMap();
        final StringTokenizer tokenizer = new StringTokenizer(urisString, ",", false);
        try {
            while (tokenizer.hasMoreTokens()) {
                final String encodedURI = tokenizer.nextToken();
                if (!"".equals(encodedURI)) {
                    final String uri = URLDecoder.decode(encodedURI, "UTF-16BE");
                    final StringTokenizer tk = new StringTokenizer(uri, "=");
                    if (tk.countTokens() == 2) {
                        uris.put(URLDecoder.decode(tk.nextToken(), "UTF-16BE"),
                                URLDecoder.decode(tk.nextToken(), "UTF-16BE"));
                    }
                }//if
            }//while
        } catch (UnsupportedEncodingException cannotHappen) {
            cannotHappen.printStackTrace();
        }//catch
        return uris;
    }

    private static String[] parseEnumeration(String constraint) {
        final List buf = Arrays.asList(StringUtilities.split(constraint, ","));
        final int max = buf.size();
        for (int i = 0; i < max; i++) {
            String s = ((String) buf.get(i)).trim();
            if ("".equals(s)) {
                buf.remove(i);
                i--;
            } else {
                buf.set(i, s);
            }
        }
        return (String[]) buf.toArray(new String[buf.size()]);
    }


    private static int[] parseRange(String constraint) {
        int retVal[] = null;
        constraint = constraint.trim();
        final String chunks[] = StringUtilities.split(constraint, "-");
        try {
            if (chunks.length == 2) {
                final int left = Integer.parseInt(chunks[0].trim());
                final int right = Integer.parseInt(chunks[1].trim());
                if (left < right) {
                    retVal = (new int[]{left, right});
                } else {
                    retVal = (new int[]{right, left});
                }
            }
        }
        catch (Exception ex) {
        }
        return retVal;
    }
    
    public interface Displayable {
		String displayString(Object o);
	}
    
    
    /**
	 * Converts an array of Objects into String.
	 */
	public static String toString(Object[] objects) {
		return toString(objects, 
			new Displayable(){ 
				public String displayString(Object o) { 
					if (o == null) return "null"; //$NON-NLS-1$
					return o.toString(); 
				}
			});
	}

	/**
	 * Converts an array of Objects into String.
	 */
	public static String toString(Object[] objects, Displayable renderer) {
		if (objects == null) return ""; //$NON-NLS-1$
		StringBuffer buffer = new StringBuffer(10);
		for (int i = 0; i < objects.length; i++){
			if (i > 0) buffer.append(", "); //$NON-NLS-1$
			buffer.append(renderer.displayString(objects[i]));
		}
		return buffer.toString();
	}

	/**
	 * @return
	 */
	public static boolean isStandaloneDecisionManger() {
		return Boolean.parseBoolean(System.getProperty(DECISION_MANAGER_ENABLE_FLAG));
	}
	

	/**
	 * Checking equality of 2 SWT objects.
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean equals(Object obj1, Object obj2) {
    	return equals(obj1, obj2, false);
    }
    
    public static boolean equals(Object obj1, Object obj2, boolean flag) {
    	if(obj1 == obj2)
            return true;
        if(obj1 != null && obj2 == null)
            return false;
        if(obj1 == null)
            return false;
        if((obj1 instanceof Comparable) && (obj2 instanceof Comparable) && obj1.getClass().isAssignableFrom(obj2.getClass()))
            return ((Comparable)obj1).compareTo(obj2) == 0;
        if((obj1 instanceof Comparable) && (obj2 instanceof Comparable) && obj2.getClass().isAssignableFrom(obj1.getClass()))
            return ((Comparable)obj2).compareTo(obj1) == 0;
        if(flag && obj1.getClass().isArray() && obj2.getClass().isArray()) {
            int i = Array.getLength(obj1);
            int j = Array.getLength(obj2);
            if(i != j)
                return false;
            for(int k = 0; k < i; k++) {
                boolean flag1 = equals(Array.get(obj1, k), Array.get(obj2, k));
                if(!flag1)
                    return false;
            }

            return true;
        } else {
            return obj1.equals(obj2);
        }
    }
}

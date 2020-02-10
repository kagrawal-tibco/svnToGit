package com.tibco.cep.kernel.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.tibco.cep.kernel.model.rule.Identifier;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 4:59:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class IdentifierUtil {

    static public String toString(Identifier[] idrs) {
        String ret = new String("[");

        if (idrs != null) {
            for(int i = 0; i < idrs.length; i++) {
                ret = ret + idrs[i].getName();
                if (i+1 < idrs.length) {
                    ret += ", ";
                }
            }
        }
        else {
            ret += " null ";
        }
        ret += "]";
        return ret;

    }

    static public String toString(Set s) {
        String ret = new String("[");

        if (s != null && s.size() > 0) {
            Iterator ite = s.iterator();
            int i = 0;
            while(ite.hasNext()) {
                ret = ret + ite.next();
                if(i+1 < s.size() )
                    ret += ", ";
                i++;
            }
        }
        else {
            ret += " null ";
        }
        ret += "]";
        return ret;
    }

    static public boolean contains(Identifier[] superSet, Identifier[] subSet) {
        if (subSet == null) {
            return true;
        }
        else if (superSet == null) {
            return false;
        }
        for(int i = 0; i < subSet.length; i++) {
            boolean contains = false;
            for(int j = 0; j < superSet.length; j++) {
                if (subSet[i].equals(superSet[j])){
                    contains = true;
                    break;
                }
            }
            if(!contains) {
                return false;
            }
        }
        return true;
    }

    static public Identifier[] notHave(Identifier[] set1, Identifier[] set2) {
        if (set2 == null) {
            return new Identifier[0];
        }
        else if (set1 == null) {
            return set2;
        }
        Vector v = new Vector();
        for(int i = 0; i < set2.length; i++) {
            boolean contains = false;
            for(int j = 0; j < set1.length; j++) {
                if (set2[i].equals(set1[j])){
                    contains = true;
                    break;
                }
            }
            if(!contains) {
                v.add(set2[i]);
            }
        }
        Identifier[] arr = new Identifier[v.size()];
        for(int i = 0; i < v.size(); i++) {
            arr[i] = (Identifier) v.get(i);
        }
        return arr;
    }

    static public Identifier[] notIn(Identifier[] set1, Identifier[] set2) {
        if (set2 == null) {
            return set1;
        }
        else if (set1 == null) {
            return new Identifier[0];
        }
        Vector v = new Vector();
        for(int i = 0; i < set1.length; i++) {
            boolean contains = false;
            for(int j = 0; j < set2.length; j++) {
                if(set1[i].equals(set2[j])) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                v.add(set1[i]);
            }
        }
        Identifier[] arr = new Identifier[v.size()];
        for(int i = 0; i < v.size(); i++) {
            arr[i] = (Identifier) v.get(i);
        }
        return arr;
    }

    static public boolean same(Identifier[] set1, Identifier[] set2) {
        if(notHave(set1, set2).length == 0 && notIn(set1, set2).length == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    static public Identifier[] commonIdentifier(Identifier[] set1, Identifier[] set2) {
        Vector v = new Vector();
        for (int i = 0; i < set1.length; i++) {
            for(int j = 0; j < set2.length; j++) {
                if(set1[i].equals(set2[j])) {
                    v.add(set1[i]);
                }
            }
        }
        Identifier[] arr = new Identifier[v.size()];
        for(int i = 0; i < v.size(); i++) {
            arr[i] = (Identifier) v.get(i);
        }
        return arr;
    }

    static public int numCommonIdentifier(Identifier[] set1, Identifier[] set2) {
        int num = 0;
        for (int i = 0; i < set1.length; i++) {
            for(int j = 0; j < set2.length; j++) {
                if(set1[i].equals(set2[j])) {
                    num++;
                }
            }
        }
        return num;
    }

    static public boolean containsAll(Identifier[] checkSet, Identifier[] set1, Identifier[] set2) {
        if(checkSet == null) {
            return true;
        }
        else if (set1 == null && set2 == null) {
            return false;
        }
        for(int i = 0; i < checkSet.length; i++) {
            boolean found = false;
            Identifier checkIdr = checkSet[i];
            for(int j = 0; set1 != null && j < set1.length; j++) {
                if(checkIdr.equals(set1[j])) {
                    found = true;
                    break;
                }
            }
            for(int k = 0; found == false && set2 != null && k < set2.length; k++) {
                if(checkIdr.equals(set2[k])) {
                    found = true;
                    break;
                }
            }
            if(!found) {
                return false;
            }
        }
        return true;
    }

    static public Identifier[] joinIdentifiers(Identifier[] set1, Identifier[] set2) {
        ArrayList result = new ArrayList();
        for(int i = 0; i < set1.length; i++) {
            result.add(set1[i]);
        }
        for(int j = 0; j < set2.length; j++) {
            if (!result.contains(set2[j])) {
                result.add(set2[j]);
            }
        }
        Identifier[] ret = new Identifier[result.size()];
        result.toArray(ret);
        return ret;
    }

    static public Identifier[] append(Identifier[] set1, Identifier[] set2) {
        Identifier[] ret = new Identifier[set1.length + set2.length];
        for(int i = 0; i < set1.length; i++) {
            ret[i] = set1[i];
        }
        for(int j = 0; j < set2.length; j++) {
            ret[set1.length + j] = set2[j];
        }
        return ret;
    }

    static public boolean matchOrder(Identifier[] subSet, Identifier[] fullSet) {
        for(int i = 0; i < subSet.length; i++) {
            if(!subSet[i].equals(fullSet[i])) return false;
        }
        return true;
    }

    static public int getIndex(Identifier[] idrs, Identifier findThis) {
        for(int i = 0; i < idrs.length; i++) {
            if(idrs[i].equals(findThis)) return i;
        }
        return -1;
    }

}

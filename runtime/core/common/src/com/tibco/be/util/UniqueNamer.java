/**
 * User: ishaan
 * Date: Apr 13, 2004
 * Time: 5:11:06 PM
 */
package com.tibco.be.util;

public class UniqueNamer {

    protected UniqueNamer() {
    }

    public static String generateUniqueName(String baseName, NameValidator validator) {
        int nextPostFix = 1;
        if(validator == null) return null;

        baseName = baseName.replace(' ', '_').replace('(', '_').replace(')', '_');

        if(validator.isNameUnique(baseName)) return baseName;

        String name = baseName;
        while(!validator.isNameUnique(name)) {
            name = baseName+ "_" + nextPostFix;
            nextPostFix++;
        }

        return name;
    }

    public interface NameValidator {
        public boolean isNameUnique(String name);
    }
}

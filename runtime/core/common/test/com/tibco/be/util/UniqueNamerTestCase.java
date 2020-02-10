/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Apr 14, 2004
 * Time: 11:54:16 AM
 * To change this template use Options | File Templates.
 */
package com.tibco.be.util;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;

public class UniqueNamerTestCase extends TestCase{

    public void setUp() {
        nameList = new ArrayList();
    }

    private ArrayList nameList;
    private UniqueNamer.NameValidator validator = new UniqueNamer.NameValidator() {
        public boolean isNameUnique(String name){
            for(int ii=0; ii<nameList.size(); ii++) {
                if(name.equals(nameList.get(ii))) return false;
            }
            return true;
        }
    };

    //have to test the testing code unfortunately
    public void testValidator(){
        assertTrue(validator.isNameUnique("empty array test"));

        nameList = new ArrayList(Arrays.asList(new String[]{"string1", "string2", "string3"}));
        assertTrue(validator.isNameUnique("unique"));
        assertFalse(validator.isNameUnique("string1"));
    }

    public void testUniqueName(){

        String simpleBaseName = "baseName";
        String parenBaseName = "baseName(1)";

        //test with an empty nameList
        String uniqueName = UniqueNamer.generateUniqueName(simpleBaseName, validator);
        assertTrue(simpleBaseName.equals(uniqueName));
        //test for uncessesarliy incrementing the paren
        uniqueName = UniqueNamer.generateUniqueName(parenBaseName, validator);
        assertTrue(parenBaseName.equals(uniqueName));

        String baseName = "basename";
        //add some initial values to nameList
        for(int ii=0; ii<50; ii++) {
            nameList.add(baseName + Integer.toString(ii));
        }

        for(int ii=0; ii<150; ii++){
            uniqueName = UniqueNamer.generateUniqueName(baseName, validator);

            //shouldn't append anything if the basename is already unique
            if(ii == 0) assertTrue(uniqueName.equals(baseName));

            assertTrue(validator.isNameUnique(uniqueName));
            assertFalse(hasMulitpleAddedParens(uniqueName));
            nameList.add(uniqueName);
        }
    }

    private boolean hasMulitpleAddedParens(String name) {
        return name.matches("\\(\\n*\\)\\w?\\(\\n*\\)$");
    }
}
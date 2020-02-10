package com.tibco.cep.pattern.dsl;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/*
* Author: Anil Jeswani / Date: Nov 16, 2009 / Time: 2:47:15 PM
*/

@Test(groups = {"InvalidPatternsTest", "All"}, sequential = true)
public class InvalidPatternsTest {

    static final String INVALID_PATTERNS_TEST_FILE =
            "r:\\dev\\be\\leo\\pattern\\pattern-lang\\test\\com\\tibco\\cep\\pattern\\dsl\\invalidpatterns.txt";
    static final String DELIMITER = ":";

    Map<String, String> patternsToTest = new LinkedHashMap<String, String>();

    @BeforeClass
    public void setUp() {
        try {
            int idx;
            String testpattern;
            BufferedReader br = new BufferedReader(new FileReader(INVALID_PATTERNS_TEST_FILE));
            while ((testpattern = br.readLine()) != null) {
                idx = testpattern.indexOf(DELIMITER);
                patternsToTest.put(testpattern.substring(0, idx), testpattern.substring(idx + 1));
            }
            assert br != null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMain() {
        for (String pattern : patternsToTest.keySet()) {
            PatternParserHelper def = new PatternParserHelper(pattern);
            try {
                def.parse();
                System.out.println("Definition is " + def);
                Assert.fail("Invalid pattern must throw an Exception");
            } catch (LanguageException e) {
                Assert.assertEquals(e.getMessage(), patternsToTest.get(pattern));
            }
        }
    }

    @AfterClass
    public void tearDown() {
        //todo
    }
}

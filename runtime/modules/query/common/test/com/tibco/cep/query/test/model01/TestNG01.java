package com.tibco.cep.query.test.model01;

import org.testng.annotations.*;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 3, 2007
 * Time: 10:04:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Test(groups = { "Model01" }, enabled = true )
public class TestNG01 {

  @BeforeClass
  public static void setupClass() {
    ppp("SETTING UP THE CLASS");
  }

  @AfterClass
  public static void tearDownClass1() {
    ppp("TEARING DOWN THE CLASS PART 1");
  }

  @AfterClass
  public static void tearDownClass2() {
    ppp("TEARING DOWN THE CLASS PART 2");
  }

  @BeforeTest
  public void beforeTestMethod() {
    ppp("BEFORE METHOD");
  }

  @AfterTest
  public void afterTestMethod() {
    ppp("AFTER METHOD");
  }

  @Test(groups = { "odd" })
  public void testMethod1() {
    ppp(".....  TESTING1");
  }

  @Test(groups = {"even"} )
  public void testMethod2() {
    ppp(".....  TESTING2");
  }

  @Test(groups = { "odd" })
  public void testMethod3() {
    ppp(".....  TESTING3");
  }

  @Test(groups = { "odd" }, enabled = false)
  public void testMethod5() {
    ppp(".....  TESTING5");
  }

  @Test(groups = { "broken" })
  public void testBroken() {
    ppp(".....  TEST BROKEN");
  }

  @Test(groups = { "fail" } , expectedExceptions = {NumberFormatException.class, ArithmeticException.class} )
  public void throwExpectedException1ShouldPass() {
    throw new NumberFormatException();
  }

  @Test(groups = { "fail" } ,expectedExceptions =  { NumberFormatException.class, ArithmeticException.class } )
  public void throwExpectedException2ShouldPass() {
    throw new ArithmeticException();
  }

  private static void ppp(String s) {
  System.out.println("[Test1] " + s);
}
}


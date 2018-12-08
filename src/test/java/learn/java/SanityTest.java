package learn.java;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class SanityTest {
	@BeforeClass
	public static void beforeClass() {
		System.out.println("***Before Class is invoked only once at the beginning of test case");
	}
	
	@AfterClass
	public static void afterClass() {
		System.out.println("***After Class is invoked at the end of test case");
	}

	@Before
	public void before() {
		System.out.println("____________________");
		System.out.println("\t Before is invoked at the beginning of each test");
	}

	@After
	public void after() {
		System.out.println("\t After is invoked at the end of each test");
		System.out.println("=================");
	}

	@Test
	public void someTest() {
		System.out.println("\t\t someTest is invoked");
	}

	@Test
	@Ignore
	public void someTest2() {
		System.out.println("\t\t someTest2 is invoked");
	}

	
}
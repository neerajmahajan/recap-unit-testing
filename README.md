# recap-unit-testing

##### Benefits of Unit Testing and Test Driven Development (TDD)
* In TDD we write code to pass the failing test, so it limits the code we write to only what is needed.
* Tests provide automated and fast regression of refactoring, bug fixes and enhancements.

##### Structure
* Unit tests are kept in the same project under src/test/java folder and are not packaged in code jar file.
* Test case for a class are kept in the same package, which allows to test default and protected access methods or properties. eg if my source class is in learn.java package, then my test class will also be in learn.java package, but in src/test/java folder.

#####  Best Practices
* It is good practice to create test classes with Test suffix. Some code coverage tools ignore tests if they don’t end with a Test suffix.
* Execution of test cases should be independent of each other.

##### Basic Annotations

* @Test
  * It represent a test method.
  * Any public method can be annotated with this.
* @Before
  * If we annotate any public void method of any name with @Before, then that method gets executed before every test execution
* @After
  *  Any method annotated with @After gets executed after each test method execution.
* @BeforeClass
  * The @BeforeClass annotated method is executed before the first test.
  * It's signature should be public static void
* @AfterClass
  * The @AfterClass annotated method is executed after the last test.
  * It's signature should be public static void

* @Test(expected=ClassName.class)
	* This is used when the test method execution expect an exception to be thrown. eg @Test(expected=RuntimeException.class)
*	@RunWith
	*	The @RunWith annotation is used to change the nature of the test class. It can be used to run a test as a parameterized test or even a Spring test, or it can be a Mockito runner to initialize the mock objects annotated with a @Mock annotation.
*	@Ignore("reason for ignoring")
	* It is used to ignore the test method by test runner.
	*	if we ignore a test, then the test report will tell you that something needs to be fixed as some tests are ignored.
	*	You can place the @Ignore annotation on a test class, effectively ignoring all the contained tests.
*	@FixMethodOrder
	* It is used to define custom test execution order. It is annotated at class level.
	* It can take following values.
		*	MethodSorters.JVM: This leaves the test methods in the order returned by the JVM. This order may vary from run to run.
		*	MethodSorters.NAME_ASCENDING: This sorts the test methods by the method name in the lexicographic order.
		*	MethodSorters.DEFAULT: This is the default value that doesn’t guarantee the execution order.
	* Eg @FixMethodOrder(MethodSorters.NAME_ASCENDING). This will execute the tests in ascending order, based on the test name.

*	@RunWith(Suite.class) and @Suite.SuiteClasses	
	* These annotations are used to run multiple test classes through a suite.
	*	@RunWith(Suite.class) tells to use suite runner
	* @SuiteClassess takes comma separated list of classess that needs to be executed. eg Suite.SuiteClasses({EmployeeTest.class,	InventoryTest.class})
	* A test suite is created for group-related tests such as a group of data access, API usage tests, or a group of input validation logic tests.
	
#####	Assert vs Assume
* Assert class provides various static methods for different validations.
* Assume class provide various static methods for making assumptions. If the the assumption is true, then the test is executed , otherwise ignored. Eg my test case validates build number which is provided by Jenkins server, and not available on local system. so here we can make use of Assume.
	


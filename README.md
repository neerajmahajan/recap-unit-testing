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

#####	Qualities of unit testing
*	Order independent.
*	Test should run on all computers.
*	A test should not take more that a second to finih it execution.
*	All dependencies should be mocked.

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
* @Test(timeout=10)
	* This will fail the test, if the execution is not completed in 10 milliseconds.
* @RunWith
	*	The @RunWith annotation is used to change the nature of the test class. It can be used to run a test as a parameterized test or even a Spring test, or it can be a Mockito runner to initialize the mock objects annotated with a @Mock annotation.
* @Ignore("reason for ignoring")
	* It is used to ignore the test method by test runner.
	*	if we ignore a test, then the test report will tell you that something needs to be fixed as some tests are ignored.
	*	You can place the @Ignore annotation on a test class, effectively ignoring all the contained tests.
* @FixMethodOrder
	* It is used to define custom test execution order. It is annotated at class level.
	* It can take following values.
		*	MethodSorters.JVM: This leaves the test methods in the order returned by the JVM. This order may vary from run to run.
		*	MethodSorters.NAME_ASCENDING: This sorts the test methods by the method name in the lexicographic order.
		*	MethodSorters.DEFAULT: This is the default value that doesn’t guarantee the execution order.
	* Eg @FixMethodOrder(MethodSorters.NAME_ASCENDING). This will execute the tests in ascending order, based on the test name.

* @RunWith(Suite.class) and @Suite.SuiteClasses	
	* These annotations are used to run multiple test classes through a suite.
	*	@RunWith(Suite.class) tells to use suite runner
	* @SuiteClassess takes comma separated list of classess that needs to be executed. eg Suite.SuiteClasses({EmployeeTest.class,	InventoryTest.class})
	* A test suite is created for group-related tests such as a group of data access, API usage tests, or a group of input validation logic tests.
	
#####	Assert vs Assume
* Assert class provides various static methods for different validations.
* Assume class provide various static methods for making assumptions. If the the assumption is true, then the test is executed , otherwise ignored. Eg my test case validates build number which is provided by Jenkins server, and not available on local system. so here we can make use of Assume.

###	Junit Rules
*	Rules are like Aspect Oriented Programming (AOP); we can do useful things before and/or after the actual test execution.
	*	Timeout rule : eg below rule will be executed for each of the test case and will fail the test if it is not executed within 20 milliseconds.
	``` 
		 @Rule
		 public Timeout globalTimeout = new Timeout(20, TimeUnit.MILLISECONDS);
	```
	*	ExpectedException Rule : It allows you to assert the expected exception type and the exception message. The expect object sets the expected exception class and expectMessage sets the expected message in the exception. If the message or exception class doesn’t match the rule’s expectation, the test fails.
	```
		@Rule
		public ExpectedException thrown= ExpectedException.none();		
		@Test
		public void throwsIllegalStateExceptionWithMessage() {
			thrown.expect(IllegalStateException.class);
			thrown.expectMessage("Is this a legal state?");
			throw new IllegalStateException("Is this a legal state?");
		}		
		```
	*	Temporary Folder Rule: The TemporaryFolder rule allows the creation of files and folders that are guaranteed to be deleted when the test method finishes (whether it passes or fails).
		```
		@Rule
		public TemporaryFolder folder = new TemporaryFolder();
		@Test
		public void testUsingTempFolder() throws IOException {
			File createdFile = folder.newFile("myfile.txt");
			File createdFolder = folder.newFolder("mysubfolder");
		}
		```
	*	ErrorCollector Rule: The ErrorCollector rule allows the execution of a test to continue after the first problem is found. In below example, none of the verification passes but the test still finishes its execution, and at the end, notifies all errors.
		```
		@Rule
		public ErrorCollector collector = new ErrorCollector();
		@Test
		public void fails_after_execution() {
			collector.checkThat("a", equalTo("b"));
			collector.checkThat(1, equalTo(2));
			collector.checkThat("ae", equalTo("g"));
		}
		```
	*	Verifier Rule:	Verifier allows to do assertions after the execution of each test.
		```
		private String errorMsg = null;
		@Rule
		public TestRule rule = new Verifier() {
		protected void verify() {
			assertNull("ErrorMsg should be null after each test execution",errorMsg);
		}
		};
		@Test
			public void testName() throws Exception {
			errorMsg = "Giving a value";
		}
		}
		```
	*	TestName Rule: It allows the test method name to be available inside test method.
		```
		public class TestNameRuleTest {
			@Rule
			public TestName name = new TestName();
			@Test
			public void testA() {
				assertEquals("testA", name.getMethodName());
			}
			@Test
			public void testB() {
				assertEquals("testB", name.getMethodName());
			}
		}
		```
	*	ExternalResource Rule:The ExternalResource rule provides a mechanism that makes resource handling a bit more convenient.
		```
		public class ExternalResourceTest {
			Resource resource;
			public @Rule TestName name = new TestName();
			public @Rule ExternalResource rule = new ExternalResource() {
			@Override protected void before() throws Throwable {
			resource = new Resource();
			resource.open();
			System.out.println(name.getMethodName());
			}
			@Override protected void after() {
			resource.close();
			System.out.println("\n");
			}
			};
			@Test
			public void someTest() throws Exception {
			System.out.println(resource.get());
			}
			@Test
			public void someTest2() throws Exception {
			System.out.println(resource.get());
			}
		}		
		```
	* Junit Categories :	The Categories runner runs only the classes and methods that are annotated with either the category given with the @IncludeCategory annotation or a subtype of that category.
		

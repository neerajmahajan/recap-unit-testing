# recap-unit-testing

##### Benefits of Unit Testing and Test Driven Development (TDD)
* In TDD we write code to pass the failing test, so it limits the code we write to only what is needed.
* Tests provide automated and fast regression of refactoring, bug fixes and enhancements.

##### Structure
* Unit tests are kept in the same project under src/test/java folder and are not packaged in code jar file.
* Test case for a class are kept in the same package, which allows to test default and protected access methods or properties. eg if my source class is in learn.java package, then my test class will also be in learn.java package, but in src/test/java folder.

#####  Best Practices
* It is good practice to create test classes with Test suffix. Some code coverage tools ignore tests if they don’t end with a Test suffix.

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

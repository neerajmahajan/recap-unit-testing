package learn.java;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.AtLeast;
import org.mockito.internal.verification.AtMost;
import org.mockito.internal.verification.Times;

public class EnvironmentMockWithAnnotationTest {

	@Mock
	Instance mockInstance;
	@Mock
	HostFinder mockHostFinder;
	@Mock
	Host mockHost;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		// If you don't want to initialise mocks using this, then use below
		// annotation at class level
		// @RunWith(MockitoJUnitRunner.class)
	}

	@Test
	public void testEnvironmentWithMockedDependencies() {
		Mockito.when(mockInstance.getHostName()).thenReturn("mockedHostName");
		Mockito.when(mockHostFinder.findByName(anyString())).thenReturn(mockHost);
		Environment env = new Environment();
		env.setInstance(mockInstance);
		env.setHostFinder(mockHostFinder);

		Assert.assertNotNull(env.getInstanceHost());

		// Ensuring when calls get executed when getInstanceHost is called. If
		// the method is not invoked, or the argument doesn’t match, then the
		// verify method will
		// raise an error to indicate that the code logic has issues
		Mockito.verify(mockInstance, new AtMost(1)).getHostName();

		// Mockito.verify(mockHostFinder).findByName(anyString());
		Mockito.verify(mockHostFinder, new AtLeast(1)).findByName(anyString());

		// Or Mockito.verify(mockHostFinder).findByName("mockedHostNamex");

		// for mock objects methods that return void, we don't need to use When
		// clause.
		Mockito.verify(mockHost, new Times(3)).returnNothing(isA(Location.class));

	}

	@Test
	public void testMockThrowException() {
		Mockito.when(mockInstance.getHostName()).thenThrow(new RuntimeException());
		Environment env = new Environment();
		env.setInstance(mockInstance);
		try {
			env.getInstanceHost();
		} catch (Exception e) {
			Assert.assertTrue(e instanceof RuntimeException);
		}

	}
	
	@Test public void test_verify_only() {
		Mockito.when(mockInstance.getHostName()).thenReturn("longHostName");
		Mockito.when(mockInstance.getHostShortName()).thenReturn("shn");
		Environment env = new Environment();
		env.setInstance(mockInstance);
		Assert.assertEquals("longHostName",env.getInstanceHostName());
		
		/* 
		 * only ensures that no other call than the specified call is made on the mock object. 
		   for eg in two different calls are made on mockinstance object,  when env.getInstanceHostName is called, then it will fail the test on verification.
		 */
		Mockito.verify(mockInstance,Mockito.only()).getHostName();
	}
	
	@Test
	public void test_verify_zero_interactions_for_mocked_object(){
		
		Mockito.when(mockHostFinder.findByName(anyString())).thenReturn(mockHost);
		Mockito.when(mockInstance.getHostName()).thenReturn("longHostName");
		
		Environment env = new Environment();
		env.setInstance(mockInstance);
		
		Assert.assertEquals("longHostName",env.getInstanceHostName());
		
		Mockito.verifyZeroInteractions(mockHostFinder);
		
	}

}

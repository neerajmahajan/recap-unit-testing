package learn.java;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.AtLeast;
import org.mockito.internal.verification.AtMost;
import org.mockito.internal.verification.Times;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class EnvironmentMockWithAnnotationTest {

	@Mock
	Instance mockInstance;
	@Mock
	HostFinder mockHostFinder;
	@Mock
	Host mockHost;
	@Mock
	Dependency mockDependency;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		// If you don't want to initialise mocks using this, then use below
		// annotation at class level
		// @RunWith(MockitoJUnitRunner.class)
	}

	@Test
	public void test_environment_with_mocked_dependencies() {
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

	@Test(expected = RuntimeException.class)
	public void test_mock_throw_exception() {
		Mockito.when(mockInstance.getHostName()).thenThrow(new RuntimeException());
		Environment env = new Environment();
		env.setInstance(mockInstance);
		env.getInstanceHost();
	}

	@Test
	public void test_verify_only() {
		Mockito.when(mockInstance.getHostName()).thenReturn("longHostName");
		Mockito.when(mockInstance.getHostShortName()).thenReturn("shn");
		Environment env = new Environment();
		env.setInstance(mockInstance);
		Assert.assertEquals("longHostName", env.getInstanceHostName());

		/*
		 * only ensures that no other call than the specified call is made on
		 * the mock object. for eg in two different calls are made on mock
		 * instance object, when env.getInstanceHostName is called, then it will
		 * fail the test on verification.
		 */
		Mockito.verify(mockInstance, Mockito.only()).getHostName();
	}

	@Test
	public void test_verify_zero_interactions_for_mocked_object() {

		Mockito.when(mockHostFinder.findByName(anyString())).thenReturn(mockHost);
		Mockito.when(mockInstance.getHostName()).thenReturn("longHostName");

		Environment env = new Environment();
		env.setInstance(mockInstance);

		Assert.assertEquals("longHostName", env.getInstanceHostName());

		Mockito.verifyZeroInteractions(mockHostFinder);

	}

	@Test(expected = RuntimeException.class)
	public void test_mocking_consective_methods_call_with_different_return() {
		Mockito.when(mockDependency.sum(isA(Integer.class), isA(Integer.class))).thenReturn(2).thenReturn(3)
				.thenReturn(4).thenThrow(new RuntimeException());

		Subject subject = new Subject();
		subject.setDependency(mockDependency);
		subject.callDependentMultipleTimes();

		Mockito.verify(mockDependency).sum(1, 1);
		Mockito.verify(mockDependency).sum(1, 2);
		Mockito.verify(mockDependency).sum(1, 3);
		Mockito.verify(mockDependency).sum(0, 0);
	}

	@Test
	public void test_answer_on_mock() {
		// Answer is used to provide dummy implementation for the invoked method
		// that can return values based on the passed arguments
		Mockito.when(mockDependency.sum(isA(Integer.class), isA(Integer.class))).thenAnswer(new Answer<Integer>() {

			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				int i = (Integer) invocation.getArguments()[0];
				int j = (Integer) invocation.getArguments()[1];

				if (i == 0 || j == 0) {
					new RuntimeException();
				}

				return i + j;
			}

		});

		Subject subject = new Subject();
		subject.setDependency(mockDependency);
		subject.callDependentMultipleTimes();

		Mockito.verify(mockDependency).sum(1, 1);
		Mockito.verify(mockDependency).sum(1, 2);
		Mockito.verify(mockDependency).sum(1, 3);
	}

	@Test
	public void test_spy() {
		// Spy is used to partially mock a object

		Orignal orignal = new Orignal();

		Orignal partialMock = Mockito.spy(orignal);

		Mockito.when(partialMock.doSquare(anyInt())).thenReturn(5);

		// This will call the real implementation... However it will internally
		// use mock method call defined above
		Assert.assertEquals(5, partialMock.square(2));

		Mockito.verify(partialMock).doSquare(anyInt());

	}

	@Test
	public void doReturn_usage_on_spy() throws Exception {
		List<String> list = new ArrayList<String>();
		List<String> spy = Mockito.spy(list);
		// impossible the real list.get(0) is called and fails
		// with IndexOutofBoundsException, as the list is empty
		//Mockito.when(spy.get(0)).thenReturn("not reachable");
		
		Mockito.doReturn("Neeraj").when(spy).get(0);
		
		Assert.assertEquals(spy.get(0), "Neeraj");
		Mockito.verify(spy).get(0);
	}

	/*
	 * anyInt(), anyDouble(), anyString(), anyList(), anyCollection(),
	 * isA(java.lang.Class<T> clazz), any(java.lang.Class<T> clazz), eq(T),
	 * eq(primitive value)
	 * 
	 * 
	 */

}

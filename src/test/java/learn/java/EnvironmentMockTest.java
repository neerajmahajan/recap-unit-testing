package learn.java;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.AtLeast;
import org.mockito.internal.verification.AtMost;
import org.mockito.internal.verification.Times;

public class EnvironmentMockTest {

	@Test
	public void testEnvironmentWithMockedDependencies() {

		Instance mockInstance = Mockito.mock(Instance.class);
		HostFinder mockHostFinder = Mockito.mock(HostFinder.class);
		Host mockHost = Mockito.mock(Host.class);

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

}

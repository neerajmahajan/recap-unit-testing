package learn.java;

import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TimeoutTest {
	@Rule
	public Timeout globalTimeout = new Timeout(40, TimeUnit.MILLISECONDS);

	@Test
	public void testInfiniteLoop1() throws InterruptedException {
		Thread.sleep(30);
	}

	@Test
	public void testInfiniteLoop2() throws InterruptedException {
		Thread.sleep(30);
	}
}
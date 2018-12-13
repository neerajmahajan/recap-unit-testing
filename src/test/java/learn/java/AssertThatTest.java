package learn.java;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class AssertThatTest {

	@Test
	public void testVerifyMatcher() {
		int age = 30;
		assertThat(age, equalTo(30));
		assertThat(age, is(30));
		assertThat(age, not(equalTo(33)));
		assertThat(age, is(not(33)));
	}

	@Test
	public void verify_multiple_values() throws Exception {
		double marks = 100.00;
		assertThat(marks, either(is(100.00)).or(is(90.9)));
		assertThat(marks, both(not(99.99)).and(not(60.00)));
		assertThat(marks, anyOf(is(100.00), is(1.00), is(55.00), is(88.00), is(67.8)));
		assertThat(marks, not(anyOf(is(0.00), is(200.00))));
		assertThat(marks, not(allOf(is(1.00), is(100.00), is(30.00))));
	}

}

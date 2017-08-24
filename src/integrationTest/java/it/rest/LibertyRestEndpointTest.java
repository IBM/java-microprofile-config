package it.rest;

import java.util.regex.Pattern;

import org.junit.Test;

import it.EndpointTest;

public class LibertyRestEndpointTest extends EndpointTest {

	@Test
	public void testDeployment() {
		testEndpoint("/rest", Pattern
				.compile(
						".*Hello from the MicroProfile Config demonstrator application!.*",
						Pattern.DOTALL));
	}
}

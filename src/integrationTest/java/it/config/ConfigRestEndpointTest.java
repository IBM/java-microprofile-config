package it.config;

import java.util.regex.Pattern;

import org.junit.Test;

import it.EndpointTest;

public class ConfigRestEndpointTest extends EndpointTest {

	@Test
	public void testValue() {
		testEndpoint("/rest/config",
				Pattern.compile("\\s*\\{\\s*" //
						+ "\"configSources\"\\s*:\\s*\\[" //
						+ ".*", Pattern.DOTALL),
				Pattern.compile(".*" //
						+ "\"name\"\\s*:\\s*\"Time Config Source\"\\s*,\\s*" //
						+ "\"ordinal\"\\s*:\\s*600\\s*,\\s*" //
						+ "\"properties\"\\s*:\\s*\\{\\s*" //
						+ "\"time.now\"\\s*:" //
						+ ".*", Pattern.DOTALL), //
				Pattern.compile(".*" //
						+ "\"name\"\\s*:\\s*\"System Properties Config Source\"\\s*,\\s*" //
						+ "\"ordinal\"\\s*:\\s*400\\s*,\\s*" //
						+ "\"properties\"\\s*:\\s*\\{" //
						+ ".*", Pattern.DOTALL), //
				Pattern.compile(".*" //
						+ "\"name\"\\s*:\\s*\"Environment Variables Config Source\"\\s*,\\s*" //
						+ "\"ordinal\"\\s*:\\s*300\\s*,\\s*" //
						+ "\"properties\"\\s*:\\s*\\{" //
						+ ".*", Pattern.DOTALL), //
				Pattern.compile(".*" //
						+ "\"name\"\\s*:\\s*\"Properties File Config Source: .*META-INF/microprofile-config\\.properties\"\\s*,\\s*" //
						+ "\"ordinal\"\\s*:\\s*100\\s*,\\s*" //
						+ "\"properties\"\\s*:\\s*\\{.*" //
						+ "\"foo\"\\s*:\\s*\"bar\"" //
						+ ".*", Pattern.DOTALL) //
		);
	}

	@Test
	public void testDynamicValue() {
		testEndpoint("/rest/config/time.now", Pattern.compile("\\s*" //
				+ "\\{\\s*\"time.now\"\\s*:\\s*\"\\d+\"\\s*\\}" //
				+ "\\s*", Pattern.DOTALL) //
		);
	}

	@Test
	public void testPresentValue() {
		testEndpoint("/rest/config/foo", Pattern.compile("\\s*" //
				+ "\\{\\s*\"foo\"\\s*:\\s*\"bar\"\\s*\\}" //
				+ "\\s*", Pattern.DOTALL) //
		);
	}

	@Test
	public void testNotPresentValue() {
		testEndpoint("/rest/config/missing", Pattern.compile("\\s*" //
				+ "\\{\\s*\\}" //
				+ "\\s*", Pattern.DOTALL) //
		);
	}
}

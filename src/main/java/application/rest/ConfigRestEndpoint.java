package application.rest;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("config")
@RequestScoped
public class ConfigRestEndpoint {
	// Get the Config based on all ConfigSources of the
	// current Thread Context ClassLoader (TCCL)
	@Inject
	private Config	config;
	@Inject
	@ConfigProperty(defaultValue = "false")
	private boolean	pretty;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response configValues() {
		// Alternate way to get Config object via static method call.
		// Config config = ConfigProvider.getConfig();

		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonArrayBuilder sources = factory.createArrayBuilder();
		config.getConfigSources().forEach(source -> {
			JsonObjectBuilder props = factory.createObjectBuilder();
			source.getProperties()
					.entrySet()
					.stream()
					.sorted(Entry.comparingByKey())
					.forEachOrdered(
							prop -> props.add(prop.getKey(), prop.getValue()));
			sources.add(factory.createObjectBuilder()
					// Add the name of the ConfigSource
					.add("name", source.getName())
					// Add the ordinal of the ConfigSource
					.add("ordinal", source.getOrdinal())
					// Add properties of the ConfigSource
					.add("properties", props));
		});
		JsonObject result = factory.createObjectBuilder()
				.add("configSources", sources)
				.build();

		return Response.ok(pretty(result)).build();
	}

	@GET
	@Path("{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response configValue(@PathParam("key") String key) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonObject result = config.getOptionalValue(key, String.class)
				.map(value -> builder.add(key, value))
				.orElse(builder)
				.build();

		return Response.ok(pretty(result)).build();
	}

	private Object pretty(JsonObject json) {
		if (!pretty) {
			return json;
		}
		StringWriter sw = new StringWriter();
		Map<String,Object> properties = new HashMap<>();
		properties.put(JsonGenerator.PRETTY_PRINTING, Boolean.TRUE);
		JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
		try (JsonWriter jsonWriter = writerFactory.createWriter(sw)) {
			jsonWriter.writeObject(json);
		}
		return sw.toString();
	}
}

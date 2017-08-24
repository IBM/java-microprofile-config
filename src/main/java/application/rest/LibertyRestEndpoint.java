package application.rest;

import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/")
@RequestScoped
public class LibertyRestEndpoint {

	@Inject
	@ConfigProperty(defaultValue = "FancyGreeting")
	private ResourceBundle	greeting;
	@Inject
	@ConfigProperty(name = "java.vm.name")
	private String			java_vm_name;
	@Inject
	@ConfigProperty(name = "java.specification.version")
	private float			java_specification_version;
	@Inject
	@ConfigProperty
	private Pattern			hotspot;
	@Inject
	@ConfigProperty(name = "time.now")
	private Provider<Long>	time;

	@SuppressWarnings("boxing")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() throws Exception {
		try (Formatter f = new Formatter()) {
			f.format("%s\n", greeting.getString("hello"));
			f.format("Running on Java VM %s\n", java_vm_name);
			f.format("Java specification version %.1f\n",
					java_specification_version);
			f.format("Is HotSpot VM? %s\n",
					hotspot.matcher(java_vm_name).matches());
			for (int i = 1; i <= 4; i++) {
				f.format("Time[%s] %tF %<tT.%<tL\n", i, time.get());
				Thread.sleep(100);
			}
			return f.toString();
		}
	}

}

package application.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
@ApplicationScoped
public class JaxrsApplication extends Application {
	// application
}

package application.converter;

import java.util.ResourceBundle;

import org.eclipse.microprofile.config.spi.Converter;

public class ResourceBundleConverter implements Converter<ResourceBundle> {

	@Override
	public ResourceBundle convert(String value) {
		return ResourceBundle.getBundle(value);
	}
}

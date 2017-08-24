package application.converter;

import java.util.regex.Pattern;

import org.eclipse.microprofile.config.spi.Converter;

public class PatternConverter implements Converter<Pattern> {

	@Override
	public Pattern convert(String value) {
		return Pattern.compile(value);
	}
}

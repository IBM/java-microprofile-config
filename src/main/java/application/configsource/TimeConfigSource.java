package application.configsource;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

public class TimeConfigSource implements ConfigSource {
	private final Map<String,String> map = new TimeMap();

	@Override
	public Map<String,String> getProperties() {
		return map;
	}

	@Override
	public String getValue(String propertyName) {
		return map.get(propertyName);
	}

	@Override
	public String getName() {
		return "Time Config Source";
	}

	@Override
	public int getOrdinal() {
		return 600;
	}

	static class TimeMap extends AbstractMap<String,String> {
		private final Set<Entry<String,String>> entries = Collections
				.singleton(new TimeEntry());

		@Override
		public Set<Entry<String,String>> entrySet() {
			return entries;
		}
	}

	static class TimeEntry implements Entry<String,String> {
		@Override
		public String getKey() {
			return "time.now";
		}

		@Override
		public String getValue() {
			return Long.toString(System.currentTimeMillis());
		}

		@Override
		public String setValue(String value) {
			throw new UnsupportedOperationException();
		}
	}
}

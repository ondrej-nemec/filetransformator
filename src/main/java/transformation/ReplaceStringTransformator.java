package transformation;

import java.util.Map;

import transform.LineTransformator;

public class ReplaceStringTransformator implements LineTransformator {

	private Map<String, String> map;
	
	private boolean regex;
	
	public ReplaceStringTransformator(Map<String, String> map, boolean isItRegex) {
		this.map = map;
		this.regex = isItRegex;
	}
	
	public ReplaceStringTransformator(Map<String, String> map) {
		this.map = map;
		this.regex = false;
	}
	
	@Override
	public String updateLine(final String line) {
		String result = line;
		for (String key : map.keySet()) {
			if (regex) {
				result = result.replaceAll(key, map.get(key));
			} else {
				result = result.replace(key, map.get(key));
			}
		}
		return result;
	}

}

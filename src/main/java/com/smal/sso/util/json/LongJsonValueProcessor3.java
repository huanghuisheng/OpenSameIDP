package com.smal.sso.util.json;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class LongJsonValueProcessor3 implements JsonValueProcessor {
	
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	private Object process(Object value) {
		if(value==null){
			return "";
		}
		return value.toString();
	}

}

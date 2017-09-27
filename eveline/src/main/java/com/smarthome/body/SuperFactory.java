package com.smarthome.body;

import java.util.HashMap;
import java.util.Map;

public abstract class SuperFactory {
	public Map<String,Integer> parseSingleByte(MDProtocol m){
		m.b2o();
		Map<String,Byte> r = new HashMap<String,Byte>();
		r.put("code", m.ctn[0]);
		return null;
	}
}

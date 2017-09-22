package com.smarthome.body;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import com.smarthome.head.SmartHomeConstant;

public final class SocketProtocolFactory {

	public static MDProtocol gen0x11(boolean isOn) throws Exception {
		Map<Integer, Boolean> p = new HashMap<Integer, Boolean>();
		p.put(0, isOn);
		return gen0x11(p, null, null);
	}

	/**
	 * 0x11
	 * 
	 * 控制插座的开关,锁,掩码
	 * 
	 * @param powers
	 *            开关 不可空
	 * @param locks
	 *            锁 可空
	 * @param masks
	 *            掩码 可空
	 * @return
	 */
	public static MDProtocol gen0x11(Map<Integer, Boolean> powers, Map<Integer, Boolean> locks, Map<Integer, Boolean> masks) throws Exception {
		if (powers == null || powers.size() == 0)
			throw new IllegalArgumentException("The parameter 'powers' is null or empty.");
		MDProtocol o = new MDProtocol();
		o.cmd = SmartHomeConstant.CMD.DEVICE_CONTROL_CHANGE;
		byte power = 0;
		for (Map.Entry<Integer, Boolean> e : powers.entrySet()) {
			power |= (e.getValue() ? 1 : 0) << e.getKey();
		}
		byte lock = 0;
		if (locks != null)
			for (Map.Entry<Integer, Boolean> e : locks.entrySet()) {
				lock |= (e.getValue() ? 1 : 0) << e.getKey();
			}
		byte mask = 0;
		if (masks != null)
			for (Map.Entry<Integer, Boolean> e : masks.entrySet()) {
				mask |= (e.getValue() ? 1 : 0) << e.getKey();
			}
		ByteBuffer bb = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
		bb.put(power);
		bb.put(lock);
		bb.put(mask);
		bb.put((byte) 0);
		o.ctn = bb.array();
		o.o2b();
		return o;
	}

	public Map<String, Object> parse0x30(MDProtocol o) {
		Map<String, Object> r = new HashMap<String, Object>();
		byte powerField = (byte) (o.ctn[0] & 0xff);
		byte lockField = (byte) (o.ctn[1] & 0xff);
		Map<Integer, Boolean> powers = new HashMap<Integer, Boolean>();
		Map<Integer, Boolean> locks = new HashMap<Integer, Boolean>();
		for (int i = 0; i < 8; i++) {
			powers.put(i, ((powerField >> i) & 0x01) == 1);
		}
		for (int i = 0; i < 8; i++) {
			locks.put(i, ((lockField >> i) & 0x01) == 1);
		}
		byte temperature = o.ctn[5];
		int current = 0x00ffff & ((o.ctn[6] & 0xff) | ((o.ctn[7] << 8) & 0xff00));
		int voltage = 0x00ffff & ((o.ctn[8] & 0xff) | ((o.ctn[9] << 8) & 0xff00));
		int watts = 0x00ffff & ((o.ctn[10] & 0xff) | ((o.ctn[11] << 8) & 0xff00));
		r.put("temperature", temperature);
		r.put("current", current);
		r.put("voltage", voltage);
		r.put("watts", watts);
		return r;
	}

}

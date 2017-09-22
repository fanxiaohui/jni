package com.smarthome.body;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Mobile-Device Protocol
 * 
 * @author juliana
 *
 */
public class MDProtocol {
	public final static byte b1 = 0x6d;
	public final static byte b2 = 0x64;
	public byte len;
	public byte version;
	public byte reserved;
	public byte cmd;
	public byte chk;
	public byte ctn[];
	public byte end[];// 最终报文

	public MDProtocol(){}
	public MDProtocol(byte[] _data) {
		this.end = _data;
	}

	/**
	 * pack
	 * 
	 * @return
	 */
	public void o2b() throws Exception {
		if (ctn.length > (0x00ff - 2)) {
			throw new Exception("ctn's length exceeds.");
		}
		ByteBuffer bb = ByteBuffer.allocate(7 + ctn.length);
		bb.put(b1);
		bb.put(b2);
		bb.put((byte) (ctn.length + 4));
		bb.put(version);
		bb.put(reserved);
		bb.put(cmd);
		bb.put(ctn);
		bb.put(check(bb.array(), 2, bb.array().length));
		end = bb.array();
	}

	public void b2o() {
		if (end.length < 1) {
			return;
		}
		ByteBuffer byteBuffer = ByteBuffer.wrap(end).order(ByteOrder.LITTLE_ENDIAN);
		byte head_1 = byteBuffer.get(0);
		byte head_2 = byteBuffer.get(1);
		if (head_1 != b1 && head_2 != b2) {
			return;
		}
		len = byteBuffer.get(2);
		if ((end.length - 2 - 1) != (len & 0xff)) {
			return;
		}
		version = byteBuffer.get(3);// 版本号
		reserved = byteBuffer.get(4);// 预留
		cmd = byteBuffer.get(5);// 命令字
		chk = byteBuffer.get(end.length - 1);// 校验字
		end[end.length - 1] = 0;
		if (chk != check(end, 2, end.length)) {
			return;
		}
		ctn = new byte[len - 4];
		System.arraycopy(end, 6, ctn, 0, ctn.length);
	}

	private static byte check(byte[] data, int s, int length) {
		int check = 0;
		for (int i = s; i < length; i++) {
			check += data[i] & 0xff;
			check &= 0xff;
		}
		return (byte) (check & 0xff);
	}
}

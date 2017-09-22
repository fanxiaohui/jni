package com.vision.util;

import java.math.BigInteger;

public class MiscUtils {
	public static byte[] fromHex(String hex) {
		byte[] bytes = new byte[hex.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
	}

	public static String toHex(byte... bytes) {
		if(bytes==null||bytes.length==0){
			return "";
		}
		BigInteger bi = new BigInteger(1, bytes);
		String hex = bi.toString(16);
		int paddingLength = (bytes.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

	public static String toHex(byte[] bytes, int b, int e) {
		byte[] aim = new byte[e - b];
		System.arraycopy(bytes, b, aim, 0, e - b);

		BigInteger bi = new BigInteger(1, aim);
		String hex = bi.toString(16);
		int paddingLength = (aim.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}
	public static int copyright(String cp) {
		String[] ns = cp.split("\\.");
		int n = 0;
		int p = 1000000000;
		for (String s : ns) {
			System.out.println(s);
			n += (Integer.parseInt(s) * (p /= 100));
			System.out.println(n);

		}
		return n;
	}

	public static byte crc(byte[] ctn) {
		int headerCheck = 0;
		for (int i = 0; i < ctn.length; i++) {
			headerCheck ^= ctn[i];
			for (int n = 8; n > 0; n--) {
				if ((headerCheck & 0x80) != 0) {
					headerCheck = (headerCheck << 1) ^ 0x31;
				} else {
					headerCheck = (headerCheck << 1);
				}
			}
		}
		return (byte) headerCheck;
	}

	public static byte crc(byte[] ctn, int l) {
		int headerCheck = 0;
		for (int i = 0; i < l; i++) {
			headerCheck ^= ctn[i];
			for (int n = 8; n > 0; n--) {
				if ((headerCheck & 0x80) != 0) {
					headerCheck = (headerCheck << 1) ^ 0x31;
				} else {
					headerCheck = (headerCheck << 1);
				}
			}
		}
		return (byte) headerCheck;
	}

	public static byte[] swapBytes(byte[] aim) {
		for (int i = 0; i < aim.length / 2; i++) {
			byte o = aim[i];
			aim[i] = aim[aim.length - 1 - i];
			aim[aim.length - 1 - i] = o;
		}
		return aim;
	}
}

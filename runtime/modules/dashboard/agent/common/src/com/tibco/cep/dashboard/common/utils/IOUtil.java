package com.tibco.cep.dashboard.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IOUtil {
	
	public static byte[] toByteArray(Object obj) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		byte[] bao;

		oos.writeObject(obj);
		bao = baos.toByteArray();
		oos.close();
		return bao;
	}

	public static String toHexString(Object obj) throws Exception {
		if (obj == null)
			return "";

		byte[] bao = IOUtil.toByteArray(obj);
		String str = HexUtil.bytesToHex(bao);
		return str;
	}

	public static Object fromByteArray(byte[] bao) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bao);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object obj = ois.readObject();
		return obj;
	}

	public static Object fromHexString(String str) throws Exception {
		byte[] bao = HexUtil.hexToBytes(str);
		Object obj = IOUtil.fromByteArray(bao);
		return obj;
	}

	public static int serializeLongLE(long value, byte[] outbuf, int offset) {
		outbuf[offset++] = (byte) (value);
		outbuf[offset++] = (byte) (value >> 8);
		outbuf[offset++] = (byte) (value >> 16);
		outbuf[offset++] = (byte) (value >> 24);
		outbuf[offset++] = (byte) (value >> 32);
		outbuf[offset++] = (byte) (value >> 40);
		outbuf[offset++] = (byte) (value >> 48);
		outbuf[offset++] = (byte) (value >> 56);

		// Return next offset.
		return offset;
	}

	public static long deserializeLongLE(byte[] inbuf, int offset) {
		return (inbuf[offset + 7] & 0xff) << 56 | (inbuf[offset + 6] & 0xff) << 48 | (inbuf[offset + 5] & 0xff) << 40 | (inbuf[offset + 4] & 0xff) << 32 | (inbuf[offset + 3] & 0xff) << 24
				| (inbuf[offset + 2] & 0xff) << 16 | (inbuf[offset + 1] & 0xff) << 8 | (inbuf[offset] & 0xff);
	}

	public static int serializeIntLE(int value, byte[] outbuf, int offset) {
		outbuf[offset++] = (byte) (value);
		outbuf[offset++] = (byte) (value >> 8);
		outbuf[offset++] = (byte) (value >> 16);
		outbuf[offset++] = (byte) (value >> 24);

		// Return next offset.
		return offset;
	}

	public static int deserializeIntLE(byte[] inbuf, int offset) {
		return (inbuf[offset + 3]) << 24 | (inbuf[offset + 2] & 0xff) << 16 | (inbuf[offset + 1] & 0xff) << 8 | (inbuf[offset] & 0xff);
	}

	public static int serializeShortLE(short value, byte[] outbuf, int offset) {
		outbuf[offset++] = (byte) (value);
		outbuf[offset++] = (byte) (value >> 8);

		// Return next offset.
		return offset;
	}

	public static short deserializeShortLE(byte[] inbuf, int offset) {
		return (short) ((inbuf[offset + 1] & 0xff) << 8 | (inbuf[offset] & 0xff));
	}

}

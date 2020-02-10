package com.tibco.cep.dashboard.common.utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.dgc.VMID;
import java.util.Random;

public class SUID implements Serializable {

	private static final long serialVersionUID = -728363684063329995L;

	// Constant
	public static final int sSuidLength = 16;
	private static long sSeed = System.currentTimeMillis();
	private static long sRunningCounter = (new Random(sSeed * 7317971)).nextLong();
	private static Random sRandom = null;
	// private static long sSequence = 1;

	// Atributes
	private byte[] mBytes = new byte[sSuidLength];
	private Integer mHashCode = new Integer(0);
	private String mStr = null;

	public static void updateSeed(long value) {
		sSeed ^= value;
	}

	public static void updateSeed(int value) {
		sSeed ^= (long) value;
	}

	public static void updateSeed(int value1, int value2) {
		sSeed ^= ((((long) value1) << 32) | (long) value2);
	}

	public static void updateSeed(String str) {
		long nVal8 = 0;

		for (int i = 0; i < str.length(); i++) {
			if ((i + 1) % 8 != 0) {
				nVal8 = (nVal8 << 8) | (byte) str.charAt(i);
			} else {
				sSeed ^= nVal8;
				nVal8 = 0;
			}
		}

		if (nVal8 != 0)
			sSeed ^= nVal8;
	}

	public static SUID createId() {
		SUID uuid = new SUID();

		try {
			// uuid.createFromSequence();
			uuid.createFromVMID();
		} catch (Exception e) {
			uuid.createFromRand();
		}

		return uuid;
	}

	public static SUID createFromHex(String hexSUID) throws Exception {
		SUID uuid = new SUID();
		uuid.fromHexString(hexSUID);
		return uuid;
	}

	// Create an empty SUID.
	private SUID() {
		mBytes = new byte[sSuidLength];
	}

	private void createFromVMID() {
		VMID vmid = new java.rmi.dgc.VMID();
		vmid2SUID(vmid.toString());
		computeHashCode();
	}

	private void createFromRand() {
		rand2SUID();
		computeHashCode();
	}

	// private void createFromSequence() {
	// long2SUID(sSequence++, 0, 0, 0);
	// computeHashCode();
	// }

	public void setSUID(byte[] SUIDBytes) {
		System.arraycopy(SUIDBytes, 0, mBytes, 0, sSuidLength);
		mStr = null;
		computeHashCode();
	}

	public byte[] getSUID() {
		// The caller better not modified it.
		return mBytes;
	}

	public Integer getHashCode() {
		return mHashCode;
	}

	public boolean equals(SUID b) {
		for (int i = 0; i < sSuidLength; i++) {
			if (mBytes[i] != b.mBytes[i])
				return false;
		}
		return true;
	}

	private void computeHashCode() {
		int hashedValue;
		int value;
		int v1, v2, v3, v4;

		v1 = (((int) mBytes[0]) < 0 ? ((int) mBytes[0]) + 256 : ((int) mBytes[0]));
		v2 = (((int) mBytes[1]) < 0 ? ((int) mBytes[1]) + 256 : ((int) mBytes[1]));
		v3 = (((int) mBytes[2]) < 0 ? ((int) mBytes[2]) + 256 : ((int) mBytes[2]));
		v4 = (((int) mBytes[3]) < 0 ? ((int) mBytes[3]) + 256 : ((int) mBytes[3]));
		hashedValue = (v1 << 24) | (v2 << 16) | (v3 << 8) | (v4);

		for (int i = 4; i < sSuidLength; i += 4) {
			v1 = (((int) mBytes[i + 0]) < 0 ? ((int) mBytes[i + 0]) + 256 : ((int) mBytes[i + 0]));
			v2 = (((int) mBytes[i + 1]) < 0 ? ((int) mBytes[i + 1]) + 256 : ((int) mBytes[i + 1]));
			v3 = (((int) mBytes[i + 2]) < 0 ? ((int) mBytes[i + 2]) + 256 : ((int) mBytes[i + 2]));
			v4 = (((int) mBytes[i + 3]) < 0 ? ((int) mBytes[i + 3]) + 256 : ((int) mBytes[i + 3]));
			value = (v1 << 24) | (v2 << 16) | (v3 << 8) | (v4);
			hashedValue ^= value;
		}

		mHashCode = new Integer(hashedValue);
	}

	public int getSize() {
		return sSuidLength;
	}

	public void copy(byte[] SUIDBytes) {
		System.arraycopy(SUIDBytes, 0, mBytes, 0, sSuidLength);
		mStr = null;
		computeHashCode();
	}

	private void vmid2SUID(String vmid) {
		vmid = System.currentTimeMillis() + ":" + (++sRunningCounter) + ":" + vmid;
		for (int i = 0, j = 0; i < vmid.length(); i++) {
			if (i < sSuidLength)
				mBytes[j] = (byte) vmid.charAt(i);
			else
				mBytes[j] ^= (byte) vmid.charAt(i);
			j++;
			if (j >= sSuidLength)
				j = 0;
		}
		mStr = null;
	}

	// Backup method for generating SUID.
	private void rand2SUID() {
		if (sRandom == null)
			sRandom = new Random(sSeed);

		long d1 = sRandom.nextLong();
		long d2 = System.currentTimeMillis() ^ (++sRunningCounter);
		long d3 = sRandom.nextLong();
		long d4 = sRandom.nextLong();
		long2SUID(d1, d2, d3, d4);
	}

	private void long2SUID(long d1, long d2, long d3, long d4) {
		int offset = 0;

		mBytes[offset++] = (byte) (d1);
		mBytes[offset++] = (byte) (d1 >> 8);
		mBytes[offset++] = (byte) (d1 >> 16);
		mBytes[offset++] = (byte) (d1 >> 24);
		mBytes[offset++] = (byte) (d2);
		mBytes[offset++] = (byte) (d2 >> 8);
		mBytes[offset++] = (byte) (d2 >> 16);
		mBytes[offset++] = (byte) (d2 >> 24);
		mBytes[offset++] = (byte) (d3);
		mBytes[offset++] = (byte) (d3 >> 8);
		mBytes[offset++] = (byte) (d3 >> 16);
		mBytes[offset++] = (byte) (d3 >> 24);
		mBytes[offset++] = (byte) (d4);
		mBytes[offset++] = (byte) (d4 >> 8);
		mBytes[offset++] = (byte) (d4 >> 16);
		mBytes[offset++] = (byte) (d4 >> 24);
	}

	public String toString() {
		if (mStr == null) {
			mStr = generateString();
		}
		return mStr;
	}

	private String generateString() {
		StringBuffer buf = new StringBuffer();

		HexUtil.bytesToHex(mBytes, 0, 16, buf);
		return buf.toString();
	}

	public String toHexString() {
		StringBuffer buf = new StringBuffer();

		HexUtil.bytesToHex(mBytes, 0, mBytes.length, buf);
		return buf.toString();
	}

	public void fromHexString(String hexValue) throws Exception {
		HexUtil.hexToBytes(hexValue, mBytes, 0);
		mStr = null;
		computeHashCode();
	}

	private void writeObject(java.io.ObjectOutputStream oos) throws IOException {
		// Use latest version to serialize.
		serialize1(oos);
	}

	private void readObject(java.io.ObjectInputStream ois) throws IOException, ClassNotFoundException {
		int version = ois.readInt();
		switch (version) {
			case 1:
				deserialize1(ois);
				break;

			default:
				throw new IOException("Fail to deserialize datafile.  Unknown verison.");
		}
		mStr = null;
		computeHashCode();
	}

	private void serialize1(ObjectOutputStream oos) throws IOException {
		// Write version 1
		oos.writeInt(1);

		oos.writeObject(mBytes);
	}

	private void deserialize1(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		mBytes = (byte[]) ois.readObject();
	}

	// Unit test
	public static void main(String[] args) {
		for (int i = 0; i < 50; i++)
			System.err.println("Created ID : " + createId());
	}
}

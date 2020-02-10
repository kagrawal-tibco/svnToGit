package com.tibco.cep.dashboard.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DigestUtil {

	private static final String PASS_PREFIX = "PASS:";

	private static HashMap<String, LinkedList<MessageDigest>> sFreeDigests = new HashMap<String, LinkedList<MessageDigest>>();

	/**
	 * Allocate a MessageDigest object. Reuse one from the free list if
	 * possible. The object should be freed once it's done.
	 * 
	 * @param hashAlgorithm
	 *            MD5, SHA, SHA-1, SHA-255, SHA-384, SHA-512
	 * @return MessageDigest
	 * @throws NoSuchAlgorithmException 
	 */
	public static MessageDigest allocateDigest(String hashAlgorithm) throws NoSuchAlgorithmException {
		synchronized (sFreeDigests) {
			LinkedList<MessageDigest> freeList = sFreeDigests.get(hashAlgorithm);

			if (freeList == null) {
				freeList = new LinkedList<MessageDigest>();
				sFreeDigests.put(hashAlgorithm, freeList);
			}

			if (freeList.size() > 0)
				return freeList.removeFirst();

			return MessageDigest.getInstance(hashAlgorithm);
		}
	}

	/**
	 * Free a MessageDigest object. It's reset and put back to the freelist.
	 * 
	 * @param digest
	 */
	public static void freeDigest(MessageDigest digest) {
		synchronized (sFreeDigests) {
			LinkedList<MessageDigest> freeList = sFreeDigests.get(digest.getAlgorithm());

			if (freeList == null) {
				freeList = new LinkedList<MessageDigest>();
				sFreeDigests.put(digest.getAlgorithm(), freeList);
			}

			if (freeList.size() < 100) {
				digest.reset();
				freeList.add(digest);
			}
		}
	}

	/**
	 * Compute a message digest.
	 * 
	 * @param hashAlgorithm
	 *            MD5, SHA, SHA-1, SHA-255, SHA-384, SHA-512
	 * @param data
	 *            [] original data
	 * @param offset
	 *            offset to start compute the digest
	 * @param length
	 *            length of the data to be used for computation.
	 * @return digest.
	 */
	public static byte[] makeDigest(String hashAlgorithm, byte data[], int offset, int length) throws NoSuchAlgorithmException {
		if (data == null || offset < 0 || length < 0 || (offset + length) > data.length)
			throw new IllegalArgumentException();

		MessageDigest md = allocateDigest(hashAlgorithm);
		md.update(data, offset, length);
		byte[] digest = md.digest();
		freeDigest(md);
		return digest;
	}

	public static String makeDigest(String originalStr) throws NoSuchAlgorithmException {
		return makeDigest("MD5", originalStr);
	}

	public static String makeDigest(String hashAlgorithm, String originalStr) throws NoSuchAlgorithmException {
		if (originalStr == null)
			throw new IllegalArgumentException();

		byte[] data = string2Bytes(originalStr);
		byte[] digest = makeDigest(hashAlgorithm, data, 0, data.length);
		return HexUtil.bytesToHex(digest, 0, digest.length);
	}

	public static byte[] makeAsciiDigest(String hashAlgorithm, String originalStr) throws NoSuchAlgorithmException {
		if (originalStr == null)
			throw new IllegalArgumentException();

		byte[] data = asciiString2Bytes(originalStr);
		return makeDigest(hashAlgorithm, data, 0, data.length);
	}

	public static String makeDigestOfList(String hashAlgorithm, List<?> strs, int length) throws NoSuchAlgorithmException {
		if (strs == null || length <= 0)
			throw new IllegalArgumentException();

		MessageDigest md = allocateDigest(hashAlgorithm);
		for (Iterator<?> i = strs.iterator(); i.hasNext();) {
			md.update(DigestUtil.string2Bytes(i.next().toString()));
		}

		byte[] digest = md.digest();
		freeDigest(md);

		if (digest.length > length) {
			byte[] digest2 = new byte[length];

			for (int i = 0, j = 0; i < digest.length; i++, j++) {
				if (j >= length)
					j = 0;
				digest2[j] ^= digest[i];
			}
			digest = digest2;
		}

		return HexUtil.bytesToHex(digest, 0, digest.length);
	}

	public static String makePasswordDigest(String userId, String password) throws NoSuchAlgorithmException {
		if (userId == null)
			throw new IllegalArgumentException();

		if (password == null)
			password = "";

		// Compute a digest.
		String userpass = userId.toUpperCase() + ":" + password;
		String passwordDigest = PASS_PREFIX + makeDigest("MD5", userpass);
		return passwordDigest;
	}

	public static boolean verifyPasswordDigest(String userId, String password, String passwordDigest) throws NoSuchAlgorithmException {
		if (userId == null || passwordDigest == null)
			throw new IllegalArgumentException();

		if (password == null)
			password = "";

		if (passwordDigest.startsWith(PASS_PREFIX)) {
			// passwordDigest is an encrypted digest.
			String userpass = userId.toUpperCase() + ":" + password;
			String passwordDigest2 = PASS_PREFIX + makeDigest("MD5", userpass);
			return passwordDigest.equals(passwordDigest2);
		} else {
			// passwordDigest is a plain password.
			return password.equals(passwordDigest);
		}
	}

	public static byte[] string2Bytes(String str) {
		if (str == null)
			throw new IllegalArgumentException();

		byte[] data = new byte[str.length() << 1];
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			data[(i << 1)] = (byte) (ch & 0xFF);
			data[(i << 1) + 1] = (byte) ((ch >> 8) & 0xFF);
		}
		return data;
	}

	public static byte[] asciiString2Bytes(String str) {
		if (str == null)
			throw new IllegalArgumentException();

		byte[] data = new byte[str.length()];
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			data[i] = (byte) (ch & 0xFF); // only take the low byte.
		}
		return data;
	}

	public static StringBuffer foldBytesTo2Digits(byte[] digest) {
		if (digest == null)
			throw new IllegalArgumentException();

		byte oneByte[] = new byte[1];

		oneByte[0] = 0;
		for (int i = 0; i < digest.length; i++) {
			oneByte[0] ^= digest[i];
		}

		StringBuffer hexStrBuf = new StringBuffer();

		HexUtil.bytesToHex(oneByte, 0, 1, hexStrBuf);
		return hexStrBuf;
	}

}

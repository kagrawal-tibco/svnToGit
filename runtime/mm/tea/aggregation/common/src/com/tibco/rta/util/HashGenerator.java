package com.tibco.rta.util;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {


	private HashGenerator() {
	}

	public static String hash(String key) {
		if (key == null) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(key.getBytes());
			byte[] digest = md.digest();
			BigInteger bInt = new BigInteger(1, digest);
			return bInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args != null && args.length == 1) {
			System.out.println(args[0] + " => " + HashGenerator.hash(args[0]));
		} else {
			System.out.println("Not a valid input.");
		}
	}

}

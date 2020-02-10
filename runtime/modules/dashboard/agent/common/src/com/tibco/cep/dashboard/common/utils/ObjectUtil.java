package com.tibco.cep.dashboard.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectUtil {
	
	public static void storeObject(Object obj, String folder, String filename) throws IOException {
		ObjectOutputStream objOutStream = null;
		try {
			File objectFile = new File(folder, filename);
			FileOutputStream fos = new FileOutputStream(objectFile);
			objOutStream = new ObjectOutputStream(fos);
			objOutStream.writeObject(obj);
			objOutStream.flush();
		} finally {
			if (objOutStream != null){
				objOutStream.close();
			}
		}
	}

	public static Object reloadObject(String folder, String filename) throws IOException, ClassNotFoundException {
		ObjectInputStream objInStream = null;
		try {
			File objectFile = new File(folder, filename);
			FileInputStream fis = new FileInputStream(objectFile);
			objInStream = new ObjectInputStream(fis);
			Object obj = objInStream.readObject();
			objInStream.close();
			return obj;
		} finally {
			if (objInStream != null){
				objInStream.close();
			}
		}
	}

	public static byte[] objectToByteArray(Serializable obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		return baos.toByteArray();
	}
	
	public static Serializable byteArrayToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return (Serializable) ois.readObject();
		} finally {
			if (ois != null){
				ois.close();
			}
		}
	}	

	public static InputStream getSerializedObjectAsInputStream(Object obj) throws IOException {
		ObjectOutputStream oos = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			// This method copies over the bytes not very efficient.
			return new ByteArrayInputStream(baos.toByteArray());
		} finally {
			if (oos != null){
				oos.close();
			}
		}
	}

	public static boolean equalWithNull(Object obj1, Object obj2) {
		// They are the same object reference, including both null.
		if (obj1 == obj2)
			return true;

		// One of them is null.
		if (obj1 == null || obj2 == null)
			return false;

		return obj1.equals(obj2);
	}

}

package com.tibco.cep.studio.wizard.ftl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileOp {
	public static String getJSONStr(String filePath) {
		String jsonStr = "";
		FileInputStream inputStream = null;
		byte[] bytes = null;
		File file = null;
		try {
			file = new File(filePath);
			inputStream = new FileInputStream(file);
			bytes = new byte[(int)file.length()];
			inputStream.read(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(bytes != null) {
		    jsonStr = new String(bytes);
		}
		return jsonStr;
	}
}

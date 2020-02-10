package com.tibco.cep.bpmn.core.doc;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to store all the DocumentationDataBean objects, which are
 * used while creating links to corresponding pages.<br/>
 * 
 * @author moshaikh
 * 
 */
public class DocumentationBeansStore {
	private static Map<String, DocumentDataBean> map = new HashMap<String, DocumentDataBean>();

	public static DocumentDataBean getBean(String id) {
		return map.get(id);
	}

	public static void putBean(String id, DocumentDataBean bean) {
		map.put(id, bean);
	}

	public static void clear() {
		map.clear();
	}
}

package com.tibco.cep.studio.core.converter;

import java.util.Map;
import java.util.Set;

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;

public abstract class ArchiveResourceConverter {
	
	ArchiveResource fCurrentResource;

	public static final String NAME_PROP = "name";
	public static final String AUTHOR_PROP = "authorProperty";
	public static final String DESC_PROP = "description"; // a guess
	
	public ArchiveResourceConverter(ArchiveResource resource) {
		this.fCurrentResource = resource;
	}

	public void convertArchive(String earName, Map ear) {
		
		Set keySet = ear.keySet();
		for (Object key : keySet) {
			Object value = ear.get(key);
			if (value instanceof Map) {
				processMap((String)key, (Map)value);
			} else if (value instanceof String){
				processAttribute((String) key, (String) value);
			} else if (value instanceof Object[]){
				processArrayAttribute((String) key, (Object[]) value);
			} else {
				System.out.println("something else::"+value);
			}
			
		}

	}

	protected abstract void processMap(String key, Map object);

	protected void processArrayAttribute(String key, Object[] value) {
		
	}
	
	protected void processAttribute(String key, String value) {
		if (NAME_PROP.equals(key)) {
			fCurrentResource.setName(value);
		} else if (AUTHOR_PROP.equals(key)) {
			fCurrentResource.setAuthor(value);
		} else if (DESC_PROP.equals(key)) {
			fCurrentResource.setDescription(value);
		}
	}

}

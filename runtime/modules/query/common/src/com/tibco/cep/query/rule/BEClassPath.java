package com.tibco.cep.query.rule;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javassist.ClassPath;
import javassist.NotFoundException;

import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;

public class BEClassPath implements ClassPath {
	
	RuleServiceProvider provider;
	HashMap classRegistry;
	HashMap inputStreamMap;
	public BEClassPath(RuleServiceProvider provider) {
		this.provider = provider;
		classRegistry = new HashMap();
		inputStreamMap = new HashMap();
		BEClassLoader loader = (BEClassLoader) provider.getClassLoader();
				
		loadClasses(loader.getClasses());
		
	}
	private void loadClasses(Collection classes) {
		
		for (Iterator iter = classes.iterator(); iter.hasNext();) {
			Class element = (Class) iter.next();
			classRegistry.put(element.getName(), element);
			Class nestedClasses[] = element.getClasses();
			loadClasses(Arrays.asList(nestedClasses));
			
		}
		
	}
	public void close() {
		// TODO Auto-generated method stub

	}

	public URL find(String classname) {
		
		
		
		if (classRegistry.containsKey(classname)) {
			try {
				return new URL("file", "localhost", 0, classname);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public InputStream openClassfile(String classname) throws NotFoundException {
		// TODO Auto-generated method stub
		BEClassLoader loader = (BEClassLoader) provider.getClassLoader();
		byte byteCode[] = loader.getByteCode(classname);
		if(!inputStreamMap.containsKey(classname)) {
			ByteArrayInputStream bis = new ByteArrayInputStream(byteCode);
			inputStreamMap.put(classname, bis);
			System.out.println("BEClassname openClassfile " + classname);
			return bis;
		} else 
			return (InputStream) inputStreamMap.get(classname);
		
	}

}

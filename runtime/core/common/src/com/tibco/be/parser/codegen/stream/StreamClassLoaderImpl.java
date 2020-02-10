package com.tibco.be.parser.codegen.stream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.tools.JavaFileObject;

import com.tibco.cep.runtime.service.loader.DynamicLoader;


/**
 * A custom ClassLoader which maps class names to JavaFileObjectImpl instances.
 */
public class StreamClassLoaderImpl extends ClassLoader implements DynamicLoader {
   private final Map<String, JavaFileObject> classMap = new HashMap<String, JavaFileObject>();
    private ClassLoader parentClassLoader;

   public StreamClassLoaderImpl(final ClassLoader parentClassLoader) {
      super(parentClassLoader);
       this.parentClassLoader = (ClassLoader) parentClassLoader;
   }

   /**
    * Add a class name/JavaFileObject mapping
    * 
    * @param qualifiedClassName
    *           the name
    * @param javaFile
    *           the file associated with the name
    */
   void add(final String qualifiedClassName, final JavaFileObject javaFile) {
      classMap.put(qualifiedClassName, javaFile);
   }

   /**
    * @return An collection of JavaFileObject instances for the classMap in the
    *         class loader.
    */
   public Collection<JavaFileObject> files() {
      return Collections.unmodifiableCollection(classMap.values());
   }

   @Override
   protected Class<?> findClass(final String qualifiedClassName)
         throws ClassNotFoundException {
      JavaFileObject file = classMap.get(qualifiedClassName);
      if (file != null) {
         byte[] bytes = ((JavaFileObjectImpl) file).getByteCode();
         return defineClass(qualifiedClassName, bytes, 0, bytes.length);
      }
      // Workaround for "feature" in Java 6
      // see http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6434149
      try {
         Class<?> c = Class.forName(qualifiedClassName);
         return c;
      } catch (ClassNotFoundException nf) {
         // Ignore and fall through
      }
      return super.findClass(qualifiedClassName);
   }

   @Override
   protected URL findResource(String name) {
	   if(classMap.containsKey(name.replaceAll("/", "."))) {
		   try {
			   return  new URL(StreamURLHandler.PROTOCOL,null,0,name,new StreamURLHandler(classMap));
		   } catch (MalformedURLException e1) {
			   return null;
		   }				
	   } else
		   return super.findResource(name);
   }

   @Override
	protected Enumeration<URL> findResources(String name)
			throws IOException {
	   if(classMap.containsKey(name.replaceAll("/", "."))) {
		   try {
			   Vector<URL> res = new Vector<URL>();
			    res.add(new URL(StreamURLHandler.PROTOCOL,null,0,name,new StreamURLHandler(classMap)));
			    return res.elements();
		   } catch (MalformedURLException e1) {
			   return null;
		   }				
	   } else
		return super.findResources(name);
	}

   @Override
	protected Package getPackage(String name) {
		// TODO Auto-generated method stub
		return super.getPackage(name);
	}


   @Override
   public InputStream getResourceAsStream(final String name) {
      if (name.endsWith(".class")) {
         String qualifiedClassName = name.substring(0,
               name.length() - ".class".length()).replace('/', '.');
         JavaFileObjectImpl file = (JavaFileObjectImpl) classMap.get(qualifiedClassName);
         if (file != null) {
            return new ByteArrayInputStream(file.getByteCode());
         }
      } else  if(classMap.containsKey(name)) {
    	  String qualifiedClassName = name.replace('/', '.');
    	  JavaFileObjectImpl file = (JavaFileObjectImpl) classMap.get(qualifiedClassName);
	         if (file != null) {
	            return new ByteArrayInputStream(file.getByteCode());
	         }  
      } 
      return super.getResourceAsStream(name);
   }

   @Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return super.loadClass(name);
	}

   @Override
   protected synchronized Class<?> loadClass(final String name, final boolean resolve)
         throws ClassNotFoundException {
      return super.loadClass(name, resolve);
   }
   
   @Override
	public void addJavaFile(String qualifiedName, JavaFileObject file) {
		add(qualifiedName,file);
	}
}
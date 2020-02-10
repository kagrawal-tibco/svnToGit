package com.tibco.cep.studio.core.functions.annotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.core.resources.IFile;

import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.stream.AbstractStreamGenerator;
import com.tibco.be.parser.codegen.stream.JavaFileLocation;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.codegen.stream.RootFolderLocation;
import com.tibco.be.parser.codegen.stream.StreamClassLoaderImpl;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class AnnotationInfoCollector {

	public static class AnnotationCompiler extends AbstractStreamGenerator {
		private RootFolderLocation rootDir;
		private JavaFolderLocation srcDir;

		/**
		 * @param packager
		 * @param ontology
		 * @param classpath
		 * @param clazzLoader
		 * @param monitor
		 * @param debug
		 * @param isAnnotationProcessingOnly
		 * @param annotationProcessors
		 */
		public AnnotationCompiler(String classpath, 
				ClassLoader clazzLoader,
				boolean debug, 
				boolean isAnnotationProcessingOnly, 
				String[] annotationProcessors) {
			super(classpath, clazzLoader, debug, StudioCore.getSourceJavaVersion(), StudioCore.getTargetJavaVersion(), CodeGenConstants.charset,
					isAnnotationProcessingOnly, annotationProcessors);
		}

		@Override
		public void debug(String msg, Object... args) {
			// TODO Auto-generated method stub

		}

		@Override
		public void init() throws Exception {
			super.init();
			this.rootDir = getFileManager().getRootFolder("Root");
			this.srcDir = rootDir.addFolder("javasrc");
		}

		/**
		 * Copies the content of the java source to the virtual file
		 * 
		 * @param fileURL
		 * @param packageName
		 * @throws Exception
		 */
		public void addFile(JavaFileInfo info) throws Exception {
			JavaFileLocation outFile = srcDir.addFile(info.getPackageName(), info.getFileName());
			InputStream fis = new ByteArrayInputStream(info.getSource());
			OutputStream os = outFile.openOutputStream();
			try {
				byte[] buffer = new byte[16384];
				int bytesRead = 0;
				while ((bytesRead = fis.read(buffer)) != -1) {
					os.write(buffer, 0, bytesRead);
				}
			} finally {
				fis.close();
				os.flush();
				os.close();
			}

		}

		@Override
		public void log(String msg, Throwable e) {
			// TODO Auto-generated method stub

		}

	}
	
	public static class JavaFileInfo {
		String packageName;
		String fileName;
		byte[] source;
		
		public static JavaFileInfo createJavaFileInfo(JavaSource javaSource) {
			return new AnnotationInfoCollector.JavaFileInfo(javaSource.getPackageName(), javaSource.getName()+"."+CommonIndexUtils.JAVA_EXTENSION, javaSource.getFullSourceText());
		}
		public JavaFileInfo(String packageName, String fileName,byte[] source) {
			super();
			this.packageName = packageName;
			this.fileName = fileName;
			this.source = new byte[source.length];
			System.arraycopy(source, 0, this.source, 0, source.length);
		}
		public String getPackageName() {
			return packageName;
		}
		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public byte[] getSource() {
			return source;
		}
		
	}

	private static ConcurrentHashMap<Thread,AnnotationProcessingContext> catalogThreadLocal= new ConcurrentHashMap<Thread,AnnotationProcessingContext>();
	private String classPath;
	private StreamClassLoaderImpl classLoader;
	boolean initialized=false;
	private AnnotationHandler<?>[] handlers;
	private List<JavaFileInfo> fileMap = new ArrayList<JavaFileInfo>();

	/**
	 * @param classpath
	 */
	private AnnotationInfoCollector(String classpath,AnnotationHandler<?>[] handlers) {
		this(classpath, AnnotationInfoCollector.class.getClassLoader(),handlers);
	}

	/**
	 * @param classpath
	 * @param parentClassLoader 
	 */
	private AnnotationInfoCollector(String classpath, ClassLoader parentClassLoader,AnnotationHandler<?>[] handlers) {
		this.classPath = classpath;
		this.classLoader = new StreamClassLoaderImpl(parentClassLoader);
		this.handlers = handlers;
		
		
	}

	/**
	 * @param classPath
	 * @return
	 */
	public static AnnotationInfoCollector newInstance(String classPath,AnnotationHandler<?>[] handlers) {
		return new AnnotationInfoCollector(classPath,handlers);
	}

	/**
	 * @param classpath
	 * @param parentClassLoader
	 * @return
	 */
	public static AnnotationInfoCollector newInstance(String classpath, ClassLoader parentClassLoader,AnnotationHandler[] handlers) {
		return new AnnotationInfoCollector(classpath, parentClassLoader,handlers);
	}

	public static AnnotationProcessingContext getThreadLocalContext() {
		return catalogThreadLocal.get(Thread.currentThread());
	}

	public static void setThreadLocalContext(AnnotationProcessingContext context) {
		if(context != null){
			
			catalogThreadLocal.putIfAbsent(Thread.currentThread(), context);
		} else {
			catalogThreadLocal.remove(Thread.currentThread());
		}
	}

	public void addFile(JavaFileInfo info) throws Exception {
		fileMap.add(info);
	}

	public synchronized Map<Class<?>, Object> collect() throws Exception {
		// Execution of the compiler is done on a single thread with a single thread specific context
		// to 
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future< Map<Class<?>, Object>> future = executorService.submit(new Callable< Map<Class<?>, Object>>() {

			@Override
			public Map<Class<?>, Object> call() throws Exception {
				AnnotationProcessingContext context = new AnnotationProcessingContext();
				context.register(handlers);
				
				try {
				AnnotationInfoCollector.setThreadLocalContext(context);
				AnnotationCompiler compiler = new AnnotationCompiler(classPath, 
						classLoader, 
						false, 
						true, 
						new String[]{AnnotationProcessorImpl.class.getCanonicalName()});
				compiler.init();
				for (JavaFileInfo entry : fileMap) {
					compiler.addFile(entry);
				}
				compiler.compile();
				context = getThreadLocalContext();
				} finally {
					AnnotationInfoCollector.setThreadLocalContext(null);
				}
				Map<Class<?>,Object> resultMap = new HashMap<Class<?>,Object>();
				for(AnnotationHandler<?> handler:context.getRegisteredHandlers().values()){
					Object res = handler.getResult();
					if(res == null)
						continue;
					resultMap.put(res.getClass(), res);
				}
				return resultMap;
			}
		});
		
		return future.get();
		
	}
	

}
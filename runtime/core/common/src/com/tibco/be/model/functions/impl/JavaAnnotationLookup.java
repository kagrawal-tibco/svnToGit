package com.tibco.be.model.functions.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.xml.sax.InputSource;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tibco.be.model.functions.BEDeprecated;
import com.tibco.be.model.functions.BEFunction;
import com.tibco.be.model.functions.BEPackage;
import com.tibco.be.model.functions.Enabled;
import com.tibco.be.model.functions.FunctionDomain;
import com.tibco.be.model.functions.FunctionParamDescriptor;
import com.tibco.be.model.functions.FunctionsCategory;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory.CatalogRoot;
import com.tibco.be.model.functions.impl.JavaStaticFunctionsFactory.IFunctionClassLoader;
import com.tibco.be.model.functions.utils.FunctionHelpBundle;
import com.tibco.be.util.JNISignature;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.provider.JavaArchiveResourceProvider;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.util.PlatformUtil;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiFactoryFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.build.MutableComponentFactoryTNS;
import com.tibco.xml.schema.build.MutableSchema;
import com.tibco.xml.schema.flavor.XSDLConstants;
import com.tibco.xml.schema.helpers.SmRootElement;
import com.tibco.xml.schema.impl.DefaultComponentFactory;
import com.tibco.xml.schema.impl.DefaultSchema;
import com.tibco.xml.schema.parse.SmParseSupport;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;

public class JavaAnnotationLookup {

	public static final String JAR_EXTN = "jar";
	public static final String[] FILE_TYPES = { JAR_EXTN }; //$NON-NLS-1$
	public static final String UNKNOWN = "unknown"; //$NON-NLS-1$
	public static final String CATALOG = "catalog"; //$NON-NLS-1$
	public static final String CATEGORY = "category"; //$NON-NLS-1$
	public static final String ENABLED = "enabled"; //$NON-NLS-1$
	public static final String BEFUNCTION = "BEFunction"; //$NON-NLS-1$
	public static final String PACKAGE_SEPARATOR = "\\."; //$NON-NLS-1$
	static Logger logger = LogManagerFactory.getLogManager().getLogger(JavaAnnotationLookup.class);

	protected static XiParser parser;
	protected static MutableComponentFactoryTNS cf;
	protected static XiFactory factory;
	private static boolean initialized = false;
	private static ThreadLocal<ClassLoader> contextClassLoader = new ThreadLocal<>();
	private static Set<String> skipScanPaths = new HashSet<String>();
	
	static {
		skipScanPaths = new HashSet<String> (Arrays.asList(System.getProperty("be.catalog.loader.ignore.paths", "/,/lib").split(",")));
	}

	static {
		com.tibco.cep.Bootstrap.ensureBootstrapped();
	}

	/**
	 * Static initialization of {@link XiParserFactory},
	 * {@link DefaultComponentFactory},{@link XiFactoryFactory},
	 * {@link FunctionHelpBundle}
	 * 
	 * @throws Exception
	 */
	protected static void init() {
		if (!initialized) {
			parser = XiParserFactory.newInstance();
			cf = DefaultComponentFactory.getTnsAwareInstance();
			factory = XiFactoryFactory.newInstance();
			initialized = true;
		}
	}

	/**
	 * Test entry point
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FunctionsCatalog catalog = new FunctionsCatalog();
		lookupCatalog(catalog, false);
	}

	/**
	 * Loads the BE jar custom functions after the project has been loaded in
	 * RSP.initproject
	 * 
	 * @param rsp
	 * @throws Exception
	 */
	public static void loadBEJarFunctions(RuleServiceProvider rsp) throws Exception {
		if (rsp != null) {
			DeployedProject dproj = rsp.getProject();
			setContextClassLoader(rsp.getClassLoader());
			JavaArchiveResourceProvider jarp = dproj.getJavaArchiveResourceProvider();
			List<URL> jarURLS = jarp.getJarResourceURLS();
			lookupCatalog(FunctionsCatalog.getINSTANCE(), jarURLS, false, false); // this will contain only one (or a few) jars, no need to incur extra time reading/writing annotations file
		}
	}

	/**
	 * @param catalog
	 * @param isConsole
	 * @throws Exception
	 */
	public static void lookupCatalog(FunctionsCatalog catalog, boolean isConsole) throws Exception {
		List<URL> urls = getSystemClassPathUrls();
		lookupCatalog(catalog, urls, isConsole, true);
	}

	/**
	 * Populates the {@link FunctionsCatalog} singleton
	 * 
	 * @param catalog
	 * @param b
	 * @throws Exception
	 */
	public static void lookupCatalog(FunctionsCatalog catalog, List<URL> urls, boolean isConsole, boolean optimize) throws Exception {
		int count = 0;
		IFunctionClassLoader fcldr = new IFunctionClassLoader() {

			@Override
			public Class<?> getClass(String className) throws ClassNotFoundException {
				return Class.forName(className);
			}

			@Override
			public ClassLoader getLoader() {
				return Class.class.getClassLoader();
			}

		};

		ClassPool cp = ClassPool.getDefault();
		if (getContextClassLoader() == null) {
			cp = createClassPoolFromClassPath(urls.toArray(new URL[0]));
		}
		cp.doPruning = true;

		try {
			Map<String, Object> jsonVals = new LinkedHashMap<String, Object>();
			String parentDir = System.getProperty("tibco.env.BE_HOME");
			if (parentDir == null || parentDir.trim().length() == 0) {
				parentDir = System.getProperty("user.dir");
			} else {
				parentDir += File.separator + "bin";
			}
			File jsonFile = new File(parentDir, "_annotations.idx");

			if (optimize && jsonFile.exists()) {
				readAnnotationsFile(jsonFile, jsonVals);
			}

			ClassLoader cldr = getContextClassLoader();
			if (cldr != null) {
				// The context Classloader is set only for BE Jar urls
				List<URL> cpURLs = getSystemClassPathUrls();
				cp = createClassPoolFromClassPath(cpURLs.toArray(new URL[0]));
				cp.doPruning = true;
				cp.appendClassPath(new LoaderClassPath(cldr));
			}

			URL url = cp.find(Enabled.class.getName());
			if (url == null) {
				cp.insertClassPath(new ClassClassPath(Enabled.class));
			}

			long millisStart = System.currentTimeMillis();
			ConcurrentHashMap<URL, Boolean> loadedURLS = new ConcurrentHashMap<URL, Boolean>();
			processCachedURLs(loadedURLS, jsonVals, urls);
			boolean useParallel = Boolean.valueOf(System.getProperty("be.catalog.loader.parallel", Boolean.TRUE.toString()));
			
			if (useParallel && loadedURLS.isEmpty()) {
				count = lookupCategoriesInParallel(catalog, urls.toArray(new URL[0]), fcldr, cp, loadedURLS, false);
			} else {
				count = lookupCategories(catalog, urls.toArray(new URL[0]), fcldr, cp, loadedURLS, false);
			}
			long millisEnd = System.currentTimeMillis();
			String s = "%d functions loaded in %d milliseconds";
			if (useParallel) {
				s = "%d functions loaded in %d milliseconds in parallel";
			}
			if (optimize && count > 0) {
				jsonVals.clear();
				writeAnnotationsFile(jsonFile, jsonVals, loadedURLS);
			}
			logger.log(Level.TRACE, String.format(s, count, millisEnd - millisStart));

		} finally {
			cp = null;
		}
		
	}

	public static void processCachedURLs(ConcurrentHashMap<URL, Boolean> loadedURLS, Map<String, Object> jsonVals, List<URL> urls) {
		for (URL url : urls) {
			String fileName = url.getFile();
			if (jsonVals.containsKey(fileName)) {
				Map<String, Object> attrs = (Map<String, Object>) jsonVals.get(fileName);
				Object object = attrs.get("modificationStamp");
				File newFile = new File(fileName);
				long modStamp = newFile.lastModified();
				if (modStamp == (long) object) {
					boolean hasAnnotations = (boolean) attrs.get("hasAnnotations");
					if (!hasAnnotations) {
						// we've already processed this exact jar, and no annotations were found.  Just add it to the 'loadedURLS' map
						// and it won't get processed again
						addLoadedURL(loadedURLS, url, false);
					}
				}
			}
		}
	}

	public static void writeAnnotationsFile(File jsonFile,
			Map<String, Object> jsonVals, ConcurrentHashMap<URL, Boolean> loadedURLS)
					throws FileNotFoundException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Enumeration<URL> keys = loadedURLS.keys();

		while(keys.hasMoreElements()) {
			URL nextElement = keys.nextElement();
			if (!"jar".equalsIgnoreCase(getExtension(nextElement.getFile()))) {
				continue;
			}
			Map<String, Object> fileVals = new LinkedHashMap<String, Object>();
			long lastModified = new File(nextElement.getFile()).lastModified();
			fileVals.put("modificationStamp", lastModified);
			boolean hasAnnotations = false;
			if (loadedURLS.get(nextElement)) {
				hasAnnotations = true;
			}
			fileVals.put("hasAnnotations", hasAnnotations);
			jsonVals.put(nextElement.getFile(), fileVals);
		}
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		if (!jsonFile.exists()) {
			boolean fileCreated = false;
			try {
				if (!jsonFile.getParentFile().exists()) {
					jsonFile.getParentFile().mkdirs();
				}
				fileCreated = jsonFile.createNewFile();
			} catch (Exception e) {
				logger.log(Level.INFO, "Unable to create _annotations.idx file [Reason: %s]", e.getMessage());
				return;
			}
			if (!fileCreated) {
				// permissions issue?
				logger.log(Level.INFO, "Unable to create _annotations.idx file");
				return;
			}
		}
		
		FileOutputStream fos = new FileOutputStream(jsonFile);
		try {
			mapper.writeValue(fos, jsonVals);
			fos.flush();
		} finally {
			fos.close();
		}
	}

	public static void readAnnotationsFile(File json, Map<String, Object> jsonVals) {
		JsonFactory factory = new JsonFactory();
		try {
			JsonParser parser = factory.createParser(json);
			JSONAnnotationReader reader = new JSONAnnotationReader(jsonVals);
			reader.parseJSON(parser);
		} catch (IOException e) {
			logger.log(Level.WARN, "Unable to read annotations file.  Rescanning all jars for function annotations", e);
		} catch (Exception e) {
			logger.log(Level.WARN, "Unable to read annotations file.  Rescanning all jars for function annotations", e);
		}
		
	}

	public static ClassPool createClassPoolFromClassPath(URL[] urls) throws URISyntaxException, NotFoundException {
		ClassPool cp = new ClassPool(ClassPool.getDefault());
		for (URL path : urls) {
			File f = new File(path.toURI());
			if (f.exists()) {
				cp.appendClassPath(f.getAbsolutePath());
			}
		}
		return cp;
	}

	public static ClassPool createClassPoolFromClassPath(URL[] urls, ClassPool parent) throws URISyntaxException, NotFoundException {
		ClassPool cp = new ClassPool(parent);
		for (URL path : urls) {
			File f = new File(path.toURI());
			if (f.exists()) {
				cp.appendClassPath(f.getAbsolutePath());
			}
		}
		return cp;
	}

	/**
	 * Looks up the classes in the class path retrieved from "java.class.path"
	 * for {@link BEFunction} and {@link BEPackage} annotations
	 * 
	 * @param catalog
	 *            Function catalog root singleton
	 * @param fcldr
	 *            Delegate class loader
	 */
	public static int lookupCategories(FunctionsCatalog catalog, final URL[] libPaths, final IFunctionClassLoader fcldr, ClassPool cp,
			ConcurrentHashMap<URL, Boolean> loadedURLS, boolean isConsole) {
		init();
		int count = 0;
		if (libPaths == null || libPaths.length == 0) {
			return count;
		}

		ClassLoader cldr = Thread.currentThread().getContextClassLoader();
		List<CtClass> annClasses = new ArrayList<>();
		try {
			Thread.currentThread().setContextClassLoader(fcldr.getClass().getClassLoader());
			try {
				/**
				 * ensure the classpool is aware of the annotation classes
				 */
				annClasses.add(cp.get(Enabled.class.getCanonicalName()));
				annClasses.add(cp.get(BEDeprecated.class.getCanonicalName()));
				annClasses.add(cp.get(BEPackage.class.getCanonicalName()));
				annClasses.add(cp.get(BEFunction.class.getCanonicalName()));
			} catch (Exception e) {
				logger.log(Level.FATAL, "Failed to initialize annotation classes to the Classpool", e);
				throw new IllegalStateException(e);
			}

			for (URL url : libPaths) {
				if (loadedURLS.containsKey(url)) {
					continue;
				}
				String extension = getExtension(url.getPath());
				try {
					if (extension.equalsIgnoreCase(JAR_EXTN)) {

						JarInputStream jis = new JarInputStream(url.openStream());
						try {

							JarEntry jarEntry = null;
							int currCount = 0;
							while ((jarEntry = jis.getNextJarEntry()) != null) {
								if (jarEntry.isDirectory())
									continue;
								final String extn = getExtension(jarEntry.getName());
								if (extn == null || !extn.equalsIgnoreCase("class"))
									continue;
								URL jarURL = null;
								if (url.getProtocol().equalsIgnoreCase("mem")) {
									final byte[] buffer = new byte[4096];
									final ByteArrayOutputStream baos = new ByteArrayOutputStream();
									int read;
									while ((read = jis.read(buffer)) != -1) {
										baos.write(buffer, 0, read);
									}
									jarURL = new URL(url, jarEntry.getName(), new URLStreamHandler() {

										@Override
										protected URLConnection openConnection(URL u) throws IOException {

											return new URLConnection(u) {

												@Override
												public void connect() throws IOException {
												}

												@Override
												public InputStream getInputStream() throws IOException {
													return new ByteArrayInputStream(baos.toByteArray());
												}

											};
										}
									});
								} else {
									jarURL = new URL(String.format("jar:%s!/%s", url.toString(), jarEntry.getName()));
								}
								currCount += processClass(catalog, loadedURLS, jarURL, cp, fcldr, isConsole, url);
							}
							count += currCount;
							if (currCount > 0) {
								logger.log(Level.TRACE, "Found %d entries in %s", currCount, url.getFile());
								addLoadedURL(loadedURLS, url, true);
							} else {
								addLoadedURL(loadedURLS, url, false);
							}
						} finally {
							jis.close();
						}
					} else if (extension.equalsIgnoreCase("class")) {
						count += processClass(catalog, loadedURLS, url, cp, fcldr, isConsole, null);
					}
				} catch (Exception e) {
					// Eat the exception and proceed to the next URL
					logger.log(Level.ERROR, "Failed to load URL:%s", e, url);
				}

			}
		} finally {
			for (CtClass annClass : annClasses) {
				annClass.detach();
			}
			cp = null;
			Thread.currentThread().setContextClassLoader(cldr);
			return count;
		}
	}

	public static class CatalogEntryComparator implements Comparator<CatalogEntry> {

		@Override
		public int compare(CatalogEntry o1, CatalogEntry o2) {
			int v = o1.getCatalogName().compareTo(o2.getCatalogName());
			if (v == 0) {
				return o1.getPackageName().compareTo(o2.getPackageName());
			} else {
				return v;
			}

		}

	}

	public static class CatalogEntry implements Comparable<CatalogEntry> {
		private String packageName;
		private JavaStaticFunction function;
		private String catalogName;

		public CatalogEntry(String catalogName, String packageName, JavaStaticFunction function) {
			super();
			this.catalogName = catalogName;
			this.packageName = packageName;
			this.function = function;
		}

		public String getCatalogName() {
			return catalogName;
		}

		public String getPackageName() {
			return packageName;
		}

		public JavaStaticFunction getFunction() {
			return function;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((catalogName == null) ? 0 : catalogName.hashCode());
			result = prime * result + ((function == null) ? 0 : function.hashCode());
			result = prime * result + ((packageName == null) ? 0 : packageName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CatalogEntry other = (CatalogEntry) obj;
			if (catalogName == null) {
				if (other.catalogName != null)
					return false;
			} else if (!catalogName.equals(other.catalogName))
				return false;
			if (function == null) {
				if (other.function != null)
					return false;
			} else if (!function.getName().equals(other.function.getName()))
				return false;
			if (packageName == null) {
				if (other.packageName != null)
					return false;
			} else if (!packageName.equals(other.packageName))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "CatalogEntry [catalogName=" + catalogName + ", packageName=" + packageName + ", function=" + function + "]";
		}

		@Override
		public int compareTo(CatalogEntry o) {
			int v = catalogName.compareTo(o.getCatalogName());
			if (v == 0) {
				return packageName.compareTo(o.getPackageName());
			}
			return v;
		}

	}

	public static class JarScanner implements Callable<Collection<CatalogEntry>> {

		private URL url;
		private IFunctionClassLoader fcldr;
		private boolean isConsole;
		private ConcurrentHashMap<URL, Boolean> loadedURLS;
		private ClassPool cp;
		private Collection<CatalogEntry> entries;
		private static List<String> noScanList = null;
		
		static {
			String noScanListString = System.getProperty("be.catalog.loader.no.scan.list");
			noScanList = prepareNoscanList(noScanListString);
		}

		public JarScanner(ClassPool cp, URL url, IFunctionClassLoader fcldr, ConcurrentHashMap<URL, Boolean> loadedURLs, boolean console) {
			this.cp = cp;
			this.url = url;
			this.fcldr = fcldr;
			this.isConsole = console;
			this.loadedURLS = loadedURLs;
			this.entries = new HashSet<CatalogEntry>();
		}

		public Collection<CatalogEntry> call() {
			
			
			String jarName = url.getPath().substring(url.getPath().lastIndexOf("/")+1, url.getPath().length());
			
			if (noScanList!=null && noScanList.contains(jarName)) { 
				  addLoadedURL(loadedURLS, url, false); 
				  return entries; 
			}
			 

			String extension = getExtension(url.getPath());
			try {
				if (extension.equalsIgnoreCase(JAR_EXTN)) {

					JarInputStream jis = new JarInputStream(url.openStream());
					try {

						JarEntry jarEntry = null;
						while ((jarEntry = jis.getNextJarEntry()) != null) {
							if (jarEntry.isDirectory())
								continue;
							final String extn = getExtension(jarEntry.getName());
							if (extn == null || !extn.equalsIgnoreCase("class"))
								continue;
							URL jarURL = null;
							// logger.log(Level.INFO,
							// String.format("JAR entry:%s",
							// jarEntry.getName()));
							if (url.getProtocol().equalsIgnoreCase("mem")) {
								final byte[] buffer = new byte[4096];
								final ByteArrayOutputStream baos = new ByteArrayOutputStream();
								int read;
								while ((read = jis.read(buffer)) != -1) {
									baos.write(buffer, 0, read);
								}
								jarURL = new URL(url, jarEntry.getName(), new URLStreamHandler() {

									@Override
									protected URLConnection openConnection(URL u) throws IOException {

										return new URLConnection(u) {

											@Override
											public void connect() throws IOException {
											}

											@Override
											public InputStream getInputStream() throws IOException {
												return new ByteArrayInputStream(baos.toByteArray());
											}

										};
									}
								});
							} else {
								jarURL = new URL(String.format("jar:%s!/%s", url.toString(), jarEntry.getName()));
							}
							entries.addAll(processClassParallel(loadedURLS, jarURL, cp, fcldr, isConsole, url));
						}
						if (entries.size() > 0) {
							logger.log(Level.DEBUG, "Found %d entries in %s", entries.size(), url.getFile());
							addLoadedURL(loadedURLS, url, true);
						} else {
							addLoadedURL(loadedURLS, url, false);
						}
					} finally {
						jis.close();
					}
				} else if (extension.equalsIgnoreCase("class")) {
					entries.addAll(processClassParallel(loadedURLS, url, cp, fcldr, isConsole, null));
					addLoadedURL(loadedURLS, url, false);
				}

			} catch (Exception e) {
				// Eat the exception and proceed to the next URL
				logger.log(Level.ERROR, "Failed to load URL:%s", e, url);
			} finally {
				return entries;
			}
		}

		/*
		 * Creating a static negative list of jars to skip scanning, as they do not contain any be related catalog functions. 
		 * In future release we shold try to avoid version in jar names, which would help here and even in install scripts. 
		 * This code is done to improve performance and a bug which was seen with javassit 3.26, taking more time to scan aws-sdk jar.
		 */
		
		private static List<String> prepareNoscanList(String noScanListString) {
			
			try {
				String jarsToSkip = null;
				if(noScanListString!=null) {
					jarsToSkip = noScanListString;
				}else {
					jarsToSkip = "Deployment.jar,com.tibco.genxdm.processor.xslt.functions_1.2.100.001.jar,poi-3.7-20101029.jar,org.eclipse.emf.ecore_2.12.0.v20160420-0247.jar,com.tibco.cep.decision.tree.core_5.6.1.jar,xml-apis.jar,mail.jar,com.tibco.tibcoxml.io.eclipse_5.60.100.001.jar,com.tibco.tibcoxml.xml.sdk.eclipse_5.60.100.002.jar,iText.jar,commons-logging.jar,com.tibco.genxdm.processor.xslt.processor_1.2.100.001.jar,commons-lang3-3.3.2.jar,jackson-dataformat-cbor-2.6.7.jar,cep-kafka.jar,com.tibco.cep.sharedresource.mqtt_5.6.1.jar,org.genxdm.wsdl.api_1.2.0.jar,com.tibco.cep.tra_5.6.1.jar,cep-migration.jar,org.genxdm.xpath.v20_1.2.0.jar,httpcore-4.4.9.jar,com.tibco.cep.datamodeling.common_5.6.1.jar,ion-java-1.0.2.jar,batik.jar,com.tibco.cep.studio.decision.table.ui_5.6.1.jar,com.tibco.cep.studio.xerces_5.6.1.jar,commons-collections-3.2.2.jar,com.tibco.tibcoxml.xml.transform.eclipse_5.60.100.001.jar,cep-datagrid-tibco.jar,com.tibco.cep.eventstreamprocessing.common_5.6.1.jar,jackson.jar,annotations-api.jar,com.tibco.xml.dictionaryapi_1.0.0.003.jar,com.tibco.cep.studio.application_5.6.1.jar,org.eclipse.uml2.uml_5.2.4.v20170530-1052.jar,com.tibco.xml.cxf.ootb_1.3.200.001.jar,com.tibco.cep.studio.common_5.6.1.jar,com.tibco.cep.eventstreamprocessing.common_5.6.1.jar,com.tibco.cep.studio.tester.core_5.6.1.jar,com.tibco.cep.studio.mapper_5.6.1.jar,gwt-servlet.jar,amazon-kinesis-client-1.9.2.jar,httpmime-4.5.5.jar,cep-sb-channel.jar,com.tibco.xml.mappermodel_1.0.700.001.jar,com.tibco.cep.studio.rms.ui_5.6.1.jar,pmml-evaluator-extension-1.3.7.jar,com.tibco.cep.decision.table.capabilities_5.6.1.jar,gwt-elemental.jar,com.tibco.cep.studio.ui.editors_5.6.1.jar,com.tibco.cep.studio.streambase_5.6.1.jar,com.tibco.cep.decision.table.common_5.6.1.jar,cep-channel-api.jar,com.tibco.cep.studio.rms.core_5.6.1.jar,validation-api-1.0.0.GA.jar,jackson-datatype-json-org-2.9.10.jar,com.tibco.xml.cxf.ootb.runtime_1.3.200.002.jar,com.tibco.tibcoxml.xml.soap.eclipse_5.60.100.001.jar,xalan.jar,kafka-clients-2.3.0.jar,jackson-databind-2.9.10.jar,tsallperspectives62dep.jar,pmml-schema-1.3.7.jar,ecj-4.4.2.jar,slf4j-api-1.7.7.jar,com.tibco.cep.liveview_5.6.1.jar,validation-api-1.0.0.GA-sources.jar,com.tibco.xml.cxf.wizard_1.2.100.002.jar,com.tibco.cep.datamodeling.common_5.6.1.jar,com.tibco.tibcoxml.lex.yap.eclipse_5.60.100.001.jar,org.genxdm.processor.xop_1.0.0.jar,com.tibco.cep.sharedresource_5.6.1.jar,stringtemplate.jar,TIsqlserver.jar,com.tibco.cep.studio.ui_5.6.1.jar,com.tibco.xml.mapperui_1.0.700.001.jar,com.tibco.tibcoxml.xml.ae2xsd.eclipse_5.60.100.001.jar,org.genxdm.processor.w3c.xs_1.2.2.jar,jackson-databind-2.6.7.jar,com.tibco.cep.decision.tree.common_5.6.1.jar,as-common.jar,cep-as3-channel.jar,tsgwtperspectives62dep.jar,commons-codec-1.10.jar,jaxb-api-2.3.1.jar,org.genxdm.xop.api_1.0.0.jar,httpclient.jar,org.genxdm.processor.xpath.v10_1.2.0.jar,org.genxdm.processor.xpath.v20_1.2.0.jar,commons-jexl3-3.1.jar,com.tibco.cep.studio.ws_5.6.1.jar,cep-kinesis-channel.jar,com.tibco.cep.studio.cluster.topology_5.6.1.jar,com.tibco.cep.sharedresource.hawk_5.6.1.jar,com.tibco.cep.sharedresource.ascon_5.6.1.jar,cep-common.jar,org.eclipse.paho.client.mqttv3-1.2.0.jar,commons-jexl-2.1.1.jar,gwt-soyc-vis.jar,poi-4.1.0.jar,protobuf-java-3.6.0.jar,com.tibco.cep.sharedresource.ftl_5.6.1.jar,commons-collections4-4.4.jar,jakarta.xml.soap-api.jar,com.tibco.cep.diagramming_5.6.1.jar,com.tibco.cep.studio.ui.navigator_5.6.1.jar,cep-ui-rms-common.jar,catalina-ha.jar,com.tibco.cep.eventstreamprocessing_5.6.1.jar,org.genxdm.bridgekit_1.2.2.jar,tomcat-embed-logging-juli.jar,org.eclipse.emf.common_2.16.0.v20190528-0845.jar,com.tibco.tibcoxml.xml.ws.wsdl.eclipse_5.60.100.001.jar,jena.jar,org.genxdm.processor.io_1.2.2.jar,com.tibco.cep.studio.wizard.ftl_5.6.1.jar,com.tibco.cep.thirdparty_5.6.1.jar,tomcat-embed-core.jar,jackson-datatype-json-org-2.6.7.jar,commons-lang3-3.9.jar,joda-time-2.8.1.jar,httpclient-4.5.5.jar,httpcore-nio-4.4.9.jar,snappy-java-1.1.7.3.jar,com.tibco.cep.studio.mapper.ui_5.6.1.jar,com.tibco.cep.tra_5.6.1.jar,jsch-0.1.55.jar,org.eclipse.uml2.types_2.0.0.v20170530-1052.jar,stax-ex.jar,gwt-advanced-components.jar,httpcore.jar,com.tibco.tibcoxml.xml.xdata.eclipse_5.60.100.001.jar,slf4j-log4j12-1.7.28.jar,jaxb-impl.jar,antlr-3.2.jar,poi-ooxml-4.1.0.jar,slf4j-api-1.7.28.jar,com.tibco.cep.decision.table.common_5.6.1.jar,com.tibco.cep.studio.cli_5.6.1.jar,jackson-annotations-2.9.10.jar,httpmime-4.5.10.jar,serializer.jar,cep-base.jar,slf4j-log4j12-1.7.7.jar,javax.activation-1.2.0.jar,com.tibco.xml.mapperui.emfapi_1.0.700.001.jar,org.eclipse.core.resources_3.11.1.v20161107-2032.jar,gwt-servlet-deps.jar,en_US.jar,as-hawk-agent.jar,com.tibco.cep.security_5.6.1.jar,lv-client-wwwdeps.jar,kafka-streams-2.3.0.jar,com.tibco.cep.studio.util_5.6.1.jar,com.tibco.cep.studio.dbconcept_5.6.1.jar,poi-scratchpad-3.7-20101029.jar,gwt-dev.jar,com.tibco.cep.studio.core_5.6.1.jar,jsch-0.1.51.jar,com.tibco.cep.datamodeling_5.6.1.jar,org.eclipse.uml2.common_2.1.0.v20170530-1052.jar,httpclient-4.5.10.jar,pmml-evaluator-1.3.7.jar,cep-thread-analyzer.jar,org.eclipse.emf.common_2.12.0.v20160420-0247.jar,org.genxdm.processor.convert_1.2.0.jar,tomcat-embed-jasper.jar,xercesImpl.jar,com.tibco.cep.decision.table.ui_5.6.1.jar,cep-liveview.jar,javassist.jar,poi-ooxml-3.7-20101029.jar,commons-lang-2.6.jar,org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar,cep-ui-rt-common.jar,org.genxdm.xpath.v10_1.2.0.jar,sbclient.jar,cep-maven.jar,tomcat-embed-el.jar,poi-ooxml-schemas-3.7-20101029.jar,log4j-1.2.15.jar,com.tibco.cep.decision.table.core_5.6.1.jar,com.tibco.cep.decision.tree.ui_5.6.1.jar,gwt-user.jar,com.tibco.genxdm.bridge.xinode_1.2.200.003.jar,xml-apis-ext.jar,com.tibco.cep.studio.ui.statemachine_5.6.1.jar,gwt-api-checker.jar,com.tibco.tibcoxml.util.eclipse_5.60.100.001.jar,org.genxdm.bridge.axiom_1.2.2.jar,cep-interpreter.jar,validation-api-1.0.0.GA.jar,commons-httpclient.jar,org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar,cep-container.jar,webstudio.jar,stax2-api.jar,pmml-model-1.3.7.jar,lv-client.jar,gwt-codeserver.jar,com.tibco.xml.mappermodel.emfapi_1.0.700.001.jar,org.genxdm.processor.w3c.xs.validation_1.2.2.jar,com.tibco.cep.studio.wizard.as_5.6.1.jar,org.genxdm.api_1.2.2.jar,httpcore-nio-4.4.12.jar,jackson-core-2.9.10.jar,org.eclipse.core.resources_3.13.400.v20190505-1655.jar,guava-22.0.jar,tomcat-embed-logging-log4j.jar,jakarta-regexp-1.2.jar,TIBCrypt.jar,com.tibco.cep.rt_5.6.1.jar,derbyclient.jar,jackson-annotations-2.6.7.jar,cep-mqtt-channel.jar,as-agent.jar,saaj-impl.jar,aws-java-sdk-1.11.434.jar,org.genxdm.processor.wsdl_1.2.2.jar,com.tibco.cep.tpcl_5.6.1.jar,rocksdbjni-5.18.3.jar,com.tibco.xml.cxf.common_1.3.200.001.jar,org.genxdm.bridge.dom_1.2.2.jar,tomcat-dbcp.jar,com.tibco.xml.xmodel_1.0.900.001.jar,com.tibco.cep.studio.debug.core_5.6.1.jar,antlr-3.2.jar,com.tibco.cep.studio.debug.ui_5.6.1.jar,com.tibco.cep.sharedresource.as3_5.6.1.jar,jaxb-core.jar,catalina-tribes.jar,gwt-maps.jar,cep-kernel.jar,poi-ooxml-schemas-4.1.0.jar,jrt-fs.jar,poi-scratchpad-4.1.0.jar,cep-docker.jar,jackson-core-2.6.7.jar,httpcore-4.4.12.jar,com.tibco.cep.studio.tester.ui_5.6.1.jar,TIBCOrt.jar,lz4-java-1.6.0.jar,com.tibco.xml.cxf.runtime_1.3.200.002.jar,tomcat7-embed-websocket.jar,com.tibco.tibcoxml.net.eclipse_5.60.100.001.jar,cep-backingstore-bdb.jar,commons-codec-1.3.jar,gwt-servlet.jar,org.eclipse.emf.ecore.xmi_2.12.0.v20160420-0247.jar,com.tibco.genxdm.extras_1.3.0.004.jar,org.genxdm.xslt2.api_1.2.100.001.jar,com.tibco.cep.studio.wizard.hawk_5.6.1.jar";
				}
				String[] a = jarsToSkip.split(",");
				List<String> noScanList = Arrays.asList(a);
				return noScanList;
			}catch (Exception e) {
				logger.log(Level.ERROR, "Harmless - Failed to skip negative jars scanning ");
				return null;
			}
			
		}
	}


	/**
	 * Looks up the classes in the class path retrieved from "java.class.path"
	 * for {@link BEFunction} and {@link BEPackage} annotations
	 * @param catalog
	 * @param libPaths
	 * @param fcldr
	 * @param cp
	 * @param loadedURLS
	 * @param isConsole
	 * @return
	 */
	public static int lookupCategoriesInParallel(final FunctionsCatalog catalog, final URL[] libPaths, final IFunctionClassLoader fcldr, ClassPool cp,
			ConcurrentHashMap<URL, Boolean> loadedURLS, boolean isConsole) {
		init();
		int count = 0;
		if (libPaths == null || libPaths.length == 0) {
			return 0;
		}

		ClassLoader cldr = Thread.currentThread().getContextClassLoader();
		List<CtClass> annClasses = new ArrayList<>();
		try {
			Thread.currentThread().setContextClassLoader(fcldr.getClass().getClassLoader());
			try {
				/**
				 * ensure the classpool is aware of the annotation classes
				 */
				annClasses.add(cp.get(Enabled.class.getCanonicalName()));
				annClasses.add(cp.get(BEDeprecated.class.getCanonicalName()));
				annClasses.add(cp.get(BEPackage.class.getCanonicalName()));
				annClasses.add(cp.get(BEFunction.class.getCanonicalName()));
			} catch (Exception e) {
				logger.log(Level.FATAL, "Failed to initialize annotation classes to the Classpool", e);
				throw new IllegalStateException(e);
			}

			int numthreads = Integer.valueOf(System.getProperty("be.catalog.loader.threads", "8"));
			ExecutorService executor = Executors.newFixedThreadPool(numthreads);

			ExecutorCompletionService<Collection<CatalogEntry>> completionService = new ExecutorCompletionService<>(executor);

			Collection<Callable<Collection<CatalogEntry>>> tasklist = new ArrayList<Callable<Collection<CatalogEntry>>>();
			List<Future<Collection<CatalogEntry>>> futures = new ArrayList<>();
			for (URL url : libPaths) {
				if (loadedURLS.containsKey(url)) {
					continue;
				}
				JarScanner jarScanner = new JarScanner(cp, url, fcldr, loadedURLS, isConsole);
				futures.add(completionService.submit(jarScanner));
			}
			try {

				for (int k = 0; k < futures.size(); k++) {
					Collection<CatalogEntry> entries = completionService.take().get();
					for (CatalogEntry entry : entries) {
						FunctionsCategory javaCatalog = null;
						if (catalog.getCatalog(entry.getCatalogName()) == null) {
							javaCatalog = new RootCategory(entry.getCatalogName());
							catalog.register(entry.getCatalogName(), javaCatalog);
						} else {
							javaCatalog = catalog.getCatalog(entry.getCatalogName());
						}
						/**
						 * category names are in package format i.e. a.b.c
						 */
						String[] categoryParts = entry.getPackageName().split(PACKAGE_SEPARATOR);
						FunctionsCategory parent = javaCatalog;
						/**
						 * 
						 */
						ExpandedName catName = null;
						for (int i = 0; i < categoryParts.length; i++) {
							catName = makeName(catName, categoryParts[i]);
							FunctionsCategory category = parent.getSubCategory(catName);
							if (category == null) {
								category = new FunctionsCategoryImpl(catName);
								parent.registerSubCategory(category);
							}
							parent = category;

						}
						parent.registerPredicate(entry.getFunction());
						count++;
						logger.log(Level.TRACE, "adding function:" + entry.getFunction().getName());

						javaCatalog.prepare();
					}
				}
			} catch (Exception e) {
				logger.log(Level.FATAL, "Failed to execute annotation scanner tasks", e);
				throw new IllegalStateException(e);
			} finally {

				for (Future<Collection<CatalogEntry>> f : futures)
					f.cancel(true);

			}

			executor.shutdown();
		} finally {
			for (CtClass annClass : annClasses) {
				annClass.detach();
			}
			cp = null;
			Thread.currentThread().setContextClassLoader(cldr);
		}
		return count;
	}

	private static List<URL> getSystemClassPathUrls() {
		List<URL> urls;
		try {
			urls = findClassPathURL();
		} catch (IOException e) {
			logger.log(Level.FATAL, "Failed to get classpath jar urls", e);
			throw new IllegalStateException(e);
		}
		return urls;
	}


	/**
	 * Process each class using javassist libraries
	 * @param loadedURLs
	 * @param url The {@link URL} of the java class from class path
	 * @param cp The javassist {@link ClassPool} object
	 * @param delegateClassLoader  The delegate {@link ClassLoader}
	 * @param isConsole
	 * @param parentJarURL 
	 * @return
	 * @throws Exception
	 */
	private static Collection<CatalogEntry> processClassParallel(ConcurrentHashMap<URL, Boolean> loadedURLs, URL url, ClassPool cp, IFunctionClassLoader delegateClassLoader,
			boolean isConsole, URL parentJarURL) throws Exception {
		Collection<CatalogEntry> entries = new HashSet<>();
		if (loadedURLs.containsKey(url))
			return entries;

		URLConnection conn = url.openConnection();
		conn.setUseCaches(false);
		DataInputStream dstream = new DataInputStream(new BufferedInputStream(conn.getInputStream()));
		try {
			ClassFile cf = null;
			try {
				cf = new ClassFile(dstream);
			} catch (IOException e) {
				// TODO:Found a IOException loading a smartgwt class giving bad
				// magic number, probably because the class was JDK7 compilant
				// while javassist
				// is JDK6 compliant, need to retest after upgrade to the latest
				// javassist
				logger.log(Level.ERROR, "Failed to load class URL:%s", e, url);
				return entries;
			}
			String className = cf.getName();
			AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
			if (visible == null) {
				addLoadedURL(loadedURLs, url, false);
				return entries;
			}

			Annotation categoryAnnotation = visible.getAnnotation(BEPackage.class.getCanonicalName());
			/**
			 * return if there is no {@link BEPackage} annotation
			 */
			if (categoryAnnotation == null) {
				addLoadedURL(loadedURLs, url, false);
				return entries;
			}
			logger.log(Level.TRACE, String.format("Loading URL:%s", url.toString()));
			CtClass cl = cp.get(className);
			try {
				BEPackage packageAnnotation = getAnnotation(new CtClassAnnotationAdapter(cl), BEPackage.class);

				if (!getIsEnabled(packageAnnotation.enabled())) {
					if (parentJarURL != null) {
						addLoadedURL(loadedURLs, parentJarURL, true);
					}
					addLoadedURL(loadedURLs, url, true);
					return entries;
				}

				String catalogName = ((packageAnnotation.catalog() == null) || (packageAnnotation.catalog().isEmpty())) ? UNKNOWN : packageAnnotation.catalog();

				Collection<? extends CatalogEntry> loadedEntries = loadCatalogInParallel(catalogName, cl, delegateClassLoader, isConsole);
				entries.addAll(loadedEntries);
				if (loadedEntries.size() > 0) {
					addLoadedURL(loadedURLs, url, true);
				} else {
					addLoadedURL(loadedURLs, url, false);
				}
			} finally {
				if (cl != null)
					cl.detach();
			}
		} catch (Throwable t) {
			logger.log(Level.ERROR, t.getMessage());

		} finally {
			dstream.close();			
		}
		return entries;
	}

	/**
	 * Process each class using javassist libraries
	 * 
	 * @param catalog
	 *            The root {@link FunctionsCatalog} singleton
	 * @param url
	 *            The {@link URL} of the java class from class path
	 * @param cp
	 *            The javassist {@link ClassPool} object
	 * @param delegateClassLoader
	 *            The delegate {@link ClassLoader}
	 * @throws Exception
	 */
	private static int processClass(FunctionsCatalog catalog, ConcurrentHashMap<URL, Boolean> loadedURLs, URL url, ClassPool cp, IFunctionClassLoader delegateClassLoader,
			boolean isConsole, URL parentJarURL) throws Exception {
		int count = 0;
		if (loadedURLs.containsKey(url))
			return count;

		DataInputStream dstream = new DataInputStream(new BufferedInputStream(url.openStream()));
		try {
			ClassFile cf = null;
			try {
				cf = new ClassFile(dstream);
			} catch (IOException e) {
				// TODO:Found a IOException loading a smartgwt class giving bad
				// magic number, probably because the class was JDK7 compilant
				// while javassist
				// is JDK6 compliant, need to retest after upgrade to the latest
				// javassist
				logger.log(Level.ERROR, "Failed to load class URL:%s", e, url);
				return count;
			}
			String className = cf.getName();
			AnnotationsAttribute visible = (AnnotationsAttribute) cf.getAttribute(AnnotationsAttribute.visibleTag);
			if (visible == null) {
				addLoadedURL(loadedURLs, url, false);
				return count;
			}

			Annotation categoryAnnotation = visible.getAnnotation(BEPackage.class.getCanonicalName());
			/**
			 * return if there is no {@link BEPackage} annotation
			 */
			if (categoryAnnotation == null) {
				addLoadedURL(loadedURLs, url, false);
				return count;
			}
			logger.log(Level.TRACE, String.format("Loading URL:%s", url.toString()));
			CtClass cl = cp.get(className);
			try {
				BEPackage packageAnnotation = getAnnotation(new CtClassAnnotationAdapter(cl), BEPackage.class);

				if (!getIsEnabled(packageAnnotation.enabled())) {
					if (parentJarURL != null) {
						addLoadedURL(loadedURLs, parentJarURL, true);
					}
					addLoadedURL(loadedURLs, url, true);
					return count;
				}

				String catalogName = ((packageAnnotation.catalog() == null) || (packageAnnotation.catalog().isEmpty())) ? UNKNOWN : packageAnnotation.catalog();

				String treeName = catalogName;// + " Functions";
				FunctionsCategory javaCatalog = null;
				if (catalog.getCatalog(treeName) == null) {
					javaCatalog = new RootCategory(catalogName);
					catalog.register(treeName, javaCatalog);
				} else {
					javaCatalog = catalog.getCatalog(treeName);
				}
				int currCount = loadCatalog(javaCatalog, cl, delegateClassLoader, isConsole);
				count += currCount;
				javaCatalog.prepare();
				if (currCount > 0) {
					addLoadedURL(loadedURLs, url, true);
				} else {
					addLoadedURL(loadedURLs, url, false);
				}
			} finally {
				if (cl != null)
					cl.detach();
			}
		} catch (Throwable t) {
			logger.log(Level.ERROR, t.getMessage());
			t.printStackTrace();
		} finally {
			dstream.close();
			return count;
		}
	}

	protected static void addLoadedURL(ConcurrentHashMap<URL, Boolean> loadedURLs, URL url, boolean hasAnnotations) {
		if (!loadedURLs.containsKey(url)) {
			loadedURLs.put(url, hasAnnotations);
		}
	}


	/**
	 * Load the catalog root 
	 * @param catalogName the catalog root
	 * @param catClazz the category javassist class {@link CtClass}
	 * @param delegateClassLoader the delegate {@link ClassLoader}
	 * @param isConsole is called from studio console
	 * @return {@link Collection} of CatalogEntry
	 * @throws Exception
	 */
	private static Collection<CatalogEntry> loadCatalogInParallel(String catalogName, CtClass catClazz, IFunctionClassLoader delegateClassLoader,
			boolean isConsole) throws Exception {
		BEPackage packageAnnotation = getAnnotation(new CtClassAnnotationAdapter(catClazz), BEPackage.class);
		String categoryName = packageAnnotation.category();

		try {
			return loadCategoryInParallel(catalogName, categoryName, catClazz, delegateClassLoader, isConsole);
		} catch (LinkageError ex) {
			if (!PlatformUtil.INSTANCE.isStudioPlatform()) {
				logger.log(Level.DEBUG, ex, "Exception while loading function category '%s'.", categoryName);
				logger.log(Level.WARN, "Failed to fully load function category '%s', moving on to next category.", categoryName);
			}
		}
		return null;

	}

	/**
	 * Load the catalog root which is a child of {@link FunctionsCatalog}
	 * 
	 * @param catalog
	 *            the catalog root
	 * @param catClazz
	 *            the category javassist class {@link CtClass}
	 * @param delegateClassLoader
	 *            the delegate {@link ClassLoader}
	 * @param isConsole
	 * @throws Exception
	 * @return number of function entries
	 */
	private static int loadCatalog(FunctionsCategory catalog, CtClass catClazz, IFunctionClassLoader delegateClassLoader, boolean isConsole) throws Exception {
		int count = 0;
		BEPackage packageAnnotation = getAnnotation(new CtClassAnnotationAdapter(catClazz), BEPackage.class);
		String categoryName = packageAnnotation.category();

		/**
		 * category names are in package format i.e. a.b.c
		 */
		String[] categoryParts = categoryName.split(PACKAGE_SEPARATOR);
		FunctionsCategory parent = catalog;
		/**
		 * 
		 */
		ExpandedName catName = null;
		for (int i = 0; i < categoryParts.length; i++) {
			catName = makeName(catName, categoryParts[i]);
			FunctionsCategory category = parent.getSubCategory(catName);
			if (category == null) {
				category = new FunctionsCategoryImpl(catName);
				parent.registerSubCategory(category);
			}
			parent = category;

		}
		try {
			count = loadCategory(parent, catClazz, delegateClassLoader, isConsole);
		} catch (LinkageError ex) {
			if (!PlatformUtil.INSTANCE.isStudioPlatform()) {
				logger.log(Level.DEBUG, ex, "Exception while loading function category '%s'.", categoryName);
				logger.log(Level.WARN, "Failed to fully load function category '%s', moving on to next category.", categoryName);
			}
		} finally {
			return count;
		}

	}

	private static ExpandedName getParentFromPackage(String packageName) {
		/**
		 * category names are in package format i.e. a.b.c
		 */
		String[] categoryParts = packageName.split(PACKAGE_SEPARATOR);
		ExpandedName catName = null;
		for (int i = 0; i < categoryParts.length; i++) {
			catName = makeName(catName, categoryParts[i]);
		}
		return catName;
	}

	/**
	 * Loads individual category from implementation class {@link CtClass}
	 * 
	 * @param entries
	 * @param categoryName
	 * @param categoryName2
	 * @param parent
	 *            the parent category {@link FunctionsCategory}
	 * @param catClazz
	 *            the category implementation class {@link CtClass}
	 * @param delegateClassLoader
	 *            the delegate {@link ClassLoader}
	 * @param isConsole
	 * @return
	 * @throws Exception
	 */
	private static Collection<CatalogEntry> loadCategoryInParallel(String catalogName, String categoryName, CtClass catClazz,
			IFunctionClassLoader delegateClassLoader, boolean isConsole) throws Exception {
		Collection<CatalogEntry> entries = new HashSet<CatalogEntry>();
		ExpandedName parentName = getParentFromPackage(categoryName);
		// get class static methods
		Class<?> catClass = null;
		try {
			if (isConsole) {
				catClass = catClazz.toClass(delegateClassLoader.getLoader(), delegateClassLoader.getClass().getProtectionDomain());
			}
			if (catClass == null)
				catClass = catClazz.toClass(delegateClassLoader.getClass().getClassLoader(), delegateClassLoader.getClass().getProtectionDomain());
		} catch (CannotCompileException e) {
			// logger.log(Level.WARN,"Duplicate loading", e);
			if (isConsole) {
				catClass = delegateClassLoader.getClass(catClazz.getName());
			}
			if (catClass == null) {
				catClass = ((ClassLoader) delegateClassLoader.getClass().getClassLoader()).loadClass(catClazz.getName());
			}
			if (catClass == null) {
				throw new Exception(String.format("Failed to load function class %s", catClazz.getName()));
			}
		}
		CtMethod[] methods = catClazz.getDeclaredMethods();
		for (CtMethod m : methods) {
			BEFunction functionAnnotation = getAnnotation(new CtMethodAnnotationAdapter(m), BEFunction.class);

			boolean isFunctionEnabled = (functionAnnotation != null) && (functionAnnotation.enabled() != null) && getIsEnabled(functionAnnotation.enabled());
			if (!isFunctionEnabled) {
				continue;
			}
			// Check for mapper
			if (!functionAnnotation.mapper().enabled().value()) {
				JavaStaticFunction function = loadJavaStaticFunction(makeName(parentName, functionAnnotation.name()), functionAnnotation,
						getMethod(catClass, m, delegateClassLoader));


				logger.log(Level.TRACE, "loading function:" + function.getName());
				entries.add(new CatalogEntry(catalogName, categoryName, function));
			} else {
				MutableSchema schema = cf.createSchema();
				schema.setNamespace(catClazz.getName() + "/" + m.getName());
				JavaStaticFunctionWithXSLT function = loadJavaStaticFunctionWithXSLT(factory, schema, makeName(parentName, m.getName()), functionAnnotation,
						getMethod(catClass, m, delegateClassLoader));


				logger.log(Level.TRACE, "loading function:" + function.getName());
				entries.add(new CatalogEntry(catalogName, categoryName, function));
			}
		}

		return entries;
	}

	/**
	 * Loads individual category from implementation class {@link CtClass}
	 * 
	 * @param parent
	 *            the parent category {@link FunctionsCategory}
	 * @param catClazz
	 *            the category implementation class {@link CtClass}
	 * @param delegateClassLoader
	 *            the delegate {@link ClassLoader}
	 * @param isConsole
	 * @throws Exception
	 * @return number of function entries
	 */
	private static int loadCategory(FunctionsCategory parent, CtClass catClazz, IFunctionClassLoader delegateClassLoader, boolean isConsole) throws Exception {
		int count = 0;
		// get class static methods
		Class<?> catClass = null;
		try {
			if (isConsole) {
				catClass = catClazz.toClass(delegateClassLoader.getLoader(), delegateClassLoader.getClass().getProtectionDomain());
			}
			if (catClass == null)
				catClass = catClazz.toClass(delegateClassLoader.getClass().getClassLoader(), delegateClassLoader.getClass().getProtectionDomain());
		} catch (CannotCompileException e) {
			// logger.log(Level.WARN,"Duplicate loading", e);
			if (isConsole) {
				catClass = delegateClassLoader.getClass(catClazz.getName());
			}
			if (catClass == null) {
				catClass = ((ClassLoader) delegateClassLoader.getClass().getClassLoader()).loadClass(catClazz.getName());
			}
			if (catClass == null) {
				throw new Exception(String.format("Failed to load function class %s", catClazz.getName()));
			}
		}
		CtMethod[] methods = catClazz.getDeclaredMethods();
		for (CtMethod m : methods) {
			BEFunction functionAnnotation = getAnnotation(new CtMethodAnnotationAdapter(m), BEFunction.class);

			boolean isFunctionEnabled = (functionAnnotation != null) && (functionAnnotation.enabled() != null) && getIsEnabled(functionAnnotation.enabled());
			if (!isFunctionEnabled) {
				continue;
			}
			// Check for mapper
			if (!functionAnnotation.mapper().enabled().value()) {
				JavaStaticFunction function = loadJavaStaticFunction(makeName(parent.getName(), functionAnnotation.name()), functionAnnotation,
						getMethod(catClass, m, delegateClassLoader));
				parent.registerPredicate(function);
				count++;
				logger.log(Level.TRACE, "adding function:" + function.getName());
			} else {
				MutableSchema schema = cf.createSchema();
				schema.setNamespace(catClazz.getName() + "/" + m.getName());
				JavaStaticFunctionWithXSLT function = loadJavaStaticFunctionWithXSLT(factory, schema, makeName(parent.getName(), m.getName()),
						functionAnnotation, getMethod(catClass, m, delegateClassLoader));
				parent.registerPredicate(function);
				count++;
				logger.log(Level.TRACE, "adding function:" + function.getName());
			}
		}

		return count;
	}

	private static Method getMethod(Class<?> catClass, CtMethod m, IFunctionClassLoader delegateClassLoader) throws NoSuchMethodException, SecurityException,
			CannotCompileException, NotFoundException, ClassNotFoundException {
		for (Method classMethod : catClass.getDeclaredMethods()) {
			if (!m.getName().equals(classMethod.getName()))
				continue;
			if (m.getSignature().equals(JNISignature.generateMethodSignature(classMethod))) {
				return classMethod;
			}
		}
		throw new IllegalStateException(String.format("Method:%s not found in class:%s", m.getName(), catClass.getName()));
	}

	/**
	 * @param factory
	 * @param schema
	 * @param functionName
	 * @param fn
	 * @param method
	 * @return
	 * @throws Exception
	 */
	private static JavaStaticFunctionWithXSLT loadJavaStaticFunctionWithXSLT(XiFactory factory, MutableSchema schema, ExpandedName functionName, BEFunction fn,
			Method method) throws Exception {

		JavaStaticFunctionWithXSLT function = new JavaStaticFunctionWithXSLT(functionName, method);

		function.mappertype = fn.mapper().type().getTypeValue();
		function.isAsync = fn.async();
		function.m_inputElement = parseSmElement(factory, method.getDeclaringClass(), fn.mapper().inputelement());
		StringBuilder sb = new StringBuilder();
		if (fn.params() == null || fn.params().length == 0) {
			sb.append("No args specified.");
		} else {
			int i = 0;
			for (FunctionParamDescriptor param : fn.params()) {
				sb.append(param.type()).append(" ").append(param.name());
				if (i < fn.params().length)
					sb.append(",\n");
			}
		}
		function.args = sb.toString();
		function.deprecated = fn.deprecated().value();

		String[] domains = fn.domain();
		if (domains != null && domains.length > 0) {
			setDeprecatedFunctionDomains(fn, function, domains);
		} else {
			function.setFunctionDomains(fn.fndomain());
		}
		// function.isValidInActionOnly =
		// Arrays.asList(fn.domain()).contains("action");
		// function.isValidInQuery =
		// Arrays.asList(fn.domain()).contains("query");
		// function.isValidInBUI =
		// Arrays.asList(fn.domain()).contains("condition");

		function.reevaluate = fn.reevaluate();

		function.schema = schema;
		return function;

	}

	protected static void setDeprecatedFunctionDomains(BEFunction fn, JavaStaticFunction function, String[] domains) {
		List<FunctionDomain> depDomains = new ArrayList<>();
		// use deprecated domain setting
		for (String domain : domains) {
			String[] split = domain.split("\\,");
			for (String string : split) {
				if ((string = string.trim()).isEmpty()) {
					continue;
				}
				try {
					FunctionDomain val = FunctionDomain.valueOf(string.toUpperCase());
					if (val != null) {
						depDomains.add(val);
					}
				} catch (IllegalArgumentException e) {
					System.err.println("Domain '" + string.trim() + "' does not exist in FunctionDomain for function " + fn.name());
				}
			}
			if (depDomains.size() > 0) {
				function.setFunctionDomains((FunctionDomain[]) depDomains.toArray(new FunctionDomain[depDomains.size()]));
			} else {
				function.setFunctionDomains(fn.fndomain());
			}
		}
	}

	/**
	 * @param factory
	 * @param clazz
	 * @param mapperStr
	 * @return
	 * @throws Exception
	 */
	private static SmElement parseSmElement(XiFactory factory, Class<?> clazz, String mapperStr) throws Exception {

		if (mapperStr == null || mapperStr.isEmpty()) {
			return null;
		}
		XiNode inputElement = XiParserFactory.newInstance().parse(new InputSource(new StringReader(mapperStr)));
		XiNode schemaNode = XiChild.getChild(inputElement, ExpandedName.makeName(XSDLConstants.NAMESPACE, "schema"));
		if (schemaNode == null)
			return null;
		XiNode copyschemaNode = schemaNode.copy();
		XiNode attr = copyschemaNode.getAttribute(ExpandedName.makeName("targetNamespace"));
		if (attr == null) {
			copyschemaNode.setAttributeStringValue(ExpandedName.makeName("targetNamespace"), clazz.getName());
		}

		XiNode doc = factory.createDocument();
		doc.appendChild(copyschemaNode);

		String xsdschema = XiSerializer.serialize(doc);
		logger.log(Level.TRACE, xsdschema);

		InputSource is = new InputSource(new StringReader(mapperStr));
		is.setSystemId(copyschemaNode.getSystemId());

		DefaultSchema defaultSchema = null;

		try {
			defaultSchema = (DefaultSchema) SmParseSupport.parseSchema(is);
		} catch (Exception e) {
			e.printStackTrace();
		}

		SmElement el = SmRootElement.getBestRoot(defaultSchema);
		return el;
	}

	/**
	 * @param functionName
	 * @param fn
	 * @param m
	 * @return
	 */
	private static JavaStaticFunction loadJavaStaticFunction(ExpandedName functionName, BEFunction fn, Method m) {

		JavaStaticFunction function = new JavaStaticFunction(functionName, m);
		StringBuilder sb = new StringBuilder();
		if (fn.params() == null || fn.params().length == 0) {
			sb.append("No args specified.");
		} else {
			int i = 0;
			for (FunctionParamDescriptor param : fn.params()) {
				// sb.append(param.type()).append(" ").append(param.name());
				sb.append(param.name());
				if (++i < fn.params().length)
					sb.append(",\n");
			}
		}
		function.args = sb.toString();

		function.isAsync = fn.async();

		// function.isValidInActionOnly =
		// Arrays.asList(fn.domain()).contains("action");
		// function.isValidInBUI =
		// Arrays.asList(fn.domain()).contains("condition");
		// function.isValidInQuery =
		// Arrays.asList(fn.domain()).contains("query");
		// function.isValidInActionOnly =
		// Arrays.asList(fn.fndomain()).contains(FunctionDomain.ACTION);
		// function.isValidInBUI =
		// Arrays.asList(fn.fndomain()).contains(FunctionDomain.CONDITION);
		// function.isValidInQuery =
		// Arrays.asList(fn.fndomain()).contains(FunctionDomain.QUERY);
		String[] domains = fn.domain();
		if (domains != null && domains.length > 0) {
			setDeprecatedFunctionDomains(fn, function, domains);
		} else {
			function.setFunctionDomains(fn.fndomain());
		}

		function.deprecated = fn.deprecated().value();
		function.reevaluate = fn.reevaluate();
		return function;
	}

	/**
	 * @param parentName
	 * @param name
	 * @return
	 */
	protected static ExpandedName makeName(ExpandedName parentName, String name) {
		if (parentName != null) {
			if (parentName.getNamespaceURI() != null)
				return ExpandedName.makeName(parentName.getNamespaceURI() + "." + parentName.getLocalName(), name);
			else
				return ExpandedName.makeName(parentName.getLocalName(), name);
		} else {
			return ExpandedName.makeName(name);
		}
	}

	protected static boolean getIsEnabled(Enabled enabled) {
		if (enabled == null)
			return true;
		String nv = Boolean.toString(enabled.value());
		String propertyName = enabled.property();
		if (propertyName != null && !propertyName.isEmpty()) {
			String pv = System.getProperty(propertyName.trim(), nv);
			return Boolean.valueOf(pv).booleanValue();
		} else {
			return Boolean.valueOf(nv).booleanValue();
		}

	}

	private static class JSONAnnotationReader {

		private Map<String, Object> jsonVals;
		private Map<String, Object> currentMap;

		public JSONAnnotationReader(Map<String, Object> jsonVals) {
			this.jsonVals = jsonVals;
			this.currentMap = jsonVals;
		}
	
		public void parseJSON(JsonParser parser) throws Exception {

	        while (!parser.isClosed()) {
	            JsonToken token = parser.nextToken();

	            if (token == null)
	                break;
	            
	            switch(token) {
	            
	            case START_OBJECT:
	            	
	            	continue;
	                
	            case END_OBJECT:
	            	
	                break;
	                
	            case START_ARRAY:
	                break;
	                
	            case END_ARRAY:
	  
	                break;
	                
	            case FIELD_NAME:
	            	String name = parser.getCurrentName();
	            	if ("modificationStamp".equals(name)) {
	            		JsonToken valToken = parser.nextToken();
	            		long valueAsLong = parser.getValueAsLong();
	            		currentMap.put(name,  valueAsLong);
	            	} else if ("hasAnnotations".equals(name)) {
	            		JsonToken valToken = parser.nextToken();
	            		boolean valueAsBoolean = parser.getValueAsBoolean();
	            		currentMap.put(name,  valueAsBoolean);
	            	} else {
	            		if (!jsonVals.containsKey(name)) {
		            		Map<String, Object> fileVals = new LinkedHashMap<String, Object>();
		            		jsonVals.put(name, fileVals);
		            		currentMap = fileVals;
		            		continue;
		            	}
	            	}
	            	break;
	                
	            case VALUE_STRING:
	            case VALUE_NUMBER_INT:
	            case VALUE_NUMBER_FLOAT:
	            case VALUE_FALSE:
	            case VALUE_TRUE:
	            	handlePrimitiveValue(parser, token);
	                break;
	                
	            case VALUE_NULL:
	                break;
	            default:
	                throw new RuntimeException("Invalid Token");
	            }
	        }
		
		}

		private void handlePrimitiveValue(JsonParser parser, JsonToken token) throws Exception {
			if (token.equals(JsonToken.VALUE_STRING)) {
				boolean hasAnnotations = parser.getValueAsBoolean();
			} else if (token == JsonToken.VALUE_NUMBER_INT) {
				long modStamp = parser.getValueAsLong();
			} else if (token == JsonToken.VALUE_FALSE || token == JsonToken.VALUE_TRUE) {
				boolean hasAnnotations = parser.getValueAsBoolean();
			}
		}
		
	}
	
	/**
	 * @author Pranab Dhar
	 *
	 */
	public static class RootCategory extends CatalogRoot {

		public RootCategory(String categoryName) {
			super(ExpandedName.makeName(categoryName));
		}

		public RootCategory(String categoryName, boolean allowSubCategories, boolean allowPredicates) {
			super(ExpandedName.makeName(categoryName), allowSubCategories, allowPredicates);
		}

	}

	/**
	 * @author Pranab Dhar
	 *
	 * @param <S>
	 *            Represents CtClass or CtMethod
	 */
	public static abstract class AnnotationAdapter<S> {
		protected Object adapted;

		protected AnnotationAdapter(Object adapted) {
			this.adapted = adapted;
		}

		public abstract Object[] getAnnotations() throws ClassNotFoundException;
	}

	/**
	 * @author Pranab Dhar CtClass adapter
	 *
	 */
	public static class CtClassAnnotationAdapter extends AnnotationAdapter<CtClass> {
		public CtClassAnnotationAdapter(Object adapted) {
			super(adapted);
		}

		@Override
		public Object[] getAnnotations() throws ClassNotFoundException {
			return ((CtClass) adapted).getAnnotations();
		}
	}

	/**
	 * @author Pranab Dhar CtMethod adapter
	 */
	public static class CtMethodAnnotationAdapter extends AnnotationAdapter<CtClass> {
		public CtMethodAnnotationAdapter(Object adapted) {
			super(adapted);
		}

		@Override
		public Object[] getAnnotations() throws ClassNotFoundException {
			return ((CtMethod) adapted).getAnnotations();
		}
	}

	/**
	 * @param cl
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static <T, K extends AnnotationAdapter<S>, S> T getAnnotation(K cl, Class<T> clazz) throws ClassNotFoundException {
		for (Object ann : cl.getAnnotations()) {
			if (clazz.isAssignableFrom(ann.getClass())) {
				return clazz.cast(ann);
			}

		}
		return (T) null;
	}

	/**
	 * find List of URLs from a current JVM class path
	 * 
	 * @return
	 * @throws IOException
	 */
	public static List<URL> findClassPathURL() throws IOException {
		String classpath = System.getProperty("java.class.path");
		return getClassPathURLs(classpath);
	}

	/**
	 * find List of URLs from a given class path
	 * 
	 * @param standardClasspath
	 * @return
	 * @throws IOException
	 */
	public static List<URL> getClassPathURLs(String standardClasspath) throws IOException {
		final List<String> types = Arrays.asList(FILE_TYPES);
		String separator = File.pathSeparator;
		StringTokenizer st = new StringTokenizer(standardClasspath, separator);
		List<String> processedTokens = new ArrayList<String>();
		List<URL> files = new ArrayList<URL>();
		while (st.hasMoreTokens()) {
			String entry = st.nextToken().trim();
			//Bala: This check is to prevent BE from scanning the /, /lib folders in cases
			//when a certain home folder is empty in the engine TRA file eg. tibco.env.HAWK_HOME=
			if (entry != null && skipScanPaths.contains(entry)) {
				continue;
			}
			File entryFile = new File(entry);
			if (!entryFile.exists()) {
				continue;
			}
			if (processedTokens.contains(entry)) {
				continue;
			}
			
			Path path = entryFile.toPath();
			if (Files.isSymbolicLink(path)) {
				Path linkPath = Files.readSymbolicLink(path);
				entryFile = linkPath.toFile();
			}
			if (!entryFile.exists()) {
				continue;
			}
			if (!entryFile.canRead()) {
				continue;
			}
			

			if (files.contains(entryFile.toURI().toURL())) {
				continue;
			}
			if (entryFile.isDirectory()) {
				if (entryFile.list() == null) {
					continue;
				}
				processDirectory(files, entryFile, new ArrayList<File>());
				processedTokens.add(entry);
			} else {
				processedTokens.add(entry);
				String extension = getExtension(entryFile.getName());
				if (types.contains(extension.toLowerCase())) {
					files.add(entryFile.toURI().toURL());
					// System.out.println(entryFile.getPath());
				}
			}
		}
		return files;
	}

	/**
	 * Collect all files in the given directory
	 * 
	 * @param files
	 * @param entryFile
	 * @throws IOException
	 */
	private static void processDirectory(List<URL> files, File parentFolder, List<File> processedDirs) throws IOException {
		final List<String> types = Arrays.asList(FILE_TYPES);
		// entryFile is a directory
		File[] fileNames = parentFolder.listFiles();
		if (fileNames == null || fileNames.length == 0)
			return;
		for (File entryFile : fileNames) {
			if (!entryFile.exists()) {
				continue;
			}
			if (files.contains(entryFile.toURI().toURL())) {
				continue;
			}
			Path path = entryFile.toPath();
			if (Files.isSymbolicLink(path)) {
				Path linkPath = Files.readSymbolicLink(path);
				entryFile = linkPath.toFile();
			}
			if (!entryFile.exists()) {
				continue;
			}
			if (!entryFile.canRead()) {
				continue;
			}
			if (entryFile.isDirectory()) {
//				Path path = entryFile.toPath();
//				if (Files.isSymbolicLink(path)) {
//					Path linkPath = Files.readSymbolicLink(path);
//					entryFile = linkPath.toFile();
//				}
				if (processedDirs.contains(entryFile)) {
					continue;
				}
				// add the directory so we don't re-process symbolic links
				processedDirs.add(entryFile);
				processDirectory(files, entryFile, processedDirs);
			} else {
				String extension = getExtension(entryFile.getName());
				if (extension != null && types.contains(extension.toLowerCase()) && !files.contains(entryFile)) {
					files.add(entryFile.toURI().toURL());
				}
			}
		}
	}

	/**
	 * Get the extension of a file.
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtension(String fileName) {
		String ext = null;
		String s = fileName;
		int i = s.lastIndexOf('.');

		if (i >= 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public static void setContextClassLoader(ClassLoader classLoader) {
		if (classLoader != null) {
			contextClassLoader.set(classLoader);
		}
	}

	public static ClassLoader getContextClassLoader() {
		return contextClassLoader.get();
	}

}

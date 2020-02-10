package com.tibco.cep.studio.codegen;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarOutputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.parser.codegen.CodeGenHelper;
import com.tibco.be.parser.codegen.CodeGenerator;
import com.tibco.be.parser.codegen.CompilationFailedException;
import com.tibco.be.parser.codegen.FileStreamGenerator;
import com.tibco.be.parser.codegen.IConceptCodeGenerator;
import com.tibco.be.parser.codegen.IDBConceptCodeGenerator;
import com.tibco.be.parser.codegen.IDecisionTableCodeGenerator;
import com.tibco.be.parser.codegen.IEventCodeGenerator;
import com.tibco.be.parser.codegen.IJavaCodeGenerator;
import com.tibco.be.parser.codegen.IJavaResourceGenerator;
import com.tibco.be.parser.codegen.IMetricConceptCodeGenerator;
import com.tibco.be.parser.codegen.IModuleCodeGenerator;
import com.tibco.be.parser.codegen.IPojoConceptCodeGenerator;
import com.tibco.be.parser.codegen.IRuleCodeGenerator;
import com.tibco.be.parser.codegen.IRuleFunctionCodeGenerator;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.FileUtils;
import com.tibco.be.util.OversizeStringConstants;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.java.JavaResource;
import com.tibco.cep.designtime.model.java.JavaSource;
import com.tibco.cep.designtime.model.process.ProcessConcept;
import com.tibco.cep.designtime.model.process.SubProcessConcept;
import com.tibco.cep.designtime.model.rule.Rule;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.designtime.model.rule.RuleTemplate;
import com.tibco.cep.studio.core.util.DirectoryContentToJarDumper;
import com.tibco.cep.studio.core.util.packaging.impl.DefaultRuntimeClassesPackager;

/**
 * Created by IntelliJ IDEA. User: aamaya Date: Apr 14, 2008 Time: 5:16:54 PM To
 * change this template use File | Settings | File Templates.
 * @deprecated Use {@link FileStreamGenerator }
 */
public abstract class BaseGenerator implements CodeGenerator {
	protected CodeGenContext context;
	protected IProgressMonitor monitor;
	protected String outputFolderPath;
	protected DefaultRuntimeClassesPackager packager;
	protected File tmpDir;
	protected File srcDir;
	protected File jarFile;
	protected File[] sourceDirs;
	protected String classpath;
	boolean debug = false;
	protected String[] annotationProcessors = new String[0];

	BaseGenerator(DefaultRuntimeClassesPackager packager, IProgressMonitor monitor) {
		setPackager(packager);
		setDebug(packager.isCompileWithDebug());
		if (monitor == null) {
			setMonitor(new NullProgressMonitor());
		} else {
			setMonitor(monitor);
		}
	}

	/**
	 * @return the packager
	 */
	public DefaultRuntimeClassesPackager getPackager() {
		return packager;
	}
	
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * @param packager the packager to set
	 */
	public void setPackager(DefaultRuntimeClassesPackager packager) {
		this.packager = packager;		
	}

	/**
	 * @return the context
	 */
	public CodeGenContext getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(CodeGenContext context) {
		this.context = context;
	}

	/**
	 * @return the monitor
	 */
	public IProgressMonitor getMonitor() {
		return monitor;
	}

	/**
	 * @param monitor
	 *            the monitor to set
	 */
	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * @return the pathToDirectory
	 */
	public String getOutputFolderPath() {
		return outputFolderPath;
	}

	/**
	 * @param folderPath the pathToDirectory to set
	 */
	public void setOutputFolderPath(String folderPath) {
		this.outputFolderPath = folderPath;
	}
	
	

	/**
	 * @return the tmpDir
	 */
	public File getTmpDir() {
		return tmpDir;
	}



	/**
	 * @param tmpDir the tmpDir to set
	 */
	public void setTmpDir(File tmpDir) {
		this.tmpDir = tmpDir;
	}
	


	/**
	 * @return the srcDir
	 */
	public File getSrcDir() {
		return srcDir;
	}



	/**
	 * @param srcDir the srcDir to set
	 */
	public void setSrcDir(File srcDir) {
		this.srcDir = srcDir;
	}

	/**
	 * @return the jarFile
	 */
	public File getJarFile() {
		return jarFile;
	}

	/**
	 * @param jarFile the jarFile to set
	 */
	public void setJarFile(File jarFile) {
		this.jarFile = jarFile;
	}
	
	

	/**
	 * @return the sourceDirs
	 */
	public File[] getSourceDirs() {
		return sourceDirs;
	}

	/**
	 * @param sourceDirs the sourceDirs to set
	 */
	public void setSourceDirs(File[] sourceDirs) {
		this.sourceDirs = sourceDirs;
	}
	
	

	/**
	 * @return the classpath
	 */
	public String getClasspath() {
		return classpath;
	}

	/**
	 * @param classpath the classpath to set
	 */
	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}
	
	/**
	 * @return
	 */
	public String[] getAnnotationProcessors() {
		return annotationProcessors;
	}
	
	/**
	 * @param annotationProcessors
	 */
	public void setAnnotationProcessors(String[] annotationProcessors) {
		this.annotationProcessors = annotationProcessors;
	}

	@Override
	public void init() throws Exception {
		System.setProperty(FunctionsCatalogLookup.SHOW_COMPILER_WARNINGS,
				Boolean.TRUE.toString());
		monitor.subTask("Creating temp compilation directory...");
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		final BEProperties props = BEProperties.loadDefault();
		if(packager.isDeleteTempFiles()) { 
			// dont care where the files are generated if they are being deleted
			setOutputFolderPath(props.getString("be.packaging.tempdir", System
					.getProperty("java.io.tmpdir")));
		} else {
			
			if ((null == packager.getPathToDirectory()) || 
					"".equals(packager.getPathToDirectory())) { 
				// if empty path given set to temp dir
				setOutputFolderPath(props.getString("be.packaging.tempdir", System
						.getProperty("java.io.tmpdir")));
			} else {
				// set to specified path
				setOutputFolderPath(packager.getPathToDirectory());
			}
		}

		final File directory = new File(getOutputFolderPath());
		//Creates BE dir with timestamp to avoid creation problems on unix.
		//Take temp dir as a property in case any customization is desired 
		
		String dir = System.getenv("be.codegen.rootDirectory");
		if(dir == null || dir.length() <= 0) {
			dir = "BE_" + System.currentTimeMillis();
		}
        setTmpDir(new File(directory, dir));
		CodeGenHelper.deleteFileOrDirectory(getTmpDir());
		if (!getTmpDir().mkdirs()) {
			throw new IOException("Failed to create BE directory: "
					+ getTmpDir().getCanonicalPath());
		}// if

		setSrcDir(new File(getTmpDir(), "src"));
		final File eventDir = new File(getTmpDir(), "eventSrc");
		final File timeEventDir = new File(getTmpDir(), "timeEventSrc");
		final File ruleDir = new File(getTmpDir(), "ruleSrc");
		final File conceptDir = new File(getTmpDir(), "conceptSrc");
		final File scorecardDir = new File(getTmpDir(), "scorecardSrc");
		final File ruleFunctionDir = new File(getTmpDir(), "ruleFunctionSrc");
		final File javaSrcDir = new File(getTmpDir(),"javaSrc");
		if (!getSrcDir().mkdirs() || !eventDir.mkdirs() || !ruleDir.mkdirs()
				|| !conceptDir.mkdirs() || !timeEventDir.mkdirs()
				|| !ruleFunctionDir.mkdirs() || !scorecardDir.mkdirs()|| !javaSrcDir.mkdirs()) {
			throw new IOException("Failed to create src directory.");
		}// if
		monitor.worked(1);
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		setJarFile(new File(getTmpDir(), Constants.NAME_JAR_FILE));
		monitor.subTask("Creating runtime class jar file:"
				+ getJarFile().getPath());
		if (!getJarFile().createNewFile()) {
			throw new IOException("Failed to create " + Constants.NAME_JAR_FILE);
		}// if
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		monitor.worked(1);

		setSourceDirs(new File[] { srcDir, eventDir, conceptDir, ruleDir,
				ruleFunctionDir, timeEventDir, scorecardDir,javaSrcDir });
		setClasspath(packager.getExtraClassPath() + System.getProperty("path.separator")
				+ System.getProperty("java.class.path", ""));

		final Properties oversizeStringConstants = new Properties();
		final Map<?,?> ruleFnUsages = new ConcurrentHashMap<>();
		
		CodeGenContext cgContext = new CodeGenContext();
		cgContext.put(CodeGenConstants.BE_CODEGEN_PACKAGER, getPackager());
		cgContext.put(CodeGenConstants.BE_CODEGEN_ONTOLOGY, getPackager().getOntology());
		cgContext.put(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS,oversizeStringConstants);
		cgContext.put(CodeGenConstants.BE_CODEGEN_EVENT_DIR, eventDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_TIME_EVENT_DIR, timeEventDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR, conceptDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_SCORECARD_DIR, scorecardDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_JAVA_SRC_DIR, javaSrcDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_RULE_DIR, ruleDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR,ruleFunctionDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE,ruleFnUsages);
		cgContext.put(CodeGenConstants.BE_CODEGEN_CLASSPATH, getClasspath());
		cgContext.put(CodeGenConstants.BE_CODEGEN_ERRORLIST, new CopyOnWriteArrayList());
		cgContext.put(CodeGenConstants.BE_CODEGEN_DEBUG, isDebug());
		cgContext.put(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE, new ConcurrentHashMap<>());
		setContext(cgContext);
	}
	
	@Override
	public InputStream compile() throws Exception {
		try {
			getMonitor().subTask("Compiling runtime code...");
			compileSource(getClasspath(), getSourceDirs(),isDebug(),false,annotationProcessors);
			getMonitor().worked(1);	
			getMonitor().subTask("Packaging runtime code...");
			final FileOutputStream fileOutputStream = new FileOutputStream(
					getJarFile());
			final JarOutputStream jarOutputStream = new JarOutputStream(
					fileOutputStream);
			final DirectoryContentToJarDumper dumper = new DirectoryContentToJarDumper(
					jarOutputStream);
			for (int ii = 0; ii < getSourceDirs().length; ii++) {
				dumper.dumpToJar(getSourceDirs()[ii]);
			}
			
			jarOutputStream.close();
			getMonitor().worked(1);
			return new FileInputStream(getJarFile());
			
		} finally {
			 
		}
	}
	
	@Override
	public void close() {
		
		if (packager.isDeleteTempFiles()) {
			getMonitor().subTask("Deleting temp files/directories...");
			CodeGenHelper.deleteFileOrDirectory(getTmpDir());
			getMonitor().worked(1);
		}// if
	}
	
	@Override
	public void generate() throws Exception {
		CodeGenContext ctx = getContext();
		monitor.subTask("Generating Rules/Rulefunction code...");
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		generateRuleAndRuleFunctions(ctx);

		monitor.worked(1);
		final List<RuleError> errors = (List<RuleError>) ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
		if (errors.size() > 0) {
			final StringBuilder buffer = new StringBuilder(
					"Failed to parse rules or rule functions:\n");
			for (Iterator i = errors.iterator(); i.hasNext();) {
				final RuleError error = (RuleError) i.next();
				if (error.getName() != null) {
					buffer.append(error.getName()).append(":\n");
				}
				buffer.append(error.toString()).append("\n");
			}// for
			throw new RuntimeException(buffer.toString());
		}// if

		Collection col = getPackager().getOntology().getEntities();
		Iterator itr = col.iterator();

		while (itr.hasNext()) {
			Entity e = (Entity) itr.next();
			if (e instanceof JavaSource) {

				monitor.subTask("Generating Java Sources entity :"+ e.getFullPath());
				if (monitor.isCanceled()) {
	    			throw new CoreException(Status.CANCEL_STATUS);
	    		}
				generateJava((JavaSource)e, ctx);
				monitor.worked(1);
				
			} else if(e instanceof JavaResource) {
				monitor.subTask("Generating Java resource entity :"+ e.getFullPath());
				if (monitor.isCanceled()) {
	    			throw new CoreException(Status.CANCEL_STATUS);
	    		}
				generateJavaResource((JavaResource)e, ctx);
				monitor.worked(1);
			} else if (CodeGenHelper.isModuleEntity(e)) {
				
				monitor.subTask("Generating DBConcepts entity :"+ e.getFullPath());
				if (monitor.isCanceled()) {
	    			throw new CoreException(Status.CANCEL_STATUS);
	    		}
				generateModule(e, ctx);
				monitor.worked(1);
				
			} else if (e instanceof Event) {

				monitor.subTask("Generating Event :" + e.getFullPath());
				if (monitor.isCanceled()) {
	    			throw new CoreException(Status.CANCEL_STATUS);
	    		}
				generateEvent((Event) e,ctx);
				monitor.worked(1);

			} else if (e instanceof Concept) {
				monitor.subTask("Generating Concept :" + e.getFullPath());
				if (monitor.isCanceled()) {
	    			throw new CoreException(Status.CANCEL_STATUS);
	    		}
				generateConcept((Concept) e,ctx);
				monitor.worked(1);
			}
		}
		
		monitor.subTask("Generating Oversize String constants...");
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		generateOversizeStringConstants(getSrcDir(),
				(Properties)ctx.get(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS));
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		monitor.worked(1);
		
	}

	private void generateRuleAndRuleFunctions(CodeGenContext ctx) throws Exception {
	
		
		generateRuleFunctions(ctx);
		
		
        generateDecisionTables(ctx);       
        
        
        CodeGenHelper.flattenRuleFnUsages((Map)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE));
        
        
        generateRules(ctx);
        
        
	}

	private void generateDecisionTables(CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_DECISION_TABLE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IDecisionTableCodeGenerator) {
				((IDecisionTableCodeGenerator) o).generateDecisionTables(ctx);
			}
		}	
	}

	private void generateRules(CodeGenContext ctx) throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_RULE_DIR);
        if(!targetDir.exists()) {
            targetDir.mkdirs();
        }
		
        for(Iterator rules = o.getRules().iterator(); rules.hasNext();) {
        	Rule rul = (Rule)rules.next();
        	try {
        		generateRule(rul,ctx);            
			} catch (Exception e) {
				throw new Exception("Failed to generate rule:"+rul.getFullPath(),e);
			}
        }
        
        for (final RuleTemplate rul : o.getRuleTemplates()) {
        	try {
        		generateRule(rul, ctx);            
			} catch (Exception e) {
				throw new Exception("Failed to generate rule:" + rul.getFullPath(), e);
			}
        }

	}

	private void generateRule(Rule rul, CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_RULE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IRuleCodeGenerator) {
				((IRuleCodeGenerator) o).generateRule(rul, ctx);
			}
		}	
		
	}

	private void generateRuleFunctions(CodeGenContext ctx) throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
		List ruleErrorList = (List)ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
		File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR);
		if (!targetDir.exists()) {
			targetDir.mkdirs();
		}

		Iterator<RuleFunction>  ruleFns = o.getRuleFunctions().iterator();
		while (ruleFns.hasNext()) {
			RuleFunction fn = ruleFns.next();
			try{
				generateRuleFunction(fn,ctx);
			}catch (Exception e) {
				throw new Exception("Failed to generate rule:"+fn.getFullPath(),e);
			}
		}
	}

	private void generateRuleFunction(RuleFunction fn, CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
		.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_RULE_FUNCTION);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IRuleFunctionCodeGenerator) {
				((IRuleFunctionCodeGenerator) o).generateRuleFunction(fn, ctx);
			}
		}		
	}

	private void generateConcept(Concept cept, CodeGenContext ctx) throws Exception {
        if (cept.isPOJO()) {
        
        	generatePOJOConcept(cept,ctx);

        } else if(CodeGenHelper.isDBConcept(cept)) {

        	generateDBConcept(cept,ctx);
        	
        } else if(cept.isMetric()) {

        	generateMetricConcept(cept,ctx);
        	
        } else if(cept instanceof ProcessConcept || cept instanceof SubProcessConcept){
        	
        	return;
        	
        } else {
        	
        	generateDefaultConcept(cept,ctx);
        }
		
	}
	
	private void generateMetricConcept(Concept cept,CodeGenContext ctx) throws Exception  {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_METRIC_CONCEPT);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IMetricConceptCodeGenerator) {
				((IMetricConceptCodeGenerator) o).generateConcept(cept, ctx);
			}
		}	
	}
	
	private void generateDefaultConcept(Concept cept,CodeGenContext ctx) throws Exception  {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_CONCEPT);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IConceptCodeGenerator) {
				((IConceptCodeGenerator) o).generateConcept(cept, ctx);
			}

		}	
	}

	private void generatePOJOConcept(Concept cept, CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_POJO_CONCEPT);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IPojoConceptCodeGenerator) {
				((IPojoConceptCodeGenerator) o).generateConcept(cept, ctx);
			}

		}	
		
	}
	
	private void generateDBConcept(Concept cept, CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_DB_CONCEPT);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IDBConceptCodeGenerator) {
				((IDBConceptCodeGenerator) o).generateConcept(cept, ctx);
			}

		}	
		
	}

	private void generateJava(JavaSource e, CodeGenContext ctx) throws Exception {
		System.out.println(e.getFullPath());
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_JAVA);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IJavaCodeGenerator) {
				((IJavaCodeGenerator) o).generateJava(e, ctx);
			}
			
		}		
	}
	private void generateJavaResource(JavaResource e, CodeGenContext ctx) throws Exception {
		System.out.println(e.getFullPath());
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_JAVA_RESOURCE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IJavaResourceGenerator) {
				((IJavaResourceGenerator) o).generateJavaResource(e, ctx);
			}

		}		
	}
	private void generateEvent(Event e, CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_EVENT);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IEventCodeGenerator) {
				((IEventCodeGenerator) o).generateEvent(e, ctx);
			}

		}		
	}

	public void generateModule(final Entity e, final CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_MODULE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IModuleCodeGenerator) {
				((IModuleCodeGenerator) o).generateModule(e, ctx);
			}

		}
	}

	

	

	

	

//	public void writeJavaClassToFile(JavaClass jClass, String modelPath,
//			File targetDir) throws IOException {
//		if (jClass != null) {
//			JavaFile jFile = new JavaFile(jClass.getName());
//			jFile.setPackage(ModelNameUtil.modelPathToExternalForm(modelPath));
//			jFile.addClass(jClass);
//			CodeGenHelper.writeFile(jFile, targetDir,
//					ModelNameUtil.RULE_SEPARATOR_CHAR);
//		}
//	}

	

	/**
	 * Compiles all java files under a directory (subdirectories will be
	 * searched) targetDir will be created it if doesn't exist, but will not be
	 * deleted if it does exist.
	 * 
	 * @param classPath
	 *            classpath to be passed to javac
	 * @param srcDirs
	 *            array of root directories of source trees
	 * @param targetDir
	 *            directory to output classfiles
	 * @param targetDir
	 *            directory to output classfiles
	 * @param debug
	 *            if true passes the -g flag to javac
	 * @throws com.tibco.be.parser.codegen.CompilationFailedException
	 *             if compilation was not successful.
	 */
	public void compileSource(String classPath, File[] srcDirs, boolean debug,boolean isAnnotationProcessingOnly,String[] annotationProcessors)
			throws CompilationFailedException {
		compileSource(classPath, srcDirs, null, debug,isAnnotationProcessingOnly,annotationProcessors);
	}

	public void compileSource(String classPath, File[] srcDirs, File targetDir,
			boolean debug,boolean isAnnotationProcessingOnly,String[] annotationProcessors) throws CompilationFailedException {
		ArrayList srcFiles = new ArrayList();
		for (int ii = 0; ii < srcDirs.length; ii++) {
			FileUtils.appendFilesRecursively(srcDirs[ii], srcFiles,
					new FileFilter() {
						public boolean accept(File file) {
							return file.getName().endsWith(
									CodeGenConstants.JAVA_FILE_EXTENSION);
						}
					});
		}

//		CodeGenHelper.compileInMemory(classPath, srcFiles, targetDir, debug);
		CodeGenHelper.compileSourceFiles(classPath, srcFiles, targetDir, debug,isAnnotationProcessingOnly,annotationProcessors);
	}

	
	


    public void generateOversizeStringConstants(File srcDir,  Properties oversizeStringConstants) throws IOException {
        if(!srcDir.exists())srcDir.mkdirs();

        FileOutputStream propsOS = new FileOutputStream(new File(srcDir, OversizeStringConstants.PROPERTY_FILE_NAME));
        oversizeStringConstants.store(propsOS, null);
        propsOS.flush();
        propsOS.close();

        File javaDir = new File(srcDir, ModelNameUtil.GENERATED_PACKAGE_PREFIX.replace('.', '/') + "/");
        if(!javaDir.exists())javaDir.mkdirs();

        Writer writer = CodeGenHelper.makeFileWriter(new File(javaDir, OversizeStringConstants.JAVA_FILE_NAME));
        writer.write(OversizeStringConstants.JAVA_FILE_BODY);
        writer.flush();
        writer.close();
    }

//    private JavaClass makeEngineAction(String name, String[] functions, Ontology ontology) {
//        JavaClass cls = new JavaClass(name);
//        cls.setAccess("static public");
//        cls.setSuperClass(CGConstants.actionImpl);
//
//        MethodRec con = new MethodRec(name);
//        con.setAccess("public");
//        con.setReturnType("");
//        con.setBody("super(null);");
//        cls.addMethod(con);
//
//        MethodRec exec = new MethodRec("public", "void", "execute");
//        exec.addArg("Object[]", "objects");
//        cls.addMethod(exec);
//
//        StringBuilder sb = new StringBuilder();
//        StringBuilder sourceText = new StringBuilder();
//        if(sb != null) {
//            for(int i = 0; i < functions.length; i++) {
//                RuleFunction fn = ontology.getRuleFunction(functions[i]);
//                sb.append(ModelNameUtil.ruleFnClassFSName(fn))
//                        .append('.')
//                        .append(fn.getName())
//                        .append("();")
//                        .append(CGConstants.BRK);
//                sourceText.append(ModelUtils.convertPathToPackage(functions[i]))
//                        .append("();")
//                        .append(CGConstants.BRK);
//            }
//        }
//        exec.setBody(sb);
//        cls.setSourceText(sourceText.toString());
//
//        return cls;
//    }

	public ClassLoader getCompilationClassLoader() {
		return null;
	}

    

    

}

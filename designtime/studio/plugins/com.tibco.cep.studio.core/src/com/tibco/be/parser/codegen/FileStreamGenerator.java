package com.tibco.be.parser.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.codegen.stream.AbstractResource;
import com.tibco.be.parser.codegen.stream.AbstractStreamGenerator;
import com.tibco.be.parser.codegen.stream.JavaFileLocation;
import com.tibco.be.parser.codegen.stream.JavaFolderLocation;
import com.tibco.be.parser.codegen.stream.RootFolderLocation;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.util.BEProperties;
import com.tibco.be.util.OversizeStringConstants;
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
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.util.packaging.RuntimeClassesPackager;

public class FileStreamGenerator extends AbstractStreamGenerator implements CodeGenerator {
	
	// codegen context
	protected CodeGenContext context;

	// progress monitor
	private IProgressMonitor monitor;

	// Designtime ontology
	private Ontology ontology;

	// source locations
	private JavaFolderLocation[] sourceDirs;
	
	private RootFolderLocation rootDir;
	
	private JavaFolderLocation srcDir;

	private RuntimeClassesPackager packager;

	private String outputFolderPath;

	private File tmpDir;

	private Integer numThreads;

	private Boolean isGenerateParallel;

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
	public FileStreamGenerator(RuntimeClassesPackager packager, 
			Ontology ontology,
			String classpath, 
			ClassLoader clazzLoader,
			IProgressMonitor monitor,
			boolean debug,
			boolean isAnnotationProcessingOnly,
			String[] annotationProcessors) {
		super(classpath,
				clazzLoader,
				debug,
				StudioCore.getSourceJavaVersion(),
				StudioCore.getTargetJavaVersion(),
				CodeGenConstants.charset,
				isAnnotationProcessingOnly,
				annotationProcessors);
		this.packager = packager;
		this.ontology = ontology;
		this.compilationClassLoader = clazzLoader;
		this.debug = debug;
		this.classPath = classpath;
		
		if(monitor == null) {
			this.monitor = new NullProgressMonitor();
		}
	}
	

	@Override
	public void close() {
		this.fileManager = null;
	}



	@Override
	public InputStream compile() throws Exception {
		return compileInMemory(
				getCompilationClassLoader(),
				getClassPath(), 
				getFileManager(),
				isDebug());
	}


	@Override
	public void generate() throws Exception {
		long start = System.currentTimeMillis();
		if(isGenerateParallel) {
			generateParallel();
		} else {
			generateSerial();
		}
		long end = System.currentTimeMillis();
		StudioCorePlugin.log(String.format("Code generation completed in %d milliseconds",(end-start)));
	}

	
	public static class ParallelCodeGenResult {
		private List<Exception> exceptions = new CopyOnWriteArrayList<>();

		public void logError(Exception e) {
			exceptions.add(e);
			
		}
		
		List<Exception> getExceptions() {
			return Collections.unmodifiableList(exceptions);
		}
		
	}
	
	private void generateParallel() throws Exception {
		int numthreads = Integer.valueOf(System.getProperty("be.codegen.threads", "8"));
		ExecutorService executor = Executors.newFixedThreadPool(numthreads);
		ExecutorCompletionService<List<ParallelCodeGenResult>> executorService = new ExecutorCompletionService<>(executor);
		List<Future<ParallelCodeGenResult>> futures =  new CopyOnWriteArrayList<Future<ParallelCodeGenResult>>();
		final CodeGenContext ctx = getContext();
		ctx.put(CodeGenConstants.BE_CODEGEN_PARALLEL_EXECUTOR_SERVICE, executorService);
		ctx.put(CodeGenConstants.BE_CODEGEN_PARALLEL_FUTURES,futures);
		ctx.put(CodeGenConstants.BE_CODEGEN_PARALLEL_CODEGEN_RESULT,new ParallelCodeGenResult());
		
		final ParallelCodeGenResult result =  (ParallelCodeGenResult) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_CODEGEN_RESULT);
		final ExecutorCompletionService<ParallelCodeGenResult> es = (ExecutorCompletionService<ParallelCodeGenResult>) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_EXECUTOR_SERVICE);
		
		monitor.subTask("Generating Rules/Rulefunction code...");
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		generateRuleAndRuleFunctions(ctx);
		

		Collection col = getOntology().getEntities();
		Iterator itr = col.iterator();

		while (itr.hasNext()) {
			final Entity e = (Entity) itr.next();
			Future<ParallelCodeGenResult> f = es.submit(new Runnable() {
				
				@Override
				public void run() {
					try {
						if (e instanceof JavaSource) {

							monitor.subTask("Generating Java Sources entity :"+ e.getFullPath());
							if (monitor.isCanceled()) {
				    			throw new CoreException(Status.CANCEL_STATUS);
				    		}
							generateJava((JavaSource)e, ctx);
							monitor.worked(1);
							
						} else if (e instanceof JavaResource) {

							monitor.subTask("Generating Java Resource entity :"+ e.getFullPath());
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
					} catch(Exception e) {
						result.logError(e);
					}
					
				}
			}, result);
			futures.add(f);
			
		}
		for (int k = 0; k < futures.size(); k++) {
			ParallelCodeGenResult entries = es.take().get();
		}
		if (!result.getExceptions().isEmpty()) {
			final StringBuffer buffer = new StringBuffer(
					"Failed to generate code:\n");
			for (Exception e : result.getExceptions()) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				buffer.append(sw.toString()).append(":\n");
			}
			throw new CompilationFailedException(buffer.toString());
		}
		
		monitor.worked(1);
		final List<RuleError> errors = (List<RuleError>) ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
		if (errors.size() > 0) {
			final StringBuffer buffer = new StringBuffer(
					"Failed to parse rules or rule functions:\n");
			for (Iterator i = errors.iterator(); i.hasNext();) {
				final RuleError error = (RuleError) i.next();
				if (error.getName() != null) {
					buffer.append(error.getName()).append(":\n");
				}
				buffer.append(error.toString()).append("\n");
			}// for
			throw new CompilationFailedException(buffer.toString());
		}// if
		
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
		/**
		 * if
		 */
		if(packager != null && !packager.isDeleteTempFiles()){
			File codegenFolder = getTmpDir();
			if(!codegenFolder.exists()) {
				codegenFolder.mkdirs();
			}
			
			generateCodeGenSource(codegenFolder,getRootDir());
		}
		
	}


	public void generateSerial() throws Exception {
		CodeGenContext ctx = getContext();
		monitor.subTask("Generating Rules/Rulefunction code...");
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		generateRuleAndRuleFunctions(ctx);

		monitor.worked(1);
		final List<RuleError> errors = (List<RuleError>) ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
		if (errors.size() > 0) {
			final StringBuffer buffer = new StringBuffer(
					"Failed to parse rules or rule functions:\n");
			for (Iterator i = errors.iterator(); i.hasNext();) {
				final RuleError error = (RuleError) i.next();
				if (error.getName() != null) {
					buffer.append(error.getName()).append(":\n");
				}
				buffer.append(error.toString()).append("\n");
			}// for
			throw new CompilationFailedException(buffer.toString());
		}// if

		Collection col = getOntology().getEntities();
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
				
			} else if (e instanceof JavaResource) {

				monitor.subTask("Generating Java Resource entity :"+ e.getFullPath());
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
		/**
		 * if
		 */
		if(packager != null && !packager.isDeleteTempFiles()){
			File codegenFolder = getTmpDir();
			if(!codegenFolder.exists()) {
				codegenFolder.mkdirs();
			}
			
			generateCodeGenSource(codegenFolder,getRootDir());
		}
		
	}



	private void generateCodeGenSource(File parentFolder,JavaFolderLocation folder) throws Exception {
		
		if(folder.hasChildren()) {
			AbstractResource filesAndFolders[] = folder.files();
			for(AbstractResource file:filesAndFolders) {
				if(file.isFolder()){
					File currentFolder = new File(parentFolder,file.getName());
					if(!currentFolder.exists()){
						currentFolder.mkdirs();
					}
					generateCodeGenSource(currentFolder,(JavaFolderLocation) file);
				} else {
					File currentFile = new File(parentFolder,file.getName());
					FileOutputStream fos = new FileOutputStream(currentFile);
					InputStream is = ((JavaFileLocation)file).openInputStream();
					is.reset();
					int nRead;
			    	byte[] data = new byte[16384];

			    	while ((nRead = is.read(data, 0, data.length)) != -1) {
			    	  fos.write(data, 0, nRead);
			    	}
			    	fos.flush();
			    	fos.close();
			    	is.close();
				}
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
        	
        } else if(cept instanceof ProcessConcept || cept instanceof SubProcessConcept ){
        	return;
        	
        } else {
        	
        	generateDefaultConcept(cept,ctx);
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
				((IDBConceptCodeGenerator) o).generateConceptStream(cept, ctx);
			}

		}	
		
	}
	
	

	private void generateDecisionTablesInParallel(final CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_DECISION_TABLE);
		final ParallelCodeGenResult result =  (ParallelCodeGenResult) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_CODEGEN_RESULT);
		final List<Future<ParallelCodeGenResult>> futures = (List<Future<ParallelCodeGenResult>>) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_FUTURES);
		final ExecutorCompletionService<ParallelCodeGenResult> es = (ExecutorCompletionService<ParallelCodeGenResult>) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_EXECUTOR_SERVICE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IDecisionTableCodeGenerator) {
				Future<ParallelCodeGenResult> future = es.submit(new Runnable() {
					
					@Override
					public void run() {
						try {
							((IDecisionTableCodeGenerator) o).generateDecisionTablesStream(ctx);
						} catch (Exception e) {
							result.logError(e);
						}
						
					}
				}, result);
				futures.add(future);
			}
		}	
	}
	
	

	private void generateDecisionTables(CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_DECISION_TABLE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IDecisionTableCodeGenerator) {
				((IDecisionTableCodeGenerator) o).generateDecisionTablesStream(ctx);
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
				((IConceptCodeGenerator) o).generateConceptStream(cept, ctx);
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
				((IEventCodeGenerator) o).generateEventStream(e, ctx);
			}

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
				((IMetricConceptCodeGenerator) o).generateConceptStream(cept, ctx);
			}
		}	
	}

	private void generateJava(JavaSource e, CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_JAVA);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IJavaCodeGenerator) {
				((IJavaCodeGenerator) o).generateJavaStream(e, ctx);
			}
			
		}		
	}
	private void generateJavaResource(JavaResource e, CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_JAVA_RESOURCE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IJavaResourceGenerator) {
				((IJavaResourceGenerator) o).generateJavaResourceStream(e, ctx);
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
				((IModuleCodeGenerator) o).generateModuleStream(e, ctx);
			}

		}
	}



	public void generateOversizeStringConstants(JavaFolderLocation srcDir,  Properties oversizeStringConstants) throws IOException {
		
        JavaFileLocation file = srcDir.addFile("",OversizeStringConstants.PROPERTY_FILE_NAME);
        OutputStream propsOS = file.openOutputStream();
        oversizeStringConstants.store(propsOS, null);
        propsOS.flush();
        propsOS.close();
        
        file = srcDir.addFile(ModelNameUtil.GENERATED_PACKAGE_PREFIX,OversizeStringConstants.JAVA_FILE_NAME);
        Writer writer = file.openWriter();

//        File javaDir = new File(srcDir, ModelNameUtil.GENERATED_PACKAGE_PREFIX.replace('.', '/') + "/");
//        if(!javaDir.exists())javaDir.mkdirs();

//        Writer writer = CodeGenHelper.makeFileWriter(new File(javaDir, OversizeStringConstants.JAVA_FILE_NAME));
        writer.write(OversizeStringConstants.JAVA_FILE_BODY);
        writer.flush();
        writer.close();
    }



	private void generatePOJOConcept(Concept cept, CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_POJO_CONCEPT);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IPojoConceptCodeGenerator) {
				((IPojoConceptCodeGenerator) o).generateConceptStream(cept, ctx);
			}

		}	
		
	}
	
	
	
	private void generateRuleInParallel(final Rule rul, final CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
				.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_RULE);
		final ParallelCodeGenResult result =  (ParallelCodeGenResult) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_CODEGEN_RESULT);
		final List<Future<ParallelCodeGenResult>> futures = (List<Future<ParallelCodeGenResult>>) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_FUTURES);
		final ExecutorCompletionService<ParallelCodeGenResult> es = (ExecutorCompletionService<ParallelCodeGenResult>) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_EXECUTOR_SERVICE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			if (o instanceof IRuleCodeGenerator) {
				Future<ParallelCodeGenResult> future = es.submit(new Runnable() {
					
					@Override
					public void run() {
						try {
							((IRuleCodeGenerator) o).generateRuleStream(rul, ctx);
						} catch (Exception e) {
							result.logError(e);
						}
						
					}
				}, result);
				futures.add(future);
				
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
				((IRuleCodeGenerator) o).generateRuleStream(rul, ctx);
			}
		}	
		
	}
	
	private void generateRuleAndRuleFunctionsInParallel(CodeGenContext ctx)
			throws Exception {

		generateRuleFunctionsInParallel(ctx);

		generateDecisionTablesInParallel(ctx);

		CodeGenHelper.flattenRuleFnUsages((Map) ctx
				.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE));

		generateRulesInParallel(ctx);

	}
	
	private void generateRuleAndRuleFunctions(CodeGenContext ctx)
			throws Exception {

		generateRuleFunctions(ctx);

		generateDecisionTables(ctx);

		CodeGenHelper.flattenRuleFnUsages((Map) ctx
				.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE));

		generateRules(ctx);

	}

	private void generateRuleFunctionInParallel(final RuleFunction fn, final CodeGenContext ctx) throws Exception {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] extensions = reg
		.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_RULE_FUNCTION);
		final ParallelCodeGenResult result =  (ParallelCodeGenResult) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_CODEGEN_RESULT);
		final List<Future<ParallelCodeGenResult>> futures = (List<Future<ParallelCodeGenResult>>) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_FUTURES);
		final ExecutorCompletionService<ParallelCodeGenResult> es = (ExecutorCompletionService<ParallelCodeGenResult>) ctx.get(CodeGenConstants.BE_CODEGEN_PARALLEL_EXECUTOR_SERVICE);
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement element = extensions[i];
			final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_GENERATOR);
			
			if (o instanceof IRuleFunctionCodeGenerator) {
				
			    Future<ParallelCodeGenResult> future = es.submit(new Runnable() {

					@Override
					public void run() {
						try {
							((IRuleFunctionCodeGenerator) o).generateRuleFunctionStream(fn, ctx);
						} catch (Exception e) {
							
							result.logError(e);
						}
						
					}
			    	
			    }, result);
			    futures.add(future);
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
				((IRuleFunctionCodeGenerator) o).generateRuleFunctionStream(fn, ctx);
			}
		}		
	}
	
	private void generateRuleFunctionsInParallel(CodeGenContext ctx) throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
//		List ruleErrorList = (List)ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
//		File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR);
//		if (!targetDir.exists()) {
//			targetDir.mkdirs();
//		}
//
		Iterator<RuleFunction>  ruleFns = o.getRuleFunctions().iterator();
		while (ruleFns.hasNext()) {
			RuleFunction fn = ruleFns.next();
			try{
				generateRuleFunctionInParallel(fn,ctx);
			}catch (Exception e) {
				throw new Exception("Failed to generate rule:"+fn.getFullPath(),e);
			}
		}
	}
	
	private void generateRuleFunctions(CodeGenContext ctx) throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
//		List ruleErrorList = (List)ctx.get(CodeGenConstants.BE_CODEGEN_ERRORLIST);
//		File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR);
//		if (!targetDir.exists()) {
//			targetDir.mkdirs();
//		}
//
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
	
	private void generateRulesInParallel(CodeGenContext ctx) throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
//		File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_RULE_DIR);
//        if(!targetDir.exists()) {
//            targetDir.mkdirs();
//        }
		
        for(Iterator rules = o.getRules().iterator(); rules.hasNext();) {
        	Rule rul = (Rule)rules.next();
        	try {
        		generateRuleInParallel(rul,ctx);            
			} catch (Exception e) {
				throw new Exception("Failed to generate rule:"+rul.getFullPath(),e);
			}
        }

        for (final RuleTemplate rul : o.getRuleTemplates()) {
            try {
                generateRuleInParallel(rul, ctx);
            } catch (Exception e) {
                throw new Exception("Failed to generate rule:" + rul.getFullPath(), e);
            }
        }

    }
	
	


	private void generateRules(CodeGenContext ctx) throws Exception {
		Ontology o = (Ontology)ctx.get(CodeGenConstants.BE_CODEGEN_ONTOLOGY);
//		File targetDir = (File)ctx.get(CodeGenConstants.BE_CODEGEN_RULE_DIR);
//        if(!targetDir.exists()) {
//            targetDir.mkdirs();
//        }
		
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

	/**
	 * @return the context
	 */
	public CodeGenContext getContext() {
		return context;
	}

	/**
	 * @return the monitor
	 */
	public IProgressMonitor getMonitor() {
		return monitor;
	}
	
	/**
	 * @return the ontology
	 */
	public Ontology getOntology() {
		return ontology;
	}

	/**
	 * @return the sourceDirs
	 */
	public JavaFolderLocation[] getSourceDirs() {
		return sourceDirs;
	}

	public RootFolderLocation getRootDir() {
		return rootDir;
	}
	
	 @Override
	public void init() throws Exception {
		System.setProperty(FunctionsCatalogLookup.SHOW_COMPILER_WARNINGS,
				Boolean.TRUE.toString());
		this.isGenerateParallel = Boolean.valueOf(System.getProperty("be.codegen.parallel",Boolean.FALSE.toString()));
		this.numThreads = Integer.valueOf(System.getProperty("be.codegen.parallel.numthreads","8"));
		monitor.subTask("Creating temp compilation directory...");
		if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
		
		final BEProperties props = BEProperties.loadDefault();
		
		super.init();	
		if(packager != null && !packager.isDeleteTempFiles()) { 
			
			if ((null == packager.getPathToDirectory()) || 
					"".equals(packager.getPathToDirectory())) { 
				// if empty path given set to temp dir
				setOutputFolderPath(props.getString("be.packaging.tempdir", System
						.getProperty("java.io.tmpdir")));
			} else {
				// set to specified path
				setOutputFolderPath(packager.getPathToDirectory());
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
		}

		
		this.rootDir = getFileManager().getRootFolder("Root");
		setSrcDir(rootDir.addFolder("src"));
		final JavaFolderLocation eventDir = rootDir.addFolder("eventSrc");
		final JavaFolderLocation timeEventDir = rootDir.addFolder("timeEventSrc");
		final JavaFolderLocation ruleDir = rootDir.addFolder("ruleSrc");
		final JavaFolderLocation conceptDir = rootDir.addFolder("conceptSrc");
		final JavaFolderLocation scorecardDir = rootDir.addFolder("scorecardSrc");
		final JavaFolderLocation ruleFunctionDir = rootDir.addFolder("ruleFunctionSrc");
		final JavaFolderLocation javaSrcDir = rootDir.addFolder("javaSrc");
		

		setSourceDirs(new JavaFolderLocation[] { srcDir, eventDir, conceptDir, ruleDir,
				ruleFunctionDir, timeEventDir, scorecardDir,javaSrcDir });
//		setClasspath(packager.getExtraClassPath() + System.getProperty("path.separator")
//				+ System.getProperty("java.class.path", ""));

		final Properties oversizeStringConstants = new Properties();
		final Map<?,?> ruleFnUsages = new ConcurrentHashMap<>();
		
		CodeGenContext cgContext = new CodeGenContext();
		cgContext.put(CodeGenConstants.BE_CODEGEN_PACKAGER, new Object());
		cgContext.put(CodeGenConstants.BE_CODEGEN_ONTOLOGY, getOntology());
		cgContext.put(CodeGenConstants.BE_CODEGEN_OVERSIZE_STRING_CONSTANTS,oversizeStringConstants);
		cgContext.put(CodeGenConstants.BE_CODEGEN_EVENT_DIR, eventDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_TIME_EVENT_DIR, timeEventDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_CONCEPT_DIR, conceptDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_SCORECARD_DIR, scorecardDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_RULE_DIR, ruleDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_DIR,ruleFunctionDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_JAVA_SRC_DIR,javaSrcDir);
		cgContext.put(CodeGenConstants.BE_CODEGEN_RULEFUNCTION_USAGE,ruleFnUsages);
		cgContext.put(CodeGenConstants.BE_CODEGEN_CLASSPATH, getCompilationClassLoader());
		cgContext.put(CodeGenConstants.BE_CODEGEN_ERRORLIST, new CopyOnWriteArrayList());
		cgContext.put(CodeGenConstants.BE_CODEGEN_DEBUG, isDebug());
		cgContext.put(CodeGenConstants.BE_CODEGEN_PROP_INFO_CACHE, new ConcurrentHashMap<>());
		setContext(cgContext);
		
	}

	    /**
	 * @param context the context to set
	 */
	public void setContext(CodeGenContext context) {
		this.context = context;
	}

	/**
	 * @param monitor the monitor to set
	 */
	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}
	
	/**
	 * @param ontology the ontology to set
	 */
	public void setOntology(Ontology ontology) {
		this.ontology = ontology;
	}
	
	/**
	 * @param sourceDirs the sourceDirs to set
	 */
	public void setSourceDirs(JavaFolderLocation[] sourceDirs) {
		this.sourceDirs = sourceDirs;
	}


	@Override
	public void log(String msg,Throwable e) {
		StudioCorePlugin.log(msg,e);
		
	}


	@Override
	public void debug(String msg,Object...args) {
		StudioCorePlugin.debug(msg,args);
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
	 * @return
	 */
	public JavaFolderLocation getSrcDir() {
		return srcDir;
	}
	
	/**
	 * @param srcDir
	 */
	public void setSrcDir(JavaFolderLocation srcDir) {
		this.srcDir = srcDir;
	}

}

package com.tibco.cep.decision.table.codegen;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarOutputStream;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.parser.codegen.JavaClassWriter;
import com.tibco.be.parser.codegen.JavaFileWriter;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.adapters.mutable.MutableRuleFunctionAdapter;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.util.DirectoryContentToJarDumper;
import com.tibco.cep.studio.util.logger.PluginLoggerImpl;

public class DTCodegenUtil {
	public static final String BUI_PREPEND_CLASSPATH = "tibco.bui.codegen.prepend_classpath";
	public static final String ENABLE_JAVASSIST = "bui.codegen.enable_javassist";
	public static final String WRITE_JAVA_FILES = "bui.codegen.write_java_files";
	public static final String WRITE_JAR_FILE = "bui.codegen.generate_jar";
//	public static final String CODEGEN_EFFECTIVE_EXPIRY_DATE_FORMAT = "yyyy:MM:dd:HH:mm:ss 'V1'";
	public static final String CODEGEN_EFFECTIVE_EXPIRY_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
	public static final FileFilter CLASS_FILTER = new FileFilter() {
		public boolean accept(File pathname) {
			return pathname.isDirectory() || pathname.getName().endsWith(".class");
		}
		
	};
		
	public static JavaFileWriter makeOptimizedDTImpl(Ontology ontology, DTCodegenTableContext ctx
			, Map<String, Map<String, int[]>> propInfoCache) throws IOException
	{
		MutableRuleFunctionAdapter mrf = getMutableRuleFunction(ontology, ctx.table);
        if(mrf != null) {
	        JavaClassWriter jClass = OptimizedDTClassGenerator.makeJavaClass(ctx, mrf, ontology, propInfoCache);
	        JavaFileWriter jFile = makeDTImplJavaFile(mrf, jClass);
	        return jFile;
        }
    	return null;
    }
	
	private static JavaFileWriter makeDTImplJavaFile(RuleFunction vrf, JavaClassWriter jclass) throws IOException {
//        JavaClass jclass = new JavaClass(ModelNameUtil.vrfImplClassShortName(table.getName()));
//        jclass.setAccess("public");
//        jclass.addMethod(dtJavaFunc);
//        jclass.addInterface(VirtualRuleFunctionImpl.class.getName());
//        jclass.addInterface(NeedsStaticInitialization.class.getName());
//        
//        JavaFile jfile = new JavaFile(jclass.getName());
//        jfile.setPackage(ModelNameUtil.vrfImplPkg(vrf));
//        jfile.addClass(jclass);
        JavaFileWriter jfile = new JavaFileWriter(jclass.getName(),ModelNameUtil.vrfImplPkg(vrf));
        jfile.addClass(jclass);
        return jfile;
	}
	
	
	
	public static MutableRuleFunctionAdapter getMutableRuleFunction(Ontology ontology, Table table) {
    	if(table != null && ontology != null) {
    		String ruleFnProjectPath = getRuleFunctionOntologyPath(table);
    		if(ruleFnProjectPath != null && ruleFnProjectPath.length() > 0) {
    			com.tibco.cep.designtime.model.rule.RuleFunction rf = ontology.getRuleFunction(ruleFnProjectPath);
    			MutableRuleFunctionAdapter cgrfa = new MutableRuleFunctionAdapter(rf);
//    			if(rf instanceof MutableRuleFunction) {
//                    return (MutableRuleFunction)rf;
//    			}
    			return cgrfa;
    		}
    	}
    	return null;
    }
    
    public static String getRuleFunctionOntologyPath(Table table) {
    	String ruleFnProjectPath = table.getImplements();
        if(ruleFnProjectPath != null && ruleFnProjectPath.length() > 0) {
            if(!ruleFnProjectPath.startsWith("/")) {
            	ruleFnProjectPath = "/" + ruleFnProjectPath;
            }
        }
        return ruleFnProjectPath;
    }

    public static File getClassDir(File tempDir) {
    	return new File(tempDir, "class");
    }

    
    
    //writes a jar only if the property is set to true
    //compilation dir should contain the classes dir which contains the be dir
    public static boolean conditionalJarUpClassDirectory(File compilationDir, File jarTargetDir, Properties props) throws IOException {
    	return conditionalJarUpDirectory(getClassDir(compilationDir), jarTargetDir, props);
    }
    //classesDir should contain the be/gen/... directory structure
    public static boolean conditionalJarUpDirectory(File classesDir, File jarTargetDir, Properties props) throws IOException {
    	if(props != null && Boolean.parseBoolean(props.getProperty(DTCodegenUtil.WRITE_JAR_FILE, "false"))) {
    		File jarFile = new File(jarTargetDir, jarTargetDir.getName() + ".jar");
    		jarUpDirectory(classesDir, jarFile);
    		return true;
    	}
    	return false;
    }

    public static void jarUpDirectory(File classesDir, File jarFile) throws IOException {
    	if(jarFile.exists() && jarFile.isFile()) jarFile.delete();
        final FileOutputStream fileOutputStream = new FileOutputStream(jarFile);
        final JarOutputStream jarOutputStream = new JarOutputStream(fileOutputStream);
        final DirectoryContentToJarDumper dumper = new DirectoryContentToJarDumper(jarOutputStream);
        try {
        	dumper.dumpToJar(classesDir, CLASS_FILTER);
        } finally {
        	jarOutputStream.close();
        	fileOutputStream.close();
        }
    }
    
    private static File tempFile(File baseDir, String packageName, JavaClassWriter jClass, String ext, boolean mkDirs) {
    	String packagePath = packageName.replace('.', File.separatorChar);
        File dir = new File(baseDir, packagePath);
        if(mkDirs && !dir.exists()) dir.mkdirs();
        File file = new File(dir, jClass.getName() + ext);
        return file;
    }
    
    private static boolean debugLogger(Object logger) {
    	if(logger==null) return false;
    	if(logger instanceof com.tibco.cep.kernel.service.logging.Logger) {
    		return ((com.tibco.cep.kernel.service.logging.Logger)logger).isEnabledFor(Level.DEBUG);
    	}
//    	if(logger instanceof PluginLoggerImpl) {
//    		return ((PluginLoggerImpl)logger).isEnabledFor(Level.DEBUG);
//    	}
    	return false;
    }
    
    private static void logDebug(Object logger, Throwable thr) {
    	if(debugLogger(logger)) {
	    	if(logger==null) return;
	    	if(logger instanceof com.tibco.cep.kernel.service.logging.Logger) {
	    		((com.tibco.cep.kernel.service.logging.Logger)logger).log(Level.DEBUG, thr, thr.getMessage());
	    	}
//	    	if(logger instanceof PluginLoggerImpl) {
//	    		((PluginLoggerImpl)logger).logDebug(thr.getMessage(), thr);
//	    	}
    	}
    }
    
    private static void logDebug(Object logger, String message) {
    	if(debugLogger(logger)) {
	    	if(logger==null) return;
	    	if(logger instanceof com.tibco.cep.kernel.service.logging.Logger) {
	    		((com.tibco.cep.kernel.service.logging.Logger)logger).log(Level.DEBUG, message);
	    	}
	    	if(logger instanceof PluginLoggerImpl) {
	    		((PluginLoggerImpl)logger).logDebug(message);
	    	}
    	}
    }

	//TODO populate the oversizeStringConstants and ruleFnUsages during dt codegen
	public static void generateDecisionTableJavaFile(DTCodegenGlobalContext ctx, DTCodegenTableContext tableCtx) throws IOException
	{
		JavaFileWriter jfwriter = makeOptimizedDTImpl(ctx.o, tableCtx, ctx.propInfoCache);
		jfwriter.setTargetDir(ctx.targetDir);
		jfwriter.writeFile();
	}
	
	public static void generateDecisionTableJavaStream(DTCodegenGlobalContext ctx, DTCodegenTableContext tableCtx) throws IOException
	{
		JavaFileWriter jfwriter = makeOptimizedDTImpl(ctx.o, tableCtx, ctx.propInfoCache);
		jfwriter.setTargetFolder(ctx.targetLoc);
		jfwriter.writeStream();
	}
	
	
    public static StudioProjectConfiguration getSPC(Ontology ontology) {
    	StudioProjectConfigurationManager spcm = StudioProjectConfigurationManager.getInstance();
    	if(spcm != null && ontology != null && ontology.getName() != null) return spcm.getProjectConfiguration(ontology.getName());
    	else return null;
    }
}
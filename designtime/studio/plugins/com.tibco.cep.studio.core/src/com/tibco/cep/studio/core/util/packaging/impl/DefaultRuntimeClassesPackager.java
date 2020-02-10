package com.tibco.cep.studio.core.util.packaging.impl;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;

import com.tibco.be.parser.codegen.CodeGenerator;
import com.tibco.be.parser.codegen.FileStreamGenerator;
import com.tibco.be.parser.codegen.stream.StreamClassLoaderImpl;
import com.tibco.be.parser.semantic.FunctionsCatalogLookup;
import com.tibco.be.util.packaging.Constants;
import com.tibco.be.util.packaging.descriptors.MutableServiceArchive;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.studio.codegen.BulkGenerator;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.configuration.manager.StudioProjectConfigurationManager;
import com.tibco.cep.studio.core.functions.annotations.AnnotationProcessorImpl;
import com.tibco.cep.studio.core.preferences.StudioCorePreferenceConstants;
import com.tibco.cep.studio.core.util.packaging.RuntimeClassesPackager;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Feb 14, 2007
 * Time: 3:26:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultRuntimeClassesPackager implements RuntimeClassesPackager {


	public static final String DEFAULT_COMPILER = "FileSystem";//$NON-NLS-1$
    public static final String IN_MEMORY_COMPILER = "InMemory";//$NON-NLS-1$
	private MutableServiceArchive serviceArchive;
    private Ontology ontology;
    
    private boolean compileWithDebug;
    private boolean deleteTempFiles;
    private String pathToDirectory = null;
    private String extraClassPath = null;
    private boolean console = false;
    private List barModels;
    private IProgressMonitor monitor;
    private InputStream compileInputStream = null;
    private boolean useLegacyCompiler = false;


    /**
     * @param serviceArchive
     * @param ontology
     * @param pathToDirectory
     * @param isConsole TODO
     * @param extraClassPath
     * @param compileWithDebug
     * @param deleteTempFiles
     * @param monitor
     * @param useLegacyCompiler TODO
     * @throws Exception
     */
    public DefaultRuntimeClassesPackager(MutableServiceArchive serviceArchive, Ontology ontology,
                                         String pathToDirectory, boolean isConsole,
                                         String extraClassPath, boolean compileWithDebug,
                                         boolean deleteTempFiles, IProgressMonitor monitor, boolean useLegacyCompiler)
            throws Exception {
    	setMonitor(monitor);
    	monitor.beginTask("Build runtime classes...",IProgressMonitor.UNKNOWN);
    	monitor.subTask("Setting runtime package parameters...");
    	if (monitor.isCanceled()) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
    	setUseLegacyCompiler(useLegacyCompiler);
        setServiceArchive(serviceArchive);
        setOntology(ontology);
        setConsole(isConsole);
        setCompileWithDebug(compileWithDebug);
        setDeleteTempFiles(deleteTempFiles);
        setPathToDirectory(pathToDirectory);
        setExtraClassPath(extraClassPath);
        setBarModels(new ArrayList());
        setCompileWithDebug(compileWithDebug);
        monitor.worked(1);
    }
    
    /**
     * @param serviceArchive
     * @param ontology
     * @param pathToDirectory
     * @param extraClassPath
     * @param compileWithDebug
     * @param deleteTempFiles
     * @param useLegacyCompiler TODO
     * @throws Exception
     */
    public DefaultRuntimeClassesPackager(MutableServiceArchive serviceArchive,
			Ontology ontology, String pathToDirectory,boolean isConsole, String extraClassPath,
			boolean compileWithDebug, boolean deleteTempFiles, boolean useLegacyCompiler) throws Exception {
		this(serviceArchive, ontology, pathToDirectory, isConsole,
				extraClassPath, compileWithDebug, deleteTempFiles, new NullProgressMonitor(), useLegacyCompiler);
	}
    
    
    

    /**
	 * @return the useLegacyCompiler
	 */
	public boolean isUseLegacyCompiler() {
		return useLegacyCompiler;
	}

	/**
	 * @param useLegacyCompiler the useLegacyCompiler to set
	 */
	public void setUseLegacyCompiler(boolean useLegacyCompiler) {
		this.useLegacyCompiler = useLegacyCompiler;
	}

	/**
	 * @return the monitor
	 */
	public IProgressMonitor getMonitor() {
		return monitor;
	}




	/**
	 * @param monitor the monitor to set
	 */
	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}




	/**
	 * @return the serviceArchive
	 */
	public MutableServiceArchive getServiceArchive() {
		return serviceArchive;
	}


	/**
	 * @param serviceArchive the serviceArchive to set
	 */
	public void setServiceArchive(MutableServiceArchive serviceArchive) {
		this.serviceArchive = serviceArchive;
	}




	/**
	 * @return the ontology
	 */
	public Ontology getOntology() {
		return ontology;
	}


	/**
	 * @param ontology the ontology to set
	 */
	public void setOntology(Ontology ontology) {
		this.ontology = ontology;
	}


	/**
	 * @return the compileWithDebug
	 */
	public boolean isCompileWithDebug() {
		return compileWithDebug;
	}


	/**
	 * @param compileWithDebug the compileWithDebug to set
	 */
	public void setCompileWithDebug(boolean compileWithDebug) {
		this.compileWithDebug = compileWithDebug;
	}


	/**
	 * @return the deleteTempFiles
	 */
	public boolean isDeleteTempFiles() {
		return deleteTempFiles;
	}


	/**
	 * @param deleteTempFiles the deleteTempFiles to set
	 */
	public void setDeleteTempFiles(boolean deleteTempFiles) {
		this.deleteTempFiles = deleteTempFiles;
	}


	/**
	 * @return the pathToDirectory
	 */
	public String getPathToDirectory() {
		return pathToDirectory;
	}


	/**
	 * @param pathToDirectory the pathToDirectory to set
	 */
	public void setPathToDirectory(String pathToDirectory) {
		this.pathToDirectory = pathToDirectory;
	}


	/**
	 * @return the extraClassPath
	 */
	public String getExtraClassPath() {
		return extraClassPath;
	}


	/**
	 * @param extraClassPath the extraClassPath to set
	 */
	public void setExtraClassPath(String extraClassPath) {
		this.extraClassPath = extraClassPath;
	}


	/**
	 * @return the barModels
	 */
	public List getBarModels() {
		return barModels;
	}


	/**
	 * @param barModels the barModels to set
	 */
	public void setBarModels(List barModels) {
		this.barModels = barModels;
	}


	/**
	 * @return
	 */
	public boolean isConsole() {
		return console;
	}

	/**
	 * @param console
	 */
	public void setConsole(boolean console) {
		this.console = console;
	}

	/**
	 * @return
	 */
	public InputStream getCompileInputStream() {
		return compileInputStream;
	}

	/**
	 * @param compileInputStream
	 */
	public void setCompileInputStream(InputStream compileInputStream) {
		this.compileInputStream = compileInputStream;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.util.packaging.Packager#close()
	 */
	public void close() throws Exception {
    	CodeGenerator bg = null;
    	if(useLegacyCompiler){
    		bg = new BulkGenerator(this, monitor);
    	} else {
    		ClassLoader pc = null;
    		if(console ) {
    			pc =   new StreamClassLoaderImpl(StudioCorePlugin.class.getClassLoader());;
    		} else {
    			pc = StudioProjectConfigurationManager.getInstance().getProjectConfigClassloader(getOntology().getName());
    		}
    		StreamClassLoaderImpl scldr = new StreamClassLoaderImpl(pc);
    		bg = new FileStreamGenerator(this,getOntology(),getExtraClassPath(),scldr,null,isCompileWithDebug(),false,new String[0]);
    	}
    	
    	
        try {
        	monitor.subTask("Initializing code generator...");
        	if (monitor.isCanceled()) {
    			throw new CoreException(Status.CANCEL_STATUS);
    		}
        	bg.init();
        	monitor.worked(1);
        	monitor.subTask("Generating runtime code...");
        	if (monitor.isCanceled()) {
    			throw new CoreException(Status.CANCEL_STATUS);
    		}
        	bg.generate();
        	//this.initGeneration();
        	monitor.worked(1);
        	monitor.subTask("Compiling runtime code...");
        	if (monitor.isCanceled()) {
    			throw new CoreException(Status.CANCEL_STATUS);
    		}
        	final InputStream jarFile = bg.compile();        	
            //BaseGenerator.compileSource(getClasspath(), getSourceDirs(),
			//		isCompileWithDebug());
			monitor.worked(1);
			
	    	
			if (serviceArchive != null) {
	            monitor.subTask("Packaging runtime jar into BAR package...");
	            final ZipVFileFactory vFactory = (ZipVFileFactory) getServiceArchive().getVFileFactory();
	            final VFileDirectory rootVDir = vFactory.getRootDirectory();
	            final VFileStream file = rootVDir.createChild(Constants.NAME_JAR_FILE, null);
	            final InputStream bufferedInputStream = new BufferedInputStream(jarFile);
	            try {
	            	
	            	file.update(bufferedInputStream);
	            	
	            } finally {
	            	
	            	bufferedInputStream.close(); 
	            	jarFile.close();
	            }
	            monitor.worked(1);
			} else {
				setCompileInputStream(jarFile);
			}
        } catch(Throwable t) {
        	if (t instanceof CoreException && 
					((CoreException)t).getStatus().getSeverity() == Status.CANCEL) {
				throw new CoreException(Status.CANCEL_STATUS);
			} else {
        	throw new Exception(t);
			}
        }
        finally {
        	bg.close();
        	bg=null;
            System.setProperty(FunctionsCatalogLookup.SHOW_COMPILER_WARNINGS, Boolean.FALSE.toString());
            monitor.done();
        }//finally
    }
}
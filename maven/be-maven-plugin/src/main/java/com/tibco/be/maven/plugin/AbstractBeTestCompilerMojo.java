package com.tibco.be.maven.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.artifact.handler.manager.ArtifactHandlerManager;
import org.apache.maven.artifact.resolver.ArtifactResolutionRequest;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.ResolutionErrorHandler;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.compiler.CompilationFailureException;
import org.apache.maven.plugin.compiler.DependencyCoordinate;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;
import org.apache.maven.shared.incremental.IncrementalBuildHelper;
import org.apache.maven.shared.incremental.IncrementalBuildHelperRequest;
import org.apache.maven.shared.utils.ReaderFactory;
import org.apache.maven.shared.utils.StringUtils;
import org.apache.maven.shared.utils.io.FileUtils;
import org.apache.maven.toolchain.Toolchain;
import org.apache.maven.toolchain.ToolchainManager;
import org.codehaus.plexus.compiler.Compiler;
import org.codehaus.plexus.compiler.CompilerConfiguration;
import org.codehaus.plexus.compiler.CompilerError;
import org.codehaus.plexus.compiler.CompilerException;
import org.codehaus.plexus.compiler.CompilerMessage;
import org.codehaus.plexus.compiler.CompilerNotImplementedException;
import org.codehaus.plexus.compiler.CompilerOutputStyle;
import org.codehaus.plexus.compiler.CompilerResult;
import org.codehaus.plexus.compiler.manager.CompilerManager;
import org.codehaus.plexus.compiler.manager.NoSuchCompilerException;
import org.codehaus.plexus.compiler.util.scan.InclusionScanException;
import org.codehaus.plexus.compiler.util.scan.SourceInclusionScanner;
import org.codehaus.plexus.compiler.util.scan.mapping.SingleTargetSourceMapping;
import org.codehaus.plexus.compiler.util.scan.mapping.SourceMapping;
import org.codehaus.plexus.compiler.util.scan.mapping.SuffixMapping;

import com.tibco.be.maven.plugin.util.BEMavenUtil;


public abstract class AbstractBeTestCompilerMojo
    extends AbstractMojo
{

	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;
	
    @Parameter( property = "maven.compiler.failOnError", defaultValue = "true" )
    private boolean failOnError = true;

    @Parameter( property = "maven.compiler.debug", defaultValue = "true" )
    private boolean debug = true;

   
    @Parameter( property = "maven.compiler.verbose", defaultValue = "false" )
    private boolean verbose;

   
    @Parameter( property = "maven.compiler.showDeprecation", defaultValue = "false" )
    private boolean showDeprecation;

    
    @Parameter( property = "maven.compiler.optimize", defaultValue = "false" )
    private boolean optimize;

    @Parameter( property = "maven.compiler.showWarnings", defaultValue = "false" )
    private boolean showWarnings;

    @Parameter( property = "maven.compiler.source", defaultValue = "1.5" )
    protected String source;

    @Parameter( property = "maven.compiler.target", defaultValue = "1.5" )
    protected String target;

    @Parameter( property = "encoding", defaultValue = "${project.build.sourceEncoding}" )
    private String encoding;

    @Parameter( property = "lastModGranularityMs", defaultValue = "0" )
    private int staleMillis;

    @Parameter( property = "maven.compiler.compilerId", defaultValue = "javac" )
    private String compilerId;

    @Parameter( property = "maven.compiler.compilerVersion" )
    private String compilerVersion;

    @Parameter( property = "maven.compiler.fork", defaultValue = "false" )
    private boolean fork;

    @Parameter( property = "maven.compiler.meminitial" )
    private String meminitial;

    @Parameter( property = "maven.compiler.maxmem" )
    private String maxmem;

   
    @Parameter( property = "maven.compiler.executable" )
    private String executable;

   
    @Parameter
    private String proc;

    @Parameter
    private String[] annotationProcessors;

    @Parameter
    private List<DependencyCoordinate> annotationProcessorPaths;

    @Parameter
    @Deprecated
    protected Map<String, String> compilerArguments;

   
    @Parameter
    protected List<String> compilerArgs;

    @Parameter
    protected String compilerArgument;

    @Parameter
    private String outputFileName;

    @Parameter( property = "maven.compiler.debuglevel" )
    private String debuglevel;

    @Component
    private ToolchainManager toolchainManager;


    @Parameter( defaultValue = "${basedir}", required = true, readonly = true )
    private File basedir;

    
    @Parameter( defaultValue = "${project.build.directory}", required = true, readonly = true )
    private File buildDirectory;

   
    @Component
    private CompilerManager compilerManager;

    @Parameter( defaultValue = "${session}", readonly = true, required = true )
    private MavenSession session;

   
    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

	@Parameter( defaultValue = "${reuseCreated}", property = "maven.compiler.compilerReuseStrategy" )
    private String compilerReuseStrategy = "reuseCreated";

    
    @Parameter( defaultValue = "false", property = "maven.compiler.skipMultiThreadWarning" )
    private boolean skipMultiThreadWarning;

   
    @Parameter( defaultValue = "false", property = "maven.compiler.forceJavacCompilerUse" )
    private boolean forceJavacCompilerUse;

    
    @Parameter( defaultValue = "${mojoExecution}", readonly = true, required = true )
    private MojoExecution mojoExecution;

    
    @Parameter
    private List<String> fileExtensions;

   
    @Parameter( defaultValue = "true", property = "maven.compiler.useIncrementalCompilation" )
    private boolean useIncrementalCompilation = true;

    
    @Component
    private RepositorySystem repositorySystem;

    @Component
    private ArtifactHandlerManager artifactHandlerManager;

    
    @Component
    private ResolutionErrorHandler resolutionErrorHandler;

    protected abstract SourceInclusionScanner getSourceInclusionScanner( int staleMillis );

    protected abstract SourceInclusionScanner getSourceInclusionScanner( String inputFileEnding );

    protected abstract List<String> getClasspathElements();

    protected abstract List<String> getCompileSourceRoots();

    protected abstract File getOutputDirectory();

    protected abstract String getSource();

    protected abstract String getTarget();

    protected abstract String getCompilerArgument();

    protected abstract Map<String, String> getCompilerArguments();

    protected abstract File getGeneratedSourcesDirectory();

    public void execute()
        throws MojoExecutionException, CompilationFailureException
    {
    	boolean isMac = false;
    	if(System.getProperty("os.name").toLowerCase().contains("mac"))
        	isMac = true;
        Compiler compiler;

        getLog().debug( "Using compiler '" + compilerId + "'." );

        try
        {
            compiler = compilerManager.getCompiler( compilerId );
        }
        catch ( NoSuchCompilerException e )
        {
            throw new MojoExecutionException( "No such compiler '" + e.getCompilerId() + "'." );
        }

        Toolchain tc = getToolchain();
        if ( tc != null )
        {
            getLog().info( "Toolchain in maven-compiler-plugin: " + tc );
            if ( executable != null )
            {
                getLog().warn( "Toolchains are ignored, 'executable' parameter is set to " + executable );
            }
            else
            {
                fork = true;
                executable = tc.findTool( compilerId );
            }
        }
        // ----------------------------------------------------------------------
        //
        // ----------------------------------------------------------------------

        List<String> compileSourceRoots = removeEmptyCompileSourceRoots( getCompileSourceRoots() );

        if ( compileSourceRoots.isEmpty() )
        {
            getLog().info( "No sources to compile" );

            return;
        }

        if ( getLog().isDebugEnabled() )
        {
            getLog().debug( "Source directories: " + compileSourceRoots.toString().replace( ',', '\n' ) );
            getLog().debug( "Classpath: " + getClasspathElements().toString().replace( ',', '\n' ) );
            getLog().debug( "Output directory: " + getOutputDirectory() );
        }

        // ----------------------------------------------------------------------
        // Create the compiler configuration
        // ----------------------------------------------------------------------

        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();

        compilerConfiguration.setOutputLocation( getOutputDirectory().getAbsolutePath() );

        compilerConfiguration.setClasspathEntries( getClasspathElements() );

        compilerConfiguration.setOptimize( optimize );

        compilerConfiguration.setDebug( debug );

        if ( debug && StringUtils.isNotEmpty( debuglevel ) )
        {
            String[] split = StringUtils.split( debuglevel, "," );
            for ( String aSplit : split )
            {
                if ( !( aSplit.equalsIgnoreCase( "none" ) || aSplit.equalsIgnoreCase( "lines" )
                    || aSplit.equalsIgnoreCase( "vars" ) || aSplit.equalsIgnoreCase( "source" ) ) )
                {
                    throw new IllegalArgumentException( "The specified debug level: '" + aSplit + "' is unsupported. "
                        + "Legal values are 'none', 'lines', 'vars', and 'source'." );
                }
            }
            compilerConfiguration.setDebugLevel( debuglevel );
        }

        compilerConfiguration.setVerbose( verbose );

        compilerConfiguration.setShowWarnings( showWarnings );

        compilerConfiguration.setShowDeprecation( showDeprecation );

        compilerConfiguration.setSourceVersion( getSource() );

        compilerConfiguration.setTargetVersion( getTarget() );

        compilerConfiguration.setProc( proc );

        File generatedSourcesDirectory = getGeneratedSourcesDirectory();
        compilerConfiguration.setGeneratedSourcesDirectory( generatedSourcesDirectory );

        if ( generatedSourcesDirectory != null )
        {
            String generatedSourcesPath = generatedSourcesDirectory.getAbsolutePath();

            compileSourceRoots.add( generatedSourcesPath );

            if ( isTestCompile() )
            {
                getLog().debug( "Adding " + generatedSourcesPath + " to test-compile source roots:\n  "
                                    + StringUtils.join( project.getTestCompileSourceRoots()
                                                               .iterator(), "\n  " ) );

                project.addTestCompileSourceRoot( generatedSourcesPath );

                getLog().debug( "New test-compile source roots:\n  "
                                    + StringUtils.join( project.getTestCompileSourceRoots()
                                                               .iterator(), "\n  " ) );
            }
            else
            {
                getLog().debug( "Adding " + generatedSourcesPath + " to compile source roots:\n  "
                                    + StringUtils.join( project.getCompileSourceRoots()
                                                               .iterator(), "\n  " ) );

                project.addCompileSourceRoot( generatedSourcesPath );

                getLog().debug( "New compile source roots:\n  " + StringUtils.join( project.getCompileSourceRoots()
                                                                                           .iterator(), "\n  " ) );
            }
        }

        compilerConfiguration.setSourceLocations( compileSourceRoots );

        compilerConfiguration.setAnnotationProcessors( annotationProcessors );

        compilerConfiguration.setProcessorPathEntries( resolveProcessorPathEntries() );

        compilerConfiguration.setSourceEncoding( encoding );

        Map<String, String> effectiveCompilerArguments = getCompilerArguments();

        String effectiveCompilerArgument = getCompilerArgument();

        if ( ( effectiveCompilerArguments != null ) || ( effectiveCompilerArgument != null )
                        || ( compilerArgs != null ) )
        {
            LinkedHashMap<String, String> cplrArgsCopy = new LinkedHashMap<String, String>();
            if ( effectiveCompilerArguments != null )
            {
                for ( Map.Entry<String, String> me : effectiveCompilerArguments.entrySet() )
                {
                    String key = me.getKey();
                    String value = me.getValue();
                    if ( !key.startsWith( "-" ) )
                    {
                        key = "-" + key;
                    }

                    if ( key.startsWith( "-A" ) && StringUtils.isNotEmpty( value ) )
                    {
                        cplrArgsCopy.put( key + "=" + value, null );
                    }
                    else
                    {
                        cplrArgsCopy.put( key, value );
                    }
                }
            }
            if ( !StringUtils.isEmpty( effectiveCompilerArgument ) )
            {
                cplrArgsCopy.put( effectiveCompilerArgument, null );
            }
            if ( compilerArgs != null )
            {
                for ( String arg : compilerArgs )
                {
                    cplrArgsCopy.put( arg, null );
                }
            }
            compilerConfiguration.setCustomCompilerArguments( cplrArgsCopy );
        }

        compilerConfiguration.setFork( fork );

        if ( fork )
        {
            if ( !StringUtils.isEmpty( meminitial ) )
            {
                String value = getMemoryValue( meminitial );

                if ( value != null )
                {
                    compilerConfiguration.setMeminitial( value );
                }
                else
                {
                    getLog().info( "Invalid value for meminitial '" + meminitial + "'. Ignoring this option." );
                }
            }

            if ( !StringUtils.isEmpty( maxmem ) )
            {
                String value = getMemoryValue( maxmem );

                if ( value != null )
                {
                    compilerConfiguration.setMaxmem( value );
                }
                else
                {
                    getLog().info( "Invalid value for maxmem '" + maxmem + "'. Ignoring this option." );
                }
            }
        }

        compilerConfiguration.setExecutable( executable );

        compilerConfiguration.setWorkingDirectory( basedir );

        compilerConfiguration.setCompilerVersion( compilerVersion );

        compilerConfiguration.setBuildDirectory( buildDirectory );

        compilerConfiguration.setOutputFileName( outputFileName );

        if ( CompilerConfiguration.CompilerReuseStrategy.AlwaysNew.getStrategy().equals( this.compilerReuseStrategy ) )
        {
            compilerConfiguration.setCompilerReuseStrategy( CompilerConfiguration.CompilerReuseStrategy.AlwaysNew );
        }
        else if ( CompilerConfiguration.CompilerReuseStrategy.ReuseSame.getStrategy().equals(
            this.compilerReuseStrategy ) )
        {
            if ( getRequestThreadCount() > 1 )
            {
                if ( !skipMultiThreadWarning )
                {
                    getLog().warn( "You are in a multi-thread build and compilerReuseStrategy is set to reuseSame."
                                       + " This can cause issues in some environments (os/jdk)!"
                                       + " Consider using reuseCreated strategy."
                                       + System.getProperty( "line.separator" )
                                       + "If your env is fine with reuseSame, you can skip this warning with the "
                                       + "configuration field skipMultiThreadWarning "
                                       + "or -Dmaven.compiler.skipMultiThreadWarning=true" );
                }
            }
            compilerConfiguration.setCompilerReuseStrategy( CompilerConfiguration.CompilerReuseStrategy.ReuseSame );
        }
        else
        {

            compilerConfiguration.setCompilerReuseStrategy( CompilerConfiguration.CompilerReuseStrategy.ReuseCreated );
        }

        getLog().debug( "CompilerReuseStrategy: " + compilerConfiguration.getCompilerReuseStrategy().getStrategy() );

        compilerConfiguration.setForceJavacCompilerUse( forceJavacCompilerUse );

        boolean canUpdateTarget;

        IncrementalBuildHelper incrementalBuildHelper = new IncrementalBuildHelper( mojoExecution, session );

        Set<File> sources;

        IncrementalBuildHelperRequest incrementalBuildHelperRequest = null;

        if ( useIncrementalCompilation )
        {
            getLog().debug( "useIncrementalCompilation enabled" );
            try
            {
                canUpdateTarget = compiler.canUpdateTarget( compilerConfiguration );

                sources = getCompileSources( compiler, compilerConfiguration );

                incrementalBuildHelperRequest = new IncrementalBuildHelperRequest().inputFiles( sources );

                // CHECKSTYLE_OFF: LineLength
                if ( ( compiler.getCompilerOutputStyle().equals( CompilerOutputStyle.ONE_OUTPUT_FILE_FOR_ALL_INPUT_FILES ) && !canUpdateTarget )
                    || isDependencyChanged()
                    || isSourceChanged( compilerConfiguration, compiler )
                    || incrementalBuildHelper.inputFileTreeChanged( incrementalBuildHelperRequest ) )
                    // CHECKSTYLE_ON: LineLength
                {
                    getLog().info( "Changes detected - recompiling the module!" );

                    compilerConfiguration.setSourceFiles( sources );
                }
                else
                {
                    getLog().info( "Nothing to compile - all classes are up to date" );

                    return;
                }
            }
            catch ( CompilerException e )
            {
                throw new MojoExecutionException( "Error while computing stale sources.", e );
            }
        }
        else
        {
            getLog().debug( "useIncrementalCompilation disabled" );
            Set<File> staleSources;
            try
            {
                staleSources =
                    computeStaleSources( compilerConfiguration, compiler, getSourceInclusionScanner( staleMillis ) );

                canUpdateTarget = compiler.canUpdateTarget( compilerConfiguration );

                if ( compiler.getCompilerOutputStyle().equals( CompilerOutputStyle.ONE_OUTPUT_FILE_FOR_ALL_INPUT_FILES )
                    && !canUpdateTarget )
                {
                    getLog().info( "RESCANNING!" );
                    // TODO: This second scan for source files is sub-optimal
                    String inputFileEnding = compiler.getInputFileEnding( compilerConfiguration );

                    sources = computeStaleSources( compilerConfiguration, compiler,
                                                             getSourceInclusionScanner( inputFileEnding ) );

                    compilerConfiguration.setSourceFiles( sources );
                }
                else
                {
                    compilerConfiguration.setSourceFiles( staleSources );
                }
            }
            catch ( CompilerException e )
            {
                throw new MojoExecutionException( "Error while computing stale sources.", e );
            }

            if ( staleSources.isEmpty() )
            {
                getLog().info( "Nothing to compile - all classes are up to date" );

                return;
            }
        }
        // ----------------------------------------------------------------------
        // Dump configuration
        // ----------------------------------------------------------------------

        if ( getLog().isDebugEnabled() )
        {
            getLog().debug( "Classpath:" );

            for ( String s : getClasspathElements() )
            {
                getLog().debug( " " + s );
            }

            getLog().debug( "Source roots:" );

            for ( String root : getCompileSourceRoots() )
            {
                getLog().debug( " " + root );
            }

            try
            {
                if ( fork )
                {
                    if ( compilerConfiguration.getExecutable() != null )
                    {
                        getLog().debug( "Excutable: " );
                        getLog().debug( " " + compilerConfiguration.getExecutable() );
                    }
                }

                String[] cl = compiler.createCommandLine( compilerConfiguration );
                if ( cl != null && cl.length > 0 )
                {
                    StringBuilder sb = new StringBuilder();
                    sb.append( cl[0] );
                    for ( int i = 1; i < cl.length; i++ )
                    {
                        sb.append( " " );
                        sb.append( cl[i] );
                    }
                    getLog().debug( "Command line options:" );
                    getLog().debug( sb );
                }
            }
            catch ( CompilerException ce )
            {
                getLog().debug( ce );
            }
        }

        // ----------------------------------------------------------------------
        // Compile!
        // ----------------------------------------------------------------------

        if ( StringUtils.isEmpty( compilerConfiguration.getSourceEncoding() ) )
        {
            getLog().warn( "File encoding has not been set, using platform encoding " + ReaderFactory.FILE_ENCODING
                               + ", i.e. build is platform dependent!" );
        }

        CompilerResult compilerResult;


        if ( useIncrementalCompilation )
        {
            incrementalBuildHelperRequest.outputDirectory( getOutputDirectory() );

            incrementalBuildHelper.beforeRebuildExecution( incrementalBuildHelperRequest );

            getLog().debug( "incrementalBuildHelper#beforeRebuildExecution" );
        }

        try
        {
            try
            {
            	String beHomeEntry = beProjectDetails.get("beHome");
            	String asHomeEntry = beProjectDetails.get("asHome");
            	beHomeEntry = beHomeEntry.replace('\\', '/');
        		if ( (asHomeEntry == null || asHomeEntry=="") && beHomeEntry.contains("/be")) {
        			String tibcoHome = beHomeEntry.substring(0, beHomeEntry.lastIndexOf("/be"));
        			String[] asVersion = new File(tibcoHome+"/as").list();
        			if (asVersion != null && asVersion.length > 0) {
        				asHomeEntry =  tibcoHome + "as/" + asVersion[0];
        			}
        		}
            	List<String> beStudioPluginsJars =new ArrayList<String>();
     		    List<String> beLibJars = new ArrayList<String>();
     		    File beLibPath = new File(beHomeEntry+ "/lib");
     		    File beStudioPath = new File(beHomeEntry +"/studio/eclipse/plugins");
            	
     		   if (beLibPath.isDirectory()){
     			   try {
     				   try {
     					   beLibJars = BEMavenUtil.lookInDirectory("", beLibPath,beLibJars);
     				   } catch (FileNotFoundException e) {
     					   e.printStackTrace();
     				   }
     			   } catch (ClassNotFoundException e) {
     				   e.printStackTrace();
     			   }
               }
               
               if (beStudioPath.isDirectory()) {
               		try {
               			try {
               				beStudioPluginsJars =BEMavenUtil.lookInDirectory("", beStudioPath,beStudioPluginsJars);
               			} catch (FileNotFoundException e) {
               				e.printStackTrace();
               			}
               		} catch (ClassNotFoundException e) {
               			e.printStackTrace();
               		}
               }
     		    
     		    
            	Set<Artifact> artifacts = project.getArtifacts();
        	    for (Artifact artifact : artifacts) {
        	    	if(artifact.getFile().getAbsolutePath().contains(".jar"))
        	    		compilerConfiguration.addClasspathEntry(artifact.getFile().getAbsolutePath());
        	    }
        	    for(String beLibJar : beLibJars){
        	    	compilerConfiguration.addClasspathEntry(beHomeEntry+ "/lib/"+beLibJar);
        	    }
        	    for(String beStudioJar : beStudioPluginsJars){
        	    	compilerConfiguration.addClasspathEntry(beHomeEntry +"/studio/eclipse/plugins/"+beStudioJar);
        	    }
        	    compilerConfiguration.addClasspathEntry(asHomeEntry+"/lib/as-common.jar");
        		if(!isMac){
        			compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar");
        			compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar");
        			compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar");
        			compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar");
        			compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.common_2.16.0.v20190528-0845.jar");
        	  	}else{
        	  		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar");
        	  		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar");
        	  		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar");
        	  		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar");
        	  		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.common_2.16.0.v20190528-0845.jar");
        	  		}
        	    if(!isMac){
        	    	compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar");
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar");
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore_2.12.0.v20160420-0247.jar");
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore.xmi_2.12.0.v20160420-0247.jar");
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.common_2.12.0.v20160420-0247.jar");
        	    }else{
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/Eclipse.app/Contents/Eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar");
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/Eclipse.app/Contents/Eclipse/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar");
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/Eclipse.app/Contents/Eclipse/plugins/org.eclipse.emf.ecore_2.12.0.v20160420-0247.jar");
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/Eclipse.app/Contents/Eclipse/plugins/org.eclipse.emf.ecore.xmi_2.12.0.v20160420-0247.jar");
            		compilerConfiguration.addClasspathEntry(beHomeEntry+"/eclipse-platform/eclipse/Eclipse.app/Contents/Eclipse/plugins/org.eclipse.emf.common_2.12.0.v20160420-0247.jar");
            	}
        	    compilerResult = compiler.performCompile( compilerConfiguration );
            }
            catch ( CompilerNotImplementedException cnie )
            {
                List<CompilerError> messages = compiler.compile( compilerConfiguration );
                compilerResult = convertToCompilerResult( messages );
            }
        }
        catch ( Exception e )
        {
            // TODO: don't catch Exception
            throw new MojoExecutionException( "Fatal error compiling", e );
        }

        if ( useIncrementalCompilation )
        {
            if ( incrementalBuildHelperRequest.getOutputDirectory().exists() )
            {
                getLog().debug( "incrementalBuildHelper#afterRebuildExecution" );
                // now scan the same directory again and create a diff
                incrementalBuildHelper.afterRebuildExecution( incrementalBuildHelperRequest );
            }
            else
            {
                getLog().debug(
                    "skip incrementalBuildHelper#afterRebuildExecution as the output directory doesn't exist" );
            }
        }

        List<CompilerMessage> warnings = new ArrayList<CompilerMessage>();
        List<CompilerMessage> errors = new ArrayList<CompilerMessage>();
        List<CompilerMessage> others = new ArrayList<CompilerMessage>();
        for ( CompilerMessage message : compilerResult.getCompilerMessages() )
        {
            if ( message.getKind() == CompilerMessage.Kind.ERROR )
            {
                errors.add( message );
            }
            else if ( message.getKind() == CompilerMessage.Kind.WARNING
                || message.getKind() == CompilerMessage.Kind.MANDATORY_WARNING )
            {
                warnings.add( message );
            }
            else
            {
                others.add( message );
            }
        }

        if ( failOnError && !compilerResult.isSuccess() )
        {
            for ( CompilerMessage message : others )
            {
                assert message.getKind() != CompilerMessage.Kind.ERROR
                    && message.getKind() != CompilerMessage.Kind.WARNING
                    && message.getKind() != CompilerMessage.Kind.MANDATORY_WARNING;
                getLog().info( message.toString() );
            }
            if ( !warnings.isEmpty() )
            {
                getLog().info( "-------------------------------------------------------------" );
                getLog().warn( "COMPILATION WARNING : " );
                getLog().info( "-------------------------------------------------------------" );
                for ( CompilerMessage warning : warnings )
                {
                    getLog().warn( warning.toString() );
                }
                getLog().info( warnings.size() + ( ( warnings.size() > 1 ) ? " warnings " : " warning" ) );
                getLog().info( "-------------------------------------------------------------" );
            }

            if ( !errors.isEmpty() )
            {
                getLog().info( "-------------------------------------------------------------" );
                getLog().error( "COMPILATION ERROR 2: " );
                getLog().info( "-------------------------------------------------------------" );
                for ( CompilerMessage error : errors )
                {
                    getLog().error( error.toString() );
                }
                getLog().info( errors.size() + ( ( errors.size() > 1 ) ? " errors " : " error" ) );
                getLog().info( "-------------------------------------------------------------" );
            }

            if ( !errors.isEmpty() )
            {
                throw new CompilationFailureException( errors );
            }
            else
            {
                throw new CompilationFailureException( warnings );
            }
        }
        else
        {
            for ( CompilerMessage message : compilerResult.getCompilerMessages() )
            {
                switch ( message.getKind() )
                {
                    case NOTE:
                    case OTHER:
                        getLog().info( message.toString() );
                        break;

                    case ERROR:
                        getLog().error( message.toString() );
                        break;

                    case MANDATORY_WARNING:
                    case WARNING:
                    default:
                        getLog().warn( message.toString() );
                        break;
                }
            }
        }
    }

    protected boolean isTestCompile()
    {
        return false;
    }

    protected CompilerResult convertToCompilerResult( List<CompilerError> compilerErrors )
    {
        if ( compilerErrors == null )
        {
            return new CompilerResult();
        }
        List<CompilerMessage> messages = new ArrayList<CompilerMessage>( compilerErrors.size() );
        boolean success = true;
        for ( CompilerError compilerError : compilerErrors )
        {
            messages.add(
                new CompilerMessage( compilerError.getFile(), compilerError.getKind(), compilerError.getStartLine(),
                                     compilerError.getStartColumn(), compilerError.getEndLine(),
                                     compilerError.getEndColumn(), compilerError.getMessage() ) );
            if ( compilerError.isError() )
            {
                success = false;
            }
        }

        return new CompilerResult( success, messages );
    }

    /**
     * @return all source files for the compiler
     */
    private Set<File> getCompileSources( Compiler compiler, CompilerConfiguration compilerConfiguration )
        throws MojoExecutionException, CompilerException
    {
        String inputFileEnding = compiler.getInputFileEnding( compilerConfiguration );
        if ( StringUtils.isEmpty( inputFileEnding ) )
        {
            // see MCOMPILER-199 GroovyEclipseCompiler doesn't set inputFileEnding
            // so we can presume it's all files from the source directory
            inputFileEnding = ".*";
        }
        SourceInclusionScanner scanner = getSourceInclusionScanner( inputFileEnding );

        SourceMapping mapping = getSourceMapping( compilerConfiguration, compiler );

        scanner.addSourceMapping( mapping );

        Set<File> compileSources = new HashSet<File>();

        for ( String sourceRoot : getCompileSourceRoots() )
        {
            File rootFile = new File( sourceRoot );

            if ( !rootFile.isDirectory() )
            {
                continue;
            }

            try
            {
                compileSources.addAll( scanner.getIncludedSources( rootFile, null ) );
            }
            catch ( InclusionScanException e )
            {
                throw new MojoExecutionException(
                    "Error scanning source root: \'" + sourceRoot + "\' for stale files to recompile.", e );
            }
        }

        return compileSources;
    }

    /**
     * @param compilerConfiguration
     * @param compiler
     * @return <code>true</code> if at least a single source file is newer than it's class file
     */
    private boolean isSourceChanged( CompilerConfiguration compilerConfiguration, Compiler compiler )
        throws CompilerException, MojoExecutionException
    {
        Set<File> staleSources =
            computeStaleSources( compilerConfiguration, compiler, getSourceInclusionScanner( staleMillis ) );

        if ( getLog().isDebugEnabled() )
        {
            for ( File f : staleSources )
            {
                getLog().debug( "Stale source detected: " + f.getAbsolutePath() );
            }
        }
        return staleSources != null && staleSources.size() > 0;
    }


    /**
     * try to get thread count if a Maven 3 build, using reflection as the plugin must not be maven3 api dependent
     *
     * @return number of thread for this build or 1 if not multi-thread build
     */
    protected int getRequestThreadCount()
    {
        try
        {
            Method getRequestMethod = session.getClass().getMethod( "getRequest" );
            Object mavenExecutionRequest = getRequestMethod.invoke( this.session );
            Method getThreadCountMethod = mavenExecutionRequest.getClass().getMethod( "getThreadCount" );
            String threadCount = (String) getThreadCountMethod.invoke( mavenExecutionRequest );
            return Integer.valueOf( threadCount );
        }
        catch ( Exception e )
        {
            getLog().debug( "unable to get threadCount for the current build: " + e.getMessage() );
        }
        return 1;
    }

    protected Date getBuildStartTime()
    {
        Date buildStartTime = null;
        try
        {
            Method getRequestMethod = session.getClass().getMethod( "getRequest" );
            Object mavenExecutionRequest = getRequestMethod.invoke( session );
            Method getStartTimeMethod = mavenExecutionRequest.getClass().getMethod( "getStartTime" );
            buildStartTime = (Date) getStartTimeMethod.invoke( mavenExecutionRequest );
        }
        catch ( Exception e )
        {
            getLog().debug( "unable to get start time for the current build: " + e.getMessage() );
        }

        if ( buildStartTime == null )
        {
            return new Date();
        }

        return buildStartTime;
    }


    private String getMemoryValue( String setting )
    {
        String value = null;

        // Allow '128' or '128m'
        if ( isDigits( setting ) )
        {
            value = setting + "m";
        }
        else if ( ( isDigits( setting.substring( 0, setting.length() - 1 ) ) )
            && ( setting.toLowerCase().endsWith( "m" ) ) )
        {
            value = setting;
        }
        return value;
    }

    //TODO remove the part with ToolchainManager lookup once we depend on
    //3.0.9 (have it as prerequisite). Define as regular component field then.
    private Toolchain getToolchain()
    {
        Toolchain tc = null;
        if ( toolchainManager != null )
        {
            tc = toolchainManager.getToolchainFromBuildContext( "jdk", session );
        }
        return tc;
    }

    private boolean isDigits( String string )
    {
        for ( int i = 0; i < string.length(); i++ )
        {
            if ( !Character.isDigit( string.charAt( i ) ) )
            {
                return false;
            }
        }
        return true;
    }

    private Set<File> computeStaleSources( CompilerConfiguration compilerConfiguration, Compiler compiler,
                                           SourceInclusionScanner scanner )
        throws MojoExecutionException, CompilerException
    {
        SourceMapping mapping = getSourceMapping( compilerConfiguration, compiler );

        File outputDirectory;
        CompilerOutputStyle outputStyle = compiler.getCompilerOutputStyle();
        if ( outputStyle == CompilerOutputStyle.ONE_OUTPUT_FILE_FOR_ALL_INPUT_FILES )
        {
            outputDirectory = buildDirectory;
        }
        else
        {
            outputDirectory = getOutputDirectory();
        }

        scanner.addSourceMapping( mapping );

        Set<File> staleSources = new HashSet<File>();

        for ( String sourceRoot : getCompileSourceRoots() )
        {
            File rootFile = new File( sourceRoot );

            if ( !rootFile.isDirectory() )
            {
                continue;
            }

            try
            {
                staleSources.addAll( scanner.getIncludedSources( rootFile, outputDirectory ) );
            }
            catch ( InclusionScanException e )
            {
                throw new MojoExecutionException(
                    "Error scanning source root: \'" + sourceRoot + "\' for stale files to recompile.", e );
            }
        }

        return staleSources;
    }

    private SourceMapping getSourceMapping( CompilerConfiguration compilerConfiguration, Compiler compiler )
        throws CompilerException, MojoExecutionException
    {
        CompilerOutputStyle outputStyle = compiler.getCompilerOutputStyle();

        SourceMapping mapping;
        if ( outputStyle == CompilerOutputStyle.ONE_OUTPUT_FILE_PER_INPUT_FILE )
        {
            mapping = new SuffixMapping( compiler.getInputFileEnding( compilerConfiguration ),
                                         compiler.getOutputFileEnding( compilerConfiguration ) );
        }
        else if ( outputStyle == CompilerOutputStyle.ONE_OUTPUT_FILE_FOR_ALL_INPUT_FILES )
        {
            mapping = new SingleTargetSourceMapping( compiler.getInputFileEnding( compilerConfiguration ),
                                                     compiler.getOutputFile( compilerConfiguration ) );

        }
        else
        {
            throw new MojoExecutionException( "Unknown compiler output style: '" + outputStyle + "'." );
        }
        return mapping;
    }

    /**
     * @todo also in ant plugin. This should be resolved at some point so that it does not need to
     * be calculated continuously - or should the plugins accept empty source roots as is?
     */
    private static List<String> removeEmptyCompileSourceRoots( List<String> compileSourceRootsList )
    {
        List<String> newCompileSourceRootsList = new ArrayList<String>();
        if ( compileSourceRootsList != null )
        {
            // copy as I may be modifying it
            for ( String srcDir : compileSourceRootsList )
            {
                if ( !newCompileSourceRootsList.contains( srcDir ) && new File( srcDir ).exists() )
                {
                    newCompileSourceRootsList.add( srcDir );
                }
            }
        }
        return newCompileSourceRootsList;
    }

    /**
     * We just compare the timestamps of all local dependency files (inter-module dependency classpath)
     * and the own generated classes
     * and if we got a file which is >= the buid-started timestamp, then we catched a file which got
     * changed during this build.
     *
     * @return <code>true</code> if at least one single dependency has changed.
     */
    protected boolean isDependencyChanged()
    {
        if ( session == null )
        {
            // we just cannot determine it, so don't do anything beside logging
            getLog().info( "Cannot determine build start date, skipping incremental build detection." );
            return false;
        }

        if ( fileExtensions == null || fileExtensions.isEmpty() )
        {
            fileExtensions = new ArrayList<String>();
            fileExtensions.add( ".class" );
        }

        Date buildStartTime = getBuildStartTime();

        for ( String classPathElement : getClasspathElements() )
        {
            // ProjectArtifacts are artifacts which are available in the local project
            // that's the only ones we are interested in now.
            File artifactPath = new File( classPathElement );
            if ( artifactPath.isDirectory() )
            {
                if ( hasNewFile( artifactPath, buildStartTime ) )
                {
                    getLog().debug( "New dependency detected: " + artifactPath.getAbsolutePath() );
                    return true;
                }
            }
        }

        // obviously there was no new file detected.
        return false;
    }

    /**
     * @param classPathEntry entry to check
     * @param buildStartTime time build start
     * @return if any changes occurred
     */
    private boolean hasNewFile( File classPathEntry, Date buildStartTime )
    {
        if ( !classPathEntry.exists() )
        {
            return false;
        }

        if ( classPathEntry.isFile() )
        {
            return classPathEntry.lastModified() >= buildStartTime.getTime()
                && fileExtensions.contains( FileUtils.getExtension( classPathEntry.getName() ) );
        }

        File[] children = classPathEntry.listFiles();

        for ( File child : children )
        {
            if ( hasNewFile( child, buildStartTime ) )
            {
                return true;
            }
        }

        return false;
    }

    private List<String> resolveProcessorPathEntries()
        throws MojoExecutionException
    {
        if ( annotationProcessorPaths == null || annotationProcessorPaths.isEmpty() )
        {
            return null;
        }

        try
        {
            Set<Artifact> requiredArtifacts = new HashSet<Artifact>();

            for ( DependencyCoordinate coord : annotationProcessorPaths )
            {
                ArtifactHandler handler = artifactHandlerManager.getArtifactHandler( coord.getType() );

                Artifact artifact = new DefaultArtifact(
                     coord.getGroupId(),
                     coord.getArtifactId(),
                     VersionRange.createFromVersionSpec( coord.getVersion() ),
                     Artifact.SCOPE_RUNTIME,
                     coord.getType(),
                     coord.getClassifier(),
                     handler,
                     false );

                requiredArtifacts.add( artifact );
            }

            ArtifactResolutionRequest request = new ArtifactResolutionRequest()
                            .setArtifact( project.getArtifact() )
                            .setResolveRoot( false )
                            .setResolveTransitively( true )
                            .setArtifactDependencies( requiredArtifacts )
                            .setLocalRepository( session.getLocalRepository() )
                            .setRemoteRepositories( project.getRemoteArtifactRepositories() );

            ArtifactResolutionResult resolutionResult = repositorySystem.resolve( request );

            resolutionErrorHandler.throwErrors( request, resolutionResult );

            List<String> classpathElements = new ArrayList<String>( resolutionResult.getArtifacts().size() );

            for ( Object resolved : resolutionResult.getArtifacts() )
            {
                classpathElements.add( ( (Artifact) resolved ).getFile().getAbsolutePath() );
            }

            return classpathElements;
        }
        catch ( Exception e )
        {
            throw new MojoExecutionException( "Resolution of annotationProcessorPath dependencies failed: "
                + e.getLocalizedMessage(), e );
        }
    }
}

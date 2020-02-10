package com.tibco.be.maven.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.surefire.AbstractSurefireMojo;
import org.apache.maven.plugin.surefire.SurefireHelper;
import org.apache.maven.plugin.surefire.SurefireReportParameters;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.apache.maven.surefire.suite.RunResult;

import com.tibco.be.maven.plugin.util.BEMavenUtil;

@Mojo( name = "betest", defaultPhase = LifecyclePhase.TEST, threadSafe = true,
requiresDependencyResolution = ResolutionScope.TEST )
public class BETestMojo extends AbstractSurefireMojo implements SurefireReportParameters{

	@Parameter
	private LinkedHashMap<String, String> beProjectDetails;
	
	@Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject projectTest;
	
    @Parameter( defaultValue = "${project.build.outputDirectory}" )
    private File classesDirectory;

    @Parameter( property = "maven.test.failure.ignore", defaultValue = "false" )
    private boolean testFailureIgnore;

    
    @Parameter( defaultValue = "${project.build.directory}/surefire-reports" )
    private File reportsDirectory;

    
    @Parameter( property = "test" )
    private String test;

    
    @Parameter( property = "surefire.printSummary", defaultValue = "true" )
    private boolean printSummary;

   
    @Parameter( property = "surefire.reportFormat", defaultValue = "brief" )
    private String reportFormat;

    
    @Parameter( property = "surefire.useFile", defaultValue = "true" )
    private boolean useFile;

    
    @Parameter( property = "surefire.failIfNoSpecifiedTests" )
    private Boolean failIfNoSpecifiedTests;

    @Parameter( property = "maven.surefire.debug" )
    private String debugForkedProcess;

    
    @Parameter( property = "surefire.timeout" )
    private int forkedProcessTimeoutInSeconds;

   
    @Parameter( property = "surefire.parallel.timeout" )
    private double parallelTestsTimeoutInSeconds;

    
    @Parameter( property = "surefire.parallel.forcedTimeout" )
    private double parallelTestsTimeoutForcedInSeconds;

    
    @Parameter
    private List<String> includes;

    
    @Parameter( property = "surefire.useSystemClassLoader", defaultValue = "true" )
    private boolean useSystemClassLoader;

   
    @Parameter( property = "surefire.useManifestOnlyJar", defaultValue = "true" )
    private boolean useManifestOnlyJar;

    @Parameter( property = "surefire.rerunFailingTestsCount", defaultValue = "0" )
    private int rerunFailingTestsCount;

    @Parameter( property = "surefire.suiteXmlFiles" )
    private File[] suiteXmlFiles;

    
    @Parameter( property = "surefire.runOrder", defaultValue = "filesystem" )
    private String runOrder;

    
    @Parameter( property = "surefire.includesFile" )
    private File includesFile;

    @Parameter( property = "surefire.excludesFile" )
    private File excludesFile;

    
    @Parameter( property = "surefire.skipAfterFailureCount", defaultValue = "0" )
    private int skipAfterFailureCount;

    @Parameter( property = "surefire.shutdown", defaultValue = "testset" )
    private String shutdown;

    protected int getRerunFailingTestsCount()
    {
        return rerunFailingTestsCount;
    }

    protected void handleSummary( RunResult summary, Exception firstForkException )
        throws MojoExecutionException, MojoFailureException
    {
        assertNoException( firstForkException );

        SurefireHelper.reportExecution( this, summary, getLog() );
    }

    private void assertNoException( Exception firstForkException )
        throws MojoFailureException
    {
        if ( firstForkException != null )
        {
            throw new MojoFailureException( firstForkException.getMessage(), firstForkException );
        }
    }

    protected boolean isSkipExecution()
    {
        return isSkip() || isSkipTests() || isSkipExec();
    }

    protected String getPluginName()
    {
        return "surefire";
    }

    protected String[] getDefaultIncludes()
    {
        return new String[]{ "**/Test*.java", "**/*Test.java", "**/*TestCase.java" ,"**/*TestSuite.java"};
    }

    // now for the implementation of the field accessors

    public boolean isSkipTests()
    {
        return skipTests;
    }

    public void setSkipTests( boolean skipTests )
    {
        this.skipTests = skipTests;
    }

    /**
     * @noinspection deprecation
     */
    public boolean isSkipExec()
    {
        return skipExec;
    }

    /**
     * @noinspection deprecation
     */
    public void setSkipExec( boolean skipExec )
    {
        this.skipExec = skipExec;
    }

    public boolean isSkip()
    {
        return skip;
    }

    public void setSkip( boolean skip )
    {
        this.skip = skip;
    }

    public boolean isTestFailureIgnore()
    {
        return testFailureIgnore;
    }

    public void setTestFailureIgnore( boolean testFailureIgnore )
    {
        this.testFailureIgnore = testFailureIgnore;
    }

    public File getBasedir()
    {
        return basedir;
    }

    public void setBasedir( File basedir )
    {
        this.basedir = basedir;
    }

    public File getTestClassesDirectory()
    {
        return testClassesDirectory;
    }

    public void setTestClassesDirectory( File testClassesDirectory )
    {
        this.testClassesDirectory = testClassesDirectory;
    }

    public File getClassesDirectory()
    {
        return classesDirectory;
    }

    public void setClassesDirectory( File classesDirectory )
    {
        this.classesDirectory = classesDirectory;
    }

    public File getReportsDirectory()
    {
        return reportsDirectory;
    }

    public void setReportsDirectory( File reportsDirectory )
    {
        this.reportsDirectory = reportsDirectory;
    }

    public String getTest()
    {
        return test;
    }

    public boolean isUseSystemClassLoader()
    {
        return useSystemClassLoader;
    }

    public void setUseSystemClassLoader( boolean useSystemClassLoader )
    {
        this.useSystemClassLoader = useSystemClassLoader;
    }

    public boolean isUseManifestOnlyJar()
    {
        return useManifestOnlyJar;
    }

    public void setUseManifestOnlyJar( boolean useManifestOnlyJar )
    {
        this.useManifestOnlyJar = useManifestOnlyJar;
    }

    public Boolean getFailIfNoSpecifiedTests()
    {
        return failIfNoSpecifiedTests;
    }

    public void setFailIfNoSpecifiedTests( boolean failIfNoSpecifiedTests )
    {
        this.failIfNoSpecifiedTests = failIfNoSpecifiedTests;
    }

    public int getSkipAfterFailureCount()
    {
        return skipAfterFailureCount;
    }

    public String getShutdown()
    {
        return shutdown;
    }

    public boolean isPrintSummary()
    {
        return printSummary;
    }

    public void setPrintSummary( boolean printSummary )
    {
        this.printSummary = printSummary;
    }

    public String getReportFormat()
    {
        return reportFormat;
    }

    public void setReportFormat( String reportFormat )
    {
        this.reportFormat = reportFormat;
    }

    public boolean isUseFile()
    {
        return useFile;
    }

    public void setUseFile( boolean useFile )
    {
        this.useFile = useFile;
    }

    public String getDebugForkedProcess()
    {
        return debugForkedProcess;
    }

    public void setDebugForkedProcess( String debugForkedProcess )
    {
        this.debugForkedProcess = debugForkedProcess;
    }

    public int getForkedProcessTimeoutInSeconds()
    {
        return forkedProcessTimeoutInSeconds;
    }

    public void setForkedProcessTimeoutInSeconds( int forkedProcessTimeoutInSeconds )
    {
        this.forkedProcessTimeoutInSeconds = forkedProcessTimeoutInSeconds;
    }

    public double getParallelTestsTimeoutInSeconds()
    {
        return parallelTestsTimeoutInSeconds;
    }

    public void setParallelTestsTimeoutInSeconds( double parallelTestsTimeoutInSeconds )
    {
        this.parallelTestsTimeoutInSeconds = parallelTestsTimeoutInSeconds;
    }

    public double getParallelTestsTimeoutForcedInSeconds()
    {
        return parallelTestsTimeoutForcedInSeconds;
    }

    public void setParallelTestsTimeoutForcedInSeconds( double parallelTestsTimeoutForcedInSeconds )
    {
        this.parallelTestsTimeoutForcedInSeconds = parallelTestsTimeoutForcedInSeconds;
    }

    public void setTest( String test )
    {
        this.test = test;
    }

    @Override
    public List<String> getIncludes()
    {
        return includes;
    }

    @Override
    public void setIncludes( List<String> includes )
    {
        this.includes = includes;
    }

    public File[] getSuiteXmlFiles()
    {
        return suiteXmlFiles.clone();
    }

    @SuppressWarnings( "UnusedDeclaration" )
    public void setSuiteXmlFiles( File[] suiteXmlFiles )
    {
        this.suiteXmlFiles = suiteXmlFiles.clone();
    }

    public String getRunOrder()
    {
        return runOrder;
    }

    @SuppressWarnings( "UnusedDeclaration" )
    public void setRunOrder( String runOrder )
    {
        this.runOrder = runOrder;
    }

    @Override
    public File getIncludesFile()
    {
        return includesFile;
    }

    @Override
    public File getExcludesFile()
    {
        return excludesFile;
    }

    @Override
    protected final List<File> suiteXmlFiles()
    {
        return hasSuiteXmlFiles() ? Arrays.asList( suiteXmlFiles ) : Collections.<File>emptyList();
    }

    @Override
    protected final boolean hasSuiteXmlFiles()
    {
        return suiteXmlFiles != null && suiteXmlFiles.length != 0;
    }

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		boolean isMac = false;
        if(System.getProperty("os.name").toLowerCase().contains("mac"))
        	isMac = true;
		String beHomeEntry = beProjectDetails.get("beHome");
		String asHomeEntry = beProjectDetails.get("asHome");
		beHomeEntry = beHomeEntry.replace('\\', '/');
		if ( (asHomeEntry == null || asHomeEntry=="") && beHomeEntry.contains("/be")) {
			String tibcoHome = beHomeEntry.substring(0, beHomeEntry.lastIndexOf("/be"));
			String[] asVersion = new File(tibcoHome+"/as").list();
			if (asVersion != null && asVersion.length > 0) {
				asHomeEntry =  tibcoHome + "/as/" + asVersion[0];
			}
		}
		List<String> beStudioPluginsJars =new ArrayList<String>();
		List<String> beLibJars = new ArrayList<String>();
		File beLibPath = new File(beHomeEntry+ "/lib");
		File beStudioPath = new File(beHomeEntry +"/studio/eclipse/plugins");
    	
		if (beLibPath.isDirectory()) {
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
		List<String> classPathEntries = new ArrayList<String>();
		Set<Artifact> artifacts = projectTest.getArtifacts();
	    for (Artifact artifact : artifacts) {
	    	if(artifact.getFile().getAbsolutePath().contains(".jar"))
	    		classPathEntries.add(artifact.getFile().getAbsolutePath());
	    }
	    for(String beLibJar : beLibJars){
	    	classPathEntries.add(beHomeEntry+ "/lib/"+beLibJar);
	    }
	    for(String beStudioJar : beStudioPluginsJars){
	    	classPathEntries.add(beHomeEntry +"/studio/eclipse/plugins/"+beStudioJar);
	    }
	    if (asHomeEntry != null && !asHomeEntry.isEmpty()) {
	    	classPathEntries.add(asHomeEntry + "/lib/as-common.jar");
	    }
	  	if(!isMac){
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar");
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar");
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar");
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar");
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.common_2.16.0.v20190528-0845.jar");
	  	}else{
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.junit_4.12.0.v201504281640/junit.jar");
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.hamcrest.core_1.3.0.v20180420-1519.jar");
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore_2.18.0.v20190528-0845.jar");
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.ecore.xmi_2.16.0.v20190528-0725.jar");
	  		classPathEntries.add(beHomeEntry+"/eclipse-platform/eclipse/plugins/org.eclipse.emf.common_2.16.0.v20190528-0845.jar");
	  		}
		
		super.setAdditionalClasspathElements(classPathEntries.toArray(new String[0]));
		super.execute();
	}

}

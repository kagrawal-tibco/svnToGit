package com.tibco.be.maven.plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.compiler.CompilationFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.compiler.util.scan.SimpleSourceInclusionScanner;
import org.codehaus.plexus.compiler.util.scan.SourceInclusionScanner;
import org.codehaus.plexus.compiler.util.scan.StaleSourceScanner;

@Mojo( name = "beTestCompile", defaultPhase = LifecyclePhase.TEST_COMPILE, threadSafe = true,
requiresDependencyResolution = ResolutionScope.TEST )
public class BETestCompileMojo extends AbstractBeTestCompilerMojo{

	@Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject projectDetails;
    /**
     * Set this to 'true' to bypass compilation of test sources.
     * Its use is NOT RECOMMENDED, but quite convenient on occasion.
     */
    @Parameter ( property = "maven.test.skip" )
    private boolean skip;

    /**
     * The source directories containing the test-source to be compiled.
     */
    @Parameter ( defaultValue = "${project.testCompileSourceRoots}", readonly = true, required = true )
    private List<String> compileSourceRoots;

    /**
     * Project test classpath.
     */
    @Parameter ( defaultValue = "${project.testClasspathElements}", required = true, readonly = true )
    private List<String> classpathElements;

    /**
     * The directory where compiled test classes go.
     */
    @Parameter ( defaultValue = "${project.build.testOutputDirectory}", required = true, readonly = true )
    private File outputDirectory;

    /**
     * A list of inclusion filters for the compiler.
     */
    @Parameter
    private Set<String> testIncludes = new HashSet<String>();

    /**
     * A list of exclusion filters for the compiler.
     */
    @Parameter
    private Set<String> testExcludes = new HashSet<String>();

    /**
     * The -source argument for the test Java compiler.
     *
     * @since 2.1
     */
    @Parameter ( property = "maven.compiler.testSource" )
    private String testSource;

    /**
     * The -target argument for the test Java compiler.
     *
     * @since 2.1
     */
    @Parameter ( property = "maven.compiler.testTarget" )
    private String testTarget;


    /**
     * <p>
     * Sets the arguments to be passed to test compiler (prepending a dash) if fork is set to true.
     * </p>
     * <p>
     * This is because the list of valid arguments passed to a Java compiler
     * varies based on the compiler version.
     * </p>
     *
     * @since 2.1
     */
    @Parameter
    private Map<String, String> testCompilerArguments;

    /**
     * <p>
     * Sets the unformatted argument string to be passed to test compiler if fork is set to true.
     * </p>
     * <p>
     * This is because the list of valid arguments passed to a Java compiler
     * varies based on the compiler version.
     * </p>
     *
     * @since 2.1
     */
    @Parameter
    private String testCompilerArgument;

    /**
     * <p>
     * Specify where to place generated source files created by annotation processing.
     * Only applies to JDK 1.6+
     * </p>
     *
     * @since 2.2
     */
    @Parameter ( defaultValue = "${project.build.directory}/generated-test-sources/test-annotations" )
    private File generatedTestSourcesDirectory;


    public void execute()
        throws MojoExecutionException, CompilationFailureException
    {
        if ( skip )
        {
            getLog().info( "Not compiling test sources" );
        }
        else
        {
        	compileSourceRoots.clear();
        	File checkMvnSrcFolder = new File(projectDetails.getBasedir().getPath()+"/src/test");
            if(checkMvnSrcFolder.exists()){
            	compileSourceRoots.add(projectDetails.getBasedir().getPath()+"/src/test");
            }else
            	compileSourceRoots.add(projectDetails.getBasedir().getPath()+"/JavaSrc");
            super.execute();
        }
    }

    protected List<String> getCompileSourceRoots()
    {
        return compileSourceRoots;
    }

    protected List<String> getClasspathElements()
    {
        return classpathElements;
    }

    protected File getOutputDirectory()
    {
        return outputDirectory;
    }

    protected SourceInclusionScanner getSourceInclusionScanner( int staleMillis )
    {
        SourceInclusionScanner scanner;

        if ( testIncludes.isEmpty() && testExcludes.isEmpty() )
        {
            scanner = new StaleSourceScanner( staleMillis );
        }
        else
        {
            if ( testIncludes.isEmpty() )
            {
                testIncludes.add( "**/*.java" );
            }
            scanner = new StaleSourceScanner( staleMillis, testIncludes, testExcludes );
        }

        return scanner;
    }

    protected SourceInclusionScanner getSourceInclusionScanner( String inputFileEnding )
    {
        SourceInclusionScanner scanner;

        // it's not defined if we get the ending with or without the dot '.'
        String defaultIncludePattern = "**/*" + ( inputFileEnding.startsWith( "." ) ? "" : "." ) + inputFileEnding;

        if ( testIncludes.isEmpty() && testExcludes.isEmpty() )
        {
            testIncludes = Collections.singleton( defaultIncludePattern );
            scanner = new SimpleSourceInclusionScanner( testIncludes, Collections.<String>emptySet() );
        }
        else
        {
            if ( testIncludes.isEmpty() )
            {
                testIncludes.add( defaultIncludePattern );
            }
            scanner = new SimpleSourceInclusionScanner( testIncludes, testExcludes );
        }

        return scanner;
    }

    protected String getSource()
    {
        return testSource == null ? source : testSource;
    }

    protected String getTarget()
    {
        return testTarget == null ? target : testTarget;
    }

    protected String getCompilerArgument()
    {
        return testCompilerArgument == null ? compilerArgument : testCompilerArgument;
    }

    protected Map<String, String> getCompilerArguments()
    {
        return testCompilerArguments == null ? compilerArguments : testCompilerArguments;
    }

    protected File getGeneratedSourcesDirectory()
    {
        return generatedTestSourcesDirectory;
    }

    @Override
    protected boolean isTestCompile()
    {
        return true;
    }


}

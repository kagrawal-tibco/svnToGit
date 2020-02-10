package com.tibco.be.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo( name = "beclean", defaultPhase = LifecyclePhase.CLEAN )
public class BECleanMojo extends AbstractMojo{
	public void execute() throws MojoExecutionException, MojoFailureException {
	}
}

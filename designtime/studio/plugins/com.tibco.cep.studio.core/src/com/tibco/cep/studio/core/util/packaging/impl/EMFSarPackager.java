package com.tibco.cep.studio.core.util.packaging.impl;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarOutputStream;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.util.packaging.descriptors.MutableApplicationArchive;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.util.packaging.ISharedArchiveResourcesProvider;
import com.tibco.cep.studio.core.util.packaging.SARPackager;
import com.tibco.objectrepo.vfile.VFileDirectory;
import com.tibco.objectrepo.vfile.VFileStream;
import com.tibco.objectrepo.vfile.zipfile.ZipVFileFactory;

public class EMFSarPackager implements SARPackager{
	
	public static final String SAR_ARCHIVE_NAME="Shared Archive.sar";
	
	
	protected MutableApplicationArchive ear;
	private StudioProjectConfiguration projectConfig;

	private String ownerProjectPath;
//	private DesignerProject index;
	private File projectPath;
	private CodeGenContext ctx;
	
	public static Set<String> excludedFileExtensions = new HashSet<String>();
	static {
//		excludedFileExtensions.add(".concept");
//		excludedFileExtensions.add(".conceptview");
//		excludedFileExtensions.add(".event");
//		excludedFileExtensions.add(".eventview");
//		excludedFileExtensions.add(".rule");
//		excludedFileExtensions.add(".statemachine");
//		excludedFileExtensions.add(".rulefunction");
		excludedFileExtensions.add(".substvar");
//		excludedFileExtensions.add(".aeschema");
//		excludedFileExtensions.add(".channel");
//		excludedFileExtensions.add(".process");
//		excludedFileExtensions.add(".idx");
//		excludedFileExtensions.add(".project");
//		excludedFileExtensions.add(".beproject");
//		excludedFileExtensions.add(".archive");
//		excludedFileExtensions.add(".rulefunctionimpl");
//		excludedFileExtensions.add(".scorecard");
//		excludedFileExtensions.add(".launch");
		excludedFileExtensions.add(".svn"); //Subversion
		excludedFileExtensions.add(".scc"); // Visual Sourcesafe
		excludedFileExtensions.add(".DS_Store"); // Visual Sourcesafe
	}
	public static Set<Pattern> excludedFolderPatterns = new HashSet<Pattern>();
	public static Set<Pattern> excludedFileExtensionPatterns = new HashSet<Pattern>();
	static {
		excludedFolderPatterns.add(Pattern.compile("(.*/)?.*~(/.*)?"));
		excludedFolderPatterns.add(Pattern.compile("(.*/)?#.*#(/.*)?"));
		excludedFolderPatterns.add(Pattern.compile("(.*/)?\\.#.*(/.*)?"));
		excludedFolderPatterns.add(Pattern.compile("(.*/)?%.*%(/.*)?"));
		excludedFolderPatterns.add(Pattern.compile("(.*/)?\\._.*(/.*)?"));
		excludedFolderPatterns.add(Pattern.compile("(.*/)?CVS(/.*)?"));//cvs
		excludedFolderPatterns.add(Pattern.compile("(.*/)?\\.cvsignore(/.*)?"));//cvs
		excludedFolderPatterns.add(Pattern.compile("(.*/)?SCCS(/.*)?"));//SCCS
		excludedFolderPatterns.add(Pattern.compile("(.*/)?\\.svn(/.*)?"));//Subversion
        excludedFileExtensions.add(".copyarea.dat"); //ClearCase
        excludedFileExtensions.add(".copyarea.db"); //ClearCase
        excludedFileExtensions.add(".taf"); //ClearCase
        excludedFileExtensions.add(".tjf"); //ClearCase
		excludedFileExtensions.add(".substvar"); //GVs
		excludedFileExtensions.add(".svn"); //Subversion
		excludedFileExtensions.add(".scc"); // Visual Sourcesafe
		excludedFileExtensions.add(".DS_Store"); // Visual Sourcesafe
//		excludedFolderPatterns.add(Pattern.compile("(.*/)?\\..*(/.*)?")); // /.*/

		for (String ext : excludedFileExtensions) {
			excludedFileExtensionPatterns.add(Pattern.compile(".*\\"+ext+"$",CASE_INSENSITIVE));
		}
		
	}
	

	/**
	 * @param project TODO
	 * @param ear
	 * @throws Exception 
	 */
	public EMFSarPackager(EMFProject project,
			MutableApplicationArchive ear) throws Exception {
		super();
		this.ctx = new CodeGenContext();
		this.projectConfig = project.getProjectConfiguration();
		this.ear = ear;
		this.ownerProjectPath = project.getRepoPath();
//		this.index = project.getRuntimeIndex();
		this.projectPath = new File(ownerProjectPath);
		ctx.put(CodeGenConstants.BE_PROJECT, project);
		ctx.put(CodeGenConstants.BE_PROJECT_NAME, project.getName());
		ctx.put(CodeGenConstants.BE_ENTERPRISE_ARCHIVE, ear);
		ctx.put(CodeGenConstants.BE_PROJECT_REPO_PATH, ownerProjectPath);


	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.util.packaging.Packager#close()
	 */
	@Override
	public void close() throws Exception {
		
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final JarOutputStream jarOutputStream = new JarOutputStream(baos);
		final List<String> entries = new ArrayList<String>();
		ctx.put(CodeGenConstants.BE_SAR_ENTRY_LIST, entries);
		ctx.put(CodeGenConstants.BE_SAR_PACKAGER_JAR_OUTPUT_STREAM, jarOutputStream);
		try {
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			IConfigurationElement[] extensions = reg
			.getConfigurationElementsFor(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_SAR_PROVIDER);
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement element = extensions[i];
				final Object o = element.createExecutableExtension(CodeGenConstants.BE_CODEGEN_EXTENSION_POINT_ATTR_SAR_PROVIDER);
				if (o instanceof ISharedArchiveResourcesProvider) {
					((ISharedArchiveResourcesProvider) o).addResources(ctx);
				}
			}	
        } catch(Throwable t) {
        	StudioCorePlugin.log(t);
        }
        finally {
        	jarOutputStream.close();
        	final ZipVFileFactory vFactory = (ZipVFileFactory) this.ear.getVFileFactory();
        	final VFileDirectory rootVDir = vFactory.getRootDirectory();
        	final VFileStream sarFile = rootVDir.createChild(SAR_ARCHIVE_NAME, null);
        	final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        	final InputStream bufferedInputStream = new BufferedInputStream(bais);
        	sarFile.update(bufferedInputStream);
        	bufferedInputStream.close();            
        	baos.close();
        }//finally
		
	}
}

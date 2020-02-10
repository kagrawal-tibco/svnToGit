package com.tibco.cep.studio.core.util.packaging.impl;

import com.tibco.be.parser.codegen.CodeGenConstants;
import com.tibco.be.parser.codegen.CodeGenContext;
import com.tibco.be.util.ByteArray;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.util.packaging.ISharedArchiveResourcesProvider;
import com.tibco.cep.studio.core.utils.ModelUtils;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

public class CoreSharedArchiveResourcesProviderImpl extends AbstractSharedResourcesProvider implements
		ISharedArchiveResourcesProvider {

	public CoreSharedArchiveResourcesProviderImpl() {
		super();
	}

	@Override
	public void addResources(CodeGenContext ctx) throws CoreException,
			IOException, FileNotFoundException, Exception {
		JarOutputStream jarOutputStream = (JarOutputStream) ctx
				.get(CodeGenConstants.BE_SAR_PACKAGER_JAR_OUTPUT_STREAM);
		File projectPath = new File((String) ctx
				.get(CodeGenConstants.BE_PROJECT_REPO_PATH));
		EMFProject project = (EMFProject) ctx.get(CodeGenConstants.BE_PROJECT);
		DesignerProject index = (DesignerProject) project.getRuntimeIndex(AddOnType.CORE);
		// check for null index
		if(index != null) {
			List<String> entries = (List<String>) ctx.get(CodeGenConstants.BE_SAR_ENTRY_LIST);
			List<File> files = getSharedResources(projectPath);
			
			if (files.size() > 0) {
				for (File f : files) {
					addResource(f, jarOutputStream, entries, projectPath);
				}
				
				final File indexFile = new File(".idx");
				byte[] indexBuffer = saveIndextoBuffer(index, indexFile);
				ByteArrayInputStream indexInputStream = new ByteArrayInputStream(
						indexBuffer);
				
				addResource(indexInputStream, indexFile.getPath(), jarOutputStream,
						entries);
				
			}
			EList<DesignerProject> plibs = index.getReferencedProjects();
			for (int i = 0; i < plibs.size(); i++) {
				DesignerProject plib = plibs.get(i);
				String plibPath = plib.getArchivePath();
				EList<EntityElement> entityElements = plib.getEntityElements();
				for (EntityElement element : entityElements) {
					if (element instanceof SharedEntityElement) {
						byte[] b = ModelUtils.getBytesForEObject(element.getEntity());
						String entryPathString = ((SharedEntityElement) element).getEntryPath().startsWith("/") ? 
								((SharedEntityElement) element).getEntryPath().substring(1) : ((SharedEntityElement) element).getEntryPath();
						IPath entryPath = new Path(entryPathString + ((SharedEntityElement) element).getFileName());
                        if(!StudioCore.isIgnoredHint(new File(entryPath.toPortableString()))){
						    addResource(new ByteArrayInputStream(b), entryPath.toPortableString(),
                                    jarOutputStream, entries);
                        }
					}
				}

				ByteArray buf = new ByteArray(8192);
				JarInputStream jis = new JarInputStream(new FileInputStream(
						plibPath));
				for (JarEntry je = jis.getNextJarEntry(); je != null; je = jis
				.getNextJarEntry()) {
					buf.reset();
					if (je.isDirectory()) {
						continue;
					}
					StudioCorePlugin.debug("Adding jar entry:" + je.getName());
					byte[] b = getBytes(jis, buf);
					IPath entryPath = new Path(je.getName());
                    if (!StudioCore.isIgnoredHint(new File(entryPath.toPortableString()))){
                        addResource(new ByteArrayInputStream(b), entryPath.toPortableString(),
                                jarOutputStream, entries);
                    }
				}
			}
		}
	}
	
	private byte[] saveIndextoBuffer(DesignerProject index, File indexFile) throws CoreException, IOException {
		final Map options = new HashMap();
		URI indexuri = URI.createFileURI(indexFile.getPath());
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(indexuri);
		resource.getContents().add(index);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		options.put( XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD );
		resource.save(baos,options);
		return baos.toByteArray();
	}

}

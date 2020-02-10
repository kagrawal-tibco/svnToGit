package com.tibco.cep.bpmn.core.codegen;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarOutputStream;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;

import com.tibco.cep.bpmn.core.BpmnCorePlugin;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.studio.core.util.packaging.ISharedArchiveResourcesProvider;
import com.tibco.cep.studio.core.util.packaging.impl.AbstractSharedResourcesProvider;
import com.tibco.cep.studio.parser.codegen.CodeGenConstants;
import com.tibco.cep.studio.parser.codegen.CodeGenContext;

public class BpmnSharedArchiveResourcesProvider extends
		AbstractSharedResourcesProvider implements
		ISharedArchiveResourcesProvider {

	public BpmnSharedArchiveResourcesProvider() {
		super();
	}

	@Override
	public void addResources(CodeGenContext ctx) throws Exception {
		JarOutputStream jarOutputStream = (JarOutputStream) ctx.get(CodeGenConstants.BE_SAR_PACKAGER_JAR_OUTPUT_STREAM);
		File projectPath = new File((String) ctx.get(CodeGenConstants.BE_PROJECT_REPO_PATH));
		String projectName = (String) ctx.get(CodeGenConstants.BE_PROJECT_NAME);
		IProject workspaceProject  = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		EObject index = BpmnCorePlugin.getDefault().getBpmnModelManager().getBpmnIndex(workspaceProject);
		//check for null index
		if(index != null) {
			List<String> entries = (List<String>) ctx.get(CodeGenConstants.BE_SAR_ENTRY_LIST);
			
			final File indexFile = new File(".bdx");
			byte[] indexBuffer = saveIndextoBuffer(projectName,index, indexFile);
			ByteArrayInputStream indexInputStream = new ByteArrayInputStream(
					indexBuffer);
			
			addResource(indexInputStream, indexFile.getPath(), jarOutputStream,
					entries);
		}

	}
	
	private byte[] saveIndextoBuffer(String projectName,EObject index, File indexFile) throws CoreException, IOException {
		final Map<Object,Object> options = new HashMap<Object,Object>();
		URI indexuri = URI.createFileURI(indexFile.getPath());
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(indexuri);
		resource.getContents().add(index);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		options.put( XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD );
		options.put( XMIResource.OPTION_URI_HANDLER, new SharedResourceURIHandler(projectName) );
		resource.save(baos,options);
		return baos.toByteArray();
	}

}

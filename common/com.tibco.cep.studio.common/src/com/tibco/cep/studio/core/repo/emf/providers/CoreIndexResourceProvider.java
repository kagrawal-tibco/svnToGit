package com.tibco.cep.studio.core.repo.emf.providers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarFile;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.tibco.cep.addon.ExternalResourceInfoProviderFinder;
import com.tibco.cep.addon.IExternalResourceInfoProvider;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.repo.Project;
import com.tibco.cep.repo.ResourceProvider;
import com.tibco.cep.repo.provider.AbstractAddOnIndexResourceProvider;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.JavaSourceFolderEntry;
import com.tibco.cep.studio.common.configuration.ProjectLibraryEntry;
import com.tibco.cep.studio.common.configuration.StudioProjectConfiguration;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.BaseBinaryStorageIndexCreator;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.DefaultProblemHandler;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;
import com.tibco.cep.studio.core.repo.emf.EMFProject;
import com.tibco.cep.studio.core.rules.IRulesProblem;
import com.tibco.objectrepo.ObjectRepoException;
import com.tibco.objectrepo.vfile.VFile;
import com.tibco.objectrepo.vfile.VFileStream;

public class CoreIndexResourceProvider extends AbstractAddOnIndexResourceProvider<EMFProject> implements ResourceProvider{
	private static final String BE_PROJECT_CONFIG = "beproject";
	static {
        ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
        final EPackage.Registry i = EPackage.Registry.INSTANCE;
        i.put("http:///com/tibco/cep/studio/common/configuration/model/project_configuration.ecore", ConfigurationPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore", ModelPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designer/index/core/model/ontology_index.ecore", IndexPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/element", ElementPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/event", EventPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/service/channel", ChannelPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/rule", RulePackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/states", StatesPackage.eINSTANCE);
        i.put("http:///com/tibco/cep/designtime/core/model/archive", ArchivePackage.eINSTANCE);
        i.put("http:///com/tibco/cep/decision/table/model/DecisionTable.ecore", DtmodelPackage.eINSTANCE);
        //load addon registries
        Collection<IExternalResourceInfoProvider> providers = ExternalResourceInfoProviderFinder.getInstance().getRegisteredProviders();
        for (IExternalResourceInfoProvider provider : providers) {
			i.putAll(provider.getPackageRegistry());
		}
    }
	
	/**
	 * Runtime index
	 */
	protected DesignerProject index;
	/**
	 * 
	 * @param project
	 */
	public CoreIndexResourceProvider(EMFProject project) {
		super(project);
//		this.emfTnsCache = new EMFTnsCache(project.getRepoPath());
	}
	
	
	@Override
	public AddOnType getAddOn() {
		return AddOnType.CORE;
	}
	
	@Override
	public void setName(String name) {
		DesignerProject index = getIndex();
		index.setName(name);
	}
	

	
	/**
	 * 
	 * @return
	 */
	
	public DesignerProject getIndex() {
		if(index == null) {
			index = IndexFactory.eINSTANCE.createDesignerProject();	
			if (!StudioProjectCache.getInstance().containsKey(getProject().getName())) {
				StudioProjectCache.getInstance().putIndex(getProject().getName(),
						index);
			}
		}
		index.setName(getProject().getName());
		return index;
	}
	@Override
	public void init() throws Exception {
		
		
	}
	
	@Override 
	public void preload() {
		StudioProjectCache.getInstance().remove(getProject().getName());
	}
	
	@Override
	public void postLoad() {
		

	}
	
	
	@Override
	public void loadProjectLibraries(StudioProjectConfiguration projectConfig) throws Exception {
		// load referenced projects first in reverse order, so that any duplicate entities are used
    	// from the local project
		for (ProjectLibraryEntry ple: projectConfig.getProjectLibEntries()) {
			String libRef = ple.getPath(ple.isVar());
			System.out.println("indexing "+libRef);
			File  file = new File(libRef);
			if(file.exists()) {
				JarFile jarFile = new JarFile(libRef);
				DesignerProject dp = IndexFactory.eINSTANCE.createDesignerProject();
				dp.setName(jarFile.getName());
				dp.setRootPath(libRef);
				dp.setArchivePath(libRef);
				BaseBinaryStorageIndexCreator creator = new BaseBinaryStorageIndexCreator(projectConfig.getName(), dp, jarFile);
				creator.index();
				getIndex().getReferencedProjects().add(dp);
			}
		}
		
	}
	

	@Override
	public int deserializeResource(String uri, InputStream is, Project project,
			VFileStream stream) throws Exception {
		String ext = uri.substring(uri.lastIndexOf(".")+1);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c = 0;
		while((c = is.read()) != -1) {
			baos.write(c);
		}
		is.reset();
		if(getIndex().getRootPath() == null) {
			VFile file = getRoot(stream);
			getIndex().setRootPath(file.getFullURI());
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		if(CommonIndexUtils.isJavaType(ext)){
			if (isInsideJavaSourceFolder(uri)) {
				IndexBuilder.indexJava(getIndex(),uri,bais);
			}
		} else if(CommonIndexUtils.isEMFType(ext)) {
			EObject eObj = CommonIndexUtils.loadEObject(uri,bais);
			IndexBuilder.indexEObject(getIndex(),uri, eObj);
		}
		if(CommonIndexUtils.isRuleType(ext)) {
			DefaultProblemHandler problemHandler = new DefaultProblemHandler();
			RuleElement ruleElement = IndexBuilder.indexRule(getIndex(), uri,bais,problemHandler);
			if(!problemHandler.hasProblems()) {
				ByteArrayOutputStream xmios = new ByteArrayOutputStream();
				HashMap options = new HashMap();
				options.put( XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD );
				XMIResource xmiResource = new XMIResourceImpl(URI.createURI(""));
				EObject ruleElementCopy = EcoreUtil.copy(ruleElement);
				xmiResource.getContents().add(ruleElementCopy);
				xmiResource.save(xmios,options);
				bais = new ByteArrayInputStream(xmios.toByteArray());			
			} else {
				List<IRulesProblem> problems = problemHandler.getHandledProblems();
				StringBuffer sb = new StringBuffer();
				sb.append("Syntax Errors found while parsing :").append(uri);
				for (IRulesProblem rulesSyntaxProblem : problems) {
					sb.append("\nError :")
					.append(rulesSyntaxProblem.getErrorMessage())
					.append("\tline:")
					.append(rulesSyntaxProblem.getLine())
					.append("\tOffset:")
					.append(rulesSyntaxProblem.getOffset());
				}
				throw new RuntimeException(sb.toString());
			}
		} 
		
		if (!CommonIndexUtils.isJavaType(ext) && !CommonIndexUtils.isEMFType(ext) && !CommonIndexUtils.isRuleType(ext)) {
			if (isInsideJavaSourceFolder(uri) && isValidPackage(uri)) {
				// need to ignore invalid resources (i.e. .svn resources)
				IndexBuilder.indexJavaResources(getIndex(),uri,bais);
			}
		}
		
		if(CommonIndexUtils.isTnsCacheResource(ext)) {
			if(bais != null) {
				bais.reset();
				project.getTnsCache().resourceChanged(uri, bais);
			}
		}
		
		return ResourceProvider.SUCCESS_CONTINUE;
	}
	
	private boolean isValidPackage(String uri) {
		Path p = new Path(uri);
		while (isInsideJavaSourceFolder(p.toString())) {
			if (p.lastSegment().startsWith(".")) {
				return false;
			}
			p = p.removeLastSegments(1);
		}

		return true;
	}


	VFile getRoot(final VFile vf) throws ObjectRepoException {
		VFile file = vf;
		while(file.getParent() != null && file.getParent() != file) {
			file = file.getParent();
		}
		return file;
	}

	private boolean isInsideJavaSourceFolder(String uri) {
		boolean present = false;
		for (JavaSourceFolderEntry entry : getProject().getProjectConfiguration().getJavaSourceFolderEntries()) {
			 if (uri.contains(entry.getPath())) {
				present= true;
				break;
			 }
		}
		return present;
	}

	@Override
	public boolean supportsResource(String uri) {
		if (isInsideJavaSourceFolder(uri)) {
			return true;
		}
		String ext = uri.substring(uri.lastIndexOf(".")+1);
		return CommonIndexUtils.isEMFType(ext) || 
		CommonIndexUtils.isRuleType(ext) || 
		CommonIndexUtils.isTnsCacheResource(ext);
	}


}

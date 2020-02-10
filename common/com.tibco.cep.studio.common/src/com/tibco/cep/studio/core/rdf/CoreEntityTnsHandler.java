package com.tibco.cep.studio.core.rdf;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.TnsEntityHandler;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.IAddOnLoader;
import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.core.adapters.CoreOntologyAdapter;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.DefaultProblemHandler;
import com.tibco.cep.studio.core.index.utils.IndexBuilder;

public class CoreEntityTnsHandler implements TnsEntityHandler {
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
    }

	private IAddOnLoader addonLoader;
	public CoreEntityTnsHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public void setAddOnLoader(IAddOnLoader l) {
		this.addonLoader = l;
	}

	@Override
	public Entity getEntity(InputSource input, String uri) throws Exception {
		Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(URI.createURI(uri));
		Resource resource;
		if (factory != null) {
			resource = factory.createResource(URI.createURI(uri));
		} else {
			resource = new XMIResourceImpl(URI.createURI(uri));
		}

		Thread thread = Thread.currentThread();
		ClassLoader loader = thread.getContextClassLoader();
		try {
			if (!CommonIndexUtils.sunClassAvailable()) {
				System.setProperty("javax.xml.parsers.SAXParserFactory",
						"org.apache.xerces.jaxp.SAXParserFactoryImpl");
				thread.setContextClassLoader(this.getClass().getClassLoader());
			}
			resource.load(input.getByteStream(), null);
			EList<EObject> contents = resource.getContents();
			if (contents.size() > 0) {
				EObject eObj = contents.get(0);
				if (eObj instanceof RuleElement) {
					RuleElement re = (RuleElement) eObj;
					eObj = re.getRule();
				}
				if (eObj instanceof Compilable) {
					CoreAdapterFactory.clearCachedAdapter(
							((Compilable) eObj).getOwnerProjectName(),
							(Compilable) eObj);
					return CoreAdapterFactory.INSTANCE.createAdapter(
							(Compilable) eObj, new CoreOntologyAdapter(
									((Compilable) eObj).getOwnerProjectName()));
				} else {
					com.tibco.cep.designtime.core.model.Entity entity = (com.tibco.cep.designtime.core.model.Entity) eObj;
					CoreAdapterFactory.clearCachedAdapter(
							entity.getOwnerProjectName(), entity);
					return CoreAdapterFactory.INSTANCE.createAdapter(
							entity,
							new CoreOntologyAdapter(entity
									.getOwnerProjectName()));
				}

			}
//		} catch (Exception e) {
//			input.getByteStream().reset();
//			int idx = uri.lastIndexOf('.');
//			String ext = "";
//			if (idx >= 0) {
//				ext = uri.substring(idx+1);
//			}
//			if(CommonIndexUtils.isRuleType(ext) && input.getByteStream().read() != '<') {
//				input.getByteStream().reset();
//				DefaultProblemHandler problemHandler = new DefaultProblemHandler();
//				DesignerProject proj = IndexFactory.eINSTANCE.createDesignerProject();
//				proj.setName("name");
//				proj.setRootPath(uri.substring(0, uri.lastIndexOf('/')));
//				RuleElement ruleElement = IndexBuilder.indexRule(proj, uri,input.getByteStream(),problemHandler);
//				Compilable rule = ruleElement.getRule();
//				return CoreAdapterFactory.INSTANCE.createAdapter(
//						(Compilable) rule, new CoreOntologyAdapter(
//								((Compilable) rule).getOwnerProjectName()));
//			}
//			throw e;
		} finally {
			thread.setContextClassLoader(loader);
			// this will set it back to sun version (if available),
			// so that Studio can use the sun version elsewhere
			CommonIndexUtils.sunClassAvailable();
		}
		return null;
	}

}

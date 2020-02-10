package com.tibco.cep.studio.ui.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.archive.util.ArchiveAdapterFactory;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.util.ElementAdapterFactory;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.util.EventAdapterFactory;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.util.RuleAdapterFactory;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.util.ChannelAdapterFactory;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.states.util.StatesAdapterFactory;
import com.tibco.cep.designtime.core.model.util.ModelAdapterFactory;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;
import com.tibco.cep.studio.core.model.topology.util.TopologyAdapterFactory;
import com.tibco.cep.studio.ui.StudioUIPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class ManageResourceUtils {

	public static ManageResourceUtils eINSTANCE = new ManageResourceUtils();
	
	private AdapterFactoryEditingDomain createDesignerEditingDomain() {
		List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
		factories.add(new ResourceItemProviderAdapterFactory());
		factories.add(new ModelAdapterFactory());
		factories.add(new ElementAdapterFactory());
		factories.add(new EventAdapterFactory());
		factories.add(new ChannelAdapterFactory());
		factories.add(new RuleAdapterFactory());
		factories.add(new StatesAdapterFactory());
		factories.add(new ArchiveAdapterFactory());
		factories.add(new TopologyAdapterFactory());
		factories.add(new ReflectiveItemProviderAdapterFactory());
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(factories);

		CommandStack commandStack = new BasicCommandStack();

		return new AdapterFactoryEditingDomain(adapterFactory, commandStack, new HashMap<Resource, Boolean>());
	}

	public AdapterFactoryEditingDomain getEditingDomain(IFile file) {
		AdapterFactoryEditingDomain editingDomain = createDesignerEditingDomain();
		// register the packages
		editingDomain.getResourceSet().getPackageRegistry().put(ModelPackage.eNS_URI, ModelPackage.eINSTANCE);
		editingDomain.getResourceSet().getPackageRegistry().put(ElementPackage.eNS_URI, ElementPackage.eINSTANCE);
		editingDomain.getResourceSet().getPackageRegistry().put(EventPackage.eNS_URI, EventPackage.eINSTANCE);
		editingDomain.getResourceSet().getPackageRegistry().put(ChannelPackage.eNS_URI, ChannelPackage.eINSTANCE);
		editingDomain.getResourceSet().getPackageRegistry().put(RulePackage.eNS_URI, RulePackage.eINSTANCE);
		editingDomain.getResourceSet().getPackageRegistry().put(StatesPackage.eNS_URI, StatesPackage.eINSTANCE);
		editingDomain.getResourceSet().getPackageRegistry().put(ArchivePackage.eNS_URI, ArchivePackage.eINSTANCE);
		editingDomain.getResourceSet().getPackageRegistry().put(TopologyPackage.eNS_URI, TopologyPackage.eINSTANCE);
		try {
			if(file.exists()){
				StudioUIPlugin.getDefault().getManageResource().loadResource(editingDomain.getResourceSet(), file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return editingDomain;
	}

	public static EObject getParent(EObject eObjet, Class<?> parent) {
		EObject res = null;

		while (eObjet != null && res == null) {
			if (parent.isInstance(eObjet)) {
				res = eObjet;
			} else {
				eObjet = eObjet.eContainer();
			}
		}

		return res;
	}
}

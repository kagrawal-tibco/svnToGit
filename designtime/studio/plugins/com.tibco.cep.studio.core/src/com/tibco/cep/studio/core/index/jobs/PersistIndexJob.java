package com.tibco.cep.studio.core.index.jobs;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;

import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class PersistIndexJob extends Job {

	private DesignerProject fIndex;
	private Map options;

	public PersistIndexJob(DesignerProject index) {
		super("Persisting index for "+index.getName());
		this.fIndex = index;
		this.options = new HashMap();
		options.put( XMIResource.OPTION_PROCESS_DANGLING_HREF, XMIResource.OPTION_PROCESS_DANGLING_HREF_DISCARD );
		options.put(XMIResource.OPTION_ENCODING, "UTF-8");
	}

	@Override
	public boolean belongsTo(Object family) {
		return family == fIndex;
	}
	
	public IStatus runNow(IProgressMonitor monitor) {
		return run(monitor);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		String indexLocation = IndexUtils.getIndexLocation(fIndex.getName());
		if (indexLocation != null) {
			URI uri = URI.createFileURI(indexLocation);
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = resourceSet.createResource(uri);
			try {
				// saving the index causes a traversal/iteration over the index contents, 
				// so we need to synchronize on it to avoid concurrent modification exceptions
				synchronized (fIndex) {
					fIndex.setLastPersisted(new Date());
					resource.getContents().add(fIndex);
					resource.save(options);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return Status.OK_STATUS;
	}

}

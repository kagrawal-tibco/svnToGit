package com.tibco.cep.studio.ui.editors.archives;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

import com.tibco.cep.designtime.core.model.archive.ArchiveFactory;
import com.tibco.cep.designtime.core.model.archive.EnterpriseArchive;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractArchiveSaveableEditorPart extends AbstractSaveableEntityEditorPart implements IArchiveConstants{
	
		protected EnterpriseArchive archive;
	    protected IFile file = null;
	    protected EnterpriseArchiveEditorInput archiveEditorInput;
	    
	    public EnterpriseArchive getArchive() {
			if (archive == null) {
				Resource resource = (Resource) getEditingDomain().getResourceSet().getResources().get(0);

				for (Iterator<?> iterator = resource.getContents().iterator(); iterator
						.hasNext();) {
					Object object = iterator.next();
					if (object instanceof EnterpriseArchive) {
						archive = (EnterpriseArchive) object;
						break;
					}
				}

				if (archive == null) {
					archive = ArchiveFactory.eINSTANCE.createEnterpriseArchive();
					archive.setDescription("");
					if (file != null) {
						archive.setOwnerProjectName(file.getProject().getName());
					}
					resource.getContents().add((EObject) archive);
				}
			}
			return archive;
		}
	    
	   
	    /**
		 * This is how the framework determines which interfaces we implement.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Object getAdapter(Class key) {
			if (key.equals(EnterpriseArchive.class))
				return getArchive();
			return super.getAdapter(key);
		}
		
		public void setEnterpriseArchive(EnterpriseArchive archive) {
			this.archive = archive;
		}
}

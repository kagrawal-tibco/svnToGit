package com.tibco.cep.studio.dashboard.ui.refactoring;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.CompositeChange;

import com.tibco.cep.designtime.core.model.beviewsconfig.Header;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynImageURLType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class HeaderRefactoringParticipant extends DashboardRefactoringParticipant {

	public HeaderRefactoringParticipant() {
		super(BEViewsElementNames.EXT_HEADER, new String[0]);
	}

	@Override
	public Change processEntity(Object elementToRefactor, String projectName, IProgressMonitor pm, boolean preChange) throws CoreException, OperationCanceledException {
		if (elementToRefactor instanceof IFile) {
			IFile file = (IFile) elementToRefactor;
			if (isExtensionOfInterest(file.getFileExtension()) == true) {
				//we are in business
				try {
					CompositeChange compositeChange = new CompositeChange(changeTitle());
					if (isDeleteRefactor() == true) {
						updateImageReference(file, projectName, compositeChange);
					} else {
						updateImageReference(file, projectName, compositeChange);
					}
					if (compositeChange != null && compositeChange.getChildren().length > 0) {
						return compositeChange;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}
		return super.processEntity(elementToRefactor, projectName, pm, preChange);
	}

	@Override
	protected boolean isExtensionOfInterest(String extension) {
		for (String acceptableImageExtension : new SynImageURLType().getAllowedImageTypes()) {
			if (acceptableImageExtension.equalsIgnoreCase(extension) == true) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String changeTitle() {
		return "Header Configuration Changes:";
	}

	private void updateImageReference(IFile file, String projectName, CompositeChange compositeChange) throws Exception {
		IProject project = getResource().getProject();
		LocalECoreFactory coreFactory = LocalECoreFactory.getInstance(project);
		List<LocalElement> headers = coreFactory.getChildren(BEViewsElementNames.HEADER);
		for (LocalElement headerElement : headers) {
			Header header = (Header) headerElement.getEObject();
			if (header.getBrandingImage() != null && header.getBrandingImage().contains(file.getProjectRelativePath().toString()) == true) {
				Header headerCopy = (Header) EcoreUtil.copy(header);
				boolean changed = false;
				if (isRenameRefactor() == true) {
					IPath path = file.getProjectRelativePath();
					//retrieve and store the extension
					String extension = path.getFileExtension();
					//remove last segment which is the name of the file plus the extension
					path = path.removeLastSegments(1);
					//append the new name
					path = path.append(getNewElementName());
					//append the extension
					path = path.addFileExtension(extension);
					//update the branding image
					headerCopy.setBrandingImage("/"+path.toString());
					changed = true;
				}
				else if (isMoveRefactor() == true) {
					//update the branding image
					headerCopy.setBrandingImage(getNewElementPath()+file.getName());
					changed = true;
				}
				else if (isDeleteRefactor() == true) {
					headerCopy.setBrandingImage(null);
					changed = true;
				}
				if (changed == true) {
					Change change = createTextFileChange(IndexUtils.getFile(projectName, headerCopy), headerCopy);
					compositeChange.add(change);
				}
			}
		}
	}

	@Override
	protected void updateReferences(EObject object, String projectName, CompositeChange compositeChange) throws Exception {
		throw new UnsupportedOperationException("updateReferences");
	}
}

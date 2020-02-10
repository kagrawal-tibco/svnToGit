package com.tibco.cep.studio.ui.navigator.view;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.studio.ui.navigator.NavigatorPlugin;

public class StudioProjectJavaElementFilter extends ViewerFilter {

	public StudioProjectJavaElementFilter() {
		// TODO Auto-generated constructor stub
	}

	QualifiedName loggedJavaError = new QualifiedName("com.tibco.be.navigator", "javaModelErrorLogged");
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof IFolder) {
			IFolder dir = (IFolder)element;
			IJavaProject javaProject = JavaCore.create(dir.getProject());
			IPath outPutPath=dir.getProject().getLocation();
			try {
				if (!javaProject.exists()) {
					Object sessionProperty = dir.getProject().getSessionProperty(loggedJavaError);
					if (sessionProperty == null) {
						NavigatorPlugin.log("Project "+dir.getProject().getName()+" does not contain Java Model");
						dir.getProject().setSessionProperty(loggedJavaError, "true");
					}
					return true;
				}
				IPath tempPath = javaProject.getOutputLocation();
				for(int iSeg=1; iSeg<tempPath.segmentCount();iSeg++){
					outPutPath = outPutPath.append(tempPath.segment(iSeg));
				}
				
			} catch (JavaModelException e) {
				NavigatorPlugin.log("Project "+dir.getProject().getName()+" does not contain Java Model", e);
			} catch (CoreException e) {
				e.printStackTrace();
			}
			
			if(outPutPath.equals(dir.getLocation())){
				return false;
			}
		}				
		return true;
	}

}

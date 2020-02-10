package com.tibco.cep.studio.core.validation;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IProblemRequestor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.WorkingCopyOwner;
import org.eclipse.jdt.core.compiler.IProblem;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.util.StudioJavaUtil;

/**
 * 
 * @author sasahoo
 *
 */
public class StudioJavaSourceValidator extends DefaultResourceValidator {

	
	public static final String JAVA_SOURCE_PROBLEM_TYPE = StudioCorePlugin.PLUGIN_ID + ".studioJavaSourceProblem";
	
	@Override
	public boolean canContinue() {
		// can continue if any java resource got any validation problem
		return true;
	}
	
	@Override
	public boolean validate(ValidationContext validationContext) {		
		
		final IResource resource = validationContext.getResource();	
		
		if (resource == null) return true;
		
		if (!StudioJavaUtil.isJavaSource((IFile)resource)) {
			return true;
		}
		
		String uri = ((IFile)resource).getFullPath().toString();
		if (!StudioJavaUtil.isInsideJavaSourceFolder(uri, resource.getProject().getName())) {
			return true;
		}
		
		try {
			ICompilationUnit workingCopy = JavaCore.createCompilationUnitFrom((IFile)resource);
			// create problem requestor for accumulating discovered problems
			IProblemRequestor problemRequestor = new IProblemRequestor() {
				public void acceptProblem(IProblem problem) {
					reportProblem(resource, problem.getMessage(), problem.getSourceLineNumber(), 
							problem.isError() ? IMarker.SEVERITY_ERROR : IMarker.SEVERITY_WARNING, JAVA_SOURCE_PROBLEM_TYPE, false);
				}
				public void beginReporting() {}
				public void endReporting() {}
				public boolean isActive() { return true; } // will detect problems if active
			};

			//register for working copy owner to hold source with error
			workingCopy = workingCopy.getWorkingCopy(newWorkingCopyOwner(problemRequestor), null);

			workingCopy.discardWorkingCopy();

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}
	
	protected WorkingCopyOwner newWorkingCopyOwner(final IProblemRequestor problemRequestor) {
		return new WorkingCopyOwner() {
			public IProblemRequestor getProblemRequestor(ICompilationUnit unit) {
				return problemRequestor;
			}
		};
	}
	
	@Override
	protected void deleteMarkers(IResource file) {
		super.deleteMarkers(file);
		try {
			file.deleteMarkers(JAVA_SOURCE_PROBLEM_TYPE, false, IResource.DEPTH_ZERO);
		} catch (CoreException ce) {
		}
	}
	
}

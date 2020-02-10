/**
 * 
 */
package com.tibco.cep.studio.decision.table.constraintpane;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * @author aathalye
 *
 */
public class DecisionTableAnalysisProblemResolutionProvider implements
		IMarkerResolutionGenerator {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
	 */
	@Override
	public IMarkerResolution[] getResolutions(final IMarker marker) {
		IMarkerResolution[] resolutions = new IMarkerResolution[]{
				new DecisionTableAnalysisProblemResolution(),
				new IMarkerResolution() {

					/* (non-Javadoc)
					 * @see org.eclipse.ui.IMarkerResolution#getLabel()
					 */
					@Override
					public String getLabel() {
						try {
							return "Ignore \"" + marker.getAttribute(IMarker.MESSAGE) + "\"";
						} catch (CoreException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}

					/* (non-Javadoc)
					 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
					 */
					@Override
					public void run(IMarker marker) {
						// TODO Auto-generated method stub
						
					}
					
				}
		};
		return resolutions;
	}

}

package com.tibco.cep.bpmn.ui.editor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;

import com.tibco.cep.bpmn.ui.BpmnUIPlugin;

public class GraphDocumentProvider extends FileDocumentProvider {
	
	
	

	public GraphDocumentProvider() {
		
	}
	
	
	@Override
	protected IDocument createEmptyDocument() {
		return new GraphDocument();
	}	

	protected IAnnotationModel createAnnotationModel(Object element) throws CoreException {
		if (element instanceof IGraphEditorInput) {
			IGraphEditorInput input= (IGraphEditorInput) element;
			GraphResourceMarkerAnnotationModel model = new GraphResourceMarkerAnnotationModel(input);
			return model;
		}

		return super.createAnnotationModel(element);
	}

	public IAnnotationModel getAnnotationModel(Object element) {
		IAnnotationModel model= super.getAnnotationModel(element);
		if (model != null)
			return model;
		if(element instanceof IGraphEditorInput) {
			IGraphEditorInput input= (IGraphEditorInput) element;
			GraphResourceMarkerAnnotationModel amodel = new GraphResourceMarkerAnnotationModel(input);
			ElementInfo info = getElementInfo(element);
			if(info != null)
				amodel.connect(info.fDocument);
		}
		

		return null;
	}
	
	
	@Override
	protected void addUnchangedElementListeners(Object element, ElementInfo info) {
		super.addUnchangedElementListeners(element, info);
		IAnnotationModel model = info.fModel;
		IDocument document = info.fDocument;
		model.connect(document);
	}
	

	

	@Override
	protected void connected() {
//		Object element = getConnectedElements().next();
//		IAnnotationModel model = getElementInfo(element).fModel;
//		IDocument document = getElementInfo(element).fDocument;
//		model.connect(document);
	}
	
	
	
	@Override
	public void changed(Object element) {
		// TODO Auto-generated method stub
		super.changed(element);
		try {
			if (element instanceof IResource) {
				IResource r = (IResource) element;
				IMarker[] markers = r.findMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
				if (markers != null && markers.length > 0) {
					IAnnotationModel model = getAnnotationModel(r);
					IDocument document = getDocument(r);
					for (int i = 0; i < markers.length; i++)
						((AbstractMarkerAnnotationModel) model).updateMarker(document, markers[i], null);
				}
			}
		} catch (CoreException e) {
			BpmnUIPlugin.log(e);
		}
	}
	

}

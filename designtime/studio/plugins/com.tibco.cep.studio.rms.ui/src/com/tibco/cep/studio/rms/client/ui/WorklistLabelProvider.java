package com.tibco.cep.studio.rms.client.ui;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.rms.artifacts.ArtifactsType;
import com.tibco.cep.studio.rms.model.ArtifactReviewTask;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;
import com.tibco.cep.studio.rms.model.event.IWorklistModelChangeListener;
import com.tibco.cep.studio.rms.model.event.ModelChangeEvent;
import com.tibco.cep.studio.rms.model.event.ModelChangeEvent.Features;
import com.tibco.cep.studio.rms.ui.RMSUIPlugin;
import com.tibco.cep.studio.rms.ui.utils.RMSUIUtils;

public class WorklistLabelProvider extends LabelProvider implements IWorklistModelChangeListener {

	private HashMap<String, Image> imageCache = new HashMap<String, Image>();
	private HashMap<String, Image> overlayImageCache = new HashMap<String, Image>();
	
	public static final String CLASS = WorklistLabelProvider.class.getName();
	/**
	 * Keep a reference to this
	 */
	private TreeViewer clientTaskViewer;

	
	public WorklistLabelProvider(final TreeViewer clientTaskViewer) {
		this.clientTaskViewer = clientTaskViewer;
	}
	/*
	 * @see ILabelProvider#getImage(Object)
	 */
	public Image getImage(Object element) {
		ImageDescriptor descriptor = null;
		boolean changed = false;
		if (element instanceof ArtifactReviewTask) {
			ArtifactReviewTask task = (ArtifactReviewTask)element;
			descriptor = RMSUIPlugin.getImageDescriptor("icons/worklist_editor.png");
			if (task.isChanged()) {
				changed = true;
			}	
			
		} else if (element instanceof CommittedArtifactDetails) {
			CommittedArtifactDetails committedArtifactDetails = (CommittedArtifactDetails)element;
			ArtifactsType type = 
				ArtifactsType.get(committedArtifactDetails.getArtifact().getArtifactExtension());
			descriptor = RMSUIUtils.getArtifactImageDescriptor(type);
			changed = committedArtifactDetails.isChanged();
		}
		
		if (descriptor == null) {
			RMSUIPlugin.log(CLASS, "Image missing for type ");
		}
		
		Image image = (Image)imageCache.get(element.toString());
		if (image == null) {
			image = descriptor.createImage();
			imageCache.put(element.toString(), image);
		}
		if (changed) {
			if (!overlayImageCache.containsKey(element.toString())) {
				image = new DecorationOverlayIcon(image, 
						RMSUIPlugin.getImageDescriptor("icons/checked_task_overlay.gif"),IDecoration.BOTTOM_LEFT).createImage();
				image = new DecorationOverlayIcon(image, 
						RMSUIPlugin.getImageDescriptor("icons/dirty_overlay.png"),IDecoration.TOP_LEFT).createImage();
				overlayImageCache.put(element.toString(), image);
			}
			image = overlayImageCache.get(element.toString());
		}
		return image;
	}
	
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.rms.model.event.IWorklistModelChangeListener#modelChanged(com.tibco.cep.studio.rms.model.event.ModelChangeEvent)
	 */
	public void modelChanged(ModelChangeEvent modelChangeEvent) {
		Features feature = modelChangeEvent.getFeature();
		CommittedArtifactDetails source = 
			(CommittedArtifactDetails)modelChangeEvent.getSource();
		
		switch (feature) {
		case STATUS_CHANGE :
			source.isChanged();
		}
	}



	/*
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element) {
		if (element instanceof ArtifactReviewTask) {
			ArtifactReviewTask task = (ArtifactReviewTask)element;
			String taskId = task.getTaskId();
			return taskId;
		} 
		if (element instanceof CommittedArtifactDetails) {
			CommittedArtifactDetails details = (CommittedArtifactDetails)element;
			String path = details.getArtifact().getArtifactPath();
			return path;
		}
		return element == null? "": element.toString();
	}

	public void dispose() {
		for (Iterator<Image> i = imageCache.values().iterator(); i.hasNext();) {
			((Image) i.next()).dispose();
		}
		for (Iterator<Image> i = overlayImageCache.values().iterator(); i.hasNext();) {
			((Image) i.next()).dispose();
		}
		imageCache.clear();
		overlayImageCache.clear();
	}
	
	public TreeViewer getClientTaskViewer() {
		return clientTaskViewer;
	}
}
package com.tibco.cep.studio.rms.client.ui;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

import com.tibco.cep.studio.rms.model.ArtifactReviewTask;
import com.tibco.cep.studio.rms.model.ArtifactReviewTaskSummary;
import com.tibco.cep.studio.rms.model.CommittedArtifactDetails;

public class WorklistContentProvider implements ITreeContentProvider {

	private static Object[] EMPTY_ARRAY = new Object[0];
	
	private TreeViewer treeViewer;
	
	public WorklistContentProvider(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		try {
			if (parentElement instanceof ArtifactReviewTaskSummary) {
				ArtifactReviewTaskSummary taskSummary = (ArtifactReviewTaskSummary)parentElement;
				return taskSummary.getTaskList().toArray();
			}
			if (parentElement instanceof ArtifactReviewTask) {
				ArtifactReviewTask request = (ArtifactReviewTask)parentElement;
				if (request.getAllCommittedArtifacts() != null) {
					return request.getAllCommittedArtifacts().toArray();
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EMPTY_ARRAY;
	}

	@Override
	public Object getParent(Object element) {
		try {
			if (element instanceof ArtifactReviewTask) {
				return ((ArtifactReviewTask)element).getParent();
			}
			if (element instanceof CommittedArtifactDetails) {
				return ((CommittedArtifactDetails)element).getParent();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof ArtifactReviewTask) {
			return true;
		}
		return getChildren(element).length > 0;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	public TreeViewer getTreeViewer() {
		return treeViewer;
	}
}
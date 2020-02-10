package com.tibco.cep.studio.ui.search;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class StudioElementSearchResult extends AbstractTextSearchResult implements IEditorMatchAdapter, IFileMatchAdapter {
	
	private ISearchQuery fQuery;

	public StudioElementSearchResult(ISearchQuery query) {
		super();
		this.fQuery = query;
	}

	public String getLabel() {
	    if (fQuery.getLabel() != null && getMatchCount() > 0) {
	        return fQuery.getLabel() + " -- " + getMatchCount() + " references in workspace";
	    }
	    return "<no references found>";
	}

	@Override
	public IEditorMatchAdapter getEditorMatchAdapter() {
		return this;
	}

	@Override
	public IFileMatchAdapter getFileMatchAdapter() {
		return this;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public ISearchQuery getQuery() {
		return fQuery;
	}

	public String getTooltip() {
		// TODO Auto-generated method stub
		return null;
	}

	public Match[] computeContainedMatches(AbstractTextSearchResult result,
			IEditorPart editor) {
		IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			List<Match> containedMatches = new ArrayList<Match>();
			IFile file = ((FileEditorInput)editorInput).getFile();
			Match[] matches = result.getMatches(file);
			for (Match match : matches) {
				if (file.equals(match.getElement())) {
					containedMatches.add(match);
				}
			}
			Match[] filteredMatches = new Match[containedMatches.size()];
			containedMatches.toArray(filteredMatches);
			return filteredMatches;
		}
		return new Match[0];
	}

	public boolean isShownInEditor(Match match, IEditorPart editor) {
		IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof FileEditorInput) {
			IFile file = ((FileEditorInput)editorInput).getFile();
			return file.equals(match.getElement());
		}
		return false;
	}

	public Match[] computeContainedMatches(AbstractTextSearchResult result,
			IFile file) {
        return new Match[0];
	}

	public IFile getFile(Object element) {
	    if (element instanceof IFile)
	        return (IFile)element;
	    return null;
	}

}

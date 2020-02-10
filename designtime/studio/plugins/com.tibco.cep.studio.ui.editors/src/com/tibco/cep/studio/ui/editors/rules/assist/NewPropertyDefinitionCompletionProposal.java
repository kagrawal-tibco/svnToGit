package com.tibco.cep.studio.ui.editors.rules.assist;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;

public class NewPropertyDefinitionCompletionProposal implements ICompletionProposal {

	private IProject fProject;
	private String fPropertyName;
	private Entity fOwnerEntity;

	public NewPropertyDefinitionCompletionProposal(IProject project, String propertyName, Entity owner) {
		this.fProject= project;
		this.fPropertyName = propertyName;
		this.fOwnerEntity = owner;
	}

//	pre-populate  rule function wizard with project and RF name
//	
//	re-reconcile rule editor upon completion
//	
//	see if form editor is easy to get working

	@Override
	public void apply(IDocument document) {
		NewPropertyDefinitionDialog dialog = new NewPropertyDefinitionDialog(new Shell(), fOwnerEntity, fPropertyName);
		if (dialog.open() == Dialog.OK) {
			PropertyDefinition propertyDefinition = dialog.getPropertyDefinition();
			if (fOwnerEntity instanceof Concept) {
				if (((Concept) fOwnerEntity).getPropertyDefinition(propertyDefinition.getName()) == null) {
					((Concept) fOwnerEntity).getProperties().add(propertyDefinition);
				} else {
					MessageDialog.openError(new Shell(), "Error", "Could not create property definition.  Property '"+fPropertyName+"' already exists for entity "+fOwnerEntity.getName());
				}
			} else if (fOwnerEntity instanceof Event) {
				if (((Event) fOwnerEntity).getPropertyDefinition(propertyDefinition.getName()) == null) {
					((Event) fOwnerEntity).getProperties().add(propertyDefinition);
				} else {
					MessageDialog.openError(new Shell(), "Error", "Could not create property definition.  Property '"+fPropertyName+"' already exists for entity "+fOwnerEntity.getName());
				}
			}
			try {
				ModelUtils.saveEObject(fOwnerEntity);
			} catch (IOException e) {
				e.printStackTrace();
			}
			IFile file = IndexUtils.getFile(fProject, fOwnerEntity);
			CommonUtil.refresh(file, 0, false);
			try {
				// create a dummy replace so that the editor is re-reconciled to fix the displayed error
				document.replace(0, 0, "");
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getAdditionalProposalInfo() {
		return "This will open a dialog to help create a new property definition in the entity '"+fOwnerEntity.getName()+"'...";
	}

	@Override
	public IContextInformation getContextInformation() {
		return new ContextInformation("Create a new Property Definition", "In owner entity "+fOwnerEntity.getName());
	}

	@Override
	public String getDisplayString() {
		return "Create a property definition in the entity '"+fOwnerEntity.getName()+"'...";
	}

	@Override
	public Image getImage() {
		return EditorsUIPlugin.getDefault().getImage("icons/property_entity.png");
	}

	@Override
	public Point getSelection(IDocument document) {
		return null;
	}

}

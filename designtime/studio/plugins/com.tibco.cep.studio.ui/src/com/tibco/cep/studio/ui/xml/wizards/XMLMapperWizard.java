package com.tibco.cep.studio.ui.xml.wizards;

import javax.swing.SwingUtilities;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.text.edits.MalformedTreeException;

import com.tibco.cep.studio.core.util.ASTNodeChangeAnalyzer;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;
import com.tibco.cep.studio.ui.xml.utils.Messages;


/**
 * 
 * @author sasahoo
 *
 */
public class XMLMapperWizard extends Wizard {
	
	private XMLMapperWizardPage fMapperPage;
	private MapperInvocationContext fContext;
	
	public XMLMapperWizard(MapperInvocationContext context) {
		setWindowTitle(Messages.getString("mapper.window.title"));
		this.fContext = context;
	}
    
	public MapperInvocationContext getContext() {
		return fContext;
	}

	public XMLMapperWizardPage getMapperPage() {
		return fMapperPage;
	}

	@Override
	public void addPages() {
		fMapperPage = new XMLMapperWizardPage(fContext);
		addPage(fMapperPage);
	}

	@Override
	public boolean performCancel() {
			return true;
	}

	@Override
	public boolean performFinish() {
		if (!MapperUtils.isSWTMapper(fContext.getProjectName())) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					doFinish(true);
				}
			});
		} else {
			doFinish(false);
		}

		return true;
	}
	
	private void doFinish(boolean requiresSWTThread) {
		try {
			String newXslt = fMapperPage.getNewXSLT();
			// take the new xslt string, and use the refactoring framework
			// to replace the existing text
			final ASTNodeChangeAnalyzer analyzer = new ASTNodeChangeAnalyzer(fContext.getDocument());
			analyzer.analyzeASTNodeReplace(fContext.getNode(), "\""+newXslt+"\"");
			if (requiresSWTThread) {
				Display.getDefault().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						try {
							analyzer.getCurrentEdit().apply(fContext.getDocument());
						} catch (MalformedTreeException e) {
							e.printStackTrace();
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
					}
				});
			} else {
				analyzer.getCurrentEdit().apply(fContext.getDocument());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean canFinish() {
		if (!getContext().isMapperEditable()) {
			return false;
		}
		return super.canFinish();
	}
	
	
}

package com.tibco.cep.studio.ui.xml.actions;

import javax.swing.SwingUtilities;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.tibco.cep.mapper.xml.xdata.bind.TemplateEditorConfiguration;
import com.tibco.cep.studio.core.repo.EMFTnsCache;
import com.tibco.cep.studio.core.util.mapper.MapperInvocationContext;
import com.tibco.cep.studio.core.util.mapper.XMLReadException;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditor;
import com.tibco.cep.studio.mapper.ui.data.bind.BindingEditorPanel;
import com.tibco.cep.studio.ui.xml.utils.MapperUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class XMLMapperListeners {
	
	public static void updateMapperPanel(MapperInvocationContext context, EMFTnsCache cache, final BindingEditorPanel bePanel, final BindingEditor ed) throws Exception {
		TemplateEditorConfiguration tmpTec = null;
		try {
			tmpTec = MapperUtils.createTemplateEditorConfiguration(
					context, cache);
		} catch (XMLReadException e) {
			boolean reset = MessageDialog.openQuestion(new Shell(), "XML Read Error", getErrorMessage(e));
			if (reset) {
				context.setXslt("xslt://");
				tmpTec = MapperUtils.createTemplateEditorConfiguration(
						context, cache);
			} else {
				throw new InterruptedException(e.getMessage());
			}
		}
		final TemplateEditorConfiguration tec = tmpTec;
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				ed.setTemplateEditorConfiguration(tec);
				bePanel.setConfiguration(tec, bePanel.getEditorState());
				bePanel.resetUndoManager();
			}
		});

	}

	private static String getErrorMessage(XMLReadException e) {
		StringBuffer buf = new StringBuffer();
		buf.append("An error was encountered while reading the xslt string:  ");
		buf.append(e.getMessage());
		buf.append("\n\nIf you continue, the xslt string will be reset and you will lose any existing mappings.  Would you like to continue?");
		
		return buf.toString();
	}

}

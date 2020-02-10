package com.tibco.cep.studio.ui.doc;

import org.eclipse.epf.richtext.IRichTextEditor;
import org.eclipse.epf.richtext.IRichTextToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * 
 * @author sasahoo
 *
 */
public class RichTextEditorToolkit {
   
    /**
     * @param parent
     * @param site
     * @param applyAction
     * @param resetAction
     * @return
     */
    public static ExtendedRichTextEditor createEditor(Composite parent, 
    		                                          IEditorSite site, 
			                                          boolean showAddTable) {
    	
    	GridData gd = new GridData(GridData.FILL_BOTH);
    	gd.widthHint = 500;
    	gd.heightHint = 200;
    	
    	parent.setLayoutData(gd/*new GridData(SWT.FILL, SWT.FILL, false, false)*/);
    	
	    FormToolkit toolkit = new FormToolkit(parent.getDisplay());
	    final ExtendedRichTextEditor editor = (ExtendedRichTextEditor)createRichTextEditor(toolkit, parent, "", SWT.None, site, showAddTable);
	    gd = new GridData(GridData.FILL_BOTH);
    	gd.widthHint = 500;
    	gd.heightHint = 200;
	    editor.getControl().setLayoutData(gd);
	    editor.addKeyListener(new KeyAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 */
			public void keyPressed(KeyEvent e) {
				editor.getTextControl().notifyModifyListeners();
			}
		});
	    return editor;
    }
    
    
    /**
     * @param toolkit
     * @param parent
     * @param text
     * @param style
     * @param editorSite
     * @param applyAction
     * @param resetAction
     * @return
     */
    private static IRichTextEditor createRichTextEditor(FormToolkit toolkit,
			                                           Composite parent, 
			                                           String text, 
			                                           int style, 
			                                           IEditorSite editorSite, 
			                                           boolean showAddTable) {
		IRichTextEditor richTextEditor = new ExtendedRichTextEditor(parent, toolkit.getBorderStyle() | style | toolkit.getOrientation(), editorSite, showAddTable) {
			/* (non-Javadoc)
			 * @see com.tibco.cep.studio.ui.doc.ExtendedRichTextEditor#fillToolBar(org.eclipse.epf.richtext.IRichTextToolBar)
			 */
			@Override
			public void fillToolBar(IRichTextToolBar toolBar) {
				super.fillToolBar(toolBar);
			}
			
			@Override
			public void enableToolBar(boolean enabled) {
				super.enableToolBar(enabled);
			}
			
			@Override
			public void additionalToolBarEnable() {
				super.additionalToolBarEnable();
			}
		};
		richTextEditor.getControl().setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TEXT_BORDER);
		if (text != null) {
			richTextEditor.setInitialText(text);
		}
		return richTextEditor;
	}
}
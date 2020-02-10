package com.tibco.cep.studio.ui.dialog;

import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.wizards.AbstractResourceElementSelector;

/**
 * 
 * @author sasahoo
 *
 */
public class AbstractFilteredResourceElementSelector extends AbstractResourceElementSelector {
    
    protected String patternString;
    protected Text patternText;

    /**
     * Common object picker
     * 
     * @param parent
     */
    public AbstractFilteredResourceElementSelector(Shell parent) {
        super(parent);
    }


	/**
	 * Create a new header which is labelled by headerLabel.
	 * 
	 * @param parent
	 * @return Label the label of the header
	 */
	private Label createHeader(Composite parent) {
		Composite header = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		header.setLayout(layout);
		Label headerLabel = new Label(header, SWT.NONE);
		headerLabel.setText(Messages.getString("StudioFilteredItemsSelectionDialog_patternLabel"));
		headerLabel.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_MNEMONIC && e.doit) {
					e.detail = SWT.TRAVERSE_NONE;
					patternText.setFocus();
				}
			}
		});

		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		headerLabel.setLayoutData(gd);
		header.setLayoutData(gd);
		return headerLabel;
	}
    
     
    protected void createTreeFilterContainer(Composite composite) {
    	final Label headerLabel = createHeader(composite);
		patternText = new Text(composite, SWT.SINGLE | SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
		patternText.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				e.result = LegacyActionTools.removeMnemonics(headerLabel
						.getText());
			}
		});
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		patternText.setLayoutData(gd);

		patternText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				patternString = patternText.getText();
				applyFilter();
			}
		});

		patternText.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					
				}
			}
		});
    }
    
    protected void applyFilter() {
    	
	}
    

    public String getPattern() {
		return patternString;
	}

	public void setPattern(String patternString) {
		this.patternString = patternString;
	}
}
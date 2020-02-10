package com.tibco.cep.studio.ui.palette.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

/**
 * For all Palatte Utilities
 * @author sasahoo
 *
 */
public class PaletteUtils {

	/**
	 * @param control
	 */
	public static void revalidateLayout (Control control) { 
		Control c = control; 
		do { 
			if (c instanceof ExpandBar) { 
				ExpandBar expandBar = (ExpandBar) c; 
				for (ExpandItem expandItem : expandBar.getItems()) { 
					expandItem 
					.setHeight(expandItem.getControl().computeSize(expandBar.getSize().x, SWT.DEFAULT, true).y); 
				} 
			} 
			c = c.getParent(); 

		} while (c != null && c.getParent() != null && !(c instanceof ScrolledComposite)); 

		if (c instanceof ScrolledComposite) { 
			ScrolledComposite scrolledComposite = (ScrolledComposite) c; 
			if (scrolledComposite.getExpandHorizontal() || scrolledComposite.getExpandVertical()) { 
				scrolledComposite 
				.setMinSize(scrolledComposite.getContent().computeSize(SWT.DEFAULT, SWT.DEFAULT, true)); 
			} else { 
				scrolledComposite.getContent().pack(true); 
			} 
		} 
		if (c instanceof Composite) { 
			Composite composite = (Composite) c; 
			composite.layout(true, true); 
		} 
	} 
	
}

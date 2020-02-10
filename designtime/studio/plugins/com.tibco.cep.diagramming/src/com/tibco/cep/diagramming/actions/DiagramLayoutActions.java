package com.tibco.cep.diagramming.actions;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.diagramming.utils.ICommandIds;
import com.tibco.cep.diagramming.utils.Messages;

/**
 * 
 * @author ggrigore, ssailapp
 *
 */
public class DiagramLayoutActions extends Action implements IMenuCreator  {

	public static final int DEFAULT_STYLE = 0;
	public static final int CIRCULAR_STYLE = 1;
	public static final int SYMMETRIC_STYLE = 2;
	public static final int ORTHOGONAL_STYLE = 3;
	public static final int HIERARCHICAL_STYLE = 4;
	public static final int HIERARCHICAL_POLYLINE_STYLE = 5;
	public static final int DECISION_STYLE = 6;
	public int layout_Selected=0;
	
	
	//ActionContributionItem citem ;
	private IWorkbenchPage page;
	Menu menu;
	
	@SuppressWarnings("static-access")
	public DiagramLayoutActions(IWorkbenchPage page) {
		super("", SWT.DROP_DOWN);
		this.page = page;
		setId(ICommandIds.DIAGRAM_LAYOUT);
		setToolTipText(Messages.getString("layout.default"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/globalLayout.png"));
		setActionDefinitionId(ICommandIds.DIAGRAM_LAYOUT);
		setMenuCreator(this);
	}

	public void run() {

			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					DiagramUtils.layoutAction(page);
					
				}
			});
		
		
	}

	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);
		populateMenu(menu);
		return menu;

	}

	public Menu getMenu(Menu parent) {
		return null;
	}

	private void populateMenu(Menu menu) {
		addLayoutActionMenuItem("layout.default", DEFAULT_STYLE, "layout.default.tooltip", "icons/globalLayout.png");
		addLayoutActionMenuItem("layout.circular", CIRCULAR_STYLE, "layout.circular.tooltip", "icons/circulardeep.png");
		addLayoutActionMenuItem("layout.orthogonal", ORTHOGONAL_STYLE, "layout.orthogonal.tooltip", "icons/orthogonaldeep.png");
		addLayoutActionMenuItem("layout.symmetric", SYMMETRIC_STYLE, "layout.symmetric.tooltip", "icons/symmetricdeep.png");
		addLayoutActionMenuItem("layout.hierarchical", HIERARCHICAL_STYLE, "layout.hierarchical.tooltip", "icons/hierarchicaldeep.png");
		addLayoutActionMenuItem("layout.hierarchical.poly", HIERARCHICAL_POLYLINE_STYLE, "layout.hierarchical.polytooltip", "icons/hierarchicaldeep.png");
		//TODO  
		//addLayoutActionMenuItem("layout.decision", DECISION_STYLE, "layout.decision.tooltip", "icons/globalLayout.png");		
	}
	
	@SuppressWarnings("static-access")
	private void addLayoutActionMenuItem(String actionName, final int style, String toolTip, String imagePath) {
		Action layoutAction = new Action(Messages.getString(actionName), Action.AS_CHECK_BOX) {
			public void run() {
				DiagramUtils.layoutAction(page, style);
				layout_Selected = style;
			}
		};
		if (layout_Selected == style) {
			layoutAction.setChecked(true);
		} else {
			layoutAction.setChecked(false);
		}
		layoutAction.setToolTipText(Messages.getString(toolTip));
		layoutAction.setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor(imagePath));
		ActionContributionItem item = new ActionContributionItem(layoutAction);
		item.fill(menu, -1);
	}
}


package com.tibco.cep.bpmn.ui.menu;

import static com.tibco.cep.bpmn.ui.menu.BPMNMenuConstants.PROCESS_BREAKPOINT_PROPERTIES_PATTERN;

import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JPopupMenu;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.diagramming.menu.Pattern;
import com.tibco.cep.diagramming.menu.popup.AbstractDiagramMenuFilter;


/**
 * 
 * @author sasahoo
 *
 */
public class BPMNDiagramMenuFilter extends AbstractDiagramMenuFilter {
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.menu.popup.AbstractDiagramMenuFilter#applyFilter(com.tomsawyer.graphicaldrawing.TSEObject)
	 */
	@Override
	public void applyPatterns(Object object, List<String> matchPatterns) {
		if(object instanceof ProcessMenuBreakPoint) {
			 matchPatterns.add(PROCESS_BREAKPOINT_PROPERTIES_PATTERN);
		}
		super.applyPatterns(object, matchPatterns);
	}

	@Override
	public Map<AbstractButton, EList<Pattern>> getMenuMap() {
		return map;
	}

	@Override
	public void setMenuMap(Map<AbstractButton, EList<Pattern>> map) {
		this.map = map;
	}
	

	@Override
	public JPopupMenu getPopupMenu() {
		return menu;
	}

	@Override
	public void setPopupMenu(JPopupMenu menu) {
		this.menu = menu;
	}
}
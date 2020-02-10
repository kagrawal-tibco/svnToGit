package com.tibco.cep.diagramming.menu.popup;

import java.awt.Component;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.MenuElement;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.diagramming.menu.Pattern;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractDiagramMenuFilter {

	protected JPopupMenu menu;
	
	public abstract JPopupMenu getPopupMenu();
	
	public abstract void setPopupMenu(JPopupMenu menu);
	
	protected Map<AbstractButton, EList<Pattern>> map;
	
	public abstract Map<AbstractButton, EList<Pattern>> getMenuMap();
	
	public abstract void setMenuMap(Map<AbstractButton, EList<Pattern>> map);
	
	/**
	 * @param object
	 * @param matchPatterns
	 */
	public void applyPatterns(Object object, List<String> matchPatterns) {
		if (object instanceof TSENode) {
			matchPatterns.add(TSENode.class.getName());
		} else if (object instanceof TSEEdge) {
			matchPatterns.add(TSEEdge.class.getName());
		} else if (object instanceof TSEGraph) {
			matchPatterns.add(TSEGraph.class.getName());
		}  else if (object instanceof TSEConnector) {
			matchPatterns.add(TSEConnector.class.getName());
		} 
		matchPatterns(menu, matchPatterns);
		adjustSeparators(menu);
		menu.repaint();
		menu.revalidate();
	}
	
	
	/**
	 * @param menu
	 * @param matchPatterns
	 */
	protected void matchPatterns(MenuElement menu,  List<String> matchPatterns) {
		if (menu instanceof JPopupMenu || menu instanceof JMenu) {
			for (MenuElement element : menu.getSubElements()) {
				EList<Pattern> patternsList = getMenuMap().get(element);
				if (patternsList == null) {
					continue;
				}
				Pattern[] patterns = new Pattern[patternsList.size()];
				patternsList.toArray(patterns);
				boolean visible = false;
				if (element instanceof JMenuItem) {
					((JMenuItem)element).setVisible(false);
				}
				if (element instanceof JMenu) {
					((JMenu)element).setVisible(false);
				}
				for (Pattern pattern: patterns) {
					if (pattern.getPattern().equals("*")) {
						if (element instanceof JMenuItem) {
							((JMenuItem)element).setVisible(true);
						}
						if (element instanceof JMenu) {
							((JMenu)element).setVisible(true);
						}
						break;
					} else if (matchPatterns.contains(pattern.getPattern())) {
						if (element instanceof JMenuItem) {
							((JMenuItem)element).setVisible(true);
						}
						if (element instanceof JMenu) {
							visible = true;
							((JMenu)element).setVisible(true);
						}
						break;
					}
				}  
				
//				List<String> elementPatterns = new ArrayList<String>();
//				for (Pattern p: patterns) {
//					elementPatterns.add(p.getPattern());
//				}
//				Collections.sort(elementPatterns);
//				Collections.sort(matchPatterns);
//				
//				if (!all) {
//					if (elementPatterns.size() == matchPatterns.size()) {
//						if (elementPatterns.equals(matchPatterns)) {
//							if (element instanceof JMenuItem) {
//								((JMenuItem)element).setVisible(true);
//							}
//							if (element instanceof JMenu) {
//								visible = true;
//								((JMenu)element).setVisible(true);
//							}
//						} else {
//							if (element instanceof JMenuItem) {
//								((JMenuItem)element).setVisible(false);
//							}
//							if (element instanceof JMenu) {
//								visible = true;
//								((JMenu)element).setVisible(false);
//							}
//						}
//					}
//				}
				if (element instanceof JMenu && visible) {
					matchPatterns(element, matchPatterns);
				}
			}
		}
	}
	
	/**
	 * @param component
	 */
	protected void adjustSeparators(Component component) {
		if (component instanceof JPopupMenu || component instanceof JMenu) {
			JComponent jComponent = (JComponent)component;
			Component[] elements = jComponent.getComponents();
			for (int i = 0; i < elements.length; i++) {
				Component element = elements[i];
				if (element instanceof JSeparator) {
					JSeparator separator = (JSeparator)element;
					separator.setVisible(true);
				}
				Component preelement = null;
				Component nextelement = null;
				if (i-1 >=0 ) {
					preelement = elements[i-1];
				}
				if (i+1 < elements.length) {
					nextelement = elements[i+1];
				}
				if (element instanceof JSeparator) {
					JSeparator separator = (JSeparator)element;
					if (!preelement.isVisible()) {
						separator.setVisible(false);
					} 
					if (!preelement.isVisible() && nextelement.isVisible()) {
						separator.setVisible(true);
					}
					if (!nextelement.isVisible()) {
						separator.setVisible(false);
					}
					//Calculate all previous elements' visibility
					int currIndex = i;
					boolean flag = false;
					for (int k = 0; k < currIndex; k++) {
						if (elements[k] instanceof JMenuItem || elements[k] instanceof JMenuItem) {
							if (elements[k].isVisible()) {
								flag = true;
								break;
							}
						}
					}
					if (!flag) {
						separator.setVisible(false);
					}
				}
				if (element instanceof JMenu) {
					adjustSeparators(element);
				}
			}  
		}
	}
}

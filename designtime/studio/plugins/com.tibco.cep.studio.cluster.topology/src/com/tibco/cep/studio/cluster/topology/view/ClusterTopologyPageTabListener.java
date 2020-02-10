package com.tibco.cep.studio.cluster.topology.view;

import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;

/**
 * 
 * @author sasahoo
 *
 */
public class ClusterTopologyPageTabListener extends  AbstractClusterTopologyViewPageListener {

	private Composite pconfigComposite;
	private Composite pinputComposite;
	private Composite poutputComposite;
	
	private CLabel pconfigLabel;
	private CLabel pinputLabel;
	private CLabel poutpputLabel;
	
	private ScrolledPageBook page;

	/**
	 * 
	 * @param tab
	 * @param spage
	 */
	public ClusterTopologyPageTabListener(ClusterTopologyPageTabComponent tab,ScrolledPageBook spage){
		pconfigComposite = tab.getPconfigComposite();
		pinputComposite = tab.getPinputComposite();
		poutputComposite = tab.getPoutputComposite();
		pconfigLabel = tab.getPconfigLabel();
		pinputLabel = tab.getPinputLabel();
		poutpputLabel = tab.getPoutpputLabel();
		page = spage;
		addListeners();
	}
	
	public void mouseDown(MouseEvent e) {

		if(e.getSource() == pconfigLabel){
			pconfigLabel.setBackground(COLOR_WHITE);
			pinputLabel.setBackground(COLOR_WIDGET_BACKGROUND);
			poutpputLabel.setBackground(COLOR_WIDGET_BACKGROUND);
			pconfigComposite.setBounds(X, TOPh, Sy, HIEGHT);
			pinputComposite.setBounds(X, TOPh + HIEGHT, WIDTH, HIEGHT);
			poutputComposite.setBounds(X, TOPh + (2 * HIEGHT), WIDTH, HIEGHT);
			page.showPage(DEPLOYMENT_CONFIGURATION_PAGE);
		}
		if(e.getSource() == pinputLabel){
			pinputLabel.setBackground(COLOR_WHITE);
			pconfigLabel.setBackground(COLOR_WIDGET_BACKGROUND);
			poutpputLabel.setBackground(COLOR_WIDGET_BACKGROUND);
			pconfigComposite.setBounds(X, TOPh, WIDTH, HIEGHT);
			pinputComposite.setBounds(X, TOPh + HIEGHT, Sy, HIEGHT);
			poutputComposite.setBounds(X, TOPh + (2 * HIEGHT), WIDTH, HIEGHT);
			page.showPage(DEPLOYMENT_INPUT_PAGE);
		}
		if(e.getSource() == poutpputLabel){
			poutpputLabel.setBackground(COLOR_WHITE);
			pconfigLabel.setBackground(COLOR_WIDGET_BACKGROUND);
			pinputLabel.setBackground(COLOR_WIDGET_BACKGROUND);
			pconfigComposite.setBounds(X, TOPh, WIDTH, HIEGHT);
			pinputComposite.setBounds(X, TOPh + HIEGHT, WIDTH, HIEGHT);
			poutputComposite.setBounds(X, TOPh + (2 * HIEGHT), Sy, HIEGHT);
			page.showPage(DEPLOYMENT_OUTPUT_PAGE);
		}
	}

	public void mouseEnter(MouseEvent e) {

		if(((CLabel)e.getSource()).getBackground().equals(COLOR_WHITE))
			return;
		
		((CLabel)e.getSource()).setBackground(COLOR_INFO_BACKGROUND);
	}

	public void mouseExit(MouseEvent e) {
		if(((CLabel)e.getSource()).getBackground().equals(COLOR_WHITE))
			return;
		
		((CLabel)e.getSource()).setBackground(COLOR_WIDGET_BACKGROUND);
	}
	
	private void addListeners(){
		pconfigLabel.addMouseListener(this);
		pconfigLabel.addMouseTrackListener(this);
		pinputLabel.addMouseListener(this);
		pinputLabel.addMouseTrackListener(this);
		poutpputLabel.addMouseListener(this);
		poutpputLabel.addMouseTrackListener(this);
	}
}

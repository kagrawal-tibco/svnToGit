package com.tibco.cep.studio.cluster.topology.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import com.tibco.cep.studio.cluster.topology.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class ClusterTopologyPageTabComponent implements IClusterTopologyViewConstants{
	
	public Composite pconfigComposite;
	public Composite pinputComposite;
	public Composite poutputComposite;
	public CLabel pconfigLabel;
	public CLabel pinputLabel;
	public CLabel poutpputLabel;
	public Composite etopComposite;
	public Composite ebottomcomposite;
	public Composite parent;
	
	public ClusterTopologyPageTabComponent(Composite parent){
		this.parent = parent;
	}
	
	public void createDeploymentPageButtons() {
	
		GridData data = new GridData(GridData.FILL_VERTICAL);
		data.widthHint = 100;
		data.heightHint = 50;
		parent.setBackground(COLOR_WIDGET_BACKGROUND);
		parent.setLayoutData(data);

		etopComposite = new Composite(parent, SWT.BORDER);
		ebottomcomposite = new Composite(parent, SWT.BORDER);

		pconfigComposite = new Composite(parent, SWT.BORDER);
		pinputComposite = new Composite(parent, SWT.BORDER);
		poutputComposite = new Composite(parent, SWT.BORDER);
		
		pconfigLabel = new CLabel(pconfigComposite, SWT.LEFT);
		pconfigLabel.setText(Messages.getString("cluster.view.configuration.tab.title"));
		pinputLabel = new CLabel(pinputComposite, SWT.LEFT);
		pinputLabel.setText(Messages.getString("cluster.view.configuration.tab.input"));
		poutpputLabel = new CLabel(poutputComposite, SWT.LEFT);
		poutpputLabel.setText(Messages.getString("cluster.view.configuration.tab.output"));
		
		initialize();
	
	}
	
	private void setLayout(){
		pconfigComposite.setLayout(new FillLayout());
		pinputComposite.setLayout(new FillLayout());
		poutputComposite.setLayout(new FillLayout());
	}
	
	private void setBackground(){
		etopComposite.setBackground(COLOR_WIDGET_BACKGROUND);
		ebottomcomposite.setBackground(COLOR_WIDGET_BACKGROUND);
		poutputComposite.setBackground(COLOR_WIDGET_BACKGROUND);
		pconfigComposite.setBackground(COLOR_WIDGET_BACKGROUND);
		pinputComposite.setBackground(COLOR_WIDGET_BACKGROUND);
		pconfigLabel.setBackground(COLOR_WHITE);
		pinputLabel.setBackground(COLOR_WIDGET_BACKGROUND);
		poutpputLabel.setBackground(COLOR_WIDGET_BACKGROUND);
	}

	private void initialBounds(){
		etopComposite.setBounds(X, Y, WIDTH, TOPh);
		pconfigComposite.setBounds(X, TOPh, Sy, HIEGHT);
		pinputComposite.setBounds(X, TOPh + HIEGHT, WIDTH, HIEGHT);
		poutputComposite.setBounds(X, TOPh + (2 * HIEGHT), WIDTH, HIEGHT);
		ebottomcomposite.setBounds(X, TOPh + (3 * HIEGHT), WIDTH, BOTTOMh);
	}
	
	private void initialize(){
		setLayout();
		setBackground();
		initialBounds();
	}
	
	public Composite getPconfigComposite() {
		return pconfigComposite;
	}

	public Composite getPinputComposite() {
		return pinputComposite;
	}

	public Composite getPoutputComposite() {
		return poutputComposite;
	}

	public CLabel getPconfigLabel() {
		return pconfigLabel;
	}

	public CLabel getPinputLabel() {
		return pinputLabel;
	}

	public CLabel getPoutpputLabel() {
		return poutpputLabel;
	}

}

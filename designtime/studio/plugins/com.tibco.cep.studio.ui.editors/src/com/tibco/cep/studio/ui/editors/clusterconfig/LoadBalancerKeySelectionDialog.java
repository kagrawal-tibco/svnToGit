package com.tibco.cep.studio.ui.editors.clusterconfig;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import com.tibco.cep.studio.core.clusterconfig.ClusterConfigModel.LoadBalancerPairConfig;
/*
@author vbarot
*/

public class LoadBalancerKeySelectionDialog extends ConfigElementSelectionDialog {

	private String selKey;
	private NodeLoadBalancerPairConfigPage pairConfigPage;

	public LoadBalancerKeySelectionDialog(Shell parent, NodeLoadBalancerPairConfigPage pairConfigPage) {
		super(parent);
		selKey = "";
		this.pairConfigPage = pairConfigPage;
	}

	public void open(ArrayList<?> functions, ArrayList<?> filterGrp) {
		initDialog("Key Selector", true);

		for (Object function: functions) {
			if (!JMSHeader.contains((String)function)) {
				TableItem item = new TableItem(table, SWT.NONE);
                item.setImage(ClusterConfigImages.getImage(ClusterConfigImages.IMG_CLUSTER_PROPERTY));
                item.setText((String) function);
                item.setData(function);
                if (isChecked((String) function))
                    item.setChecked(true);
			}
		}

		bOK.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
                StringBuilder selectedKeys = new StringBuilder();
                for (TableItem item : table.getItems()) {
                    if (item.getChecked()) {
                        selectedKeys.append(item.getText(0));
                        selectedKeys.append(",");
                    }
                }

                if (selectedKeys.length() > 0) {
                    selKey = selectedKeys.substring(0, selectedKeys.length() - 1);
                }			    
				dialog.dispose();
			}
		});

		openDialog();
	}

    private boolean isChecked(String item) {
        if (pairConfigPage.config != null) {
            String routingKeys = pairConfigPage.config.values.get(LoadBalancerPairConfig.ELEMENT_KEY);
            if (routingKeys != null && routingKeys.trim().length() != 0) {
                return routingKeys.contains(item);
            }
        }
        return false;
    }
	
	public String getSelectedKey() {
		return selKey;
	}
	
	private enum JMSHeader {
        JMSCorrelationID,
        JMSCorrelationIDAsBytes,
        JMSDeliveryMode,
        JMSDestination,
        JMSExpiration,
        JMSMessageID,
        JMSPriority,
        JMSRedelivered,
        JMSReplyTo,
        JMSTimestamp,
        JMSType;

         static boolean contains(String JmsHeader) {
            if (JmsHeader == null) {
                return false;
            }
            try {
                JMSHeader.valueOf(JmsHeader);
                return true;
            } catch (IllegalArgumentException ex) {
                return false;
            }
        }
    }
	
}

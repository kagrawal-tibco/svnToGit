package com.tibco.cep.decisionproject.adapter;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;

import com.tibco.cep.decision.table.model.dtmodel.Expression;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.ontology.Implementation;

public class ImplementationAdapter extends EContentAdapter {
//	private static final PluginLoggerImpl TRACE = LoggerRegistry.getLogger(DecisionProjectPlugin.PLUGIN_ID);
	
	private Implementation impl;
	public ImplementationAdapter(Implementation impl){
		this.impl = impl;
	}

	@Override
	public void notifyChanged(Notification notification) {
			Object notifier = notification.getNotifier();
			if (notifier instanceof MetaData){
				if (!impl.isModified()){
					impl.setModified(true);
				}
			}
			else if (notifier instanceof Expression){
				if (!impl.isModified()){
					impl.setModified(true);
				}
			}
			else if (notifier instanceof TableRuleVariable){			
				TableRuleVariable ruleVariable = (TableRuleVariable)notifier;
				if (!impl.isModified()){
					impl.setModified(true);
				}
				if (!ruleVariable.isModified()){
					ruleVariable.setModified(true);
				}
			}
			else if (notifier instanceof TableRule){
				if (!impl.isModified()){
					impl.setModified(true);
				}			
			}
			else if (notifier instanceof TableRuleSet){
				if (!impl.isModified()){
					impl.setModified(true);
				}
			}
			else if (notifier instanceof Table){
				if (!impl.isModified()){
					impl.setModified(true);
				}
			}
		
		}
	

	

}

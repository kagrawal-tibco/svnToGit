package com.jidesoft.decision;

import static com.tibco.cep.decision.table.language.DTLanguageUtil.OPERATORS;

import java.util.List;

import org.eclipse.core.runtime.preferences.InstanceScope;

import com.jidesoft.converter.ConverterContext;
import com.jidesoft.converter.MultilineStringConverter;
import com.jidesoft.converter.ObjectConverter;
import com.jidesoft.decision.cell.editors.custom.DefaultConverterContext;
import com.tibco.cep.decision.table.editors.DecisionTableDesignViewer;
import com.tibco.cep.decision.table.model.domainmodel.util.DMCache;
import com.tibco.cep.decision.table.model.domainmodel.util.DMCache.DomainInfo;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.utils.ExpressionBodyUtil;

public class RuleVariableConverter extends MultilineStringConverter implements ObjectConverter {
    
    private static boolean _graphicsAliasVisible = false;
    
    private Table tableEModel;
    
    private DecisionTableDesignViewer decisionTableDesignViewer;
   
	public RuleVariableConverter(DecisionTableDesignViewer decisionTableDesignViewer, 
    		                     Table tableEModel){
    	super();
    	
    	this.tableEModel = tableEModel;
    	
    	this.decisionTableDesignViewer = decisionTableDesignViewer;
    }
        
   
    public DecisionTableDesignViewer getDecisionTableDesignViewer() {
		return decisionTableDesignViewer;
	}


	public String toString(Object object, ConverterContext context) {
		 if (object instanceof TableRuleVariable) {
            TableRuleVariable ruleVariable = (TableRuleVariable) object;
            // get column header id for this TRV            
            String body = null;
//            if (context == null) {
//                if (body != null) {            	
//                    //return ExpressionBodyUtil.getRuleVariableConverterExpBody(body,_textAliasVisible, expression.getAlias());
//                	return ExpressionBodyUtil.getRuleVariableConverterExpBody(body,_textAliasVisible, "");
//                }
//            }
            UserObject userObject = (UserObject)context.getUserObject();
            Column column = userObject.getColumn();
            String alias = column.getName();
            if (column.isSubstitution()) {
            	
            	int idx = alias.length();
            	if (alias.contains(" ") && alias.indexOf(' ') < idx) {
        			idx = alias.indexOf(' ');
        		}
            	
            	for (String op : OPERATORS) {
            		if (alias.contains(op) && alias.indexOf(op) < idx) {
            			idx = alias.indexOf(op);
            		}
            	}
            	alias = alias.substring(0, idx);
            }
            //Expression expression = ruleVariable.getExpression();
            String expr = "";
            
            boolean showDomainDescr = InstanceScope.INSTANCE.getNode(DecisionTableUIPlugin.PLUGIN_ID).getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION, false);
            
            if(!column.isSubstitution() && showDomainDescr/*DecisionTableUIPlugin.getDefault().getPluginPreferences().getBoolean(PreferenceConstants.SHOW_DOMAIN_DESCRIPTION)*/) {
            	expr = (ruleVariable.getDisplayValue()==null || ruleVariable.getDisplayValue().equals(""))?ruleVariable.getExpr():ruleVariable.getDisplayValue();
            } else {
            	expr = (ruleVariable.getExpr()==null)?"":ruleVariable.getExpr();
            }
			boolean showDes = false;
			//Table tableEModel = DecisionTableUtil.getTableEModelFromActiveEditor();
			//Table tableEModel = DecisionTableUtil.getTableEModelFromNavigationView();
			if (tableEModel != null){
				showDes = tableEModel.isShowDescription();
			}
			if (showDes){
				String retVal = expr;
				if (retVal == null){
					// get it from domain model
					String resourcePath = column.getPropertyPath();
					DomainInfo dmInfo = DMCache.getInstance().getPropDomainInfoMap().get(resourcePath);		
					List<String> valList = null;
					List<String> desList = null;
					if (dmInfo != null){
						valList = dmInfo.getValList();
						desList = dmInfo.getDesList();			
						// if domain refers to any DB Concept property
						com.tibco.cep.decision.table.model.domainmodel.Domain domain = dmInfo.getDomain();
						String dbRef = domain.getDbRef();
						if (dbRef != null && !"".equals(dbRef.trim())){
							dmInfo = DMCache.getInstance().getPropDomainInfoMap().get(dbRef);
							valList = dmInfo.getValList();
							desList = dmInfo.getDesList();
							int index = 0;
							for (String str :valList){
								if (!valList.contains(str)){
									valList.add(str);
									String des = desList.get(index);
									desList.add(des);
								}
								index ++;
							}
				
						}
			
					}
					body = expr;
					String formattedString = null;
					if(context instanceof DefaultConverterContext){
						if(((DefaultConverterContext)context).getTableType() == TableTypes.DECISION_TABLE) {
							formattedString = ExpressionBodyUtil.getRuleVariableConverterExpBody(body, decisionTableDesignViewer.isToggleTextAliasFlag(), alias);
						} else if (((DefaultConverterContext)context).getTableType() == TableTypes.EXCEPTION_TABLE){
							formattedString =  ExpressionBodyUtil.getRuleVariableConverterExpBody(body, decisionTableDesignViewer.isExpToggleTextAliasFlag(), alias);
						} else{
							formattedString = ExpressionBodyUtil.getRuleVariableConverterExpBody(body, decisionTableDesignViewer.isToggleTextAliasFlag(), alias);
						}
					}
											
					StringBuilder sb = new StringBuilder("");
					if (formattedString != null){
						String[] splitValues = formattedString.split(ExpressionBodyUtil.DELIMITER);
						int counter = 1;
						for (String str : splitValues){
							String trmStr = str.trim();
							String des = null;
							if (valList != null && desList != null){
								int index = valList.indexOf(trmStr);
								if (index != -1){
									des = desList.get(index);
								} else {
									des = trmStr;
								}
							} else {
								des = trmStr;
							}
							
							sb.append(des);
							if (counter < splitValues.length){
								sb.append(";");
							}
							counter ++;
						}						
						retVal = sb.toString();
						return retVal;
					}
				}
			}
            
            body = expr;

            ColumnType columnType = userObject.getColumn().getColumnType();
            if (ColumnType.CUSTOM_CONDITION.equals(columnType) || 
            		               ColumnType.CUSTOM_ACTION.equals(columnType)){
            	if (body != null){
            		return body.trim();
            	}
            }    
            if (body != null) {            	
                //return ExpressionBodyUtil.getRuleVariableConverterExpBody(body,_textAliasVisible, expression.getAlias());
            	
            	if(context instanceof DefaultConverterContext){
					if(((DefaultConverterContext)context).getTableType() == TableTypes.DECISION_TABLE) {
						return ExpressionBodyUtil.getRuleVariableConverterExpBody(body, decisionTableDesignViewer.isToggleTextAliasFlag(), alias);
					} else if (((DefaultConverterContext)context).getTableType() == TableTypes.EXCEPTION_TABLE){
						return ExpressionBodyUtil.getRuleVariableConverterExpBody(body, decisionTableDesignViewer.isExpToggleTextAliasFlag(), alias);
					} else{
						return ExpressionBodyUtil.getRuleVariableConverterExpBody(body, decisionTableDesignViewer.isToggleTextAliasFlag(), alias);
					}
				}            	
            }
        }
        String value = object != null ? object.toString() : "";
        return value;
    }


    
    public static boolean isGraphicsAliasVisible() {
        return _graphicsAliasVisible;
    }

    public static void setGraphicsAliasVisible(boolean graphicsAliasVisible) {
        _graphicsAliasVisible = graphicsAliasVisible;
    }

    public boolean supportToString(Object object, ConverterContext context) {
        return true;
    }

    public Object fromString(String string, ConverterContext context) {
        return string;
    }

    public boolean supportFromString(String string, ConverterContext context) {
        return false;
    }
}

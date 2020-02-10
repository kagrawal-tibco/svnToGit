package com.tibco.cep.container.standalone.hawk.methods.wm;

import java.util.ArrayList;
import java.util.Iterator;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.be.util.NVPair;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.container.standalone.hawk.methods.util.Command;
import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.JoinTableCollectionProvider;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.runtime.session.RuleServiceProvider;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetJoinTableMethod extends AmiMethod implements Command {


    protected HawkRuleAdministrator m_hma;


    public GetJoinTableMethod(HawkRuleAdministrator hma) {
        super("getJoinTable", "Retrieves a join table from the Session(s).", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Table Id", "Id of the Table.", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        AmiParameterList returns = new AmiParameterList();
        fillInOneReturnsEntry(returns, 0, "");
        return returns;
    }//getReturns


    void fillInOneReturnsEntry(AmiParameterList values, int index, String row) {
        values.addElement(new AmiParameter("Line", "Row Number.", index));
        //values.addElement(new AmiParameter("Key", "Key in the table", 0));
        values.addElement(new AmiParameter("Row", "Objects in the Row.", row));
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        AmiParameterList values = null;
        String strTableId;
        int    tableId;

        try {
            strTableId = inParams.getString(0);
            if (strTableId == null || strTableId.trim().length() == 0) {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Table Id is empty");
            }
            tableId = Integer.valueOf(strTableId).intValue();
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        try {
        	JoinTable[] allTables = JoinTableCollectionProvider.getInstance().getJoinTableCollection().toArray();

            if ((0 <= tableId && tableId < allTables.length) &&
                (allTables[tableId] != null)) {
                    values = new AmiParameterList();
                    int i = 0;
                    for (Iterator it = allTables[tableId].iterator(); it.hasNext(); ) {
                        final Object[] rows = (Object[]) it.next();
                        fillInOneReturnsEntry(values, i, Format.objsToStr(rows));
                        i++;
                    }//for
            }//if
            else {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "JoinTable " + tableId + " does not exist");
            }

        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return values;
    }//onInvoke


    public NVPair[] invoke(RuleServiceProvider rsp, String inputParams) throws Exception {
            int tableId;
            try {
                tableId = Integer.valueOf(inputParams).intValue();
            }
            catch (Exception e) {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Please provide a valid table ID.");
            }
            JoinTable[] allTables = JoinTableCollectionProvider.getInstance().getJoinTableCollection().toArray();
            if ((0 > tableId) || (tableId >= allTables.length) || (allTables[tableId] == null)) {
                throw new AmiException(AmiErrors.AMI_REPLY_ERR, "JoinTable " + tableId + " does not exist");
            }

            final ArrayList lines = new ArrayList();

            for (Iterator it = allTables[tableId].iterator(); it.hasNext();) {
                final Object[] rows = (Object[]) it.next();
                lines.add(new NVPair("table "+tableId, Format.objsToStr(rows)));
            }//for

            return (NVPair[]) lines.toArray(new NVPair[lines.size()]);
    }


}//class


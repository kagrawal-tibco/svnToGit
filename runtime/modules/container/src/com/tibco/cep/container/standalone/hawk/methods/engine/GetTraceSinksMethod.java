package com.tibco.cep.container.standalone.hawk.methods.engine;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.cep_commonVersion;
import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class GetTraceSinksMethod extends AmiMethod {

    protected HawkRuleAdministrator m_hma;


    public GetTraceSinksMethod(HawkRuleAdministrator hma) {
        super("getTraceSinks", "Gets information about trace sinks.", AmiConstants.METHOD_TYPE_INFO, "Line");
        m_hma = hma;
    }


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("Role Name", "Name of a Role (optional)", ""));
        args.addElement(new AmiParameter("Sink Name", "Name of a Sink (optional)", ""));
        return args;
    }


    public AmiParameterList getReturns() {
        AmiParameterList values = new AmiParameterList();
        values.addElement(new AmiParameter("Line", "Line Number", 0));
        values.addElement(new AmiParameter("Instance ID", "Instance ID of the application", ""));
        values.addElement(new AmiParameter("Application Name", "Name of the application", cep_commonVersion.getComponent()));
        values.addElement(new AmiParameter("Sink Name", "Sink Name", ""));
        values.addElement(new AmiParameter("Sink Type", "Sink Type (for example fileSink, rvSink)", ""));
        values.addElement(new AmiParameter("Description", "Sink Description (for example filename=file)", ""));
        values.addElement(new AmiParameter("Role", "Sink Role (for example error, warn, debug)", ""));
        return values;
    }//getReturns


    /**
     * Fills in either the sample values (if wf is null)
     * or the actual values (from wf, if wf != null)
     * by adding AmiParameters to the given list.
     */
    void fillInReturnsEntries(String roleName, String sinkName, AmiParameterList values) {
//        final BETrace trace = (BETrace) m_hma.getServiceProvider().getTrace(); // todo
//        final String instanceID = m_hma.getServiceProvider().getName();
//
//        boolean showRoles = (null == roleName) || "".equals(roleName);
//        final List rolesNamesList = new ArrayList();
//        for (Iterator it = trace.getRoles(); it.hasNext(); ) {
//            String name = (String) it.next();
//            if (name.endsWith("Role")) {
//                name = name.substring(0, name.length()-4);
//            }//if
//            rolesNamesList.add(name);
//        }//for
//        final String[] roleNames = (String[]) rolesNamesList.toArray(new String[0]);
//
//        final String appName = cep_containerVersion.getComponent();
//        int row = 0;
//        boolean showSinks = (null == sinkName) || "".equals(sinkName);
//        for (Iterator it = trace.getSinks(); it.hasNext();) {
//            final MSink sink = (MSink) it.next();
//            final String currentSinkName = sink.getName();
//            if (showSinks || sinkName.equals(currentSinkName)) {
//                final String status = "";
//                for (int i = 0, max = roleNames.length; i<max; i++) {
//                    final String role = roleNames[i];
//                    if (showRoles || roleName.equals(role)) {
//                        values.addElement(new AmiParameter("Line", "Line Number", row));
//                        values.addElement(new AmiParameter("Instance ID", "Instance ID of the application", instanceID));
//                        values.addElement(new AmiParameter("Application Name", "Name of the application", appName));
//                        values.addElement(new AmiParameter("Sink Name", "Sink Name", currentSinkName));
//                        String sinkType;
//                        if(sink instanceof MFileSink) {
//                            sinkType = "file";
//                        }
//                        else if (sink instanceof MHawkSink) {
//                            sinkType = "hawk";
//                        }
//                        else if (sink instanceof MRvSink) {
//                            sinkType = "rv";
//                        }
//                        else if (sink instanceof MJmsSink) {
//                            sinkType = "jms";
//                        }
//                        else if (sink instanceof MStdoutSink) {
//                            sinkType = "stdout";
//                        }
//                        else if (sink instanceof MStderrSink) {
//                            sinkType = "stderr";
//                        }
//                        else if (sink instanceof TibRvSink) {
//                            sinkType = "tibrv";
//                        }
//                        else if (sink instanceof MStreamSink) {
//                            sinkType = "stream";
//                        }
//                        else {
//                            sinkType = sink.getClass().getName().toLowerCase();
//                            if(sinkType.endsWith("sink")) {
//                                sinkType = sinkType.substring(0, sinkType.lastIndexOf("sink")-1);
//                            }
//                        }
//                        values.addElement(new AmiParameter("Sink Type", "Sink Type (for example fileSink, rvSink)", sinkType));
//                        values.addElement(new AmiParameter("Description", "Sink Description (for example filename=file)", status));
//                        values.addElement(new AmiParameter("Role", "Sink Role (error, warn, debug)", role));
//                        row++;
//                    }//if
//                }//for
//            }//if
//        }//for
    }//fillInOneReturnsEntry


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        AmiParameterList values = null;
        try {
            final String roleName = inParams.getString(0);
            final String sinkName = inParams.getString(1);
            values = new AmiParameterList();
            fillInReturnsEntries(roleName, sinkName, values);
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return values;
    }//onInvoke


}//class



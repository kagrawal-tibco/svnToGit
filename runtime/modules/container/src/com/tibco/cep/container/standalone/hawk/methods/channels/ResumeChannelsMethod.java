package com.tibco.cep.container.standalone.hawk.methods.channels;


import java.util.Iterator;

import COM.TIBCO.hawk.ami.AmiConstants;
import COM.TIBCO.hawk.ami.AmiErrors;
import COM.TIBCO.hawk.ami.AmiException;
import COM.TIBCO.hawk.ami.AmiMethod;
import COM.TIBCO.hawk.ami.AmiParameter;
import COM.TIBCO.hawk.ami.AmiParameterList;

import com.tibco.cep.container.standalone.hawk.HawkRuleAdministrator;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;


/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Sep 16, 2004
 * Time: 11:49:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResumeChannelsMethod extends AmiMethod {


    protected HawkRuleAdministrator m_hma;


    public ResumeChannelsMethod(HawkRuleAdministrator hma) {
        super("resumeChannels", "Resumes channels.", AmiConstants.METHOD_TYPE_ACTION);
        m_hma = hma;
    }//constr


    public AmiParameterList getArguments() {
        final AmiParameterList args = new AmiParameterList();
        args.addElement(new AmiParameter("URI", "URI of the Channel to resume (optional).", ""));
        return args;
    }//getArguments


    public AmiParameterList getReturns() {
        return null;
    }//getReturns


    public AmiParameterList onInvoke(AmiParameterList inParams) throws AmiException {
        try {
            final ChannelManager channelManager = m_hma.getServiceProvider().getChannelManager();
            final String uriRequested = inParams.getString(0);
            if ((null != uriRequested) && !"".equals(uriRequested)) {
                final Channel channel = channelManager.getChannel(uriRequested);
                if (null == channel) {
                    throw new AmiException(AmiErrors.AMI_REPLY_ERR, "Invalid Channel URI: " + uriRequested);
                }
                channel.resume();
            } else {
                for (Iterator it = channelManager.getChannels().iterator(); it.hasNext();) {
                    final Channel channel = (Channel) it.next();
                    channel.resume();
                }//for
            }
        } catch (Exception e) {
            throw new AmiException(AmiErrors.AMI_REPLY_ERR, e.getMessage());
        }//catch
        return new AmiParameterList();
    }//onInvoke


}//class


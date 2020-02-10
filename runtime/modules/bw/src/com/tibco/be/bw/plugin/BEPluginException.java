package com.tibco.be.bw.plugin;

import com.tibco.pe.errors.CommonCode;
import com.tibco.pe.plugin.ConfigErrorsHelper;
import com.tibco.plugin.PluginException;
import com.tibco.sdk.MMessageBundle;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Aug 14, 2004
 * Time: 10:36:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class BEPluginException extends PluginException
{
    final static String BE_PLUGIN_MSG="BW-BE";
    final static String BE_PACKAGE="com.tibco.be.bw.plugin.MessageCode";

    static {
        ConfigErrorsHelper.addResourceBundle(BE_PLUGIN_MSG, BE_PACKAGE);
        ConfigErrorsHelper.addResourceBundle(CommonCode.COMMON_KEY, CommonCode.COMMON_MESSAGE_PACKAGE);
    }

    public BEPluginException(String errorCode ) {
        super(errorCode);
    }

    public BEPluginException(String errorCode, Object param1) {
		super(errorCode, param1);
    }

    public BEPluginException(String errorCode, Object param1, Object param2) {
		super(errorCode, param1, param2);
    }

    public BEPluginException(String errorCode, Object param1, Object param2,
                              Object param3) {
		super(errorCode, param1, param2, param3);
    }

	public BEPluginException(XiNode data, String errorCode, Object param1) {
		super(data, errorCode, param1);
	}

	public BEPluginException(XiNode data, String errorCode, Object param1, Object param2 ) 	{
		super(data, errorCode, param1, param2);
	}

    public BEPluginException(String errorCode, XiNode node ) {
        // Parameter order is reversed in this super call.
        // super(errorCode, node) would call the wrong super method
        // and the XiNode would not be available at runtime.
        super(node, errorCode);
    }

    static public String getMessage(String errorCode, Object[] params) {
        return MMessageBundle.getMessage(errorCode, params);
    }

    static public String getMessage(String errorCode) {
        return MMessageBundle.getMessage(errorCode);
    }

}


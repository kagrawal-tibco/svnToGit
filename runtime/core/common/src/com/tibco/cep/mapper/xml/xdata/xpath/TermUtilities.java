package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmType;

public class TermUtilities {

    /**
     * Prints a debug version of the type.
     */
    public static String formatType(SmType type) {
        StringBuffer sb = new StringBuffer();
        String name = type.getName();
        if (name==null) {
            sb.append("<no name>");
        } else {
            sb.append(name);
        }
        SmType base = type.getBaseType();
        if (base!=null) {
            sb.append(" base " + base.getName());
        }
        if (type.isMixedContent()) {
            sb.append(" mixed");
        }
        if (type.getValueType()!=null) {
            sb.append(" value:" + type.getValueType());
        }
        return sb.toString();
    }

    public static int multiplyCardinality(int c1, int c2) {
        if (c1==0 || c2==0) {
            return 0;
        }
        if (c1==SmParticle.UNBOUNDED || c2==SmParticle.UNBOUNDED) {
            return SmParticle.UNBOUNDED;
        }
        long cr = ((long)c1) * c2;
        if (cr>100) { // ? this is probably ok...
            return SmParticle.UNBOUNDED;
        }
        return (int) cr;
    }
}



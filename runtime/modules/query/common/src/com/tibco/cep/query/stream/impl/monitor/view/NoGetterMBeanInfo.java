package com.tibco.cep.query.stream.impl.monitor.view;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Aug 5, 2008
 * Time: 5:27:40 PM
 * To change this template use File | Settings | File Templates.
 */
import java.util.ArrayList;
import java.util.List;

import javax.management.Descriptor;
import javax.management.MBeanException;
import javax.management.MBeanOperationInfo;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanConstructorInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;

public class NoGetterMBeanInfo extends ModelMBeanInfoSupport {
    public NoGetterMBeanInfo(ModelMBeanInfo mmbi) {
        super(mmbi);
    }

    @Override
    public NoGetterMBeanInfo clone() {                                 //todo what does this do ?
        return new NoGetterMBeanInfo(this);
    }

    private Object writeReplace() {
        List ops = new ArrayList();
        for (MBeanOperationInfo mboi : this.getOperations()) {
            ModelMBeanOperationInfo mmboi = (ModelMBeanOperationInfo) mboi;
            Descriptor d = mmboi.getDescriptor();
            String role = (String) d.getFieldValue("role");
            if (!"getter".equalsIgnoreCase(role) &&
                    !"setter".equalsIgnoreCase(role))
                ops.add(mmboi);
        }
        ModelMBeanOperationInfo[] mbois = new ModelMBeanOperationInfo[ops.size()];
        ops.toArray(mbois);
        Descriptor mbeanDescriptor;
        try {
            mbeanDescriptor = this.getMBeanDescriptor();
        } catch (MBeanException e) {
            throw new RuntimeException(e);
        }
        return new ModelMBeanInfoSupport(
                this.getClassName(),
                this.getDescription(),
                (ModelMBeanAttributeInfo[]) this.getAttributes(),
                (ModelMBeanConstructorInfo[]) this.getConstructors(),
                mbois,
                (ModelMBeanNotificationInfo[]) this.getNotifications(),
                mbeanDescriptor);
    }
}

package com.tibco.be.bw.factory;

import java.util.Collection;
import java.util.Properties;

import com.tibco.be.bw.XiEvent;
import com.tibco.be.model.rdf.RDFTnsFlavor;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.PayloadFactory;
import com.tibco.cep.runtime.model.event.impl.PayloadFactoryImpl;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmElement;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jul 13, 2006
 * Time: 9:56:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class XiEntityFactory implements TypeManager {

    RuleServiceProvider provider;
    Properties env;
    PayloadFactory payloadFactory;

    public XiEntityFactory(RuleServiceProvider provider, Properties env) {
        this.provider = provider;
        this.env = env;
        this.payloadFactory = new PayloadFactoryImpl(this);
    }

    public PayloadFactory getPayloadFactory() {
        return payloadFactory;
    }


    public Entity createEntity(ExpandedName en) throws Exception {

        SmElement element = provider.getProject().getSmElement(en);
        String fullPath = en.getNamespaceURI().substring(RDFTnsFlavor.BE_NAMESPACE.length());
        Event evtDesc = (Event)provider.getProject().getOntology().getEntity(fullPath);
        XiEvent event = new XiEvent(0, this, en,element, evtDesc); //TODO
        return event;
    }

    public Entity createEntity(String fullName) throws Exception {
        String ns = RDFTnsFlavor.BE_NAMESPACE + fullName;
        String localName = fullName.substring(fullName.lastIndexOf('/')+1);
        ExpandedName name = ExpandedName.makeName(ns, localName);
        return createEntity(name);
    }

    public Collection getTypeDescriptors(int type) {
        throw new RuntimeException("getTypeDescriptors(Type type) is not implemented in XiEntityFactory");
    }

    public TypeDescriptor getTypeDescriptor(Class cls)  {
        throw new RuntimeException("Use getTypeDescriptor(ExpandedName)");
    }

    public TypeDescriptor getTypeDescriptor(ExpandedName en)  {
        SmElement element = provider.getProject().getSmElement(en);
        return new TypeDescriptor(TypeManager.TYPE_SIMPLEEVENT, "", XiEvent.class, en, element, TypeManager.METRIC_TYPE_FALSE);
    }

    public TypeDescriptor getTypeDescriptor(String fullName) {
        String localName = fullName.substring(fullName.lastIndexOf('/')+1);
        String ns = fullName;
        if (!fullName.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI))
            ns = TypeManager.DEFAULT_BE_NAMESPACE_URI + fullName;
        ExpandedName name = ExpandedName.makeName(ns, localName);
        return getTypeDescriptor(name);
    }

    public boolean instanceOf(Entity entity, String fullName) {
        throw new RuntimeException("instanceOf(Object entity, String fullName) is not implemented in XiEntityFactory");
    }

    public boolean instanceOf(Entity entity, ExpandedName en) {
        throw new RuntimeException("instanceOf(Object entity, ExpandedName en) is not implemented in XiEntityFactory");
    }
}

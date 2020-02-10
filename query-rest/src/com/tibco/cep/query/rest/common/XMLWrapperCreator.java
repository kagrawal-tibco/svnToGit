package com.tibco.cep.query.rest.common;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/17/14
 * Time: 4:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLWrapperCreator<T> extends AbstractTypeWrapperCreator<T> {

    int queryLimit;
    int offset;
    String[] projectionAttributes;
    EntityType entityType;

    public XMLWrapperCreator(int queryLimit, int offset, String[] selectAttributes, EntityType entityType) {
        this.queryLimit = queryLimit;
        this.offset = offset;
        this.projectionAttributes = selectAttributes;
        this.entityType = entityType;
    }

    @Override
    TypeWrapper<T> createWrapper() {
        XmlWrapper xmlWrapper = new XmlWrapper<T>(queryLimit, offset, projectionAttributes, entityType);

        return xmlWrapper;
    }
}

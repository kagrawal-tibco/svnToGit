package com.tibco.cep.query.rest.common;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 4/17/14
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class JsonWrapperCreator<T> extends AbstractTypeWrapperCreator<T> {

    int queryLimit;
    int offset;
    String[] projectionAttributes;
    EntityType entityType;

    public JsonWrapperCreator(int queryLimit, int offset, String[] selectAttributes, EntityType entityType) {
        this.queryLimit = queryLimit;
        this.offset = offset;
        this.projectionAttributes = selectAttributes;
        this.entityType = entityType;
    }


    @Override
    TypeWrapper<T> createWrapper() {
        JsonWrapper jsonWrapper = new JsonWrapper<T>(queryLimit, offset, projectionAttributes, entityType);

        return jsonWrapper;
    }
}

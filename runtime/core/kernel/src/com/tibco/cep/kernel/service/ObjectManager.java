package com.tibco.cep.kernel.service;

import java.util.List;

import com.tibco.cep.kernel.core.base.HandleManger;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Aug 17, 2006
 * Time: 10:35:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectManager extends HandleManger{

    Event getEvent(String extId);

    Event getEvent(long id);

    Element getElement(String extId);

    Element getElement(long id);
    Element getElement(long id, boolean ignoreRetractedOrMarkedDelete);

    int numOfElement();

    int numOfEvent();

    /**
     * Get a list of all objects in the working memory.
     * @return a list of all objects in the working memory.
     */
    List getObjects();

    List getObjects(Filter filter);

    /**
     * Get a list of all handles in the working memory.
     * @return a list of all handles in the working memory.
     */
    List getHandles();

    boolean isObjectStore();

    /**
     * Suresh Added for Fedex - POJO Support
     */

    Entity getEntity(String extId);

    Object getObject(Object obj);

    public Element getNamedInstance(String uri, Class entityClz);

    public void createElement(Element entity) throws DuplicateExtIdException;

    public void createEvent(Event event) throws DuplicateExtIdException;
    
    public void deleteEntity(Handle handle);
    
    Element getElementByUri(String extId, String uri);
    Event 	getEventByUri(String extId, String uri);

    Element getElementByUri(long id, String uri);
    Event 	getEventByUri(long id, String uri);
}

package com.tibco.cep.repo.mutable;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 19, 2006
 * Time: 8:02:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ResourceChangeNotificationEvent {

    int ENTITY_ADDED = 0;
    int ENTITY_UPDATE = 1;
    int ENTITY_DELETE = 2;
    int ENTITY_RENAME = 3;
    int ENTITY_MOVED  = 4;

    /**
     *
     * @return one the predefined Status code
     */
    int getModificationCode();
    /**
     * Get the entity that was modified
     * @return a generic Object
     */
    Object getEntity();

    /**
     * Get the input stream associated to the entity for this Modification Event
      * @return InputStream
     */
    InputStream getInputStream() throws Exception;

    /**
     * Get the OldPath of the entity or the existing path in the filesystem if it has moved
     * @return String
     */
    String getOldPath();

    /**
     * Get the current path of the entity
     * @return String
     */
    String getFullPath();

    /**
     * Return the entity name that was modified
     * @return String
     */
    String getName();

    /**
     * Get the extension of the entity that was modified
     * @return String
     */
    String getExtension();

}

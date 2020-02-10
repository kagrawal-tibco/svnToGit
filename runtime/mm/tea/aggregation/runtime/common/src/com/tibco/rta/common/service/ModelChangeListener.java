package com.tibco.rta.common.service;

import com.tibco.rta.model.MetadataElement;

/**
 * Interface to be implemented by clients wishing to register
 * themselves for model CUD notifications.
 */
public interface ModelChangeListener {

    /**
     * Issue this callback when any new {@MetadataElement} is created.
     * @param metadataElement
     */
    public void onCreate(MetadataElement metadataElement);

    /**
     * Issue this callback when any {@MetadataElement} is updated.
     * @param metadataElement
     */
    public void onUpdate(MetadataElement metadataElement);

    /**
     * Issue this callback when any new {@MetadataElement} is deleted.
     * @param metadataElement
     */
    public void onDelete(MetadataElement metadataElement);
}

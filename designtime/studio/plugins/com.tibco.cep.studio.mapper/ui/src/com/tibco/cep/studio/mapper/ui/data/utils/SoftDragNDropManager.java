package com.tibco.cep.studio.mapper.ui.data.utils;



public interface SoftDragNDropManager {
    public void stopDrag();

    /**
     * Is the currently active drag a 'copy' drag (i.e. did user press the ctrl key)
     */
    public boolean isDragCopy();
}

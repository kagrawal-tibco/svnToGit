package com.tibco.cep.studio.mapper.ui.data.bind.find;

import javax.swing.JComponent;
import javax.swing.event.ChangeListener;

import com.tibco.objectrepo.object.ObjectProvider;

/**
 * WCETODO --- rename & move this somewhere else....
 */
public interface FindWindowPlugin {

   /**
    * Gets the panel used to enter the search 'query'.
    *
    * @return The panel to be used to input the search parameters.
    */
   JComponent getFindParametersPanel();

   /**
    * From within the panel itself, locate the 'primary' entry component; the one that should get focus when activiated.
    *
    * @return The component which MUST be part of the panel {@link #getFindParametersPanel}.
    */
   JComponent getPrimaryEntryComponent();

   void setEditorChangeListener(ChangeListener cl);


   /**
    * Gets the current search filter, as edited by {@link #getFindParametersPanel}.
    * The find data should have a <i>reasonable</i> toString() implementation that corresponds with what is being displayed.
    *
    * @return The find data.
    * @throws Exception If thrown, the message of this exception will be formatted as the reason for not doing a search (i.e. bad regex, etc.)
    */
   Object getFindData() throws Exception;

   JComponent getResultRendererComponent(Object result, boolean isSelected);

   /**
    * Perform a search given the query.
    * This call <b>can</b> be made in a multi-threaded way; an implementation shouldn't shared any state with the
    * other methods; it should just use the passed in object.
    *
    * @param toHandler A callback interface for passing back results.
    * @param findData  A search filter object returned by {@link #getFindData()}.  An implementation should make
    *                  no assumptions about the find data other than that it will have been returned by this {@link #getFindData()}
    *                  (i.e. it shouldn't cache or do anything fancy)
    */
   void find(FindResultHandler toHandler, ObjectProvider objectProvider, String resourceLoc, Object findData);

   /**
    * Indicates that theFor global mode searches only; selects
    *
    * @param displayResource
    * @param findData
    */
   void selectResult(String displayResource, Object findData);
}

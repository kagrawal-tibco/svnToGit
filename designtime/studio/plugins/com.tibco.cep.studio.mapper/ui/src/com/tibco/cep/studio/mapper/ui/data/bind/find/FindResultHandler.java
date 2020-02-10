package com.tibco.cep.studio.mapper.ui.data.bind.find;



/**
 * WCETODO --- rename & move this somewhere else before checkin in.
 */
public interface FindResultHandler {
   /**
    * Issue results here.
    *
    * @param searchResult A search result closure object.
    */
   void result(Object searchResult);

   /**
    * Should be check periodically in the search to see if the search should continue.
    *
    * @return true if the search should stop as soon as possible, false if it should continue.
    */
   boolean hasBeenCancelled();
}

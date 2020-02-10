package com.tibco.cep.studio.mapper.ui.data.bind.find;



/**
 * WCETODO --- rename & move this somewhere else before checkin in.
 */
public class FindWindowResult {
   private final String mLocation;
   private final Object mClosure;

   public FindWindowResult(String location, Object closure) {
      mLocation = location;
      mClosure = closure;
   }

   public Object getClosure() {
      return mClosure;
   }

   public String getLocation() {
      return mLocation;
   }
}

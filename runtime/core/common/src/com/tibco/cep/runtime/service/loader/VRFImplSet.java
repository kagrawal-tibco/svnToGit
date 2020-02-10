package com.tibco.cep.runtime.service.loader;

import java.util.ArrayList;

import com.tibco.cep.runtime.service.decision.impl.DTImpl;
import com.tibco.cep.runtime.service.decision.impl.VRFImpl;

/**
 * Created by IntelliJ IDEA.
* User: aamaya
* Date: May 20, 2009
* Time: 6:40:20 PM
* To change this template use File | Settings | File Templates.
*/
public class VRFImplSet
{
    public ArrayList<String> names = new ArrayList(1);
    public ArrayList<VRFImpl> impls = new ArrayList(1);
    //layout is <dtimpls with prio 1 thru 5><vrfimpls that aren't dtimpls><dtimpls with prio greater than 5>
    public int vrfStartIndex = 0;
    public int lowPrioStartIndex = 0;
    
    //returns true if the impl was inserted into index 0 (means it should be the new default)
    public boolean insertImpl(VRFImpl impl, String implName) {
        if(impl == null || implName == null) return false;
        
        if(impl instanceof DTImpl) {
            int thisPrio = 5;
            int startIndex = 0;
            int endIndex = 0;
            thisPrio = ((DTImpl)impl).priority();
            if(thisPrio > 5) {
                startIndex = lowPrioStartIndex;
                endIndex = impls.size();
            } else {
                startIndex = 0;
                endIndex = vrfStartIndex;
                //DTs with priority <= 5 are inserted before the vrfImpls and the low priority DTs
                vrfStartIndex++;
                lowPrioStartIndex++;
            }

            int ii = startIndex;
            for(; ii < endIndex; ii++) {
                if(((DTImpl)impls.get(ii)).priority() >= thisPrio) break;
            }
            //TODO this is an arraycopy
            impls.add(ii, impl);
            names.add(ii, implName);
            return ii == 0;
        } else {
            //non DTImpls don't have priority so just insert in front of all the others
            //TODO this is an arraycopy
            impls.add(vrfStartIndex, impl);
            names.add(vrfStartIndex, implName);
            //these are inserted before the lowPrio DTImpls
            lowPrioStartIndex++;
            //if the first (highest prio) DTImpl has prio 5 then this new VRFImpl should be the new default
            //because VRFImpls are treated as having priority 5
            return vrfStartIndex == 0 || ((DTImpl)impls.get(0)).priority() == 5;
        }
    }
    
    public VRFImpl removeImpl(String implName) {
        if(implName == null) return null;
        
        int idx = names.lastIndexOf(implName);
        if(idx >= 0) {
            if(idx< vrfStartIndex) vrfStartIndex--;
            if(idx<lowPrioStartIndex) lowPrioStartIndex--;
            //TODO this is an arraycopy
            names.remove(idx);
            return impls.remove(idx);
        }
        return null;
    }
}
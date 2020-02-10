package com.tibco.cep.dashboard.psvr.mal.managers;

import com.tibco.cep.dashboard.psvr.mal.MALElementsCollector;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALRangeAlert;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert;

public class MALRangeAlertElementPostProcessor implements MALElementPostProcessor {

    @Override
    public void postCreateElement(MALElement element) {
        //do nothing
    }

    @Override
    public void postLoad(PersistentStore pStore, MALElement element, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException {
        MALRangeAlert malRangeAlert = (MALRangeAlert) element;
        RangeAlert rangeAlert = (RangeAlert) persistedObject;
        //delete the upper value if needed
        if (rangeAlert.isSetUpperValue() == false) {
            //upper value is not set , clear it from malRangeAlert
            malRangeAlert.deleteUpperValue();
        }
        //delete the lower value if needed
        if (rangeAlert.isSetLowerValue() == false) {
            //upper value is not set , clear it from malRangeAlert
            malRangeAlert.deleteLowerValue();
        }
    }

    @Override
    public void postApplyPersonalizations(PersistentStore pStore, MALElement element, MALElementsCollector elementsCollector) throws MALException {
    }

    @Override
    public void postSave(PersistentStore pStore, MALElement element, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException {
        MALRangeAlert malRangeAlert = (MALRangeAlert) element;
        RangeAlert rangeAlert = (RangeAlert) persistedObject;
        //delete the upper value if needed
        if (malRangeAlert.hasUpperValue() == false) {
            //upper value is not set , clear it from rangeAlert
            rangeAlert.unsetUpperValue();
        }
        //delete the lower value if needed
        if (malRangeAlert.hasLowerValue() == false) {
            //upper value is not set , clear it from rangeAlert
            rangeAlert.unsetLowerValue();
        }
    }

    @Override
    public void postReset(MALElement element, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException {
    }

    @Override
    public void postCopy(MALElement originalElement, MALElement copiedElement, MALElementsCollector elementsCollector, boolean resetPrimaryProps) throws MALException {
    	MALRangeAlert originalRangeAlert = (MALRangeAlert) originalElement;
    	MALRangeAlert copiedRangeAlert = (MALRangeAlert) copiedElement;
    	if (originalRangeAlert.hasUpperValue() == false) {
    		copiedRangeAlert.deleteUpperValue();
    	}
    	if (originalRangeAlert.hasLowerValue() == false) {
    		copiedRangeAlert.deleteLowerValue();
    	}
    }

}

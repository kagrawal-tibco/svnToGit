package com.tibco.cep.dashboard.psvr.mal.managers;

import com.tibco.cep.dashboard.psvr.mal.MALElementsCollector;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.store.PersistentStore;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;

public interface MALElementPostProcessor {

    public void postCreateElement(MALElement element);

    public void postLoad(PersistentStore pStore, MALElement element, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException;

    public void postApplyPersonalizations(PersistentStore pStore, MALElement element, MALElementsCollector elementsCollector) throws MALException;

    public void postSave(PersistentStore pStore, MALElement element, BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException;

    public void postReset(MALElement element,BEViewsElement persistedObject, MALElementsCollector elementsCollector) throws MALException;

    public void postCopy(MALElement originalElement, MALElement copiedElement, MALElementsCollector elementsCollector, boolean resetPrimaryProps) throws MALException;

}
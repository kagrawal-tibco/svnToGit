package com.tibco.cep.studio.core.repo.emf.providers.adapters;


import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.be.util.config.cdd.ObjectManagerConfig;
import com.tibco.cep.designtime.core.model.archive.OBJECT_MGMT_TYPE;

/*
* User: Nicolas Prade
* Date: Sep 9, 2009
* Time: 1:55:28 PM
*/


public class ObjectManagerAdapter {


    private ObjectManagerConfig omConfig;


    public ObjectManagerAdapter(
            ObjectManagerConfig omConfig) {
        this.omConfig = omConfig;
    }


    public OBJECT_MGMT_TYPE getType() {
        if (this.omConfig instanceof CacheManagerConfig) {
            return OBJECT_MGMT_TYPE.CACHE;
        } 
//        else if (this.omConfig instanceof BdbManagerConfig) {
//            return OBJECT_MGMT_TYPE.PERSISTENCE;
//        } 
        else {
            return OBJECT_MGMT_TYPE.IN_MEMORY;
        }
    }


    public int getCacheSize() {
//        switch (this.getType()) {
//            case CACHE: {
//                final CacheManagerConfig mgr = (CacheManagerConfig) this.omConfig;
//                final EvictionConfig eviction = mgr.getEviction();
//                if (null != eviction) {
//                    return Integer.valueOf(CddTools.getValueFromMixed(eviction.getMaxSize();
//                }
//            }
//            break;
//
////            case PERSISTENCE: {
////                final BdbManagerConfig mgr = (BdbManagerConfig) this.omConfig;
////                final EvictionConfig eviction = mgr.getEviction();
////                if (null != eviction) {
////                    return (int) eviction.getMaxSize();
////                }
////
////            }
////            break;
//
//            //case IN_MEMORY:
//            //default:
//        }
        return 0;
    }


    public int getCheckpointInterval() {
        switch (this.getType()) {
//            case PERSISTENCE: {
//                final BdbManagerConfig mgr = (BdbManagerConfig) this.omConfig;
//                //todo
//            }
//            break;

            //case CACHE:
            //case IN_MEMORY:
            //default:
        }
        return 0;
    }


    public int getCheckpointOpsLimit() {
        switch (this.getType()) {
//            case PERSISTENCE: {
//                final BdbManagerConfig mgr = (BdbManagerConfig) this.omConfig;
//                //todo
//            }
//            break;

            //case CACHE:
            //case IN_MEMORY:
            //default:
        }
        return 0;
    }


    public String getDbEnvironmentDir() {
        switch (this.getType()) {
//            case PERSISTENCE: {
//                final BdbManagerConfig mgr = (BdbManagerConfig) this.omConfig;
//                //todo
//            }
//            break;

            //case CACHE:
            //case IN_MEMORY:
            //default:
        }
        return null;
    }


    public String getCacheConfigFile() {
        switch (this.getType()) {
            case CACHE: {
                final CacheManagerConfig mgr = (CacheManagerConfig) this.omConfig;
                //todo
            }
            break;

            //case PERSISTENCE:
            //case IN_MEMORY:
            //default:
        }
        return null; //todo
    }


    public String getCustomRecoveryFactory() {
        switch (this.getType()) {
            case CACHE: {
                final CacheManagerConfig mgr = (CacheManagerConfig) this.omConfig;
                //todo
            }
            break;

//            case PERSISTENCE: {
//                final BdbManagerConfig mgr = (BdbManagerConfig) this.omConfig;
//                //todo
//            }
//            break;

            //case IN_MEMORY:
            //default:
        }
        return null;
    }

}

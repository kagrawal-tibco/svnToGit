package com.tibco.cep.kernel.core.base;

/**
 * Created by IntelliJ IDEA.
* User: aamaya
* Date: Sep 23, 2009
* Time: 12:39:40 PM
* To change this template use File | Settings | File Templates.
*/ 
//unused = not deployed
//unshared = cache-only (thread local)
//shared = not cache-only
//mixed = for the type or its children, some are cache-only and some aren't
public enum EntitySharingLevel
{
    UNUSED, UNSHARED, SHARED, MIXED;

    public static final EntitySharingLevel DEFAULT = SHARED;
}

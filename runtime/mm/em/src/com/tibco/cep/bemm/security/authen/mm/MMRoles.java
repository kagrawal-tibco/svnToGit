package com.tibco.cep.bemm.security.authen.mm;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: 1/28/11
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public enum MMRoles {
    MM_ADMINISTRATOR("mm_administrator"),
    MM_USER("mm_user");

    private String rName;

    MMRoles(String rName) {
        this.rName = rName;
    }

    public String getName() {
        return rName;
    }
}

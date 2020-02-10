package com.tibco.cep.releases.bom.impl;

/**
 * User: nprade
 * Date: 6/28/11
 * Time: 1:36 PM
 */
abstract class AbstractNamedComponent {

    @SuppressWarnings({"CanBeFinal"})
    private String name;


    public AbstractNamedComponent(
            String name) {

        this.name = name;
    }


    public String getName() {
        return this.name;
    }

}

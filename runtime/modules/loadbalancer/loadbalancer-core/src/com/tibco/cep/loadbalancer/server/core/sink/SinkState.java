package com.tibco.cep.loadbalancer.server.core.sink;

/*
* Author: Ashwin Jayaprakash / Date: Jul 12, 2010 / Time: 11:08:34 AM
*/
public enum SinkState {
    off,
    on,

    //Confirmed error, unconfirmed error, unresponsive, unable to confirm - all these are clubbed under a simple state.
    suspect;
}

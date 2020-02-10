package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Oct 22, 2007
 * Time: 2:56:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface GroupPolicy extends QueryContext {


    /**
     * @return CaptureType of this GroupPolicy, null if there is none.
     */
    CaptureType getCaptureType();


    /**
     * @return EmitType of this GroupPolicy, null if there is none.
     */
    EmitType getEmitType();

}

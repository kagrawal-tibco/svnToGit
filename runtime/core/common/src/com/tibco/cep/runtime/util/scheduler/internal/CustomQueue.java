/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.cep.runtime.util.scheduler.internal;

import com.tibco.cep.runtime.util.scheduler.Id;

/**
 *
 * @param <I> item
 * Author: Karthikeyan Subramanian / Date: Jan 27, 2010 / Time: 12:20:25 PM
 */
public interface CustomQueue<I> {

    I getItem() throws InterruptedException;

    void addItem(I item);

    I removeItem(Id jobId);

    void close();

    boolean hasItem(Id jobId);

}

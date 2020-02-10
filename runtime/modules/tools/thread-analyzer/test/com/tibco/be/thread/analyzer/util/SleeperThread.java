/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.be.thread.analyzer.util;

/**
 *
 * @author ksubrama
 * Author: Karthikeyan Subramanian / Date: Nov 23, 2009 / Time: 4:00:45 PM
 */
public class SleeperThread implements Runnable {

    @Override
    public void run() {
        Thread.currentThread().setName("SleeperThread");
        while (!Thread.interrupted()) {
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (InterruptedException ex) {
                break;
            }
        }
    }
}

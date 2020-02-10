package com.tibco.store.query.exec.impl.invm;

import com.tibco.store.query.exec.EvaluationOutputStack;

import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/12/13
 * Time: 4:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class ConcurrentEvaluationOutputStack<T> extends SimpleEvaluationOutputStack<T> implements EvaluationOutputStack<T> {

    private Deque<T> deque = new LinkedBlockingDeque<T>();

    @Override
    public void push(T entry) {
        deque.addFirst(entry);
    }

    @Override
    public T pop() {
        return deque.removeFirst();
    }

    @Override
    public int size() {
        return deque.size();
    }
}

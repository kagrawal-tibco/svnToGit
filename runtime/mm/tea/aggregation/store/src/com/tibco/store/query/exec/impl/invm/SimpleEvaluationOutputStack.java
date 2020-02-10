package com.tibco.store.query.exec.impl.invm;

import com.tibco.store.query.exec.EvaluationOutputStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/12/13
 * Time: 10:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleEvaluationOutputStack<T> implements EvaluationOutputStack<T> {

    private Stack<T> stack = new Stack<T>();

    public void push(T entry) {
        stack.push(entry);
    }

    public T pop() {
        return stack.pop();
    }

    public Collection<T> popTopEntries(int numOfTopEntries) {
        if (size() >= numOfTopEntries) {
            List<T> poppedEntries = new ArrayList<T>(numOfTopEntries);
            for (int loop = 0; loop < numOfTopEntries; loop++) {
                T poppedEntry = pop();
                poppedEntries.add(poppedEntry);
            }
            return poppedEntries;
        }
        return new ArrayList<T>(0);
    }

    public Collection<T> popAllEntries() {
        return popTopEntries(size());
    }

    public int size() {
        return stack.size();
    }
}

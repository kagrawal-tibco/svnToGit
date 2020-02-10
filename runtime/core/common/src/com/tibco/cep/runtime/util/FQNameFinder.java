package com.tibco.cep.runtime.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/*
* Author: Ashwin Jayaprakash Date: Mar 2, 2009 Time: 2:59:02 PM
*/
public interface FQNameFinder extends Externalizable {
    public boolean matches(FQName name);

    //------------

    public static class ChildFinder implements FQNameFinder {
        protected FQName parentFQN;

        public ChildFinder() {
        }

        public FQName getParentFQN() {
            return parentFQN;
        }

        public void setParentFQN(FQName parentFQN) {
            this.parentFQN = parentFQN;
        }

        //-----------

        /**
         * Can't use the {@link FQName#getCollatedName()} because the component names could contain
         * collation characters and we do not escape them.
         *
         * @param name
         * @return
         */
        public boolean matches(FQName name) {
            String[] parentStrings = parentFQN.getComponentNames();
            String[] candidateStrings = name.getComponentNames();

            if (candidateStrings.length < parentStrings.length) {
                return false;
            }

            for (int i = 0; i < parentStrings.length; i++) {
                String parentString = parentStrings[i];
                String candidateString = candidateStrings[i];

                if (parentString.equals(candidateString) == false) {
                    return false;
                }
            }

            return true;
        }

        //-----------

        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(parentFQN);
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            parentFQN = (FQName) in.readObject();
        }
    }

    //------------

    public static class ParentFinder implements FQNameFinder {
        protected FQName childFQN;

        public ParentFinder() {
        }

        public FQName getChildFQN() {
            return childFQN;
        }

        public void setChildFQN(FQName childFQN) {
            this.childFQN = childFQN;
        }

        //-----------

        /**
         * Can't use the {@link FQName#getCollatedName()} because the component names could contain
         * collation characters and we do not escape them.
         *
         * @param name
         * @return
         */
        public boolean matches(FQName name) {
            String[] childStrings = childFQN.getComponentNames();
            String[] candidateStrings = name.getComponentNames();

            if (candidateStrings.length > childStrings.length) {
                return false;
            }

            for (int i = 0; i < candidateStrings.length; i++) {
                String childString = childStrings[i];
                String candidateString = candidateStrings[i];

                if (childString.equals(candidateString) == false) {
                    return false;
                }
            }

            return true;

        }

        //-----------

        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeObject(childFQN);
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            childFQN = (FQName) in.readObject();
        }
    }
}

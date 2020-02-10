package com.tibco.cep.query.stream.monitor;

import java.util.Collection;
import java.util.LinkedHashSet;

import com.tibco.cep.query.stream.util.SingleElementCollection;

/*
 * Author: Ashwin Jayaprakash Date: Nov 27, 2007 Time: 3:19:35 PM
 */

/**
 * {@link #equals(Object)} and {@link #hashCode()} are default from Object.
 */
public final class ResourceId {
    protected final String id;

    protected ResourceId parent;

    protected Object/* LinkedHashSet<ResourceId> */children;

    public ResourceId(String id) {
        this(null, id);
    }

    public ResourceId(ResourceId parent, String id) {
        this.parent = parent;
        this.id = id;

        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }

    public String getId() {
        return id;
    }

    public void addChild(ResourceId childId) {
        if (children == null) {
            children = childId;
        }
        else {
            LinkedHashSet<ResourceId> set = null;

            if (children instanceof LinkedHashSet) {
                set = (LinkedHashSet<ResourceId>) children;
            }
            else {
                set = new LinkedHashSet<ResourceId>();
                set.add((ResourceId) children);
                children = set;
            }

            set.add(childId);
        }

        childId.parent = this;
    }

    /**
     * @return Can be <code>null</code>. If not, then it should <b>not</b> be modified.
     */
    public Collection<ResourceId> getChildren() {
        if (children != null) {
            if (children instanceof LinkedHashSet) {
                return (LinkedHashSet<ResourceId>) children;
            }
            else {
                return new SingleElementCollection<ResourceId>((ResourceId) children);
            }
        }

        return null;
    }

    protected void removeChild(ResourceId childId) {
        if (children != null) {
            if (children instanceof LinkedHashSet) {
                LinkedHashSet<ResourceId> set = (LinkedHashSet<ResourceId>) children;

                set.remove(childId);
                if (set.isEmpty()) {
                    children = null;
                }
            }
            else {
                children = null;
            }
        }

        childId.parent = null;
    }

    /**
     * {@link #children} and {@link #id} remain intact.
     */
    public void discard() {
        if (parent != null) {
            parent.removeChild(this);
        }
    }

    /**
     * @return Can be <code>null</code>.
     */
    public ResourceId getParent() {
        return parent;
    }

    public String generateSequenceToParentString() {
        String s = id;

        if (parent != null) {
            s = parent.generateSequenceToParentString() + " -> " + s;
        }

        return s;
    }

    @Override
    public String toString() {
        return generateSequenceToParentString();
    }
}

package com.tibco.cep.mapper.util.intern;

import java.lang.ref.WeakReference;

/**
 * Purposefully package private.<br>
 * Used exclusively by {@link QNameInterner} and {@link SingleNamespaceInterner}.
 */
final class FinalWeakReference extends WeakReference {
    public FinalWeakReference(Object val) {
        super(val);
    }
}

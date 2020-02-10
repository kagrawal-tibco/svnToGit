package com.tibco.cep.query.exec;

import javassist.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 6, 2007
 * Time: 7:52:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ModifierMask {
    // Modifier masks
    int ABSTRACT = Modifier.ABSTRACT;
    int ANNOTATION = Modifier.ANNOTATION;
    int ENUM = Modifier.ENUM;
    int FINAL = Modifier.FINAL;
    int INTERFACE = Modifier.INTERFACE;
    int NATIVE = Modifier.NATIVE;
    int PRIVATE = Modifier.PRIVATE;
    int PROTECTED = Modifier.PROTECTED;
    int PUBLIC = Modifier.PUBLIC;
    int STATIC = Modifier.STATIC;
    int STRICT = Modifier.STRICT;
    int SYNCHRONIZED = Modifier.SYNCHRONIZED;
    int TRANSIENT = Modifier.TRANSIENT;
    int VOLATILE = Modifier.VOLATILE;
}

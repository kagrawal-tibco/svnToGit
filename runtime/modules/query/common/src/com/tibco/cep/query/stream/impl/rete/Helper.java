package com.tibco.cep.query.stream.impl.rete;

import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;

/*
 * Author: Ashwin Jayaprakash Date: Nov 16, 2007 Time: 3:00:47 PM
 */

public class Helper {
    public static boolean isReteObject(Object object) {
        if (object instanceof Event || object instanceof Element || object instanceof Entity) {
            return true;
        }

        return true;
    }

    public static long extractId(Object object) {
        if (object instanceof Event) {
            return ((Event) object).getId();
        }
        else if (object instanceof Element) {
            return ((Element) object).getId();
        }
        else if (object instanceof Entity) {
            return ((Entity) object).getId();
        }
        else {
            // Object.
            return object.hashCode();
        }
    }
}

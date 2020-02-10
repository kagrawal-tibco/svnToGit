package com.tibco.cep.query.stream.impl.rete.join;

import java.util.Map;

import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.impl.IdentifierImpl;
import com.tibco.cep.query.stream.expression.Expression;
import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Apr 9, 2008 Time: 3:48:44 PM
*/
public class Helper {
    public static Identifier[] extractIdentifiers(Expression expression) {
        Map<String, Class<? extends Tuple>> aliasAndTypes = expression.getAliasAndTypes();

        Identifier[] identifiers = new Identifier[aliasAndTypes.size()];

        int h = 0;
        for (String alias : aliasAndTypes.keySet()) {
            identifiers[h++] = new IdentifierImpl(aliasAndTypes.get(alias), alias);
        }

        return identifiers;
    }

    public static String[] extractIdentifierNames(Identifier[] identifiers) {
        String[] cachedIdentifierNames = new String[identifiers.length];
        int x = 0;

        for (Identifier identifier : identifiers) {
            //Has to be in the same order as the Idents.
            cachedIdentifierNames[x] = identifier.getName();
            x++;
        }

        return cachedIdentifierNames;
    }
}

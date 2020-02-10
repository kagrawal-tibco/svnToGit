package com.tibco.cep.decision.table.constraintpane;

/**
 * User: ssubrama
 * Creation Date: Aug 2, 2008
 * Creation Time: 8:21:41 AM
 * <p/>
 * $LastChangedDate$
 * $Rev$
 * $LastChangedBy$
 * $URL$
 */
interface DecisionTableMutableAttributes {
    String getId();
    void setId(String id);

    String getAlias();
    void setAlias(String alias);

    String getPath();
    void setPath(String path);

    String getBody();
    void setBody(String body);


    static class IdAttribute implements DecisionTableMutableAttributes {
        String id;
        public String getAlias() {
            return null;
        }

        public String getBody() {
            return null;
        }

        public String getId() {
            return id;
        }

        public String getPath() {
            return null;
        }

        public void setAlias(String alias) {

        }

        public void setBody(String body) {

        }

        public void setId(String id) {
            this.id = id;
        }

        public void setPath(String path) {

        }
    }
}

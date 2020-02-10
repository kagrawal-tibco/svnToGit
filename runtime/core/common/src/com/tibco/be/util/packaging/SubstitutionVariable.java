package com.tibco.be.util.packaging;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:52:45 PM
 */

import java.util.Comparator;


public class SubstitutionVariable
        implements Comparator, Cloneable {


    public static final String BOOLEAN_TYPE = "Boolean";
    public static final String INTEGER_TYPE = "Integer";
    public static final String PASSWORD_TYPE = "Password";
    public static final String STRING_TYPE = "String";

    public static final String LEGAL_TYPES[] = {STRING_TYPE, INTEGER_TYPE, BOOLEAN_TYPE, PASSWORD_TYPE};

    private String constraint;
    private boolean deploymentSettable;
    private String description;
    private boolean imported;
    private long modTime;
    private String name;
    private boolean serviceSettable;
    private String type;
    private String value;


    public SubstitutionVariable(String name, String value) {
        this(name, value, 0L);
    }


    public SubstitutionVariable(String name, String value, long modTime) {
        this(name, value, null, true, false, STRING_TYPE, null, modTime);
    }


    public SubstitutionVariable(
            String name,
            String value,
            String description,
            boolean deploymentSettable,
            boolean serviceSettable,
            String type,
            String constraint,
            long modTime) {
        this.imported = false;
        if (value == null) {
            value = "";
        }

        if ((null == name) || name.isEmpty()) {
            this.name = null;
        } else { // Trims '%' off the edges of the name and sets it.
            int k = name.length();

            int i;
            for (i = 0; (i < k) && (name.charAt(i) == '%'); i++) {
            }

            int j;
            for (j = k - 1; (j >= 0) && (name.charAt(j) == '%'); j--) {
            }

            if (j < i) {
                this.name = null;
            } else {
                if (k - 1 == j) {
                    if (0 != i) {
                        name = name.substring(i);
                    }
                } else {
                    name = name.substring(i, j + 1);
                }
                this.name = name;
            }
        }

        if (value == null) {
            this.value = null;
        } else {
            this.value = value;
        }

        if ((type == null) || !this.isLegalType(type)) {
            type = STRING_TYPE;
        }

        this.type = type;
        this.description = description;
        this.deploymentSettable = deploymentSettable;
        this.serviceSettable = serviceSettable;
        this.constraint = constraint;
        this.modTime = modTime;
    }


    public Object clone() throws CloneNotSupportedException {
        final SubstitutionVariable var = new SubstitutionVariable(this.name, this.value);
        var.modTime = this.modTime;
        var.type = this.type;
        var.description = this.description;
        var.deploymentSettable = this.deploymentSettable;
        var.serviceSettable = this.serviceSettable;
        var.constraint = this.constraint;
        return var;
    }


    public int compare(Object obj, Object obj1) {
        if (obj == obj1) {
            return 0;
        } else if (null == obj1) {
            return -1;
        } else if (null == obj) {
            return 1;
        } else {
            return ((String) obj).compareTo((String) obj1);
        }
    }


//    public GlobalVar createGlobalVar() {
//        return new GlobalVar(this.name, this.value, null, this.modTime, this.description, this.deploymentSettable,
//                this.serviceSettable, this.type, this.constraint);
//    }


    public boolean equals(Object obj) {
        if (obj instanceof SubstitutionVariable) {
            final SubstitutionVariable substitutionvariable = (SubstitutionVariable) obj;
            return this.name.equals(substitutionvariable.getName())
                    && (((this.value == null) && (substitutionvariable.value == null))
                    || ((this.value != null) && this.value.equals(substitutionvariable.value)));
        } else {
            return false;
        }
    }


    public String getName() {
        return this.name;
    }


    public String getConstraint() {
        return this.constraint;
    }


    public String getDescription() {
        return this.description;
    }


    public long getModTime() {
        return this.modTime;
    }


    public String getType() {
        return this.type;
    }


    public String getValue() {
        return this.value;
    }


    public boolean identical(Object obj) {
        if (obj instanceof SubstitutionVariable) {
            final SubstitutionVariable var = (SubstitutionVariable) obj;
            return this.name.equals(var.getName())
                    && (this.deploymentSettable == var.deploymentSettable)
                    && (this.serviceSettable == var.serviceSettable)
                    && (((this.value == null) && (var.value == null)) || ((this.value != null) && this.value.equals(var.value)))
                    && (((this.constraint == null) && (var.constraint == null)) || ((this.constraint != null) && this.constraint.equals(var.constraint)))
                    && (((this.type == null) && (var.type == null)) || ((this.type != null) && this.type.equals(var.type)))
                    && (((this.description == null) && (var.description == null)) || ((this.description != null) && this.description.equals(var.description)))
                    ;
        } else {
            return false;
        }
    }


    public boolean isDeploymentSettable() {
        return this.deploymentSettable;
    }


    public boolean isImported() {
        return this.imported;
    }


    public boolean isLegalType(String s) {
        if (null != s) {
            for (String type : LEGAL_TYPES) {
                if (type.equalsIgnoreCase(s)) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isServiceSettable() {
        return this.serviceSettable;
    }


    public void setIsImported(boolean imported) {
        this.imported = imported;
    }


    public String toString() {
        final StringBuffer sb = new StringBuffer(this.name).append(" = ").append(this.value)
                .append(";isDeploymentSettable=").append(this.deploymentSettable)
                .append(";isServiceSettable=").append(this.serviceSettable);

        if (null != this.type) {
            sb.append(";type=").append(this.type);
        }
        if (this.constraint != null) {
            sb.append(";constraint=").append(this.constraint);
        }
        if (null != this.description) {
            sb.append(";description=").append(this.description);
        }
        return sb.toString();
    }


}
package com.tibco.cep.releases.notes;

/**
* User: nprade
* Date: 7/25/11
* Time: 11:33 AM
*/
@SuppressWarnings({"UnusedDeclaration"})
public class RNClosedIssue
        implements Comparable<RNClosedIssue> {


    private final Integer index;
    private final String key;
    private final String product;
    private final String text;


    public RNClosedIssue(
            String key,
            String product,
            Integer index,
            String text) {

        this.index = index;
        this.key = key;
        this.product = product;
        this.text = text;
    }


    @Override
    public int compareTo(
            RNClosedIssue o) {

        if (null == o) {
            return 1;
        }

        if (!this.product.equals(o.product)) {
            return this.product.compareTo(o.product);
        }

        if (!this.index.equals(o.index)) {
            return this.index.compareTo(o.index);
        }

        if (!this.key.equals(o.key)) {
            return this.key.compareTo(o.key);
        }

        return this.text.compareTo(o.text);
    }


    public CharSequence getClosedIssueKey() {

        if ((null != this.index) && (null != this.product)) {
            return new StringBuffer(this.product).append("-").append(this.index);
        }

        return null;
    }


    public Integer getIndex() {

        return this.index;
    }


    public String getKey() {

        return this.key;
    }


    public String getProduct() {

        return this.product;
    }


    public String getText() {

        return this.text;
    }


    public String toString() {

        final boolean noProduct = (null == this.product) || this.product.isEmpty();
        final boolean noIndex = (null == this.index) || this.index.equals(0);
        return new StringBuffer()
                .append(noProduct ? "??" : this.product)
                .append("-")
                .append(noIndex ? "????" : this.index)
                .append((noProduct || noIndex) ?  (" (RN:" + this.key + ")\n") : "\n")
                .append(this.text)
                .toString();
    }


}


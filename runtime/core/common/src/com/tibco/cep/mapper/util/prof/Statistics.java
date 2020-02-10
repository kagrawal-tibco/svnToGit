package com.tibco.cep.mapper.util.prof;

import java.text.NumberFormat;

public class Statistics {
    public Statistics(String name, String catName, long ic, long min, long max, long tt, long ts, Statistics[] c) {
        this.name = name;
        this.catName = catName;
        invokeCount = ic;
        this.min = min;
        this.max = max;
        this.totalTime = tt;
        this.timeSquare = ts;
        this.children = c;
        if (c==null) {
            throw new NullPointerException("Null children");
        }
    }

    public final String name;
    public final String catName;    
    public final long invokeCount;
    public final long min;
    public final long max;
    public final long totalTime;
    public final long timeSquare;
    public final Statistics[] children;

    public double getAverage() {
        if (invokeCount==0) {
            return Double.NaN;
        }
        return ((double)totalTime)/invokeCount;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        format(sb,"",totalTime);
        return sb.toString();
    }

    private void format(StringBuffer sb, String indent, long externalTotalTime) {
        sb.append(indent);
        String nindent = indent + "  ";
        sb.append(name);
        sb.append(" {\n");
        sb.append(nindent + "count   = " + invokeCount + '\n');
        sb.append(nindent + "average = " + getAverage() + '\n');
        sb.append(nindent + "total   = " + totalTime + '\n');
        double percent = ((double)totalTime)/externalTotalTime;
        sb.append(nindent + "percent of total = " + formatPercent(percent) + '\n');
        long accountedFor = 0;
        for (int i=0;i<children.length;i++) {
            accountedFor += children[i].totalTime;
        }
        if (children.length>0) {
            long unaccountedFor = totalTime - accountedFor;
            sb.append(nindent + "unaccounted percent = " + formatPercent((((double)unaccountedFor) / totalTime)) + '\n');
            sb.append(nindent + "unaccounted time = " + unaccountedFor + '\n');
        }
        for (int i=0;i<children.length;i++) {
            children[i].format(sb,nindent,totalTime);
        }
        sb.append(indent);
        sb.append("}\n");
    }

    public String formatPercent(double val) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        return nf.format(val);
    }
}

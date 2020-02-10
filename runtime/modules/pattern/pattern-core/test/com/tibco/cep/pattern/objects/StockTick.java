package com.tibco.cep.pattern.objects;

import com.tibco.cep.pattern.impl.util.EventProperty;
import com.tibco.cep.pattern.impl.util.EventUniqueId;

/*
* Author: Ashwin Jayaprakash Date: Aug 12, 2009 Time: 4:03:21 PM
*/
public class StockTick {
    protected String symbol;

    protected double price;

    protected long volume;

    protected long uniqueId;

    public StockTick() {
    }

    public StockTick(String symbol, double price, long volume, long uniqueId) {
        this.symbol = symbol;
        this.price = price;
        this.volume = volume;
        this.uniqueId = uniqueId;
    }

    @EventProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @EventProperty("price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @EventProperty("volume")
    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    @EventUniqueId
    public long getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(long uniqueId) {
        this.uniqueId = uniqueId;
    }
}
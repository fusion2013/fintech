package com.gjj.igden.model.com.gjj.igden.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gjj.igden.model.Bar;

import java.io.Serializable;

/**
 * Created by murtaza8t8 on 10/14/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarDTO implements Serializable {

    private long id;
    private String instId;
    private String ticker;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;

    public BarDTO() {

    }

    public BarDTO(Bar bar) {
        this.id = bar.getId().getId();
        this.instId = bar.getId().getInstId();
        this.ticker = bar.getTicker();
        this.open = bar.getOpen();
        this.high = bar.getHigh();
        this.low = bar.getLow();
        this.close = bar.getClose();
        this.volume = bar.getVolume();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}

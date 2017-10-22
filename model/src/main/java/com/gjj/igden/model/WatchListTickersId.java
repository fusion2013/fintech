package com.gjj.igden.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by murtaza8t8 on 10/13/2017.
 */
@Embeddable
public class WatchListTickersId implements Serializable {

    private String inst;

    private Long dataSet;

    public WatchListTickersId() {

    }

    public  WatchListTickersId(String inst, Long dataSet) {
        this.inst = inst;
        this.dataSet = dataSet;
    }

    @Column(name = "instId")
    public String getInst() {
        return inst;
    }

    public void setInst(String inst) {
        this.inst = inst;
    }

    public void setDataSet(Long dataSet) {
        this.dataSet = dataSet;
    }

    @Column(name = "watchlist_id_fk")
    public Long getDataSet() {
        return dataSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WatchListTickersId)) return false;
        WatchListTickersId that = (WatchListTickersId) o;
        return Objects.equals(getInst(), that.getInst()) &&
                Objects.equals(getDataSet(), that.getDataSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInst(), getDataSet());
    }
}

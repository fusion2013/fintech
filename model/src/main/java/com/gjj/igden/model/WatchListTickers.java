package com.gjj.igden.model;

import com.google.common.base.Objects;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by murtaza8t8 on 10/11/2017.
 */
@Entity
@Table(name = "wl_tickers")
public class WatchListTickers implements Serializable {

    private WatchListTickersId id;

    @EmbeddedId
    public WatchListTickersId getId() {
        return id;
    }

    public void setId(WatchListTickersId id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WatchListTickers watchListTickers = (WatchListTickers) o;
        return Objects.equal(id, watchListTickers.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

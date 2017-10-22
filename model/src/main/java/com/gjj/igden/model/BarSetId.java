package com.gjj.igden.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by murtaza8t8 on 10/14/2017.
 */
@Embeddable
public class BarSetId implements Serializable {

    private Long id;

    private String instId;

    public BarSetId() {

    }

    public BarSetId(Long id) {
        this.id = id;
    }

    public BarSetId(Long id, String instId) {
        this.id = id;
        this.instId = instId;
    }

    @Column(name ="md_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name ="instId_fk")
    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BarSetId)) return false;

        BarSetId barSetId = (BarSetId) o;

        if (!getId().equals(barSetId.getId())) return false;
        return getInstId().equals(barSetId.getInstId());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getInstId().hashCode();
        return result;
    }
}

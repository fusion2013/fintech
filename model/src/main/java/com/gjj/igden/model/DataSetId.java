package com.gjj.igden.model;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by murtaza8t8 on 10/14/2017.
 */
@Embeddable
public class DataSetId implements Serializable {


    private Long id;

    private Long accountId;

    public DataSetId() {

    }

    public DataSetId(Long id) {
        this.id = id;
    }

    public DataSetId(Long id, Long accountId) {
        this.id = id;
        this.accountId = accountId;
    }

    @Column(name ="data_set_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "account_fk_id")
    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSetId)) return false;
        DataSetId that = (DataSetId) o;
        return Objects.equals(getAccountId(), that.getAccountId()) &&
                Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getId());
    }
}

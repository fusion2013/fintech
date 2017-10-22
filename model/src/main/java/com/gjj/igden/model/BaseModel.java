package com.gjj.igden.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "basemodel")
public class BaseModel implements Serializable {

    private Long id;

    private String name;

    private String code;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(unique = true, name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

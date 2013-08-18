package com.damintsev.client.devices;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Damintsev Andrey
 * Date: 18.08.13
 * Time: 19:18
 */
@Entity
@org.hibernate.annotations.Entity
@Table(name = "TRUNK_NAME")
public class TrunkName {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

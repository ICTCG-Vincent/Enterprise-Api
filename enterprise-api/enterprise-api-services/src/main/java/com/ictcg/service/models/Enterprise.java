package com.ictcg.service.models;

import com.ictcg.service.constraint.ValidEnterpriseAddressConstraint;
import com.ictcg.service.constraint.ValidTvaConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "enterprise")
public class Enterprise {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "The enteprise name should be present")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "A tva should be present")
    @Column(nullable = false)
    @ValidTvaConstraint
    private String tva;

    @ValidEnterpriseAddressConstraint
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "enterprise_addresses",
            joinColumns =
                    { @JoinColumn(name = "enterprise_id", referencedColumnName = "id", nullable = false) },
            inverseJoinColumns =
                    { @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false) })
    private Set<Address> addresses;

    @ManyToOne( fetch = FetchType.EAGER, optional = false)
    @JoinTable(name = "siege_address",
            joinColumns =
                    { @JoinColumn(name = "enterprise_id", referencedColumnName = "id", nullable = false) },
            inverseJoinColumns =
                    { @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false) })
    private Address siegeSocial;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTva() {
        return tva;
    }

    public void setTva(String tva) {
        this.tva = tva;
    }

    public void setSiege(Address siegeSocial){
        this.siegeSocial = siegeSocial;
    }

    public Address getSiege(){
        return this.siegeSocial;
    }

    public Set<Address> getAddress(){
        return this.addresses;
    }

    public void setAddress(Set<Address> addresses){
        this.addresses = addresses;
    }

}

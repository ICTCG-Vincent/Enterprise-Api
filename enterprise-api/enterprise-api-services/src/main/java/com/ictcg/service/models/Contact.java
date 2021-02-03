package com.ictcg.service.models;

import com.ictcg.service.constraint.ValidStatusConstraint;
import com.ictcg.service.constraint.ValidTvaConstraint;
import com.ictcg.service.types.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "contact")
@ValidStatusConstraint
public class Contact {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "The name should be present")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "The firstName should be present")
    @Column(nullable = false)
    private String firstname;

    @NotNull(message = "The status should be present")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ValidTvaConstraint
    private String tva;

    @NotNull
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "enterprise_contact",
            joinColumns =
                    { @JoinColumn(name = "contact_id", referencedColumnName = "id") },
            inverseJoinColumns =
                    { @JoinColumn(name = "enterprise_id", referencedColumnName = "id") })
    private Set<Enterprise> enterprise;

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
    public String getFirstName() {
        return firstname;
    }
    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public Status getStatus(){
        return status;
    }
    public void setStatus(Status status){
        this.status = status;
    }

    public Address getAddress(){
        return this.address;
    }

    public void setAddress(Address address){
        this.address = address;
    }

    public Set<Enterprise> getEnterprise(){
        return this.enterprise;
    }

    public void setEnterprise(Set<Enterprise> enterprise){
        this.enterprise = enterprise;
    }

    public void setTva(String tva){
        this.tva = tva;
    }

    public String getTva(){
        return this.tva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return id == contact.id && Objects.equals(name, contact.name) && Objects.equals(firstname, contact.firstname) && status == contact.status && Objects.equals(address, contact.address) && Objects.equals(enterprise, contact.enterprise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstname, status, address, enterprise);
    }
}

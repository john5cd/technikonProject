package com.technikon.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Property implements Serializable {
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private Long propertyId;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private int yearOfConstruction;
    
    @Column(nullable = false)
    private PropertyType propertyType;
    
    @ManyToOne
    @JoinColumn(name = "propertyOwner_id")
    private PropertyOwner propertyOwner;
    
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyRepair> propertyRepairs;

    @Override
    public String toString() {
        return "Property{" + "id=" + id + ", propertyId=" + propertyId + ", address=" + address + ", yearOfConstruction=" + yearOfConstruction + ", propertyType=" + propertyType + ", propertyOwner=" + propertyOwner.getId() + ", propertyRepairs=" + propertyRepairs + ", isActive=" + isActive + '}';
    }
    
    
}

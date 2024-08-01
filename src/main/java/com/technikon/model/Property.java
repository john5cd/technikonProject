package com.technikon.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
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

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PropertyRepair> propertyRepairs;
    
}

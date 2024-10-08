package com.technikon.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyRepair implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repairId;

    @ManyToOne
    @JoinColumn(name = "propertyOwner_id")
    private PropertyOwner propertyOwner;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeOfRepairEnum typeOfRepair; //Enum

    @Column(nullable = false)
    private String shortDescription;

    @Column(nullable = false)
    private LocalDate submissionDate;

    @Column(nullable = false)
    private String description;

    private LocalDate proposedStartDate;

    private LocalDate proposedEndDate;

    private int proposedCost;

    private boolean ownerAcceptance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOfRepairEnum status;

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;
    // Constructors, Getters, Setters, toString() from lombok
    
    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Override
    public String toString() {
        return "PropertyRepair{" + "repairId=" + repairId + ", propertyOwner=" + propertyOwner.getId() + ", property=" + property.getId() + ", typeOfRepair=" + typeOfRepair + ", shortDescription=" + shortDescription + ", submissionDate=" + submissionDate + ", description=" + description + ", proposedStartDate=" + proposedStartDate + ", proposedEndDate=" + proposedEndDate + ", proposedCost=" + proposedCost + ", ownerAcceptance=" + ownerAcceptance + ", status=" + status + ", actualStartDate=" + actualStartDate + ", actualEndDate=" + actualEndDate + ", isActive=" + isActive +'}';
    }
}

package com.technikon.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.*;

@Data
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
    private String typeOfRepair; //Enum

    @Column(nullable = false)
    private String shortDescription;

    @Column(nullable = false)
    private LocalDateTime submissionDate; //ok

    @Column(nullable = false)
    private String description;

    private LocalDate proposedStartDate;

    private LocalDate proposedEndDate;

    private int proposedCost;//auto iswn na to kanoyme kai int alla den einai toso geniko

    private boolean ownerAcceptance;

    @Column(nullable = false)
    private String status;//enum

    private LocalDate actualStartDate;

    private LocalDate actualEndDate;
    // Constructors, Getters, Setters, toString() from lombok
}

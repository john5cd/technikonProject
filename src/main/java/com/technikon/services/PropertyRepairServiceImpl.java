package com.technikon.services;

import com.technikon.model.Property;
import com.technikon.model.PropertyOwner;
import com.technikon.model.PropertyRepair;
import com.technikon.model.StatusOfRepairEnum;
import com.technikon.model.TypeOfRepairEnum;
import com.technikon.repository.PropertyRepairRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PropertyRepairServiceImpl implements PropertyRepairService{
    
    private final PropertyRepairRepository propertyRepairRepository;

    public PropertyRepairServiceImpl(PropertyRepairRepository propertyRepairRepository) {
        this.propertyRepairRepository = propertyRepairRepository;
    }
    
    @Override
    public void initiateRepair(Long ownerId, Long propertyId, TypeOfRepairEnum typeOfRepair, String shortDescription, LocalDateTime dateTimeOfTheSubmission, String fullDescription) {
        PropertyOwner propertyOwner = PropertyOwner.builder().id(ownerId).build();
        Property property = Property.builder().id(propertyId).build();
        PropertyRepair repair = PropertyRepair.builder()
                .propertyOwner(propertyOwner)
                .property(property)
                .typeOfRepair(typeOfRepair)
                .shortDescription(shortDescription)
                .submissionDate(dateTimeOfTheSubmission)
                .description(fullDescription)
                .status(StatusOfRepairEnum.PENDING)
                .build();
        propertyRepairRepository.create(repair);
    }

    @Override
    public List<PropertyRepair> findRepairsInProgressByOwner(Long ownerId) {
        return propertyRepairRepository.findAll().stream()
                .filter(repair -> StatusOfRepairEnum.INPROGRESS.equals(repair.getStatus()) && ownerId.equals(repair.getPropertyOwner().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<PropertyRepair> findRepairsCompletedByOwner(Long ownerId) {
        return propertyRepairRepository.findAll().stream()
                .filter(repair -> StatusOfRepairEnum.COMPLETE.equals(repair.getStatus()) && ownerId.equals(repair.getPropertyOwner().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePendingRepair(Long repairId) {
        propertyRepairRepository.findById(repairId).ifPresent(repair -> {
            if (StatusOfRepairEnum.PENDING.equals(repair.getStatus())) {
                propertyRepairRepository.delete(repair);
            }
        });
    }
}
package com.technikon.services;

import com.technikon.model.PropertyRepair;
import com.technikon.model.TypeOfRepairEnum;
import java.time.LocalDateTime;
import java.util.List;

public interface PropertyRepairService {
    void initiateRepair(Long ownerId, Long propertyId, TypeOfRepairEnum typeOfRepair, String shortDescription, LocalDateTime dateTimeOfTheSubmission, String fullDescription);
    List<PropertyRepair> findRepairsInProgressByOwner(Long ownerId);
    List<PropertyRepair> findRepairsCompletedByOwner(Long ownerId);
    void deletePendingRepair(Long repairId);
 
}

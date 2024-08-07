package com.technikon.services;

import com.technikon.model.PropertyRepair;
import com.technikon.model.TypeOfRepairEnum;
import java.time.LocalDate;
import java.util.List;

public interface PropertyRepairService {
    void initiateRepair(Long ownerId, Long propertyId, TypeOfRepairEnum typeOfRepair, String shortDescription, String fullDescription);
    List<PropertyRepair> findRepairsInProgressByOwner(Long ownerId);
    List<PropertyRepair> findRepairsCompletedByOwner(Long ownerId);
    void deletePendingRepair(Long repairId);
 
    Boolean acceptRepair(PropertyRepair propertyRepair);
    <T> void updateRepair(PropertyRepair propertyRepair,T value,int choose);
    List<PropertyRepair> searchRepairsByDateRage(LocalDate startDate,LocalDate endDate);
    List<PropertyRepair> searchRepairsBySubmissionDate(LocalDate submissionDate);
    void softDelete(Long repairId);
 
}

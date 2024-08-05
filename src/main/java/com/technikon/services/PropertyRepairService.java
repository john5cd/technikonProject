package com.technikon.services;

import com.technikon.model.PropertyRepair;
import com.technikon.model.TypeOfRepairEnum;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;

public interface PropertyRepairService {
    void initiateRepair(Long ownerId, Long propertyId, TypeOfRepairEnum typeOfRepair, String shortDescription, LocalDateTime dateTimeOfTheSubmission, String fullDescription);
    List<PropertyRepair> findRepairsInProgressByOwner(Long ownerId);
    List<PropertyRepair> findRepairsCompletedByOwner(Long ownerId);
    void deletePendingRepair(Long repairId);
 
    Boolean acceptRepair(PropertyRepair propertyRepair);
    <T> void updateRepair(PropertyRepair propertyRepair,T value,int choose);
    List<PropertyRepair> searchRepairsByDateRage(Date startDate,Date endDate);
    List<PropertyRepair> searchRepairsBySubmissionDate(Date submissionDate);
 
}

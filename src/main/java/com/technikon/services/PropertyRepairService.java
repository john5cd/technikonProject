
package com.technikon.services;

import com.technikon.model.PropertyRepair;
import java.util.Date;
import java.util.List;


public interface PropertyRepairService {
    Boolean acceptRepair(PropertyRepair propertyRepair);
    <T> void updateRepair(PropertyRepair propertyRepair,T value,int choose);
    List<PropertyRepair> searchRepairsByDateRage(Date startDate,Date endDate);
    List<PropertyRepair> searchRepairsBySubmissionDate(Date submissionDate);
    
}

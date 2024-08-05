package com.technikon.services;

import com.technikon.model.PropertyRepair;
import com.technikon.model.StatusOfRepairEnum;
import com.technikon.model.TypeOfRepairEnum;
import com.technikon.repository.PropertyRepairRepository;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

public class PropertyRepairServiceImpl implements PropertyRepairService {

    private EntityManager entityManager;
    
    
    PropertyRepairRepository propertyRepairRepository = new PropertyRepairRepository(entityManager);

    @Override
    public Boolean acceptRepair(PropertyRepair propertyRepair) {
        if (propertyRepair.isOwnerAcceptance() == true) {
            propertyRepair.setStatus(StatusOfRepairEnum.INPROGRESS);
            propertyRepair.setActualStartDate(propertyRepair.getProposedStartDate());
            propertyRepair.setActualEndDate(propertyRepair.getProposedEndDate());
            propertyRepairRepository.update(propertyRepair);
            return true;
        }

        propertyRepair.setStatus(StatusOfRepairEnum.DECLINED);
        /*-------------------Stay null--------------*/
        propertyRepairRepository.update(propertyRepair);
        return false;
    }

    @Override
    public <T> void updateRepair(PropertyRepair propertyRepair,T value,int choose) {//any type of data
        if (propertyRepair.getStatus().equals("PENDING")) {
            switch (choose) {
                case 1 -> {
                    if (value instanceof TypeOfRepairEnum typeOfRepairStr){
                        propertyRepair.setTypeOfRepair(typeOfRepairStr);
                    }
                }
                case 2 -> {
                    if (value instanceof String shortDesciptionStr) {
                        propertyRepair.setShortDescription(shortDesciptionStr);
                    }
                    //auta isws ginoun throws h exceptions
                }
                case 3 -> {
                    if(value instanceof String descriptionStr){
                        propertyRepair.setDescription(descriptionStr);
                    }
                }

            }
            
            propertyRepairRepository.update(propertyRepair);
        } else {
            //θα βάλω exception
        }

    }

    @Override
    public List<PropertyRepair> searchRepairsByDateRage(Date startDate, Date endDate) {
        return propertyRepairRepository.searchByDateRange(startDate, endDate);
    }

    @Override
    public List<PropertyRepair> searchRepairsBySubmissionDate(Date submissionDate){
    return propertyRepairRepository.searchBySubmissionDate(submissionDate);
    }
}

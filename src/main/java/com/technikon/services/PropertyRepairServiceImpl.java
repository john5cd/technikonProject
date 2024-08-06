package com.technikon.services;

import com.technikon.exception.OwnerNotFoundException;
import com.technikon.model.Property;
import com.technikon.model.PropertyOwner;
import com.technikon.model.PropertyRepair;
import com.technikon.model.StatusOfRepairEnum;
import com.technikon.model.TypeOfRepairEnum;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.repository.PropertyRepairRepository;
import com.technikon.repository.PropertyRepository;
import java.util.Date;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hibernate.PropertyNotFoundException;

public class PropertyRepairServiceImpl implements PropertyRepairService {

    private final PropertyOwnerRepository propertyOwnerRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyRepairRepository propertyRepairRepository;

    public PropertyRepairServiceImpl(PropertyOwnerRepository propertyOwnerRepository, PropertyRepository propertyRepository, PropertyRepairRepository propertyRepairRepository) {
        this.propertyOwnerRepository = propertyOwnerRepository;
        this.propertyRepository = propertyRepository;
        this.propertyRepairRepository = propertyRepairRepository;
    }

    @Override
    public void initiateRepair(Long ownerId, Long propertyId, TypeOfRepairEnum typeOfRepair, String shortDescription, LocalDateTime dateTimeOfTheSubmission, String fullDescription) {
        PropertyOwner propertyOwner = propertyOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Owner with id " + ownerId + " not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property with id " + propertyId + " not found"));

        PropertyRepair repair = PropertyRepair.builder()
                .propertyOwner(propertyOwner)
                .property(property)
                .typeOfRepair(typeOfRepair)
                .shortDescription(shortDescription)
                .submissionDate(dateTimeOfTheSubmission)
                .description(fullDescription)
                .status(StatusOfRepairEnum.PENDING)
                .isActive(true)
                .build();
        propertyRepairRepository.create(repair);
    }

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
        PropertyRepair repair = propertyRepairRepository.findById(repairId)
            .orElseThrow(() -> new IllegalStateException("Repair with id " + repairId + " does not exist"));

        if (repair.getStatus() == StatusOfRepairEnum.PENDING) {
            repair.setIsActive(false); 
            propertyRepairRepository.update(repair);
        } else {
            throw new IllegalStateException("Repair is not pending and cannot be deleted");
        }
    }

    @Override
    public <T> void updateRepair(PropertyRepair propertyRepair, T value, int choose) {//any type of data
        if (propertyRepair.getStatus().equals("PENDING")) {
            switch (choose) {
                case 1 -> {
                    if (value instanceof TypeOfRepairEnum typeOfRepairStr) {
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
                    if (value instanceof String descriptionStr) {
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
    public List<PropertyRepair> searchRepairsBySubmissionDate(Date submissionDate) {
        return propertyRepairRepository.searchBySubmissionDate(submissionDate);
    }
}

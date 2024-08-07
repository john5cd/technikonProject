package com.technikon.services;

import com.technikon.exception.InvalidInputException;
import com.technikon.exception.OwnerNotFoundException;
import com.technikon.exception.PropertyNotFoundException;
import com.technikon.exception.PropertyOwnerExistsException;
import com.technikon.model.Property;
import com.technikon.model.PropertyOwner;
import com.technikon.model.PropertyRepair;
import com.technikon.model.StatusOfRepairEnum;
import com.technikon.model.TypeOfRepairEnum;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.repository.PropertyRepairRepository;
import com.technikon.repository.PropertyRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;

public class PropertyRepairServiceImpl implements PropertyRepairService {

    private final PropertyRepairRepository propertyRepairRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final PropertyRepository propertyRepository;

    public PropertyRepairServiceImpl(PropertyOwnerRepository propertyOwnerRepository, PropertyRepository propertyRepository, PropertyRepairRepository propertyRepairRepository) {
        this.propertyOwnerRepository = propertyOwnerRepository;
        this.propertyRepository = propertyRepository;
        this.propertyRepairRepository = propertyRepairRepository;
    }

    @Override
    public void initiateRepair(Long ownerId, Long propertyId, TypeOfRepairEnum typeOfRepair, String shortDescription, String fullDescription) {
        PropertyOwner propertyOwner = propertyOwnerRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Owner with id " + ownerId + " not found"));

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyNotFoundException("Property with id " + propertyId + " not found"));

        PropertyRepair repair = PropertyRepair.builder()
                .propertyOwner(propertyOwner)
                .shortDescription(shortDescription)
                .property(property)
                .typeOfRepair(typeOfRepair)
                .submissionDate(LocalDate.now())
                .description(fullDescription)
                .status(StatusOfRepairEnum.PENDING)
                .isActive(true)
                .build();
        propertyRepairRepository.create(repair);
    }

    @Override
    public Boolean acceptRepair(PropertyRepair propertyRepair) throws PropertyOwnerExistsException {
        try {
            if (propertyRepair != null) {
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
            }
        } catch (PersistenceException e) {
            throw new OwnerNotFoundException("There is no property to repair");
        }
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
        propertyRepairRepository.findById(repairId).ifPresent(repair -> {
            if (StatusOfRepairEnum.PENDING.equals(repair.getStatus())) {
                propertyRepairRepository.delete(repair);
            }
        });
    }

    @Override
    public <T> void updateRepair(PropertyRepair propertyRepair, T value, int choose) {//any type of data
        try {
            if (propertyRepair.getStatus().equals(StatusOfRepairEnum.PENDING) && propertyRepair.getIsActive()) {
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
            }
        } catch (PersistenceException e) {
            throw new InvalidInputException("Your input is Invalid");
        }

    }

    @Override
    public List<PropertyRepair> searchRepairsByDateRage(LocalDate startDate, LocalDate endDate) {
        return propertyRepairRepository.searchByDateRange(startDate, endDate);
    }

    @Override
    public List<PropertyRepair> searchRepairsBySubmissionDate(LocalDate submissionDate) {
        return propertyRepairRepository.searchBySubmissionDate(submissionDate);
    }

    @Override
    public void softDelete(Long repairId) throws PropertyOwnerExistsException {
          PropertyRepair repair = propertyRepairRepository.findById(repairId)
            .orElseThrow(() -> new IllegalStateException("Repair with id " + repairId + " does not exist"));

        if (repair.getStatus().equals(StatusOfRepairEnum.PENDING)) {
            repair.setIsActive(false); 
            propertyRepairRepository.update(repair);
        } else {
            throw new IllegalStateException("Repair is not pending and cannot be deleted");
        }
    }
}

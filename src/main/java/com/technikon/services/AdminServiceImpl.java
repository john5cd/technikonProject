package com.technikon.services;

import com.technikon.exception.InvalidInputException;
import com.technikon.exception.PropertyNotFoundException;
import com.technikon.model.Admin;
import com.technikon.model.Property;
import com.technikon.model.PropertyRepair;
import com.technikon.model.StatusOfRepairEnum;
import com.technikon.repository.AdminRepository;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.repository.PropertyRepairRepository;
import com.technikon.repository.PropertyRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyRepairRepository propertyRepairRepository;

    public AdminServiceImpl(AdminRepository adminRepository,
            PropertyOwnerRepository propertyOwnerRepository,
            PropertyRepository propertyRepository,
            PropertyRepairRepository propertyRepairRepository,
            EntityManager entityManager) {
        this.adminRepository = adminRepository;
        this.propertyRepository = propertyRepository;
        this.propertyRepairRepository = propertyRepairRepository;

    }

    @Override
    public Admin createAdmin(Admin admin) {
        return adminRepository.createAdmin(admin);
    }

    @Override
    public Admin changeAdmin(String username, String password) {
        return adminRepository.changeAdmin(username, password);
    }

    @Override
    public String getAdminUsername() {
        return adminRepository.getAdminUsername();
    }

    @Override
    public List<PropertyRepair> getAllRepairs() {
        return propertyRepairRepository.findAll();
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public PropertyRepair repairProposition(Long repairId, String newStatus, LocalDate proposedStartDate, LocalDate proposedEndDate, int proposedCost)
            throws PropertyNotFoundException, InvalidInputException {
        // Find the repair by ID
        Optional<PropertyRepair> optionalPropertyRepair = propertyRepairRepository.findById(repairId);

        if (optionalPropertyRepair.isEmpty()) {
            throw new PropertyNotFoundException("Repair with id " + repairId + " does not exist");
        } else {
            PropertyRepair repair = optionalPropertyRepair.get();

            // Validate and set the new status
            try {
                StatusOfRepairEnum statusEnum = StatusOfRepairEnum.valueOf(newStatus);
                repair.setStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new InvalidInputException("Invalid status: " + newStatus);
            }

            // Update the proposed cost and dates
            repair.setProposedCost(proposedCost);
            repair.setProposedStartDate(proposedStartDate);
            repair.setProposedEndDate(proposedEndDate);

            // Save the updated repair
            propertyRepairRepository.update(repair);

            return repair;
        }
    }

    @Override
    public List<PropertyRepair> getActiveRepairs() {
        return adminRepository.getActiveRepairs();
    }

    @Override
    public List<PropertyRepair> getInactiveRepairs() {
        return adminRepository.getInactiveRepairs();
    }

    @Override
    public List<PropertyRepair> getAllPendingRepairs() {
        return adminRepository.getAllPendingRepairs();
    }
}

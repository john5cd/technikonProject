package com.technikon.services;

import com.technikon.model.Property;
import com.technikon.model.PropertyOwner;
import com.technikon.model.PropertyType;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.repository.PropertyRepository;
import java.util.List;
import java.util.Optional;


public class PropertyServiceImpl implements PropertyService{

    private final PropertyRepository propertyRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    
    public PropertyServiceImpl(PropertyRepository propertyRepository, PropertyOwnerRepository propertyOwnerRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
    }
    
    @Override
    public Optional<Property> findByPropertyIdNumber(Long propertyIdNumber) {
        return propertyRepository.findByPropertyIdNumber(propertyIdNumber);
    }

    @Override
    public List<Property> findByOwnerVatNumber(Long vatNumber) {
        return propertyRepository.findByOwnerVatNumber(vatNumber);
    }

    @Override
    public Property createProperty(Long propertyIdNumber, String address, int yearOfConstruction, PropertyType propertyType, Long propertyOwnerId) {
        
        //check if owner exists
        Optional<PropertyOwner> owner = propertyOwnerRepository.findById(propertyOwnerId);
        
        if (!owner.isPresent()) {
            throw new IllegalArgumentException("Owner with ID " + propertyOwnerId + " does not exist."); //TODO custom exception
        }
        //check if property id (e9) is unique
        Optional<Property> existingProperty = propertyRepository.findByPropertyIdNumber(propertyIdNumber);
        
        if (existingProperty.isPresent()) {
            throw new IllegalArgumentException("Property with ID " + propertyIdNumber + " already exists."); //TODO custom exception
        }
        
        Property property = Property.builder()
                .propertyId(propertyIdNumber)
                .address(address)
                .yearOfConstruction(yearOfConstruction)
                .propertyType(propertyType)
                .propertyOwner(owner.get())
                .build();
        
        return propertyRepository.create(property);
    }

    @Override
    public Property updateProperty(Long id, Long propertyIdNumber, String address, int yearOfConstruction, PropertyType propertyType, Long propertyOwnerId) {
        Optional<Property> propertyToUpdateCheck = propertyRepository.findById(id);
        if (!propertyToUpdateCheck.isPresent()){
            throw new IllegalArgumentException("Property with ID " + id + " does not exist."); //TODO custom exception
        }
        
        Property propertyToUpdate = propertyToUpdateCheck.get();
        
        //check if its going to be changed and validate that the new property id will be different from the previous one if its going to be changed
        if (propertyIdNumber != null && !propertyIdNumber.equals(propertyToUpdate.getPropertyId())) {
            //check if the propertyId already exists
            Optional<Property> existingPropertyWithSameIdNumber = findByPropertyIdNumber(propertyIdNumber);
            if (existingPropertyWithSameIdNumber.isPresent()) {
                throw new IllegalArgumentException("Property with identification number " + propertyIdNumber + " already exists."); //TODO create custom exception
            }
            propertyToUpdate.setPropertyId(propertyIdNumber);
        }
        
        if (address != null) {
            propertyToUpdate.setAddress(address);
        }
        
        if (yearOfConstruction > 1800 && yearOfConstruction <= java.time.Year.now().getValue()) {
            propertyToUpdate.setYearOfConstruction(yearOfConstruction);
        } else if (yearOfConstruction != 0) {
            throw new IllegalArgumentException("Year of construction must be between 1800 and the current year."); //TODO custom exception
        }
        
        if (propertyType != null) {
            propertyToUpdate.setPropertyType(propertyType);
        }
        
        if (propertyOwnerId != null) {
            Optional<PropertyOwner> owner = propertyOwnerRepository.findById(propertyOwnerId);
            if (!owner.isPresent()) {
                throw new IllegalArgumentException("Owner with ID " + propertyOwnerId + " does not exist."); //TODO custom exception
            }
            propertyToUpdate.setPropertyOwner(owner.get());
        }
        
        propertyRepository.update(propertyToUpdate);
        
        return propertyToUpdate;
        
        
    }

    @Override
    public Property deleteProperty(Long id) {
        Optional<Property> propertyToDeleteCheck = propertyRepository.findById(id);
        if (!propertyToDeleteCheck.isPresent()) {
            throw new IllegalArgumentException("Property with ID " + id + " does not exist.");
        }
        
        Property propertyToDelete = propertyToDeleteCheck.get();
        propertyRepository.delete(propertyToDelete);
        return propertyToDelete;
    }

    
    
}

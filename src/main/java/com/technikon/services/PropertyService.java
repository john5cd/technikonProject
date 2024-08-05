package com.technikon.services;

import com.technikon.model.Property;
import com.technikon.model.PropertyType;
import java.util.List;
import java.util.Optional;


public interface PropertyService {
    Property createProperty(Long propertyIdNumber, String address, int yearOfConstruction, PropertyType propertyType, Long propertyOwnerId);
    Property updateProperty(Long id, Long propertyIdNumber, String address, int yearOfConstruction, PropertyType propertyType, Long propertyOwnerId);
    Optional<Property> findByPropertyIdNumber(Long propertyIdNumber);
    List<Property> findByOwnerVatNumber(Long vatNumber);
    Property deleteProperty(Long id);
}

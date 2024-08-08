package com.technikon;

import com.technikon.model.PropertyOwner;
import com.technikon.model.PropertyType;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.repository.PropertyRepository;
import com.technikon.services.PropertyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class MockTest {
    @Mock
    private PropertyRepository propertyRepository;
    
    @Mock
    private PropertyOwnerRepository propertyOwnerRepository;
    
    @InjectMocks
    private PropertyServiceImpl propertyService;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testCreateProperty_Success() {
        Long propertyIdNumber = 1000L;
        String address = "1203";
        int yearOfConstruction = 2000;
        PropertyType propertyType = PropertyType.DETACHED_HOUSE;
        Long propertyOwnerId = 1L;
        
        PropertyOwner owner = new PropertyOwner();
        owner.setId(propertyOwnerId);
        
        
        
    }

}

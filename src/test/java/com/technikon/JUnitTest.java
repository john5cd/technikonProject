package com.technikon;

import com.technikon.model.Property;
import com.technikon.model.PropertyType;
import com.technikon.repository.PropertyRepository;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class JUnitTest {
    private PropertyRepository propertyRepository;
    
    @BeforeEach
    public void setUp() {
        EntityManager entityManager = null;
        propertyRepository = new PropertyRepository(entityManager);
    }
    
    @Test
    public void testCreateProperty() {
        Property property = Property.builder()
                .propertyId(10001L)
                .address("123 AIj")
                .yearOfConstruction(2000)
                .propertyType(PropertyType.MAISONETTE)
                .isActive(true)
                .build();
        
        assertNotNull(property);
        assertEquals(10001L, property.getPropertyId());
        assertEquals("123 AIj", property.getAddress());
        assertEquals(2000, property.getYearOfConstruction());
        assertEquals(PropertyType.MAISONETTE, property.getPropertyType());
        assertTrue(property.getIsActive());
        
        
    }
    
    

}

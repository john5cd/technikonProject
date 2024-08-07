package com.technikon.util;

import com.technikon.model.Property;
import com.technikon.model.PropertyType;
import com.technikon.services.PropertyService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class PropertyCSVImporter {
    private final PropertyService propertyService;
    private static final String PROPERTIES_FILE = "src/main/resources/property.csv";
    
    public PropertyCSVImporter(PropertyService propertyService) {
        this.propertyService = propertyService;
    }
    
    public void importPropertiesFromCSV() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(PROPERTIES_FILE))) {
            String line;
            boolean isHeader = true;
            
            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                
                try {
                    String[] values = line.split(",");
                    Long propertyIdNumber = Long.parseLong(values[0]);
                    String address = values[1];
                    int yearOfConstruction = Integer.parseInt(values[2]);
                    PropertyType propertyType = PropertyType.valueOf(values[3]);
                    Long propertyOwnerId = Long.parseLong(values[4]);
                    
                    Property property = propertyService.createProperty(propertyIdNumber, address, yearOfConstruction, propertyType, propertyOwnerId);
                    System.out.println("Property added: " + property);
                    
                } catch(Exception e) {
                    System.out.println("Error processing line: " + e.getMessage());
                }
            }
        }
        System.out.println("CSV data imported successfully!");
    }
}

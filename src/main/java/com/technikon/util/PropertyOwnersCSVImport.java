package com.technikon.util;

import com.technikon.model.PropertyOwner;
import com.technikon.services.PropertyOwnerService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PropertyOwnersCSVImport {
    private final PropertyOwnerService propertyOwnerService;
    private static final String PROPERTIES_FILE = "src/main/resources/owners.csv";
    
    public PropertyOwnersCSVImport(PropertyOwnerService propertyOwnerService){
        this.propertyOwnerService = propertyOwnerService;
    }
    
    public void importOwnersCSV() throws IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(PROPERTIES_FILE))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null){
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                try{
                    String[] values = line.split(",");
                    Long vaNumber = Long.parseLong(values[0]);
                    String firstName = values[1];
                    String lastname = values[2];
                    String address = values[3];
                    Long phoneNumber = Long.parseLong(values[4]);
                    String email = values[5];
                    String userName = values[6];
                    String pass = values[7];
                    
                    PropertyOwner propertyowner = propertyOwnerService.create(firstName, lastname, email, userName, phoneNumber.toString(), address, vaNumber.toString(), pass);
                }
                catch(Exception e) {
                    System.out.println("Error processing line: " + e.getMessage());
                }
            }
        }
        System.out.println("CSV data imported successfully!");
    }
}

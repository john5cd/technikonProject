package com.technikon.util;

import com.technikon.model.PropertyOwner;
import com.technikon.model.Property;
import com.technikon.model.PropertyRepair;
import com.technikon.model.StatusOfRepairEnum;
import com.technikon.model.TypeOfRepairEnum;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.repository.PropertyRepairRepository;
import com.technikon.repository.PropertyRepository;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class PropertyRepairCSVImport {

    private final PropertyRepairRepository propertyRepairRepository;
    private final PropertyRepository propertyRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    private static final String PROPERTIES_FILE = "src/main/resources/property_repair_data.csv";

    public PropertyRepairCSVImport(PropertyRepairRepository propertyRepairRepository, PropertyRepository propertyRepository, PropertyOwnerRepository propertyOwnerRepository) {
        this.propertyRepairRepository = propertyRepairRepository;
        this.propertyRepository = propertyRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
    }

    public void propertyRepairImportFromCSV() throws IOException {
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

                    Long propertyOwnerId = Long.parseLong(values[0]);
                    Long propertyId = Long.parseLong(values[1]);
                    TypeOfRepairEnum typeOfRepairEnum = TypeOfRepairEnum.valueOf(values[2]);
                    String shortDescription = values[3];
                    LocalDate submissionDate = LocalDate.parse(values[4]);
                    String description = values[5];
                    LocalDate proposedStartDate = LocalDate.parse(values[6]);
                    LocalDate proposedEndDate = LocalDate.parse(values[7]);
                    int proposedCost = Integer.parseInt(values[8]);
                    boolean ownerAcceptance = Boolean.parseBoolean(values[9]);
                    StatusOfRepairEnum status = StatusOfRepairEnum.valueOf(values[10]);
                    LocalDate actualStartDate = LocalDate.parse(values[11]);
                    LocalDate actualEndDate = LocalDate.parse(values[12]);
                    boolean isActive = Boolean.parseBoolean(values[13]);

                    Optional<PropertyOwner> propertyOwner = propertyOwnerRepository.findById(propertyOwnerId);
                    Optional<Property> property = propertyRepository.findById(propertyId);

                    if (!propertyOwner.isPresent() || !property.isPresent()) {
                        throw new IllegalArgumentException("PropertyOwner or Property not found");
                    }

                    PropertyRepair propertyRepair = PropertyRepair.builder()
                            .propertyOwner(propertyOwner.get())
                            .property(property.get())
                            .typeOfRepair(typeOfRepairEnum)
                            .shortDescription(shortDescription)
                            .submissionDate(submissionDate)
                            .description(description)
                            .proposedStartDate(proposedStartDate)
                            .proposedEndDate(proposedEndDate)
                            .proposedCost(proposedCost)
                            .ownerAcceptance(ownerAcceptance)
                            .status(status)
                            .actualStartDate(actualStartDate)
                            .actualEndDate(actualEndDate)
                            .isActive(isActive)
                            .build();

                    propertyRepairRepository.create(propertyRepair);
                    System.out.println("Property Repair Added:" + propertyRepair);

                } catch (Exception e) {
                    System.out.println("Error processing line: " + e.getMessage());
                }
            }
        }
        System.out.println("CSV data imported successfully!");
    }
}

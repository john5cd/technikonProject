package com.technikon;

import com.technikon.exception.OwnerNotFoundException;
import com.technikon.model.Property;
import com.technikon.model.PropertyOwner;
import com.technikon.model.PropertyRepair;
import com.technikon.model.PropertyType;
import com.technikon.model.StatusOfRepairEnum;
import com.technikon.model.TypeOfRepairEnum;
import com.technikon.repository.AdminRepository;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.repository.PropertyRepairRepository;
import com.technikon.repository.PropertyRepository;
import com.technikon.services.AdminService;
import com.technikon.services.AdminServiceImpl;
import com.technikon.services.PropertyOwnerService;
import com.technikon.services.PropertyOwnerServiceImpl;
import com.technikon.services.PropertyRepairService;
import com.technikon.services.PropertyRepairServiceImpl;
import com.technikon.services.PropertyService;
import com.technikon.services.PropertyServiceImpl;
import com.technikon.util.PropertyCSVImporter;
import com.technikon.util.PropertyOwnersCSVImport;
import com.technikon.util.PropertyRepairCSVImport;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RunExamples {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CompanyPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        PropertyOwnerRepository propertyOwnerRepository = new PropertyOwnerRepository(entityManager);
        PropertyOwnerService propertyOwnerService = new PropertyOwnerServiceImpl(propertyOwnerRepository);

        PropertyRepository propertyRepository = new PropertyRepository(entityManager);
        PropertyService propertyService = new PropertyServiceImpl(propertyRepository, propertyOwnerRepository);

        PropertyRepairRepository propertyRepairRepository = new PropertyRepairRepository(entityManager);
        PropertyRepairService propertyRepairService = new PropertyRepairServiceImpl(propertyOwnerRepository, propertyRepository, propertyRepairRepository);
        
        AdminRepository adminRepository = new AdminRepository(entityManager);
        AdminService adminService = new AdminServiceImpl(adminRepository, propertyOwnerRepository, propertyRepository, propertyRepairRepository, entityManager);
        
        
        PropertyOwnersCSVImport ownersCSVImport = new PropertyOwnersCSVImport(propertyOwnerService);
        PropertyCSVImporter cSVImporter = new PropertyCSVImporter(propertyService);
        PropertyRepairCSVImport propertyRepairCSVImport = new PropertyRepairCSVImport(propertyRepairRepository, propertyRepository, propertyOwnerRepository);
        

        //use case 4.1
        
        
        
//        try {
//            ownersCSVImport.importOwnersCSV();
//        }
//        catch (IOException e){
//            System.out.println(e.getMessage());
//        }
//        
//        try {
//            cSVImporter.importPropertiesFromCSV();
//        }
//        catch (IOException e){
//            System.out.println(e.getMessage());
//        }
//        
//        try {
//            propertyRepairCSVImport.propertyRepairImportFromCSV();
//        }
//        catch (IOException e){
//            System.out.println(e.getMessage());
//        }
//-----------------------------------------------------------------------------------------
         //use case 4.2
         //create Owner
        
//        PropertyOwner propertyOwner = propertyOwnerService.create("johnysaa", "johnaa", "jjjaa@gmail.com", "johnJ", "6973366983", "wall street", "5555565668", "aAs35!@");
//        System.out.println("Welcome " + propertyOwner.getFirstName());
//        
//
//        //create Repairs
//        //Input from Owner
//        //1
//        Property property = propertyService.createProperty(1004L, "wall street 2", 2000, PropertyType.MAISONETTE, 4L);
//        //2
//        Property property1 = propertyService.createProperty(1005L, "wall street 3", 2004, PropertyType.DETACHED_HOUSE, 4L);
//        System.out.println("New property created" + property);
//        System.out.println("New property created" + property1);
//        //Update Property on wrogly inserted data 
//        Property updateProperty = propertyService.updateProperty(4L, 1004L, "wallStreet 20", 0, PropertyType.MAISONETTE, null);
//        System.out.println("Your Update:" + updateProperty);

//------------------------------------------------------------------------------------------
                    //use case 4.3
//        try {
//            PropertyOwner owner = propertyOwnerService.get(1L);
//            List<Property> properties = owner.getProperties();
//            
//            // Print the properties
//            System.out.println("Properties of owner with ID " + owner.getId() + ":");
//            for (Property property : properties) {
//                System.out.println("Property ID: " + property.getId());
//                System.out.println("Property ID Number: " + property.getPropertyId());
//                System.out.println("Address: " + property.getAddress());
//                System.out.println("Year of Construction: " + property.getYearOfConstruction());
//                System.out.println("Property Type: " + property.getPropertyType());
//                System.out.println("Is Active: " + property.getIsActive());
//                System.out.println("----");
//            }
//        } catch (OwnerNotFoundException e) {
//            System.out.println("Owner not found: " + e.getMessage());
//        }
        
//        Long ownerId = 1L;
//        Long propertyId = 1L;
//        try {
//            propertyRepairService.initiateRepair(ownerId, propertyId, TypeOfRepairEnum.ELECTRICALWORK, "Painting", "Painting the living room wall");
//            System.out.println("Repair initiated successfully!");
//        } catch(OwnerNotFoundException e){
//            System.out.println("Error initiating repair" + e.getMessage());
//        }


           //USE CASE 4.4
           //get all pending repairs
//           List<PropertyRepair> pendingRepairs = adminService.getAllPendingRepairs();
//            System.out.println("Pending Repairs: " + pendingRepairs);
//            
//            LocalDate proposedStartDate = LocalDate.now().plusDays(1);
//            LocalDate proposedEndDate = LocalDate.now().plusDays(10);
//            int proposedCost = 5000;
//            adminService.repairProposition(4L, "PENDING", proposedStartDate, proposedEndDate, proposedCost);
            
//          Optional<PropertyRepair> propertyRepair = propertyRepairRepository.findById(4L);
//          propertyRepair.get().setOwnerAcceptance(true);
//          propertyRepairService.acceptRepair(propertyRepair.get());
//          System.out.println(propertyRepair.get());
          
            
//--------------------------------------------------------------------------------------------------
        //USE CASE 4.5
        //Get all Properties 
        //first search by id
//        PropertyOwner searchOwner = propertyOwnerService.get(1L);
//        System.out.println("Owner:" + searchOwner);
//        //find all properties
//        List<Property> allProperties = propertyRepository.findAll();
//        System.out.println("All properties:" + allProperties);
//        //show all properties with Owners Id
//        System.out.println("Your Properties:");
//        for (Property property : allProperties) {
//            if (property.getPropertyOwner().getId().equals(searchOwner.getId())) {
//                System.out.println("-------------");
//                System.out.println("" + property);
//
//                List<PropertyRepair> propertyRepairs = property.getPropertyRepairs();
//        if (propertyRepairs != null && !propertyRepairs.isEmpty()) {
//            System.out.println("Repairs for property " + property.getPropertyId() + ":");
//            for (PropertyRepair repair : propertyRepairs) {
//                System.out.println("Repair ID: " + repair.getRepairId() + ", Status: " + repair.getStatus());
//            }
//        } else {
//            System.out.println("No repairs found for property " + property.getPropertyId());
//        }
//            }
//        }
//        
        // Get and print active repairs
//        List<PropertyRepair> activeRepairs = adminService.getActiveRepairs();
//        System.out.println("Active Repairs:");
//        for (PropertyRepair repair : activeRepairs) {
//            System.out.println(repair);
//        }
//
//        // Get and print inactive repairs
//        List<PropertyRepair> inactiveRepairs = adminService.getInactiveRepairs();
//        System.out.println("Inactive Repairs:");
//        for (PropertyRepair repair : inactiveRepairs) {
//            System.out.println(repair);
//        }
                     
        //propertyOwnerService.create("sam", "duhaw", "sam@gmail.com", "sammy", "6911122233", "ermou 12", "1111122222", "aAs12!@");
        
        

        //makeRepair(propertyRepairService, propertyOwnerService, propertyService);

        //updateRepair(propertyRepairService, propertyOwnerService, propertyService, propertyRepairRepository);

        //searchRepair(propertyRepairService, propertyRepairRepository);

        //deleteRepair(propertyRepairService, propertyOwnerService, propertyService);
        
        //soft delete use case
//        Long propertyId = 4L;
//        propertyService.softDeleteProperty(propertyId);

//        try {
//            PropertyOwner owner = propertyOwnerService.get(4L);
//            List<Property> properties = owner.getProperties();
//            
//            // Print the properties
//            System.out.println("Properties of owner with ID " + owner.getId() + ":");
//            for (Property property : properties) {
//                System.out.println("Property ID: " + property.getId());
//                System.out.println("Property ID Number: " + property.getPropertyId());
//                System.out.println("Address: " + property.getAddress());
//                System.out.println("Year of Construction: " + property.getYearOfConstruction());
//                System.out.println("Property Type: " + property.getPropertyType());
//                System.out.println("Is Active: " + property.getIsActive());
//                System.out.println("----");
//            }
//        } catch (OwnerNotFoundException e) {
//            System.out.println("Owner not found: " + e.getMessage());
//        }
        
        List<Property> properties= propertyService.findByOwnerVatNumber(5555565668L);
        
        for (Property property : properties) {
            System.out.println("Owner with id 4: " );
                System.out.println("Property ID: " + property.getId());
                System.out.println("Property ID Number: " + property.getPropertyId());
                System.out.println("Address: " + property.getAddress());
                System.out.println("Year of Construction: " + property.getYearOfConstruction());
                System.out.println("Property Type: " + property.getPropertyType());
                System.out.println("Is Active: " + property.getIsActive());
                System.out.println("----");
        }
    }

    private static void searchRepair(PropertyRepairServiceImpl propertyRepairService, PropertyRepairRepository propertyRepairRepository) {
        Optional<PropertyRepair> propertyRepair = propertyRepairRepository.findById(1L);
        System.out.println(propertyRepairService.searchRepairsBySubmissionDate(propertyRepair.get().getSubmissionDate()));

   }

    private static void updateRepair(PropertyRepairServiceImpl propertyRepairService, PropertyOwnerServiceImpl propertyOwnerService, PropertyServiceImpl propertyService, PropertyRepairRepository propertyRepairRepository) {

        System.out.println(propertyRepairRepository.findById(1L).get());

        propertyRepairService.updateRepair(propertyRepairRepository.findById(1L).get(), "hey how", 3);
    }

    private static void deleteRepair(PropertyRepairServiceImpl propertyRepairService, PropertyOwnerServiceImpl propertyOwnerService, PropertyServiceImpl propertyService) {
    }

    private static void makeRepair(PropertyRepairServiceImpl propertyRepairService, PropertyOwnerServiceImpl propertyOwnerService, PropertyServiceImpl propertyService) {
        PropertyOwner propertyOwner = propertyOwnerService.get(1L);
        Optional<Property> property = propertyService.findByPropertyIdNumber(1231231231L);

        System.out.println(propertyOwner);
        System.out.println(property.get());
        try {
            propertyRepairService.initiateRepair(propertyOwner.getId(), property.get().getId(), TypeOfRepairEnum.PAINTING, "paint", "painting");
        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }
}

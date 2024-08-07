package com.technikon;

import com.technikon.model.Property;
import com.technikon.model.PropertyOwner;
import com.technikon.model.PropertyRepair;
import com.technikon.model.TypeOfRepairEnum;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.repository.PropertyRepairRepository;
import com.technikon.repository.PropertyRepository;
import com.technikon.services.PropertyOwnerServiceImpl;
import com.technikon.services.PropertyRepairServiceImpl;
import com.technikon.services.PropertyServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RunExamples {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CompanyPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        PropertyOwnerRepository propertyOwnerRepository = new PropertyOwnerRepository(entityManager);
        PropertyOwnerServiceImpl propertyOwnerService = new PropertyOwnerServiceImpl(propertyOwnerRepository);

        PropertyRepository propertyRepository = new PropertyRepository(entityManager);
        PropertyServiceImpl propertyService = new PropertyServiceImpl(propertyRepository, propertyOwnerRepository);

        PropertyRepairRepository propertyRepairRepository = new PropertyRepairRepository(entityManager);
        PropertyRepairServiceImpl propertyRepairService = new PropertyRepairServiceImpl(propertyOwnerRepository, propertyRepository, propertyRepairRepository);

        //makeRepair(propertyRepairService, propertyOwnerService, propertyService);

        //updateRepair(propertyRepairService, propertyOwnerService, propertyService, propertyRepairRepository);

        //searchRepair(propertyRepairService, propertyRepairRepository);

        //deleteRepair(propertyRepairService, propertyOwnerService, propertyService);


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

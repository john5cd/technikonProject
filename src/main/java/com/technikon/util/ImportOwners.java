package com.technikon.util;

import com.technikon.model.PropertyOwner;
import com.technikon.repository.PropertyOwnerRepository;
import com.technikon.services.PropertyOwnerServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class ImportOwners {
    public static void main(String[] args) {

        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CompanyPU");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            PropertyOwnerRepository propertyOwnerRepository = new PropertyOwnerRepository(entityManager);
            PropertyOwnerServiceImpl propertyOwnerService = new PropertyOwnerServiceImpl(propertyOwnerRepository);

            BufferedReader bufferedReader = new BufferedReader(new FileReader("source/main/resources/owners.csv"));
            ArrayList<String[]> owners = new ArrayList<>();

            bufferedReader.lines().distinct().map(l -> l.split(", ")).forEach(t -> owners.add(t));
            owners.removeIf(t -> t.length!=8);
            owners.removeIf(t -> Arrays.asList(t).contains(" ") || Arrays.asList(t).contains("firstName") || Arrays.asList(t).contains("") || Arrays.asList(t).contains("null"));

            HashSet<PropertyOwner> newOwner = new HashSet<>();
            owners.forEach(t -> {propertyOwnerService.create(t[0], t[1], t[3], t[2], t[7], t[5], t[6], t[4]);
            });

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}

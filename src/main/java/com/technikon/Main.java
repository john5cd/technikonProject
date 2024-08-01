package com.technikon;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CompanyPU");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
    }
}
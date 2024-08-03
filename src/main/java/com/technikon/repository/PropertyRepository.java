package com.technikon.repository;

import com.technikon.model.Property;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class PropertyRepository implements Repository<Property> {

    private EntityManager entityManager;

    public PropertyRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Property create(Property property) {
        entityManager.getTransaction().begin();
        entityManager.persist(property);
        entityManager.getTransaction().commit();
        return property;
    }

    @Override
    public void update(Property property) {
        entityManager.getTransaction().begin();
        entityManager.merge(property);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(Property property) {
        entityManager.getTransaction().begin();
        entityManager.remove(property);
        entityManager.getTransaction().commit();
    }

    @Override
    public <V> Optional<Property> findById(V v) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Property> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

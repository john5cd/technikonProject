package com.technikon.repository;

import com.technikon.model.Property;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public <V> Optional<Property> findById(V id) {
        try {
            entityManager.getTransaction().begin();
            Property property = entityManager.find(Property.class, id);
            entityManager.getTransaction().commit();
            return Optional.of(property);
        } catch (Exception e) {
            log.debug("Property not found");
        }
        return Optional.empty();
    }

    @Override
    public List<Property> findAll() {
        TypedQuery<Property> query =
                entityManager.createQuery("SELECT po FROM Property po", Property.class);
        return query.getResultList();
    }

}

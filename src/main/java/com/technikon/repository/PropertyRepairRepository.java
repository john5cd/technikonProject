package com.technikon.repository;

import com.technikon.model.PropertyRepair;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyRepairRepository implements Repository<PropertyRepair> {

    private EntityManager entityManager;

    public PropertyRepairRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PropertyRepair create(PropertyRepair propertyRepair) {
        entityManager.getTransaction().begin();
        entityManager.persist(propertyRepair);
        entityManager.getTransaction().commit();
        return propertyRepair;
    }

    @Override
    public void update(PropertyRepair v) {
        if (v instanceof PropertyRepair) {
            PropertyRepair repair = (PropertyRepair) v;
            entityManager.getTransaction().begin();
            entityManager.merge(repair);//Ενημερώνει τον πίνακα με τα νέα δεδομένα 
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public void delete(PropertyRepair propertyRepair) {
        entityManager.getTransaction().begin();
        entityManager.remove(propertyRepair);
        entityManager.getTransaction().commit();
    }

    /*Δημιουργεί ένα ερώτημα που αναζητά εγγραφές εντός του εύρους startDate και endDate.
    Χρησιμοποιεί το setParameter() για να θέσει τις τιμές των παραμέτρων.*/
    public List<PropertyRepair> searchByDateRange(Date startDate, Date endDate) {
        TypedQuery<PropertyRepair> query;
        query = entityManager.createQuery(
                "SELECT r FROM PropertyRepair r WHERE r.submissionDate BETWEEN :startDate AND :endDate", PropertyRepair.class);
        query.setParameter("startDate", startDate, TemporalType.TIMESTAMP);
        query.setParameter("endDate", endDate, TemporalType.TIMESTAMP);
        return query.getResultList();
    }
    
    public List<PropertyRepair> searchBySubmissionDate(Date submissionDate) {
        TypedQuery<PropertyRepair> query = entityManager.createQuery(
                "SELECT r FROM PropertyRepair r WHERE r.submissionDate = :submissionDate", PropertyRepair.class);
        query.setParameter("submissionDate", submissionDate, TemporalType.TIMESTAMP);
        return query.getResultList();
    }

    public List<PropertyRepair> searchByOwnerId(Long ownerId) {
        TypedQuery<PropertyRepair> query = entityManager.createQuery(
                "SELECT r FROM PropertyRepair r WHERE r.ownerId = :ownerId", PropertyRepair.class);
        query.setParameter("ownerId", ownerId);
        return query.getResultList();
    }

    @Override
    public List<PropertyRepair> findAll() {
        TypedQuery<PropertyRepair> query =
                entityManager.createQuery("SELECT po FROM PropertyRepair po", PropertyRepair.class);
        return query.getResultList();
    }

    @Override
    public <V> Optional<PropertyRepair> findById(V id) {
        try {
            entityManager.getTransaction().begin();
            PropertyRepair repair = entityManager.find(PropertyRepair.class, id);
            entityManager.getTransaction().commit();
            return Optional.of(repair);
        } catch (Exception e) {
            log.debug("Property's repair not found");
        }
        return Optional.empty();
    }

}

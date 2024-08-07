package com.technikon.repository;

import com.technikon.exception.OwnerNotFoundException;
import com.technikon.model.PropertyOwner;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class PropertyOwnerRepository implements Repository<PropertyOwner> {

    private EntityManager entityManager;

    @Override
    public PropertyOwner create(PropertyOwner propertyOwner) throws PersistenceException {
        entityManager.getTransaction().begin();
        entityManager.persist(propertyOwner);
        entityManager.getTransaction().commit();
        return propertyOwner;
    }

    @Override
    public void update(PropertyOwner propertyOwner) throws PersistenceException {
        entityManager.getTransaction().begin();
        entityManager.merge(propertyOwner);
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(PropertyOwner propertyOwner) throws IllegalArgumentException, NullPointerException{
        entityManager.getTransaction().begin();
        entityManager.remove(propertyOwner);
        entityManager.getTransaction().commit();
    }

    @Override
    public <V> Optional<PropertyOwner> findById(V id) {
        try {
            entityManager.getTransaction().begin();
            PropertyOwner owner = entityManager.find(PropertyOwner.class, id);
            entityManager.getTransaction().commit();
            if (owner.getIsActive()==false) {
                return Optional.empty();
            }
            return Optional.of(owner);
        } catch (Exception e) {
            log.debug("Owner not found");
        }
        return Optional.empty();
    }

    @Override
    public List<PropertyOwner> findAll() {
        TypedQuery<PropertyOwner> query
                = entityManager.createQuery("SELECT po FROM PropertyOwner po WHERE po.isActive=true", PropertyOwner.class);
        return query.getResultList();
    }

    public PropertyOwner searchByEmail(String email) {

        List<PropertyOwner> owner = entityManager.createQuery("SELECT po FROM PropertyOwner po WHERE po.email LIKE: givenEmail")
                .setParameter("givenEmail", email)
                .getResultList();

        if (owner.isEmpty()) {
            throw new OwnerNotFoundException("This is not an existing owner");
        }

        if (owner.get(0).getIsActive()==false) {
            throw new OwnerNotFoundException("This is not an existing owner");
        }

        return (PropertyOwner) owner.get(0);
    }

    public PropertyOwner searchByVat(Long vat) {

        List<PropertyOwner> owner = entityManager.createQuery("SELECT po FROM PropertyOwner po WHERE po.vat LIKE ?1")
                .setParameter(1, vat)
                .getResultList();

        if (owner.isEmpty()) {
            throw new OwnerNotFoundException("This is not an existing owner");
        }

        if (owner.get(0).getIsActive()==false) {
            throw new OwnerNotFoundException("This is not an existing owner");
        }

        return (PropertyOwner) owner.get(0);
    }
}

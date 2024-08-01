package com.technikon.repository;

import com.technikon.exception.OwnerNotFoundException;
import com.technikon.model.PropertyOwner;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
public class PropertyOwnerRepository implements Repository<PropertyOwner> {

    private EntityManager entityManager;

    private final Map<Long, PropertyOwner> propertyOwners = new HashMap<>();


    @Override
    public PropertyOwner create(PropertyOwner propertyOwner) {
        if (propertyOwner == null || propertyOwners.containsKey(propertyOwner.getId())) {
            throw new IllegalArgumentException("PropertyOwner is null or already exists");
        }
        propertyOwners.put(propertyOwner.getId(), propertyOwner);
        return propertyOwner;
    }


    @Override
    public  void update(PropertyOwner propertyOwner) {
            entityManager.getTransaction().begin();
            entityManager.merge(propertyOwner);
            entityManager.getTransaction().commit();
    }

    @Override
    public void delete(PropertyOwner propertyOwner) {
        entityManager.getTransaction().begin();
        entityManager.remove(propertyOwner);
        entityManager.getTransaction().commit();
    }

    @Override
    public <V> Optional<PropertyOwner> findById(V v) {
        return Optional.empty();
    }

    @Override
    public List<PropertyOwner> findAll() {
        return null;
    }

    public PropertyOwner searchByEmail (String email) {

        List owner = entityManager.createQuery("SELECT po FROM PropertyOwner po WHERE po.email LIKE: givenEmail")
                .setParameter("givenEmail", email)
                .getResultList();

        if (owner.isEmpty()) {
            throw new OwnerNotFoundException("This is not an existing owner");
        }

        return (PropertyOwner) owner.get(0);
    }

    public PropertyOwner searchByVat (Long vat) {

        List owner = entityManager.createQuery("SELECT po FROM PropertyOwner po WHERE po.vat LIKE ?1")
                .setParameter(1, vat)
                .getResultList();

        if (owner.isEmpty()) {
            throw new OwnerNotFoundException("This is not an existing owner");
        }
        return (PropertyOwner) owner.get(0);
    }
}

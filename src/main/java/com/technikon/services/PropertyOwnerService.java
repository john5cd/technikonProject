
package com.technikon.services;


import com.technikon.model.PropertyOwner;

import java.util.List;

public interface PropertyOwnerService {

    PropertyOwner get(Long id);

    PropertyOwner create(String firstName,
                         String lastName,
                         String email,
                         String userName,
                         String phoneNumber,
                         String address,
                         String vat,
                         String password);

    void delete(Long id);

    void update(PropertyOwner Owner);
    
}

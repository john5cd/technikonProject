package com.technikon.services;

import com.technikon.model.Admin;
import com.technikon.model.Property;
import com.technikon.model.PropertyRepair;
import java.time.LocalDate;
import java.util.List;

public interface AdminService {
    Admin createAdmin(Admin admin);
    Admin changeAdmin(String username, String password);
    String getAdminUsername();
    List<PropertyRepair> getAllRepairs();
    List<Property> getAllProperties();
    PropertyRepair repairProposition(Long repairId, String newStatus,  LocalDate proposedStartDate, LocalDate proposedEndDate, int proposedCost);
    List<PropertyRepair> getActiveRepairs();
    List<PropertyRepair> getInactiveRepairs();
    List<PropertyRepair> getAllPendingRepairs();
}

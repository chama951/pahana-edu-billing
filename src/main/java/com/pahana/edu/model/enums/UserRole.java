package com.pahana.edu.model.enums;

import java.util.EnumSet;
import java.util.Set;

public enum UserRole {
	
    ADMIN("Manage everything", 
        EnumSet.of(
            Privilege.ALL
            // All other privileges...
        )),
        
    CASHIER("Manage customers",
        EnumSet.of(
            Privilege.CREATE_BILLS,
            Privilege.VIEW_BILLS,
            Privilege.CANCEL_BILLS
            // Clerk privileges...
        )),
        
    INVENTORY_MANAGER("Manage product inventory",
        EnumSet.of(
            Privilege.MANAGE_ITEMS
            // Inventory privileges...
        ));
    
    private final String description;
    private final Set<Privilege> privileges;
    
    UserRole(String description, Set<Privilege> privileges) {
        this.description = description;
        this.privileges = privileges;
    }
    
    public boolean hasPrivilege(Privilege privilege) {
        return privileges.contains(privilege);
    }
    
    public String getDescription() {
        return description;
    }
    
    public Set<Privilege> getPrivileges() {
        return privileges;
    }
}

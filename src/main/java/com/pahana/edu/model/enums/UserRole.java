package com.pahana.edu.model.enums;

import java.util.EnumSet;
import java.util.Set;

public enum UserRole {
	
    ADMIN("Manage everything", 
        EnumSet.of(
            Privilege.MANAGE_USERS,
            Privilege.SYSTEM_CONFIG
            // All other privileges...
        )),
        
    CLERK("Handle customers and billing",
        EnumSet.of(
            Privilege.VIEW_CUSTOMERS,
            Privilege.CREATE_CUSTOMERS
            // Clerk privileges...
        )),
        
    INVENTORY_MANAGER("Manage product inventory",
        EnumSet.of(
            Privilege.VIEW_ITEMS,
            Privilege.CREATE_ITEMS
            // Inventory privileges...
        )),
        
    GUEST("View-only access",
        EnumSet.of(
            Privilege.VIEW_ITEMS,
            Privilege.VIEW_CUSTOMERS
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

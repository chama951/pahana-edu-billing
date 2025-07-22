package com.pahana.edu.model.enums;

public enum Privilege {
	
	ALL("All Privileges"),

    MANAGE_CUSTOMERS("manage:customers"),
    
    MANAGE_ITEMS("manage:items"),
    
    CREATE_BILLS("create:bills"),
    VIEW_BILLS("view:bills"),
    CANCEL_BILLS("cancel:bills"),
    
    MANAGE_USERS("manage:users"),
    
    VIEW_REPORTS("view:reports");
    
    private final String permission;
    
    Privilege(String permission) {
        this.permission = permission;
    }
    
    public String getPermission() {
        return permission;
    }
}

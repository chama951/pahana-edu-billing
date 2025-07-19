package com.pahana.edu.model.enums;

public enum Privilege {

    VIEW_CUSTOMERS("view:customers"),
    CREATE_CUSTOMERS("create:customers"),
    EDIT_CUSTOMERS("edit:customers"),
    DELETE_CUSTOMERS("delete:customers"),
    
    VIEW_ITEMS("view:items"),
    CREATE_ITEMS("create:items"),
    EDIT_ITEMS("edit:items"),
    DELETE_ITEMS("delete:items"),
    
    CREATE_BILLS("create:bills"),
    VIEW_BILLS("view:bills"),
    CANCEL_BILLS("cancel:bills"),
    
    MANAGE_USERS("manage:users"),
    VIEW_REPORTS("view:reports"),
    SYSTEM_CONFIG("system:config");
    
    private final String permission;
    
    Privilege(String permission) {
        this.permission = permission;
    }
    
    public String getPermission() {
        return permission;
    }
}

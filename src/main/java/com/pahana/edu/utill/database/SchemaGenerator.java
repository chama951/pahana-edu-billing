package com.pahana.edu.utill.database;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SchemaGenerator {
    public static void main(String[] args) {
        // This will trigger schema generation
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("schema-generation-pu");
        emf.createEntityManager();
              
        System.out.println("Schema generation complete. Check src/main/resources/schema.sql");
    }
}
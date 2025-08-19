package com.pahana.edu.utill;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SchemaGenerator {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("schema-generation-pu");
		emf.createEntityManager();

		System.out.println("Schema generation complete. Check src/main/resources/schema.sql");
	}
}
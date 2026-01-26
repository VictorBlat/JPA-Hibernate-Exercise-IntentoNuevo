package org.ieselgrao.hibernatepractica.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DAOController {
    private static EntityManagerFactory emf;

    public static void init(String unitName) {
        if (emf != null) {
            emf.close();
        }
        emf = Persistence.createEntityManagerFactory(unitName);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            throw new IllegalStateException("DAOController no inicializado. Llama a init() primero.");
        }
        return emf;
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            emf = null;
        }
    }
}
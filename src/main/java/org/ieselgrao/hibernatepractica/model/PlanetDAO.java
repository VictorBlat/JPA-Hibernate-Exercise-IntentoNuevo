package org.ieselgrao.hibernatepractica.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class PlanetDAO {

    public EntityManager getEntityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

    public List<Planet> obtenerTodos(EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        List<Planet> lista = em.createQuery("select p from Planet p", Planet.class).getResultList();
        em.close();
        return lista;
    }

    public Planet obtenerPorId(int id, EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        Planet p = em.find(Planet.class, id);
        em.close();
        return p;
    }

    public void guardar(Planet p, EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
    }

    public void actualizar(Planet p, EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
        em.close();
    }

    public void eliminar(int id, EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        em.getTransaction().begin();
        Planet p = em.find(Planet.class, id);
        if (p != null) {
            em.remove(p);
        }
        em.getTransaction().commit();
        em.close();
    }
}
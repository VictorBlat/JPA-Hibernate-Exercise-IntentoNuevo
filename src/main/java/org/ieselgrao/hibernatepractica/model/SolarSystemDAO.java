package org.ieselgrao.hibernatepractica.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class SolarSystemDAO {

    public EntityManager getEntityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

    public List<SolarSystem> obtenerTodos(EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        List<SolarSystem> lista = em.createQuery("select s from SolarSystem s", SolarSystem.class).getResultList();
        em.close();
        return lista;
    }

    public SolarSystem obtenerPorId(int id, EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        SolarSystem s = em.find(SolarSystem.class, id);
        em.close();
        return s;
    }

    public void guardar(SolarSystem sS, EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        em.getTransaction().begin();
        em.persist(sS);
        em.getTransaction().commit();
        em.close();
    }

    public void actualizar(SolarSystem sS, EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        em.getTransaction().begin();
        em.merge(sS);
        em.getTransaction().commit();
        em.close();
    }

    public void eliminar(int id, EntityManagerFactory emf) {
        EntityManager em = getEntityManager(emf);
        em.getTransaction().begin();
        SolarSystem sS = em.find(SolarSystem.class, id);
        if (sS != null) {
            em.remove(sS);
        }
        em.getTransaction().commit();
        em.close();
    }
}
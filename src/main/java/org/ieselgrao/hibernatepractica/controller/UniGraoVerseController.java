package org.ieselgrao.hibernatepractica.controller;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;
import jakarta.persistence.EntityManagerFactory;
import org.ieselgrao.hibernatepractica.model.*;

public class UniGraoVerseController {

    private LinkedList<SolarSystem> solarSystems;
    private SolarSystemDAO solarSystemDAO;
    private PlanetDAO planetDAO;
    private EntityManagerFactory emf;

    public UniGraoVerseController() {
        solarSystemDAO = new SolarSystemDAO();
        planetDAO = new PlanetDAO();
        emf = DAOController.getEntityManagerFactory();
        solarSystems = loadSolarSystems();
        loadPlanets();
    }

    // Load all solar systems from the selected persistence unit
    private LinkedList<SolarSystem> loadSolarSystems() {
        List<SolarSystem> sistemas = solarSystemDAO.obtenerTodos(emf);

        // Si no hay datos, inicializar con datos hardcoded
        if (sistemas.isEmpty()) {
            inicializarDatosHardcoded();
            sistemas = solarSystemDAO.obtenerTodos(emf);
        }

        return new LinkedList<>(sistemas);
    }

    // Load all planets from the selected persistence unit
    private void loadPlanets() {
        // Los planetas ya se cargan con los sistemas solares gracias a EAGER fetch
        for (SolarSystem ss : solarSystems) {
            System.out.println("Loaded " + ss.getPlanets().size() + " planets for " + ss.getName());
        }
    }

    private void inicializarDatosHardcoded() {
        // This method is "hardcoded" to have some initial data
        SolarSystem sistemaSolar = new SolarSystem("Sistema Solar", "Sol", 0, 40);
        sistemaSolar.addPlanet(new Planet("Mercurio", 0, 3.3011e23, 2439.7, 3.7, LocalDate.of(2023, 1, 15), false, sistemaSolar));
        sistemaSolar.addPlanet(new Planet("Venus", 0, 4.8675e24, 6051.8, 8.87, LocalDate.of(2023, 2, 20), false, sistemaSolar));
        sistemaSolar.addPlanet(new Planet("Tierra", 1, 5.972e24, 6371.0, 9.80, LocalDate.of(2024, 1, 1), false, sistemaSolar));
        sistemaSolar.addPlanet(new Planet("Marte", 2, 6.4171e23, 3389.5, 3.71, LocalDate.of(2023, 5, 5), false, sistemaSolar));
        sistemaSolar.addPlanet(new Planet("Júpiter", 95, 1.898e27, 69911.0, 24.79, LocalDate.of(2022, 11, 30), true, sistemaSolar));
        sistemaSolar.addPlanet(new Planet("Saturno", 146, 5.683e26, 58232.0, 10.44, LocalDate.of(2023, 10, 10), true, sistemaSolar));
        sistemaSolar.addPlanet(new Planet("Urano", 27, 8.681e25, 25362.0, 8.69, LocalDate.of(2023, 9, 25), true, sistemaSolar));
        sistemaSolar.addPlanet(new Planet("Neptuno", 16, 1.024e26, 24622.0, 11.15, LocalDate.of(2023, 7, 18), true, sistemaSolar));
        sistemaSolar.addPlanet(new Planet("Plutón", 5, 1.309e22, 1188.3, 0.62, LocalDate.of(2023, 4, 12), false, sistemaSolar));
        solarSystemDAO.guardar(sistemaSolar, emf);

        SolarSystem lichSystem = new SolarSystem("Lich system", "Lich", 700, 0.46);
        lichSystem.addPlanet(new Planet("Draugr", 0, 1.194e23, 1400.0, 0.45, LocalDate.of(2012, 1, 21), false, lichSystem));
        lichSystem.addPlanet(new Planet("Poltergeist", 1, 2.568e25, 9400.0, 12.8, LocalDate.of(2013, 1, 21), false, lichSystem));
        lichSystem.addPlanet(new Planet("Phobetor", 2, 2.329e25, 9100.0, 11.2, LocalDate.of(2014, 1, 21), false, lichSystem));
        lichSystem.addPlanet(new Planet("Lich-e", 3, 2.38e21, 450.0, 0.08, LocalDate.of(2002, 2, 10), false, lichSystem));
        solarSystemDAO.guardar(lichSystem, emf);

        SolarSystem sistemaInventado = new SolarSystem("Sistema Inventado", "Aitana", 100, 55);
        sistemaInventado.addPlanet(new Planet("Tatooine", 0, 3.3011e23, 2439.7, 3.7, LocalDate.of(2023, 1, 15), false, sistemaInventado));
        sistemaInventado.addPlanet(new Planet("Arrakis", 0, 4.8675e24, 6051.8, 8.87, LocalDate.of(2023, 2, 20), false, sistemaInventado));
        sistemaInventado.addPlanet(new Planet("Pandora", 1, 5.972e24, 6371.0, 9.80, LocalDate.of(2024, 1, 1), false, sistemaInventado));
        sistemaInventado.addPlanet(new Planet("Solaris", 2, 6.4171e23, 3389.5, 3.71, LocalDate.of(2023, 5, 5), false, sistemaInventado));
        solarSystemDAO.guardar(sistemaInventado, emf);
    }

    // Add a solar system to the controller. Also, call persistence!
    public void addSolarSystem(String name, String starName, double starDistance, double Radius) {
        SolarSystem nuevo = new SolarSystem(name, starName, starDistance, Radius);
        solarSystemDAO.guardar(nuevo, emf);
        solarSystems.add(nuevo);
    }

    // Update an existing planet in the controller. Also, call persistence!
    public boolean updatePlanet(int ID, String name, double mass, double Radius, double gravity, LocalDate date) {
        Planet planet = planetDAO.obtenerPorId(ID, emf);
        if (planet != null) {
            planet.setName(name);
            planet.setMass(mass);
            planet.setRadius(Radius);
            planet.setGravity(gravity);
            planet.setLastAlbedoMeasurement(date);
            planetDAO.actualizar(planet, emf);

            // Actualizar también en memoria
            for (SolarSystem solarSystem : solarSystems) {
                for (Planet p : solarSystem.getPlanets()) {
                    if (p.getId() == ID) {
                        p.setName(name);
                        p.setMass(mass);
                        p.setRadius(Radius);
                        p.setGravity(gravity);
                        p.setLastAlbedoMeasurement(date);
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    // Add planet and persist to database
    public void addPlanet(int solarSystemId, String name, double mass, double radius, double gravity, LocalDate lastAlbedoMeasurement) {
        SolarSystem ss = solarSystemDAO.obtenerPorId(solarSystemId, emf);
        if (ss != null) {
            Planet nuevoPlaneta = new Planet(name, mass, radius, gravity, lastAlbedoMeasurement);
            nuevoPlaneta.setSolarSystem(ss);
            planetDAO.guardar(nuevoPlaneta, emf);

            // Actualizar en memoria
            for (SolarSystem sistema : solarSystems) {
                if (sistema.getId() == solarSystemId) {
                    sistema.addPlanet(nuevoPlaneta);
                    System.out.println("Added new planet to Solar System " + solarSystemId + ". Current size is: " + sistema.getPlanets().size());
                    break;
                }
            }
        } else {
            System.err.println("Could not find solar system with id: " + solarSystemId);
        }
    }

    // Remove Planets from Java and from persistence
    public void removePlanet(int planetId) {
        planetDAO.eliminar(planetId, emf);

        // Eliminar también de memoria
        for (SolarSystem ss : solarSystems) {
            ss.getPlanets().removeIf(p -> p.getId() == planetId);
        }
    }

    public void removeSolarSystem(int solarSystemId) {
        solarSystemDAO.eliminar(solarSystemId, emf);
        solarSystems.removeIf(ss -> ss.getId() == solarSystemId);
    }

    public List<String> getSolarSystemsData() {
        List<String> solarSystemsData = new ArrayList<>();
        Gson gson = new Gson();
        for (SolarSystem s : solarSystems) {
            Map<String, String> data = new HashMap<>();
            data.put("id", String.valueOf(s.getId()));
            data.put("name", s.getName());
            data.put("star", s.getStarName());
            data.put("distance", String.valueOf(s.getStarDistance()));
            data.put("radius", String.valueOf(s.getRadius()));
            solarSystemsData.add(gson.toJson(data));
        }
        return solarSystemsData;
    }

    public List<String> getPlanetsData(int solarSystemId) {
        List<String> planetsData = new ArrayList<>();
        Gson gson = new Gson();
        for (SolarSystem s : solarSystems) {
            if (s.getId() != solarSystemId) {
                continue;
            }
            for (Planet p : s.getPlanets()) {
                Map<String, String> data = new HashMap<>();
                data.put("id", String.valueOf(p.getId()));
                data.put("name", p.getName());
                data.put("mass", String.valueOf(p.getMass()));
                data.put("radius", String.valueOf(p.getRadius()));
                data.put("gravity", String.valueOf(p.getGravity()));
                data.put("date", String.valueOf(p.getLastAlbedoMeasurement()));
                planetsData.add(gson.toJson(data));
            }
        }
        return planetsData;
    }
}
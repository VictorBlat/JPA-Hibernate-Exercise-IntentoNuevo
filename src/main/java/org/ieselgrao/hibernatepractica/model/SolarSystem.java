package org.ieselgrao.hibernatepractica.model;

import jakarta.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "solar_systems")
public class SolarSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "star_name", nullable = false)
    private String starName;

    @Column(name = "star_distance", nullable = false)
    private double starDistance;

    @Column(name = "radius", nullable = false)
    private double Radius;

    @OneToMany(mappedBy = "solarSystem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Planet> planets;

    // Constructor vacío para JPA
    public SolarSystem() {
        this.planets = new LinkedList<>();
    }

    public SolarSystem(String name, String starName, double starDistance, double Radius) {
        this.name = name;
        this.starName = starName;
        this.starDistance = starDistance;
        this.Radius = Radius;
        this.planets = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new UniverseException(UniverseException.INVALID_NAME);
        }
        this.name = name;
    }

    public String getStarName() {
        return starName;
    }

    public void setStarName(String starName) {
        if (starName == null || starName.trim().isEmpty()) {
            throw new UniverseException(UniverseException.INVALID_NAME);
        }
        this.starName = starName;
    }

    public double getStarDistance() {
        return starDistance;
    }

    public void setStarDistance(double starDistance) {
        if (starDistance < 0) {
            throw new UniverseException(UniverseException.INVALID_DISTANCE);
        }
        this.starDistance = starDistance;
    }

    public double getRadius() {
        return Radius;
    }

    public void setRadius(double Radius) {
        this.Radius = Radius;
    }

    public List<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(LinkedList<Planet> planets) {
        this.planets = planets;
        for (Planet p : planets) {
            p.setSolarSystem(this);
        }
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
        planet.setSolarSystem(this);
    }

    public void removePlanet(Planet planet) {
        planets.remove(planet);
        planet.setSolarSystem(null);
    }
}
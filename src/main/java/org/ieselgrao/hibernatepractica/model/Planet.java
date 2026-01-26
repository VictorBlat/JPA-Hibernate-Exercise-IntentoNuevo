package org.ieselgrao.hibernatepractica.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "planets")
public class Planet {

    private static final double MIN_MASS = 1e18;
    private static final double MIN_RADIUS = 400;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_moons", nullable = false)
    private int numberOfMoons;

    @Column(name = "mass", nullable = false)
    private double mass;

    @Column(name = "radius", nullable = false)
    private double radius;

    @Column(name = "gravity", nullable = false)
    private double gravity;

    @Column(name = "last_albedo_measurement", nullable = false)
    private LocalDate lastAlbedoMeasurement;

    @Column(name = "has_rings", nullable = false)
    private boolean hasRings;

    @ManyToOne
    @JoinColumn(name = "solar_system_id", nullable = false)
    private SolarSystem solarSystem;

    // Constructor vacío para JPA
    public Planet() {
    }

    public Planet(String name, double mass, double radius, double gravity, LocalDate lastAlbedoMeasurement) {
        this(name, 0, mass, radius, gravity, lastAlbedoMeasurement, false, null);
    }

    public Planet(String name, int numberOfMoons, double mass, double radius, double gravity, LocalDate lastAlbedoMeasurement, boolean hasRings, SolarSystem solarSystem) {
        setName(name);
        setNumberOfMoons(numberOfMoons);
        setMass(mass);
        setRadius(radius);
        setGravity(gravity);
        setLastAlbedoMeasurement(lastAlbedoMeasurement);
        setHasRings(hasRings);
        this.solarSystem = solarSystem;
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

    public int getNumberOfMoons() {
        return numberOfMoons;
    }

    public void setNumberOfMoons(int numberOfMoons) {
        if (numberOfMoons < 0) {
            throw new UniverseException(UniverseException.INVALID_NUMBER_OF_MOONS);
        }
        this.numberOfMoons = numberOfMoons;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        if (mass < MIN_MASS) {
            throw new UniverseException(UniverseException.INVALID_MASS);
        }
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        if (radius < MIN_RADIUS) {
            throw new UniverseException(UniverseException.INVALID_RADIUS);
        }
        this.radius = radius;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        if (gravity <= 0) {
            throw new UniverseException(UniverseException.INVALID_GRAVITY);
        }
        this.gravity = gravity;
    }

    public LocalDate getLastAlbedoMeasurement() {
        return lastAlbedoMeasurement;
    }

    public void setLastAlbedoMeasurement(LocalDate lastAlbedoMeasurement) {
        if (lastAlbedoMeasurement == null || lastAlbedoMeasurement.isAfter(LocalDate.now())) {
            throw new UniverseException(UniverseException.INVALID_LAST_ALBEDO_MEASUREMENT);
        }
        this.lastAlbedoMeasurement = lastAlbedoMeasurement;
    }

    public boolean hasRings() {
        return hasRings;
    }

    public void setHasRings(boolean hasRings) {
        this.hasRings = hasRings;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }
}
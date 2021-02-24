package ca.cmpt213.asn5.server.model;

/**
 * Hakim Bashe 301390647 hbashe@sfu.ca
 * CMPT 213 Assignment 5
 * Tokimon  class
 * This class contains the individual parts of a tokimon, such as id name etc
 * It also contains a constructor, getters and setters
 */
public class Tokimon {
    long id;
    String name;
    double weight;
    double height;
    String ability;
    double strength;
    String colour;

    public Tokimon(String name, double weight, double height, String ability, double strength, String colour) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.ability = ability;
        this.strength = strength;
        this.colour = colour;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}

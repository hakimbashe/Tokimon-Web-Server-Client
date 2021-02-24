package client;
/**
 * Hakim Bashe 301390647 hbashe@sfu.ca
 * CMPT 213 Assignment 5
 * Display Class
 * This class contains the characteristics for the display page of the client
 * it contains the name weight and height of tokis, which are used to make the images
 * it also has a contructor and setters/getters
 */
public class Display {
    private String name;
    private double weight;
    private double height;

    public Display(String name, double weight, double height) {
        this.name = name;
        this.weight = weight;
        this.height = height;
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
}

package at.tugraz.thedrunksailor;

/**
 * Created by Sami on 18/05/2016.
 */
public class Place {
    private String name;
    private int id;
    private String adress;
    private String city;
    private String country;
    private int city_code;
    private String description;
    private double current_capacity;
    private double user_note;

    public Place(String name, double current_capacity, double user_note) {
        this.name=name;
        this.current_capacity=current_capacity;
        this.user_note=user_note;
    }

    public String getName() {
        return name;
    }

    public double getCurrent_capacity() {
        return current_capacity;
    }

    public double getUser_note() {
        return user_note;
    }
}

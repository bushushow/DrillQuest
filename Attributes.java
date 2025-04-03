/**
 * Represents the attributes of a valuable object.
 * This class encapsulates information about a valuable object, including its name, worth, and weight.
 */
public class Attributes {

    private String valuable; // The name of the valuable object.
    private final int worth; // The worth of the valuable object.
    private final int weight; // The weight of the valuable object.

    /**
     * Constructs an Attributes object with the specified parameters.
     *
     * @param valuable The name of the valuable object.
     * @param worth The worth of the valuable object.
     * @param weight The weight of the valuable object.
     */
    public Attributes(String valuable, int worth, int weight) {
        this.valuable = valuable;
        this.worth = worth;
        this.weight = weight;
    }

    /**
     * Retrieves the worth of the valuable object.
     *
     * @return The worth of the valuable object.
     */
    public int getWorth() {
        return worth;
    }

    /**
     * Retrieves the weight of the valuable object.
     *
     * @return The weight of the valuable object.
     */
    public int getWeight() {
        return weight;
    }
}

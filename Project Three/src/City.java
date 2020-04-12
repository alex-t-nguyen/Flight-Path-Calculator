
public class City {

    // PRIVATE VARIABLES

    private String name;
    private int cost;
    private int travelTime;

    // CONSTRUCTORS

    /**
     * Default contsructor for empty city
     */
    public City()
    {
        name = null;
        cost = 0;
        travelTime = 0;
    }
    
    /**
     * Overloaded constructor for city with set name, cost, and time
     */
    public City(String name, int cost, int travelTime)
    {
        this.name = name;
        this.cost = cost;
        this.travelTime = travelTime;
    }

    // ACCESSORS

    /**
     * Gets name of city
     * @return name of city
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets cost of flight to city
     * @return cost of flight to city
     */
    public int getCost()
    {
        return cost;
    }

    /**
     * Gets travel time of flight to city
     * @return travel time of flight to city
     */
    public int getTravelTime()
    {
        return travelTime;
    }

    // MUTATORS

    /**
     * Sets the name of city
     * @param name name of city
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Sets the cost of flight to city
     * @param cost cost of flight to city
     */
    public void setCost(int cost)
    {
        this.cost = cost;
    }

    /**
     * Sets the travel time to city
     * @param travelTime travel time to city
     */
    public void setTravelTime(int travelTime)
    {
        this.travelTime = travelTime;
    }
}

// Alex Nguyen
// atn170001

public class Node<T> implements Comparable<Node<T>>{

    // Private data members
    private T data;
    private Node<T> next;
    private boolean isVisited;

    /**
     * Default constructor of node
     */
    public Node() {
        data = null;
        next = null;
    }

    /**
     * Constructor of specific node
     * 
     * @param d payload to give node
     */
    public Node(T d) {
        data = d;
        next = null;
    }

    /**
     * Construct node with next position
     * 
     * @param d payload to give node
     * @param n next node to set
     */
    public Node(T d, Node<T> n) {
        data = d;
        next = n;
    }

    /**
     * Returns data of node
     * 
     * @return data of node
     */
    public T getData() {
        return data;
    }

    /**
     * Returns next node
     * 
     * @return next node
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * Checks if node is visited or note
     * @return boolean true if node is visited, else false
     */
    public boolean isVisited()
    {
        return isVisited;
    }

    /**
     * Sets payload of node
     * 
     * @param payload data of node
     */
    public void setData(T payload) {
        data = payload;
    }

    /**
     * Sets visited value of node
     * @param visited boolean value indicating if node is visited or not
     */
    public void setVisited(boolean visited)
    {
        isVisited = visited;
    }

    /**
     * Sets next node
     * 
     * @param n next node
     */
    public void setNext(Node<T> n) {
        next = n;
    }

    /**
     * Overrided toString method to display player's data
     * 
     * @return player's data
     * 
     * @Override public String toString() { int numAtBats =
     *           ((Player)data).calculateNumAtBats(((Player)data).getHits(),((Player)data).getOuts(),
     *           ((Player)data).getStrikeouts()); int numPlateAppearances =
     *           ((Player)data).calculatePlateAppearances(((Player)data).getHits(),
     *           ((Player)data).getOuts(), ((Player)data).getStrikeouts(),
     *           ((Player)data).getWalks(), ((Player)data).getHitByPitches(),
     *           ((Player)data).getSacrifices()); return ((Player) data).getName() +
     *           "\t" + numAtBats + "\t" + ((Player)data).getHits() + "\t" +
     *           ((Player)data).getWalks() + "\t" + ((Player)data).getStrikeouts() +
     *           "\t" + ((Player)data).getHitByPitches() + "\t" +
     *           ((Player)data).getSacrifices() + "\t" + String.format("%.3f",
     *           ((Player)data).calculateBattingAverage(((Player)data).getHits(),
     *           numAtBats)) + "\t" + String.format("%.3f",
     *           ((Player)data).calculateOnBasePercentage(((Player)data).getHits(),
     *           ((Player)data).getWalks(), ((Player)data).getHitByPitches(),
     *           numPlateAppearances)) + "\n"; }
     */

    /**
     * Compares names of 2 node objects
     * Check if object in paramter is of class type "City" before comparing
     * @param obj Node to compare to
     * @return number to determine if names are equal or not
     */
    @Override
    public int compareTo(Node<T> obj) {
        //if(obj.getData() instanceof City)
        //{
            //System.out.println("SOURCE: " + ((City)this.data).getName().toLowerCase());
            //System.out.println("SOURCE 2: " + ((City)obj.getData()).getName().toLowerCase());
            int result = ((City)this.data).getName().toLowerCase().compareTo(((City)obj.getData()).getName().toLowerCase());
            //System.out.println(result);
            if (result == 0) {
                return 0;
            } else {
                return result;
            }
        //}
        /*
        else
        {
            System.out.println("Obj not of type City");
            return Integer.MIN_VALUE;
        }*/
    }

    /**
     * Overloaded compareTo to compare names of players by inputing specific name
     * 
     * @param name name of player to compare to
     * @return number to determine if names are equal or not
     */
    public int compareTo(String name) {
        int result = ((City) this.data).getName().toLowerCase().compareTo(name.toLowerCase());
        if (result == 0) {
            return 0;
        } else {
            return result;
        }
    }

   /**
    * Overrided toString method to display city name
    * @return city name
    */
    @Override
    public String toString()
    {
        if(next == null)
            return ((City)data).getName() + ". ";
        else
            return ((City)data).getName() + " -> ";
    }

    public Node<T> copyList(Node<T> headNode)
    {
        if(headNode == null)
            return null;
        else
        {
            T data = headNode.getData();
            Node<T> newNode = new Node<T>(data);
            Node<T> n = headNode.next;
            newNode.setNext(copyList(n));
            return newNode;
        }
    }
}
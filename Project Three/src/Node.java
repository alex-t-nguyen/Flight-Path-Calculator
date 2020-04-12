// Generic Node class
public class Node<T> {

    // PRIVATE VARIABLES

    private Node<T> next;
    private T data;

    // CONSTRUCTORS

    /**
     * Default constructor for empty generic node
     */
    public Node()
    {
        data = null;
        next = null;
    }

    /**
     * Overloaded constructor for generic node with set data
     * @param data
     */
    public Node(T data)
    {
        this.data = data;
    }

    // ACCESSORS

    /**
     * Gets the data of node
     * @return data of node
     */
    public T getData()
    {
        return data;
    }

    /**
     * Gets the next node in linked list
     * @return next node in linked list
     */
    public Node<T> getNext()
    {
        return next;
    }

    // MUTATORS

    /**
     * Sets the data of node
     * @param data data to set for node
     */
    public void setData(T data)
    {
        this.data = data;
    }

    /**
     * Sets the next node in linked list
     * @param next next node to set in linked list
     */
    public void setNext(Node<T> next)
    {
        this.next = next;
    }
}
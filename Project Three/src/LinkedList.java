// Generic Linked List class
public class LinkedList<T> {
    
    // PRIVATE VARIABLES
    private Node<T> head;


    // CONSTRUCTORS

    /**
     * Default constructor for empty generic linked list
     */
    public LinkedList()
    {
        head = null;
    }

    /**
     * Overloaded constructor for generic linked list with set head
     */
    public LinkedList(Node<T> head)
    {
        this.head = head;
    }

    /**
     * Appends generic node to generic linked list
     * @param node generic node to append to generic linked list
     */
    public void appendNode(Node<T> node)
    {
        if(head == null)
        {
            head = node;
        }
        else
        {
            Node<T> iter = head;
            while(iter.getNext() != null)   // iterate through linked list
                iter = iter.getNext();
            iter.setNext(node);
        }
    }

    /**
     * Remove node given a key from generic linked list
     * @param key identifier for removing node from generic linked list
     */
    public void removeNode(T key)
    {
        Node<T> current = head.getNext();
        Node<T> prev = head;
        
        if(prev == null)
        {
            return;
        }
        else if(prev.getData().equals(key))
        {
            head = null;
        }
        else
        {
            while(current.getNext() != null && !(current.getData().equals(key)))
            {
                prev = current;
                current = current.getNext();
            }
            prev.setNext(current.getNext());
        }
    }


    /**
     * Overloaded removeNode method 
     * Uses class "City" and string City.getName() as key
     * Remove node given a key from generic linked list
     * @param key identifier for removing node from generic linked list
     */
    public void removeNode(String key)
    {
        Node<T> current = head.getNext();
        Node<T> prev = head;
        
        if(prev == null)
        {
            return;
        }
        else if((((City)prev.getData()).getName()).equals(key))
        {
            head = null;
        }
        else
        {
            while(current.getNext() != null && !(current.getData().equals(key)))
            {
                prev = current;
                current = current.getNext();
            }
            prev.setNext(current.getNext());
        }
    }
}
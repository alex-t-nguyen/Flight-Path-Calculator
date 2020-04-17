
// Generic Stack implemented as a linked list
public class Stack<T> {
    
    Node<T> head;

    /**
     * Default constructor for stack
     */
    public Stack()
    {
        head = null;
    }

    /**
     * Returns true if stack is empty, else false
     * @return true if stack is empty, false if stack is not empty
     */
    public boolean isEmpty()
    {
        if(head != null)
            return false;
        else
            return true;
    }

    /**
     * Returns data of popped node or null if no node in stack
     * @return data of node or null
     */
    public T pop()
    {
        T data = null;
        if(head != null)
        {
            data = head.getData();
            head = head.getNext();
        }
        else
        {
            System.out.println("Stack is empty");
        }
        return data;
    }

    /**
     * Pushes data onto stack (at head of linked list)
     * @param data data to push onto stack
     */
    public void push(T data)
    {
        if(head != null)
        {
            Node<T> temp = head;
            head = new Node<>(data);
            head.setNext(temp);
        }
        else
            head = new Node<>(data);        
    }

    /**
     * Returns data on top of stack
     * @return data on top of stack or null if stack is empty
     */
    public T peek()
    {
        if(head != null)
            return head.getData();
        else
        {
            System.out.println("Stack is empty");
            return null;
        }
    }
}
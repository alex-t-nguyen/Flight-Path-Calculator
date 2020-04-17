// Alex Nguyen
// atn170001

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

public class LinkList<T> implements Iterable<T> {
    private Node<T> head;
    static final int NUM_LEADER_CATEGORIES = 5;

    /**
     * Default constructor for linked list
     */
    public LinkList() {
        head = null;
    }

    /**
     * Overloaded constructor for linked list
     * 
     * @param n head node of linked list
     */
    public LinkList(Node<T> n) {
        head = n;
    }

    /**
     * Adds node to end of linked list
     * 
     * @param n node to add to end of list
     */
    public void appendList(Node<T> n) {
        //n.setNext(null);
        if (head != null) 
        {
            Node<T> iter = head;
            while (iter.getNext() != null) 
            {
                iter = iter.getNext();
            }
            iter.setNext(n);
            // iter.getNext().setNext(null);
            return;
        } 
        else 
        {
            head = n;
            // head.setNext(null);
            return;
        }
    }

    public void deleteNode(Node<T> node)
    {
        if(head == null)
            return;
        Node<T> temp = head;
        if(node.compareTo(head) == 0)
        {
            head = temp.getNext();
            return;
        }
        else
        {
            Node<T> current = head.getNext();
            Node<T> prev = head;
            while(current != null)
            {
                if(current.compareTo(node) == 0)
                {
                    prev.setNext(current.getNext());
                    return;
                }
                prev = current;
                current = current.getNext();
            }
        }
    }

    /**
     * Returns head of linked list
     * 
     * @return head of linked list
     */
    public Node<T> getHead() {
        return head;
    }

    /**
     * Sets head of linked list
     * 
     * @param h head of linked list
     */
    public void setHead(Node<T> h) {
        this.head = h;
    }

    /**
     * Displays players' data in file recursively
     * 
     * @param h       head node of list
     * @param bWriter buffered writer that has the output file
     */
    public void displayPlayerData(Node<T> h, BufferedWriter bWriter) throws IOException {
        try {
            if (h == null) // Check if no node in list
                return;
            else {
                bWriter.write(h.toString());
                // Display all stats of player
                /*
                 * int numAtBats =
                 * ((Player)h.getData()).calculateNumAtBats(((Player)h.getData()).getHits(),((
                 * Player)h.getData()).getOuts(), ((Player)h.getData()).getStrikeouts()); int
                 * numPlateAppearances =
                 * ((Player)h.getData()).calculatePlateAppearances(((Player)h.getData()).getHits
                 * (), ((Player)h.getData()).getOuts(), ((Player)h.getData()).getStrikeouts(),
                 * ((Player)h.getData()).getWalks(), ((Player)h.getData()).getHitByPitches(),
                 * ((Player)h.getData()).getSacrifices());
                 * bWriter.write(((Player)h.getData()).getName() + "\t");
                 * bWriter.write(numAtBats + "\t");
                 * bWriter.write(((Player)h.getData()).getHits() + "\t");
                 * bWriter.write(((Player)h.getData()).getWalks() + "\t");
                 * bWriter.write(((Player)h.getData()).getStrikeouts() + "\t");
                 * bWriter.write(((Player)h.getData()).getHitByPitches() + "\t");
                 * bWriter.write(((Player)h.getData()).getSacrifices() + "\t");
                 * bWriter.write(String.format("%.3f",
                 * ((Player)h.getData()).calculateBattingAverage(((Player)h.getData()).getHits()
                 * , numAtBats)) + "\t"); bWriter.write(String.format("%.3f",
                 * ((Player)h.getData()).calculateOnBasePercentage(((Player)h.getData()).getHits
                 * (), ((Player)h.getData()).getWalks(),
                 * ((Player)h.getData()).getHitByPitches(), numPlateAppearances)));
                 * bWriter.write("\n");
                 */
                h = h.getNext();
                displayPlayerData(h, bWriter); // Recursive call of display function
            }
        } catch (IOException exp) {
            throw exp;
        }
    }

    /**
     * Gets size of linked list
     * 
     * @param head head of linked list
     * @return size of linked list
     */
    public int getListSize(Node<T> head) {
        int numNodes = 0;
        while (head != null) {
            numNodes++;
            head = head.getNext();
        }
        return numNodes;
    }

    /**
     * Deletes linked list
     */
    public void deleteList() {
        head = null;
    }

    /**
     * Searches linked list for node with specified name and returns node if found
     * 
     * @param name name of node data to search for
     * @return node that has the same name
     */
    public Node<T> searchList(String name) {
        if (head == null)
            return null;
        else {
            for (Node<T> iter = head; iter != null; iter = iter.getNext()) {
                if (iter.compareTo(name) == 0) {
                    return iter;
                } else
                    continue;
            }
        }
        return null;
    }

    /**
     * Searches linked list for node with specified name and returns node if found
     * 
     * @param name name of node data to search for
     * @return node that has the same name
     */
    public Node<T> searchList(Node<T> n) {
        if (head == null)
            return null;
        else {
            for (Node<T> iter = head; iter != null; iter = iter.getNext()) {
                if (iter.compareTo(n) == 0) {
                    return iter;
                } else
                    continue;
            }
        }
        return null;
    }

    /**
     * Searches linked list for node with specified name and returns boolean value
     * 
     * @param name name of node data to search for
     * @return true if node is in list, else false
     */
    public boolean isInList(Node<T> data) {
        if (head == null)
            return false;
        else {
            for (Node<T> iter = head; iter != null; iter = iter.getNext()) {
                if (iter.compareTo(data) == 0) {
                    return true;
                } else
                    continue;
            }
        }
        return false;
    }

    /**
     * Sorts linked list of cities alphabetically by name
     * 
     * @param head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortAlphabetically(Node<T> head) {
        boolean swapped = true;

        if (head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else {
            while (swapped) {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while (iter != null) {
                    // Compare the name of players
                    if (head.compareTo(iter) > 0) {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        }
    }

    public LinkList<T> copyList()
    {
        LinkList<T> copy = new LinkList<>();
        Node<T> iter = head;
        while(iter != null)
        {
            copy.appendList(iter);
            iter = iter.getNext();
        }
        return copy;
    }

    /**
     * Get node in linked list by index
     * @param index index of node in linked list
     * @return node in index
     */
    public Node<T> getNode(int index)
    {
        Node<T> iter = head;
        for(int i = 0; i < index; i++)
        {
            iter = iter.getNext();
        }
        return iter;
    }

    public void displayTopThreePaths(Node<T> h, BufferedWriter bWriter) throws IOException
    {
        try
        {
            if(h == null)
                return;
            else
            {
                bWriter.write(h.toString());
                h = h.getNext();
                displayTopThreePaths(h, bWriter);
            }
        } catch (IOException exp) {
            throw exp;
        }
    }

    public void printList()
    {
        Node<T> iter = head;
        while(iter != null)
        {
            System.out.print(iter.toString());
            iter = iter.getNext();
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new myIterator<>(this);
    }  
}
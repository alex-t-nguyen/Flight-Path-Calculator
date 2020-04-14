import java.util.Iterator;

public class myIterator<T> implements Iterator<T> {

    Node<T> currentNode;

    public myIterator(LinkList<T> list)
    {
        currentNode = list.getHead();
    }

    @Override
    public boolean hasNext() {
        if(currentNode != null)
            return true;
        else
            return false;
    }

    @Override
    public T next() {
            T data = currentNode.getData();
            currentNode = currentNode.getNext();
            return data;
    }

}
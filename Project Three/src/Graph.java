
public class Graph<T> {
    private LinkList<LinkList<T>> adjList;

    public Graph()
    {
        adjList = new LinkList<>();
    }

    public Graph(LinkList<LinkList<T>> adjList)
    {
        this.adjList = adjList;
    }

    public void addVertex(T source)
    {
        //LinkList<T> adjacentCities = new LinkList<>(new Node<T>(source));
        adjList.appendList(new Node<LinkList<T>>(new LinkList<T>(new Node<T>(source))));
    }

    public void addEdge(Node<T> source, Node<T> destination, boolean undirected)
    {
        boolean originInList = false;
        boolean destinationInList = false;

        if(adjList.getHead() == null)
        {
            addVertex(source.getData());
        }

        /**
         * Check to see if origin city if or isn't already in the list
         */
        for(LinkList<T> iter : adjList)
        {
            if(iter.getHead().compareTo(source) == 0)
            {   
                originInList = true;
                for(Node<T> innerIter = iter.getHead().getNext(); innerIter != null; innerIter = innerIter.getNext())
                {
                    if(innerIter.compareTo(destination) == 0)   // If destination is already in origin city's list of adjacent cities -> do not add destination
                        destinationInList = true;
                }
                if(!destinationInList)  // Adds destination city to list of adjacent nodes to origin city
                    iter.appendList(destination);
            }
        }
        if(!originInList)  // If origin city is not already in adjacency list, add it to end of list 
        {
            addVertex(source.getData());    // Add origin city to adjacency list
            Node<LinkList<T>> current = adjList.getHead();
            while(current.getNext() != null)    // Get last node in adjacency list (most recently added origin city (from previous line))
                current = current.getNext();
            current.getData().appendList(destination);  // Add destination city to origin city's list
        }

        /**
         * Check to see if destination city is or isn't already in the list
         */
        originInList = false;
        destinationInList = false;
        if(undirected)  // If graph is undirected, add destination city as origin city as well, else don't add
        {
            for(LinkList<T> iter : adjList)
            {
                if(iter.getHead().compareTo(destination) == 0)
                {
                    destinationInList = true;
                    for(Node<T> innerIter = iter.getHead().getNext(); innerIter != null; innerIter = innerIter.getNext())
                    {
                        if(innerIter.compareTo(source) == 0)    // If origin is already in destination city's list of adjacent cities -> do not add origin
                            originInList = true;
                    }
                    if(!originInList)   // Adds origin city to list of adjacent nodes to destination city
                        iter.appendList(source);
                }
            }
            if(!destinationInList)  // If destination city is not already in adjacency list, add it to end of list
            {
                addVertex(destination.getData());   // Add destination city to adjacency list
                Node<LinkList<T>> current = adjList.getHead();
                while(current.getNext() != null)    // Get last node in adjacency list (most recently added destination city (from previous line))
                    current = current.getNext();
                current.getData().appendList(source);   // Add origin city to destination city's list
            }
        }
    }

    public LinkList<LinkList<T>> getAdjList()
    {
        return adjList;
    }

    public void setAdjList(LinkList<LinkList<T>> list)
    {
        adjList = list;
    }

    /**
     * Returns number of vertices in graph
     * @return int number of vertices in graph
     */
    public int getGraphSize()
    {
        return adjList.getListSize(adjList.getHead());
    }

    public Graph<T> getAllPaths(Node<T> source, Node<T> destination)
    {
        Graph<T> pathsList = new Graph<>();
        LinkList<T> path = new LinkList<>();
        Stack<Node<LinkList<T>>> visitedNodes = new Stack<>();
        boolean needNewAdjacentNodes = true;

        Node<LinkList<T>> iter = adjList.getHead();
        int match = iter.getData().getHead().compareTo(source);
        
        while(match != 0)  // Looks for current node in list
        {
            iter = iter.getNext();
            if(iter == null)
                return pathsList;
            match = iter.getData().getHead().compareTo(source);
        }


        /**
         * GET ALL PATHS BETWEEN 2 VERTICES IN GRAPH
         * 
         * 1) Add origin to path, mark as visited, and push to stack
         * 2) Push all UNVISITED adjacent nodes of current node onto stack
         * 3) Peek top of stack
         *      a) Check if visited
         *          i) If not visited, go to its node in adjacency list, mark as visited, add to path
         *          ii) If visited, pop off stack, unvisit, and remove from path, don't get new adjacent nodes and go back to while loop
         *      b) Check if node is the destination
         *          i) If it is destination, save path to list of paths, pop from stack, remove from path, and mark as unvisited
         *          ii) If not destination, push all UNVISITED adjacent nodes to stack and repeat step 3
         * 4) Repeat while stack is not empty
         * 5) When stack is empty (reach source node on stack and pop it off) end function
         */

        // 1) Add origin to path, mark as visited, and push to stack
        Node<T> sourceNode = iter.getData().getHead();
        path.appendList(new Node<>(sourceNode.getData()));
        iter.setVisited(true);
        visitedNodes.push(iter);

        // 4) Repeat while stack is not empty
        while(visitedNodes.isEmpty() == false)  
        {    
            Node<LinkList<T>> linkListNodes;
            if(needNewAdjacentNodes)
            {
                // 2) Push all UNVISITED adjacent nodes of current node onto stack
                linkListNodes = adjList.getHead();
                for(Node<T> i = iter.getData().getHead().getNext(); i != null; i = i.getNext())
                {
                    while(linkListNodes.getData().getHead().compareTo(i) != 0 && linkListNodes != null)
                        linkListNodes = linkListNodes.getNext();
                    if(!linkListNodes.isVisited() && linkListNodes != null)
                        visitedNodes.push(linkListNodes);
                    linkListNodes = adjList.getHead();  // Reset linkListNodes to head of adjacency list
                }
            }
            // 3) Peek top of stack
            Node<LinkList<T>> stackNode = visitedNodes.peek();
            linkListNodes = adjList.getHead();  // Reset linkListNodes to head of adjacency list
            // 3a,i) If not visited, go to its node in adjacency list, mark as visited, add to path
            if(!stackNode.isVisited())
            {
                while(linkListNodes.getData().getHead().compareTo(stackNode.getData().getHead()) != 0)
                    linkListNodes = linkListNodes.getNext();
                linkListNodes.setVisited(true);
                path.appendList(new Node<>(linkListNodes.getData().getHead().getData()));
                
            }
            // 3a,ii) If visited, pop off stack, unvisit, and remove from path, don't get new adjacent nodes and go back to while loop
            else
            {
                Node<LinkList<T>> nodeToDelete = visitedNodes.pop();
                nodeToDelete.setVisited(false);
                path.deleteNode(nodeToDelete.getData().getHead());
                needNewAdjacentNodes = false;
                continue;
            }
            
            // 3b) Check if node is destination
            if(stackNode.getData().getHead().compareTo(destination) == 0)
            {
                // 3b,i) If it is destination, save path to list of paths, pop from stack, remove from path, and mark as unvisited
                
                // Make a shallow copy of path because if you delete node from original path the pointer is still connected to it in pathsList
                // resulting in the node being removed from pathsList as well
                LinkList<T> listCopy = new LinkList<>(path.getHead().copyList(path.getHead())); 

                pathsList.getAdjList().appendList(new Node<LinkList<T>>(listCopy));
                Node<LinkList<T>> nodeToDelete = visitedNodes.pop();
                path.deleteNode(nodeToDelete.getData().getHead());
                nodeToDelete.setVisited(false);
                needNewAdjacentNodes = false;
            }
            // 3b,ii) If not destination, push all of its UNVISITED adjacent nodes to stack and repeat step 3
            else
            {
                needNewAdjacentNodes = true;
                iter = stackNode;
            }
        }

        // 5) When stack is empty (reach source node on stack and pop it off) end function
        //pathsList.getAdjList().getHead().getData().printList();
        return pathsList;
    }
}
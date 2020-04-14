import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        
    
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        Scanner fileReader = null;
        File file = new File(filename);
        try
        {
            fileReader = new Scanner(file);
        }
        catch(java.io.FileNotFoundException exception)
        {
            scanner.close();
            throw exception;
        }

        LinkList<LinkList<City>> adjList = new LinkList<>();
    
        String line = null;
        
        int maxSize = 0;
        if(fileReader.hasNextLine())
        {
            maxSize = Integer.parseInt(fileReader.nextLine());
            System.out.println("Size " + maxSize);
        }
        else
        {
            fileReader.close();
            scanner.close();
            return;
        }

        for(int i = 0; i < maxSize; i++)    // Loop through all lines in file
        {
            boolean originInList = false;   // boolean flag to check if origin city is already in adjacency list
            boolean destinationInList = false;  // boolean flag to check if destinatino city is already in adjacency list
            line = fileReader.nextLine();
            String[] flightData = line.split("\\|");
            String origin = flightData[0];
            String destination = flightData[1];
            int cost = Integer.parseInt(flightData[2]);
            int travelTime = Integer.parseInt(flightData[3]);
            
            /**
             * Check to see if origin city if or isn't already in the list
             */
            for (LinkList<City> iter : adjList) {
                if(iter.getHead().getData().getName().equals(origin))
                {
                    originInList = true;
                    for(Node<City> innerIter = iter.getHead().getNext(); innerIter != null; innerIter = innerIter.getNext())
                    {
                        if(innerIter.getData().getName().equals(destination))   // If destination is already in origin city's list of adjacent cities -> do not add destination
                            destinationInList = true;   
                    }
                    if(!destinationInList) // Adds destination city to list of adjacent nodes to origin city
                    {
                        City city = new City(destination, cost, travelTime);
                        iter.appendList(new Node<>(city));
                    }
                }
            }
            if(!originInList)   // If origin city is not already in adjacency list, add it to end of list
            {
                City city = new City();
                city.setName(origin);
                LinkList<City> adjacentCities = new LinkList<>(new Node<City>(city));
                adjacentCities.appendList(new Node<City>(new City(destination, cost, travelTime)));
                adjList.appendList(new Node<>(adjacentCities));
            } 

            /**
             * Check to see if destination city is or isn't already in the list
             */
            originInList = false;
            destinationInList = false;
            for (LinkList<City> iter : adjList) {
                if(iter.getHead().getData().getName().equals(destination))
                {
                    destinationInList = true;
                    for(Node<City> innerIter = iter.getHead().getNext(); innerIter != null; innerIter = innerIter.getNext())
                    {
                        if(innerIter.getData().getName().equals(origin))   // If origin is already in destination city's list of adjacent cities -> do not add origin
                            originInList = true;   
                    }
                    if(!originInList) // Adds origin city to list of adjacent nodes to destination city
                    {
                        City city = new City(origin, cost, travelTime);
                        iter.appendList(new Node<>(city));
                    }
                }
            }
            if(!destinationInList)   // If destination city is not already in adjacency list, add it to end of list
            {
                City city = new City();
                city.setName(destination);
                LinkList<City> adjacentCities = new LinkList<>(new Node<City>(city));
                adjacentCities.appendList(new Node<City>(new City(origin, cost, travelTime)));
                adjList.appendList(new Node<>(adjacentCities));
            } 
        }

        /**
         * Testing printout of flight data file as adjacency list
         * Can comment out when not testing
         */
        for(LinkList<City> iter : adjList)
        {
            Node<City> nodeIter = iter.getHead();
            while(nodeIter != null)
            {
                if(nodeIter.getNext() != null)
                    System.out.print(nodeIter.getData().getName() + ", ");
                else
                    System.out.print(nodeIter.getData().getName());
                nodeIter = nodeIter.getNext();
            }
            System.out.println();
        }
        scanner.close();
        fileReader.close();
    }
}
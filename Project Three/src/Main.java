import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        Scanner scanner;
        BufferedWriter bWriter = null;
        String command;
        do
        {
            scanner = new Scanner(System.in);
            command = scanner.next();
            if(!command.equals("/flightPlan"))
                System.out.println("Please enter proper command: \"/flightPlan\" <FlightDataFile> <PathsToCalculateFile> <OutputFile> ");
        } while(!command.equals("/flightPlan"));
        String dataFile = scanner.next();
        String requestedFlightsFile = scanner.next();
        String outputFile = scanner.next();
        Scanner fileReader = null;
        Scanner requestedFlightsReader = null;
        try
        {
            File data = new File(dataFile); // Flight data file
            File rqstFlights = new File(requestedFlightsFile);  // Requested flights data file

            fileReader = new Scanner(data); // flight data scanner
            requestedFlightsReader = new Scanner(rqstFlights);  // Requested flights data scanner
        }
        catch(java.io.FileNotFoundException exception)
        {
            scanner.close();
            throw exception;
        }

        Graph<City> graph = new Graph<>();
        String line = null;
        
        int maxSize = 0;
        int numRequestedFlights = 0;
        if(fileReader.hasNextLine())
        {
            maxSize = Integer.parseInt(fileReader.nextLine());
            if(requestedFlightsReader.hasNextLine())
                numRequestedFlights = Integer.parseInt(requestedFlightsReader.nextLine());
        }
        else
        {
            fileReader.close();
            scanner.close();
            requestedFlightsReader.close();
            return;
        }

        File output = new File(outputFile);
        if(!output.exists())
            output.createNewFile();
        FileWriter outFile = new FileWriter(output);
        bWriter = new BufferedWriter(outFile);

        // Add all flights in "flight data file" to graph
        for(int i = 0; i < maxSize; i++)    // Loop through all lines in file
        {
            line = fileReader.nextLine();
            String[] flightData = line.split("\\|");
            String origin = flightData[0];
            String destination = flightData[1];
            int cost = Integer.parseInt(flightData[2]);
            int travelTime = Integer.parseInt(flightData[3]);
            
            Node<City> source = new Node<>(new City(origin, cost, travelTime));
            Node<City> dest = new Node<>(new City(destination, cost, travelTime));
            
            graph.addEdge(source, dest, true);
        } 

        // Graph to hold all of the paths between 2 nodes
        Graph<City> allPaths = new Graph<>();

        /**
         * Get cost/time of all flight paths
         * 1) Iterate through flight paths list (allPaths)
         * 2) Add cost/time of the adjacent nodes (side part of adjacency list --> NOT VERTICAL PART) that are on the path
         * 3) Repeat for each path in flight paths list 
         * 4) Get top 3 paths based on cost/time as indicated by user
         */
            // Get all path costs of flights in "requested flights file" 
            for(int i = 0; i < numRequestedFlights; i++)    // Loop through all lines in file
            {
                line = requestedFlightsReader.nextLine();
                String[] flightData = line.split("\\|");
                String origin = flightData[0];
                String destination = flightData[1];
                char flightSortType = flightData[2].charAt(0);
                int cost = 0;
                int travelTime = 0;
                int pathNumber = 0;

                // Get all paths between 2 nodes and put into a graph
                allPaths = graph.getAllPaths(new Node<City>(new City(origin,0,0)), new Node<City>(new City(destination,0,0)));            
                
                // If not path available between nodes -> display error message and end program
                if(allPaths.getAdjList().getHead() == null)
                {
                    System.out.println("No flight plans available for flight " + origin + ", " + destination);
                    scanner.close();
                    fileReader.close();
                    requestedFlightsReader.close();
                    return;
                }
                else
                {
                    PriorityQueue<PathStat> maxHeap;
                    if(flightSortType == 'C')
                        maxHeap = new PriorityQueue<>(3, new pathCostComparator());
                    else if(flightSortType == 'T')
                        maxHeap = new PriorityQueue<>(3, new pathTimeComparator());
                    else
                    {
                        System.out.println("'" + flightSortType + "' " + "is not a valid sorting character.");
                        fileReader.close();
                        scanner.close();
                        requestedFlightsReader.close();
                        return;
                    }
                    // 1) Iterate through flight paths list (allPaths)
                    for(LinkList<City> verticalIter : allPaths.getAdjList())
                    {
                        Node<City> sourceIter = verticalIter.getHead();
                        Node<City> destinationIter = sourceIter.getNext();
                        while(destinationIter != null)
                        {
                            Node<LinkList<City>> graphDataIter = graph.getAdjList().getHead();
                            while(graphDataIter.getData().getHead().compareTo(sourceIter) != 0) // Find source node in graph (vertically)
                                graphDataIter = graphDataIter.getNext();
                            Node<City> graphDataDestination = graphDataIter.getData().searchList(destinationIter);
                            cost += graphDataDestination.getData().getCost();
                            travelTime += graphDataDestination.getData().getTravelTime();
                            
                            sourceIter = destinationIter;
                            destinationIter = destinationIter.getNext();
                        }
                        // End while loop --> done with getting total cost/travel time of a path

                        /**
                         * Get top 3 paths between 2 destination in graph
                         * 1) Create object (pathStat) to hold data of path (cost, time, index)
                         * 2) Create priority queue as max heap to hold object (pathStat) and put first 3 paths into queue
                         * 3) Check if subsequent paths are smaller than the max (root of heap) either by cost or time
                         *      i) If smaller than root -> swap root and path
                         *      ii) If equal to root -> add path to heap (can have multiple 3rd place paths)
                         * 4) Display header for flight and the sort type <Origin, Destination> <Cost/Time>
                         *    or check if sort type is valid -> if not exit program
                         * 5) After all paths are in max heap, create a min heap and move objects (pathStat) from
                         *    max heap to min heap (results in paths being in ascending order -> easily display top paths
                         *    by removing root until min heap is empty)
                         * 6) Check if min heap is empty
                         *      i) If min heap is empty -> print error message (no flight path is available)
                         *      ii) If min heap is not empty -> While min heap is not empty
                         *              a) Display path and its time and cost
                         */

                        // 1) Create object (pathStat) to hold data of path (cost, time, index)
                        PathStat pathStat = new PathStat(cost, travelTime, pathNumber);

                        // 2) Create priority queue as max heap to hold object (pathStat) and put first 3 paths into queue
                        if(maxHeap.size() < 3)
                            maxHeap.add(pathStat);
                        // 3) Check if subsequent paths are smaller than the max (root of heap) either by cost or time
                        else
                        {
                            if(flightSortType == 'C')
                            {
                                // 3i) If smaller than root -> swap root and path
                                if(pathStat.getCost() < maxHeap.peek().getCost())
                                {
                                    maxHeap.remove();
                                    maxHeap.add(pathStat);
                                }
                                // 3ii) If equal to root -> add path to heap (can have multiple 3rd place paths)
                                else if(pathStat.getCost() == maxHeap.peek().getCost()) // If tie for 3rd best path then need to display
                                {
                                    maxHeap.add(pathStat);
                                }
                            }
                            else    // if(flightSortType == 'T')
                            {
                                // 3i) If smaller than root -> swap root and path
                                if(pathStat.getTime() < maxHeap.peek().getCost())
                                {
                                    maxHeap.remove();
                                    maxHeap.add(pathStat);
                                }
                                // 3ii) If equal to root -> add path to heap (can have multiple 3rd place paths)
                                else if(pathStat.getTime() == maxHeap.peek().getTime()) // If tie for 3rd best path then need to display
                                {
                                    maxHeap.add(pathStat);
                                }
                            }
                        }
                        pathNumber++;   // Increment path number and loop back to get cost of next path
                    }
                    // End for loop --> Proeccessed costs for each path for a single pair of nodes
                
                    // 4) Display header for flight and the sort type <Origin, Destination> <Cost/Time>
                    if(flightSortType == 'C')
                    {
                        bWriter.write("Flight " + (i + 1) + ": " + origin + ", " + destination + " (Cost)\n");
                    }
                    else if(flightSortType == 'T')
                    {
                        bWriter.write("Flight " + (i + 1) + ": " + origin + ", " + destination + " (Time)\n");
                    }
                    else
                    {
                        System.out.println("Not a valid flight sort type.");
                        scanner.close();
                        requestedFlightsReader.close();
                        fileReader.close();
                        return;
                    }

                    // 5) After all paths are in max heap, create a min heap and move objects (pathStat) from max heap to min heap
                    PriorityQueue<PathStat> minHeap;
                    if(flightSortType == 'C')
                        minHeap = new PriorityQueue<>(maxHeap.size(), new pathMinCostComparator());
                    else // if(flightSortType == 'T')
                        minHeap = new PriorityQueue<>(maxHeap.size(), new pathMinTimeComparator());

                    while(!maxHeap.isEmpty()) 
                        minHeap.add(maxHeap.remove());
                    
                    // 6) Check if min heap is empty
                    // 6i) If min heap is empty -> print error message (no flight path is available)
                    if(minHeap.isEmpty())
                        System.out.println("No flight plans available for flight " + origin + ", " + destination);
                    else
                    {
                        // 6ii) If min heap is not empty -> While min heap is not empty 
                        //     6a) Display path and its time and cost
                        while(!minHeap.isEmpty())
                        {
                            PathStat pStat = minHeap.remove();
                            Node<LinkList<City>> path = allPaths.getAdjList().getNode(pStat.getIndex());
                            
                            // Display path
                            path.getData().displayTopThreePaths(path.getData().getHead(), bWriter);

                            // Display path's time and cost
                            bWriter.write("Time: " + pStat.getTime() + " Cost: " + String.format("%.2f",(double)pStat.getCost()) + "\n");
                        }
                    }
                }
            }
            scanner.close();
            fileReader.close();
            requestedFlightsReader.close();
            bWriter.close();
            outFile.close();
    }

    /**
     * Test method to print adjacency list graph
     * @param graph graph to print implemented as adjacency list
     */
    public void printGraph(Graph<City> graph)
    {
        for(LinkList<City> iter : graph.getAdjList())
        {
            Node<City> nodeIter = iter.getHead();
            while(nodeIter != null)
            {
                if(nodeIter.getNext() != null)
                    System.out.print(nodeIter.getData().getName() + ", ");
                else
                    System.out.print(nodeIter.getData().getName() + ".");
                nodeIter = nodeIter.getNext();
            }
            System.out.println();
        }
    }
}
import java.util.*;

public class TSPSolver<E extends Comparable<? super E>> {

    /**
     * Approximates the shortest cycle that starts and ends at the same vertex and visits all other vertices once.
     * @param g the graph to find the circuit in
     * @return the list of vertices in order they would be visited on the shortest cycle
     */
    public ArrayList<E> getApproxShortestCircuit(WeightedDiGraphList<E> g) {
        PriorityQueue<Triple<E>> edgeQueue = new PriorityQueue<>(Comparator.comparingInt(Triple::getWeight));
        Map<E, Integer> degrees = new HashMap<>();
        Map<E, Set<E>> connectedComponents = new HashMap<>();

        for (E v : g.getVertices()) {
            degrees.put(v, 0);
            connectedComponents.put(v, new HashSet<>());
            for (E u : g.getAllAdjacent(v)) {
                if (v.compareTo(u) < 0) {
                    edgeQueue.add(new Triple<>(v, u, g.getWeight(v, u)));
                }
            }
        }

        ArrayList<E> tour = new ArrayList<>();
        int numVertices = g.getVertices().size();

        while (!edgeQueue.isEmpty() && tour.size() < numVertices) {
            Triple<E> edge = edgeQueue.poll();
            E v1 = edge.getStart();
            E v2 = edge.getEnd();

            if (degrees.get(v1) < 2 && degrees.get(v2) < 2 && !formsCycle(v1, v2, connectedComponents)) {
                connectedComponents.get(v1).add(v2);
                connectedComponents.get(v2).add(v1);
                degrees.put(v1, degrees.get(v1) + 1);
                degrees.put(v2, degrees.get(v2) + 1);
                tour.add(v1);
                tour.add(v2);
            }
        }

        return convertToPath(tour, numVertices);
    }

    private boolean formsCycle(E v1, E v2, Map<E, Set<E>> connectedComponents) {
        Set<E> visited = new HashSet<>();
        Stack<E> stack = new Stack<>();
        stack.push(v1);

        while (!stack.isEmpty()) {
            E current = stack.pop();
            if (current.equals(v2)) {
                return true;
            }
            visited.add(current);
            for (E neighbor : connectedComponents.get(current)) {
                if (!visited.contains(neighbor)) {
                    stack.push(neighbor);
                }
            }
        }

        return false;
    }

    private ArrayList<E> convertToPath(ArrayList<E> edges, int numVertices) {
        LinkedHashSet<E> uniqueVertices = new LinkedHashSet<>(edges);
        if (uniqueVertices.size() != numVertices) {
            return new ArrayList<>(uniqueVertices);
        }
        return new ArrayList<>(uniqueVertices);
    }
}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A class to compare different implementations of the traveling salesman solver
 * @param <E> - the element type that represents vertices in the graph
 */
public class TSPSolver<E extends Comparable<? super E>> {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("What is the file you want to create the graph from?");
        String filename = in.nextLine().trim();
        List<Triple<String>> edges = readEdges(filename);
        WeightedGraphList<String> g = new WeightedGraphList<>(edges);

        // Get the shortest circuit starting and ending at the same vertex, visiting all vertices
        List<String> shortestCircuit = new TSPSolver<String>().getShortestCircuit(g);
        System.out.println("Shortest circuit: " + shortestCircuit);
    }
    
    /**
     * Reads and creates the graph from the list of edges in the specified file
     * @param file the name of the file that has the list of edges
     * @return the graph created from the edges listed in the file
     */
    public static List<Triple<String>> readEdges(String file) {
        List<Triple<String>> edges = new ArrayList<>();
        try {
            Scanner infile = new Scanner(new File(file));
            while(infile.hasNextLine()) {
                String line = infile.nextLine();
                String[] parts = line.split(",");
                edges.add(new Triple<>(parts[0], parts[1], Integer.parseInt(parts[2])));
            }
            infile.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return edges;
    }

    /**
     * Determines the shortest cycle that starts and ends at the same vertex
     * and visits all other vertices once.
     * @param g the graph to find the circuit in
     * @return the list of vertices in order they would be visited on the
     * shortest cycle, null if no such cycle exists
     */
    public List<E> getShortestCircuit(WeightedGraphList<E> g) {
        List<E> vertices = new ArrayList<>(g.getVertices());
        List<E> bestTour = null;
        int bestCost = Integer.MAX_VALUE;

        // Generate all permutations of vertices to try each possible tour
        Collections.sort(vertices);  // Sort to ensure the permutations cover all possibilities
        do {
            // Check if the current permutation forms a valid tour
            int cost = calculateTourCost(g, vertices);
            if (cost < bestCost) {
                bestCost = cost;
                bestTour = new ArrayList<>(vertices);
                bestTour.add(vertices.get(0)); // close the tour by returning to the start
            }
        } while (nextPermutation(vertices));

        return bestTour;
    }

    private int calculateTourCost(WeightedGraphList<E> g, List<E> tour) {
        int cost = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            E from = tour.get(i);
            E to = tour.get(i + 1);
            Integer edgeCost = g.getEdgeWeight(from, to);
            if (edgeCost == null) return Integer.MAX_VALUE; // edge does not exist, invalid tour
            cost += edgeCost;
        }
        // Add cost to return to the starting vertex
        E last = tour.get(tour.size() - 1);
        E first = tour.get(0);
        Integer returnCost = g.getEdgeWeight(last, first);
        if (returnCost == null) return Integer.MAX_VALUE; // edge does not exist, invalid tour
        cost += returnCost;

        return cost;
    }

    public static <E extends Comparable<? super E>> boolean nextPermutation(List<E> elements) {
        // Find the longest non-increasing suffix
        int i = elements.size() - 2;
        while (i >= 0 && elements.get(i).compareTo(elements.get(i + 1)) >= 0) {
            i--;
        }
        if (i == -1) {
            return false;  // Last permutation
        }

        // Find successor to pivot
        int j = elements.size() - 1;
        while (elements.get(i).compareTo(elements.get(j)) >= 0) {
            j--;
        }
        Collections.swap(elements, i, j);

        // Reverse suffix
        Collections.reverse(elements.subList(i + 1, elements.size()));
        return true;
    }

	/**
	 * Determines the weight of the path represented by the list of vertices.
	 * If the path is null or is not possible (e.g., one of the required edges does not exist),
	 * the length of the path is said to be -1.
	 * @param g the graph to use to calculate the path length
	 * @param path the list of vertices in the path (in order)
	 * @return the length of the provided path, -1 if it is not a valid path
	 */
	public int getPathWeight(WeightedDiGraphList<E> g, List<E> path) {
		if (path == null || path.size() < 2) { // Path must have at least two vertices to form an edge
			return -1;
		}

		int totalWeight = 0;

		// Iterate over the path list and calculate the cumulative weight
		for (int i = 0; i < path.size() - 1; i++) {
			E currentVertex = path.get(i);
			E nextVertex = path.get(i + 1);
			int edgeWeight = g.getWeight(currentVertex, nextVertex); // Using the correct method name

			if (edgeWeight == -1) { // If edge does not exist
				return -1;
			}

			totalWeight += edgeWeight;
		}

		return totalWeight;
	}

	



}

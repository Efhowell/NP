import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
/**
 * Represented a weighted undirected graph
 * @author Catie Baker
 */
public class WeightedGraphList<E> extends WeightedDiGraphList<E>{

	    private Map<E, Map<E, Integer>> adjacencyList = new HashMap<>();

	

	/**
	 * Constructs an simple graph.
	 */
	public WeightedGraphList(List<Triple<E>> edges) {
		super(edges);
        for (Triple<E> edge : edges) {
            addEdge(edge.getStart(), edge.getEnd(), edge.getWeight());
        }
    }
	

	/**
	 * Adds an edge to the graph.
	 *   @param v1 the vertex at the start of the edge
	 *   @param v2 the vertex at the end of the edge
	 */
	protected void addEdge(E v1, E v2, int w) {
		super.addEdge(v1, v2, w);
		super.addEdge(v2, v1, w);
		adjacencyList.putIfAbsent(v1, new HashMap<>());
        adjacencyList.putIfAbsent(v2, new HashMap<>());
        adjacencyList.get(v1).put(v2, w);
        adjacencyList.get(v2).put(v1, w);
	}

	public List<E> getVertices() {
        return new ArrayList<>(adjacencyList.keySet());
    }

	/**
     * Retrieves the weight of an edge between two vertices.
     * @param from the starting vertex
     * @param to the ending vertex
     * @return the weight of the edge if it exists, null otherwise
     */
    public Integer getEdgeWeight(E from, E to) {
        if (adjacencyList.containsKey(from)) {
            return adjacencyList.get(from).get(to);
        }
        return null; // If 'from' vertex does not exist or no edge exists from 'from' to 'to'
    }


}



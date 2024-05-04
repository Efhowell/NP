import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.List;
/**
 * Represents a weighted directed graph
 * @author Catie Baker
 *
 */
public class WeightedDiGraphList<E> {
	protected Map<E, Map<E,Integer>> adjLists;

	/**
	 * Constructs a directed graph with the specified edges.
	 *   @param edges the list of edges (vertex pairs)
	 */
	public WeightedDiGraphList(List<Triple<E>> edges) {
		this.adjLists = new HashMap<E, Map<E,Integer>>();
		for (Triple<E> edgePair : edges) {
			addEdge(edgePair.getStart(), edgePair.getEnd(), edgePair.getWeight());            
		}
	}

	/**
	 * Adds an edge to the graph.
	 * @param v1 the starting vertex of the edge
	 * @param v2 the ending vertex of the edge
	 */
	protected void addEdge(E v1, E v2, int w) {
		if (!this.adjLists.containsKey(v1)) {     
			this.adjLists.put(v1, new HashMap<E,Integer>());
		}
		this.adjLists.get(v1).put(v2,w);         
	}

	/**
	 * Determines whether the two vertices are adjacent.
	 *   @param v1 the vertex at the start of the edge
	 *   @param v2 the vertex at the end of the edge
	 *   @return true if v1->v2 edge is in the graph
	 */
	public boolean isAdjacentTo(E v1, E v2) {
		return this.adjLists.containsKey(v1) && this.getAllAdjacent(v1).contains(v2);
	}

	/**
	 * Gets the weight of the edge from v1 to v2
	 * @param v1 the start vertex
	 * @param v2 the end vertex
	 * @return the weight of the edge or -1 if the edge does not exist
	 */
	public int getWeight(E v1, E v2) {
		if(!this.isAdjacentTo(v1, v2)) return -1;
		return this.adjLists.get(v1).get(v2);
	}

	/**
	 * Gets all adjacent vertices of the specified vertex.
	 *   @param v a vertex in the graph
	 *   @return a set of all vertices adjacent to v
	 */
	public Set<E> getAllAdjacent(E v) {
		if (!this.adjLists.containsKey(v)) {  
			return new HashSet<E>();
		}
		return this.adjLists.get(v).keySet();
	}

}


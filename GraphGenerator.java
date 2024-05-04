import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Random;
import java.util.Scanner;

/**
 * Code for randomly generating weighted graph with edge weights assigned a
 * random value between 1 and 500
 * 
 * @author Catie Baker
 */
public class GraphGenerator {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("How many vertices should be in the graph?");
		int numVert = Integer.parseInt(in.nextLine());

		System.out.println("What should the file name of the graph be?");
		String filename = in.nextLine();
		List<Triple<String>> g = GraphGenerator.genGraphSet(numVert);
		GraphGenerator.writeGraph(g, filename);

	}

	/**
	 * Randomly generates a list of edges for a complete graph, given the specified
	 * number of vertices. The vertices will be labeled Vertex1 through VertexN,
	 * with N being the number of vertices. The weights will be for an undirected
	 * graph and will be randonly generated numbers between 1 and 500.
	 * 
	 * @param numVertices the number of vertices to have in the graph
	 * @return the list of weighted edges of the complete graph that is randomly
	 *         generated
	 */
	public static List<Triple<String>> genGraphSet(int numVertices) {
		Random r = new Random();
		ArrayList<Triple<String>> g = new ArrayList<Triple<String>>();
		for (int i = 1; i <= numVertices; i++) {
			for (int j = i + 1; j <= numVertices; j++) {
				String a = "Vert" + i;
				String b = "Vert" + j;
				g.add(new Triple<String>(a, b, r.nextInt(500) + 1));
			}
		}
		return g;
	}

	/**
	 * Writes the list of weighted edges to a file with the provided name
	 * 
	 * @param g    the list of edges
	 * @param file the file name
	 */
	public static void writeGraph(List<Triple<String>> g, String file) {
		try {
			FileWriter fw = new FileWriter(new File(file));
			System.out.println(g.size());
			for (Triple<String> t : g) {
				fw.write(t.getStart() + "," + t.getEnd() + "," + t.getWeight() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("Error writing to the file");
		}
	}
}

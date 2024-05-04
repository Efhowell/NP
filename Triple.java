/**
 * A class to represent a weighted edge
 * @author Catie Baker
 *
 */
public class Triple<E> implements Comparable<Triple<E>>{
	private E start; 
	private E end;
	private int weight;
	
	/**
	 * Creates a triple to store information about a weighted edge
	 * @param s the start vertex 
	 * @param e the end vertex
	 * @param weight the weight of the edge
	 */
	public Triple(E s, E e, int weight) {
		this.start = s;
		this.end = e;
		this.weight = weight;
	}
	
	/**
	 * Gets the start vertex
	 * @return the start vertex
	 */
	public E getStart() {
		return this.start;
	}
	
	/**
	 * Gets the end vertex
	 * @return the end vertex
	 */
	public E getEnd() {
		return this.end;
	}
	
	/**
	 * Gets the weight of the edge
	 * @return the weight of the edge
	 */
	public int getWeight() {
		return this.weight;
	}
	
	/**
	 * Compares the two triples based on weight
	 * @param other the triple to compare to
	 * @return  a negative integer if this < other
	 * 			0 if this = other
	 * 			a positive integer if this > other
	 */
	public int compareTo(Triple<E> other) {
		return this.weight - other.weight;
	}
}

/**
 * 
 * @author jia_wu
 * Vertex<O> is a class that represents a vertex and stores an item of type O
 */

import java.util.Iterator;
import java.util.LinkedList;

public class Vertex<O>
{
	// Attributes
	O object;					// Represents object stored at vertex
	LinkedList <Edge<O>> adjacentVertices = new LinkedList <Edge<O>>(); // This is the adjacency list	
	boolean visited = false;	// Vertex is by default unvisited	
	Position heapPosition;		// Position of the Vertex item in heap
	int distance; 				// Distance of nodes, for use in MST
	Vertex<O> parent = null;	// Vertex by default has no parent
	
	
	/**
	 * Vertex is the constructor for a vertex object
	 * @param objectIn is the object to be stored at the vertex 
	 */
	public Vertex (O objectIn)
	{
		this.object = objectIn;		// Make the object
	}
	

	/**
	 * *getObject() will get an object stored at a vertex
	 * @return the object stored at a vertex
	 */
	public O getObject()
	{
		return this.object;
	}
	
	
	/**
	 * addAdjacent will take a edge and insert it between 2 vertices
	 * @param e is the edge to be inserted
	 * @throws GraphException if the edge already exists between either vertex
	 */
	public void addAdjacent(Edge<O> e) throws GraphException
	{
		// Get 2 endpoints of edge
		Vertex<O> v1 = e.getEndPoint1();
		Vertex<O> v2 = e.getEndPoint2();
		
		// Check if edge with endpoints (v1,v2) or (v2,v1) is already in list
		Iterator<Edge<O>> check = adjacentVertices.iterator();	
		
		while (check.hasNext())
		{
			Edge<O> checking = check.next();
			if ((checking.endPoint1 == v1 && checking.endPoint2 == v2) | (checking.endPoint2 == v1 && checking.endPoint1 == v2))
			{
				throw new GraphException("The Edge is already in the list.");
			}
		}
		adjacentVertices.add(e);	//Add edge since no exception was thrown
	}
	

	/**
	 * incidentEdges() is a method to get all the incident edges of a vertex object
	 * @return all the incident edges of a vertex
	 */
	public Iterator<Edge<O>> incidentEdges()
	{
		Iterator<Edge<O>> incident = adjacentVertices.iterator();
		return incident;
	}

	/**
	 * isAdjacent is a method to determine whether 2 vertices are adjacent
	 * @param v	is the vertex to be compared
	 * @return true/false based on if adjacent or not
	 */
	public boolean isAdjacent (Vertex<O> v)
	{
		Iterator<Edge<O>> adjacent = adjacentVertices.iterator();
		Edge<O> compare;

		// Check the adjacency lists
		while (adjacent.hasNext())
		{
			compare = adjacent.next();
			if (compare.endPoint1 == v | compare.endPoint2 == v) //If vertex is either endpoint of an edge
			{
				return true;
			}
		}

		return false; // Return false because nothing found
	}

	
	/**
	 * setVisited() is a method to update if a node ahs been visisted or not
	 * this is public and is required for connectedComponents method in Graph.java
	 */
	public void setVisited()
	{
		this.visited = true;
	}
	
	
	/**
	 * visited is a method to return if a node is visited or not
	 * @param v is the node to be checked
	 * @return if a node has been visited or not
	 * this is public and needs to be for the connectedComponents method
	 */
	public boolean visited(Vertex<O> v)
	{
		return visited;
	}
	
	
	/**
	 * getPosition is a method to return the position of a vertex on the heap
	 * @return the position of a vertex on the heap
	 * this is public and needs to help BFS creation of connectedComponents
	 */
	public Position getPosition()
	{
		return this.heapPosition;
	}
	
	
	/**
	 * setPosition will set a position of a vertex on a Heap
	 * @param pos is the position of the node
	 * this is public and needs to be for MST
	 */
	public void setPosition(Position pos)
	{
		this.heapPosition = pos;
	}
	

	/**
	 * getDistance is a method to return a vertex's distance
	 * @return the distance of a node in the MST
	 * this is public and is required for MST
	 */
	public int getDistance()
	{
		return distance;
	}
	
	
	/**
	 * setDistance will set the distance of a node in the MST
	 * @param d is the distance to be set
	 * this is public and is required for MST
	 */
	public void setDistance(int d)
	{
		this.distance = d;
	}
	
	
	/**
	 * getParent is a method to get the parent vertex of a node
	 * @return the parent vertex object
	 * this is required in order to determine the MST edges
	 */
	public Vertex<O> getParent()
	{
		return this.parent;
	}
	

	/**
	 * setParent is a method to set the parent vertex of a node
	 * @param p is the parent of the node
	 * this is required in order to determine MST tree edges
	 */
	public void setParent(Vertex<O> p)
	{
		this.parent = p;
	}
	
	/**
	 * removeAdjacent will clear the adjacency list of a vertex
	 * this is public and is required because of MST impelmenentation to remove k-1 edges
	 */
	public void removeAdjacent()
	{
		this.adjacentVertices.clear();
	}
}

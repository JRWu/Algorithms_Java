/**
 * 
 * @author jia_wu
 * Graph is a class that represents a graph of type <O>
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class Graph<O> implements GraphInterface<O>
{
	// Attributes
	int numVertices, numEdges;
	LinkedList <Vertex<O>> vertices = new LinkedList <Vertex<O>>(); // This is a list of all vertices
	
	
	/**
	 * Graph is the constructor for a graph which stores Vertex<O> objects
	 * has default 0 edges and 0 vertices
	 */
	public Graph()
	{
		// Set default starting values for the Graph <O>
		numVertices = 0;
		numEdges = 0;
	}
	
	
	/**
	 * insertVertex will put a vertex into the graph
	 * @param o is the object to be stored in a vertex
	 * @return returns the vertex inserted into the graph
	 */
	public Vertex<O> insertVertex(O o)
	{
		Vertex<O> insert = new Vertex<O>(o);	// Create new vertex, store vertex
		numVertices ++;							// Increment number of vertices
		vertices.add(insert);
		return insert;
	}
	
	
	/**
	 * getNumVertices returns the number of vertices in a graph
	 * @return the number of vertices in a graph
	 */
	public int getNumVertices()
	{
		return numVertices; 
	}

	
	/**
	 * getNumEdges returns the number of edges in a graph
	 * @return the number of edges in a graph
	 */
	public int getNumEdges()
	{
		return numEdges;
	}
	
	/**
	 * findEdge will give an edge object in a graph
	 * @param u is the EndPoint1
	 * @param v is the EndPoint2
	 * @throws GraphException if the edge does not exist
	 * @return null if nothing found, or the edge if found
	 */
	public Edge<O> findEdge(Vertex<O> u, Vertex<O> v) throws GraphException
	{
		if (u == null | v == null)
		{
			throw new GraphException("The vertices are null pointers.");
		}
		
		// Traverse the adjacency list of 1st vertex
		Iterator <Edge<O>> uEdge = u.incidentEdges();
		Edge<O> temp;
		
		// Check the whole adjacency list
		while (uEdge.hasNext())
		{
			temp = uEdge.next();
			if (temp.endPoint1 == v | temp.endPoint2 ==v)
			{
				return temp;
			}
		}
		
		// Traverse the adjacency list of the 2nd vertex
		Iterator <Edge<O>> vEdge = v.incidentEdges();
		
		while (vEdge.hasNext())
		{
			temp = vEdge.next();
			if (temp.endPoint1 == u | temp.endPoint2 == u)
			{
				return temp;
			}
		}
		return null; // Edge does not exist
	}
	
	
	/**
	 * areAdjacent will determine if 2 vertices are adjacent to each other
	 * @param v	EndPoint1
	 * @param u EndPoint2
	 * @throws GraphException if either of the vertices are null
	 */
	public boolean areAdjacent(Vertex<O> v, Vertex<O> u) throws GraphException
	{
		if (u == null | v == null) //Null vertices?
		{
			throw new GraphException("The vertices are null.");
		}
		
		if (v.isAdjacent(u)) 
		{
			return true; // Adjacent
		}
		return false;	 // Not adjacent
	}

	
	/**
	 * insertEdge will put an edge between 2 vertex objects with a given weight
	 * @param v EndPoint1
	 * @param u EndPoint2
	 * @param weight is the weight of the edge object
	 * @throws GraphException if a self loop is attempted or if the edge already exists
	 */
	public void insertEdge (Vertex<O> v, Vertex<O> u, int weight) throws GraphException
	{
		// Dont need iterator over edges, so simply incrementing edges will suffice
		numEdges ++;	// Increment # of edges
		
		if (v == u)		// Self loop?
		{
			throw new GraphException("Cannot have self loops!");	
		}
		
		if (findEdge(v,u) != null) // Check if the edge exists already (NOT NULL)
		{
			throw new GraphException("The Edge Already Exists.");
		}
		
		else	// Edge does not exist, can create
		{
			Edge<O> newEdge = new Edge<O> (v,u,weight);
			v.addAdjacent(newEdge);							// Add adjacent edge for vertex 1
			Edge<O> newEdge2 = new Edge<O> (u,v,weight);
			u.addAdjacent(newEdge2); 						// Add adjacent edge for vertex 2
		}
	}
	
	
	/**
	 * deleteEdge will remove a edge object from the graph and delete it from the adjacency lists of its 2 endpoints
	 * @param e is the edge to be deleted
	 * @throws GraphException if the edge does not exist
	 */
	public void deleteEdge (Edge<O> e) throws GraphException
	{
		if (e == null)
		{
			throw new GraphException("The edge does not exist.");			
		}
		numEdges --; // Decrement the number of edges in the graph

		// Gotta go to the adjacency lists of both nodes and delete it
		Vertex<O> v1 = e.endPoint1;
		Vertex<O> v2 = e.endPoint2;
		v1.adjacentVertices.remove(e);
		v2.adjacentVertices.remove(e);
	}
	
	
	/**
	 * vertices will return an iterator over all vertices in the graph
	 * @return iterator over all vertices
	 */
	public Iterator<Vertex<O>> vertices()
	{
		Iterator<Vertex<O>> vertexIt = vertices.iterator();
		return vertexIt;
	}
	

	/**
	 * giveOpposite will give the opposite endpoint of a given vertex and edge
	 * @param v is the vertex we want the opposite of 
	 * @param e is the edge we wanna check
	 * @return the opposite endpoint of a given vertex
	 */
	public Vertex<O> giveOpposite(Vertex<O> v, Edge<O> e)
	{
		// Get endpoints of the edge
		Vertex<O> v1 = e.endPoint1;
		Vertex<O> v2 = e.endPoint2;
		
		if (v1 == v)	// If not 1st edge
		{
			return v2;	// Return second
		}
		else
		{
			return v1;	// return first edge because v is 2nd edge
		}
	}
		
	
	/**
	 * ConnectedComponents will calculate the number of connected components in a given graph
	 * Uses BFS as a helper method to calculate connected components
	 * @return an Iterator of Iterator of Vertex objects
	 */
	public Iterator<Iterator<Vertex<O>>> ConnectedComponents()
	{
		// LinkedList is a container for the Iterator of Vertices
		LinkedList <Iterator<Vertex<O>>> connectedComponents = new LinkedList <Iterator<Vertex<O>>>();	
		return BFS(this, connectedComponents);
	}

//	private Iterator<Vertex<O>> DFS(Graph<O> graph, Vertex<O> vertex, LinkedList<Vertex<O>> component)
//	{
//		return null;
//	}
	
	// Takes the bigger container
	/**
	 * BFS is a breadth first search over the vertices in a graph
	 * @param graph is the graph to be searched
	 * @param connectedComponents is the container in which a connectd component is stored
	 * @return an iterator over the connected components
	 */
	private Iterator<Iterator<Vertex<O>>> BFS(Graph<O> graph, LinkedList <Iterator<Vertex<O>>> connectedComponents)
	{
		Iterator<Vertex<O>> allVertices = graph.vertices();
		Vertex<O> currentVertex;
		
		// Iterate through all vertices to check if nodes are unmarked
		while (allVertices.hasNext())
		{
			currentVertex = allVertices.next();
			if (currentVertex.visited == false)
			{
				// Must call BFS because we encountered an unmarked node
				connectedComponents.add(BFS(currentVertex)); // Add iterator over vertices to iterator
			}
		}
		return connectedComponents.iterator();	// Return an iterator over an interator of vertex objects
	}
	
	
	/**
	 * BFS is a non-recursive method to search all the vertices of a graph
	 * @param unvisitedVertex is a vertex that is unvisited 
	 * @return an iterator over vertex objects
	 */
	private Iterator<Vertex<O>> BFS(Vertex<O> unvisitedVertex)
	{
		LinkedList <Vertex<O>> connectedComponent = new LinkedList <Vertex<O>>(); 
		Stack<Vertex<O>> newStack = new Stack<Vertex<O>>();
		Vertex<O> nextV, oppositeV;
		Edge<O> incidentEdge;
		Iterator <Edge<O>> adjacent;
		
		newStack.add(unvisitedVertex);	// Add a node to stack if unvisited
		unvisitedVertex.setVisited();	// Mark it visited
		connectedComponent.add(unvisitedVertex);	// Add a node to the connected component 
		
		while (newStack.empty() == false)
		{
			nextV = newStack.pop();				// check next vertex
			adjacent = nextV.incidentEdges();	// for all e = G.incidentEdges(v)
			
			while (adjacent.hasNext())
			{
				incidentEdge = adjacent.next();
				oppositeV = this.giveOpposite(nextV, incidentEdge);
				if (oppositeV.visited == true)
				{
					// Do nothing cause it's visited
				}
				else
				{
					oppositeV.setVisited();	// Add next unvisited vertex to the stack
					newStack.add(oppositeV);
					connectedComponent.add(oppositeV);	// Add the next unvisited vertex to the connected component
				}
			}			
		}
		
		return connectedComponent.iterator();	// Return Iterator over vertex objects
	}
	
	/**
	 * MST is an iterator over all the edge objects that comprise a minimum spanning tree
	 * @return an iterator over edge objects in a graph
	 */
	public Iterator <Edge<O>> MST()
	{
		IntegerComparator intComparator = new IntegerComparator();	// Comparator for HeapPQ implementation 
		Iterator<Vertex<O>> allVertices = this.vertices();			// Get all vertices
		HeapPQ<Integer, Vertex<O>> queue = new HeapPQ <Integer, Vertex<O>> (2 * numVertices, intComparator);	// Create heap object to store vertices
		
		Vertex<O> s, v;
		Position i;
		
		// Set up the vertices for a Priority Queue
		if (allVertices.hasNext())
		{
			s = allVertices.next();
			s.setDistance(0);	// Make original node distance 0
			i = queue.insert(s.getDistance(), s);
			s.setPosition(i);	
			
			while (allVertices.hasNext())
			{
				s = allVertices.next();
				s.setDistance(2147483647);	 // Make every other node distance (infinity) 
				i = queue.insert(s.getDistance(), s);
				s.setPosition(i);
			}			
		}
		
		Iterator<Edge<O>> adjacentEdges;
		Edge<O> adjacent;
		int r;
		
		while (queue.isEmpty() == false)
		{
			v = queue.removeMin();				// Get the smallest queue item
			v.setVisited();
			adjacentEdges = v.incidentEdges();	// get all G.incidentEdges(v)
			
			while (adjacentEdges.hasNext())
			{
				adjacent = adjacentEdges.next();
				s = this.giveOpposite(v,adjacent);	// z <- g.Opposite(u,e)
				
				if (s.visited == false)				// If the node is unvisited
				{
					r = adjacent.getWeight();
					if (r < s.getDistance())
					{
						s.setDistance(r);
						s.setParent(v);
						queue.decreaseKey(s.getPosition(), r);	// Relax the distance
					}	
				}				
			}			
		}
		
		LinkedList <Edge<O>> mstEdges = new LinkedList <Edge<O>>();
		// Go through your nodes
		Iterator <Vertex<O>> checkVertices = this.vertices();
		Vertex<O> check;
		
		while (checkVertices.hasNext())	// Check all vertices
		{
			check = checkVertices.next();
			if (check.parent != null)
			{
				mstEdges.add(this.findEdge(check,check.getParent()));	// Get all edges of node-parent interactions
			}		
		}

		return mstEdges.iterator();
	}
}

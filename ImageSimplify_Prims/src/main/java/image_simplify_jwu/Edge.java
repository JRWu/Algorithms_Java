/**
 * 
 * @author jia_wu
 * Edge<O> is a class that represents an edge between 2 vertices, (u,v)
 */

public class Edge<O>
{
	// Attributes
	Vertex<O> endPoint1;
	Vertex<O> endPoint2;
	int weight;
	
	/**
	 * Edge is the constructor for an edge object
	 * @param u is EndPoint1
	 * @param v is EndPoint2
	 * @param weight is the weight of the edge
	 */
	Edge(Vertex<O> u, Vertex<O> v, int weight)
	{
		this.endPoint1 = u;
		this.endPoint2 = v;
		this.weight = weight;
	}
	
	// Methods
	/**
	 * getEndPoint1 will get the 1st endpoint of a given vertex
	 * @return the object stored at the endpoint
	 */
	public Vertex<O> getEndPoint1()
	{
		return this.endPoint1;
	}

	
	/**
	 * getEndPoint2 will get the 2nd endpoint of a given vertex
	 * @return the object stored at the endpoint
	 */
	public Vertex<O> getEndPoint2()
	{
		return this.endPoint2;
	}

	/**
	 * getWeight will get the weight of an edge between 2 vertices
	 * @return the weight of an edge
	 */
	public int getWeight()
	{
		return this.weight;
	}
	
}

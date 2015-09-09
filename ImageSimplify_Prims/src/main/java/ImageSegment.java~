/*
 * ImageSegment.java
 * 
 * ImageSegment implements Prim's Algorithm on any image to create a minimum 
 * spanning tree (MST) of the number of connected components a user specifies.
 * The program then averages the RGB values of each connected component and 
 * outputs the file to a file the user specifies. 
 * 
 * Usage: java -jar target/ImageSimplify_Prims-1.0-jar-with-dependencies.jar 
	input_file.jpg output_file.jpg num_components
 * 
 */

import java.io.*;
import java.util.*;
import java.lang.Math;
import image_simplify_jwu.*;

public class ImageSegment 
{
	public static void main (String args[])
	{
		// Check arguments
		if (args.length != 3)
		{
			System.out.println("Args: input.jpg output.jpg n");
			System.exit(0);
		}
		
		int k = Integer.valueOf(args[2]); // Number of components
		
		try
		{		
			// Read in an image and convert it into a graph 
			ImageReader picture = new ImageReader(args[0]);
			int imgHeight = picture.getHeight();
			int imgWidth = picture.getWidth();
			int red, green, blue;
			
			Graph<Pixel> imageGraph = new Graph<Pixel>();
			
			// Use Vector to store vertex objects for later reference
			Vector<Vertex<Pixel>> lookup = new Vector<Vertex<Pixel>> (imgWidth * imgHeight);
			lookup.setSize(imgWidth * imgHeight);
			
			// Create the graph
			for (int y = 0; y < imgHeight; y++)	// 0 ... Y-1 axis
			{
				for (int x = 0; x < imgWidth; x++)	// 0... X-1 axis
				{
					red = picture.getRed(x, y);
					green = picture.getGreen(x, y);
					blue = picture.getBlue(x, y);
					
					Pixel pix = new Pixel(x, y, red, green, blue);
					
					lookup.setElementAt(imageGraph.insertVertex(pix),  x+y*imgWidth);	// Store vertex objects into Vector
				}
			}
			
			int edgeWeight;
			// Gotta loop through the vector now to insert edges
			for (int y = 0; y < imgHeight; y++)
			{
				for (int x = 0; x < imgWidth; x++)
				{
					if (x < imgWidth-1) // Horizontal Edges
					{
						Vertex<Pixel> v1 = lookup.get(x+y*imgWidth);
						Vertex<Pixel> v2 = lookup.get((x+1)+y*imgWidth);

						edgeWeight =( Math.abs(v1.getObject().getRed() - v2.getObject().getRed()) + Math.abs(v1.getObject().getGreen() - v2.getObject().getGreen()) + Math.abs(v1.getObject().getBlue() - v2.getObject().getBlue()) );
						imageGraph.insertEdge(v1, v2, edgeWeight);
					}
					
					if (y > 0)	// Vertical Edges
					{
						Vertex<Pixel> v1 = lookup.get(x + (y-1) * imgWidth);
						Vertex<Pixel> v2 = lookup.get(x + y * imgWidth);
						
						edgeWeight =( Math.abs(v1.getObject().getRed() - v2.getObject().getRed()) + Math.abs(v1.getObject().getGreen() - v2.getObject().getGreen()) + Math.abs(v1.getObject().getBlue() - v2.getObject().getBlue()) );
						imageGraph.insertEdge(v1,v2,edgeWeight);
					}
				}
			}
			
			Iterator <Edge<Pixel>> mst = imageGraph.MST();	// Get the MST of the graph
			Iterator <Vertex<Pixel>> allVertices = imageGraph.vertices();
			
			while (allVertices.hasNext())
			{
				(allVertices.next()).removeAdjacent();	// Clear all edges from the graph
			}
			imageGraph.numEdges = 0;	// Reset the number of edges in the graph to 0
			
			
			ReverseIntegerComparator rComparator = new ReverseIntegerComparator();	// To help in removing K-1 edges
			HeapPQ <Integer,Edge<Pixel>> mstEdgeHeap = new HeapPQ <Integer, Edge<Pixel>>((imgHeight*imgWidth), rComparator);
			Edge<Pixel> mstEdge;
			
			// Sort the Edge objects
			while (mst.hasNext())
			{
				mstEdge = mst.next();
				mstEdgeHeap.insert(mstEdge.getWeight(), mstEdge);
			}
			
			// Remove K-1 edges
			for (int i = 0; i < (k-1); i++)
			{
				if (mstEdgeHeap.isEmpty() == false)	// Condition to check for an empty heap
				{
					mstEdgeHeap.removeMin();	// Remove k-1 edges
				}
			}
			
			// Re-enter all of the MST edges into the graph
			LinkedList <Edge<Pixel>> mstKEdges = new LinkedList <Edge<Pixel>>();
			while (mstEdgeHeap.isEmpty() == false)
			{
				mstKEdges.add(mstEdgeHeap.removeMin());
			}
			
			Iterator <Edge<Pixel>> alteredEdges = mstKEdges.iterator();			
			Edge<Pixel> finalEdge;
			while (alteredEdges.hasNext())
			{
				finalEdge = alteredEdges.next();
				imageGraph.insertEdge(finalEdge.getEndPoint1(), finalEdge.getEndPoint2(), finalEdge.getWeight());
			}			
			
			
			// Reset all vertices for connected-components method
			allVertices = imageGraph.vertices();
			while (allVertices.hasNext())
			{
				(allVertices.next().visited) = false;
			}
			
			// Get the connected components of the graph
			Iterator <Iterator<Vertex<Pixel>>> connectedComponents = imageGraph.ConnectedComponents();
			allVertices = imageGraph.vertices();
			
			while (allVertices.hasNext())
			{
				(allVertices.next().visited) = false;
			}
			
			// Re-colour the pixels
			Iterator <Iterator<Vertex<Pixel>>> connectedComponents2 = imageGraph.ConnectedComponents();	// Create 2nd to copy by value not reference
			Iterator<Vertex<Pixel>> component;
			Iterator<Vertex<Pixel>> componentCopy;
			Vertex<Pixel> componentPixels;
			int numPix = 0, totalRed = 0, totalGreen = 0, totalBlue = 0;
			int index, x, y;
			int segment = 0;
			
			// Go throught each connected component
			while (connectedComponents.hasNext())
			{
				segment ++;
				component = connectedComponents.next();	// Each component represents 1 connected component
				componentCopy = connectedComponents2.next();
				
				while (component.hasNext())					
				{
					// Calculate the information of each connected component
					componentPixels = component.next();	// Get the pixels of the connected component 
					numPix ++;
					totalRed = totalRed + componentPixels.getObject().getRed();
					totalGreen = totalGreen + componentPixels.getObject().getGreen();
					totalBlue = totalBlue + componentPixels.getObject().getBlue();

				}
				totalRed = (totalRed/numPix);
				totalGreen = (totalGreen/numPix);
				totalBlue = (totalBlue/numPix);
				
				System.out.println("Segment "+ segment + " of size " + numPix + ": Red "+totalRed+" Green "+totalGreen+" Blue "+totalBlue);
				
				while (componentCopy.hasNext()) 
				{
					componentPixels = componentCopy.next();
					picture.setColor(componentPixels.getObject().getX(), componentPixels.getObject().getY(), totalRed, totalGreen, totalBlue);					
				}
				
				numPix = 0;
				totalRed = 0;
				totalGreen = 0;
				totalBlue = 0;
			}
			System.out.println("Number of segments is " + segment);
			picture.saveImage(args[1]);
			
		}
		catch (Exception e)
		{
			System.out.println(e);
		}			
	}
}

/**
 * 
 * @author jia_wu
 * GraphException is called whenever a runtime error is encountered
 */

public class GraphException extends RuntimeException
{
	public GraphException (String Exception) 
	{
		super (Exception);					// Create a exception message with the parent constructor
	}
}

/*@
 *
 *
 *
 *
 * @version Oct 17, 2011
 * @author Jonathan Moorman <jonmoo1@umbc.edu>
 * @project CMSC 202 - Spring 2011 - Project 1
 * @section 02
 */
package proj2;

//your package statement here



/**
* Exception class for access in empty containers
* such as stacks, queues, and priority queues.
* @author Mark Allen Weiss
*/
public class UnderflowException extends RuntimeException
{

 /** default constructor **/
 public UnderflowException( )
 {
	this("Underflow");
 }

 /**
  * Construct this exception object.
  * @param message the error message.
  */
 public UnderflowException( String message )
 {
     super( message );
 }
}

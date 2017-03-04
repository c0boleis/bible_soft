package books.model.interfaces;

/**
 * 
 * @author C.B.
 *
 * @param <X>
 */
public interface IRule<X> {

	/**
	 * @param object to check
	 * @return null if the object is OK, else
	 * return an error String
	 */
	public String checkRule(X object);
	
	/**
	 * @param object to modify
	 * @return if the object is OK, return the object
	 * with modification if needed. 
	 */
	public X modifyWithRule(X object);
}

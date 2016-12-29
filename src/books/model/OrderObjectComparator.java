/**
 * 
 */
package books.model;

import java.util.Comparator;

/**
 * @author bata
 *
 */
public class OrderObjectComparator implements Comparator<IOrderedObject> {

	/**
	 * 
	 */
	public OrderObjectComparator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IOrderedObject o1, IOrderedObject o2) {
		return Integer.compare(o1.getOrder(), o2.getOrder());
	}

}

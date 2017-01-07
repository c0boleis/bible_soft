/**
 * 
 */
package books.model;

import java.util.Comparator;

import books.model.interfaces.IOrderedObject;

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
		if(o1.getOrder()<0 && o2.getOrder()>=0){
			return 1;
		}else if(o1.getOrder()>=0 && o2.getOrder()<0){
			return -1;
		}else if(o1.getOrder()<0 && o2.getOrder()<0){
			return o1.toString().compareTo(o2.toString());
		}
		return Integer.compare(o1.getOrder(), o2.getOrder());
	}

}

package books.model.interfaces;

import books.model.listener.OrderedObjectListener;

/**
 * 
 * @author Corentin Boleis
 * @version 0.1
 * @since 0.1
 * 
 * A sub-division who can contains other subDivision or
 * some text
 *
 */
public interface ISubDivision extends IOrderedObject,ITextContainer,
IPropertiesUsed,ISubDivisonContainer, IShearable, ILoadSaveObject{
	
	public String getName();
	
	public String setName(String newName);
	
	public String getAbv();
	
	public String setAbv(String newAbv);
	
	public String getHierarchy();
	
	public String setHierarchy(String newHierarchy);
	
	public String getPath();
	
	/**
	 * 
	 * @return the subDivision if isn't a book
	 * else return null
	 */
	public ISubDivision getSubDivision();
	
	/**
	 * 
	 * @return the book of the subdivision
	 * the book can't be null
	 */
	public IBook getBook();
	
	public IText getText(String name);
	
	public OrderedObjectListener[] getOrderListeners();
	
	public void addOrderListener(OrderedObjectListener listener);
	
	public void removeOrderListener(OrderedObjectListener listener);
	
	public boolean allowListener();
	
	public void setAllowListener(boolean allow);

}

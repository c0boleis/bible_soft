package books.model.interfaces;

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
	
	public String getAbv();
	
	public String getHierarchy();
	
	/**
	 * 
	 * @return the subDivision if isn't a book
	 * else return null
	 */
	public ISubDivision getSubdivions();
	
	/**
	 * 
	 * @return the book of the subdivision
	 * the book can't be null
	 */
	public IBook getBook();

}

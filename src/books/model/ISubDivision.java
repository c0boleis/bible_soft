package books.model;

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
public interface ISubDivision extends IOrderedObject,ITextContainer, IPropertiesUsed,ISubDivisonContainer{
	
	public String getName();
	
	public String getAbv();

}

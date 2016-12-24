package books.model;

/**
 * 
 * @author Corentin Boleis
 * @version 0.1
 * @since 0.1
 * 
 * The smallest division, this one contains the text
 *
 */
public interface IText extends IOrderedObject{

	public String getName();
	
	public String getText();
	
	public void setText();

}

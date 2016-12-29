package books.model;

import books.IReadable;

/**
 * 
 * @author Corentin Boleis
 * @version 0.1
 * @since 0.1
 * 
 * The smallest division, this one contains the text
 *
 */
public interface IText extends IOrderedObject,IReadable,
IShearable,ILoadSaveObject{

	public String getName();
	
	public String getText();
	
	public void setText(String t);

	public String getRefference();
	
	public ISubDivision getSubDivision();

}

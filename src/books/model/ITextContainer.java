package books.model;

import books.IReadable;

public interface ITextContainer extends IReadable{
	
	public IText[] getTexts();
	
	public void loadTexts();
	
	public boolean isTextEmpty();

}

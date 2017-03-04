package books.model.interfaces;

import books.model.listener.TextAddedListener;

public interface ITextContainer extends IReadable{
	
	public IText[] getTexts();

	public void sortTexts();
	
	public void loadTexts();
	
	public boolean isTextEmpty();
	
	public int getNbrOfTexts();
	
	public int calculNbrOfTexts();
	
	public boolean isAutoOpen();
	
	public void addTextAddedListener(TextAddedListener listener);
	
	public void removeTextAddedListener(TextAddedListener listener);
	
	public TextAddedListener[] getTextAddedListeners();

}

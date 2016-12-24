package books.model;

public interface ITextContainer {
	
	public IText[] getTexts();
	
	public void loadTexts();
	
	public boolean isTextEmpty();

}
